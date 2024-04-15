package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProduct;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgProductMergeRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 通关产品基础信息 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgBaseProductMapper extends BaseMapper<TgBaseProduct> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgBaseProductResult> queryPage(@Param("page") Page page, @Param("param") TgBaseProductParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<TgBaseProductResult> export(@Param("param") TgBaseProductParam param);

    /**
     * 报关产品信息导出
     * @param param
     * @return
     */
    List<TgBaseProductApplyResult> exportCustomsApply(@Param("param") TgBaseProductParam param);

    /**
     * 清关产品信息导出
     * @param param
     * @return
     */
    List<TgBaseProductClearResult> exportCustomsClear(@Param("param") TgBaseProductParam param);

    /**
     * 报关材质下拉
     * @return
     */
    List<BaseSelectResult> clearMaterialCnSelect();

    /**
     * 报关英文材质下拉
     * @return
     */
    List<BaseSelectResult> clearMaterialEnSelect();

    /**
     * 清关产品合并-分页查询列表
     * @param param
     * @return
     */
    Page<TgProductMergeResult> queryMergePage(@Param("page") Page page, @Param("param") TgBaseProductParam param);

    /**
     * 清关产品合并-已合并产品查询
     * @param param
     * @return
     */
    Page<TgProductMergeResult> queryAlrMergePage(@Param("page") Page page, @Param("param") TgBaseProductParam param);

    /**
     * 清关产品合并-规则合并预览
     * @param param
     * @return
     */
    List<TgProductMergeRuleResult> ruleMergePreview(@Param("param") TgProductMergeRuleParam param);

    /**
     * 更新合并的明细状态
     * @param detailId
     * @param name
     */
    void updateMergeProductDetailById(@Param("detailId") BigDecimal detailId, @Param("name") String name);

    /**
     * 清关产品合并-自定义合并预览
     * @param param
     * @return
     */
    List<TgProductMergeResult> selectMergePreview(@Param("param") List<BigDecimal> param);

    /**
     * 清关产品合并-获取通关产品信息
     * @return
     */
    List<TgProductMergeResult> getProductList();

    /**
     * 获取EBMS标签管理数据
     * @param param
     * @return
     */
    List<ValidateLabelResult> selectTbComShippingLabel(@Param("param") ValidateLabelResult param);

    /**
     * 查询财务结算表领星店铺数据SID
     * @param shopName
     * @param site
     * @return
     */
    String getFinanceLxSid(@Param("shopName") String shopName, @Param("site") String site);

    /**
     * 查询财务结算表领星店铺数据SID
     * @return
     */
    List<CwLingxingShopInfo> getFinanceLxSidList();

    /**
     * 获取更新产品信息
     * @param day
     * @return
     */
    List<TgBaseProductResult> getUpdateProductInfo(@Param("day") Integer day);

    /**
     * 获取更新转换物料产品信息
     * @return
     */
    List<TgBaseProductResult> getUpdateChangeProductInfo();

    /**
     * 获取更新组合物料产品信息
     * @return
     */
    List<TgBaseProductResult> getUpdateGroupProductInfo();

    /**
     * 获取物料编码对应的组合物料
     * @return
     */
    List<TgBaseProductResult> getGroupMat();

    /**
     * 更新组合物料价格
     * @return
     */
    void updateGroupMat();
}
