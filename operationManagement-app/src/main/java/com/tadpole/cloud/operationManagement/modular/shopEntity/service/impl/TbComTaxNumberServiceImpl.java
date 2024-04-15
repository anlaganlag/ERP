package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNumber;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComTaxNumberMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumberParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumberResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
 /**
 * 资源-税号管理;(Tb_Com_Tax_Number)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComTaxNumberServiceImpl extends ServiceImpl<TbComTaxNumberMapper, TbComTaxNumber>  implements TbComTaxNumberService{
    @Resource
    private TbComTaxNumberMapper tbComTaxNumberMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNumber queryById(String id){
        return tbComTaxNumberMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param tbComTaxNumber 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComTaxNumberResult> paginQuery(TbComTaxNumberParam tbComTaxNumber, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComTaxNumber> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComTaxNumber.getElePlatformName())){
            queryWrapper.eq(TbComTaxNumber::getElePlatformName, tbComTaxNumber.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getSellerId())){
            queryWrapper.eq(TbComTaxNumber::getSellerId, tbComTaxNumber.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getCountryCode())){
            queryWrapper.eq(TbComTaxNumber::getCountryCode, tbComTaxNumber.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getTaxNumber())){
            queryWrapper.eq(TbComTaxNumber::getTaxNumber, tbComTaxNumber.getTaxNumber());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComName())){
            queryWrapper.eq(TbComTaxNumber::getComName, tbComTaxNumber.getComName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComAddr())){
            queryWrapper.eq(TbComTaxNumber::getComAddr, tbComTaxNumber.getComAddr());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComPostCode())){
            queryWrapper.eq(TbComTaxNumber::getComPostCode, tbComTaxNumber.getComPostCode());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComTel())){
            queryWrapper.eq(TbComTaxNumber::getComTel, tbComTaxNumber.getComTel());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComEmail())){
            queryWrapper.eq(TbComTaxNumber::getComEmail, tbComTaxNumber.getComEmail());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComHeadName())){
            queryWrapper.eq(TbComTaxNumber::getComHeadName, tbComTaxNumber.getComHeadName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComProfile())){
            queryWrapper.eq(TbComTaxNumber::getComProfile, tbComTaxNumber.getComProfile());
        }
        //2. 执行分页查询
        Page<TbComTaxNumberResult> pagin = new Page<>(current , size , true);
        IPage<TbComTaxNumberResult> selectResult = tbComTaxNumberMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComTaxNumber 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNumber insert(TbComTaxNumber tbComTaxNumber){
        tbComTaxNumberMapper.insert(tbComTaxNumber);
        return tbComTaxNumber;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComTaxNumber 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComTaxNumber update(TbComTaxNumber tbComTaxNumber){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComTaxNumber> chainWrapper = new LambdaUpdateChainWrapper<>(tbComTaxNumberMapper);
        if(StrUtil.isNotBlank(tbComTaxNumber.getElePlatformName())){
            chainWrapper.set(TbComTaxNumber::getElePlatformName, tbComTaxNumber.getElePlatformName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getSellerId())){
            chainWrapper.set(TbComTaxNumber::getSellerId, tbComTaxNumber.getSellerId());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getCountryCode())){
            chainWrapper.set(TbComTaxNumber::getCountryCode, tbComTaxNumber.getCountryCode());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getTaxNumber())){
            chainWrapper.set(TbComTaxNumber::getTaxNumber, tbComTaxNumber.getTaxNumber());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComName())){
            chainWrapper.set(TbComTaxNumber::getComName, tbComTaxNumber.getComName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComAddr())){
            chainWrapper.set(TbComTaxNumber::getComAddr, tbComTaxNumber.getComAddr());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComPostCode())){
            chainWrapper.set(TbComTaxNumber::getComPostCode, tbComTaxNumber.getComPostCode());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComTel())){
            chainWrapper.set(TbComTaxNumber::getComTel, tbComTaxNumber.getComTel());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComEmail())){
            chainWrapper.set(TbComTaxNumber::getComEmail, tbComTaxNumber.getComEmail());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComHeadName())){
            chainWrapper.set(TbComTaxNumber::getComHeadName, tbComTaxNumber.getComHeadName());
        }
        if(StrUtil.isNotBlank(tbComTaxNumber.getComProfile())){
            chainWrapper.set(TbComTaxNumber::getComProfile, tbComTaxNumber.getComProfile());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComTaxNumber::getId, tbComTaxNumber.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComTaxNumber.getId());
        }else{
            return tbComTaxNumber;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String id){
        int total = tbComTaxNumberMapper.deleteById(id);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> idList){
         int delCount = tbComTaxNumberMapper.deleteBatchIds(idList);
         if (idList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }


 }