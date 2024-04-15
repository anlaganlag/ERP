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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopCountryCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopCountryCodeResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopCountryCode;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopCountryCodeMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopCountryCodeService;
 /**
 * 资源-店铺站点编码;(Tb_Com_Shop_Country_Code)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopCountryCodeServiceImpl extends ServiceImpl<TbComShopCountryCodeMapper, TbComShopCountryCode>  implements TbComShopCountryCodeService{
    @Resource
    private TbComShopCountryCodeMapper tbComShopCountryCodeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param countryCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopCountryCode queryById(String countryCode){
        return tbComShopCountryCodeMapper.selectById(countryCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopCountryCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopCountryCodeResult> paginQuery(TbComShopCountryCodeParam tbComShopCountryCode, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopCountryCode> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopCountryCode.getCode())){
            queryWrapper.eq(TbComShopCountryCode::getCode, tbComShopCountryCode.getCode());
        }
        //2. 执行分页查询
        Page<TbComShopCountryCodeResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopCountryCodeResult> selectResult = tbComShopCountryCodeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopCountryCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopCountryCode insert(TbComShopCountryCode tbComShopCountryCode){
        tbComShopCountryCodeMapper.insert(tbComShopCountryCode);
        return tbComShopCountryCode;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopCountryCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopCountryCode update(TbComShopCountryCode tbComShopCountryCode){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopCountryCode> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopCountryCodeMapper);
        if(StrUtil.isNotBlank(tbComShopCountryCode.getCode())){
            chainWrapper.set(TbComShopCountryCode::getCode, tbComShopCountryCode.getCode());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopCountryCode::getCountryCode, tbComShopCountryCode.getCountryCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComShopCountryCode.getCountryCode());
        }else{
            return tbComShopCountryCode;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param countryCode 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String countryCode){
        int total = tbComShopCountryCodeMapper.deleteById(countryCode);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param countryCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> countryCodeList){
         int delCount = tbComShopCountryCodeMapper.deleteBatchIds(countryCodeList);
         if (countryCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}