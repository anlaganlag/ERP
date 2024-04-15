package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsInputPreShareReportParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流投入预估分担报告-暂时不需要;(tb_logistics_input_pre_share_report)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsInputPreShareReportService extends IService<TbLogisticsInputPreShareReport> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogisrId 主键
     * @return 实例对象
     */
    TbLogisticsInputPreShareReport queryById(BigDecimal pkLogisrId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsInputPreShareReport 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsInputPreShareReportResult> paginQuery(TbLogisticsInputPreShareReportParam tbLogisticsInputPreShareReport, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsInputPreShareReport 实例对象
     * @return 实例对象
     */
    TbLogisticsInputPreShareReport insert(TbLogisticsInputPreShareReport tbLogisticsInputPreShareReport);
    /** 
     * 更新数据
     *
     * @param tbLogisticsInputPreShareReport 实例对象
     * @return 实例对象
     */
    TbLogisticsInputPreShareReport update(TbLogisticsInputPreShareReportParam tbLogisticsInputPreShareReport);
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