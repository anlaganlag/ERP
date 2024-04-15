package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsCountryAreaPartition;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsCountryAreaPartitionResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsCountryAreaPartitionParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流国家区域划分;(tb_logistics_country_area_partition)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsCountryAreaPartitionService extends IService<TbLogisticsCountryAreaPartition> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsCountryAreaPartition queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsCountryAreaPartition 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsCountryAreaPartitionResult> paginQuery(TbLogisticsCountryAreaPartitionParam tbLogisticsCountryAreaPartition, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsCountryAreaPartition 实例对象
     * @return 实例对象
     */
    TbLogisticsCountryAreaPartition insert(TbLogisticsCountryAreaPartition tbLogisticsCountryAreaPartition);
    /** 
     * 更新数据
     *
     * @param tbLogisticsCountryAreaPartition 实例对象
     * @return 实例对象
     */
    TbLogisticsCountryAreaPartition update(TbLogisticsCountryAreaPartitionParam tbLogisticsCountryAreaPartition);
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