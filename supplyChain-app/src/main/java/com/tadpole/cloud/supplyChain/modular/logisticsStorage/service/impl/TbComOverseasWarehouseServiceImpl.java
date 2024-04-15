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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbComOverseasWarehouse;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbComOverseasWarehouseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbComOverseasWarehouseService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbComOverseasWarehouseResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbComOverseasWarehouseParam;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 海外仓信息;(Tb_Com_Overseas_Warehouse)表服务实现类
 * @author : LSY
 * @date : 2024-1-19
 */
@Service
@Transactional
@Slf4j
public class TbComOverseasWarehouseServiceImpl  extends ServiceImpl<TbComOverseasWarehouseMapper, TbComOverseasWarehouse> implements TbComOverseasWarehouseService{
    @Resource
    private TbComOverseasWarehouseMapper tbComOverseasWarehouseMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param owName 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbComOverseasWarehouse queryById(String owName){
        return tbComOverseasWarehouseMapper.selectById(owName);
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
    public Page<TbComOverseasWarehouseResult> paginQuery(TbComOverseasWarehouseParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComOverseasWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbComOverseasWarehouse::getElePlatformName, param.getElePlatformName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbComOverseasWarehouse::getCountryCode, param.getCountryCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOwState()),TbComOverseasWarehouse::getOwState, param.getOwState());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOwName()),TbComOverseasWarehouse::getOwName, param.getOwName());
        //2. 执行分页查询
        Page<TbComOverseasWarehouseResult> pagin = new Page<>(current , size , true);
        IPage<TbComOverseasWarehouseResult> selectResult = tbComOverseasWarehouseMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComOverseasWarehouse 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbComOverseasWarehouse insert(TbComOverseasWarehouse tbComOverseasWarehouse){
        tbComOverseasWarehouseMapper.insert(tbComOverseasWarehouse);
        return tbComOverseasWarehouse;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbComOverseasWarehouse update(TbComOverseasWarehouseParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComOverseasWarehouse> wrapper = new LambdaUpdateChainWrapper<TbComOverseasWarehouse>(tbComOverseasWarehouseMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwState()),TbComOverseasWarehouse::getOwState, param.getOwState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwAddress()),TbComOverseasWarehouse::getOwAddress, param.getOwAddress());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwPerson()),TbComOverseasWarehouse::getOwPerson, param.getOwPerson());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwTel()),TbComOverseasWarehouse::getOwTel, param.getOwTel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwEmail()),TbComOverseasWarehouse::getOwEmail, param.getOwEmail());

        //2. 设置主键，并更新
        wrapper.eq(TbComOverseasWarehouse::getOwName, param.getOwName());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getOwName());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param owName 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String owName){
        int total = tbComOverseasWarehouseMapper.deleteById(owName);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param owNameList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> owNameList) {
        int delCount = tbComOverseasWarehouseMapper.deleteBatchIds(owNameList);
        if (owNameList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}