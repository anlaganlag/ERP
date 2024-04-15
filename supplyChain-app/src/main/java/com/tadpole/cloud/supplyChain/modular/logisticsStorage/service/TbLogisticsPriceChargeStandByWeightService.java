package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceChargeStandByWeight;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceChargeStandByWeightResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceChargeStandByWeightParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流价格按重量计费的--暂时不需要;(tb_logistics_price_charge_stand_by_weight)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPriceChargeStandByWeightService extends IService<TbLogisticsPriceChargeStandByWeight> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpcswId 主键
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByWeight queryById(BigDecimal lpcswId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPriceChargeStandByWeight 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPriceChargeStandByWeightResult> paginQuery(TbLogisticsPriceChargeStandByWeightParam tbLogisticsPriceChargeStandByWeight, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceChargeStandByWeight 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByWeight insert(TbLogisticsPriceChargeStandByWeight tbLogisticsPriceChargeStandByWeight);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPriceChargeStandByWeight 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByWeight update(TbLogisticsPriceChargeStandByWeightParam tbLogisticsPriceChargeStandByWeight);
    /** 
     * 通过主键删除数据
     *
     * @param lpcswId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lpcswId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lpcswIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lpcswIdList);
}