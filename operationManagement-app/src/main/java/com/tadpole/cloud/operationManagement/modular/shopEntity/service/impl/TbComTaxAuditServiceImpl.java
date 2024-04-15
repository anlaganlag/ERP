package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxAuditResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxAudit;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxAuditMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxAuditService;
 /**
 * 资源-税号查账记录;(Tb_Com_Tax_Audit)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComTaxAuditServiceImpl extends ServiceImpl<TbComTaxAuditMapper, TbComTaxAudit>  implements TbComTaxAuditService{
    @Resource
    private TbComTaxAuditMapper tbComTaxAuditMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param caseNo 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAudit queryById(String caseNo){
        return tbComTaxAuditMapper.selectById(caseNo);
    }
    
    /**
     * 分页查询
     *
     * @param tbComTaxAudit 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComTaxAuditResult> paginQuery(TbComTaxAuditParam tbComTaxAudit, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComTaxAudit> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComTaxAudit.getTaxnOverseas())){
            queryWrapper.eq(TbComTaxAudit::getTaxnOverseas, tbComTaxAudit.getTaxnOverseas());
        }
        //2. 执行分页查询
        Page<TbComTaxAuditResult> pagin = new Page<>(current , size , true);
        IPage<TbComTaxAuditResult> selectResult = tbComTaxAuditMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComTaxAudit 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAudit insert(TbComTaxAudit tbComTaxAudit){
        tbComTaxAuditMapper.insert(tbComTaxAudit);
        return tbComTaxAudit;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComTaxAudit 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxAudit update(TbComTaxAudit tbComTaxAudit){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComTaxAudit> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxAuditMapper);
        if(StrUtil.isNotBlank(tbComTaxAudit.getTaxnOverseas())){
            chainWrapper.set(TbComTaxAudit::getTaxnOverseas, tbComTaxAudit.getTaxnOverseas());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComTaxAudit::getCaseNo, tbComTaxAudit.getCaseNo());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComTaxAudit.getCaseNo());
        }else{
            return tbComTaxAudit;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param caseNo 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String caseNo){
        int total = tbComTaxAuditMapper.deleteById(caseNo);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param caseNoList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> caseNoList){
         int delCount = tbComTaxAuditMapper.deleteBatchIds(caseNoList);
         if (caseNoList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}