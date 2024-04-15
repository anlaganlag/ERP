package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.B2bMabangOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrderItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrderItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bMabangOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

 /**
 * B2B马帮订单具体商品项item;(B2B_MABANG_ORDER_ITEM)--服务实现类
 * @author : LSY
 * @create : 2023-9-13
 */
@Slf4j
@Service
@Transactional
public class B2bMabangOrderItemServiceImpl extends ServiceImpl<B2bMabangOrderItemMapper, B2bMabangOrderItem>  implements B2bMabangOrderItemService {
    @Resource
    private B2bMabangOrderItemMapper b2bMabangOrderItemMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bMabangOrderItem queryById(String id){
        return b2bMabangOrderItemMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "external")
    @Override
    public Page<B2bMabangOrderItemResult> paginQuery(B2bMabangOrderItemParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<B2bMabangOrderItem> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getId()),B2bMabangOrderItem::getId, queryParam.getId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getHasGoods()),B2bMabangOrderItem::getHasGoods, queryParam.getHasGoods());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getOrderItemId()),B2bMabangOrderItem::getOrderItemId, queryParam.getOrderItemId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getErpOrderItemId()),B2bMabangOrderItem::getErpOrderItemId, queryParam.getErpOrderItemId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getIsCombo()),B2bMabangOrderItem::getIsCombo, queryParam.getIsCombo());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getItemId()),B2bMabangOrderItem::getItemId, queryParam.getItemId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getItemRemark()),B2bMabangOrderItem::getItemRemark, queryParam.getItemRemark());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getNoLiquidCosmetic()),B2bMabangOrderItem::getNoLiquidCosmetic, queryParam.getNoLiquidCosmetic());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getOriginOrderId()),B2bMabangOrderItem::getOriginOrderId, queryParam.getOriginOrderId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPictureUrl()),B2bMabangOrderItem::getPictureUrl, queryParam.getPictureUrl());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getOriginalPictureUrl()),B2bMabangOrderItem::getOriginalPictureUrl, queryParam.getOriginalPictureUrl());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPlatformSku()),B2bMabangOrderItem::getPlatformSku, queryParam.getPlatformSku());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getProductUnit()),B2bMabangOrderItem::getProductUnit, queryParam.getProductUnit());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSpecifics()),B2bMabangOrderItem::getSpecifics, queryParam.getSpecifics());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStatus()),B2bMabangOrderItem::getStatus, queryParam.getStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStockGrid()),B2bMabangOrderItem::getStockGrid, queryParam.getStockGrid());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStockSku()),B2bMabangOrderItem::getStockSku, queryParam.getStockSku());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStockStatus()),B2bMabangOrderItem::getStockStatus, queryParam.getStockStatus());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStockWarehouseId()),B2bMabangOrderItem::getStockWarehouseId, queryParam.getStockWarehouseId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTitle()),B2bMabangOrderItem::getTitle, queryParam.getTitle());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTransactionId()),B2bMabangOrderItem::getTransactionId, queryParam.getTransactionId());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getStockWarehouseName()),B2bMabangOrderItem::getStockWarehouseName, queryParam.getStockWarehouseName());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCostPrice()),B2bMabangOrderItem::getCostPrice, queryParam.getCostPrice());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPlatformQuantity()),B2bMabangOrderItem::getPlatformQuantity, queryParam.getPlatformQuantity());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getQuantity()),B2bMabangOrderItem::getQuantity, queryParam.getQuantity());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSellPrice()),B2bMabangOrderItem::getSellPrice, queryParam.getSellPrice());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSellPriceOrigin()),B2bMabangOrderItem::getSellPriceOrigin, queryParam.getSellPriceOrigin());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getUnitWeight()),B2bMabangOrderItem::getUnitWeight, queryParam.getUnitWeight());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncTime()),B2bMabangOrderItem::getSyncTime, queryParam.getSyncTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTime()),B2bMabangOrderItem::getCreateTime, queryParam.getCreateTime());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getUpdateTime()),B2bMabangOrderItem::getUpdateTime, queryParam.getUpdateTime());
        //2. 执行分页查询
        Page<B2bMabangOrderItemResult> pagin = new Page<>(current , size , true);
        IPage<B2bMabangOrderItemResult> selectResult = b2bMabangOrderItemMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param b2bMabangOrderItem 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bMabangOrderItem insert(B2bMabangOrderItem b2bMabangOrderItem){
        b2bMabangOrderItemMapper.insert(b2bMabangOrderItem);
        return b2bMabangOrderItem;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bMabangOrderItem update(B2bMabangOrderItem entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<B2bMabangOrderItem> chainWrapper = new LambdaUpdateChainWrapper<>(b2bMabangOrderItemMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getHasGoods()),B2bMabangOrderItem::getHasGoods, entityParam.getHasGoods());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getOrderItemId()),B2bMabangOrderItem::getOrderItemId, entityParam.getOrderItemId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getErpOrderItemId()),B2bMabangOrderItem::getErpOrderItemId, entityParam.getErpOrderItemId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getIsCombo()),B2bMabangOrderItem::getIsCombo, entityParam.getIsCombo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getItemId()),B2bMabangOrderItem::getItemId, entityParam.getItemId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getItemRemark()),B2bMabangOrderItem::getItemRemark, entityParam.getItemRemark());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getNoLiquidCosmetic()),B2bMabangOrderItem::getNoLiquidCosmetic, entityParam.getNoLiquidCosmetic());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getOriginOrderId()),B2bMabangOrderItem::getOriginOrderId, entityParam.getOriginOrderId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getPictureUrl()),B2bMabangOrderItem::getPictureUrl, entityParam.getPictureUrl());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getOriginalPictureUrl()),B2bMabangOrderItem::getOriginalPictureUrl, entityParam.getOriginalPictureUrl());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getPlatformSku()),B2bMabangOrderItem::getPlatformSku, entityParam.getPlatformSku());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getProductUnit()),B2bMabangOrderItem::getProductUnit, entityParam.getProductUnit());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSpecifics()),B2bMabangOrderItem::getSpecifics, entityParam.getSpecifics());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStatus()),B2bMabangOrderItem::getStatus, entityParam.getStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStockGrid()),B2bMabangOrderItem::getStockGrid, entityParam.getStockGrid());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStockSku()),B2bMabangOrderItem::getStockSku, entityParam.getStockSku());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStockStatus()),B2bMabangOrderItem::getStockStatus, entityParam.getStockStatus());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStockWarehouseId()),B2bMabangOrderItem::getStockWarehouseId, entityParam.getStockWarehouseId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTitle()),B2bMabangOrderItem::getTitle, entityParam.getTitle());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTransactionId()),B2bMabangOrderItem::getTransactionId, entityParam.getTransactionId());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getStockWarehouseName()),B2bMabangOrderItem::getStockWarehouseName, entityParam.getStockWarehouseName());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getCostPrice()),B2bMabangOrderItem::getCostPrice, entityParam.getCostPrice());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getPlatformQuantity()),B2bMabangOrderItem::getPlatformQuantity, entityParam.getPlatformQuantity());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getQuantity()),B2bMabangOrderItem::getQuantity, entityParam.getQuantity());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSellPrice()),B2bMabangOrderItem::getSellPrice, entityParam.getSellPrice());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSellPriceOrigin()),B2bMabangOrderItem::getSellPriceOrigin, entityParam.getSellPriceOrigin());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getUnitWeight()),B2bMabangOrderItem::getUnitWeight, entityParam.getUnitWeight());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSyncTime()),B2bMabangOrderItem::getSyncTime, entityParam.getSyncTime());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getUpdateTime()),B2bMabangOrderItem::getUpdateTime, entityParam.getUpdateTime());
        //2. 设置主键，并更新
        chainWrapper.eq(B2bMabangOrderItem::getId, entityParam.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getId());
        }else{
            return entityParam;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteById(String id){
        int total = b2bMabangOrderItemMapper.deleteById(id);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteBatchIds(List<String> idList){
         int delCount = b2bMabangOrderItemMapper.deleteBatchIds(idList);
         if (idList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
     /**
      * B2B订单时抓取创建完成的状态，可能会删减里面的物料信息，因此每次抓取回来的时候直接删除原来的，然后保存最新的
      * @param itemList
      */
     @DataSource(name = "external")
     @Override
     @Transactional(rollbackFor = Exception.class)
     public void refreshData(List<B2bMabangOrderItem> itemList) {
         if (ObjectUtil.isEmpty(itemList)) {
             return;
         }
         String orderId = itemList.get(0).getOriginOrderId();
         LambdaQueryWrapper<B2bMabangOrderItem> tWrapper = new LambdaQueryWrapper<>();
         tWrapper.eq(B2bMabangOrderItem::getOriginOrderId, orderId);
         this.getBaseMapper().delete(tWrapper);
         this.saveOrUpdateBatch(itemList);

     }
 }