package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBill;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillParam;

import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流账单;(tb_logistics_bill)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsBillService extends IService<TbLogisticsBill> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsBill queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsBill 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsBillResult> paginQuery(TbLogisticsBillParam tbLogisticsBill, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsBill 实例对象
     * @return 实例对象
     */
    TbLogisticsBill insert(TbLogisticsBill tbLogisticsBill);
    /** 
     * 更新数据
     *
     * @param tbLogisticsBill 实例对象
     * @return 实例对象
     */
    TbLogisticsBill update(TbLogisticsBillParam tbLogisticsBill);
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