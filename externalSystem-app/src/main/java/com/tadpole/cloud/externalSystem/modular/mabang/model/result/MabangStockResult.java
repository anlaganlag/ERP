package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
    * 马帮仓库列表
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MABANG_SHOP_LIST")
public class MabangStockResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 仓库编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 库存SKU */
    @ApiModelProperty("库存SKU")
    private String stockSku;

    /** 1启用2停用 */
    @ApiModelProperty("STATUS")
    private String nameCN;

    /** 英文名 */
    @ApiModelProperty("英文名")
    private String nameEN;

    /** 统一成本价 */
    @ApiModelProperty("统一成本价")
    private String defaultCost;

    /** 日均销量 */
    @ApiModelProperty("日均销量")
    private String forecastDaySale;

    /** 是否含电池:1是;2否 */
    @ApiModelProperty("是否含电池:1是;2否")
    private Integer hasBattery;

    /** 是否新款:1:是;2否 */
    @ApiModelProperty("是否新款:1:是;2否")
    private Integer isNewType;

    /** 是否侵权:1是;2否 */
    @ApiModelProperty("是否侵权:1是;2否")
    private Integer isTort;

    /** 活跃度:1:爆款;2:旺款;3:平款;4:滞销款; */
    @ApiModelProperty("活跃度:1:爆款;2:旺款;3:平款;4:滞销款;")
    private Integer livenessType;

    /** 状态:1.自动创建;2.待开发;3.正常;4.清仓;5.停止销售 */
    @ApiModelProperty("状态:1.自动创建;2.待开发;3.正常;4.清仓;5.停止销售")
    private Integer status;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private String timeCreated;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private String timeModify;

    /** 原厂SKU */
    @ApiModelProperty("原厂SKU")
    private String originalSku;

    /** 品牌 */
    @ApiModelProperty("品牌")
    private String brandName;

    /** 商品目录(一级) */
    @ApiModelProperty("商品目录(一级)")
    private String parentCategoryName;

    /** 商品目录(二级) */
    @ApiModelProperty("商品目录(二级)")
    private String categoryName;

    /** 售价 */
    @ApiModelProperty("售价")
    private String salePrice;

    /** 申报价格 */
    @ApiModelProperty("申报价格")
    private String declareValue;

    /** 页面显示图片地址 */
    @ApiModelProperty("页面显示图片地址")
    private String stockPicture;

    /** 原始图片地址 */
    @ApiModelProperty("原始图片地址")
    private String salePicture;

    /** 产品细节图 */
    @ApiModelProperty("产品细节图")
    private List<String> stockDetailImg;

    /** 长 */
    @ApiModelProperty("长")
    private String length;

    /** 宽 */
    @ApiModelProperty("宽")
    private String width;

    /** 高 */
    @ApiModelProperty("高")
    private String height;

    /** 重量 */
    @ApiModelProperty("重量")
    private String weight;

    /** 申报品名(中文) */
    @ApiModelProperty("申报品名(中文)")
    private String declareName;

    /** 申报品名(英文) */
    @ApiModelProperty("申报品名(英文)")
    private String declareEname;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 销售备注 */
    @ApiModelProperty("销售备注")
    private String saleRemark;

    /** 采购备注 */
    @ApiModelProperty("采购备注")
    private String purchaseRemark;

    /** 包材 */
    @JSONField(name = "package")
    private String packageRemane;

    /** 报关编码 */
    @ApiModelProperty("报关编码")
    private String declareCode;

    /** 是否赠品1是;2否 */
    @ApiModelProperty("是否赠品1是;2否")
    private Integer isGift;

    /** 带磁:1:是;2:否 */
    @ApiModelProperty("带磁:1:是;2:否")
    private Integer magnetic;

    /** 粉末:1:是;2:否 */
    @ApiModelProperty("粉末:1:是;2:否")
    private Integer powder;

    /** 最新采购价 */
    @ApiModelProperty("最新采购价")
    private String purchasePrice;

    /** 开发员ID */
    @ApiModelProperty("开发员ID")
    private Integer developerId;

    /** 开发员名称 */
    @ApiModelProperty("开发员名称")
    private String developerName;

    /** 采购员ID */
    @ApiModelProperty("采购员ID")
    private Integer buyerId;

    /** 采购员名称 */
    @ApiModelProperty("采购员名称")
    private String buyerName;

    /** 美工ID */
    @ApiModelProperty("美工ID")
    private Integer artDesignerId;

    /** 美工名称 */
    @ApiModelProperty("美工名称")
    private String artDesignerName;

    /** 商品材质 */
    @ApiModelProperty("商品材质")
    private String commodityMaterial;

    /** 商品用途 */
    @ApiModelProperty("商品用途")
    private String commodityUse;

    /** 虚拟sku,接口参数传showVirtualSku才返回 */
    @ApiModelProperty("虚拟sku,接口参数传showVirtualSku才返回")
    private List<String> virtualSku;

    /** 默认供应商名称,接口参数传showProvider才返回 */
    @ApiModelProperty("默认供应商名称,接口参数传showProvider才返回")
    private String provider;

    /** 采购链接,接口参数传showProvider才返回 */
    @ApiModelProperty("采购链接,接口参数传showProvider才返回")
    private String productLinkAddress;

    /** 财务编号 */
    @ApiModelProperty("财务编号")
    private String financial;

   // /** 仓库信息,接口参数传showWarehouse才返回 */
   // @ApiModelProperty("仓库信息,接口参数传showWarehouse才返回")
   // private List<String> warehouse;
   //
   // /** 自定义分类,接口参数传showLabel才返回 */
   // @ApiModelProperty("自定义分类,接口参数传showLabel才返回")
   // private List<String> label;
   //
   // /** 多属性,接口参数传showattributes才返回 */
   // @ApiModelProperty("多属性,接口参数传showattributes才返回")
   // private List<String> attributes;
   //
   // /** sales */
   // @ApiModelProperty("sales")
   // private List<String> sales;

}