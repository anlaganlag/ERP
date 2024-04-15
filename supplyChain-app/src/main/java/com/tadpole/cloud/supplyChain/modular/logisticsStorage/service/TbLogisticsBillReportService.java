package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillReportParam;

import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流账单报告;(tb_logistics_bill_report)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsBillReportService extends IService<TbLogisticsBillReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsBillReport queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsBillReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsBillReportResult> paginQuery(TbLogisticsBillReportParam tbLogisticsBillReport, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillReport 实例对象
     * @return 实例对象
     */
    TbLogisticsBillReport insert(TbLogisticsBillReport tbLogisticsBillReport);
    /** 
     * 更新数据
     *
     * @param tbLogisticsBillReport 实例对象
     * @return 实例对象
     */
    TbLogisticsBillReport update(TbLogisticsBillReportParam tbLogisticsBillReport);
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