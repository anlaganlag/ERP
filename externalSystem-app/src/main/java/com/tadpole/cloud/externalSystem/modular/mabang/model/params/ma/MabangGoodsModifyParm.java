package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建分仓调拨单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangGoodsModifyParm {

    //api:stock-do-change-stock

    @ApiModelProperty("库存sku")
    private String stockSku		            ;//库存sku

    @ApiModelProperty("商品名称，建议中文名称")
    private String nameCN	            ;//商品名称，建议中文名称

    @ApiModelProperty("备用商品名称，建议英文名称")
    private String nameEN	            ;//备用商品名称，建议英文名称

    @ApiModelProperty("商品状态")
    private String status	        ;//商品状态：1.自动创建 2.待开发 3.正常 4.清仓 5.停止销售

    @ApiModelProperty("商品图片")
    private String picture	        ;//商品图片

    @ApiModelProperty("长")
    private String length	            ;//长

    @ApiModelProperty("宽")
    private String width	            ;//宽

    @ApiModelProperty("高")
    private String height	            ;//高

    @ApiModelProperty("重量")
    private String weight	            ;//重量

    @ApiModelProperty("原厂sku")
    private String originalSku	            ;//原厂sku

    @ApiModelProperty("一级目录名称")
    private String parentCategoryName	            ;//一级目录名称

    @ApiModelProperty("二级目录名称")
    private String categoryName	            ;//二级目录名称

    @ApiModelProperty("售价")
    private String salePrice	            ;//售价

    @ApiModelProperty("商品备注")
    private String remark	            ;//商品备注

//    @ApiModelProperty("关联供应商信息")
//    private List<MabangGoodsSupplierParm> suppliersData	            ;//关联供应商信息
//
//    @ApiModelProperty("仓库信息json格式字符串")
//    private List<MabangGoodsWarehouseParm> warehouseData	            ;//仓库信息json格式字符串
//
//    @ApiModelProperty("多属性json格式")
//    private List<MabangGoodsAttrituteParm> attributesData	            ;//多属性json格式

    @ApiModelProperty("关联供应商信息")
    private String suppliersData	            ;//关联供应商信息

    @ApiModelProperty("仓库信息json格式字符串")
    private String warehouseData	            ;//仓库信息json格式字符串

    @ApiModelProperty("多属性json格式")
    private String attributesData	            ;//多属性json格式

    @ApiModelProperty("修改供应商，供应商不存在是否创建，1.是 2.否")
    private String supplierSyn	            ;//修改供应商，供应商不存在是否创建，1.是 2.否

    @ApiModelProperty("虚拟sku,多个英文分号隔开")
    private String virtualSkus	            ;//虚拟sku,多个英文分号隔开

    @ApiModelProperty("最新采购价")
    private String purchasePrice	            ;//最新采购价

    @ApiModelProperty("申报中文名称")
    private String declareName	            ;//申报中文名称

    @ApiModelProperty("申报英文名称")
    private String declareEname	            ;//申报英文名称

    @ApiModelProperty("brandName")
    private String brandName	            ;//brandName

    @ApiModelProperty("主SKU")
    private String salesSku		            ;//主SKU

    @ApiModelProperty("0:非液体,2:液体(化妆品),1:非液体(化妆品),3:液体(非化妆品)")
    private String noLiquidCosmetic	            ;//0:非液体,2:液体(化妆品),1:非液体(化妆品),3:液体(非化妆品)

    @ApiModelProperty("带电池 1.是 2.否")
    private String hasBattery	            ;//带电池 1.是 2.否

    @ApiModelProperty("侵权 1.是 2.否")
    private String isTort	            ;//侵权 1.是 2.否

    @ApiModelProperty("带磁 1.是 2.否")
    private String magnetic	            ;//带磁 1.是 2.否

    @ApiModelProperty("统一成本价")
    private String defaultCost	            ;//统一成本价

    @ApiModelProperty("采购员名称")
    private String buyerId	            ;//采购员名称

    @ApiModelProperty("美工名称")
    private String artDesignerName	            ;//美工名称

    @ApiModelProperty("开发员名称")
    private String developerName	            ;//开发员名称

    @ApiModelProperty("销售员名称")
    private String salesName	            ;//销售员名称

    @ApiModelProperty("操作员工")
    private String operName	            ;//操作员工

    @ApiModelProperty("财务编码")
    private String financial	            ;//财务编码

    @ApiModelProperty("是否是赠品 1是 2否")
    private String isGift	            ;//是否是赠品 1是 2否

//    @ApiModelProperty("自定义分类json格式")
//    private List<MabangGoodsLabelParm> labelData	            ;//自定义分类json格式

    @ApiModelProperty("自定义分类json格式")
    private String labelData	            ;//自定义分类json格式


}
