package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouseDetail;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManageRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasChangeReportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseSyncErrorDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.BaseSyncStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OperateTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OverseasBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TransferBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasWarehouseManageRecordMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 海外仓库存管理操作记录服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@Service
@Slf4j
public class OverseasWarehouseManageRecordServiceImpl extends ServiceImpl<OverseasWarehouseManageRecordMapper, OverseasWarehouseManageRecord> implements IOverseasWarehouseManageRecordService {

    @Autowired
    private OverseasWarehouseManageRecordMapper mapper;
    @Autowired
    private IOverseasWarehouseManageService manageService;
    @Autowired
    private IOverseasWarehouseManageRecordService recordService;
    @Autowired
    private ISyncTransferToErpService syncTransferToErpService;
    @Autowired
    private IOverseasOutWarehouseService outWarehouseService;
    @Autowired
    private IOverseasOutWarehouseDetailService outWarehouseDetailService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(OverseasWarehouseManageRecordParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPageTotal(OverseasWarehouseManageRecordParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryPageTotal(param));
    }

    @Override
    @DataSource(name = "logistics")
    public String getNowOrder(String orderTypePre) {
        return mapper.getNowOrder(orderTypePre);
    }

    @Override
    @DataSource(name = "logistics")
    public void deleteByRsdId(BigDecimal rsdId) {
        this.baseMapper.deleteByRsdId(rsdId);
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData queryChangeReportPage(OverseasChangeReportParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryChangeReportPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasChangeReportResult> exportChangeReport(OverseasChangeReportParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryChangeReportPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData querySyncErrorPage(OverseasWarehouseSyncErrorParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return ResponseData.success(mapper.querySyncErrorPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData querySyncErrorDetail(SyncErpParam param) {
        return ResponseData.success(mapper.querySyncErrorDetail(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasWarehouseSyncErrorDetailResult> exportSyncError(OverseasWarehouseSyncErrorParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return mapper.exportSyncError(param);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData syncErp(SyncErpParam param) {
        if(StringUtils.isBlank(param.getMcOrder())){
            return ResponseData.error("订单号不能为空");
        }
        //根据订单号查询操作记录数据
        List<OverseasWarehouseManageRecord> recordList = mapper.listByMcOrder(param.getMcOrder());
        if(CollectionUtil.isEmpty(recordList)){
            return ResponseData.error("操作记录不存在");
        }

        List<BigDecimal> recordIdList = new ArrayList<>();
        for (OverseasWarehouseManageRecord record : recordList) {
            recordIdList.add(record.getId());
        }

        QueryWrapper<OverseasWarehouseManageRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.select("OPERATE_TYPE").in("ID", recordIdList).groupBy("OPERATE_TYPE");
        List<OverseasWarehouseManageRecord> operateTypeList = recordService.list(recordWrapper);
        if(CollectionUtil.isNotEmpty(operateTypeList) && operateTypeList.size() > 1){
            return ResponseData.error("只允许一种业务操作!");
        }
        String operateType = operateTypeList.get(0).getOperateType();
        List<BigDecimal> inIdList = new ArrayList<>();//盘点盘盈调K3其他入库
        List<BigDecimal> outIdList = new ArrayList<>();//盘点盘亏调K3其他出库
        List<BigDecimal> changeInIdList = new ArrayList<>();//其他入库单id集合（不同物料编码换标入库）
        List<BigDecimal> changeOutIdList = new ArrayList<>();//其他出库单id集合（不同物料编码换标出库）
        List<BigDecimal> changeTransferIdList = new ArrayList<>();//跨组织直接调拨单id集合（同物料编码不同组织换标）
        List<BigDecimal> rakutenOutIdList = new ArrayList<>();//乐天出库调用K3销售出库
        Set<String> outOrderSet = new HashSet<>();//海外仓出库亚马逊调用K3直接调拨单
        Set<String> outOrderWalmartSet = new HashSet<>();//海外仓出库沃尔玛调用K3直接调拨单
        List<BigDecimal> fbaInIdList = new ArrayList<>();//跨组织直接调拨单id集合（同物料编码不同组织换标）
        for (OverseasWarehouseManageRecord record : recordList) {
            if(OverseasBusinessTypeEnum.CHECK_ADD.getName().equals(record.getBusinessType())){
                inIdList.add(record.getId());
            }
            if(OverseasBusinessTypeEnum.CHECK_SUB.getName().equals(record.getBusinessType())){
                outIdList.add(record.getId());
            }
            if(OperateTypeEnum.CHANGE.getName().equals(record.getOperateType()) && "1".equals(record.getIsChangeMaterialCode())){
                if(OverseasBusinessTypeEnum.IN.getName().equals(record.getBusinessType())){
                    changeInIdList.add(record.getId());
                }
                if(OverseasBusinessTypeEnum.OUT.getName().equals(record.getBusinessType())){
                    changeOutIdList.add(record.getId());
                }
            }
            if(OperateTypeEnum.CHANGE.getName().equals(record.getOperateType()) && "0".equals(record.getIsChangeMaterialCode()) && "1".equals(record.getIsChangeOrg())){
                changeTransferIdList.add(record.getId());
            }

            if(OperateTypeEnum.RAKUTEN.getName().equals(record.getOperateType())){
                rakutenOutIdList.add(record.getId());
            }

            if(OperateTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(record.getOperateType())){
                if(!outOrderSet.contains(record.getOutOrder())){
                    outOrderSet.add(record.getOutOrder());
                }
            }

            if(OperateTypeEnum.OVERSEAS_TO_WALMART.getName().equals(record.getOperateType())){
                if(!outOrderWalmartSet.contains(record.getOutOrder())){
                    outOrderWalmartSet.add(record.getOutOrder());
                }
            }

            if(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(record.getOperateType())){
                fbaInIdList.add(record.getId());
            }
        }

        //异常信息
        StringBuffer errorMsg = new StringBuffer();

        //盘点
        if(OperateTypeEnum.CHECK.getName().equals(operateType)){
            if(CollectionUtil.isNotEmpty(inIdList)){
                //K3其他入库
                if(inIdList.size() > 1){
                    ResponseData resp = manageService.syncBatchCheckInStockToErp(inIdList);
                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                        errorMsg.append(resp.getMessage());
                    }
                }else{
                    ResponseData resp = manageService.syncCheckInStockToErp(inIdList.get(0));
                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                        errorMsg.append(resp.getMessage());
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(outIdList)){
                //K3其他出库
                if(outIdList.size() > 1){
                    ResponseData resp = manageService.syncBatchCheckOutStockToErp(outIdList);
                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                        errorMsg.append(resp.getMessage());
                    }
                }else{
                    ResponseData resp = manageService.syncCheckOutStockToErp(outIdList.get(0));
                    if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                        errorMsg.append(resp.getMessage());
                    }
                }
            }

        }

        //换标
        if(OperateTypeEnum.CHANGE.getName().equals(operateType)){
            if(CollectionUtil.isNotEmpty(changeInIdList)){
                ResponseData resp = manageService.syncBatchChangeInStockToErp(changeInIdList);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                }
            }
            if(CollectionUtil.isNotEmpty(changeOutIdList)){
                ResponseData resp = manageService.syncBatchChangeOutStockToErp(changeOutIdList);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                }
            }
            if(CollectionUtil.isNotEmpty(changeTransferIdList)){
                for (BigDecimal id : changeTransferIdList) {
                    ResponseData transferResponseData = syncTransferToErpService.save(null, id, TransferBusinessTypeEnum.DIFF_SITE_CHANGE);
                    if(ResponseData.DEFAULT_ERROR_CODE.equals(transferResponseData.getCode())){
                        errorMsg.append(transferResponseData.getMessage());
                        log.error("换标同步ERP直接调拨单失败，详情：[]", transferResponseData.getMessage());
                    }
                }
                /*ResponseData resp = syncTransferToErpService.diffSiteChangeSyncERP(changeTransferIdList);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                }*/
            }
        }

        //乐天海外仓出库
        if(OperateTypeEnum.RAKUTEN.getName().equals(operateType)){
            if(CollectionUtil.isNotEmpty(rakutenOutIdList)){
                String resp = manageService.syncOutWarehouseToErp(rakutenOutIdList);
                if(StringUtils.isNotEmpty(resp)){
                    errorMsg.append(resp);
                }
            }
        }

        //海外仓发亚马逊仓
        if(OperateTypeEnum.OVERSEAS_TO_AMAZON.getName().equals(operateType)){
            for (String outOrder : outOrderSet) {
                ResponseData resp = syncTransferToErpService.overseasOutSyncERP(outOrder, TransferBusinessTypeEnum.OVERSEAS_TO_AMAZON);
                String syncStatus = null;
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                    syncStatus = BaseSyncStatusEnum.ERROR.getCode();
                    if(resp.getMessage().contains("调用k3直接调拨单审核接口失败")){
                        syncStatus = BaseSyncStatusEnum.AUDIT_ERROR.getCode();
                    }
                }else{
                    syncStatus = BaseSyncStatusEnum.SUCCESS.getCode();
                }
                //更新海外仓出库管理同步ERP状态
                LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OverseasOutWarehouse :: getOutOrder, outOrder)
                        .set(OverseasOutWarehouse :: getSyncErpStatus, syncStatus)
                        .set(OverseasOutWarehouse :: getUpdateTime, DateUtil.date());
                outWarehouseService.update(null,updateWrapper);
            }
        }

        //海外仓发沃尔玛仓
        if(OperateTypeEnum.OVERSEAS_TO_WALMART.getName().equals(operateType)){
            for (String outOrder : outOrderWalmartSet) {
                ResponseData resp = syncTransferToErpService.overseasOutSyncERP(outOrder, TransferBusinessTypeEnum.OVERSEAS_TO_WALMART);
                String syncStatus = null;
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                    syncStatus = BaseSyncStatusEnum.ERROR.getCode();
                    if(resp.getMessage().contains("调用k3直接调拨单审核接口失败")){
                        syncStatus = BaseSyncStatusEnum.AUDIT_ERROR.getCode();
                    }
                }else{
                    syncStatus = BaseSyncStatusEnum.SUCCESS.getCode();
                }
                //更新海外仓出库管理同步ERP状态
                LambdaUpdateWrapper<OverseasOutWarehouse> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OverseasOutWarehouse :: getOutOrder, outOrder)
                        .set(OverseasOutWarehouse :: getSyncErpStatus, syncStatus)
                        .set(OverseasOutWarehouse :: getUpdateTime, DateUtil.date());
                outWarehouseService.update(null,updateWrapper);
            }
        }

        //亚马逊仓发海外仓
        if(OperateTypeEnum.AMAZON_TO_OVERSEAS.getName().equals(operateType)){
            if(CollectionUtil.isNotEmpty(fbaInIdList)){
                for (BigDecimal id : fbaInIdList) {
                    //根据操作记录ID，获取单据头数据
                    QueryWrapper<OverseasWarehouseManageRecord> recordQueryWrapper = new QueryWrapper<>();
                    recordQueryWrapper.eq("ID", id);
                    OverseasWarehouseManageRecord inRecord = recordService.getOne(recordQueryWrapper);
                    //推送ERP->创建调拨单 业务类型亚马逊仓发海外仓
                    try{
                        ResponseData responseData;
                        if(inRecord.getInOrg().equals(inRecord.getOutOrg())){
                            responseData = syncTransferToErpService.save(inRecord.getId(),null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);
                        }else{
                            responseData = syncTransferToErpService.save(inRecord.getId(),null, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS_OVER);
                        }
                        if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
                            errorMsg.append(responseData.getMessage());
                            log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inRecord.getId(), responseData.getMessage());
                        }
                    }catch(Exception e){
                        log.error("亚马逊仓发海外仓创建调拨单同步异常！操作记录ID：[{}]，信息：[{}]", inRecord.getId(), e.getMessage());
                    }
                }

                //批量
                /*ResponseData resp = syncTransferToErpService.overseasFbaInSyncERP(fbaInIdList, TransferBusinessTypeEnum.AMAZON_TO_OVERSEAS);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    errorMsg.append(resp.getMessage());
                }*/
            }
        }
        if(StringUtils.isEmpty(errorMsg)){
            return ResponseData.success();
        }else{
            return ResponseData.error(errorMsg.toString());
        }
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData querySyncEBMSErrorPage(OverseasWarehouseSyncErrorParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return ResponseData.success(mapper.querySyncEBMSErrorPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData querySyncEBMSErrorDetail(SyncErpParam param) {
        return ResponseData.success(mapper.querySyncEBMSErrorDetail(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasWarehouseSyncErrorDetailResult> exportSyncEBMSError(OverseasWarehouseSyncErrorParam param) {
        if(StringUtils.isNotEmpty(param.getStartCreateTime())){
            param.setStartCreateTime(param.getStartCreateTime() + " 00:00:00");
        }
        if(StringUtils.isNotEmpty(param.getEndCreateTime())){
            param.setEndCreateTime(param.getEndCreateTime() + " 23:59:59");
        }
        return mapper.exportSyncEBMSError(param);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData syncEBMS(SyncErpParam param) {
        if(StringUtils.isBlank(param.getMcOrder())){
            return ResponseData.error("订单号不能为空");
        }

        LambdaQueryWrapper<OverseasOutWarehouse> outWrapper = new LambdaQueryWrapper<>();
        outWrapper.eq(OverseasOutWarehouse :: getOutOrder, param.getMcOrder());
        OverseasOutWarehouse outWarehouse = outWarehouseService.getOne(outWrapper);
        if(outWarehouse == null){
            return ResponseData.error("不存在此订单号！");
        }

        //根据订单号查询同步EBMS异常操作记录数据
        List<OverseasWarehouseManageRecord> errorRecordList = mapper.listByMcOrderEBMS(param.getMcOrder());
        if(CollectionUtil.isEmpty(errorRecordList)){
            return ResponseData.error("同步EBMS异常操作记录不存在！");
        }

        //出库主表参数
        OverseasOutWarehouseParam syncEbmsParam = new OverseasOutWarehouseParam();
        BeanUtils.copyProperties(outWarehouse, syncEbmsParam);

        LambdaQueryWrapper<OverseasOutWarehouseDetail> outDetailWrapper = new LambdaQueryWrapper<>();
        outDetailWrapper.eq(OverseasOutWarehouseDetail::getOutOrder, param.getMcOrder());
        List<OverseasOutWarehouseDetail> outWarehouseDetails = outWarehouseDetailService.list(outDetailWrapper);
        if(CollectionUtil.isEmpty(outWarehouseDetails)){
            return ResponseData.error("订单明细数据不存在！");
        }
        //出库明细表参数
        List<OverseasOutWarehouseDetailParam> detailListParam = new ArrayList<>();
        for (OverseasOutWarehouseDetail detail : outWarehouseDetails) {
            OverseasOutWarehouseDetailParam detailParam = new OverseasOutWarehouseDetailParam();
            BeanUtils.copyProperties(detail, detailParam);
            detailListParam.add(detailParam);
        }
        syncEbmsParam.setDetailList(detailListParam);
        ResponseData responseData = outWarehouseService.createShipmentList(syncEbmsParam, true);
        if(ResponseData.DEFAULT_SUCCESS_CODE.equals(responseData.getCode())){
            return ResponseData.success();
        } else {
            log.error("海外仓出库单号[{}]，推送EBMS创建出货清单失败！信息[{}]", param.getMcOrder(), responseData.getMessage());
            return ResponseData.error(responseData.getMessage());
        }
    }
}
