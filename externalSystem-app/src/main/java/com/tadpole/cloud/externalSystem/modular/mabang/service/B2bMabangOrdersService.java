package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;

import java.util.List;

 /**
 * B2B马帮订单列表;(B2B_MABANG_ORDERS)--服务接口
 * @author : LSY
 * @date : 2023-9-13
 */
public interface B2bMabangOrdersService extends IService<B2bMabangOrders> {
    
    /** 
     * 通过平台订单号查询单条数据
     *
     * @param platformOrderId 平台订单号
     * @return 实例对象
     */
    B2bMabangOrdersResult queryByPlatformOrderId(String platformOrderId);
    
    /**
     * 分页查询
     *
     * @param b2bMabangOrders 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<B2bMabangOrdersResult> paginQuery(B2bMabangOrdersParam b2bMabangOrders, long current, long size);
    /** 
     * 新增数据
     *
     * @param b2bMabangOrders 实例对象
     * @return 实例对象
     */
    B2bMabangOrders insert(B2bMabangOrders b2bMabangOrders);

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

     ResponseData updateCancelOrder(List<String> updateOrderList);

     /**
      * 同步配货中的订单列表
      * @param orderParm
      * @return
      */
     ResponseData preparingStockOrderList(OrderParm orderParm);

     /**
      * 转换从马帮接口获取的订单信息并添加到mc系统
      * @param param
      */
      void add(MabangResult param,List<String> shopNameList);
      void addNew(MabangResult param,List<String> shopNameList);


     void updateSendOrders(MabangResult param, List<String> shopNameList);


 }