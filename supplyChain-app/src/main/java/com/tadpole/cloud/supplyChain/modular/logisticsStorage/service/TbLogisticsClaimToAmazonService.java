package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.DownloadLogisticsAmazonClaimsViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsAmazonClaimsEmailViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimToAmazonParam;
import java.util.List;

/**
 * 亚马逊物流索赔;(tb_logistics_claim_to_amazon)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsClaimToAmazonService extends IService<TbLogisticsClaimToAmazon> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkClaimId 主键
     * @return 实例对象
     */
    TbLogisticsClaimToAmazon queryById(String pkClaimId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsClaimToAmazon 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsClaimToAmazonResult> paginQuery(TbLogisticsClaimToAmazonParam tbLogisticsClaimToAmazon, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsClaimToAmazon 实例对象
     * @return 实例对象
     */
    TbLogisticsClaimToAmazon insert(TbLogisticsClaimToAmazon tbLogisticsClaimToAmazon);
    /** 
     * 更新数据
     *
     * @param tbLogisticsClaimToAmazon 实例对象
     * @return 实例对象
     */
    TbLogisticsClaimToAmazon update(TbLogisticsClaimToAmazonParam tbLogisticsClaimToAmazon);
    /** 
     * 通过主键删除数据
     *
     * @param pkClaimId 主键
     * @return 是否成功
     */
    boolean deleteById(String pkClaimId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkClaimIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> pkClaimIdList);

    /**
     * 查看索赔邮件内容模板
     * @param pkClaimID
     * @return
     */
    List<LogisticsAmazonClaimsEmailViewModel> queryLogisticsAmazonClaimsEmail(String pkClaimID);

    /**
     * 下载索赔附件模板
     *
     * @param pkClaimID pkClaimID
     * @return 实例对象
     */
    List<DownloadLogisticsAmazonClaimsViewModel> downloadLogisticsAmazonClaims(String pkClaimID);

    /**
     * 标记申请索赔
     * @param param
     * @return
     */
    ResponseData markLogisticsAmazonClaims(TbLogisticsClaimToAmazonParam param);
}