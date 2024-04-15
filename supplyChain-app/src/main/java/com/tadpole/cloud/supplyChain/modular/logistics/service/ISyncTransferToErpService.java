package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TransferBusinessTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 调用金蝶ERP接口类
 * @date: 2022/8/2
 */
public interface ISyncTransferToErpService {

    /**
     * 保存
     * @param inId 操作记录表入库ID
     * @param outId 操作记录表出库ID
     * @param transferBusinessType 海外仓K3组织调拨类型
     * @return
     */
    ResponseData save(BigDecimal inId, BigDecimal outId, TransferBusinessTypeEnum transferBusinessType);

    /**
     * 提交
     * @param orgTransferId K3组织调拨ID
     * @param orgTransferBillNo K3组织调拨单号
     * @return
     */
    ResponseData submit(String orgTransferId, String orgTransferBillNo);

    /**
     * 审核
     * @param orgTransferId K3组织调拨ID
     * @param orgTransferBillNo K3组织调拨单号
     * @return
     */
    ResponseData audit(String orgTransferId, String orgTransferBillNo);

    /**
     * 取消
     * @param orgTransferId K3组织调拨ID
     * @param orgTransferBillNo K3组织调拨单号
     * @return
     */
    ResponseData cancelAssign(String orgTransferId, String orgTransferBillNo);

    /**
     * 海外仓出库调用ERP组织调拨
     * @param outOrder 海外仓出库单号
     * @param typeEnum 业务类型枚举
     * @return
     */
    ResponseData overseasOutSyncERP(String outOrder, TransferBusinessTypeEnum typeEnum);

    /**
     * FBA退海外仓入库调用ERP组织调拨
     * @param inIdList 入库id集合
     * @param typeEnum 业务类型枚举
     * @return
     */
    ResponseData overseasFbaInSyncERP(List<BigDecimal> inIdList, TransferBusinessTypeEnum typeEnum);

    /**
     * 同物料跨组织换标调用ERP组织调拨
     * @param transferIdList 同物料跨组织id集合
     * @return
     */
    ResponseData diffSiteChangeSyncERP(List<BigDecimal> transferIdList);
}
