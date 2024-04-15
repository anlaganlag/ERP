package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimDetToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimDetToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimDetToAmazonParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊物流索赔明细;(tb_logistics_claim_det_to_amazon)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsClaimDetToAmazonService extends IService<TbLogisticsClaimDetToAmazon> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkClaimDetId 主键
     * @return 实例对象
     */
    TbLogisticsClaimDetToAmazon queryById(BigDecimal pkClaimDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsClaimDetToAmazon 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsClaimDetToAmazonResult> paginQuery(TbLogisticsClaimDetToAmazonParam tbLogisticsClaimDetToAmazon, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsClaimDetToAmazon 实例对象
     * @return 实例对象
     */
    TbLogisticsClaimDetToAmazon insert(TbLogisticsClaimDetToAmazon tbLogisticsClaimDetToAmazon);
    /** 
     * 更新数据
     *
     * @param tbLogisticsClaimDetToAmazon 实例对象
     * @return 实例对象
     */
    TbLogisticsClaimDetToAmazon update(TbLogisticsClaimDetToAmazonParam tbLogisticsClaimDetToAmazon);
    /** 
     * 通过主键删除数据
     *
     * @param pkClaimDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkClaimDetId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkClaimDetIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkClaimDetIdList);
}