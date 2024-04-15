package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbReceivedInvenrotyAnalysisV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbReceivedInvenrotyAnalysisV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbReceivedInvenrotyAnalysisV2Param;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 来货报告;(Tb_Received_Invenroty_Analysis_V2)表服务接口
 * @author : LSY
 * @date : 2024-3-18
 */
public interface TbReceivedInvenrotyAnalysisV2Service extends IService<TbReceivedInvenrotyAnalysisV2> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    TbReceivedInvenrotyAnalysisV2 queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param tbReceivedInvenrotyAnalysisV2 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbReceivedInvenrotyAnalysisV2Result> paginQuery(TbReceivedInvenrotyAnalysisV2Param tbReceivedInvenrotyAnalysisV2, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbReceivedInvenrotyAnalysisV2 实例对象
     * @return 实例对象
     */
    TbReceivedInvenrotyAnalysisV2 insert(TbReceivedInvenrotyAnalysisV2 tbReceivedInvenrotyAnalysisV2);
    /** 
     * 更新数据
     *
     * @param tbReceivedInvenrotyAnalysisV2 实例对象
     * @return 实例对象
     */
    TbReceivedInvenrotyAnalysisV2 update(TbReceivedInvenrotyAnalysisV2Param tbReceivedInvenrotyAnalysisV2);
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> undefinedIdList);
}