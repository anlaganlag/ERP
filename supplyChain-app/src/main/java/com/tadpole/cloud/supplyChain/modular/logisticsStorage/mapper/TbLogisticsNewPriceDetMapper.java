package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPriceDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 物流商的价格信息-明细;(tb_logistics_new_price_det)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsNewPriceDetMapper  extends MPJBaseMapper<TbLogisticsNewPriceDet> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsNewPriceDetResult> selectByPage(IPage<TbLogisticsNewPriceDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsNewPriceDet> wrapper);

    /**
     * 唯一值+重量区间
     * @param priceOnlyKeySet
     * @return
     */

    List<TbLogisticsNewPriceResult> queryByPriceDetOnlyKey(Set<String> priceOnlyKeySet);
}