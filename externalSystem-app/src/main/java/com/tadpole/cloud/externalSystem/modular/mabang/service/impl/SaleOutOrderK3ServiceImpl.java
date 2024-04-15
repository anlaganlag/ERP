package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.util.K3GeneratorNoUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderItemK3;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderK3;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderK3Mapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.SaleOutOrderK3QueryResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMaterialPriceInfoService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemK3Service;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderK3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 根据K3仓库可用数量自动产生-销售出库单 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2023-04-07
 */
@Service
@Slf4j

public class SaleOutOrderK3ServiceImpl extends ServiceImpl<SaleOutOrderK3Mapper, SaleOutOrderK3> implements ISaleOutOrderK3Service {

    @Resource
    private SaleOutOrderK3Mapper mapper;

    @Resource
    private ISaleOutOrderItemK3Service itemK3Service;

    @Resource
    private K3TransferItemMapper k3TransferItemMapper;
    @Resource
    SyncToErpUtil syncToErpUtil;

    @Resource
    K3GeneratorNoUtil k3GeneratorNoUtil;

    @Resource
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @Resource
    IMaterialPriceInfoService materialPriceInfoService;

    public static Integer BATCH_SIZE = 50;

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void createK3SaleOutOrder(List<K3AvailableResult> availableResultList) {
        if (ObjectUtil.isEmpty(availableResultList)) {
            return;
        }

        Map<String, BigDecimal> matPriceMap = materialPriceInfoService.getMabangMatPrice(availableResultList);
        if (ObjectUtil.isEmpty(matPriceMap)) {  //所有物料编码都没有找到物料的采购价
            return;
        }

        List<List<K3AvailableResult>> availableResultSplitList = CollUtil.split(availableResultList, BATCH_SIZE);
        for (List<K3AvailableResult> batchResultList : availableResultSplitList) {

            //过滤出未找到物料价格的k3查询记录
            List<K3AvailableResult> resultList = batchResultList.stream().filter(r -> matPriceMap.containsKey(r.getMaterialCode())).collect(Collectors.toList());
            if (ObjectUtil.isEmpty(resultList)) {
                continue;
            }

            String billNo = k3GeneratorNoUtil.getNoByKey(MabangConstant.SALEOUT_ORDER_PREFIX_MB, MabangConstant.SYNC_K3_SALEOUT_ORDER_KEY, 6);

            SaleOutOrderK3 out = new SaleOutOrderK3();
            //1-1-1.销售出库单头
            out.setFBillNo(billNo);
            LocalDate localDate = LocalDate.now();

            String fDate = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
            out.setFDate(fDate);//必填字段：日期

            out.setFCreatorId("S20180229"); //必填字段：制单人
            out.setFSaleOrgId("002");//必填字段：销售组织编码:采购中心
            out.setFCustomerId("平台发展");//必填字段：客户编码
//            out.setFCorrespondOrgId("平台发展");//对应组织编码
            out.setFStockOrgId("002");//必填字段：发货组织编码
            out.setFNote("销售出库");
            out.setFPayerId("平台发展");
            out.setCreatePurchaseOrder(BigDecimal.valueOf(-1L));
            this.save(out);
            //单体
            ArrayList<SaleOutOrderItemK3> itemList = new ArrayList<>();
            for (K3AvailableResult result : resultList) {
                SaleOutOrderItemK3 item = new SaleOutOrderItemK3();
                item.setFCustomerSku(result.getMaterialCode());
                item.setFMaterialId(result.getMaterialCode());//必填字段：物料编码编码
                item.setFMaterialName(result.getProductName());
                item.setFUnitId("Pcs");
                item.setFOwnerTypeId("业务组织");//必填字段：货主类型
                item.setFRealQty(String.valueOf(result.getQty().intValue()));//必填字段：实发数量
                item.setFStockId(result.getStockNo());//必填字段：仓库#编码   雁田定制仓：YT05_008
                item.setWarehouseName(result.getStockName());
                item.setFBscTeam("平台发展组");//必填字段：需求Team编码绑定基础资料：部门
                if ("雁田-BTB仓库".equals(result.getStockName()) ||"YT06_BTB".equals(result.getStockNo()) ) {
                    item.setFBscTeam("BTB组");//必填字段：需求Team编码绑定基础资料：部门
                }
                item.setFBscDept("平台发展部");//必填字段：需求部门编码，基础资料：部门
//                item.setFBscSubremark1("是备注内容");//必填字段：需求部门编码，基础资料：部门
                item.setFBillNo(billNo);
                item.setSaleOutOrderId(out.getId());
                item.setSalePrice(matPriceMap.get(result.getMaterialCode()));
                itemList.add(item);
            }
            itemK3Service.saveBatch(itemList);

            //组织请求k3销售出库接口的请求参数

            JSONArray Jarr = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("FBillNo", out.getFBillNo());
            object.put("FDate", out.getFDate());
            object.put("FCreatorId", out.getFCreatorId());
            object.put("FSaleOrgId", out.getFSaleOrgId());
            object.put("FCustomerID", out.getFCustomerId());
            object.put("FCorrespondOrgId", out.getFCorrespondOrgId());
            object.put("FStockOrgId", out.getFStockOrgId());
            object.put("FNote", out.getFNote());
            object.put("FPayerID", out.getFPayerId());

            JSONArray itemArray = new JSONArray();
            for (SaleOutOrderItemK3 item : itemList) {
                JSONObject itemObject = new JSONObject();
                itemObject.put("FMaterialID", item.getFMaterialId());
                itemObject.put("FRealQty", Integer.valueOf(item.getFRealQty()));
                itemObject.put("FSALBASEQTY", Integer.valueOf(item.getFRealQty()));
                itemObject.put("FPRICEBASEQTY", Integer.valueOf(item.getFRealQty()));
                itemObject.put("FBASEUNITQTY", Integer.valueOf(item.getFRealQty()));
                itemObject.put("FOwnerTypeID", item.getFOwnerTypeId());
                itemObject.put("FStockID", item.getFStockId());
                itemObject.put("F_BSC_Team", item.getFBscTeam());
                itemObject.put("F_BSC_Dept", item.getFBscDept());
                itemObject.put("FUnitID", item.getFUnitId());
                itemObject.put("F_BSC_SubRemark2", item.getSalePrice().toPlainString()); //销售出库单价 传在备注2字段，k3接口自动映射 备注2到销售出库价格上
                itemArray.add(itemObject);
            }

            object.put("FEntity", itemArray);
            Jarr.add(object);

            log.info("查询k3仓库可用库存数量>0的物料自动产生销售出库单[{}]，K3销售出库请求k3参数:[{}]", billNo, JSONUtil.toJsonStr(Jarr));
            out.setSyncRequstPar(JSONUtil.toJsonStr(Jarr));
            try {
                JSONObject obj = syncToErpUtil.saleOutStock(Jarr);

                log.info("查询仓库可用库存数量>0的物料自动产生销售出库单[{}]，K3销售出库请求k3返回结果:[{}]", billNo, JSONUtil.toJsonStr(obj));
                out.setSyncResultMsg(JSONUtil.toJsonStr(obj));

                if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {  //调用接口创建销售出库单失败
                    out.setSyncStatus("0");
                    String responseMsg = null;
                    JSONArray responseResult = JSON.parseArray(obj.getString("Response"));
                    if (ObjectUtil.isNotNull(responseResult)) {
                        responseMsg = responseResult.getJSONObject(0).getString("SubMessage");
                    }
                    //调用失败 ： 后面查询 更改状态 ，如果k3也是没有找到，删除该记录，如果k3有找到则，修改为成功

                } else {
                    out.setSyncStatus("1");
                    //调用接口创建销售出库--成功
                }
            } catch (Exception e) {
                out.setSyncStatus("0");
                out.setSyncResultMsg(JSONUtil.toJsonStr(e));
                log.error("查询仓库可用库存数量>0的物料自动产生销售出库单[{}],处理异常:[{}]", billNo, JSONUtil.toJsonStr(e.getMessage()));
            }
            out.setUpdateTime(new Date());
            out.setSyncTime(new Date());
            this.saveOrUpdate(out);
        }

    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saleOutOrderExceptionHandle(String billNo) {

//         查找创建销售出库单失败
        LambdaQueryWrapper<SaleOutOrderK3> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleOutOrderK3::getSyncStatus, "0");
        queryWrapper.eq(ObjectUtil.isNotEmpty(billNo), SaleOutOrderK3::getFBillNo, billNo);

        List<SaleOutOrderK3> orderK3s = this.list(queryWrapper);
        if (ObjectUtil.isEmpty(orderK3s)) {
            return;
        }

        List<String> delBillNoList = new ArrayList<>();
        for (SaleOutOrderK3 orderK3 : orderK3s) {

            String k3BillNo = orderK3.getFBillNo();

            try {
                K3CloudApi api = new K3CloudApi(getConfigInfo());
                if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                    String jsonData = "{\"Number\":\"" + k3BillNo + "\"}";
                    log.info("使用WEB-API查询K3销售出库单【{}】信息-请求", k3BillNo);
                    String result = api.view(MabangConstant.K3_FROM_ID_SAL_OUTSTOCK, jsonData);
                    log.info("使用WEB-API查询K3销售出库单【{}】信息-返回结果[{}]", k3BillNo, result);

                    JSONObject resultJson = JSON.parseObject(result);

                    if (ObjectUtil.isNotNull(resultJson)) {
                        JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                        if (ObjectUtil.isNotNull(resultValue)) {
                            JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                            if (ObjectUtil.isNotNull(resultResponse)) {
                                //2-1.保存同步返回成功状态处理
                                if ("true".equals(resultResponse.getString("IsSuccess"))) {
                                    //返回成功
                                    log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--成功[{}]", k3BillNo, result);
                                    orderK3.setSyncStatus("1");
                                    orderK3.setSyncResultMsg("调拨单创建时出现异常，后调用查询接口，查到k3已经创建成功，现更改同步状态为成功");

                                } else {
                                    //查询失败没有找到或者异常
                                    log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--失败[{}]", k3BillNo, result);
                                    Date syncTime = ObjectUtil.isNull(orderK3.getSyncTime()) ? orderK3.getCreateTime() : orderK3.getSyncTime();
                                    LocalDate syncDate = syncTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                                    //k3没有创建成功的记录 需要删除
                                    if (result.contains("传递的编码值不存在")) {
                                        delBillNoList.add(orderK3.getFBillNo());
                                        log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--失败-传递的编码值不存在，MCMS系统删除[{}]", k3BillNo, result);
                                    } else if (syncDate.plusDays(5).isBefore(LocalDate.now())) {
                                        //异常的数据超过5天自动清楚
                                        delBillNoList.add(orderK3.getFBillNo());
                                    }

                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {
                log.error("调拨单单据:[{}] 提交异常，异常信息:[{}]", k3BillNo, e.getMessage());
            }
            orderK3.setUpdateTime(new Date());
        }


        this.updateBatchById(orderK3s);

        //删除异常记录超过一定时间的数据
        if (ObjectUtil.isNotEmpty(delBillNoList)) {
            LambdaQueryWrapper<SaleOutOrderK3> orderQuery = new LambdaQueryWrapper<>();
            orderQuery.in(SaleOutOrderK3::getFBillNo, delBillNoList);
            this.baseMapper.delete(orderQuery);

            LambdaQueryWrapper<SaleOutOrderItemK3> orderItemQuery = new LambdaQueryWrapper<>();
            orderItemQuery.in(SaleOutOrderItemK3::getFBillNo, delBillNoList);
            this.itemK3Service.remove(orderItemQuery);
        }
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


    @DataSource(name = "k3cloud")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<K3AvailableResult> queryK3AvailableQty(List<String> warehouseList) {
        List<K3AvailableResult> resultList = k3TransferItemMapper.queryK3AvailableQty(warehouseList);
        return resultList;
    }

    @Override
    public ResponseData orderUpdate(String orderNo) {

//        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-search-sku-list",mabangGoodsSearchParm);
//
//        MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
//
//        List<MabangStockResult> result = (List<MabangStockResult>)(((Map) mabangResult.getData()).get("data"));
//
//        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));

        return null;
    }

    @DataSource(name = "k3cloud")
    @Override
    public ResponseData k3stockPrice(String jsonData) {

        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                System.out.println("11111111111111111111111111111111111111111111111111111111111111111");
                String result1 = api.getSysReportData("HS_HSSummary", jsonData);
                System.out.println("3333333333333333333333333333333333333333333333333333333333333333333");
                System.out.println(result1);


//                String jsonData = "{\"Number\":\"" + k3BillNo + "\"}";
                String jsonData2 = "{\"FieldKeys\":\"FMaterialNo,FMaterialName,FStatus\",\"SchemeId\":\"\",\"StartRow\":0,\"Limit\":2000,\"IsVerifyBaseDataField\":\"true\",\"Model\":{\"FACCTGSYSTEMID\":{\"FNumber\":\"KJHSTX01_SYS\"},\"FACCTGORGID\":{\"FNumber\":\"002\"},\"FACCTPOLICYID\":{\"FNumber\":\"KJZC01_SYS\"},\"FYear\":\"2023\",\"FPeriod\":\"3\",\"FMATERIALID\":{\"FNumber\":\"P00LK001\"},\"FENDMATERIALID\":{\"FNumber\":\"\"},\"FACCTGRANGEID\":{\"FNumber\":\"\"},\"FCURRENCYID\":{\"FNumber\":\"\"},\"FOnlyWrong\":\"false\"}}";
                String jsonData3 = "{\n" +
                        "  \"FieldKeys\": \"FMaterialNo,FMaterialName,FStatus\",\n" +
                        "  \"SchemeId\": \"\",\n" +
                        "  \"StartRow\": 0,\n" +
                        "  \"Limit\": 2000,\n" +
                        "  \"IsVerifyBaseDataField\": \"true\",\n" +
                        "  \"Model\": {\n" +
                        "    \"FACCTGSYSTEMID\": {\n" +
                        "      \"FNumber\": \"KJHSTX01_SYS\"\n" +
                        "    },\n" +
                        "    \"FACCTGORGID\": {\n" +
                        "      \"FNumber\": \"002\"\n" +
                        "    },\n" +
                        "    \"FACCTPOLICYID\": {\n" +
                        "      \"FNumber\": \"KJZC01_SYS\"\n" +
                        "    },\n" +
                        "    \"FYear\": \"2023\",\n" +
                        "    \"FPeriod\": \"第3期\",\n" +
                        "    \"FMATERIALID\": {\n" +
                        "      \"FNumber\": \"P00LK001\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
                log.info("使用WEB-API查询K3库存成本价【{}】信息-请求", jsonData);
                String result = api.getSysReportData("HS_HSSummary", jsonData);
                log.info("使用WEB-API查询K3库存成本价-返回结果[{}]", result);

                JSONObject resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                    if (ObjectUtil.isNotNull(resultValue)) {
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if (ObjectUtil.isNotNull(resultResponse)) {
                            //2-1.保存同步返回成功状态处理
                            if ("true".equals(resultResponse.getString("IsSuccess"))) {
                                //返回成功
                                log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--成功[{}]", result);
                                return ResponseData.success(result);
                            } else {
                                //查询失败没有找到或者异常
                                log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--失败[{}]", result);

                                return ResponseData.error(result);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            log.error("调拨单单据提交异常，异常信息:[{}]", e.getMessage());
        }
        return ResponseData.success();
    }

    @DataSource(name = "k3cloud")
    @Override
    public ResponseData k3stockPrice2(String jsonData) {

        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String jsonData2 = "{\"FieldKeys\":\"FMATERIALNAME,FENDPrice\",\"SchemeId\":\"\",\"StartRow\":0,\"Limit\":2000,\"IsVerifyBaseDataField\":\"true\",\"Model\":{\"FACCTGSYSTEMID\":{\"FNumber\":\"KJHSTX01_SYS\"},\"FACCTGORGID\":{\"FNumber\":\"002\"},\"FACCTPOLICYID\":{\"FNumber\":\"KJZC01_SYS\"},\"FYear\":\"2023\",\"FENDYEAR\":\"\",\"FPeriod\":\"3\",\"FEndPeriod\":\"\",\"FSTARTMATERIALID\":{\"FNumber\":\"P00LK001\"},\"FENDMATERIALID\":{\"FNumber\":\"\"},\"FPeriodTotal\":\"false\",\"FEXPENID\":{\"FNumber\":\"\"},\"FENDEXPENID\":{\"FNumber\":\"\"},\"FACCTGRANGEID\":{\"FNumber\":\"\"},\"FSTOCKORGID\":{\"FNumber\":\"\"},\"FSTOCKID\":{\"FNumber\":\"\"},\"FOwnerID\":{\"FNumber\":\"\"},\"FCHXEXPENSE\":\"false\",\"FMATERTYPE\":{\"FNUMBER\":\"\"},\"FCHXNOINOUT\":\"false\",\"FCHXNOSTOCKADJ\":\"false\",\"FCHXNOCOSTALLOT\":\"false\"}}";

                log.info("使用WEB-API查询K3库存成本价【{}】信息-请求", jsonData);
                String result = api.getSysReportData("HS_NoDimInOutStockDetailRpt", jsonData);
                log.info("使用WEB-API查询K3库存成本价-返回结果[{}]", result);

                JSONObject resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                    if (ObjectUtil.isNotNull(resultValue)) {
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if (ObjectUtil.isNotNull(resultResponse)) {
                            //2-1.保存同步返回成功状态处理
                            if ("true".equals(resultResponse.getString("IsSuccess"))) {
                                //返回成功
                                log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--成功[{}]", result);
                                return ResponseData.success(result);
                            } else {
                                //查询失败没有找到或者异常
                                log.info("使用WEB-API查询K3销售出库单【{}】信息-查询--失败[{}]", result);

                                return ResponseData.error(result);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            log.error("调拨单单据提交异常，异常信息:[{}]", e.getMessage());
        }
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    public PageResult<SaleOutOrderK3QueryResult> findPageBySpec(SaleOutOrderK3QueryResult param) {
        Page pageContext = getPageContext();
        IPage<SaleOutOrderK3QueryResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);

    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
