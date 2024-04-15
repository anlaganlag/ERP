package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceSurchargeItems;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceSurchargeItemsResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceSurchargeItemsParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流价格附加项目--暂时不需要;(tb_logistics_price_surcharge_items)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPriceSurchargeItemsService extends IService<TbLogisticsPriceSurchargeItems> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpsiId 主键
     * @return 实例对象
     */
    TbLogisticsPriceSurchargeItems queryById(BigDecimal lpsiId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPriceSurchargeItems 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPriceSurchargeItemsResult> paginQuery(TbLogisticsPriceSurchargeItemsParam tbLogisticsPriceSurchargeItems, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceSurchargeItems 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceSurchargeItems insert(TbLogisticsPriceSurchargeItems tbLogisticsPriceSurchargeItems);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPriceSurchargeItems 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceSurchargeItems update(TbLogisticsPriceSurchargeItemsParam tbLogisticsPriceSurchargeItems);
    /** 
     * 通过主键删除数据
     *
     * @param lpsiId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lpsiId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lpsiIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lpsiIdList);
}