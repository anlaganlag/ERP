package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentRemind;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsShipmentRemindMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentRemindParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentRemindResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * TbLogisticsShipmentRemind--暂时不需要;(tb_logistics_shipment_remind)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsShipmentRemindServiceImpl  extends ServiceImpl<TbLogisticsShipmentRemindMapper, TbLogisticsShipmentRemind> implements TbLogisticsShipmentRemindService {
    @Resource
    private TbLogisticsShipmentRemindMapper tbLogisticsShipmentRemindMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentRemind queryById(BigDecimal sysId){
        return tbLogisticsShipmentRemindMapper.selectById(sysId);
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
    public Page<TbLogisticsShipmentRemindResult> paginQuery(TbLogisticsShipmentRemindParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsShipmentRemind> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentRemind::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackStoreHouseType()),TbLogisticsShipmentRemind::getPackStoreHouseType, param.getPackStoreHouseType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackStoreHouseName()),TbLogisticsShipmentRemind::getPackStoreHouseName, param.getPackStoreHouseName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsShipmentRemind::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsShipmentRemind::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsShipmentRemind::getShipmentId, param.getShipmentId());
        //2. 执行分页查询
        Page<TbLogisticsShipmentRemindResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsShipmentRemindResult> selectResult = tbLogisticsShipmentRemindMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentRemind 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentRemind insert(TbLogisticsShipmentRemind tbLogisticsShipmentRemind){
        tbLogisticsShipmentRemindMapper.insert(tbLogisticsShipmentRemind);
        return tbLogisticsShipmentRemind;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentRemind update(TbLogisticsShipmentRemindParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsShipmentRemind> wrapper = new LambdaUpdateChainWrapper<TbLogisticsShipmentRemind>(tbLogisticsShipmentRemindMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentRemind::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseType()),TbLogisticsShipmentRemind::getPackStoreHouseType, param.getPackStoreHouseType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackStoreHouseName()),TbLogisticsShipmentRemind::getPackStoreHouseName, param.getPackStoreHouseName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsShipmentRemind::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsShipmentRemind::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsShipmentRemind::getShipmentId, param.getShipmentId());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsShipmentRemind::getSysId, param.getSysId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getSysId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal sysId){
        int total = tbLogisticsShipmentRemindMapper.deleteById(sysId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysIdList) {
        int delCount = tbLogisticsShipmentRemindMapper.deleteBatchIds(sysIdList);
        if (sysIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}