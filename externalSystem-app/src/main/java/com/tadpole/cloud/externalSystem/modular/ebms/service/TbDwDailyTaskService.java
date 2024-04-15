package com.tadpole.cloud.externalSystem.modular.ebms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwDailyTask;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwDailyTaskParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwDailyTaskResult;
import java.util.List;

 /**
 * TBDWDailyTask;(TB_DW_Daily_Task)--服务接口
 * @author : LSY
 * @date : 2023-8-14
 */
public interface TbDwDailyTaskService extends IService<TbDwDailyTask> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwDailyId 主键
     * @return 实例对象
     */
    TbDwDailyTask queryById(BigDecimal sysDwDailyId);
    
    /**
     * 分页查询
     *
     * @param tbDwDailyTask 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbDwDailyTaskResult> paginQuery(TbDwDailyTaskParam tbDwDailyTask, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbDwDailyTask 实例对象
     * @return 实例对象
     */
    TbDwDailyTask insert(TbDwDailyTask tbDwDailyTask);
    /** 
     * 更新数据
     *
     * @param tbDwDailyTask 实例对象
     * @return 实例对象
     */
    TbDwDailyTask update(TbDwDailyTask tbDwDailyTask);
    /** 
     * 通过主键删除数据
     *
     * @param sysDwDailyId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysDwDailyId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysDwDailyIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysDwDailyIdList);
}