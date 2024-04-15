package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceSurchargeItems;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceSurchargeItemsResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流价格附加项目--暂时不需要;(tb_logistics_price_surcharge_items)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPriceSurchargeItemsMapper  extends BaseMapper<TbLogisticsPriceSurchargeItems>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPriceSurchargeItemsResult> selectByPage(IPage<TbLogisticsPriceSurchargeItemsResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPriceSurchargeItems> wrapper);
}