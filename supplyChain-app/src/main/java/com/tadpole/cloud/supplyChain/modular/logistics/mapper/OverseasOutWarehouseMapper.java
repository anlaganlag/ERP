package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ExportOverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 海外仓出库管理Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface OverseasOutWarehouseMapper extends BaseMapper<OverseasOutWarehouse> {

    /**
     * 海外仓出库管理查询列表
     * @param param
     * @return
     */
    Page<OverseasOutWarehouseResult> queryListPage(@Param("page") Page page, @Param("param") OverseasOutWarehouseParam param);

    /**
     * 海外仓出库管理查询列表数据汇总
     * @param param
     * @return
     */
    BigDecimal queryPageTotal(@Param("param") OverseasOutWarehouseParam param);

    /**
     * 查询出库单详情接口
     */
    List<OverseasOutWarehouseDetailResult> list(@Param("param") OverseasOutWarehouseDetailParam param);

    /**
     * 海外仓出库管理导出
     * @param param
     * @return
     */
    List<ExportOverseasOutWarehouseResult> allList(@Param("param") OverseasOutWarehouseParam param);


}
