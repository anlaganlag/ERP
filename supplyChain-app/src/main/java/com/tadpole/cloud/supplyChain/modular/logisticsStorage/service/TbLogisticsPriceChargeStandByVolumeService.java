package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceChargeStandByVolume;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceChargeStandByVolumeResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceChargeStandByVolumeParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流价格按体积计费的--暂时不需要;(tb_logistics_price_charge_stand_by_volume)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPriceChargeStandByVolumeService extends IService<TbLogisticsPriceChargeStandByVolume> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpcsvId 主键
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByVolume queryById(BigDecimal lpcsvId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPriceChargeStandByVolume 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPriceChargeStandByVolumeResult> paginQuery(TbLogisticsPriceChargeStandByVolumeParam tbLogisticsPriceChargeStandByVolume, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceChargeStandByVolume 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByVolume insert(TbLogisticsPriceChargeStandByVolume tbLogisticsPriceChargeStandByVolume);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPriceChargeStandByVolume 实例对象
     * @return 实例对象
     */
    TbLogisticsPriceChargeStandByVolume update(TbLogisticsPriceChargeStandByVolumeParam tbLogisticsPriceChargeStandByVolume);
    /** 
     * 通过主键删除数据
     *
     * @param lpcsvId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lpcsvId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lpcsvIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lpcsvIdList);
}