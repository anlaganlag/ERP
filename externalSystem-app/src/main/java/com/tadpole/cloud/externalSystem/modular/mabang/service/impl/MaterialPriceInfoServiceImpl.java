package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MaterialPriceInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MaterialPriceInfoMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3StockReceiveSendDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MaterialPriceInfoParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MaterialPriceInfoResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMaterialPriceInfoService;
import com.tadpole.cloud.externalSystem.modular.mabang.task.QueryK3StockPriceCallable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * <p>
 * 物料价格信息 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2023-05-06
 */
@Service
@Slf4j
public class MaterialPriceInfoServiceImpl extends ServiceImpl<MaterialPriceInfoMapper, MaterialPriceInfo> implements IMaterialPriceInfoService {

    @Resource
    private MaterialPriceInfoMapper mapper;

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, BigDecimal> getMabangMatPrice(List<K3AvailableResult> availableResultList) {

        Set<String> matCodeSet = availableResultList.stream().map(a -> a.getMaterialCode()).collect(Collectors.toSet());
        List<MaterialPriceInfo> materialPriceList = new ArrayList<>();
        for (List<String> codeList : CollUtil.split(matCodeSet, 500)) {
            LambdaQueryWrapper<MaterialPriceInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MaterialPriceInfo::getMaterialCode, codeList);
            List<MaterialPriceInfo> materialList = mapper.selectList(queryWrapper);
            materialPriceList.addAll(materialList);
        }

        boolean haveMcMatPrice = true;
        if (ObjectUtil.isEmpty(materialPriceList)) {
            haveMcMatPrice = false; //没有记录则所有 本次erp查找的可用数量的物料都需要在mc系统增加 物料价格信息
        }

        List<MaterialPriceInfo> addOrUpdateInfoList = new ArrayList<>();
        Map<String, List<MaterialPriceInfo>> mcMatPriceMap = new HashMap<>();
        if (haveMcMatPrice) {
            mcMatPriceMap = materialPriceList.stream().collect(Collectors.groupingBy(MaterialPriceInfo::getMaterialCode));
        }

        Map<String, BigDecimal> resultMap = new HashMap<>();

        for (K3AvailableResult k3Result : availableResultList) {

            String matCode = k3Result.getMaterialCode();

            MaterialPriceInfo materialPriceInfo = new MaterialPriceInfo();

            if (haveMcMatPrice && mcMatPriceMap.containsKey(matCode)) {//MC能找到物料价格信息
                materialPriceInfo = mcMatPriceMap.get(matCode).get(0);
                //更新采购价和最近采购日期
                materialPriceInfo.setPurchasePrice(k3Result.getPurchasePrice());
                materialPriceInfo.setPurchaseDate(k3Result.getPurchaseDate());
                materialPriceInfo.setProductType(k3Result.getProductType());
                materialPriceInfo.setProductName(k3Result.getProductName());

            } else {//新增物料价格信息
                BeanUtil.copyProperties(k3Result, materialPriceInfo);
                materialPriceInfo.setId(matCode);

            }

            if (ObjectUtil.isNotNull(materialPriceInfo.getStockPrice())
                    || ObjectUtil.isNotNull(materialPriceInfo.getPurchasePrice())
                    || ObjectUtil.isNotNull(materialPriceInfo.getTemporaryPrice())) {
                materialPriceInfo.setNoPrice(BigDecimal.ZERO);
            }

            addOrUpdateInfoList.add(materialPriceInfo);

            BigDecimal mabangMatPrice = this.getMabangMatPrice(materialPriceInfo);
            if (ObjectUtil.isNotNull(mabangMatPrice)) {
                resultMap.put(matCode, mabangMatPrice);
            }
        }

        if (ObjectUtil.isNotEmpty(addOrUpdateInfoList)) {
            this.saveOrUpdateBatch(addOrUpdateInfoList);
        }

        return resultMap;
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData updateMatStockPrice(List<K3StockReceiveSendDetail> detailList) {
        Map<String, K3StockReceiveSendDetail> k3MatPriceMap = detailList.stream()
                .collect(Collectors.toMap(
                        d -> d.getFMATERIALID(),
                        d -> d,
                        (v1,v2)->(this.getStockCheckDate(v1.getFPERIOD()).compareTo(this.getStockCheckDate(v2.getFPERIOD()))>=0 ? v1 : v2)
                ));
        Set<String> matCodeSet = k3MatPriceMap.keySet();
        List<MaterialPriceInfo> needUpMatInfoList = new ArrayList<>();
        List<MaterialPriceInfo> addMatInfoList = new ArrayList<>();

        List<MaterialPriceInfo> matInfoList = new ArrayList<>();
        for (List<String> matCodes : CollUtil.split(matCodeSet, 500)) {
            LambdaQueryWrapper<MaterialPriceInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MaterialPriceInfo::getMaterialCode, matCodes);
            List<MaterialPriceInfo> infoList = this.baseMapper.selectList(queryWrapper);
            matInfoList.addAll(infoList);
        }
        Map<String, MaterialPriceInfo> mcMatPriceMap = matInfoList.stream().collect(Collectors.toMap(d -> d.getMaterialCode(), d -> d));


        for (K3StockReceiveSendDetail k3Detail : detailList) {
            String materialCode = k3Detail.getFMATERIALID();
            if (mcMatPriceMap.containsKey(materialCode)) {
                MaterialPriceInfo mcInfo = mcMatPriceMap.get(materialCode);
                mcInfo.setStockPrice(new BigDecimal(k3Detail.getFENDPrice()));
                mcInfo.setStockCheckDate(this.getStockCheckDate(k3Detail.getFPERIOD()));
                mcInfo.setUpdatedTime(new Date());
                needUpMatInfoList.add(mcInfo);
                continue;
            }

            if (ObjectUtil.isEmpty(k3Detail.getFENDPrice())) {
                //没有库存价
               continue;
            }

            MaterialPriceInfo newAddInfo = new MaterialPriceInfo();
            newAddInfo.setId(materialCode);
            newAddInfo.setMaterialCode(materialCode);
            newAddInfo.setProductName(k3Detail.getFMATERIALNAME());
            newAddInfo.setStockPrice(new BigDecimal(k3Detail.getFENDPrice()));
            newAddInfo.setStockCheckDate(this.getStockCheckDate(k3Detail.getFPERIOD()));

            newAddInfo.setNoPrice(BigDecimal.ZERO);
            addMatInfoList.add(newAddInfo);
        }

        if (ObjectUtil.isNotEmpty(needUpMatInfoList)) {
            this.updateBatchById(needUpMatInfoList, 1000);
            log.info("MCMS更新k3的物料库存价格-->已经更新数据【{}】条",needUpMatInfoList.size());
        }

        if (ObjectUtil.isNotEmpty(addMatInfoList)) {
            this.saveBatch(addMatInfoList, 1000);
            log.info("MCMS更新k3的物料库存价格-->新增数据【{}】条",addMatInfoList.size());
        }

        log.info("MCMS更新k3的物料库存价格-->新增或者更新总条数【{}】",matCodeSet.size());
        return ResponseData.success();
    }

    /**
     * 转换k3库存核算日期  默认每月最后一天
     * @param period
     * @return
     */
    private  Date getStockCheckDate(String period) {
        //K3返回格式2023.2  补齐.1 后2023.2.1 //保存每月最后一天
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy.M").parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();
        LocalDate stockCheckLocalDate = LocalDate.parse(period, formatter).with(TemporalAdjusters.lastDayOfMonth());
        Date stockCheckDate = Date.from(stockCheckLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return stockCheckDate;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData updateMatStockPrice(LocalDate tempDate) {
        List<K3StockReceiveSendDetail> matStockPriceList = getMatStockPrice(tempDate);
        if (ObjectUtil.isEmpty(matStockPriceList)) {
          return   ResponseData.error("未获取到k3的物料库存价格");
        }
        log.info("MCMS更新k3的物料库存价格-->K3数据已获取成功");
       return this.updateMatStockPrice(matStockPriceList);
    }


    @DataSource(name = "external")
    @Override
    public List<K3StockReceiveSendDetail> getMatStockPrice(LocalDate needUpdateDate) {
        log.info("MCMS更新k3的物料库存价格-->getMatStockPrice--入参--needUpdateDate[{}]",needUpdateDate);
        List<K3StockReceiveSendDetail> detailList = new ArrayList<>();

        int pageLimit = 2000;
        Integer pageSize = getPageCount(needUpdateDate, 0, pageLimit);
        if (ObjectUtil.isNull(pageSize)) {
            log.error("未能获取到分页数据,待更新时间【{}】",needUpdateDate);
            return null;
        }

        log.info("MCMS更新k3的物料库存价格-->本次查询分页总数"+pageSize);

        //创建一个FutureTask list来放置所有的任务
        List<FutureTask<String>> futureTasks=new ArrayList<>();

        for (Integer i = 0; i < pageSize; i++) {
            QueryK3StockPriceCallable task = new QueryK3StockPriceCallable(needUpdateDate, i * pageLimit, pageLimit);
            futureTasks.add(new FutureTask(task));
        }

        //创建线程池后，依次的提交任务，执行
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.initialize();
        taskExecutor.setCorePoolSize(pageSize);
        for (FutureTask<String> futureTask : futureTasks) {
            taskExecutor.execute(futureTask);
            try {
                Thread.sleep(1000*12);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < futureTasks.size(); i++) {

            FutureTask<String> future = futureTasks.get(i);
            String resutlStr = null;

            try {
                resutlStr = future.get(3, TimeUnit.HOURS);//根据任务数，依次的去获取任务返回的结果，这里获取结果时会依次返回，若前一个没返回，则会等待，阻塞

            } catch (InterruptedException e) {
                //线程被中断将会进入此处
                log.error("MCMS更新k3的物料库存价格-->多线程编号[{}]--线程被中断异常[{}]",i+1,e);
            } catch (ExecutionException e) {
                //线程执行异常将会进入此处
                log.error("MCMS更新k3的物料库存价格-->多线程编号[{}]--线程执行异常[{}]",i+1,e);
            }catch (TimeoutException e){
                // 获取结果超时将会进入此处
                log.error("MCMS更新k3的物料库存价格-->多线程编号[{}]--获取结果超时异常[{}]",i+1,e);
            }

            if (ObjectUtil.isEmpty(resutlStr)) {
                continue;
            }
            List<K3StockReceiveSendDetail> pageDetailList = this.resultAnalysis(resutlStr);
            if (ObjectUtil.isNotEmpty(pageDetailList)) {
                detailList.addAll(pageDetailList);
            }
        }
        taskExecutor.shutdown();
        log.info("MCMS更新k3的物料库存价格-->已经从K3获取价格数据[{}]",detailList.size());
        return detailList;
    }

    @DataSource(name = "external")
    @Override
    public PageResult<MaterialPriceInfoResult> findPageBySpec(MaterialPriceInfoParam param) {
        Page pageContext = getPageContext();
        IPage<MaterialPriceInfoResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);


    }

    @DataSource(name = "external")
    @Override
    public ResponseData updateTemporaryPrice(MaterialPriceInfoParam param) {
        if (ObjectUtil.isEmpty(param.getMaterialCode()) &&  ObjectUtil.isEmpty(param.getId())) {
            return ResponseData.success("ID或者物料编码必传一个");
        }

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseData.error("请登录后再设置物料的临时价格");
        }
        LambdaUpdateWrapper<MaterialPriceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ObjectUtil.isNotEmpty(param.getMaterialCode()),MaterialPriceInfo::getMaterialCode, param.getMaterialCode());
        updateWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),MaterialPriceInfo::getId, param.getId());
        updateWrapper.set(MaterialPriceInfo::getTemporaryPrice, param.getTemporaryPrice());
        updateWrapper.set(MaterialPriceInfo::getUpdatedTime, new Date());
        updateWrapper.set(MaterialPriceInfo::getUpdatedBy,currentUser.getName() );
        updateWrapper.set(MaterialPriceInfo::getNoPrice,0 );

        if (this.update(updateWrapper)) {
            return ResponseData.success("设置物料临时价格成功");
        }
        return ResponseData.error("设置物料临时价格失败");
    }

    /**
     * 正确请求得到了数据的json解析
     *
     * @param resultJsonStr
     * @return
     */
    private List<K3StockReceiveSendDetail> resultAnalysis(String resultJsonStr) {

        JSONObject resultJson = JSON.parseObject(resultJsonStr);

        if (ObjectUtil.isNotNull(resultJson)) {

            JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));

            if (ObjectUtil.isNotNull(resultValue)) {

                //异常或者失败的情况下  会有ResponseStatus
                JSONObject responseStatus = resultValue.getJSONObject("ResponseStatus");
                if (ObjectUtil.isNotNull(responseStatus) && false == responseStatus.getBoolean("IsSuccess")) {
                    return null;
                }

                //没有出现异常
                String isSuccess = resultValue.getString("IsSuccess");
                Integer rowCount = resultValue.getInteger("RowCount");
                JSONArray rows = resultValue.getJSONArray("Rows");
                if (rowCount > 0 && rows.size() > 0) {
                    List<K3StockReceiveSendDetail> detailList = new ArrayList<>();
                    for (int i = 0; i < rows.size(); i++) {
                        JSONArray detail = (JSONArray) rows.get(i);
                        //过滤出 期末结存价
                        if ("期末结存".equals(String.valueOf(detail.get(4)))
                                && ObjectUtil.isNotEmpty(String.valueOf(detail.get(3)))) {
                            K3StockReceiveSendDetail receiveSendDetail = new K3StockReceiveSendDetail();
                            // FPERIOD,FMATERIALID,FMATERIALNAME,FENDPrice,FBUSINESSTYPE  请求参数FieldKeys 传递顺序返回
                            receiveSendDetail.setFPERIOD(String.valueOf(detail.get(0)));
                            receiveSendDetail.setFMATERIALID(String.valueOf(detail.get(1)));
                            receiveSendDetail.setFMATERIALNAME(String.valueOf(detail.get(2)));
                            receiveSendDetail.setFENDPrice(String.valueOf(detail.get(3)));
                            receiveSendDetail.setFBUSINESSTYPE(String.valueOf(detail.get(4)));
                            detailList.add(receiveSendDetail);
                        }
                    }
                    if (ObjectUtil.isNotEmpty(detailList)) {
                        return detailList;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 统一请求K3获取指定期限的物料库存价格
     *
     * @param needUpdateDate
     * @param startRow
     * @param pageSize
     * @return
     */
    private String requestK3(LocalDate needUpdateDate, int startRow, int pageSize) {

        String jsonData = getJsonParmStr(needUpdateDate, startRow, pageSize);
        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("使用WEB-API查询K3库存成本价-startRow【{}】，pageSize【{}】请求参数【{}】",startRow,pageSize, jsonData);
                String result = api.getSysReportData("HS_NoDimInOutStockDetailRpt", jsonData);
                log.info("使用WEB-API查询K3库存成本价-返回结果[{}]", result);
                return result;
            }
            return null;
        } catch (Exception e) {
            log.info("使用WEB-API查询K3库存成本价-startRow【{}】，pageSize【{}】-异常[{}]",startRow,pageSize, e.getMessage());
        }
        return null;
    }

    private Integer getPageCount(LocalDate needUpdateDate, int startRow, int pageSize) {

        String result = requestK3(needUpdateDate, startRow, 10);
        if (ObjectUtil.isEmpty(result)) {
            return null;
        }

        JSONObject resultJson = JSON.parseObject(result);

        if (ObjectUtil.isNotNull(resultJson)) {

            JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));

            if (ObjectUtil.isNotNull(resultValue)) {

                //异常或者失败的情况下  会有ResponseStatus
                JSONObject responseStatus = resultValue.getJSONObject("ResponseStatus");
                if (ObjectUtil.isNotNull(responseStatus) && false == responseStatus.getBoolean("IsSuccess")) {
                    log.error("使用WEB-API查询K3库存成本价-待更新日期【{}】获取分页总数getPageCount失败[{}]", needUpdateDate, result);
                    return null;
                }
                //没有出现异常
                String isSuccess = resultValue.getString("IsSuccess");
                Integer rowCount = resultValue.getInteger("RowCount");
                JSONArray rows = resultValue.getJSONArray("Rows");
                if (rowCount > 0) {
                    int pageCount = (int) Math.ceil(Double.valueOf(rowCount) / pageSize);
                    log.info("使用WEB-API查询K3库存成本价-待更新日期【{}】获取分页总数getPageCount--成功[{}]", needUpdateDate, pageCount);
                    return pageCount;
                }
            }
        }

        return null;
    }


    public static String getJsonParmStr(LocalDate needUpdateDate, Integer startRow, Integer limit) {
        int year = needUpdateDate.getYear();
        int month = needUpdateDate.getMonthValue();
//        year = 2023;
//        month = 2;
        String jsonStrParm = "{\"FieldKeys\": \"FPERIOD,FMATERIALID,FMATERIALNAME,FENDPrice,FBUSINESSTYPE,FMATERTYPENAME\",\"StartRow\": " + startRow + ",\"Limit\": " + limit + ",\"IsVerifyBaseDataField\": \"false\",\"Model\":{\"FACCTGSYSTEMID\": {\"FNumber\": \"KJHSTX01_SYS\"},\"FACCTGORGID\": {\"FNumber\": \"002\"},\"FACCTPOLICYID\": {\"FNumber\": \"KJZC01_SYS\"},\"FYear\": \"" + year + "\",\"FENDYEAR\": \"" + year + "\",\"FPeriod\": \"" + month + "\",\"FEndPeriod\": \"" + month + "\"}}";
        return jsonStrParm;
    }


    /**
     * 传递马帮采购单的价格 取值规则 （库存价，采购价，临时价）
     *
     * @param materialPriceInfo
     * @return
     */
    private BigDecimal getMabangMatPrice(MaterialPriceInfo materialPriceInfo) {
        int fallbackMonths = 2;

        if (ObjectUtil.isNull(materialPriceInfo)) {
            return null;
        }
        if (ObjectUtil.isNotNull(materialPriceInfo.getStockPrice())) {
            //增加库存价格时间段的获取  获取有效期内的 库存核算价格
            LocalDate fallBackLocalDate = LocalDate.now().minusMonths(fallbackMonths);

            Date stockCheckDate = materialPriceInfo.getStockCheckDate();
            LocalDate stockCheckLocalDate = stockCheckDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            stockCheckLocalDate = stockCheckLocalDate.with(TemporalAdjusters.lastDayOfMonth());

            if (stockCheckLocalDate.compareTo(fallBackLocalDate)>=0) {
                return materialPriceInfo.getStockPrice();
            }
        }

        if (ObjectUtil.isNotNull(materialPriceInfo.getPurchasePrice())) {
            return materialPriceInfo.getPurchasePrice();
        }

        if (ObjectUtil.isNotNull(materialPriceInfo.getTemporaryPrice())) {
            return materialPriceInfo.getTemporaryPrice();
        }
        return null;
    }


    public static String getJsonParmStr2(LocalDate needUpdateDate, Integer startRow, Integer limit) {
        int year = needUpdateDate.getYear();
        int month = needUpdateDate.getMonthValue();

        year = 2023;
        month = 2;

        JSONObject object = new JSONObject();
        object.put("FieldKeys", "FPERIOD,FBILLDATE,FMATERIALID,FMATERIALNAME,FRECEIVEPrice,FSENDPrice,FENDPrice,FBUSINESSTYPE");
        object.put("StartRow", startRow);
        object.put("Limit", limit);
        object.put("IsVerifyBaseDataField", "false");

        JSONObject model = new JSONObject();

        //核算体系编码
        JSONObject facctgsystemid = new JSONObject();
        facctgsystemid.put("FNumber", "KJHSTX01_SYS");
        model.put("FACCTGSYSTEMID", facctgsystemid);


        //核算组织编码
        JSONObject facctgorgid = new JSONObject();
        facctgorgid.put("FNumber", "002");
        model.put("FACCTGORGID", facctgorgid);


        //会计政策编码
        JSONObject facctpolicyid = new JSONObject();
        facctpolicyid.put("FNumber", "KJZC01_SYS");
        model.put("FACCTPOLICYID", facctpolicyid);


        object.put("FYear", String.valueOf(year));
        object.put("FENDYEAR", String.valueOf(year));
        object.put("FPeriod", String.valueOf(month));
        object.put("FEndPeriod", String.valueOf(month));

//        JSONArray fstartmaterialid = new JSONArray();
//        facctgsystemid.add(new JSONObject().put("FNumber","P00LK001") );
//        model.add(new JSONObject().put("FSTARTMATERIALID"), fstartmaterialid);
//
//        JSONArray fendmaterialid = new JSONArray();
//        facctgsystemid.add(new JSONObject().put("FNumber","P00LK001") );
//        model.add(new JSONObject().put("FENDMATERIALID"), fendmaterialid);

        object.put("Model", model);

        String jsonStrParm = object.toJSONString();
        return jsonStrParm;
    }

    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb = new IdentifyInfo() {
        };
        //应用id
        identifyInfoAdb.setAppId(k3CloudWebapiConfig.getAppid());

        identifyInfoAdb.setAppSecret(k3CloudWebapiConfig.getAppsec());
        //账套id
        identifyInfoAdb.setdCID(k3CloudWebapiConfig.getAcctid());

        identifyInfoAdb.setUserName(k3CloudWebapiConfig.getUsername());

        identifyInfoAdb.setServerUrl(k3CloudWebapiConfig.getServerurl());
        return identifyInfoAdb;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
