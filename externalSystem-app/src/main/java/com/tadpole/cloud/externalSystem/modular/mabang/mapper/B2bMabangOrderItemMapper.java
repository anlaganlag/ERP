package com.tadpole.cloud.externalSystem.modular.mabang.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrderItemResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * B2B马帮订单具体商品项item;(B2B_MABANG_ORDER_ITEM)--数据库访问层
 * @author : LSY
 * @date : 2023-9-13
 */
@Mapper
public interface B2bMabangOrderItemMapper  extends BaseMapper<B2bMabangOrderItem> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<B2bMabangOrderItemResult> selectByPage(IPage<B2bMabangOrderItemResult> page , @Param(Constants.WRAPPER) Wrapper<B2bMabangOrderItem> wrapper);
}