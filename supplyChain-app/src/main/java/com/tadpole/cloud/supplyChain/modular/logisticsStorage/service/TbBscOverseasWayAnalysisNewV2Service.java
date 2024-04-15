package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWayAnalysisNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbBscOverseasWayAnalysisNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayAnalysisNewV2Result;

import java.util.List;

/**
 * 发货单数据;(Tb_Bsc_Overseas_Way_Analysis_New_V2)表服务接口
 * @author : LSY
 * @date : 2024-3-18
 */
public interface TbBscOverseasWayAnalysisNewV2Service extends IService<TbBscOverseasWayAnalysisNewV2> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    TbBscOverseasWayAnalysisNewV2 queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param tbBscOverseasWayAnalysisNewV2 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbBscOverseasWayAnalysisNewV2Result> paginQuery(TbBscOverseasWayAnalysisNewV2Param tbBscOverseasWayAnalysisNewV2, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbBscOverseasWayAnalysisNewV2 实例对象
     * @return 实例对象
     */
    TbBscOverseasWayAnalysisNewV2 insert(TbBscOverseasWayAnalysisNewV2 tbBscOverseasWayAnalysisNewV2);
    /** 
     * 更新数据
     *
     * @param tbBscOverseasWayAnalysisNewV2 实例对象
     * @return 实例对象
     */
    TbBscOverseasWayAnalysisNewV2 update(TbBscOverseasWayAnalysisNewV2Param tbBscOverseasWayAnalysisNewV2);
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