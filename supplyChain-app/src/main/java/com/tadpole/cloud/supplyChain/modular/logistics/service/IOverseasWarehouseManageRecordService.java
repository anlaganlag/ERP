package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManageRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasChangeReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseManageRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseSyncErrorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.SyncErpParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasChangeReportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseSyncErrorDetailResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 海外仓库存管理操作记录服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface IOverseasWarehouseManageRecordService extends IService<OverseasWarehouseManageRecord> {

    /**
     * 海外仓库存管理操作记录分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(OverseasWarehouseManageRecordParam param);

    /**
     * 海外仓库存管理操作记录分页查询列表数据汇总
     * @param param
     * @return
     */
    ResponseData queryPageTotal(OverseasWarehouseManageRecordParam param);

    /**
     * 根据订单类型获取当前的订单号
     * @param orderTypePre
     * @return
     */
    String getNowOrder(String orderTypePre);

    /**
     * 删除原fba发海外仓来货记录
     * @param rsdId
     */
    void deleteByRsdId(BigDecimal rsdId);

    /**
     * 海外仓换标报表查询列表
     * @param param
     * @return
     */
    ResponseData queryChangeReportPage(OverseasChangeReportParam param);

    /**
     * 海外仓换标报表导出
     * @param param
     * @return
     */
    List<OverseasChangeReportResult> exportChangeReport(OverseasChangeReportParam param);

    /**
     * 海外仓同步ERP异常管理查询列表
     * @param param
     * @return
     */
    ResponseData querySyncErrorPage(OverseasWarehouseSyncErrorParam param);

    /**
     * 海外仓同步ERP异常管理明细
     * @param param
     * @return
     */
    ResponseData querySyncErrorDetail(SyncErpParam param);

    /**
     * 海外仓同步ERP异常管理查询列表导出
     * @param param
     * @return
     */
    List<OverseasWarehouseSyncErrorDetailResult> exportSyncError(OverseasWarehouseSyncErrorParam param);

    /**
     * 同步ERP
     * @param param
     * @return
     */
    ResponseData syncErp(SyncErpParam param);

    /**
     * 海外仓同步EBMS异常管理查询列表
     * @param param
     * @return
     */
    ResponseData querySyncEBMSErrorPage(OverseasWarehouseSyncErrorParam param);

    /**
     * 海外仓同步EBMS异常管理明细
     * @param param
     * @return
     */
    ResponseData querySyncEBMSErrorDetail(SyncErpParam param);

    /**
     * 海外仓同步EBMS异常管理查询列表导出
     * @param param
     * @return
     */
    List<OverseasWarehouseSyncErrorDetailResult> exportSyncEBMSError(OverseasWarehouseSyncErrorParam param);

    /**
     * 同步EBMS
     * @param param
     * @return
     */
    ResponseData syncEBMS(SyncErpParam param);
}
