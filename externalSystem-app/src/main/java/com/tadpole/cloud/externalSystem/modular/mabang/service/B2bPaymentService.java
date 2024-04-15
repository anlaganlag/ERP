package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPayment;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.B2bMabangOrdersExportResult;

import java.util.List;

 /**
 * B2B客户付款信息;(B2B_PAYMENT)--服务接口
 * @author : LSY
 * @date : 2023-9-14
 */
public interface B2bPaymentService extends IService<B2bPayment> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    B2bPayment queryById(String id);
    
    /**
     * 分页查询
     *
     * @param b2bPayment 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<B2bMabangOrdersResult> paginQuery(B2bPaymentParam b2bPayment, long current, long size);
    /** 
     * 新增数据
     *
     * @param b2bPayment 实例对象
     * @return 实例对象
     */
    B2bPayment insert(B2bPayment b2bPayment);
    /** 
     * 更新数据
     *
     * @param b2bPayment 实例对象
     * @return 实例对象
     */
    B2bPayment update(B2bPayment b2bPayment);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
    
    /**
     * 通过主键删除数据--批量删除
     * @param idList
     * @return
     */
     boolean deleteBatchIds(List<String> idList);

     /**
      * 创建货更新订单的付款信息
      * @param b2bMabangOrders
      */
     void createOrUpdateB2bPayment(B2bMabangOrders b2bMabangOrders);

     /**
      * 更新付款信息汇总数据
      * @param paymentDetail 付款明细数据
      * @param bizTyep 1:运营提交，-1：财务确认
      * @return
      */
     B2bPayment updateAmount(B2bPaymentDetail paymentDetail, int bizTyep);

     /**
      *
      * @param platformOrderId 平台订单号
      * @param b2bOrderCloseByOper 操作人类型，财务Or运营
      * @return
      */
     ResponseData orderClose(String platformOrderId, String b2bOrderCloseByOper);

     /**
      * 按查询条件导出
      * @param b2bPaymentParam
      * @return
      */
     List<B2bMabangOrdersExportResult> export(B2bPaymentParam b2bPaymentParam);
 }