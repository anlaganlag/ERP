package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbEPlatStorageCenter;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbEPlatStorageCenterMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbEPlatStorageCenterService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbEPlatStorageCenterResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbEPlatStorageCenterParam;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 电商平台仓储中心;(Tb_E_Plat_Storage_Center)表服务实现类
 * @author : LSY
 * @date : 2024-1-2
 */
@Service
@Transactional
@Slf4j
public class TbEPlatStorageCenterServiceImpl  extends ServiceImpl<TbEPlatStorageCenterMapper, TbEPlatStorageCenter> implements TbEPlatStorageCenterService{
    @Resource
    private TbEPlatStorageCenterMapper tbEPlatStorageCenterMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbEPlatStorageCenter queryById(BigDecimal id){
        return tbEPlatStorageCenterMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbEPlatStorageCenterResult> paginQuery(TbEPlatStorageCenterParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbEPlatStorageCenter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbEPlatStorageCenter::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbEPlatStorageCenter::getElePlatformName, param.getElePlatformName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbEPlatStorageCenter::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFbaCode()),TbEPlatStorageCenter::getFbaCode, param.getFbaCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCity()),TbEPlatStorageCenter::getCity, param.getCity());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getState()),TbEPlatStorageCenter::getState, param.getState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getZip()),TbEPlatStorageCenter::getZip, param.getZip());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAddress()),TbEPlatStorageCenter::getAddress, param.getAddress());
        //2. 执行分页查询
        Page<TbEPlatStorageCenterResult> pagin = new Page<>(current , size , true);
        IPage<TbEPlatStorageCenterResult> selectResult = tbEPlatStorageCenterMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbEPlatStorageCenter 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbEPlatStorageCenter insert(TbEPlatStorageCenter tbEPlatStorageCenter){
        tbEPlatStorageCenterMapper.insert(tbEPlatStorageCenter);
        return tbEPlatStorageCenter;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbEPlatStorageCenter update(TbEPlatStorageCenterParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbEPlatStorageCenter> wrapper = new LambdaUpdateChainWrapper<TbEPlatStorageCenter>(tbEPlatStorageCenterMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbEPlatStorageCenter::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbEPlatStorageCenter::getElePlatformName, param.getElePlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbEPlatStorageCenter::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFbaCode()),TbEPlatStorageCenter::getFbaCode, param.getFbaCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCity()),TbEPlatStorageCenter::getCity, param.getCity());
         wrapper.set(ObjectUtil.isNotEmpty(param.getState()),TbEPlatStorageCenter::getState, param.getState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getZip()),TbEPlatStorageCenter::getZip, param.getZip());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAddress()),TbEPlatStorageCenter::getAddress, param.getAddress());
        //2. 设置主键，并更新
        wrapper.eq(TbEPlatStorageCenter::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id){
        int total = tbEPlatStorageCenterMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbEPlatStorageCenterMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}