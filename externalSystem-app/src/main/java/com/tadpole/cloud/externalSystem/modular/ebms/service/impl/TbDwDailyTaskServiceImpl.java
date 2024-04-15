package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.ObjectUtil;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwDailyTaskParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwDailyTaskResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwDailyTask;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbDwDailyTaskMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwDailyTaskService;
 /**
 * TBDWDailyTask;(TB_DW_Daily_Task)--服务实现类
 * @author : LSY
 * @create : 2023-8-14
 */
@Slf4j
@Service
@Transactional
public class TbDwDailyTaskServiceImpl extends ServiceImpl<TbDwDailyTaskMapper, TbDwDailyTask>  implements TbDwDailyTaskService{
    @Resource
    private TbDwDailyTaskMapper tbDwDailyTaskMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwDailyId 主键
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwDailyTask queryById(BigDecimal sysDwDailyId){
        return tbDwDailyTaskMapper.selectById(sysDwDailyId);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "EBMS")
    @Override
    public Page<TbDwDailyTaskResult> paginQuery(TbDwDailyTaskParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbDwDailyTask> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwdailyTaskName()),TbDwDailyTask::getDwdailyTaskName, queryParam.getDwdailyTaskName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjNum()),TbDwDailyTask::getDwDataObjNum, queryParam.getDwDataObjNum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjEnName()),TbDwDailyTask::getDwDataObjEnName, queryParam.getDwDataObjEnName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataTableObj()),TbDwDailyTask::getDwDataTableObj, queryParam.getDwDataTableObj());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlType()),TbDwDailyTask::getDwDlType, queryParam.getDwDlType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlExeactDate()),TbDwDailyTask::getDwDlExeactDate, queryParam.getDwDlExeactDate());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlComplateTime()),TbDwDailyTask::getDwDlComplateTime, queryParam.getDwDlComplateTime());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwSaveType()),TbDwDailyTask::getDwSaveType, queryParam.getDwSaveType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwHandleDetInfo()),TbDwDailyTask::getDwHandleDetInfo, queryParam.getDwHandleDetInfo());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwSellerId()),TbDwDailyTask::getDwSellerId, queryParam.getDwSellerId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwShopNameSimple()),TbDwDailyTask::getDwShopNameSimple, queryParam.getDwShopNameSimple());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwCountryCode()),TbDwDailyTask::getDwCountryCode, queryParam.getDwCountryCode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlPerson()),TbDwDailyTask::getDwDlPerson, queryParam.getDwDlPerson());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlPersonName()),TbDwDailyTask::getDwDlPersonName, queryParam.getDwDlPersonName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwUseDepart()),TbDwDailyTask::getDwUseDepart, queryParam.getDwUseDepart());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwFileUploadStatus()),TbDwDailyTask::getDwFileUploadStatus, queryParam.getDwFileUploadStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwFileUploadPath()),TbDwDailyTask::getDwFileUploadPath, queryParam.getDwFileUploadPath());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwFileUploadResult()),TbDwDailyTask::getDwFileUploadResult, queryParam.getDwFileUploadResult());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwFileHandleStatus()),TbDwDailyTask::getDwFileHandleStatus, queryParam.getDwFileHandleStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwFileHandleResult()),TbDwDailyTask::getDwFileHandleResult, queryParam.getDwFileHandleResult());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwType()),TbDwDailyTask::getDwType, queryParam.getDwType());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataReportTypeEnum()),TbDwDailyTask::getDwDataReportTypeEnum, queryParam.getDwDataReportTypeEnum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwAwsKey()),TbDwDailyTask::getDwAwsKey, queryParam.getDwAwsKey());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwEndPoint()),TbDwDailyTask::getDwEndPoint, queryParam.getDwEndPoint());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwMarkIdList()),TbDwDailyTask::getDwMarkIdList, queryParam.getDwMarkIdList());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwReportRequestId()),TbDwDailyTask::getDwReportRequestId, queryParam.getDwReportRequestId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwReportId()),TbDwDailyTask::getDwReportId, queryParam.getDwReportId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwTaskState()),TbDwDailyTask::getDwTaskState, queryParam.getDwTaskState());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwExecResult()),TbDwDailyTask::getDwExecResult, queryParam.getDwExecResult());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwExecServerIP()),TbDwDailyTask::getDwExecServerIP, queryParam.getDwExecServerIP());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwExecServerMac()),TbDwDailyTask::getDwExecServerMac, queryParam.getDwExecServerMac());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwPlatName()),TbDwDailyTask::getDwPlatName, queryParam.getDwPlatName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDocumentId()),TbDwDailyTask::getDocumentId, queryParam.getDocumentId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getReportStatus()),TbDwDailyTask::getReportStatus, queryParam.getReportStatus());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSysDwDailyId()),TbDwDailyTask::getSysDwDailyId, queryParam.getSysDwDailyId());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwTaskCreateTime()),TbDwDailyTask::getDwTaskCreateTime, queryParam.getDwTaskCreateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwFileUploadTime()),TbDwDailyTask::getDwFileUploadTime, queryParam.getDwFileUploadTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwFileHandleTime()),TbDwDailyTask::getDwFileHandleTime, queryParam.getDwFileHandleTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwHasData()),TbDwDailyTask::getDwHasData, queryParam.getDwHasData());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwIsSchedule()),TbDwDailyTask::getDwIsSchedule, queryParam.getDwIsSchedule());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwIsAutoDownload()),TbDwDailyTask::getDwIsAutoDownload, queryParam.getDwIsAutoDownload());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwDateBegin()),TbDwDailyTask::getDwDateBegin, queryParam.getDwDateBegin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwDateEnd()),TbDwDailyTask::getDwDateEnd, queryParam.getDwDateEnd());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwExecTime()),TbDwDailyTask::getDwExecTime, queryParam.getDwExecTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwComTime()),TbDwDailyTask::getDwComTime, queryParam.getDwComTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getIsPush()),TbDwDailyTask::getIsPush, queryParam.getIsPush());
        //2. 执行分页查询
        Page<TbDwDailyTaskResult> pagin = new Page<>(current , size , true);
        IPage<TbDwDailyTaskResult> selectResult = tbDwDailyTaskMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbDwDailyTask 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwDailyTask insert(TbDwDailyTask tbDwDailyTask){
        tbDwDailyTaskMapper.insert(tbDwDailyTask);
        return tbDwDailyTask;
    }
    
    /** 
     * 更新数据
     *
     * @param tbDwDailyTask 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwDailyTask update(TbDwDailyTask entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbDwDailyTask> chainWrapper = new LambdaUpdateChainWrapper<>(tbDwDailyTaskMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwdailyTaskName()),TbDwDailyTask::getDwdailyTaskName, entityParam.getDwdailyTaskName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjNum()),TbDwDailyTask::getDwDataObjNum, entityParam.getDwDataObjNum());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjEnName()),TbDwDailyTask::getDwDataObjEnName, entityParam.getDwDataObjEnName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataTableObj()),TbDwDailyTask::getDwDataTableObj, entityParam.getDwDataTableObj());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlType()),TbDwDailyTask::getDwDlType, entityParam.getDwDlType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlExeactDate()),TbDwDailyTask::getDwDlExeactDate, entityParam.getDwDlExeactDate());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlComplateTime()),TbDwDailyTask::getDwDlComplateTime, entityParam.getDwDlComplateTime());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwSaveType()),TbDwDailyTask::getDwSaveType, entityParam.getDwSaveType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwHandleDetInfo()),TbDwDailyTask::getDwHandleDetInfo, entityParam.getDwHandleDetInfo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwSellerId()),TbDwDailyTask::getDwSellerId, entityParam.getDwSellerId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwShopNameSimple()),TbDwDailyTask::getDwShopNameSimple, entityParam.getDwShopNameSimple());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwCountryCode()),TbDwDailyTask::getDwCountryCode, entityParam.getDwCountryCode());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlPerson()),TbDwDailyTask::getDwDlPerson, entityParam.getDwDlPerson());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlPersonName()),TbDwDailyTask::getDwDlPersonName, entityParam.getDwDlPersonName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwUseDepart()),TbDwDailyTask::getDwUseDepart, entityParam.getDwUseDepart());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwFileUploadStatus()),TbDwDailyTask::getDwFileUploadStatus, entityParam.getDwFileUploadStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwFileUploadPath()),TbDwDailyTask::getDwFileUploadPath, entityParam.getDwFileUploadPath());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwFileUploadResult()),TbDwDailyTask::getDwFileUploadResult, entityParam.getDwFileUploadResult());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwFileHandleStatus()),TbDwDailyTask::getDwFileHandleStatus, entityParam.getDwFileHandleStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwFileHandleResult()),TbDwDailyTask::getDwFileHandleResult, entityParam.getDwFileHandleResult());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwType()),TbDwDailyTask::getDwType, entityParam.getDwType());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataReportTypeEnum()),TbDwDailyTask::getDwDataReportTypeEnum, entityParam.getDwDataReportTypeEnum());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwAwsKey()),TbDwDailyTask::getDwAwsKey, entityParam.getDwAwsKey());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwEndPoint()),TbDwDailyTask::getDwEndPoint, entityParam.getDwEndPoint());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwMarkIdList()),TbDwDailyTask::getDwMarkIdList, entityParam.getDwMarkIdList());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwReportRequestId()),TbDwDailyTask::getDwReportRequestId, entityParam.getDwReportRequestId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwReportId()),TbDwDailyTask::getDwReportId, entityParam.getDwReportId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwTaskState()),TbDwDailyTask::getDwTaskState, entityParam.getDwTaskState());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwExecResult()),TbDwDailyTask::getDwExecResult, entityParam.getDwExecResult());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwExecServerIP()),TbDwDailyTask::getDwExecServerIP, entityParam.getDwExecServerIP());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwExecServerMac()),TbDwDailyTask::getDwExecServerMac, entityParam.getDwExecServerMac());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwPlatName()),TbDwDailyTask::getDwPlatName, entityParam.getDwPlatName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDocumentId()),TbDwDailyTask::getDocumentId, entityParam.getDocumentId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getReportStatus()),TbDwDailyTask::getReportStatus, entityParam.getReportStatus());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwTaskCreateTime()),TbDwDailyTask::getDwTaskCreateTime, entityParam.getDwTaskCreateTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwFileUploadTime()),TbDwDailyTask::getDwFileUploadTime, entityParam.getDwFileUploadTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwFileHandleTime()),TbDwDailyTask::getDwFileHandleTime, entityParam.getDwFileHandleTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwHasData()),TbDwDailyTask::getDwHasData, entityParam.getDwHasData());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwIsSchedule()),TbDwDailyTask::getDwIsSchedule, entityParam.getDwIsSchedule());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwIsAutoDownload()),TbDwDailyTask::getDwIsAutoDownload, entityParam.getDwIsAutoDownload());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwDateBegin()),TbDwDailyTask::getDwDateBegin, entityParam.getDwDateBegin());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwDateEnd()),TbDwDailyTask::getDwDateEnd, entityParam.getDwDateEnd());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwExecTime()),TbDwDailyTask::getDwExecTime, entityParam.getDwExecTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwComTime()),TbDwDailyTask::getDwComTime, entityParam.getDwComTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getIsPush()),TbDwDailyTask::getIsPush, entityParam.getIsPush());
        //2. 设置主键，并更新
        chainWrapper.eq(TbDwDailyTask::getSysDwDailyId, entityParam.getSysDwDailyId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getSysDwDailyId());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDwDailyId 主键
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteById(BigDecimal sysDwDailyId){
        int total = tbDwDailyTaskMapper.deleteById(sysDwDailyId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysDwDailyIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysDwDailyIdList){
         int delCount = tbDwDailyTaskMapper.deleteBatchIds(sysDwDailyIdList);
         if (sysDwDailyIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}