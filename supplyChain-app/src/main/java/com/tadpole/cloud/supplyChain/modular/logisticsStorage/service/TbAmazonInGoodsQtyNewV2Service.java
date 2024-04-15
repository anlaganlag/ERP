package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbAmazonInGoodsQtyNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbAmazonInGoodsQtyNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbAmazonInGoodsQtyNewV2Result;

import java.math.BigDecimal;
import java.util.List;

/**
 * Amazon在途库存报表;(Tb_Amazon_In_Goods_Qty_New_V2)表服务接口
 * @author : LSY
 * @date : 2024-3-18
 */
public interface TbAmazonInGoodsQtyNewV2Service extends IService<TbAmazonInGoodsQtyNewV2> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbAmazonInGoodsQtyNewV2 queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbAmazonInGoodsQtyNewV2 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbAmazonInGoodsQtyNewV2Result> paginQuery(TbAmazonInGoodsQtyNewV2Param tbAmazonInGoodsQtyNewV2, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbAmazonInGoodsQtyNewV2 实例对象
     * @return 实例对象
     */
    TbAmazonInGoodsQtyNewV2 insert(TbAmazonInGoodsQtyNewV2 tbAmazonInGoodsQtyNewV2);
    /** 
     * 更新数据
     *
     * @param tbAmazonInGoodsQtyNewV2 实例对象
     * @return 实例对象
     */
    TbAmazonInGoodsQtyNewV2 update(TbAmazonInGoodsQtyNewV2Param tbAmazonInGoodsQtyNewV2);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);
}