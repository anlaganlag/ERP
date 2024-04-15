package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


 /**
 * B2B马帮订单列表;(B2B_MABANG_ORDERS)--数据库访问层
 * @author : LSY
 * @date : 2023-9-13
 */
@Mapper
public interface B2bMabangOrdersMapper  extends MPJBaseMapper<B2bMabangOrders> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<B2bMabangOrdersResult> selectByPage(IPage<B2bMabangOrdersResult> page , @Param(Constants.WRAPPER) Wrapper<B2bMabangOrders> wrapper);
}