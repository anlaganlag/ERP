package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManageRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasChangeReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseManageRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseSyncErrorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.SyncErpParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 海外仓库存管理操作记录Mapper接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface OverseasWarehouseManageRecordMapper extends BaseMapper<OverseasWarehouseManageRecord> {

    /**
     * 海外仓库存管理详情分页查询列表
     * @param param
     * @return
     */
    Page<OverseasWarehouseManageRecordResult> queryPage(@Param("page") Page page, @Param("param") OverseasWarehouseManageRecordParam param);

    /**
     * 海外仓库存管理操作记录分页查询列表数据汇总
     * @param param
     * @return
     */
    OverseasWarehouseManageTotalResult queryPageTotal(@Param("param") OverseasWarehouseManageRecordParam param);

    /**
     * 根据订单类型获取当前的订单号
     * @param orderTypePre
     * @return
     */
    String getNowOrder(@Param("orderTypePre") String orderTypePre);

    /**
     * 删除原fba发海外仓来货记录
     * @param rsdId
     */
    void deleteByRsdId(@Param("rsdId") BigDecimal rsdId);

    /**
     * 海外仓换标报表查询列表、导出
     * @param param
     * @return
     */
    Page<OverseasChangeReportResult> queryChangeReportPage(@Param("page") Page page, @Param("param") OverseasChangeReportParam param);

    /**
     * 海外仓同步ERP异常管理查询列表
     * @param page
     * @param param
     * @return
     */
    Page<OverseasWarehouseSyncErrorResult> querySyncErrorPage(@Param("page") Page page, @Param("param") OverseasWarehouseSyncErrorParam param);

    /**
     * 海外仓同步ERP异常管理明细
     * @param page
     * @param param
     * @return
     */
    Page<OverseasWarehouseSyncErrorDetailResult> querySyncErrorDetail(@Param("page") Page page, @Param("param") SyncErpParam param);

    /**
     * 海外仓同步ERP异常管理查询列表导出
     * @param param
     * @return
     */
    List<OverseasWarehouseSyncErrorDetailResult> exportSyncError(@Param("param") OverseasWarehouseSyncErrorParam param);

    /**
     * 根据订单号查询同步K3异常记录
     * @param mcOrder
     * @return
     */
    List<OverseasWarehouseManageRecord> listByMcOrder(@Param("mcOrder") String mcOrder);

    /**
     * 海外仓同步EBMS异常管理查询列表
     * @param page
     * @param param
     * @return
     */
    Page<OverseasWarehouseSyncErrorResult> querySyncEBMSErrorPage(@Param("page") Page page, @Param("param") OverseasWarehouseSyncErrorParam param);

    /**
     * 海外仓同步EBMS异常管理明细
     * @param page
     * @param param
     * @return
     */
    Page<OverseasWarehouseSyncErrorDetailResult> querySyncEBMSErrorDetail(@Param("page") Page page, @Param("param") SyncErpParam param);

    /**
     * 海外仓同步EBMS异常管理查询列表导出
     * @param param
     * @return
     */
    List<OverseasWarehouseSyncErrorDetailResult> exportSyncEBMSError(@Param("param") OverseasWarehouseSyncErrorParam param);

    /**
     * 根据订单号查询同步EBMS异常记录
     * @param mcOrder
     * @return
     */
    List<OverseasWarehouseManageRecord> listByMcOrderEBMS(@Param("mcOrder") String mcOrder);
}
