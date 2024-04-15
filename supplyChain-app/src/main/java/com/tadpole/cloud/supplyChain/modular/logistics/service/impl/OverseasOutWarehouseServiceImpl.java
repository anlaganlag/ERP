package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseDetailBoxInfoParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseDetailParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsOverseasOutWarehouseLogisticsParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.InvProductGalleryParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BoxInfoResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ExportOverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.EbmsOverseaConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.*;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasOutWarehouseMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  海外仓出库管理服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Service
@Slf4j
public class OverseasOutWarehouseServiceImpl extends ServiceImpl<OverseasOutWarehouseMapper, OverseasOutWarehouse> implements IOverseasOutWarehouseService {

    @Autowired
    private OverseasOutWarehouseMapper mapper;
    @Autowired
    private EbmsOverseaConsumer ebmsOverseaConsumer;
    @Autowired
    private IOverseasOutWarehouseDetailService overseasOutWarehouseDetailService;
    @Autowired
    private IOverseasWarehouseManageService overseasWarehouseManageService;
    @Autowired
    private IOverseasWarehouseManageRecordService recordService;
    @Autowired
    private ISyncTransferToErpService syncTransferToErpService;
    @Autowired
    private IOverseasWarehouseAgeService ageService;
    @Value("${logistics.order.env}")
    private String env;

    @DataSource(name = "logistics")
    @Override
    public PageResult<OverseasOutWarehouseResult> queryListPage(OverseasOutWarehouseParam param) {

        //1-1.箱条码、SKU、物料编码查询入库单号列表
        if(StrUtil.isEmpty(param.getOutOrder())) {
            if (StrUtil.isNotEmpty(param.getMaterialCode()) || StrUtil.isNotEmpty(param.getSku())
                    || StrUtil.isNotEmpty(param.getPackageBarCode())) {
                QueryWrapper<OverseasOutWarehouseDetail> qw = new QueryWrapper<>();
                qw.eq(StrUtil.isNotEmpty(param.getPackageBarCode()), "PACKAGE_BAR_CODE", param.getPackageBarCode())
                        .eq(StrUtil.isNotEmpty(param.getSku()), "SKU", param.getSku())
                        .eq(StrUtil.isNotEmpty(param.getMaterialCode()), "MATERIAL_CODE", param.getMaterialCode());

                List<OverseasOutWarehouseDetail> list = overseasOutWarehouseDetailService.list(qw);

                if (CollectionUtil.isNotEmpty(list)) {
                    List<String> outOrderList = list.stream().map(OverseasOutWarehouseDetail::getOutOrder).distinct().collect(Collectors.toList());
                    param.setOutOrders(outOrderList);
                } else {
                    param.setOutOrder("NULL");
                }
            }
        }
        //1-2.空值筛选
        List<String> list=param.getInWarehouseNames();
        List<String> statusList=param.getLogisticsStatusList();
        if(CollectionUtil.isNotEmpty(list)){
            if(list.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(statusList)){
            if(statusList.contains("空")){
                param.setLogisticsStatus("空");
            }
        }
        Page pageContext = getPageContext();
        IPage<OverseasOutWarehouseResult> page = this.baseMapper.queryListPage(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryPageTotal(OverseasOutWarehouseParam param) {

        //1-1.箱条码、SKU、物料编码查询入库单号列表
        if(StrUtil.isEmpty(param.getOutOrder())) {
            if (StrUtil.isNotEmpty(param.getMaterialCode()) || StrUtil.isNotEmpty(param.getSku())
                    || StrUtil.isNotEmpty(param.getPackageBarCode())) {
                QueryWrapper<OverseasOutWarehouseDetail> qw = new QueryWrapper<>();
                qw.eq(StrUtil.isNotEmpty(param.getPackageBarCode()), "PACKAGE_BAR_CODE", param.getPackageBarCode())
                        .eq(StrUtil.isNotEmpty(param.getSku()), "SKU", param.getSku())
                        .eq(StrUtil.isNotEmpty(param.getMaterialCode()), "MATERIAL_CODE", param.getMaterialCode());

                List<OverseasOutWarehouseDetail> list = overseasOutWarehouseDetailService.list(qw);

                if (CollectionUtil.isNotEmpty(list)) {
                    List<String> outOrderList = list.stream().map(OverseasOutWarehouseDetail::getOutOrder).distinct().collect(Collectors.toList());
                    param.setOutOrders(outOrderList);
                } else {
                    param.setOutOrder("NULL");
                }
            }
        }
        //1-2.空值筛选
        List<String> list=param.getInWarehouseNames();
        List<String> statusList=param.getLogisticsStatusList();
        if(CollectionUtil.isNotEmpty(list)){
            if(list.contains("空")){
                param.setInWarehouseName("空");
            }
        }
        if(CollectionUtil.isNotEmpty(statusList)){
            if(statusList.contains("空")){
                param.setLogisticsStatus("空");
            }
        }
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("shouldOutQuantityTotal", mapper.queryPageTotal(param));
        return ResponseData.success(result);
    }

    @DataSource(name = "logistics")
    @Override
    public List<OverseasOutWarehouseDetailResult> list(OverseasOutWarehouseDetailParam param) {

        return this.baseMapper.list(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<ExportOverseasOutWarehouseResult> export(OverseasOutWarehouseParam param) {
        List<String> list=param.getInWarehouseNames();
        List<String> statusList=param.getLogisticsStatusList();
        if(CollectionUtil.isNotEmpty(list)){
            list.contains("空");
            param.setInWarehouseName("空");
        }
        if(CollectionUtil.isNotEmpty(statusList)){
            statusList.contains("空");
            param.setLogisticsStatus("空");
        }
        return this.baseMapper.allList(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<Map<String, Object>> outWarehouseSelect() {
        QueryWrapper<OverseasOutWarehouse> wrapper = new QueryWrapper<>();
        wrapper.select("OUT_WAREHOUSE_NAME").groupBy("OUT_WAREHOUSE_NAME");
        return this.listMaps(wrapper);
    }

    @Override
    @DataSource(name = "logistics")
    public List<Map<String, Object>> inWarehouseSelect() {
        QueryWrapper<OverseasOutWarehouse> wrapper = new QueryWrapper<>();
        wrapper.select("IN_WAREHOUSE_NAME").groupBy("IN_WAREHOUSE_NAME").orderByDesc("IN_WAREHOUSE_NAME");
        List<Map<String, Object>> selectList = this.listMaps(wrapper);
        if(selectList.contains(null)){
            Map<String, Object> map = new HashMap<>();
            map.put("IN_WAREHOUSE_NAME", "空");
            Collections.replaceAll(selectList, null, map);
        }
        return selectList;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData receiveLogisticsByEBMS(List<EbmsOverseasOutWarehouseLogisticsParam> params) {
        log.info("获取EBMS物流状态信息:[{}]", JSONObject.toJSON(params));
        //回传物流状态、物流单号
       try{
           if(CollectionUtil.isNotEmpty(params)){

               String logisticsStatus="未发货";

               for(EbmsOverseasOutWarehouseLogisticsParam detailParam:params){

                   LambdaUpdateWrapper<OverseasOutWarehouseDetail> detailWrapper=new LambdaUpdateWrapper<>();
                   detailWrapper.eq(OverseasOutWarehouseDetail::getOutOrder,detailParam.getPackCode())
                           .eq(StringUtils.isNotBlank(detailParam.getPackDetBoxCode()), OverseasOutWarehouseDetail::getPackageBarCode,detailParam.getPackDetBoxCode())
                           .eq(OverseasOutWarehouseDetail::getPackageNum,detailParam.getPackDetBoxNum())
                           .set(OverseasOutWarehouseDetail::getLogisticsCompany,detailParam.getLogisticsCompany())
                           .set(OverseasOutWarehouseDetail::getLogisticsNum,detailParam.getLogisticsNum())
                           .set(OverseasOutWarehouseDetail::getUpdateTime,DateUtil.date());
                   overseasOutWarehouseDetailService.update(null,detailWrapper);
               }

               //获取EBMS票单号
               Map<String,List<EbmsOverseasOutWarehouseLogisticsParam>> orderNoList=params.stream()
                       .collect(Collectors.groupingBy(EbmsOverseasOutWarehouseLogisticsParam::getPackCode, LinkedHashMap::new, Collectors.toList()));

               for(Map.Entry<String,List<EbmsOverseasOutWarehouseLogisticsParam>> entry : orderNoList.entrySet()){

                       //票单号值
                       String outOrder = entry.getKey();

                       LambdaQueryWrapper<OverseasOutWarehouseDetail> queryWrapper=new LambdaQueryWrapper<>();
                       queryWrapper.eq(OverseasOutWarehouseDetail::getOutOrder,outOrder);
                       List<OverseasOutWarehouseDetail> resultList=overseasOutWarehouseDetailService.list(queryWrapper);

                       //箱件总记录
                       int total=resultList.size();
                       if(resultList.size()>0){
                           List<OverseasOutWarehouseDetail> result=null;
                           //返回结果集->无物流单
                           result=resultList.stream()
                                   .filter((OverseasOutWarehouseDetail p) -> StrUtil.isNotEmpty(p.getLogisticsNum()))
                                   .collect(Collectors.toList());
                           int resultTotal=result.size();

                           if(resultTotal > 0 && total > resultTotal){
                               logisticsStatus="部分发货";
                           }

                           if(resultTotal==total){
                               logisticsStatus="全部发货";
                           }
                       }
                       //物流状态更新
                       LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper=new LambdaUpdateWrapper<>();
                       updateWrapper.eq(OverseasOutWarehouse::getOutOrder,outOrder)
                               .set(OverseasOutWarehouse::getUpdateTime,DateUtil.date())
                               .set(OverseasOutWarehouse::getLogisticsStatus,logisticsStatus);
                       mapper.update(null,updateWrapper);

               }
           }
           return ResponseData.success("EBMS海外仓出库物流信息回传成功！");
       }catch (Exception e){
           log.error("EBMS海外仓出库物流信息回传失败，异常信息[{}]",e.getMessage());
           return ResponseData.error("EBMS海外仓出库物流信息回传失败,异常信息:"+e.getMessage());
       }

    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData save(OverseasOutWarehouseParam param) throws Exception{

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        if(CollectionUtil.isNotEmpty(param.getDetailList())){
            OverseasOutWarehouse ent=new OverseasOutWarehouse();

            List<OverseasOutWarehouseDetailParam> list=param.getDetailList();
            List<OverseasOutWarehouseDetail> detailList=new ArrayList<>();
            //数量求和
            BigDecimal shouldOutQuantity=list.stream().map(OverseasOutWarehouseDetailParam::getQuantity).reduce(BigDecimal.ZERO,BigDecimal::add);
            //sku种类数量
            long skuCount=list.stream().map(OverseasOutWarehouseDetailParam::getSku).distinct().count();
            //总箱数
            long totalPackageNum=list.stream().map(OverseasOutWarehouseDetailParam::getPackageNum).distinct().count();

            //新建出库单
            String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
            String outOrder = overseasWarehouseManageService.getLogisticsOrder(env + "_"  + OrderTypePreEnum.CKQD.getCode() + pureDate, OrderTypePreEnum.CKQD, 5);

            BeanUtils.copyProperties(param,ent);
            ent.setOutOrder(outOrder);
            TransferBusinessTypeEnum typeEnum = null;
            if("亚马逊仓".equals(param.getInWarehouseType())){
                ent.setOperateType(OperateTypeEnum.OVERSEAS_TO_AMAZON.getName());
                typeEnum = TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON;
            }else{
                ent.setOperateType(OperateTypeEnum.OVERSEAS_TO_WALMART.getName());
                typeEnum = TransferBusinessTypeEnum.OVERSEAS_TO_WALMART;
            }
            ent.setShouldOutQuantity(shouldOutQuantity);
            ent.setSkuNum(new BigDecimal(skuCount));
            ent.setTotalPackageNum(new BigDecimal(totalPackageNum));
            ent.setCreateUser(accountAndName);
            ent.setCreateTime(DateUtil.date());

            param.setOutOrder(outOrder);
            this.save(ent);

            for(OverseasOutWarehouseDetailParam detailParam : param.getDetailList()){

                String packageNumName="Box "+String.valueOf(detailParam.getPackageNum())+" - "+"QTY";
                OverseasOutWarehouseDetail detail=new OverseasOutWarehouseDetail();
                BeanUtils.copyProperties(detailParam,detail);
                detail.setOutOrder(outOrder);
                detail.setPackageNumName(packageNumName);
                detail.setCreateUser(accountAndName);
                detail.setCreateTime(DateUtil.date());
                detailList.add(detail);
                overseasOutWarehouseDetailService.save(detail);

                //海外仓库存扣减和入库操作记录
                BeanUtils.copyProperties(ent,param);
                outWarehouseReduceInventory(param,detailParam);
            }

            //推送ERP->创建组织内调拨单
            ResponseData erpResponseData = syncTransferToErpService.overseasOutSyncERP(outOrder, typeEnum);
            if(ResponseData.DEFAULT_SUCCESS_CODE.equals(erpResponseData.getCode())){
                //k3同步状态更新
                LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OverseasOutWarehouse::getOutOrder,param.getOutOrder())
                        .set(OverseasOutWarehouse::getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode())
                        .set(OverseasOutWarehouse::getUpdateTime,DateUtil.date())
                        .set(OverseasOutWarehouse::getUpdateUser,accountAndName);
                mapper.update(null,updateWrapper);
            }else{
                String syncStatus = BaseSyncStatusEnum.ERROR.getCode();
                if(erpResponseData.getMessage().contains("调用k3直接调拨单审核接口失败")){
                    syncStatus = BaseSyncStatusEnum.AUDIT_ERROR.getCode();
                }
                LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OverseasOutWarehouse::getOutOrder,param.getOutOrder())
                        .set(OverseasOutWarehouse::getSyncErpStatus,syncStatus)
                        .set(OverseasOutWarehouse::getUpdateTime,DateUtil.date())
                        .set(OverseasOutWarehouse::getUpdateUser,accountAndName);
                mapper.update(null,updateWrapper);
                log.error("调用k3接口异常，返回k3信息[{}]",erpResponseData.getMessage());
            }

            //推送EBMS->创建出货清单
            ResponseData responseData = createShipmentList(param, false);
            if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                log.error("海外仓出库单号[{}]，创建出货清单失败！信息[{}]",param.getOutOrder(),responseData.getMessage());
                //throw new RuntimeException(responseData.getMessage());
            }
        }
        return ResponseData.success("保存成功！");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData createShipmentList(OverseasOutWarehouseParam param, Boolean isTask) {

        //获取当前操作用户信息
        String account = "";
        String name = "";
        if(isTask){
            String[] user = param.getCreateUser().split("_");
            account = user[0];
            name = user[1];
        } else {
            account = LoginContext.me().getLoginUser().getAccount();
            name = LoginContext.me().getLoginUser().getName();
        }

        List<EbmsOverseasOutWarehouseDetailParam> detailList=new ArrayList<>();
        List<EbmsOverseasOutWarehouseDetailBoxInfoParam> boxInfoList=new ArrayList<>();

        try{
            EbmsOverseasOutWarehouseParam outParam=new EbmsOverseasOutWarehouseParam();
            outParam.setPackCode(param.getOutOrder());
            outParam.setPackPerCode(account);
            outParam.setPackPerName(name);
            outParam.setCountryCode(param.getSysSite());
            outParam.setShopNameSimple(param.getSysShopsName());
            outParam.setShopPlatName(param.getPlatform());

            if("亚马逊仓".equals(param.getInWarehouseType())){
                outParam.setComCountryCode(param.getSysSite());
                outParam.setComShopNameSimple(param.getSysShopsName());
                outParam.setComShopPlatName(param.getPlatform());
            }else{
                outParam.setComCountryCode(param.getInSysSite());
                outParam.setComShopNameSimple(param.getInSysShopsName());
                outParam.setComShopPlatName(param.getInPlatform());
            }

            outParam.setComWarehouseType(param.getOutWarehouseType());
            outParam.setComWarehouseName(param.getOutWarehouseName());
            outParam.setPackStoreHouseType(param.getInWarehouseType());
            outParam.setPackStoreHouseName(param.getInWarehouseName());
            outParam.setBillType(param.getOperateType());

            if(CollectionUtil.isNotEmpty(param.getDetailList())){
                for(OverseasOutWarehouseDetailParam detailParam:param.getDetailList()){
                    String packageNumName="Box "+String.valueOf(detailParam.getPackageNum())+" - "+"QTY";

                    EbmsOverseasOutWarehouseDetailBoxInfoParam boxInfoParam=new EbmsOverseasOutWarehouseDetailBoxInfoParam();
                    boxInfoParam.setPackCode(param.getOutOrder());
                    boxInfoParam.setPackDetBoxNum(detailParam.getPackageNum());
                    boxInfoParam.setPackDetBoxNumUpload(packageNumName);
                    boxInfoParam.setPackDetBoxType(detailParam.getPackBoxType());
                    boxInfoParam.setPackDetBoxLength(detailParam.getLength());
                    boxInfoParam.setPackDetBoxWidth(detailParam.getWidth());
                    boxInfoParam.setPackDetBoxHeight(detailParam.getHeight());
                    boxInfoParam.setPackDetBoxWeight(detailParam.getWeight());
                    boxInfoParam.setPackDetBoxCode(detailParam.getPackageBarCode());
                    boxInfoParam.setPackDetBoxVolume(detailParam.getVolume());
                    boxInfoList.add(boxInfoParam);

                   EbmsOverseasOutWarehouseDetailParam outDetailParam=new EbmsOverseasOutWarehouseDetailParam();
                   outDetailParam.setPackCode(param.getOutOrder());
                   outDetailParam.setSku(detailParam.getSku());
                   outDetailParam.setQuantity(detailParam.getQuantity());
                   outDetailParam.setMateCode(detailParam.getMaterialCode());

                   outDetailParam.setPackDetBoxNum(detailParam.getPackageNum());
                   outDetailParam.setPackDetBoxNumUpload(packageNumName);
                   outDetailParam.setFnSKU(detailParam.getFnSku());
                   outDetailParam.setPackDetBoxCode(detailParam.getPackageBarCode());
                   outDetailParam.setDepName(detailParam.getDepartment());
                   outDetailParam.setTeamName(detailParam.getTeam());
                   outDetailParam.setPerName(detailParam.getNeedsUser());
                   detailList.add(outDetailParam);

                }
                outParam.setTbLogisticsPackingListDet2List(detailList);
                outParam.setTbLogisticsPackingListDet1List(boxInfoList);
            }

            //新建出货清单
            log.info("海外仓出库任务推送EBMS创建出货清单开始！[{}]", JSON.toJSONString(outParam));
            ResponseData responseData = ebmsOverseaConsumer.createShipmentList(outParam);

            if(ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){

                //推送状态更新
                LambdaUpdateWrapper<OverseasWarehouseManageRecord> uw=new LambdaUpdateWrapper<>();
                    uw.eq(OverseasWarehouseManageRecord::getOutOrder,param.getOutOrder())
                        .set(OverseasWarehouseManageRecord::getSyncEbmsStatus,BaseSyncStatusEnum.SUCCESS.getCode())
                        .set(OverseasWarehouseManageRecord::getSyncEbmsTime,new Date());
                    recordService.update(null,uw);

            }else{

                LambdaUpdateWrapper<OverseasWarehouseManageRecord> uw=new LambdaUpdateWrapper<>();
                uw.eq(OverseasWarehouseManageRecord::getOutOrder,param.getOutOrder())
                        .set(OverseasWarehouseManageRecord::getSyncEbmsStatus, BaseSyncStatusEnum.ERROR.getCode())
                        .set(OverseasWarehouseManageRecord::getSyncEbmsTime,new Date());
                recordService.update(null,uw);

                return responseData;
            }

            return ResponseData.success("海外仓出库任务推送EBMS创建出货清单成功！");
        }catch(Exception e){
            log.error("海外仓出库任务推送EBMS创建出货清单失败，异常信息[{}]",e.getMessage());
            return ResponseData.error("海外仓出库任务推送EBMS创建出货清单失败，异常信息："+e.getMessage());
        }

    }

    /**
     * 推送EBMS创建出货清单
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData rePushShipmentList(){

        OverseasOutWarehouseParam param = new OverseasOutWarehouseParam();

        LambdaQueryWrapper<OverseasWarehouseManageRecord> qw = new LambdaQueryWrapper<>();
        qw.eq(OverseasWarehouseManageRecord::getSyncEbmsStatus,BaseSyncStatusEnum.ERROR.getCode())
          .eq(OverseasWarehouseManageRecord::getOperateType,OperateTypeEnum.OVERSEAS_TO_AMAZON.getName());

        List<OverseasWarehouseManageRecord> recordList = recordService.list(qw);

        log.info("海外仓出库任务推送EBMS创建出货清单开始！");
        for(OverseasWarehouseManageRecord rd:recordList){


            LambdaQueryWrapper<OverseasOutWarehouse> outWrapper = new LambdaQueryWrapper<>();
            outWrapper.eq(OverseasOutWarehouse::getOutOrder,rd.getOutOrder());
            OverseasOutWarehouse outWarehouse=mapper.selectOne(outWrapper);

            if (ObjectUtil.isNotEmpty(outWarehouse)){
                LambdaQueryWrapper<OverseasOutWarehouseDetail> outDetailWrapper = new LambdaQueryWrapper<>();
                outDetailWrapper.eq(OverseasOutWarehouseDetail::getOutOrder,rd.getOutOrder());
                List<OverseasOutWarehouseDetail> outWarehouseDetails = overseasOutWarehouseDetailService.list(outDetailWrapper);

                List<OverseasOutWarehouseDetailParam> detailParams = new ArrayList<>();
                BeanUtils.copyProperties(outWarehouse,param);

                if (CollectionUtil.isNotEmpty(outWarehouseDetails)) {
                    for (OverseasOutWarehouseDetail overseas : outWarehouseDetails) {
                        OverseasOutWarehouseDetailParam ent=new OverseasOutWarehouseDetailParam();
                        BeanUtils.copyProperties(overseas,ent);
                        detailParams.add(ent);
                    }
                }

                param.setDetailList(detailParams);

                ResponseData responseData = createShipmentList(param, true);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                    log.error("海外仓出库单号[{}]，推送EBMS创建出货清单失败！信息[{}]",param.getOutOrder(),responseData.getMessage());
                }
            }
        }
        return ResponseData.success("海外仓出库任务推送EBMS创建出货清单成功！");
    }

    /**
     * 海外仓库存管理帐存处理
     * @param param
     */
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData outWarehouseReduceInventory(OverseasOutWarehouseParam param,OverseasOutWarehouseDetailParam detailParam) throws Exception{

        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;

        if(ObjectUtil.isNotEmpty(detailParam)){
                //当前账存信息
                LambdaQueryWrapper<OverseasWarehouseManage> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(OverseasWarehouseManage::getPlatform,param.getPlatform())
                        .eq(OverseasWarehouseManage::getSysShopsName,param.getSysShopsName())
                        .eq(OverseasWarehouseManage::getSysSite,param.getSysSite())
                        .eq(OverseasWarehouseManage::getWarehouseName,param.getOutWarehouseName())
                        .eq(OverseasWarehouseManage::getSku,detailParam.getSku())
                        .likeLeft(OverseasWarehouseManage::getFnSku, detailParam.getFnSku());
                OverseasWarehouseManage qw=overseasWarehouseManageService.getOne(queryWrapper);

                //海外仓库存管理->库存减少
                if(ObjectUtil.isNotEmpty(qw) && ObjectUtil.isNotNull(qw)){
                    //现账存数量
                    BigDecimal nowInventoryQuantity = qw.getInventoryQuantity().subtract(detailParam.getQuantity());
                    LambdaUpdateWrapper<OverseasWarehouseManage> warehouseWrapper=new LambdaUpdateWrapper<>();
                    warehouseWrapper.eq(OverseasWarehouseManage::getPlatform,param.getPlatform())
                            .eq(OverseasWarehouseManage::getSysShopsName,param.getSysShopsName())
                            .eq(OverseasWarehouseManage::getSysSite,param.getSysSite())
                            .eq(OverseasWarehouseManage::getWarehouseName,param.getOutWarehouseName())
                            .eq(OverseasWarehouseManage::getSku,detailParam.getSku())
                            .likeLeft(OverseasWarehouseManage::getFnSku,detailParam.getFnSku())
                            .set(OverseasWarehouseManage::getInventoryQuantity,nowInventoryQuantity);
                    overseasWarehouseManageService.update(null,warehouseWrapper);

                    //插入操作记录表
                    OverseasWarehouseManageRecord record = new OverseasWarehouseManageRecord();
                    BeanUtils.copyProperties(detailParam,record);
                    record.setParentId(qw.getId());
                    record.setPlatform(qw.getPlatform());
                    record.setSysShopsName(qw.getSysShopsName());
                    record.setSysSite(qw.getSysSite());
                    record.setWarehouseName(param.getOutWarehouseName());
                    record.setOutOrder(param.getOutOrder());
                    record.setOutOrgName(param.getOutOrgName());
                    record.setInOrgName(param.getInOrgName());
                    if("亚马逊仓".equals(param.getInWarehouseType())){
                        record.setOperateType(OperateTypeEnum.OVERSEAS_TO_AMAZON.getName());
                        record.setOperateTypeDetail(OutBusinessTypeEnum.OVERSEAS_TO_AMAZON.getName());
                    }else{
                        record.setOperateType(OperateTypeEnum.OVERSEAS_TO_WALMART.getName());
                        record.setOperateTypeDetail(OutBusinessTypeEnum.OVERSEAS_TO_WALMART.getName());
                        record.setInWarehouseName(param.getInWarehouseName());
                    }
                    record.setBusinessType(OverseasBusinessTypeEnum.OUT.getName()); //出库
                    //原账存数量
                    record.setInventoryQuantity(qw.getInventoryQuantity());
                    record.setChangeInventoryQuantity(detailParam.getQuantity());
                    record.setNowInventoryQuantity(nowInventoryQuantity);
                    record.setCreateTime(new Date());
                    record.setCreateUser(accountAndName);
                    record.setOutOrg(param.getOutOrg());
                    record.setInOrg(param.getInOrg());
                    record.setComeQuantity(qw.getComeQuantity());
                    record.setChangeComeQuantity(BigDecimal.ZERO);
                    record.setNowComeQuantity(qw.getComeQuantity());
                    record.setIsChangeOrg("0");
                    record.setIsChangeMaterialCode("0");
                    record.setRemark(param.getRemark());
                    //插入操作记录表
                    recordService.save(record);

                    //处理库龄报表出库
                    List<OverseasWarehouseAge> ageOutParam = new ArrayList<>();
                    OverseasWarehouseAge ageOut = new OverseasWarehouseAge();
                    ageOut.setPlatform(record.getPlatform());
                    ageOut.setSysShopsName(record.getSysShopsName());
                    ageOut.setSysSite(record.getSysSite());
                    ageOut.setWarehouseName(record.getWarehouseName());
                    ageOut.setFnSku(record.getFnSku());
                    ageOut.setSku(record.getSku());
                    ageOut.setMaterialCode(record.getMaterialCode());
                    ageOut.setInventoryQuantity(record.getChangeInventoryQuantity());
//                    ageOut.setRemark(record.getOutOrder() + ":" + record.getChangeInventoryQuantity());
                    ageOutParam.add(ageOut);
                    ageService.batchAgeOut(ageOutParam);
                } else{
                    log.error("该海外仓没有对应SKU可以出库！");
                    return ResponseData.error("该海外仓没有对应SKU可以出库！");
                }

        }
        return ResponseData.success();
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData importExcel(OverseasOutWarehouseParam param, MultipartFile file, List<String> departmentList, List<String> teamList) {
        log.info("导入Excel处理开始");

        //新建输入校验
        if(StrUtil.isEmpty(param.getPlatform()) || StrUtil.isEmpty(param.getSysShopsName()) || StrUtil.isEmpty(param.getSysSite()) || StrUtil.isEmpty(param.getOutWarehouseType()) || StrUtil.isEmpty(param.getOutWarehouseName())
                || StrUtil.isEmpty(param.getInPlatform()) || StrUtil.isEmpty(param.getInSysShopsName()) || StrUtil.isEmpty(param.getInSysSite()) || StrUtil.isEmpty(param.getInWarehouseType()) || StrUtil.isEmpty(param.getInWarehouseName())){
            return ResponseData.error("出货平台、出货账号、出货站点、出货仓类型、出货仓名称、收货平台、收货账号、收货站点、收货仓类型、收货仓名称不能为空！");
        }

        if("Amazon".equals(param.getInPlatform())){
            if(!"亚马逊仓".equals(param.getInWarehouseType())){
                return ResponseData.error("收货类型须为亚马逊仓！");
            }
            if(!"亚马逊仓".equals(param.getInWarehouseName())){
                return ResponseData.error("收货名称须为亚马逊仓！");
            }
        }

        if("Walmart".equals(param.getInPlatform())){
            if("亚马逊仓".equals(param.getInWarehouseType())){
                return ResponseData.error("收货类型不能为亚马逊仓！");
            }
            if("亚马逊仓".equals(param.getInWarehouseName())){
                return ResponseData.error("收货名称不能为亚马逊仓！");
            }
        }

        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OverseasOutWarehouseDetailResult>();
            EasyExcel.read(buffer, OverseasOutWarehouseDetailResult.class, listener).sheet()
                    .doRead();

            List<OverseasOutWarehouseDetailResult> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }

            List<OverseasOutWarehouseDetailResult> errorList = new ArrayList<>();
            this.validation(param, dataList, errorList, departmentList, teamList);

            //返回上传出货数据集合
            if(CollectionUtil.isNotEmpty(dataList)){
                if(CollectionUtil.isNotEmpty(errorList)){
                    String fileName = this.dealImportErrorList(dataList);
                    //部分导入成功
                    return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败！", fileName);
                }
                return ResponseData.success(dataList);
            }
            return ResponseData.success("导入失败！导入正常数据为空！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入新建出库任务数据校验处理
     * @param param
     * @param dataList
     * @param errorList
     * @param departmentList
     * @param teamList
     * @return
     */
    private List<OverseasOutWarehouseDetailResult> validation(OverseasOutWarehouseParam param,List<OverseasOutWarehouseDetailResult> dataList,
                                                              List<OverseasOutWarehouseDetailResult> errorList,
                                                              List<String> departmentList,
                                                              List<String> teamList) {

        Iterator<OverseasOutWarehouseDetailResult> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            OverseasOutWarehouseDetailResult result = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(result.getQuantity() == null || BigDecimal.ZERO.equals(result.getQuantity())){
                validInfo.append("数量不能为空或者0!");
            }

            if (result.getPackageNum() == null
                    || result.getFnSku() == null
                    || result.getSku() == null
                    || result.getPackBoxType() == null
                    || result.getNeedsUser() == null
            ) {
                //不为空校验
                validInfo.append("箱号、FNSKU、SKU、箱型、需求人员为必填项！");
            } else {
                //1-1.请求EBMS箱规格信息
                ResponseData responseData = getBoxInfoByBoxType(result.getPackBoxType());
                if (ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())) {
                    Object boxInfo = getBoxInfoByBoxType(result.getPackBoxType()).getData();
                    BoxInfoResult boxInfoResult = new ObjectMapper().convertValue(boxInfo, BoxInfoResult.class);
                    result.setLength(boxInfoResult.getPackDetBoxLength());
                    result.setHeight(boxInfoResult.getPackDetBoxHeight());
                    result.setWidth(boxInfoResult.getPackDetBoxWidth());
                    result.setPackBoxType(boxInfoResult.getPackDetBoxType());
                    result.setVolume(boxInfoResult.getVolume());

                    //根据维度查询海外仓账存信息
                    /*QueryWrapper<OverseasWarehouseManage> queryWrapper = new QueryWrapper();
                    queryWrapper.eq("PLATFORM", param.getPlatform())
                            .eq("SYS_SHOPS_NAME", param.getSysShopsName())
                            .eq("SYS_SITE", param.getSysSite())
                            .eq("WAREHOUSE_NAME", param.getOutWarehouseName())
                            .eq("SKU", result.getSku())
                            .likeLeft("FN_SKU", result.getFnSku());
                    OverseasWarehouseManage warehouseManage = overseasWarehouseManageService.getOne(queryWrapper);
                    if (ObjectUtil.isEmpty(warehouseManage) ) {
                        validInfo.append("该海外仓没有对应SKU可以出库！");
                    }else{
                        //根据SKU、FNSKU汇总不同库存的出库数
                        BigDecimal skuGroupQuantity=dataList.stream().filter(a -> a.getSku().equals(result.getSku())
                                && a.getFnSku().equals(result.getFnSku())).map(OverseasOutWarehouseDetailResult::getQuantity).reduce(BigDecimal.ZERO,BigDecimal::add);

                        //sku分组出库数量不能大于现有库存数量
                        if(skuGroupQuantity.compareTo(warehouseManage.getInventoryQuantity()) > 0){
                            validInfo.append("出库数量不能大于现有库存数量！");
                        }
                        result.setMaterialCode(warehouseManage.getMaterialCode());
                    }*/
                }

                //优先根据账号、站点、sku系统匹配事业部和Team，系统匹配不出则取Excel数据
                InvProductGalleryParam productParam = new InvProductGalleryParam();
                productParam.setSysShopsName(param.getSysShopsName());
                productParam.setSysSite(param.getSysSite());
                productParam.setSku(result.getSku());
                List<InvProductGalleryParam> productList = overseasWarehouseManageService.getInvProductGallery(productParam);
                if(CollectionUtil.isNotEmpty(productList)){
                    result.setDepartment(productList.get(0).getDepartment());
                    result.setTeam(productList.get(0).getTeam());
                } else{
                    //校验导入Excel的事业部和Team值
                    if (StringUtils.isNotBlank(result.getDepartment()) && !departmentList.contains(result.getDepartment())) {
                        validInfo.append("系统不存在此需求部门!");
                    }
                    if (StringUtils.isNotBlank(result.getTeam()) && !teamList.contains(result.getTeam())) {
                        validInfo.append("系统不存在此需求Team!");
                    }
                }
                if(StringUtils.isBlank(result.getDepartment())){
                    validInfo.append("系统不存在此需求部门且导入数据为空!");
                }
                if(StringUtils.isBlank(result.getTeam())){
                    validInfo.append("系统不存在此需求Team且导入数据为空!");
                }
            }
            if(StringUtils.isNotBlank(result.getUploadRemark())){
                result.setUploadRemark(null);//清空导入异常备注
            }
            if(StringUtils.isNotEmpty(validInfo)){
                result.setUploadRemark(validInfo.toString());
                errorList.add(result);
            }
        }
        return dataList;
    }

    private String dealImportErrorList(List<OverseasOutWarehouseDetailResult> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, OverseasOutWarehouseDetailResult.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("导入Excel异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入Excel异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    public ResponseData getBoxInfoByBoxType(String boxType) {
        ResponseData responseData = ebmsOverseaConsumer.getAllBoxInfo();
        if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
            return ResponseData.error("获取EBMS海外仓箱子信息失败");
        }
        JSONArray jsonArray = JSONObject.parseArray(responseData.getData().toString());
        for (Object obj : jsonArray) {
            BoxInfoResult boxInfo = JSONObject.parseObject(obj.toString(), BoxInfoResult.class);
            if(boxType.equals(boxInfo.getPackDetBoxType())){
                BigDecimal volume = boxInfo.getPackDetBoxLength().multiply(boxInfo.getPackDetBoxWidth()).multiply(boxInfo.getPackDetBoxHeight());
                boxInfo.setVolume(volume.divide(new BigDecimal(1000000), 4, RoundingMode.HALF_UP));
                return ResponseData.success(boxInfo);
            }
        }
        return null;
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
