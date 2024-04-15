package com.tadpole.cloud.externalSystem.modular.ebms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwAnalyRule;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwAnalyRuleParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwAnalyRuleResult;
import java.util.List;

 /**
 * TbDWAnalyRule;(Tb_DW_Analy_Rule)--服务接口
 * @author : LSY
 * @date : 2023-8-14
 */
public interface TbDwAnalyRuleService extends IService<TbDwAnalyRule> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwRuleId 主键
     * @return 实例对象
     */
    TbDwAnalyRule queryById(BigDecimal sysDwRuleId);
    
    /**
     * 分页查询
     *
     * @param tbDwAnalyRule 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbDwAnalyRuleResult> paginQuery(TbDwAnalyRuleParam tbDwAnalyRule, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbDwAnalyRule 实例对象
     * @return 实例对象
     */
    TbDwAnalyRule insert(TbDwAnalyRule tbDwAnalyRule);
    /** 
     * 更新数据
     *
     * @param tbDwAnalyRule 实例对象
     * @return 实例对象
     */
    TbDwAnalyRule update(TbDwAnalyRule tbDwAnalyRule);
    /** 
     * 通过主键删除数据
     *
     * @param sysDwRuleId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysDwRuleId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysDwRuleIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysDwRuleIdList);
}