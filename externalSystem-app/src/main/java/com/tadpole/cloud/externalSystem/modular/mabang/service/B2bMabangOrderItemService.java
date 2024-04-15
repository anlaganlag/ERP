package com.tadpole.cloud.externalSystem.modular.mabang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrderItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrderItemResult;

import java.util.List;

 /**
 * B2B马帮订单具体商品项item;(B2B_MABANG_ORDER_ITEM)--服务接口
 * @author : LSY
 * @date : 2023-9-13
 */
public interface B2bMabangOrderItemService extends IService<B2bMabangOrderItem> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    B2bMabangOrderItem queryById(String id);
    
    /**
     * 分页查询
     *
     * @param b2bMabangOrderItem 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<B2bMabangOrderItemResult> paginQuery(B2bMabangOrderItemParam b2bMabangOrderItem, long current, long size);
    /** 
     * 新增数据
     *
     * @param b2bMabangOrderItem 实例对象
     * @return 实例对象
     */
    B2bMabangOrderItem insert(B2bMabangOrderItem b2bMabangOrderItem);
    /** 
     * 更新数据
     *
     * @param b2bMabangOrderItem 实例对象
     * @return 实例对象
     */
    B2bMabangOrderItem update(B2bMabangOrderItem b2bMabangOrderItem);
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
      * B2B订单时抓取创建完成的状态，可能会删减里面的物料信息，因此每次抓取回来的时候直接删除原来的，然后保存最新的
      * @param itemList
      */
     void refreshData(List<B2bMabangOrderItem> itemList);
 }