package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillChargeDetial;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillChargeDetialResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物流账单费用明细;(tb_logistics_bill_charge_detial)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsBillChargeDetialMapper  extends BaseMapper<TbLogisticsBillChargeDetial>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsBillChargeDetialResult> selectByPage(IPage<TbLogisticsBillChargeDetialResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsBillChargeDetial> wrapper);
}