package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPayment;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bPaymentResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


 /**
 * B2B客户付款信息;(B2B_PAYMENT)--数据库访问层
 * @author : LSY
 * @date : 2023-9-14
 */
@Mapper
public interface B2bPaymentMapper  extends MPJBaseMapper<B2bPayment> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<B2bPaymentResult> selectByPage(IPage<B2bPaymentResult> page , @Param(Constants.WRAPPER) Wrapper<B2bPayment> wrapper);
}