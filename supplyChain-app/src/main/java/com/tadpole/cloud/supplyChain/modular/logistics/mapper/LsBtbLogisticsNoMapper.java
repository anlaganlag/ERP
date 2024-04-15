package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsTrackReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsTrackReportResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  BTB物流单Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
public interface LsBtbLogisticsNoMapper extends BaseMapper<LsBtbLogisticsNo> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsBtbLogisticsNoResult> queryPage(@Param("page") Page page, @Param("param") LsBtbLogisticsNoParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<LsBtbLogisticsNoResult> export(@Param("param") LsBtbLogisticsNoParam param);

    /**
     * BTB物流跟踪报表分页查询列表
     * @param param
     * @return
     */
    Page<LsLogisticsTrackReportResult> queryBtbPage(@Param("page") Page page, @Param("param") LsLogisticsTrackReportParam param);

    /**
     * EBMS物流跟踪报表分页查询列表
     * @param param
     * @return
     */
    Page<LsLogisticsTrackReportResult> queryEbmsPage(@Param("page") Page page, @Param("param") LsLogisticsTrackReportParam param);

    /**
     * BTB物流跟踪报表查询列表
     * @param param
     * @return
     */
    List<LsLogisticsTrackReportResult> queryBtbList(@Param("param") LsLogisticsTrackReportParam param);

    /**
     * EBMS物流跟踪报表查询列表
     * @param param
     * @return
     */
    List<LsLogisticsTrackReportResult> queryEbmsList(@Param("param") LsLogisticsTrackReportParam param);

    /**
     * BTB发货仓下拉
     * @return
     */
    List<BaseSelectResult> btbShipmentWarehouseList();

    /**
     * EBMS发货仓下拉
     * @return
     */
    List<BaseSelectResult> ebmsShipmentWarehouseList();

}
