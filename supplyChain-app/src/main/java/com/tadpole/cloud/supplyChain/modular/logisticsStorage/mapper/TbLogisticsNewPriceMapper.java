package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPrice;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 物流商的价格信息;(tb_logistics_new_price)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsNewPriceMapper  extends MPJBaseMapper<TbLogisticsNewPrice> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsNewPriceResult> selectByPage(IPage<TbLogisticsNewPriceResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsNewPrice> wrapper);

    List<TbLogisticsNewPriceParam> queryByPriceOnlyKey(Set<String> priceOnlyKeySet);
}