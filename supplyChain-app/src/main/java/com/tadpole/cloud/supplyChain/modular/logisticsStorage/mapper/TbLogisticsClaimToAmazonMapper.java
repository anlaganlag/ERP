package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.DownloadLogisticsAmazonClaimsViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsAmazonClaimsEmailViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimToAmazonResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 亚马逊物流索赔;(tb_logistics_claim_to_amazon)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsClaimToAmazonMapper  extends MPJBaseMapper<TbLogisticsClaimToAmazon> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsClaimToAmazonResult> selectByPage(IPage<TbLogisticsClaimToAmazonResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsClaimToAmazon> wrapper);

    /**
     * 查看索赔邮件内容模板
     * @param pkClaimID
     * @return
     */
    List<LogisticsAmazonClaimsEmailViewModel> queryLogisticsAmazonClaimsEmail(@Param("pkClaimID") String pkClaimID);

    /**
     * 下载索赔附件模板
     * @param pkClaimID
     * @return
     */
    List<DownloadLogisticsAmazonClaimsViewModel> downloadLogisticsAmazonClaims(@Param("pkClaimID") String pkClaimID);
}