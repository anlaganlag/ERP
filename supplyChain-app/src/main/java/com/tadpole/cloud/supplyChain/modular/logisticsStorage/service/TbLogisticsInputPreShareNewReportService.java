package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareNewReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareNewReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsInputPreShareNewReportParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流投入预估分担报告-新-暂时不需要;(tb_logistics_input_pre_share_new_report)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsInputPreShareNewReportService extends IService<TbLogisticsInputPreShareNewReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogisrId 主键
     * @return 实例对象
     */
    TbLogisticsInputPreShareNewReport queryById(BigDecimal pkLogisrId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsInputPreShareNewReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsInputPreShareNewReportResult> paginQuery(TbLogisticsInputPreShareNewReportParam tbLogisticsInputPreShareNewReport, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsInputPreShareNewReport 实例对象
     * @return 实例对象
     */
    TbLogisticsInputPreShareNewReport insert(TbLogisticsInputPreShareNewReport tbLogisticsInputPreShareNewReport);
    /** 
     * 更新数据
     *
     * @param tbLogisticsInputPreShareNewReport 实例对象
     * @return 实例对象
     */
    TbLogisticsInputPreShareNewReport update(TbLogisticsInputPreShareNewReportParam tbLogisticsInputPreShareNewReport);
    /** 
     * 通过主键删除数据
     *
     * @param pkLogisrId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkLogisrId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkLogisrIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkLogisrIdList);
}