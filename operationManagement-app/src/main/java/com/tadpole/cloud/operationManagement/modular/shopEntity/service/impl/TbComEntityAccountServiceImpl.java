package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityAccount;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityAccountMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityAccountParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityAccountResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 资源-公司实体账户信息;(Tb_Com_Entity_Account)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityAccountServiceImpl extends ServiceImpl<TbComEntityAccountMapper, TbComEntityAccount>  implements TbComEntityAccountService{
    @Resource
    private TbComEntityAccountMapper tbComEntityAccountMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param comAccId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityAccount queryById(BigDecimal comAccId){
        return tbComEntityAccountMapper.selectById(comAccId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityAccount 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityAccountResult> paginQuery(TbComEntityAccountParam tbComEntityAccount, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityAccount> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityAccount.getComNameCn())){
            queryWrapper.eq(TbComEntityAccount::getComNameCn, tbComEntityAccount.getComNameCn());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccType())){
            queryWrapper.eq(TbComEntityAccount::getComAccType, tbComEntityAccount.getComAccType());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccBank())){
            queryWrapper.eq(TbComEntityAccount::getComAccBank, tbComEntityAccount.getComAccBank());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccNo())){
            queryWrapper.eq(TbComEntityAccount::getComAccNo, tbComEntityAccount.getComAccNo());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccCurrency())){
            queryWrapper.eq(TbComEntityAccount::getComAccCurrency, tbComEntityAccount.getComAccCurrency());
        }
        //2. 执行分页查询
        Page<TbComEntityAccountResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityAccountResult> selectResult = tbComEntityAccountMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityAccount 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityAccount insert(TbComEntityAccount tbComEntityAccount){
        tbComEntityAccountMapper.insert(tbComEntityAccount);
        return tbComEntityAccount;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityAccount 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityAccount update(TbComEntityAccount tbComEntityAccount){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityAccount> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityAccountMapper);
        if(StrUtil.isNotBlank(tbComEntityAccount.getComNameCn())){
            chainWrapper.set(TbComEntityAccount::getComNameCn, tbComEntityAccount.getComNameCn());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccType())){
            chainWrapper.set(TbComEntityAccount::getComAccType, tbComEntityAccount.getComAccType());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccBank())){
            chainWrapper.set(TbComEntityAccount::getComAccBank, tbComEntityAccount.getComAccBank());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccNo())){
            chainWrapper.set(TbComEntityAccount::getComAccNo, tbComEntityAccount.getComAccNo());
        }
        if(StrUtil.isNotBlank(tbComEntityAccount.getComAccCurrency())){
            chainWrapper.set(TbComEntityAccount::getComAccCurrency, tbComEntityAccount.getComAccCurrency());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityAccount::getComAccId, tbComEntityAccount.getComAccId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityAccount.getComAccId());
        }else{
            return tbComEntityAccount;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param comAccId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal comAccId){
        int total = tbComEntityAccountMapper.deleteById(comAccId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param comAccIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> comAccIdList){
         int delCount = tbComEntityAccountMapper.deleteBatchIds(comAccIdList);
         if (comAccIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}