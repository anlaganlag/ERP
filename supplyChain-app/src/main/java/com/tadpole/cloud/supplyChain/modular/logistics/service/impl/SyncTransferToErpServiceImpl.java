package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3CrossTransferItemParamMap;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3CrossTransferParamMap;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouseDetail;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManageRecord;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.BaseSyncStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OperateTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TransferBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ty
 * @description: 调用金蝶ERP实现类
 * @date: 2022/8/2
 */
@Service
@Slf4j
public class SyncTransferToErpServiceImpl implements ISyncTransferToErpService {

    @Autowired
    IOverseasWarehouseManageRecordService recordService;
    @Autowired
    IOverseasOutWarehouseService outWarehouseService;
    @Autowired
    IOverseasOutWarehouseDetailService outWarehouseDetailService;
    @Autowired
    IOverseasInWarehouseService inWarehouseService;
    @Autowired
    IOverseasInWarehouseDetailService inWarehouseDetailService;

    @Value("${k3webapi.acctid}")
    private String k3Acctid;
    @Value("${k3webapi.username}")
    private String k3Username;
    @Value("${k3webapi.appid}")
    private String k3Appid;
    @Value("${k3webapi.appsec}")
    private String k3Appsec;
    @Value("${k3webapi.serverurl}")
    private String k3Serverurl;

    private static String k3FormId="STK_TransferDirect";

    @DataSource(name = "logistics")
    @Override
    public ResponseData save(BigDecimal inId, BigDecimal outId, TransferBusinessTypeEnum transferBusinessType) {
        if(TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(transferBusinessType.getName()) && inId == null){
            return ResponseData.error("入库ID不能为空");
        }
        if(TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER.getName().equals(transferBusinessType.getName()) && inId == null){
            return ResponseData.error("入库ID不能为空");
        }
        if(TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(transferBusinessType.getName()) && outId == null){
            return ResponseData.error("出库ID不能为空");
        }
        if(TransferBusinessTypeEnum.OVERSEAS_TO_WALMART.getName().equals(transferBusinessType.getName()) && outId == null){
            return ResponseData.error("出库ID不能为空");
        }
        if(TransferBusinessTypeEnum.DIFF_SITE_CHANGE.getName().equals(transferBusinessType.getName())
                && outId == null
        ){
            return ResponseData.error("出库ID不能为空");
        }

        //封装保存接口数据
        JSONObject objectAll = new JSONObject();
        //直接调拨单主表信息
        K3CrossTransferParamMap k3Cross = new K3CrossTransferParamMap();
        //单据类型|默认值：ZJDB01_SYS 标准直接调拨单 关联K3【单据类型表】作业
        k3Cross.setFBillTypeId("ZJDB01_SYS");
        //业务类型|默认值：NORMAL 标准 枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务
        k3Cross.setFBizType("NORMAL");
        //调拨方向|默认值：GENERAL 普通 枚举值：GENERAL 普通，RETURN 退货
        k3Cross.setFTransferDirect("GENERAL");
        //调拨类型|默认值：OverOrgTransfer 跨组织调拨 枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多
        k3Cross.setFTransferBizType(transferBusinessType.getType());
        //调入货主类型|默认值：业务组织
        k3Cross.setFOwnerTypeIdHead("BD_OwnerOrg");

        //k3跨组织调拨单明细对象信息
        ArrayList<K3CrossTransferItemParamMap> itemList = new ArrayList<>();

        //亚马逊仓发海外仓（入库）
        OverseasWarehouseManageRecord manageRecord = null;
        OverseasWarehouseManageRecord manageRecord1 = null;
        if(TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(transferBusinessType.getName())
                || TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER.getName().equals(transferBusinessType.getName())){
            manageRecord = recordService.getById(inId);
            //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
            k3Cross.setFStockOutorgId(manageRecord.getOutOrg());
            //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerOutIdHead(manageRecord.getOutOrg());
            //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFStockOrgId(manageRecord.getInOrg());
            //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerIdHead(manageRecord.getInOrg());
            //日期|传入订单的发货日期
            k3Cross.setFDate(DateUtil.formatDateTime(manageRecord.getCreateTime()));
            //备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】
            String remark = StringUtils.isBlank(manageRecord.getRemark()) ? "亚马逊仓发海外仓" : "亚马逊仓发海外仓-MC备注：" + manageRecord.getRemark();
            k3Cross.setFNote(remark);
            //k3单据编号存亚马逊发海外仓单号
            k3Cross.setFBillNo(manageRecord.getInOrder());

            //明细
            K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
            k3CrossItem.setFBscBase(manageRecord.getSku());
            k3CrossItem.setFRowType("Standard");
            k3CrossItem.setFMaterialId(manageRecord.getMaterialCode());
            k3CrossItem.setFUnitId("Pcs");
            k3CrossItem.setFQty(manageRecord.getChangeInventoryQuantity());
            k3CrossItem.setFSrcStockId(manageRecord.getOutOrg());
            k3CrossItem.setFDestStockId(manageRecord.getWarehouseName());
            k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
            k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
            k3CrossItem.setFOwnerOutId(manageRecord.getOutOrg());
            k3CrossItem.setFOwnerId(manageRecord.getInOrg());
            k3CrossItem.setFBscTeam(manageRecord.getTeam());
            itemList.add(k3CrossItem);
        }

        //海外仓发亚马逊仓/沃尔玛（出库）
        if(TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(transferBusinessType.getName())
                || TransferBusinessTypeEnum.OVERSEAS_TO_WALMART.getName().equals(transferBusinessType.getName())){
            manageRecord = recordService.getById(outId);

            //拿到操作记录表的出库单号，获取出库主表
            QueryWrapper<OverseasOutWarehouse> outWarehouseWrapper = new QueryWrapper<>();
            outWarehouseWrapper.eq("OUT_ORDER", manageRecord.getOutOrder());
            OverseasOutWarehouse outWarehouse = outWarehouseService.getOne(outWarehouseWrapper);
            if(outWarehouse == null){
                return ResponseData.error("不存在海外仓出库管理主数据");
            }

            //拿到海外仓出库单号，获取海外仓出库明细
            QueryWrapper<OverseasOutWarehouseDetail> outWarehouseDetailWrapper = new QueryWrapper<>();
            outWarehouseDetailWrapper.eq("OUT_ORDER", outWarehouse.getOutOrder());
            List<OverseasOutWarehouseDetail> outWarehouseDetailList = outWarehouseDetailService.list(outWarehouseDetailWrapper);

            //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
            k3Cross.setFStockOutorgId(manageRecord.getOutOrg());
            //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerOutIdHead(manageRecord.getOutOrg());
            //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFStockOrgId(manageRecord.getInOrg());
            //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerIdHead(manageRecord.getInOrg());
            //日期|传入订单的发货日期
            k3Cross.setFDate(DateUtil.formatDateTime(manageRecord.getCreateTime()));
            //备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】
            String remark = StringUtils.isBlank(manageRecord.getRemark()) ? "海外仓发亚马逊仓" : "海外仓发亚马逊仓-MC备注：" + manageRecord.getRemark();
            k3Cross.setFNote(remark);
            //k3单据编号存海外仓出库单号
            k3Cross.setFBillNo(outWarehouse.getOutOrder());

            if(CollectionUtil.isNotEmpty(outWarehouseDetailList)){
                for(OverseasOutWarehouseDetail outWarehouseDetail : outWarehouseDetailList){
                    K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
                    k3CrossItem.setFBscBase(outWarehouseDetail.getSku());
                    k3CrossItem.setFRowType("Standard");
                    k3CrossItem.setFMaterialId(outWarehouseDetail.getMaterialCode());
                    k3CrossItem.setFUnitId("Pcs");
                    k3CrossItem.setFQty(outWarehouseDetail.getQuantity());
                    k3CrossItem.setFSrcStockId(outWarehouse.getOutWarehouseName());
                    k3CrossItem.setFOwnerOutId(manageRecord.getOutOrg());
                    k3CrossItem.setFOwnerId(manageRecord.getInOrg());
                    if(TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(transferBusinessType.getName())){
                        k3CrossItem.setFDestStockId(manageRecord.getInOrg());
                    }else{
                        k3CrossItem.setFDestStockId(manageRecord.getInWarehouseName());
                    }
                    k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFBscTeam(manageRecord.getTeam());
                    itemList.add(k3CrossItem);
                }
            }
        }

        //跨站点换标
        if(TransferBusinessTypeEnum.DIFF_SITE_CHANGE.getName().equals(transferBusinessType.getName())){
            manageRecord = recordService.getById(outId);

            //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
            k3Cross.setFStockOutorgId(manageRecord.getOutOrg());
            //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerOutIdHead(manageRecord.getOutOrg());
            //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFStockOrgId(manageRecord.getInOrg());
            //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerIdHead(manageRecord.getInOrg());
            //日期|传入订单的发货日期
            k3Cross.setFDate(DateUtil.formatDateTime(manageRecord.getCreateTime()));
            //备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】
            String remark = StringUtils.isBlank(manageRecord.getRemark()) ? "海外仓跨组织换标" : "海外仓跨组织换标-MC备注：" + manageRecord.getRemark();
            k3Cross.setFNote(remark);
            //k3单据编号传海外仓换标单号
            k3Cross.setFBillNo(manageRecord.getOutOrder());

            //明细
            K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
            k3CrossItem.setFBscBase(manageRecord.getSku());
            k3CrossItem.setFRowType("Standard");
            k3CrossItem.setFMaterialId(manageRecord.getMaterialCode());
            k3CrossItem.setFUnitId("Pcs");
            k3CrossItem.setFQty(manageRecord.getChangeInventoryQuantity());
            k3CrossItem.setFSrcStockId(manageRecord.getWarehouseName());
            k3CrossItem.setFOwnerOutId(manageRecord.getOutOrg());
            k3CrossItem.setFDestStockId(manageRecord.getInWarehouseName());
            k3CrossItem.setFOwnerId(manageRecord.getInOrg());
            k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
            k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
            k3CrossItem.setFBscTeam(manageRecord.getTeam());
            itemList.add(k3CrossItem);
        }

        //同站点换标
        if(TransferBusinessTypeEnum.SAME_SITE_CHANGE.getName().equals(transferBusinessType.getName())){
            manageRecord = recordService.getById(inId);
            manageRecord1 = recordService.getById(outId);

            //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
            k3Cross.setFStockOutorgId(manageRecord.getOutOrg());
            //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerOutIdHead(manageRecord.getOutOrg());
            //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFStockOrgId(manageRecord1.getInOrg());
            //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
            k3Cross.setFOwnerIdHead(manageRecord1.getInOrg());
            //日期|传入订单的发货日期
            k3Cross.setFDate(DateUtil.formatDateTime(manageRecord.getCreateTime()));
            //备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】
            String remark = StringUtils.isBlank(manageRecord.getRemark()) ? "海外同站点仓换标" : "海外仓同站点换标-MC备注：" + manageRecord.getRemark();
            k3Cross.setFNote(remark);
            //k3单据编号传海外仓换标单号
            k3Cross.setFBillNo(manageRecord.getInOrder());

            //明细
            K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
            k3CrossItem.setFBscBase(manageRecord.getSku());
            k3CrossItem.setFRowType("Standard");
            k3CrossItem.setFMaterialId(manageRecord.getMaterialCode());
            k3CrossItem.setFUnitId("Pcs");
            k3CrossItem.setFQty(manageRecord.getChangeInventoryQuantity());
            k3CrossItem.setFSrcStockId(manageRecord1.getWarehouseName());//manageRecord.getOutOrg()
            k3CrossItem.setFOwnerOutId(manageRecord.getOutOrg());
            k3CrossItem.setFDestStockId(manageRecord.getWarehouseName());//manageRecord1.getInOrg()
            k3CrossItem.setFOwnerId(manageRecord1.getInOrg());
            k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
            k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
            k3CrossItem.setFBscTeam(manageRecord.getTeam());
            itemList.add(k3CrossItem);
        }

        //审核中状态同步
        //调用k3跨组织调拨接口,保存时自动生成
        if(StrUtil.isNotEmpty(manageRecord.getOrgTransferBillNo())){
            //4、审核失败、需调用取消接口，重新走全部流程
            if(BaseSyncStatusEnum.AUDIT_ERROR.getCode().equals(manageRecord.getSyncErpStatus())){
                this.cancelAssign(manageRecord.getOrgTransferId(), manageRecord.getOrgTransferBillNo());
            }
            k3Cross.setFId(new BigDecimal(manageRecord.getOrgTransferId()));
            k3Cross.setFBillNo(manageRecord.getOrgTransferBillNo());
        }

        k3Cross.setFBillEntry(itemList);
        objectAll.put("Model", k3Cross);
        String jsonData = JSON.toJSONString(objectAll);

        try{
            //1、保存
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info( "调用k3直接调拨单保存接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.save(k3FormId, jsonData);
                log.info( "调用k3直接调拨单保存接口，响应参数[{}]", result);
                JSONObject resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){

                            //K3保存成功，更新入库
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                String fId = resultValue.getString("Id");
                                String fNumber = resultValue.getString("Number");

                                List<OverseasWarehouseManageRecord> updateList = new ArrayList<>();
                                if(inId != null){
                                    OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                                    inUpdateRecord.setId(inId);
                                    inUpdateRecord.setOrgTransferId(fId);
                                    inUpdateRecord.setOrgTransferBillNo(fNumber);
                                    inUpdateRecord.setSyncErpTime(new Date());
                                    inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
                                    updateList.add(inUpdateRecord);
                                }

                                if(outId != null){
                                    OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                                    outUpdateRecord.setId(outId);
                                    outUpdateRecord.setOrgTransferId(fId);
                                    outUpdateRecord.setOrgTransferBillNo(fNumber);
                                    outUpdateRecord.setSyncErpTime(new Date());
                                    outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.SUCCESS.getCode());
                                    updateList.add(outUpdateRecord);
                                }
                                recordService.updateBatchById(updateList);

                                //2、提交
                                ResponseData submitResponseData = this.submit(fId, fNumber);
                                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(submitResponseData.getCode())){
                                    return submitResponseData;
                                }

                                List<OverseasWarehouseManageRecord> updateSubmitList = new ArrayList<>();
                                if(inId != null){
                                    OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                                    inUpdateRecord.setId(inId);
                                    inUpdateRecord.setSyncErpTime(new Date());
                                    inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.SUBMIT.getCode());
                                    updateSubmitList.add(inUpdateRecord);
                                }

                                if(outId != null){
                                    OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                                    outUpdateRecord.setId(outId);
                                    outUpdateRecord.setSyncErpTime(new Date());
                                    outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.SUBMIT.getCode());
                                    updateSubmitList.add(outUpdateRecord);
                                }
                                recordService.updateBatchById(updateSubmitList);

                                //3、审核
                                ResponseData auditResponseData = this.audit(fId, fNumber);
                                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(auditResponseData.getCode())) {
                                    List<OverseasWarehouseManageRecord> auditSubmitList = new ArrayList<>();
                                    if(inId != null){
                                        OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                                        inUpdateRecord.setId(inId);
                                        inUpdateRecord.setSyncErpTime(new Date());
                                        inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.AUDIT_ERROR.getCode());
                                        auditSubmitList.add(inUpdateRecord);
                                    }

                                    if(outId != null){
                                        OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                                        outUpdateRecord.setId(outId);
                                        outUpdateRecord.setSyncErpTime(new Date());
                                        outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.AUDIT_ERROR.getCode());
                                        auditSubmitList.add(outUpdateRecord);
                                    }
                                    recordService.updateBatchById(auditSubmitList);
                                    return auditResponseData;
                                }

                                List<OverseasWarehouseManageRecord> auditSubmitList = new ArrayList<>();
                                if(inId != null){
                                    OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                                    inUpdateRecord.setId(inId);
                                    inUpdateRecord.setSyncErpTime(new Date());
                                    inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.AUDIT_SUCCESS.getCode());
                                    auditSubmitList.add(inUpdateRecord);
                                }

                                if(outId != null){
                                    OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                                    outUpdateRecord.setId(outId);
                                    outUpdateRecord.setSyncErpTime(new Date());
                                    outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.AUDIT_SUCCESS.getCode());
                                    auditSubmitList.add(outUpdateRecord);
                                }
                                recordService.updateBatchById(auditSubmitList);
                                return ResponseData.success();
                            } else {
                                //失败，更新状态
                                List<OverseasWarehouseManageRecord> updateList = new ArrayList<>();
                                if(inId != null){
                                    OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                                    inUpdateRecord.setId(inId);
                                    inUpdateRecord.setSyncErpTime(new Date());
                                    inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                                    updateList.add(inUpdateRecord);
                                }

                                if(outId != null){
                                    OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                                    outUpdateRecord.setId(outId);
                                    outUpdateRecord.setSyncErpTime(new Date());
                                    outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                                    updateList.add(outUpdateRecord);
                                }
                                recordService.updateBatchById(updateList);
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3直接调拨单保存接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }
            }else{
                //失败，更新状态
                List<OverseasWarehouseManageRecord> updateList = new ArrayList<>();
                if(inId != null){
                    OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                    inUpdateRecord.setId(inId);
                    inUpdateRecord.setSyncErpTime(new Date());
                    inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                    updateList.add(inUpdateRecord);
                }

                if(outId != null){
                    OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                    outUpdateRecord.setId(outId);
                    outUpdateRecord.setSyncErpTime(new Date());
                    outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                    updateList.add(outUpdateRecord);
                }
                recordService.updateBatchById(updateList);
                return ResponseData.error("调用k3直接调拨单鉴权接口失败!");
            }
        }catch(Exception e){
            List<OverseasWarehouseManageRecord> updateList = new ArrayList<>();
            if(inId != null){
                OverseasWarehouseManageRecord inUpdateRecord = new OverseasWarehouseManageRecord();
                inUpdateRecord.setId(inId);
                inUpdateRecord.setSyncErpTime(new Date());
                inUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                updateList.add(inUpdateRecord);
            }

            if(outId != null){
                OverseasWarehouseManageRecord outUpdateRecord = new OverseasWarehouseManageRecord();
                outUpdateRecord.setId(outId);
                outUpdateRecord.setSyncErpTime(new Date());
                outUpdateRecord.setSyncErpStatus(BaseSyncStatusEnum.ERROR.getCode());
                updateList.add(outUpdateRecord);
            }
            recordService.updateBatchById(updateList);
            log.error("调用k3直接调拨单保存接口异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("调用k3直接调拨单保存接口异常");
        }
        return ResponseData.error("调用K3组织调拨失败！");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData submit(String orgTransferId, String orgTransferBillNo) {
        try{
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String jsonData = "{\"Numbers\":[\""+orgTransferBillNo+"\"],\"Ids\":\""+orgTransferId+"\"}";
                log.info( "调用k3直接调拨单提交接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.submit(k3FormId, jsonData);
                log.info( "调用k3直接调拨单提交接口，响应参数[{}]", result);
                JSONObject  resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                return ResponseData.success();
                            } else{
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3直接调拨单提交接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            log.error("调用k3直接调拨单提交接口异常，异常信息:[{}]", e.getMessage());
        }
        return ResponseData.error("调用k3直接调拨单提交接口失败");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData audit(String orgTransferId, String orgTransferBillNo) {
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(api));
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String jsonData = "{\"Numbers\":[\""+orgTransferBillNo+"\"],\"Ids\":\""+orgTransferId+"\"}";
                log.info( "调用k3直接调拨单审核接口，请求参数[{}]", jsonData);
                String result = api.audit(k3FormId, jsonData);
                log.info( "调用k3直接调拨单审核接口，响应参数[{}]", result);
                JSONObject  resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                return ResponseData.success();
                            } else{
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3直接调拨单审核接口失败！失败详情：" + errors);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            log.error("调用k3直接调拨单审核接口异常，异常信息:[{}]", e.getMessage());
        }
        return ResponseData.error("调用k3直接调拨单审核接口失败");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData cancelAssign(String orgTransferId, String orgTransferBillNo) {
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(api));
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String jsonData = "{\"Numbers\":[\""+orgTransferBillNo+"\"],\"Ids\":\""+orgTransferId+"\"}";
                log.info( "调用k3直接调拨单取消接口，请求参数[{}]", jsonData);
                String result = api.cancelAssign(k3FormId, jsonData);
                log.info( "调用k3直接调拨单取消接口，响应参数[{}]", result);
                JSONObject  resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.撤销同步返回状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                return ResponseData.success();
                            }else{
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3直接调拨单取消接口失败！失败详情：" + errors);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            log.error("调用k3直接调拨单取消接口异常，异常信息:[{}]", e.getMessage());
        }
        return ResponseData.error("调用k3直接调拨单取消接口失败");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData overseasOutSyncERP(String outOrder, TransferBusinessTypeEnum typeEnum) {
        //封装保存接口数据
        JSONObject objectAll = new JSONObject();
        //直接调拨单主表信息
        K3CrossTransferParamMap k3Cross = new K3CrossTransferParamMap();
        //单据类型|默认值：ZJDB01_SYS 标准直接调拨单 关联K3【单据类型表】作业
        k3Cross.setFBillTypeId("ZJDB01_SYS");
        //业务类型|默认值：NORMAL 标准 枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务
        k3Cross.setFBizType("NORMAL");
        //调拨方向|默认值：GENERAL 普通 枚举值：GENERAL 普通，RETURN 退货
        k3Cross.setFTransferDirect("GENERAL");
        //调拨类型|默认值：OverOrgTransfer 跨组织调拨 枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多
        k3Cross.setFTransferBizType(typeEnum.getType());
        //调入货主类型|默认值：业务组织
        k3Cross.setFOwnerTypeIdHead("BD_OwnerOrg");

        //k3跨组织调拨单明细对象信息
        ArrayList<K3CrossTransferItemParamMap> itemList = new ArrayList<>();

        //根据出库单号，获取操作记录数据
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("MATERIAL_CODE, SUM(CHANGE_INVENTORY_QUANTITY) AS CHANGE_INVENTORY_QUANTITY, MAX(SKU) AS SKU, " +
                "MAX(OUT_ORG) AS OUT_ORG, MAX(IN_ORG) AS IN_ORG, MIN(CREATE_TIME) AS CREATE_TIME, MAX(REMARK) AS REMARK, MAX(WAREHOUSE_NAME) AS WAREHOUSE_NAME, MAX(TEAM) AS TEAM, " +
                "MAX(IN_WAREHOUSE_NAME) AS IN_WAREHOUSE_NAME, MAX(ORG_TRANSFER_BILL_NO) AS ORG_TRANSFER_BILL_NO, MAX(ORG_TRANSFER_ID) AS ORG_TRANSFER_ID, MAX(SYNC_ERP_STATUS ) AS SYNC_ERP_STATUS")
        .eq("OUT_ORDER", outOrder).groupBy("MATERIAL_CODE");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("不存在海外库存管理操作记录数据");
        }

        //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
        k3Cross.setFStockOutorgId(recordList.get(0).getOutOrg());
        //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
        k3Cross.setFOwnerOutIdHead(recordList.get(0).getOutOrg());
        //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
        k3Cross.setFStockOrgId(recordList.get(0).getInOrg());
        //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
        k3Cross.setFOwnerIdHead(recordList.get(0).getInOrg());
        //日期|传入订单的发货日期
        k3Cross.setFDate(DateUtil.formatDateTime(recordList.get(0).getCreateTime()));
        //备注|存放订单编号platformOrderId字段值，页面显示为【平台订单编号】
        String remark = StringUtils.isBlank(recordList.get(0).getRemark()) ? typeEnum.getName() : typeEnum.getName() + "-MC备注：" + recordList.get(0).getRemark();
        k3Cross.setFNote(remark);
        //k3单据编号存海外仓出库单号
        k3Cross.setFBillNo(outOrder);

        if(CollectionUtil.isNotEmpty(recordList)){
            for(OverseasWarehouseManageRecord record : recordList){
                K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
                k3CrossItem.setFBscBase(record.getSku());
                k3CrossItem.setFRowType("Standard");
                k3CrossItem.setFMaterialId(record.getMaterialCode());
                k3CrossItem.setFUnitId("Pcs");
                k3CrossItem.setFQty(record.getChangeInventoryQuantity());
                k3CrossItem.setFSrcStockId(record.getWarehouseName());
                k3CrossItem.setFOwnerOutId(record.getOutOrg());
                k3CrossItem.setFOwnerId(record.getInOrg());
                if(TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(typeEnum.getName())){
                    k3CrossItem.setFDestStockId(record.getInOrg());
                }else{
                    k3CrossItem.setFDestStockId(record.getInWarehouseName());
                }
                k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
                k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
                k3CrossItem.setFBscTeam(record.getTeam());
                itemList.add(k3CrossItem);
            }
        }

        //审核中状态同步
        //调用k3跨组织调拨接口,保存时自动生成
        if(StrUtil.isNotEmpty(recordList.get(0).getOrgTransferBillNo())){
            //4、审核失败、需调用取消接口，重新走全部流程
            if(BaseSyncStatusEnum.AUDIT_ERROR.getCode().equals(recordList.get(0).getSyncErpStatus())){
                this.cancelAssign(recordList.get(0).getOrgTransferId(), recordList.get(0).getOrgTransferBillNo());
            }
            k3Cross.setFId(new BigDecimal(recordList.get(0).getOrgTransferId()));
            k3Cross.setFBillNo(recordList.get(0).getOrgTransferBillNo());
        }

        k3Cross.setFBillEntry(itemList);
        objectAll.put("Model", k3Cross);
        String jsonData = JSON.toJSONString(objectAll);

        try{
            //1、保存
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info( "海外仓出库调用k3直接调拨单保存接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.save(k3FormId, jsonData);
                log.info( "海外仓出库调用k3直接调拨单保存接口，响应参数[{}]", result);
                JSONObject resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){

                            //K3保存成功，更新入库
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                String fId = resultValue.getString("Id");
                                String fNumber = resultValue.getString("Number");

                                LambdaUpdateWrapper<OverseasWarehouseManageRecord> saveRecordWrapper = new LambdaUpdateWrapper<>();
                                saveRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                                        .set(OverseasWarehouseManageRecord :: getOrgTransferId, fId)
                                        .set(OverseasWarehouseManageRecord :: getOrgTransferBillNo, fNumber)
                                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                                recordService.update(null, saveRecordWrapper);

                                //2、提交
                                ResponseData submitResponseData = this.submit(fId, fNumber);
                                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(submitResponseData.getCode())){
                                    return submitResponseData;
                                }
                                LambdaUpdateWrapper<OverseasWarehouseManageRecord> submitRecordWrapper = new LambdaUpdateWrapper<>();
                                submitRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUBMIT.getCode());
                                recordService.update(null, submitRecordWrapper);

                                //3、审核
                                ResponseData auditResponseData = this.audit(fId, fNumber);
                                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(auditResponseData.getCode())) {
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                    auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_ERROR.getCode());
                                    recordService.update(null, auditRecordWrapper);
                                    return auditResponseData;
                                }
                                LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_SUCCESS.getCode());
                                recordService.update(null, auditRecordWrapper);
                                return ResponseData.success();
                            } else {
                                //失败，更新状态
                                LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                                errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                                recordService.update(null, errorRecordWrapper);
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("海外仓出库调用k3直接调拨单保存接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }
            }else{
                //失败，更新状态
                LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                recordService.update(null, errorRecordWrapper);
                return ResponseData.error("海外仓出库调用k3直接调拨单鉴权接口失败!");
            }
        }catch(Exception e){
            LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
            errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOutOrder, outOrder)
                    .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                    .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
            recordService.update(null, errorRecordWrapper);
            log.error("海外仓出库调用k3直接调拨单保存接口异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("海外仓出库调用k3直接调拨单保存接口异常" + e.getMessage());
        }
        return ResponseData.error("海外仓出库调用K3组织调拨失败！");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData overseasFbaInSyncERP(List<BigDecimal> inIdList, TransferBusinessTypeEnum typeEnum) {
        //封装保存接口数据
        JSONObject objectAll = new JSONObject();

        //根据操作记录ID，获取单据头数据
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("OUT_ORG","IN_ORG", "ORG_TRANSFER_ID", "ORG_TRANSFER_BILL_NO")
                .eq("OPERATE_TYPE", OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                .in("ID", inIdList)
                .groupBy("OUT_ORG","IN_ORG", "ORG_TRANSFER_ID", "ORG_TRANSFER_BILL_NO");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("不存在海外库存管理操作记录数据");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord record : recordList) {
            try{
                //直接调拨单主表信息
                K3CrossTransferParamMap k3Cross = new K3CrossTransferParamMap();
                //单据类型|默认值：ZJDB01_SYS 标准直接调拨单 关联K3【单据类型表】作业
                k3Cross.setFBillTypeId("ZJDB01_SYS");
                //业务类型|默认值：NORMAL 标准 枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务
                k3Cross.setFBizType("NORMAL");
                //调拨方向|默认值：GENERAL 普通 枚举值：GENERAL 普通，RETURN 退货
                k3Cross.setFTransferDirect("GENERAL");
                //调拨类型|默认值：OverOrgTransfer 跨组织调拨 枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多
                if(record.getInOrg().equals(record.getOutOrg())){
                    k3Cross.setFTransferBizType(TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS.getType());
                } else {
                    k3Cross.setFTransferBizType(TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER.getType());
                }
                //调入货主类型|默认值：业务组织
                k3Cross.setFOwnerTypeIdHead("BD_OwnerOrg");
                //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
                k3Cross.setFStockOutorgId(record.getOutOrg());
                //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFOwnerOutIdHead(record.getOutOrg());
                //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFStockOrgId(record.getInOrg());
                //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFOwnerIdHead(record.getInOrg());
                //日期|传入订单的发货日期
                k3Cross.setFDate(DateUtil.formatDateTime(DateUtil.date()));

                //k3跨组织调拨单明细对象信息
                ArrayList<K3CrossTransferItemParamMap> itemList = new ArrayList<>();
                //根据操作记录ID和组织编码，获取明细数据
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("OPERATE_TYPE", OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                        .eq("OUT_ORG", record.getOutOrg())
                        .eq("IN_ORG", record.getInOrg())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), "ORG_TRANSFER_ID", record.getOrgTransferId())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), "ORG_TRANSFER_BILL_NO", record.getOrgTransferBillNo())
                        .in("ID", inIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
                    k3CrossItem.setFBscBase(detailRecord.getSku());
                    k3CrossItem.setFRowType("Standard");
                    k3CrossItem.setFMaterialId(detailRecord.getMaterialCode());
                    k3CrossItem.setFUnitId("Pcs");
                    k3CrossItem.setFQty(detailRecord.getChangeInventoryQuantity());
                    k3CrossItem.setFSrcStockId(detailRecord.getOutOrg());
                    k3CrossItem.setFDestStockId(detailRecord.getWarehouseName());
                    k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFOwnerOutId(detailRecord.getInOrg());
                    k3CrossItem.setFOwnerId(detailRecord.getInOrg());
                    k3CrossItem.setFBscTeam(detailRecord.getTeam());
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "海外仓发亚马逊仓" : "海外仓发亚马逊仓-MC备注：" + detailRecord.getRemark();
                    k3CrossItem.setFNoteEntry(detailRecord.getInOrder() + remark);
                    itemList.add(k3CrossItem);
                }

                //审核中状态同步
                //调用k3跨组织调拨接口,保存时自动生成
                if(StrUtil.isNotEmpty(record.getOrgTransferBillNo())){
                    //4、审核失败、需调用取消接口，重新走全部流程
                    if(BaseSyncStatusEnum.AUDIT_ERROR.getCode().equals(record.getSyncErpStatus())){
                        this.cancelAssign(record.getOrgTransferId(), record.getOrgTransferBillNo());
                    }
                    k3Cross.setFId(new BigDecimal(record.getOrgTransferId()));
                    k3Cross.setFBillNo(record.getOrgTransferBillNo());
                }

                k3Cross.setFBillEntry(itemList);
                objectAll.put("Model", k3Cross);
                String jsonData = JSON.toJSONString(objectAll);

                //1、保存
                K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
                log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
                if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                    log.info( "亚马逊发海外仓入库调用k3直接调拨单保存接口，请求参数[{}]", jsonData);
                    String result = k3CloudApi.save(k3FormId, jsonData);
                    log.info( "亚马逊发海外仓入库调用k3直接调拨单保存接口，响应参数[{}]", result);
                    JSONObject resultJson = JSON.parseObject(result);
                    if (ObjectUtil.isNotNull(resultJson)) {
                        JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                        if(ObjectUtil.isNotNull(resultValue)){
                            JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                            if(ObjectUtil.isNotNull(resultResponse)){

                                //K3保存成功，更新入库
                                if("true".equals(resultResponse.getString("IsSuccess"))){
                                    String fId = resultValue.getString("Id");
                                    String fNumber = resultValue.getString("Number");

                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> saveRecordWrapper = new LambdaUpdateWrapper<>();
                                    saveRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, inIdList)
                                            .set(OverseasWarehouseManageRecord :: getOrgTransferId, fId)
                                            .set(OverseasWarehouseManageRecord :: getOrgTransferBillNo, fNumber)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                                    recordService.update(null, saveRecordWrapper);

                                    //2、提交
                                    ResponseData submitResponseData = this.submit(fId, fNumber);
                                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(submitResponseData.getCode())){
                                        errorMsg.append(submitResponseData.getMessage());
                                    }
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> submitRecordWrapper = new LambdaUpdateWrapper<>();
                                    submitRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, inIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUBMIT.getCode());
                                    recordService.update(null, submitRecordWrapper);

                                    //3、审核
                                    ResponseData auditResponseData = this.audit(fId, fNumber);
                                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(auditResponseData.getCode())) {
                                        LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                        auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                                                .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                                .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                                .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                                .in(OverseasWarehouseManageRecord :: getId, inIdList)
                                                .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                                .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_ERROR.getCode());
                                        recordService.update(null, auditRecordWrapper);
                                        errorMsg.append(auditResponseData.getMessage());
                                    }
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                    auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, inIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_SUCCESS.getCode());
                                    recordService.update(null, auditRecordWrapper);
                                } else {
                                    //失败，更新状态
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                                    errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, inIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                                    recordService.update(null, errorRecordWrapper);
                                    String errors = resultResponse.getString("Errors");
                                    errorMsg.append("亚马逊发海外仓入库调用k3直接调拨单保存接口失败，失败详情：" + errors);
                                }
                            }
                        }
                    }
                }else{
                    //失败，更新状态
                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                    errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                            .in(OverseasWarehouseManageRecord :: getId, inIdList)
                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                    recordService.update(null, errorRecordWrapper);
                    errorMsg.append("亚马逊发海外仓入库调用k3直接调拨单鉴权接口失败!");
                }
            }catch(Exception e){
                LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.AMAZON_TO_OVERSEAS.getName())
                        .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                        .in(OverseasWarehouseManageRecord :: getId, inIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                recordService.update(null, errorRecordWrapper);
                log.error("亚马逊发海外仓入库调用k3直接调拨单保存接口异常，异常信息[{}]",e.getMessage());
                errorMsg.append("亚马逊发海外仓入库调用k3直接调拨单保存接口异常" + e.getMessage());
            }
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData diffSiteChangeSyncERP(List<BigDecimal> transferIdList) {
        //封装保存接口数据
        JSONObject objectAll = new JSONObject();

        //根据操作记录ID，获取单据头数据
        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("OUT_ORG", "IN_ORG", "ORG_TRANSFER_ID", "ORG_TRANSFER_BILL_NO")
                .eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                .in("ID", transferIdList)
                .groupBy("OUT_ORG", "IN_ORG", "ORG_TRANSFER_ID", "ORG_TRANSFER_BILL_NO");
        List<OverseasWarehouseManageRecord> recordList = recordService.list(recordWrapper);
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("不存在海外库存管理操作记录数据");
        }
        StringBuffer errorMsg = new StringBuffer();
        for (OverseasWarehouseManageRecord record : recordList) {
            try{
                //直接调拨单主表信息
                K3CrossTransferParamMap k3Cross = new K3CrossTransferParamMap();
                //单据类型|默认值：ZJDB01_SYS 标准直接调拨单 关联K3【单据类型表】作业
                k3Cross.setFBillTypeId("ZJDB01_SYS");
                //业务类型|默认值：NORMAL 标准 枚举值：NORMAL 标准，CONSIGNMENT 寄售，OUTSOURCING委外，DRPTRANS 分销调拨，VMI VMI业务
                k3Cross.setFBizType("NORMAL");
                //调拨方向|默认值：GENERAL 普通 枚举值：GENERAL 普通，RETURN 退货
                k3Cross.setFTransferDirect("GENERAL");
                //调拨类型|默认值：OverOrgTransfer 跨组织调拨 枚举值：InnerOrgTransfer 组织内调拨，OverOrgTransfer 跨组织调拨，...，值比较多
                k3Cross.setFTransferBizType(TransferBusinessTypeEnum.DIFF_SITE_CHANGE.getType());
                //调入货主类型|默认值：业务组织
                k3Cross.setFOwnerTypeIdHead("BD_OwnerOrg");
                //调出库存组织|默认值：002 采购中心 关联K3【客户列表】作业
                k3Cross.setFStockOutorgId(record.getOutOrg());
                //调出货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFOwnerOutIdHead(record.getOutOrg());
                //调入库存组织编码（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFStockOrgId(record.getInOrg());
                //调入货主（又名：金蝶组织编码） 举例：1101 eBay_MK_ALL 关联K3【客户列表】作业
                k3Cross.setFOwnerIdHead(record.getInOrg());
                //日期|传入订单的发货日期
                k3Cross.setFDate(DateUtil.formatDateTime(DateUtil.date()));


                //k3跨组织调拨单明细对象信息
                ArrayList<K3CrossTransferItemParamMap> itemList = new ArrayList<>();
                //根据操作记录ID和组织编码，获取明细数据
                QueryWrapper<OverseasWarehouseManageRecord> detailRecordWrapper = new QueryWrapper<>();
                detailRecordWrapper.eq("OPERATE_TYPE", OperateTypeEnum.CHANGE.getName())
                        .eq("OUT_ORG", record.getOutOrg())
                        .eq("IN_ORG", record.getInOrg())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), "ORG_TRANSFER_ID", record.getOrgTransferId())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), "ORG_TRANSFER_BILL_NO", record.getOrgTransferBillNo())
                        .in("ID", transferIdList);
                List<OverseasWarehouseManageRecord> detailRecordList = recordService.list(detailRecordWrapper);
                for (OverseasWarehouseManageRecord detailRecord : detailRecordList) {
                    K3CrossTransferItemParamMap k3CrossItem = new K3CrossTransferItemParamMap();
                    k3CrossItem.setFBscBase(detailRecord.getSku());
                    k3CrossItem.setFRowType("Standard");
                    k3CrossItem.setFMaterialId(detailRecord.getMaterialCode());
                    k3CrossItem.setFUnitId("Pcs");
                    k3CrossItem.setFQty(detailRecord.getChangeInventoryQuantity());
                    k3CrossItem.setFSrcStockId(detailRecord.getWarehouseName());
                    k3CrossItem.setFOwnerOutId(detailRecord.getOutOrg());
                    k3CrossItem.setFDestStockId(detailRecord.getInWarehouseName());
                    k3CrossItem.setFOwnerId(detailRecord.getInOrg());
                    k3CrossItem.setFSrcStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFDestStockStatusId("KCZT01_SYS");
                    k3CrossItem.setFBscTeam(detailRecord.getTeam());
                    String remark = StringUtils.isBlank(detailRecord.getRemark()) ? "海外仓同物料跨组织换标" : "海外仓同物料跨组织换标-MC备注：" + detailRecord.getRemark();
                    k3CrossItem.setFNoteEntry(detailRecord.getOutOrder() + remark);
                    itemList.add(k3CrossItem);
                }

                //审核中状态同步
                //调用k3跨组织调拨接口,保存时自动生成
                if(StrUtil.isNotEmpty(record.getOrgTransferBillNo())){
                    //4、审核失败、需调用取消接口，重新走全部流程
                    if(BaseSyncStatusEnum.AUDIT_ERROR.getCode().equals(record.getSyncErpStatus())){
                        this.cancelAssign(record.getOrgTransferId(), record.getOrgTransferBillNo());
                    }
                    k3Cross.setFId(new BigDecimal(record.getOrgTransferId()));
                    k3Cross.setFBillNo(record.getOrgTransferBillNo());
                }

                k3Cross.setFBillEntry(itemList);
                objectAll.put("Model", k3Cross);
                String jsonData = JSON.toJSONString(objectAll);

                //1、保存
                K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
                log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
                if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                    log.info( "海外仓同物料跨组织换标调用k3直接调拨单保存接口，请求参数[{}]", jsonData);
                    String result = k3CloudApi.save(k3FormId, jsonData);
                    log.info( "海外仓同物料跨组织换标调用k3直接调拨单保存接口，响应参数[{}]", result);
                    JSONObject resultJson = JSON.parseObject(result);
                    if (ObjectUtil.isNotNull(resultJson)) {
                        JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                        if(ObjectUtil.isNotNull(resultValue)){
                            JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                            if(ObjectUtil.isNotNull(resultResponse)){

                                //K3保存成功，更新入库
                                if("true".equals(resultResponse.getString("IsSuccess"))){
                                    String fId = resultValue.getString("Id");
                                    String fNumber = resultValue.getString("Number");

                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> saveRecordWrapper = new LambdaUpdateWrapper<>();
                                    saveRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                                            .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                                            .set(OverseasWarehouseManageRecord :: getOrgTransferId, fId)
                                            .set(OverseasWarehouseManageRecord :: getOrgTransferBillNo, fNumber)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUCCESS.getCode());
                                    recordService.update(null, saveRecordWrapper);

                                    //2、提交
                                    ResponseData submitResponseData = this.submit(fId, fNumber);
                                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(submitResponseData.getCode())){
                                        errorMsg.append(submitResponseData.getMessage());
                                    }
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> submitRecordWrapper = new LambdaUpdateWrapper<>();
                                    submitRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                                            .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.SUBMIT.getCode());
                                    recordService.update(null, submitRecordWrapper);

                                    //3、审核
                                    ResponseData auditResponseData = this.audit(fId, fNumber);
                                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(auditResponseData.getCode())) {
                                        LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                        auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                                                .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                                                .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                                .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                                .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                                .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                                                .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                                .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_ERROR.getCode());
                                        recordService.update(null, auditRecordWrapper);
                                        errorMsg.append(auditResponseData.getMessage());
                                    }
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> auditRecordWrapper = new LambdaUpdateWrapper<>();
                                    auditRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                                            .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.AUDIT_SUCCESS.getCode());
                                    recordService.update(null, auditRecordWrapper);
                                } else {
                                    //失败，更新状态
                                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                                    errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                                            .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                                            .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                                    recordService.update(null, errorRecordWrapper);
                                    String errors = resultResponse.getString("Errors");
                                    errorMsg.append("海外仓同物料跨组织换标调用k3直接调拨单保存接口失败，失败详情：" + errors);
                                }
                            }
                        }
                    }
                }else{
                    //失败，更新状态
                    LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                    errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                            .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                            .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                            .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                            .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                            .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                            .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                            .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                    recordService.update(null, errorRecordWrapper);
                    errorMsg.append("海外仓同物料跨组织换标调用k3直接调拨单鉴权接口失败!");
                }
            }catch(Exception e){
                LambdaUpdateWrapper<OverseasWarehouseManageRecord> errorRecordWrapper = new LambdaUpdateWrapper<>();
                errorRecordWrapper.eq(OverseasWarehouseManageRecord :: getOperateType, OperateTypeEnum.CHANGE.getName())
                        .eq(OverseasWarehouseManageRecord :: getOutOrg, record.getOutOrg())
                        .eq(OverseasWarehouseManageRecord :: getInOrg, record.getInOrg())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferId()), OverseasWarehouseManageRecord :: getOrgTransferId, record.getOrgTransferId())
                        .eq(StringUtils.isNotEmpty(record.getOrgTransferBillNo()), OverseasWarehouseManageRecord :: getOrgTransferBillNo, record.getOrgTransferBillNo())
                        .in(OverseasWarehouseManageRecord :: getId, transferIdList)
                        .set(OverseasWarehouseManageRecord :: getSyncErpTime, DateUtil.date())
                        .set(OverseasWarehouseManageRecord :: getSyncErpStatus, BaseSyncStatusEnum.ERROR.getCode());
                recordService.update(null, errorRecordWrapper);
                log.error("海外仓同物料跨组织换标调用k3直接调拨单保存接口异常，异常信息[{}]",e.getMessage());
                errorMsg.append("海外仓同物料跨组织换标调用k3直接调拨单保存接口异常" + e.getMessage());
            }
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }
        return ResponseData.error(errorMsg.toString());
    }

    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb=new IdentifyInfo(){};
        //应用id
        identifyInfoAdb.setAppId(k3Appid);

        identifyInfoAdb.setAppSecret(k3Appsec);
        //账套id
        identifyInfoAdb.setdCID(k3Acctid);

        identifyInfoAdb.setUserName(k3Username);

        identifyInfoAdb.setServerUrl(k3Serverurl);
        return identifyInfoAdb;
    }
}
