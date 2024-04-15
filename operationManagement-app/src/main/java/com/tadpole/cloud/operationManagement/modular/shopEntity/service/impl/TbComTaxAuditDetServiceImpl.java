package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;

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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxAuditDetResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxAuditDet;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxAuditDetMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxAuditDetService;
 /**
 * 资源-税号查账记录明细;(Tb_Com_Tax_Audit_Det)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComTaxAuditDetServiceImpl extends ServiceImpl<TbComTaxAuditDetMapper, TbComTaxAuditDet>  implements TbComTaxAuditDetService{
    @Resource
    private TbComTaxAuditDetMapper tbComTaxAuditDetMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param caseNoDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAuditDet queryById(BigDecimal caseNoDetId){
        return tbComTaxAuditDetMapper.selectById(caseNoDetId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComTaxAuditDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComTaxAuditDetResult> paginQuery(TbComTaxAuditDetParam tbComTaxAuditDet, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComTaxAuditDet> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getCaseNo())){
            queryWrapper.eq(TbComTaxAuditDet::getCaseNo, tbComTaxAuditDet.getCaseNo());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getRequireInfo())){
            queryWrapper.eq(TbComTaxAuditDet::getRequireInfo, tbComTaxAuditDet.getRequireInfo());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getAuditResult())){
            queryWrapper.eq(TbComTaxAuditDet::getAuditResult, tbComTaxAuditDet.getAuditResult());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getCaseNoDetFiles())){
            queryWrapper.eq(TbComTaxAuditDet::getCaseNoDetFiles, tbComTaxAuditDet.getCaseNoDetFiles());
        }
        //2. 执行分页查询
        Page<TbComTaxAuditDetResult> pagin = new Page<>(current , size , true);
        IPage<TbComTaxAuditDetResult> selectResult = tbComTaxAuditDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComTaxAuditDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAuditDet insert(TbComTaxAuditDet tbComTaxAuditDet){
        tbComTaxAuditDetMapper.insert(tbComTaxAuditDet);
        return tbComTaxAuditDet;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComTaxAuditDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAuditDet update(TbComTaxAuditDet tbComTaxAuditDet){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComTaxAuditDet> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxAuditDetMapper);
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getCaseNo())){
            chainWrapper.set(TbComTaxAuditDet::getCaseNo, tbComTaxAuditDet.getCaseNo());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getRequireInfo())){
            chainWrapper.set(TbComTaxAuditDet::getRequireInfo, tbComTaxAuditDet.getRequireInfo());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getAuditResult())){
            chainWrapper.set(TbComTaxAuditDet::getAuditResult, tbComTaxAuditDet.getAuditResult());
        }
        if(StrUtil.isNotBlank(tbComTaxAuditDet.getCaseNoDetFiles())){
            chainWrapper.set(TbComTaxAuditDet::getCaseNoDetFiles, tbComTaxAuditDet.getCaseNoDetFiles());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComTaxAuditDet::getCaseNoDetId, tbComTaxAuditDet.getCaseNoDetId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComTaxAuditDet.getCaseNoDetId());
        }else{
            return tbComTaxAuditDet;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param caseNoDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal caseNoDetId){
        int total = tbComTaxAuditDetMapper.deleteById(caseNoDetId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param caseNoDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> caseNoDetIdList){
         int delCount = tbComTaxAuditDetMapper.deleteBatchIds(caseNoDetIdList);
         if (caseNoDetIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}