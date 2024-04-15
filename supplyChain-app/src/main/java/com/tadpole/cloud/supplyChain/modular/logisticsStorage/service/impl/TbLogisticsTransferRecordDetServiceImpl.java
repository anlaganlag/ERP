package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecordDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsTransferRecordDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsTransferRecordDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsTransferRecordDetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
/**
 * 物流调拨记录明细;(tb_logistics_transfer_record_det)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsTransferRecordDetServiceImpl  extends ServiceImpl<TbLogisticsTransferRecordDetMapper, TbLogisticsTransferRecordDet> implements TbLogisticsTransferRecordDetService {
    @Resource
    private TbLogisticsTransferRecordDetMapper tbLogisticsTransferRecordDetMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecordDet queryById(BigDecimal sysDetId){
        return tbLogisticsTransferRecordDetMapper.selectById(sysDetId);
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
    public Page<TbLogisticsTransferRecordDetResult> paginQuery(TbLogisticsTransferRecordDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsTransferRecordDet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMateCode()),TbLogisticsTransferRecordDet::getMateCode, param.getMateCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsTransferRecordDet::getPackDetBoxCode, param.getPackDetBoxCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxNum()),TbLogisticsTransferRecordDet::getPackDetBoxNum, param.getPackDetBoxNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),TbLogisticsTransferRecordDet::getSku, param.getSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFnSku()),TbLogisticsTransferRecordDet::getFnSku, param.getFnSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferOutHouseCode()),TbLogisticsTransferRecordDet::getTransferOutHouseCode, param.getTransferOutHouseCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferOutHouseName()),TbLogisticsTransferRecordDet::getTransferOutHouseName, param.getTransferOutHouseName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferInHouseCode()),TbLogisticsTransferRecordDet::getTransferInHouseCode, param.getTransferInHouseCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferInHouseName()),TbLogisticsTransferRecordDet::getTransferInHouseName, param.getTransferInHouseName());
            queryWrapper.eq(TbLogisticsTransferRecordDet::getSysId, param.getSysId());
        //2. 执行分页查询
        Page<TbLogisticsTransferRecordDetResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsTransferRecordDetResult> selectResult = tbLogisticsTransferRecordDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsTransferRecordDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecordDet insert(TbLogisticsTransferRecordDet tbLogisticsTransferRecordDet){
        tbLogisticsTransferRecordDetMapper.insert(tbLogisticsTransferRecordDet);
        return tbLogisticsTransferRecordDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsTransferRecordDet update(TbLogisticsTransferRecordDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsTransferRecordDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsTransferRecordDet>(tbLogisticsTransferRecordDetMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getMateCode()),TbLogisticsTransferRecordDet::getMateCode, param.getMateCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsTransferRecordDet::getPackDetBoxCode, param.getPackDetBoxCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxNum()),TbLogisticsTransferRecordDet::getPackDetBoxNum, param.getPackDetBoxNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSku()),TbLogisticsTransferRecordDet::getSku, param.getSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFnSku()),TbLogisticsTransferRecordDet::getFnSku, param.getFnSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTransferOutHouseCode()),TbLogisticsTransferRecordDet::getTransferOutHouseCode, param.getTransferOutHouseCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTransferOutHouseName()),TbLogisticsTransferRecordDet::getTransferOutHouseName, param.getTransferOutHouseName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTransferInHouseCode()),TbLogisticsTransferRecordDet::getTransferInHouseCode, param.getTransferInHouseCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTransferInHouseName()),TbLogisticsTransferRecordDet::getTransferInHouseName, param.getTransferInHouseName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsTransferRecordDet::getSysDetId, param.getSysDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getSysDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal sysDetId){
        int total = tbLogisticsTransferRecordDetMapper.deleteById(sysDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param sysDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysDetIdList) {
        int delCount = tbLogisticsTransferRecordDetMapper.deleteBatchIds(sysDetIdList);
        if (sysDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据 物流调拨记录ID （SysId）查询调拨明细
      * @param sysId
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsTransferRecordDet> queryBySysId(Integer sysId) {
         LambdaQueryWrapper<TbLogisticsTransferRecordDet> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsTransferRecordDet::getSysId, sysId);
         return tbLogisticsTransferRecordDetMapper.selectList(queryWrapper);
     }

 }