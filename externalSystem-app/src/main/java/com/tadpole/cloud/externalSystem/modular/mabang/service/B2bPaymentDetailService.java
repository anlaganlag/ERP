package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3BankAccountInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentDetailParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bPaymentDetailResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * B2B客户付款明细;(B2B_PAYMENT_DETAIL)--服务接口
 * @author : LSY
 * @date : 2023-9-14
 */
public interface B2bPaymentDetailService extends IService<B2bPaymentDetail> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    B2bPaymentDetail queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param b2bPaymentDetail 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<B2bPaymentDetailResult> paginQuery(B2bPaymentDetailParam b2bPaymentDetail, long current, long size);
    /** 
     * 新增数据
     *
     * @param b2bPaymentDetail 实例对象
     * @return 实例对象
     */
    B2bPaymentDetail insert(B2bPaymentDetail b2bPaymentDetail);
    /** 
     * 更新数据
     *
     * @param b2bPaymentDetail 实例对象
     * @return 实例对象
     */
    B2bPaymentDetail update(B2bPaymentDetail b2bPaymentDetail);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
    
    /**
     * 通过主键删除数据--批量删除
     * @param idList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> idList);

     /**
      * 根据平台订单编号查询 收款明细记录
      * @param platformOrderId
      * @return
      */
     List<B2bPaymentDetail> queryByPlatformOrderId(String platformOrderId);

     /**
      * 财务确认收款
      * @param parm
      * @return
      */
     ResponseData financeConfirm(B2bPaymentDetail parm);



     /**
      * 运营提交付款信息
      * @param b2bPaymentDetail
      * @return
      */
     ResponseData operSubmit(B2bPaymentDetail b2bPaymentDetail);

     /**
      * 同步付款明细到K3
      * @param id
      * @return
      */
     ResponseData syncPaymentDetail(BigDecimal id);
     /**
      * 根据组织编码（财务编码）查找该组织下的银行收款信息
      * @param orgCode
      * @param shopName
      * @return
      */
     List<K3BankAccountInfo> queryK3BankInfoByOrgCode(String orgCode,String shopName);
 }