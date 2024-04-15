package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwAnalyRule;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbDwAnalyRuleMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwAnalyRuleParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwAnalyRuleResult;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwAnalyRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * TbDWAnalyRule;(Tb_DW_Analy_Rule)--服务实现类
 * @author : LSY
 * @create : 2023-8-14
 */
@Slf4j
@Service
@Transactional
public class TbDwAnalyRuleServiceImpl extends ServiceImpl<TbDwAnalyRuleMapper, TbDwAnalyRule>  implements TbDwAnalyRuleService{
    @Resource
    private TbDwAnalyRuleMapper tbDwAnalyRuleMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwRuleId 主键
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwAnalyRule queryById(BigDecimal sysDwRuleId){
        return tbDwAnalyRuleMapper.selectById(sysDwRuleId);
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
    public Page<TbDwAnalyRuleResult> paginQuery(TbDwAnalyRuleParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbDwAnalyRule> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjNum()),TbDwAnalyRule::getDwDataObjNum, queryParam.getDwDataObjNum());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataTableObj()),TbDwAnalyRule::getDwDataTableObj, queryParam.getDwDataTableObj());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwReportProperty()),TbDwAnalyRule::getDwReportProperty, queryParam.getDwReportProperty());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwUnityProperty()),TbDwAnalyRule::getDwUnityProperty, queryParam.getDwUnityProperty());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwModelName()),TbDwAnalyRule::getDwModelName, queryParam.getDwModelName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwComments()),TbDwAnalyRule::getDwComments, queryParam.getDwComments());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwRemark()),TbDwAnalyRule::getDwRemark, queryParam.getDwRemark());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSysDwRuleId()),TbDwAnalyRule::getSysDwRuleId, queryParam.getSysDwRuleId());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwStatus()),TbDwAnalyRule::getDwStatus, queryParam.getDwStatus());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwCreateTime()),TbDwAnalyRule::getDwCreateTime, queryParam.getDwCreateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwLastUpdateTime()),TbDwAnalyRule::getDwLastUpdateTime, queryParam.getDwLastUpdateTime());
        //2. 执行分页查询
        Page<TbDwAnalyRuleResult> pagin = new Page<>(current , size , true);
        IPage<TbDwAnalyRuleResult> selectResult = tbDwAnalyRuleMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbDwAnalyRule 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwAnalyRule insert(TbDwAnalyRule tbDwAnalyRule){
        tbDwAnalyRuleMapper.insert(tbDwAnalyRule);
        return tbDwAnalyRule;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwAnalyRule update(TbDwAnalyRule entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbDwAnalyRule> chainWrapper = new LambdaUpdateChainWrapper<>(tbDwAnalyRuleMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjNum()),TbDwAnalyRule::getDwDataObjNum, entityParam.getDwDataObjNum());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataTableObj()),TbDwAnalyRule::getDwDataTableObj, entityParam.getDwDataTableObj());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwReportProperty()),TbDwAnalyRule::getDwReportProperty, entityParam.getDwReportProperty());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwUnityProperty()),TbDwAnalyRule::getDwUnityProperty, entityParam.getDwUnityProperty());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwModelName()),TbDwAnalyRule::getDwModelName, entityParam.getDwModelName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwComments()),TbDwAnalyRule::getDwComments, entityParam.getDwComments());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwRemark()),TbDwAnalyRule::getDwRemark, entityParam.getDwRemark());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwStatus()),TbDwAnalyRule::getDwStatus, entityParam.getDwStatus());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwCreateTime()),TbDwAnalyRule::getDwCreateTime, entityParam.getDwCreateTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwLastUpdateTime()),TbDwAnalyRule::getDwLastUpdateTime, entityParam.getDwLastUpdateTime());
        //2. 设置主键，并更新
        chainWrapper.eq(TbDwAnalyRule::getSysDwRuleId, entityParam.getSysDwRuleId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getSysDwRuleId());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDwRuleId 主键
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteById(BigDecimal sysDwRuleId){
        int total = tbDwAnalyRuleMapper.deleteById(sysDwRuleId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysDwRuleIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysDwRuleIdList){
         int delCount = tbDwAnalyRuleMapper.deleteBatchIds(sysDwRuleIdList);
         if (sysDwRuleIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}