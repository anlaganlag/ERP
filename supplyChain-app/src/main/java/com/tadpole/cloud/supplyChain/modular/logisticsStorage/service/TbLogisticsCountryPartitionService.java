package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsCountryPartition;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsCountryPartitionResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsCountryPartitionParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流国家划分;(tb_logistics_country_partition)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsCountryPartitionService extends IService<TbLogisticsCountryPartition> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsCountryPartition queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsCountryPartition 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsCountryPartitionResult> paginQuery(TbLogisticsCountryPartitionParam tbLogisticsCountryPartition, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsCountryPartition 实例对象
     * @return 实例对象
     */
    TbLogisticsCountryPartition insert(TbLogisticsCountryPartition tbLogisticsCountryPartition);
    /** 
     * 更新数据
     *
     * @param tbLogisticsCountryPartition 实例对象
     * @return 实例对象
     */
    TbLogisticsCountryPartition update(TbLogisticsCountryPartitionParam tbLogisticsCountryPartition);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);
}