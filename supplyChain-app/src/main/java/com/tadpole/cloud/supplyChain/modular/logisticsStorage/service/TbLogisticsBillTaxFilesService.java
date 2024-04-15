package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillTaxFiles;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillTaxFilesResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillTaxFilesParam;

import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流账单税务文件;(tb_logistics_bill_tax_files)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsBillTaxFilesService extends IService<TbLogisticsBillTaxFiles> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsBillTaxFiles queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsBillTaxFiles 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsBillTaxFilesResult> paginQuery(TbLogisticsBillTaxFilesParam tbLogisticsBillTaxFiles, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillTaxFiles 实例对象
     * @return 实例对象
     */
    TbLogisticsBillTaxFiles insert(TbLogisticsBillTaxFiles tbLogisticsBillTaxFiles);
    /** 
     * 更新数据
     *
     * @param tbLogisticsBillTaxFiles 实例对象
     * @return 实例对象
     */
    TbLogisticsBillTaxFiles update(TbLogisticsBillTaxFilesParam tbLogisticsBillTaxFiles);
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