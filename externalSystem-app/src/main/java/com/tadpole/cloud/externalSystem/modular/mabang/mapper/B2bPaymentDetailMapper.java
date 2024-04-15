package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3BankAccountInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bPaymentDetailResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * B2B客户付款明细;(B2B_PAYMENT_DETAIL)--数据库访问层
 * @author : LSY
 * @date : 2023-9-14
 */
@Mapper
public interface B2bPaymentDetailMapper  extends BaseMapper<B2bPaymentDetail>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<B2bPaymentDetailResult> selectByPage(IPage<B2bPaymentDetailResult> page , @Param(Constants.WRAPPER) Wrapper<B2bPaymentDetail> wrapper);

     /**
      * 查询K3银行和科目信息
      * @param bankNo 银行账号
      * @param orgCode 组织编码
      * @return
      */
     K3BankAccountInfo queryK3BankAccountInfo(String bankNo,String orgCode);

     /**
      * 根据组织编码（财务编码）查找该组织下的银行收款信息
      * @param orgCode
      * @return
      */
     List<K3BankAccountInfo> queryK3BankInfoByOrgCode(String orgCode);
 }