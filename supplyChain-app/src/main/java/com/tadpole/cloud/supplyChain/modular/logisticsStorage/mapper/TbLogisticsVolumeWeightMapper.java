package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsVolumeWeight;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsVolumeWeightResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流体积重量;(tb_logistics_volume_weight)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsVolumeWeightMapper  extends BaseMapper<TbLogisticsVolumeWeight>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsVolumeWeightResult> selectByPage(IPage<TbLogisticsVolumeWeightResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsVolumeWeight> wrapper);
}