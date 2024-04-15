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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackListDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListDetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 亚马逊货件-明细-按SKU的汇总;(tb_logistics_pack_list_det)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackListDetServiceImpl  extends ServiceImpl<TbLogisticsPackListDetMapper, TbLogisticsPackListDet> implements TbLogisticsPackListDetService {
    @Resource
    private TbLogisticsPackListDetMapper tbLogisticsPackListDetMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shipDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListDet queryById(BigDecimal shipDetId){
        return tbLogisticsPackListDetMapper.selectById(shipDetId);
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
    public Page<TbLogisticsPackListDetResult> paginQuery(TbLogisticsPackListDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackListDet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListDet::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListDet::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsPackListDet::getMerchantSku, param.getMerchantSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAsin()),TbLogisticsPackListDet::getAsin, param.getAsin());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTitle()),TbLogisticsPackListDet::getTitle, param.getTitle());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFnsku()),TbLogisticsPackListDet::getFnsku, param.getFnsku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getExternalId()),TbLogisticsPackListDet::getExternalId, param.getExternalId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPrepType()),TbLogisticsPackListDet::getPrepType, param.getPrepType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getWhoWillLable()),TbLogisticsPackListDet::getWhoWillLable, param.getWhoWillLable());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListDet::getShipmentRealStatus, param.getShipmentRealStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListDet::getPackListCode, param.getPackListCode());
        //2. 执行分页查询
        Page<TbLogisticsPackListDetResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackListDetResult> selectResult = tbLogisticsPackListDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListDet insert(TbLogisticsPackListDet tbLogisticsPackListDet){
        tbLogisticsPackListDetMapper.insert(tbLogisticsPackListDet);
        return tbLogisticsPackListDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListDet update(TbLogisticsPackListDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackListDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackListDet>(tbLogisticsPackListDetMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListDet::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListDet::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsPackListDet::getMerchantSku, param.getMerchantSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAsin()),TbLogisticsPackListDet::getAsin, param.getAsin());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTitle()),TbLogisticsPackListDet::getTitle, param.getTitle());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFnsku()),TbLogisticsPackListDet::getFnsku, param.getFnsku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getExternalId()),TbLogisticsPackListDet::getExternalId, param.getExternalId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPrepType()),TbLogisticsPackListDet::getPrepType, param.getPrepType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getWhoWillLable()),TbLogisticsPackListDet::getWhoWillLable, param.getWhoWillLable());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListDet::getShipmentRealStatus, param.getShipmentRealStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListDet::getPackListCode, param.getPackListCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackListDet::getShipDetId, param.getShipDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getShipDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param shipDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal shipDetId){
        int total = tbLogisticsPackListDetMapper.deleteById(shipDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param shipDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> shipDetIdList) {
        int delCount = tbLogisticsPackListDetMapper.deleteBatchIds(shipDetIdList);
        if (shipDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackListDet> queryByShipmentId(String shipmentId) {
         LambdaQueryWrapper<TbLogisticsPackListDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListDet::getShipmentId, shipmentId);
        return tbLogisticsPackListDetMapper.selectList(wrapper);
     }

     /**
      * 根据shipmentId更新已装箱数量
      * @param shipmentId
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int updateBoxedQty(String shipmentId,String packListCode) {

         if (ObjectUtil.isNotEmpty(shipmentId)) {
             return tbLogisticsPackListDetMapper.updateBoxedQtyByShipmentId(shipmentId);
         }
         if (ObjectUtil.isNotEmpty(packListCode)) {
             return tbLogisticsPackListDetMapper.updateBoxedQtyByPackListCode(packListCode);
         }
         return 0;

     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackListDet> queryByPackListCode(String packListCode) {
         LambdaQueryWrapper<TbLogisticsPackListDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListDet::getPackListCode, packListCode);
         return tbLogisticsPackListDetMapper.selectList(wrapper);
     }

     @Override
     @DataSource(name = "logistics")
     public TbLogisticsPackListDet queryByPackListCodeAndSku(String packListCode, String sku) {
         LambdaQueryWrapper<TbLogisticsPackListDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListDet::getPackListCode, packListCode);
         wrapper.eq(TbLogisticsPackListDet::getMerchantSku, sku);
         List<TbLogisticsPackListDet> packListDetList = tbLogisticsPackListDetMapper.selectList(wrapper);
         if (ObjectUtil.isNotEmpty(packListDetList)) {
             return packListDetList.get(0);
         }
         return null;
     }

     @Override
     @DataSource(name = "logistics")
     public int deleteByPackListCode(String packListCode) {

         LambdaQueryWrapper<TbLogisticsPackListDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListDet::getPackListCode, packListCode);
         return tbLogisticsPackListDetMapper.delete(wrapper);
     }

     @Override
     @DataSource(name = "logistics")
     public int deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackListDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListDet::getPackCode, packCode);
         return tbLogisticsPackListDetMapper.delete(wrapper);

     }
 }