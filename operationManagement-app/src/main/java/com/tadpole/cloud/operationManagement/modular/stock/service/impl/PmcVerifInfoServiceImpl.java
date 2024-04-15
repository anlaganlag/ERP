package com.tadpole.cloud.operationManagement.modular.stock.service.impl;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.*;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.PmcVerifInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyItem;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.K3PurchaseOrderApplyParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcAutoAnalyzeParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcAutoAnalyzeResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcVerifInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SupplierInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPmcVerifInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockPmcVerifK3Service;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 审核记录信息 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-05
 */
@Service
public class PmcVerifInfoServiceImpl extends ServiceImpl<PmcVerifInfoMapper, PmcVerifInfo> implements IPmcVerifInfoService {

    @Resource
    private PmcVerifInfoMapper mapper;


    @Autowired
    K3GeneratorNoUtil k3GeneratorNoUtil;

    @Autowired
    IPurchaseOrdersService purchaseOrdersService;

    @Autowired
    ITeamVerifService teamVerifService;

    @Autowired
    SyncToErpConsumer syncToErpConsumer;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IStockPmcVerifK3Service stockPmcVerifK3Service;

    @Autowired
    IPmcVerifInfoService pmcVerifInfoService;

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateBatch(List<PmcVerifInfoParam> list, HashMap<String, String> supplierMap) {
        List<PmcVerifInfo> infos = new ArrayList<>();

        for (PmcVerifInfoParam model : list) {
            PmcVerifInfo obj = new PmcVerifInfo();
            BeanUtils.copyProperties(model, obj, "billType");

            LambdaQueryWrapper<PmcVerifInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PmcVerifInfo::getPurchaseOrderId, obj.getPurchaseOrderId())
                    .eq(PmcVerifInfo::getVerifBizType, 30);

            List<PmcVerifInfo> extsisList = this.baseMapper.selectList((wrapper));
            if (extsisList != null) {
                //TODO：写法【集合对象属性是否包含某一个值】
                if (extsisList.stream().map(PmcVerifInfo::getPurchaseOrderId).collect(Collectors.toList()).contains(obj.getPurchaseOrderId())) {
                    //TODO：写法【集合中获取符合条件的对象】
                    model.setId(extsisList.stream().filter(f -> f.getPurchaseOrderId().equals(model.getPurchaseOrderId())).findAny().orElse(null).getId());
                    PmcVerifInfo uModel = extsisList.stream().filter(f -> f.getPurchaseOrderId().equals(obj.getPurchaseOrderId())).findAny().orElse(null);
                    if (uModel != null) {
                        obj.setId(uModel.getId());
                    }
                    obj.setUpdateTime(new Date());
                } else {
                    obj.setCreateTime(new Date());
                }
            }
            obj.setVerifBizType(30);
            obj.setVerifType("自动");
            obj.setVerifDate(new Date());
            obj.setVerifPersonNo(this.getUserAccount());
            obj.setVerifPersonName(this.getUserName());

            if (ObjectUtil.isNotEmpty(supplierMap.get(obj.getAdviceSupplier()))) {
                String supplierId = supplierMap.get(obj.getAdviceSupplier());
                obj.setAdviceSupplierId(supplierId);
            }
            //款式二级标签
            if (ObjectUtil.isNotEmpty(model.getStyleSecondLabel())) {
                obj.setMatstylesecondlabel(model.getStyleSecondLabel());
            }
            infos.add(obj);
        }
        return this.saveOrUpdateBatch(infos, 1000);
    }

    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class)
    public HashMap<String, String> getAllSupplier() {
        Object supplierObject = redisTemplate.opsForValue().get(StockConstant.SUPPLIER_KEY);

        if (ObjectUtil.isNotNull(supplierObject)) {
            return (HashMap<String, String>) supplierObject;
        }
        List<Map<String, String>> allSupplier = mapper.getAllSupplier();
        HashMap<String, String> supplierMap = new HashMap<>();
        if (ObjectUtil.isNotEmpty(allSupplier)) {
            for (Map<String, String> map : allSupplier) {
                supplierMap.put(map.get("adviceSupplier"), map.get("adviceSupplierId"));
            }
        }

        redisTemplate.delete(StockConstant.SUPPLIER_KEY);
        redisTemplate.boundValueOps(StockConstant.SUPPLIER_KEY).set(supplierMap, 1, TimeUnit.HOURS);
        return supplierMap;
    }

    @Override
    public ResponseData getSupplierByName(PmcVerifInfoParam param, HashMap<String, String> allSupplier) {

        String supplierName = param.getAdviceSupplier();

        if (ObjectUtil.isEmpty(supplierName)) {
            return ResponseData.success(allSupplier);
        }
        List<String> likeName = allSupplier.keySet().stream().filter(s -> s.contains(supplierName)).collect(Collectors.toList());

        List<HashMap<String, String>> resultListMap = new ArrayList<>();

        for (String name : likeName) {
            HashMap<String, String> map = new HashMap<>();
            map.put(name, allSupplier.get(name));
            resultListMap.add(map);
        }

        return ResponseData.success(resultListMap);
    }

    @Override
    @DataSource(name = "stocking")
    public List<PmcVerifInfoResult> queryList(PmcVerifInfoParam param) {
        List<PmcVerifInfoResult> resultList = this.baseMapper.queryList(param);
        return resultList;
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData autoAnalyze(PmcAutoAnalyzeParam param) {
        List<PmcAutoAnalyzeResult> resultList = baseMapper.autoAnalyze(param);

        if (ObjectUtil.isNotEmpty(resultList)) {
            for (PmcAutoAnalyzeResult analyzeResult : resultList) {
                String billNo = analyzeResult.getBillNo();
                analyzeResult.setIncludeTax(1);
                if (billNo.contains("T") && billNo.lastIndexOf("T") == (billNo.length()-1)) {
                    analyzeResult.setIncludeTax(0);
                }
                analyzeResult.setCreateOrderType("常规");//自动分析默认值
            }
        }
        return ResponseData.success(resultList);
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData autoAnalyzeByMat(PmcAutoAnalyzeParam param) {
        List<PmcAutoAnalyzeResult> resultList = baseMapper.autoAnalyzeByMat(param);

        if (ObjectUtil.isNotEmpty(resultList)) {
            for (PmcAutoAnalyzeResult analyzeResult : resultList) {
                String billNo = analyzeResult.getBillNo();
                analyzeResult.setIncludeTax(1);
                if (billNo.contains("T") && billNo.lastIndexOf("T") == (billNo.length()-1)) {
                    analyzeResult.setIncludeTax(0);
                }
                analyzeResult.setIncludeTaxName( analyzeResult.getIncludeTax()==1 ? "HS" : "BHS");
            }
        }
        return ResponseData.success(resultList);
    }



    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchMergeAction(List<PmcVerifInfoParam> parmList, String verifyResult) {

        //查出所有待审核的数据，按照合并字段分组
        List<PmcVerifInfoResult> queryListResult = this.queryList(null);
        Map<String, List<PmcVerifInfoResult>> groupResult = queryListResult.stream().collect(Collectors.groupingBy(PmcVerifInfoResult::getMergeGroupField));

        //1，检查页面查询完数据后，后台又有新的数据插入，导致页面看到的数据不是最新汇总的，需要提示
        List<PmcVerifInfoParam> exceptionDataList = parmList.stream().filter(p -> {
            if (groupResult.containsKey(p.getMergeGroupField())) {
                List<PmcVerifInfoResult> queryList = groupResult.get(p.getMergeGroupField());
                if (!p.getMergeCount().equals(queryList.get(0).getMergeCount())) {//合并数量不一致需要刷新再提交
                    return true;
                }
            } else {//数据已经提交了，不存在了，不需要处理
                return false;
            }
            return false;
        }).collect(Collectors.toList());

        if (ObjectUtil.isNotEmpty(exceptionDataList)) {
            return ResponseData.error(500, "页面显示合并数量和系统后台现有数量不一致，需要刷新再提交", exceptionDataList);
        }


        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();


        Map<String, List<PmcVerifInfoParam>> groupParmMap = parmList.stream().collect(Collectors.groupingBy(pmcVerifInfoParam -> pmcVerifInfoParam.getTeam()+pmcVerifInfoParam.getMaterialCode()));

        //从数据库查询结果中，过滤出本次需要提交的数据
        LambdaQueryWrapper<PmcVerifInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PmcVerifInfo::getVerifResult, StockConstant.VERIFY_WAIT);
        List<PmcVerifInfo> pmcVerifInfoList = pmcVerifInfoService.getBaseMapper().selectList(wrapper);


        Map<String, List<PmcVerifInfo>> needUpdatePmcVerif = pmcVerifInfoList.stream().filter(q -> groupParmMap.containsKey(q.getTeam() + q.getMaterialCode()))
                .collect(Collectors.groupingBy(pmc -> pmc.getTeam() + pmc.getMaterialCode()));
        boolean needCallK3 = false;
        if (StockConstant.VERIFY_SUCESS.equals(verifyResult)) {
            needCallK3 = true;
        }

        this.verifyDoing(verifyResult, currentUser, groupParmMap, needUpdatePmcVerif);
        return ResponseData.success();
    }

    /**
     *
     * @param verifyResult  审核结果 1通过 2 不通过
     * @param currentUser  操作人
     * @param groupParmMap  提交参数 分组集合
     * @param needUpdatePmcVerif  数据库待更新的pmc审核信息 分组集合
     */
    private void verifyDoing(String verifyResult, LoginUser currentUser, Map<String, List<PmcVerifInfoParam>> groupParmMap,
                             Map<String, List<PmcVerifInfo>> needUpdatePmcVerif) {

        for (Map.Entry<String, List<PmcVerifInfo>> entry : needUpdatePmcVerif.entrySet()) {
            String mergeGroupField = entry.getKey();
            List<PmcVerifInfo> resultList = entry.getValue();
            PmcVerifInfoParam pmcParm = groupParmMap.get(mergeGroupField).get(0);
            String billNo = k3GeneratorNoUtil.getNoByKey(StockConstant.PURCHASE_ORDER_PREFIX, StockConstant.SYNC_K3_PURCHASE_ORDER_KEY, 6);


            for (PmcVerifInfo info : resultList) {
                this.updatePmcVerifInfo(verifyResult, currentUser, pmcParm, info,billNo);
            }

            if (StockConstant.VERIFY_SUCESS.equals(verifyResult)) {
//                needCallK3 = true;
                StockPmcVerifK3 stockPmcVerifK3 = stockPmcVerifK3Service.callK3(resultList,true);
            } else {

                this.updateBatchById(resultList);

                List<String> orderIdList = resultList.stream().map(p -> p.getPurchaseOrderId()).collect(Collectors.toList());

                LambdaUpdateWrapper<PurchaseOrders> purchaseOrderWrapper = new LambdaUpdateWrapper<>();
                purchaseOrderWrapper.set(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PMC_NO)
                        .set(PurchaseOrders::getUpdateTime, new Date())
                        .in(PurchaseOrders::getId, orderIdList);
                purchaseOrdersService.update(purchaseOrderWrapper);
            }
        }
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData mergeDetails(PmcVerifInfoParam parm) {

        if (ObjectUtil.isNull(parm)
                || ObjectUtil.isNull(parm.getDepartment())
                || ObjectUtil.isNull(parm.getTeam())
                || ObjectUtil.isNull(parm.getMaterialCode())) {
            return ResponseData.error("传入参数（Department，Team，MaterialCode）不能为空 ");
        }

        List<PmcVerifInfoResult> resultList = mapper.mergeDetails(parm);

        return ResponseData.success(resultList);

    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData detailsComit(List<PmcVerifInfoParam> parmList) {

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        //通过的数据
        List<PmcVerifInfoParam> passData = parmList.stream()
                .filter(p -> StockConstant.VERIFY_SUCESS.equals(p.getVerifResult())).collect(Collectors.toList());
        //不通过的数据
        List<PmcVerifInfoParam> rejectData = parmList.stream()
                .filter(p -> StockConstant.VERIFY_FAIL.equals(p.getVerifResult())).collect(Collectors.toList());


        //处理通过的数据
        if (ObjectUtil.isNotEmpty(passData)) {
            //校验必填字段
            List<PmcVerifInfoParam> exceptionData = passData.stream().filter(pd -> ObjectUtil.isNull(pd.getAdviceSupplier())
                    || ObjectUtil.isNull(pd.getAdviceSupplierId())
                    || ObjectUtil.isNull(pd.getPurchasePerson())
                    || ObjectUtil.isNull(pd.getPurchasePersonId())
                    || ObjectUtil.isNull(pd.getIncludeTax())
                    || ObjectUtil.isNull(pd.getCreateOrderType())
            ).collect(Collectors.toList());

            if (ObjectUtil.isNotEmpty(exceptionData)) {
                return ResponseData.error("必填字段【建议供应商,建议供应商Id,是否含税,采购员,采购员id,下单方式】参数缺少");
            }
            this.batchDetailComit(StockConstant.VERIFY_SUCESS,passData, currentUser);
        }

        //处理不通过的数据
        if (ObjectUtil.isNotEmpty(rejectData)) {

           this.batchDetailComit(StockConstant.VERIFY_FAIL, rejectData, currentUser);
        }

        return  ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData planApprovedOrder(PmcVerifInfoParam param) {

        if (ObjectUtil.isEmpty(param.getDepartment()) || ObjectUtil.isEmpty(param.getTeam()) ||ObjectUtil.isEmpty(param.getMaterialCode())) {
            return ResponseData.error("部门，team，物料编码，都不能为空，请求检查请求参数");
        }
        List<PmcVerifInfoResult> pmcVerifInfoList = mapper.planApprovedOrder(param);
        return ResponseData.success(pmcVerifInfoList);
    }

    @Override
    @DataSource(name = "stocking")
    public String getMatModeSpec(String materialCode) {
        return mapper.getMatModeSpec(materialCode);
    }

    /**
     *
     * @param verifyResult  审批结果 1通过，2不通过
     * @param data
     * @param currentUser
     */
    private void batchDetailComit(String verifyResult, List<PmcVerifInfoParam> data, LoginUser currentUser) {
        Map<String, List<PmcVerifInfoParam>> parmGroupData = data.stream()
                .collect(Collectors.groupingBy(pd -> pd.getTeam() + pd.getMaterialCode()));

        //从数据库查询结果中，过滤出本次需要提交的数据
        LambdaQueryWrapper<PmcVerifInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PmcVerifInfo::getId, data.stream().map(pd->pd.getId()).collect(Collectors.toList()));
        wrapper.eq(PmcVerifInfo::getVerifResult,StockConstant.VERIFY_WAIT);
        List<PmcVerifInfo> pmcVerifInfoList = pmcVerifInfoService.getBaseMapper().selectList(wrapper);

        Map<String, List<PmcVerifInfo>> needUpdatePmcVerif = pmcVerifInfoList.stream().collect(Collectors.groupingBy(pmc -> pmc.getTeam() + pmc.getMaterialCode()));
        this.verifyDoing(verifyResult, currentUser, parmGroupData, needUpdatePmcVerif);
    }

    /**
     * @param verifyResult 审核结果
     * @param currentUser  登录人信息
     * @param pmcParm      入参
     * @param info         需要更新的审核信息
     */
    private void updatePmcVerifInfo(String verifyResult, LoginUser currentUser, PmcVerifInfoParam pmcParm, PmcVerifInfo info,String billNo) {
        info.setAdviceSupplier(pmcParm.getAdviceSupplier());
        info.setAdviceSupplierId(pmcParm.getAdviceSupplierId());
        info.setPurchasePerson(pmcParm.getPurchasePerson());
        info.setPurchasePersonId(pmcParm.getPurchasePersonId());
        info.setOrderLastTime(pmcParm.getOrderLastTime());
        info.setVerifResult(pmcParm.getVerifResult());
        info.setIncludeTax(pmcParm.getIncludeTax());
        info.setCreateOrderType(pmcParm.getCreateOrderType());
        info.setOperExpectedDate(pmcParm.getOperExpectedDate());//todo:需要确定是否更改期望交期
        info.setVerifReason(pmcParm.getVerifReason());

        info.setVerifType("人工");
        info.setVerifPersonNo(currentUser.getAccount());
        info.setVerifPersonName(currentUser.getName());
        info.setVerifDate(new Date());

        info.setUpdateTime(new Date());
        info.setMatControlPerson(pmcParm.getMatControlPerson());


        if (StockConstant.VERIFY_SUCESS.equals(verifyResult)) {
//                    order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_YES); //PMC通过
            info.setVerifResult(StockConstant.VERIFY_SUCESS);
            info.setBillNo(billNo);
        } else if (StockConstant.VERIFY_FAIL.equals(verifyResult)) {
//                    order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_NO); //PMC未通过
            info.setVerifResult(StockConstant.VERIFY_FAIL);
        }
    }


    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class)
    public String getSupplier(String supplier) {

        Object supplierObject = redisTemplate.opsForValue().get(StockConstant.SUPPLIER_KEY);

        HashMap<String, String> supplierMap = null;

        if (ObjectUtil.isNotNull(supplierObject)) {

            supplierMap = (HashMap) supplierObject;

            if (ObjectUtil.isNotNull(supplierMap.get(supplier))) {  //key :
                return supplierMap.get(supplier);
            }
        }

        //把所有的供应商取出来  存到redis
        List<Map<String, String>> allSupplier = mapper.getAllSupplier();

        if (ObjectUtil.isNotEmpty(allSupplier)) {
            for (Map<String, String> map : allSupplier) {
                supplierMap.put(map.get("sujustSupplier"), map.get("supplierCode"));
            }
        }

        redisTemplate.delete(StockConstant.SUPPLIER_KEY);

        redisTemplate.boundValueOps(StockConstant.SUPPLIER_KEY)
                .set(supplierMap, 7, TimeUnit.DAYS);


        if (ObjectUtil.isNotEmpty(supplierMap.get(supplier))) {
            return supplierMap.get(supplier);
        }
        return null;

    }

    @Override
    @DataSource(name = "stocking")
    public void exportExcel(HttpServletResponse response, PmcVerifInfoParam info) {
        LambdaQueryWrapper<PmcVerifInfo> queryWrapper = new LambdaQueryWrapper<>();

        //采购订单状态:值域{"0:待审核"/"1:不备货"/"2:待计划部审批"/"3:计划未通过"/"4:待PMC审批"/"5:PMC未通过"/"6:已通过"}默认值：待审核
        if (ObjectUtil.isNotEmpty(info.getVerifResult())) {
            queryWrapper.eq(PmcVerifInfo::getVerifResult, info.getVerifResult());
        }
        //备货类型:正常备货：0，特殊紧急备货：1
        if (ObjectUtil.isNotEmpty(info.getBillType())) {
            queryWrapper.eq(PmcVerifInfo::getBillType, info.getBillType());
        }
        //物料编码
        if (ObjectUtil.isNotEmpty(info.getMaterialCode())) {
            queryWrapper.eq(PmcVerifInfo::getMaterialCode, info.getMaterialCode());
        }
        //款式二级标签
        if (ObjectUtil.isNotEmpty(info.getStyleSecondLabel())) {
            queryWrapper.eq(PmcVerifInfo::getMatstylesecondlabel, info.getStyleSecondLabel());
        }
        List<PmcVerifInfo> infos = this.baseMapper.selectList(queryWrapper);
        List<PmcVerifInfoResult> results = new ArrayList<>();
        for (PmcVerifInfo model : infos) {
            PmcVerifInfoResult obj = new PmcVerifInfoResult();
            BeanUtils.copyProperties(model, obj);

            switch (obj.getBillType()) {
                case "RCBH":
                    obj.setBillType("日常备货");
                    break;
                case "JJBH":
                    obj.setBillType("紧急备货");
                    break;
                case "XMBH":
                    obj.setBillType("项目备货");
                    break;
                case "XPBH":
                    obj.setBillType("新品备货");
                    break;
                default:
                    break;
            }
            results.add(obj);
        }
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("PMC审批.xlsx".getBytes("utf-8"), "ISO8859-1"));
            EasyExcel.write(response.getOutputStream(), PmcVerifInfoResult.class).sheet("PMC审批").doWrite(results);
            //            ExcelUtils.exportExcel(results, "PMC审批导出数据", "sheet1", PmcVerifInfoResult.class, "PMC审批导出数据New", response);
        } catch (IOException ex) {
            log.error("PMC审批导出数据异常", ex);
        }
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData createPmcVerifyInfo(VerifInfoRecord verifInfoRecord, PurchaseOrders order) {

        String billNo = k3GeneratorNoUtil.getNoByKey(StockConstant.PURCHASE_ORDER_PREFIX, StockConstant.SYNC_K3_PURCHASE_ORDER_KEY, 6);

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, order.getId());
        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);


        PmcVerifInfo pmcVerifInfo = new PmcVerifInfo();
        if (ObjectUtil.isNotEmpty(teamVerifList)) {
            pmcVerifInfo.setApplyPerson(teamVerifList.get(0).getVerifPersonName());
            pmcVerifInfo.setApplyPersonId(teamVerifList.get(0).getVerifPersonNo());
        }


        LambdaQueryWrapper<PmcVerifInfo> tWrapper = new LambdaQueryWrapper<>();
        tWrapper.eq(PmcVerifInfo::getMaterialCode, order.getMaterialCode());
        tWrapper.eq(PmcVerifInfo::getVerifResult, String.valueOf(StockConstant.VERIFY_SUCESS));
        tWrapper.orderByDesc(PmcVerifInfo::getCreateTime);
        List<PmcVerifInfo> pmcVerifInfoList = this.baseMapper.selectList(tWrapper);
        if (ObjectUtil.isNotEmpty(pmcVerifInfoList)) {
            pmcVerifInfo.setOrderLastTime(pmcVerifInfoList.get(0).getCreateTime());
        }

        pmcVerifInfo.setId(IdWorker.getIdStr());
        pmcVerifInfo.setPlatform(order.getPlatform());
        pmcVerifInfo.setPurchaseOrderId(order.getId());
        pmcVerifInfo.setQty(verifInfoRecord.getQty());
        pmcVerifInfo.setVerifBizType(StockConstant.VERIF_BIZ_TYPE_PMC);
        //        pmcVerifInfo.setVerifType("人工");
        //        pmcVerifInfo.setBillNo(billNo);

        pmcVerifInfo.setApplyDate(new Date());
        pmcVerifInfo.setBillType(order.getBillType());
        pmcVerifInfo.setMaterialCode(order.getMaterialCode());//
        pmcVerifInfo.setProductType(order.getProductType());//
        pmcVerifInfo.setModel(order.getModel());//
        pmcVerifInfo.setDepartment(order.getDepartment());//
        pmcVerifInfo.setTeam(order.getTeam());//
        pmcVerifInfo.setMinpoqty(order.getMinpoqty());
        pmcVerifInfo.setMinpoqtyNotes(order.getMinpoqtyNotes());
        pmcVerifInfo.setSpellOrdernum(order.getSpellOrdernum());
        pmcVerifInfo.setSpellOrdernumRemark(order.getSpellOrdernumRemark());
        pmcVerifInfo.setMatstylesecondlabel(order.getMatstylesecondlabel());
        pmcVerifInfo.setOperExpectedDate(order.getOperExpectedDate());
        pmcVerifInfo.setVerifResult(StockConstant.VERIFY_WAIT);
        pmcVerifInfo.setSyncStatus(StockConstant.SYNC_WAIT);
        pmcVerifInfo.setRemark(verifInfoRecord.getVerifReason());
        pmcVerifInfo.setCreateTime(new Date());

        if (ObjectUtil.isNotEmpty(order.getBrand())) {
            pmcVerifInfo.setProductName(order.getCompanyBrand() + " " + order.getProductName());
        }

        //物料属性合集
        StringBuffer buffer = new StringBuffer();
        buffer.append(StringUtils.isEmpty(order.getBrand()) ? "" : order.getBrand() + " ")
                .append(StringUtils.isEmpty(order.getModel()) ? "" : order.getModel() + " ")
                .append(StringUtils.isEmpty(order.getStyle()) ? "" : order.getStyle() + " ")
                .append(StringUtils.isEmpty(order.getPattern()) ? "" : order.getPattern() + " ")
                .append(StringUtils.isEmpty(order.getColor()) ? "" : order.getColor() + " ")
                .append(StringUtils.isEmpty(order.getMainMaterial()) ? "" : order.getMainMaterial() + " ")
                .append(StringUtils.isEmpty(order.getSizes()) ? "" : order.getSizes() + " ")
                .append(StringUtils.isEmpty(order.getPacking()) ? "" : order.getPacking() + " ")
                .append(StringUtils.isEmpty(order.getVersion()) ? "" : order.getVersion() + " ")
                .append(StringUtils.isEmpty(order.getVersionDes()) ? "" : order.getVersionDes());
        pmcVerifInfo.setMaterialProperties(buffer.toString().trim());

        //获取物料属性集合--从product库的物料表PROD_MATERIEL 查找MAT_MODE_SPEC属性
        String matModeSpec=  pmcVerifInfoService.getMatModeSpec(order.getMaterialCode());
        pmcVerifInfo.setMatModeSpec(matModeSpec);


        if (this.save(pmcVerifInfo)) {
            return ResponseData.success();
        }
        return ResponseData.error("创建pmc审核信息失败");
    }


    /**
     * pmc 审核
     *
     * @param param
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pmcVerif(PmcVerifInfoParam param) {

        PmcVerifInfo pmcVerifInfo = this.getBaseMapper().selectById(param.getId());
        PurchaseOrders order = purchaseOrdersService.getById(param.getPurchaseOrderId());

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        String result = param.getVerifResult();

        boolean needCallK3 = false;
        pmcVerifInfo.setVerifType("人工");
        pmcVerifInfo.setVerifPersonNo(currentUser.getAccount());
        pmcVerifInfo.setVerifPersonName(currentUser.getName());
        pmcVerifInfo.setVerifDate(new Date());
        pmcVerifInfo.setVerifReason(param.getVerifReason());
        pmcVerifInfo.setVerifReason(param.getVerifReason());

        if (StockConstant.VERIFY_SUCESS.equals(result)) {
            order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_YES); //PMC通过
            pmcVerifInfo.setVerifResult(StockConstant.VERIFY_SUCESS);
            needCallK3 = true;
        } else if (StockConstant.VERIFY_FAIL.equals(result)) {
            order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_NO); //PMC未通过
            pmcVerifInfo.setVerifResult(StockConstant.VERIFY_FAIL);
        }

        BigDecimal applyQty = param.getQty();

        if (applyQty.compareTo(BigDecimal.ZERO) <= 0) {
            pmcVerifInfo.setQty(applyQty);
        }

        pmcVerifInfo.setUpdateTime(new Date());


        if (this.updateById(pmcVerifInfo) && purchaseOrdersService.updateById(order)) {
            if (!needCallK3) {
                return ResponseData.success();
            }
            if (callK3(pmcVerifInfo)) {
                order.setOrderLastTime(new Date());
                purchaseOrdersService.updateById(order);
                return ResponseData.success();
            } else {
                order.setOrderStatus(StockConstant.ORDER_STATUS_PMC_YES_SYNC_FAIL);
                purchaseOrdersService.updateById(order);
                return ResponseData.error(500, "同步k3采购单失败", pmcVerifInfo.getBillNo());
            }
        }
        return ResponseData.error(500, "更新审核信息失败", pmcVerifInfo.getBillNo());
    }


    /**
     * 同步采购订单到k3
     *
     * @param pmcVerifInfo
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean callK3(PmcVerifInfo pmcVerifInfo) {

        boolean syncResult = false;

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamVerif::getPurchaseApplyNo, pmcVerifInfo.getPurchaseOrderId());
        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);

        ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();
        if (ObjectUtil.isEmpty(teamVerifList)) {
            return syncResult;
        }

        //item只有一项
        TeamVerif teamVerif = teamVerifList.get(0);
        K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
        applyItem.setFPaezBase(teamVerif.getTeam());
        applyItem.setFPaezBase2(teamVerif.getVerifPersonNo());
        applyItem.setFMaterialId(teamVerif.getMaterialCode());
        applyItem.setFReqQty(pmcVerifInfo.getQty().intValue());
        applyItem.setFPurchaserId(pmcVerifInfo.getPurchasePersonId());
        applyItem.setFSuggestSupplierId(pmcVerifInfo.getAdviceSupplierId());
        applyItem.setFEntryNote(pmcVerifInfo.getVerifReason());
        applyItem.setFBscHdate(pmcVerifInfo.getOperExpectedDate());
        itemList.add(applyItem);


        //单头
        K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
        orderApplyParam.setFCreateDate(new Date());
        orderApplyParam.setFBillNo(pmcVerifInfo.getBillNo());
        orderApplyParam.setFApplicationDate(pmcVerifInfo.getCreateTime());
        orderApplyParam.setFCreatorId(currentUser.getAccount());
        orderApplyParam.setFApplicantId(currentUser.getAccount());
        orderApplyParam.setFNote(pmcVerifInfo.getRemark());
        orderApplyParam.setFEntity(itemList);

        String jsonString = JSON.toJSONString(orderApplyParam);
        pmcVerifInfo.setSyncRequestMsg(jsonString);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(orderApplyParam);

        try {
            JSONObject jsonObject = syncToErpConsumer.syncPurschaseObj(jsonArray);

            if (ObjectUtil.isNotNull(jsonObject)) {
                pmcVerifInfo.setSyncResultMsg(JSON.toJSONString(jsonObject.toJSONString()));

                String code1 = jsonObject.getString("Code");
                if (ObjectUtil.isNotEmpty(code1) && code1.equals("0")) {
                    //同步成功修改状态为1
                    pmcVerifInfo.setSyncStatus(StockConstant.SYNC_SUCESS);
                    syncResult = true;
                } else {
                    pmcVerifInfo.setSyncStatus(StockConstant.SYNC_FAIL);
                }
            }
        } catch (Exception e) {
            log.error("备货2.0调用k3采购订单接口异常:{}", e);
            pmcVerifInfo.setSyncResultMsg(JSON.toJSONString(e));
        }

        pmcVerifInfo.setSyncTime(new Date());
        pmcVerifInfo.setUpdateTime(new Date());

        this.updateById(pmcVerifInfo);

        if (!syncResult) { //同步失败 将订单状态置位7

        }

        return syncResult;
    }

    /**
     * 同步采购订单到k3
     *
     * @param pmcVerifInfoList
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCallK3(List<PmcVerifInfo> pmcVerifInfoList) {

        List<String> successVerifIds = new ArrayList<>();
        List<String> successOrderIds = new ArrayList<>();
        List<String> failVerifIds = new ArrayList<>();
        List<String> failOrderIds = new ArrayList<>();
        List<String> idList = pmcVerifInfoList.stream().map(pmc -> pmc.getId()).collect(Collectors.toList());

        boolean syncResult = false;

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        Map<String, List<PmcVerifInfo>> pmcMap = pmcVerifInfoList.stream().collect(Collectors.groupingBy(PmcVerifInfo::getPurchaseOrderId));
        List<String> orderIdList = pmcMap.keySet().stream().collect(Collectors.toList());

        LambdaQueryWrapper<TeamVerif> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TeamVerif::getPurchaseApplyNo, orderIdList);
        List<TeamVerif> teamVerifList = teamVerifService.list(wrapper);

        if (ObjectUtil.isEmpty(teamVerifList)) {
            return syncResult;
        }
        Map<String, List<TeamVerif>> teamMap = teamVerifList.stream().collect(Collectors.groupingBy(TeamVerif::getPurchaseApplyNo));

        JSONArray jsonArray = new JSONArray();

        for (Map.Entry<String, List<TeamVerif>> entry : teamMap.entrySet()) {

            List<TeamVerif> teamVerifs = entry.getValue();
            PmcVerifInfo pmcVerifInfo = pmcMap.get(entry.getKey()).get(0);

            ArrayList<K3PurchaseOrderApplyItem> itemList = new ArrayList<>();

            //item只有一项
            TeamVerif teamVerif = teamVerifs.get(0);
            K3PurchaseOrderApplyItem applyItem = new K3PurchaseOrderApplyItem();
            applyItem.setFPaezBase(teamVerif.getTeam());
            applyItem.setFPaezBase2(teamVerif.getVerifPersonNo());
            applyItem.setFMaterialId(teamVerif.getMaterialCode());
            applyItem.setFReqQty(pmcVerifInfo.getQty().intValue());
            applyItem.setFPurchaserId(pmcVerifInfo.getPurchasePersonId());
            applyItem.setFSuggestSupplierId(pmcVerifInfo.getAdviceSupplierId());
            applyItem.setFEntryNote(pmcVerifInfo.getVerifReason());
            applyItem.setFBscHdate(pmcVerifInfo.getOperExpectedDate());
            itemList.add(applyItem);


            K3PurchaseOrderApplyParam orderApplyParam = new K3PurchaseOrderApplyParam();
            orderApplyParam.setFCreateDate(new Date());
            orderApplyParam.setFBillNo(pmcVerifInfo.getBillNo());
            orderApplyParam.setFApplicationDate(pmcVerifInfo.getCreateTime());
            orderApplyParam.setFCreatorId(currentUser.getAccount());
            orderApplyParam.setFApplicantId(currentUser.getAccount());
            orderApplyParam.setFNote(pmcVerifInfo.getRemark());
            orderApplyParam.setFEntity(itemList);

            String jsonString = JSON.toJSONString(orderApplyParam);
            pmcVerifInfo.setSyncRequestMsg(jsonString);

            jsonArray.add(orderApplyParam);

        }

        LambdaUpdateWrapper<PmcVerifInfo> successUpdateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<PmcVerifInfo> failUpdateWrapper = new LambdaUpdateWrapper<>();

        try {
            JSONObject jsonObject = syncToErpConsumer.syncPurschaseObj(jsonArray);
            if (ObjectUtil.isNotNull(jsonObject)) {

                String code1 = jsonObject.getString("Code");
                if (ObjectUtil.isNotEmpty(code1) && code1.equals("0")) {
                    successUpdateWrapper.set(PmcVerifInfo::getSyncResultMsg, jsonObject.toJSONString());
                    //同步成功修改状态为1
                    syncResult = true;
                    String resultStr = jsonObject.toJSONString();
                    for (PmcVerifInfo info : pmcVerifInfoList) {
                        if (resultStr.contains(info.getBillNo())) {
                            successVerifIds.add(info.getId());
                            successOrderIds.add(info.getPurchaseOrderId());
                        } else {
                            failVerifIds.add(info.getId());
                            failOrderIds.add(info.getPurchaseOrderId());
                        }
                    }

                    if (ObjectUtil.isNotEmpty(failVerifIds)) {
                        failUpdateWrapper.set(PmcVerifInfo::getSyncResultMsg, "批量同步采购订单到K3系统，部分失败,调用时间" + LocalDateTime.now().toString());
                    }

                } else {
                    failUpdateWrapper.set(PmcVerifInfo::getSyncResultMsg, jsonObject.toJSONString());
                    log.error("备货2.0批量调用k3采购订单接口失败:" + JSON.toJSONString(jsonObject));
                    failVerifIds = idList;
                }
            } else {
                failUpdateWrapper.set(PmcVerifInfo::getSyncResultMsg, jsonObject.toJSONString());
                log.error("备货2.0批量调用k3采购订单接无返回值:" + JSON.toJSONString(jsonObject));
                failVerifIds = idList;
            }
        } catch (Exception e) {
            log.error("备货2.0批量调用k3采购订单接口异常:{}", e);
            failUpdateWrapper.set(PmcVerifInfo::getSyncResultMsg, JSON.toJSONString(e));
            failVerifIds = idList;
        }

        if (ObjectUtil.isNotEmpty(successVerifIds)) {
            successUpdateWrapper.set(PmcVerifInfo::getVerifResult, StockConstant.VERIFY_SUCESS);
            successUpdateWrapper.set(PmcVerifInfo::getSyncStatus, StockConstant.SYNC_SUCESS);
            successUpdateWrapper.set(PmcVerifInfo::getSyncTime, new Date());
            successUpdateWrapper.set(PmcVerifInfo::getUpdateTime, new Date());
            successUpdateWrapper.in(PmcVerifInfo::getId, successVerifIds);
            this.update(successUpdateWrapper);
        }

        if (ObjectUtil.isNotEmpty(failVerifIds)) {
            failUpdateWrapper.set(PmcVerifInfo::getSyncStatus, StockConstant.SYNC_FAIL);
            failUpdateWrapper.set(PmcVerifInfo::getSyncTime, new Date());
            failUpdateWrapper.set(PmcVerifInfo::getUpdateTime, new Date());
            failUpdateWrapper.in(PmcVerifInfo::getId, failVerifIds);
            this.update(failUpdateWrapper);
        }

        //更新订单状态 同步成功的
        if (ObjectUtil.isNotEmpty(successOrderIds)) {
            LambdaUpdateWrapper<PurchaseOrders> tWrapper = new LambdaUpdateWrapper<>();
            tWrapper.set(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PMC_YES);
            tWrapper.set(PurchaseOrders::getUpdateTime, new Date());
            tWrapper.in(PurchaseOrders::getId, successOrderIds);
            purchaseOrdersService.update(tWrapper);
        }

        //更新订单状态 同步失败的
        if (ObjectUtil.isNotEmpty(failOrderIds)) {
            LambdaUpdateWrapper<PurchaseOrders> tWrapper = new LambdaUpdateWrapper<>();
            tWrapper.set(PurchaseOrders::getOrderStatus, StockConstant.ORDER_STATUS_PMC_YES_SYNC_FAIL);
            tWrapper.set(PurchaseOrders::getUpdateTime, new Date());
            tWrapper.in(PurchaseOrders::getId, failOrderIds);
            purchaseOrdersService.update(tWrapper);
        }

        return syncResult;
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchVerif(List<PmcVerifInfoParam> parmList, String verifyResult) {

        String result = verifyResult;
        ArrayList<String> errorList = new ArrayList<>();

        for (PmcVerifInfoParam pmcVerifInfoParam : parmList) {
            pmcVerifInfoParam.setVerifResult(verifyResult);
            ResponseData responseData = this.pmcVerif(pmcVerifInfoParam);
            if (!responseData.getSuccess()) {
                errorList.add(String.valueOf(responseData.getData()));
            }
        }
        if (ObjectUtil.isNotEmpty(errorList)) {
            return ResponseData.error(500, "部分数据审核失败", errorList);
        }
        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateInfo(PmcVerifInfoParam param) {

        if (ObjectUtil.isNull(param) || ObjectUtil.isEmpty(param.getId())) {
            return ResponseData.error("传入参数id为空");
        }

        PmcVerifInfo pmcVerifInfo = this.getById(param.getId());
        pmcVerifInfo.setPurchasePerson(param.getPurchasePerson());
        pmcVerifInfo.setPurchasePersonId(param.getPurchasePersonId());
        pmcVerifInfo.setAdviceSupplier(param.getAdviceSupplier());
        pmcVerifInfo.setAdviceSupplierId(param.getAdviceSupplierId());
        pmcVerifInfo.setVerifReason(param.getVerifReason());
        pmcVerifInfo.setUpdateTime(new Date());
        //款式二级标签
        if (ObjectUtil.isNotEmpty(param.getStyleSecondLabel())) {
            pmcVerifInfo.setMatstylesecondlabel(param.getStyleSecondLabel());
        }
        if (this.updateById(pmcVerifInfo)) {
            return ResponseData.success();
        }

        return ResponseData.error("更新失败");
    }


    @DataSource(name = "stocking")
    @Override
    public PageResult<PmcVerifInfoResult> querypmcVerifList(PmcVerifInfoParam param) {

        //        log.info("PMC审批查询");
        Page pageContext = param.getPageContext();
        Page<PmcVerifInfoResult> page = this.baseMapper.querypmcVerifList(pageContext, param);
        return new PageResult<>(page);

    }

    @DataSource(name = "erpcloud")
    @Override
    public List<SupplierInfoResult> getMatControlPersonBySupplier(String supplier,String supplierCode) {
        return  this.baseMapper.getMatControlPersonBySupplier(supplier,supplierCode);

    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData auditAll() {

        LambdaQueryWrapper<PmcVerifInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PmcVerifInfo::getVerifResult, StockConstant.VERIFY_WAIT, StockConstant.VERIFY_SUCESS);
        wrapper.ne(PmcVerifInfo::getSyncStatus, StockConstant.SYNC_SUCESS);
        wrapper.isNotNull(PmcVerifInfo::getPurchasePersonId);
        wrapper.isNotNull(PmcVerifInfo::getAdviceSupplierId);

        List<PmcVerifInfo> pmcVerifInfoList = this.getBaseMapper().selectList(wrapper);

        if (ObjectUtil.isEmpty(pmcVerifInfoList)) {
            return ResponseData.success("未找到能自动通过的数据");
        }

        if (this.batchCallK3(pmcVerifInfoList)) {
            return ResponseData.success("自动审核完成");
        }
        return ResponseData.error("自动审核失败");

    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }
}