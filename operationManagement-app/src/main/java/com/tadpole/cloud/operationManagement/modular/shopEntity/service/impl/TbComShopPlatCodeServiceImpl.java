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
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopPlatCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopPlatCodeResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopPlatCode;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopPlatCodeMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopPlatCodeService;
 /**
 * 资源-店铺平台编码;(Tb_Com_Shop_Plat_Code)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopPlatCodeServiceImpl extends ServiceImpl<TbComShopPlatCodeMapper, TbComShopPlatCode>  implements TbComShopPlatCodeService{
    @Resource
    private TbComShopPlatCodeMapper tbComShopPlatCodeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param elePlatformName 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopPlatCode queryById(String elePlatformName){
        return tbComShopPlatCodeMapper.selectById(elePlatformName);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopPlatCode 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopPlatCodeResult> paginQuery(TbComShopPlatCodeParam tbComShopPlatCode, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopPlatCode> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopPlatCode.getCode())){
            queryWrapper.eq(TbComShopPlatCode::getCode, tbComShopPlatCode.getCode());
        }
        //2. 执行分页查询
        Page<TbComShopPlatCodeResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopPlatCodeResult> selectResult = tbComShopPlatCodeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopPlatCode insert(TbComShopPlatCode tbComShopPlatCode){
        tbComShopPlatCodeMapper.insert(tbComShopPlatCode);
        return tbComShopPlatCode;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopPlatCode update(TbComShopPlatCode tbComShopPlatCode){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopPlatCode> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopPlatCodeMapper);
        if(StrUtil.isNotBlank(tbComShopPlatCode.getCode())){
            chainWrapper.set(TbComShopPlatCode::getCode, tbComShopPlatCode.getCode());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopPlatCode::getElePlatformName, tbComShopPlatCode.getElePlatformName());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComShopPlatCode.getElePlatformName());
        }else{
            return tbComShopPlatCode;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param elePlatformName 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String elePlatformName){
        int total = tbComShopPlatCodeMapper.deleteById(elePlatformName);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param elePlatformNameList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> elePlatformNameList){
         int delCount = tbComShopPlatCodeMapper.deleteBatchIds(elePlatformNameList);
         if (elePlatformNameList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}