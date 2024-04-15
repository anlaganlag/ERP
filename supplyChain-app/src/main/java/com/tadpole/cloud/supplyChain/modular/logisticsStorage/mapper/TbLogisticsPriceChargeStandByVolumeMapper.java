package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceChargeStandByVolume;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceChargeStandByVolumeResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流价格按体积计费的--暂时不需要;(tb_logistics_price_charge_stand_by_volume)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPriceChargeStandByVolumeMapper  extends BaseMapper<TbLogisticsPriceChargeStandByVolume>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPriceChargeStandByVolumeResult> selectByPage(IPage<TbLogisticsPriceChargeStandByVolumeResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPriceChargeStandByVolume> wrapper);
}