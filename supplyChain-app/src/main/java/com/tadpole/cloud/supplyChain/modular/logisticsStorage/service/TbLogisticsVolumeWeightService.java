package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsVolumeWeight;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsVolumeWeightResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsVolumeWeightParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流体积重量;(tb_logistics_volume_weight)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsVolumeWeightService extends IService<TbLogisticsVolumeWeight> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lvwId 主键
     * @return 实例对象
     */
    TbLogisticsVolumeWeight queryById(BigDecimal lvwId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsVolumeWeight 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsVolumeWeightResult> paginQuery(TbLogisticsVolumeWeightParam tbLogisticsVolumeWeight, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsVolumeWeight 实例对象
     * @return 实例对象
     */
    TbLogisticsVolumeWeight insert(TbLogisticsVolumeWeight tbLogisticsVolumeWeight);
    /** 
     * 更新数据
     *
     * @param tbLogisticsVolumeWeight 实例对象
     * @return 实例对象
     */
    TbLogisticsVolumeWeight update(TbLogisticsVolumeWeightParam tbLogisticsVolumeWeight);
    /** 
     * 通过主键删除数据
     *
     * @param lvwId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lvwId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lvwIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lvwIdList);
}