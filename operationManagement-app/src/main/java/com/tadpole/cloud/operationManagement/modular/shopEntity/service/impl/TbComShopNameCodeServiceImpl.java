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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopNameCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopNameCodeResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopNameCode;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopNameCodeMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopNameCodeService;
 /**
 * 资源-店铺编码;(Tb_Com_Shop_Name_Code)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopNameCodeServiceImpl extends ServiceImpl<TbComShopNameCodeMapper, TbComShopNameCode>  implements TbComShopNameCodeService{
    @Resource
    private TbComShopNameCodeMapper tbComShopNameCodeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shopNameSimple 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopNameCode queryById(String shopNameSimple){
        return tbComShopNameCodeMapper.selectById(shopNameSimple);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopNameCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopNameCodeResult> paginQuery(TbComShopNameCodeParam tbComShopNameCode, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopNameCode> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopNameCode.getCode())){
            queryWrapper.eq(TbComShopNameCode::getCode, tbComShopNameCode.getCode());
        }
        //2. 执行分页查询
        Page<TbComShopNameCodeResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopNameCodeResult> selectResult = tbComShopNameCodeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopNameCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopNameCode insert(TbComShopNameCode tbComShopNameCode){
        tbComShopNameCodeMapper.insert(tbComShopNameCode);
        return tbComShopNameCode;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopNameCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopNameCode update(TbComShopNameCode tbComShopNameCode){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopNameCode> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopNameCodeMapper);
        if(StrUtil.isNotBlank(tbComShopNameCode.getCode())){
            chainWrapper.set(TbComShopNameCode::getCode, tbComShopNameCode.getCode());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopNameCode::getShopNameSimple, tbComShopNameCode.getShopNameSimple());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComShopNameCode.getShopNameSimple());
        }else{
            return tbComShopNameCode;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param shopNameSimple 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String shopNameSimple){
        int total = tbComShopNameCodeMapper.deleteById(shopNameSimple);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param shopNameSimpleList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> shopNameSimpleList){
         int delCount = tbComShopNameCodeMapper.deleteBatchIds(shopNameSimpleList);
         if (shopNameSimpleList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}