package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProduct;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 通关产品基础信息 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgBaseProductService extends IService<TgBaseProduct> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgBaseProductParam param);

    /**
     * 导出
     */
    List<TgBaseProductResult> export(TgBaseProductParam param);

    /**
     * 报关产品信息导出
     */
    List<TgBaseProductApplyResult> exportCustomsApply(TgBaseProductParam param);

    /**
     * 清关产品信息导出
     */
    List<TgBaseProductClearResult> exportCustomsClear(TgBaseProductParam param);

    /**
     * 导入报关信息
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 导入清关信息
     * @param file
     * @return
     */
    ResponseData importDetail(MultipartFile file);

    /**
     * 批量审核通过
     * @param param
     * @return
     */
    ResponseData batchAuditPass(TgBaseProductParam param);

    /**
     * 批量反审
     * @param param
     * @return
     */
    ResponseData batchAuditReset(TgBaseProductParam param);

    /**
     * 更新产品信息
     * @param day
     * @param resultList
     * @param changeResultList
     * @param groupResultList
     * @param groupMatList
     * @return
     */
    ResponseData updateProductInfo(Integer day, List<TgBaseProductResult> resultList, List<TgBaseProductResult> changeResultList,
                                   List<TgBaseProductResult> groupResultList, List<TgBaseProductResult> groupMatList);

    /**
     * 获取更新产品信息
     * @param day
     * @return
     */
    List<TgBaseProductResult> getUpdateProductInfo(Integer day);

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
     * 编辑保存
     * @param param
     * @return
     */
    ResponseData edit(TgBaseProductEditParam param);

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
     */
    ResponseData queryMergePage(TgBaseProductParam param);

    /**
     * 清关产品合并-导出
     */
    List<TgProductMergeResult> exportMerge(TgBaseProductParam param);

    /**
     * 清关产品合并-编辑保存
     * @param param
     * @return
     */
    ResponseData editMerge(TgMergeProductEditParam param);

    /**
     * 清关产品合并-已合并产品查询
     */
    ResponseData queryAlrMergePage(TgBaseProductParam param);

    /**
     * 清关产品合并-规则合并
     * @param params
     * @return
     */
    ResponseData ruleMerge(List<TgMergeRuleConfirmParam> params);

    /**
     * 清关产品合并-规则合并预览
     * @param param
     * @return
     */
    ResponseData ruleMergePreview(TgProductMergeRuleParam param);

    /**
     * 清关产品合并-自定义合并
     * @param param
     * @return
     */
    ResponseData selectMerge(List<TgProductMergeIdsParam> param);

    /**
     * 清关产品合并-自定义合并预览
     * @param param
     * @return
     */
    ResponseData selectMergePreview(List<TgProductMergeIdsParam> param);

    /**
     * 清关产品合并-合并拆分
     * @param param
     * @return
     */
    ResponseData mergeSplit(TgProductMergeIdsParam param);

    /**
     * 查询EBMS标签库
     * @param param
     * @return
     */
    List<ValidateLabelResult> selectTbComShippingLabel(ValidateLabelResult param);

    /**
     * 查询财务结算表领星店铺数据SID
     * @param shopName
     * @param site
     * @return
     */
    String getFinanceLxSid(String shopName, String site);

    /**
     * 查询财务结算表领星店铺数据SID
     * @return
     */
    List<CwLingxingShopInfo> getFinanceLxSidList();

    /**
     * 定时获取领星亚马逊Listing
     * @param param
     * @return
     */
    ResponseData getLxAmazonListing(CwLingxingShopInfo param);

    /**
     * 根据物料编码获取产品基本信息
     * @param matCodeList
     * @return
     */
    List<TgBaseProduct> getProductInfoList(List<String> matCodeList);
}
