package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;

import java.util.List;
import java.util.Map;

/**
 * ;(EST_LOGISTICS_ESTIMATE_FEE_DET)表数据库访问层
 *
 * @author : LSY
 * @date : 2024-3-14
 */
@Mapper
public interface EstLogisticsEstimateFeeDetMapper extends MPJBaseMapper<EstLogisticsEstimateFeeDet> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<EstLogisticsEstimateFeeDetResult> selectByPage(IPage<EstLogisticsEstimateFeeDetResult> page, @Param(Constants.WRAPPER) Wrapper<EstLogisticsEstimateFeeDet> wrapper);

    List<LsLogisticsPriceResult> getLpPrice(@Param("param") EstLogisticsEstimateFeeDetParam param);


    List<String> shipToList();

    List<ShipToPostCodeResult> getPostCode();

    List<LogisticsInfoResult> getLogisticsInfo();

    List<EstLogisticsEstimateFeeDetExportResult> exportList(@Param("param") EstLogisticsEstimateFeeDetParam param);
}