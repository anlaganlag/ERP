package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮订单具体商品项item
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
@TableName("MABANG_ORDER_ITEM")
@ExcelIgnoreUnannotated
public class MabangOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 主键 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** MCMS含义:商品成本价-----马帮含义:商品成本价 */
    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    /** MCMS含义:缺货状态(0正在计算是否缺货 1有货 2缺货 3已补货)---马帮含义:缺货订单 0 正在计算是否缺货 、 1有货、2缺货、3 已补货 */
    @TableField("HAS_GOODS")
    private String hasGoods;

    /** MCMS含义:商品编号(从平台抓取的商品编号)---马帮含义:马帮订单商品编号 */
    @TableField("ORDER_ITEM_ID")
    private String orderItemId;

    /** MCMS含义:马帮商品编号(马帮表中的商品编号ID)---马帮含义:马帮订单商品编号 */
    @TableField("ERP_ORDER_ITEM_ID")
    private String erpOrderItemId;

    /** MCMS含义:是否是组合订单-----马帮含义:是否是组合商品 1.组合 2非组合 */
    @TableField("IS_COMBO")
    private String isCombo;

    /** MCMS含义:平台itemId-----马帮含义:平台itemId */
    @TableField("ITEM_ID")
    private String itemId;

    /** MCMS含义:订单商品备注-----马帮含义:订单商品备注 */
    @TableField("ITEM_REMARK")
    private String itemRemark;

    /** MCMS含义:液体和化妆品说明(0非液体 1非液体(化妆品)	2液体(化妆品) 3液体(非化妆品))---马帮含义:0:非液体,2:液体(化妆品),1:非液体(化妆品),3:液体(非化妆品) */
    @TableField("NO_LIQUID_COSMETIC")
    private String noLiquidCosmetic;

    /** MCMS含义:ERP系统关联订单编号，对应订单信息中的erpOrderId(主外键)---马帮含义:ERP系统关联订单编号，对应订单信息中的erpOrderId */
    @TableField("ORIGIN_ORDER_ID")
    private String originOrderId;

    /** MCMS含义:商品主图片(url链接)---马帮含义:商品图片 */
    @TableField("PICTURE_URL")
    private String pictureUrl;

    /** MCMS含义:电商平台商品主图(url链接)---马帮含义:电商平台商品图片 */
    @TableField("ORIGINAL_PICTURE_URL")
    private String originalPictureUrl;

    /** MCMS含义:商品数量(从平台拉取的商品数量)---马帮含义:商品数量 */
    @TableField("PLATFORM_QUANTITY")
    private BigDecimal platformQuantity;

    /** MCMS含义:平台SKU-----马帮含义:平台sku */
    @TableField("PLATFORM_SKU")
    private String platformSku;

    /** MCMS含义:商品单位(这个字段要抓一下数据看看)---马帮含义:商品单位 */
    @TableField("PRODUCT_UNIT")
    private String productUnit;

    /** MCMS含义:商品数量(马帮ERP系统生成的商品数量，正常情况下与platformQuantity字段值是一样的)---马帮含义:商品数量 */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /** MCMS含义:商品售价-----马帮含义:商品售价 */
    @TableField("SELL_PRICE")
    private BigDecimal sellPrice;

    /** MCMS含义:商品原始售价-----马帮含义:商品原始售价 */
    @TableField("SELL_PRICE_ORIGIN")
    private BigDecimal sellPriceOrigin;

    /** MCMS含义:商品多属性(这个字段干嘛的？)---马帮含义:商品多属性 */
    @TableField("SPECIFICS")
    private String specifics;

    /** MCMS含义:商品状态(1未付款 2未发货 3已发货 4已作废)---马帮含义:商品状态 1：未付款  2：未发货  3：已发货  4：已作废 */
    @TableField("STATUS")
    private String status;

    /** MCMS含义:仓位-----马帮含义:商品仓位  注意：字段首字母大写，字段赋值可能为空 */
    @TableField("STOCK_GRID")
    private String stockGrid;

    /** MCMS含义:库存sku-----马帮含义:库存sku */
    @TableField("STOCK_SKU")
    private String stockSku;

    /** MCMS含义:库存SKU状态(1自动创建 2待开发 3正常 4清仓 5停止销售)---马帮含义:状态：1.自动创建2.待开发3.正常4.清仓5.停止销售 */
    @TableField("STOCK_STATUS")
    private String stockStatus;

    /** MCMS含义:商品仓库编号-----马帮含义:商品仓库编号 */
    @TableField("STOCK_WAREHOUSE_ID")
    private String stockWarehouseId;

    /** MCMS含义:商品名称-----马帮含义:商品名称 */
    @TableField("TITLE")
    private String title;

    /** MCMS含义:平台transactionId(这个字段拉一下值看看)---马帮含义:平台transactionId */
    @TableField("TRANSACTION_ID")
    private String transactionId;

    /** MCMS含义:单品重量-----马帮含义:商品单品重量 */
    @TableField("UNIT_WEIGHT")
    private BigDecimal unitWeight;

    /** MCMS含义:商品仓库名称-----马帮含义:商品仓库名称 */
    @TableField("STOCK_WAREHOUSE_NAME")
    private String stockWarehouseName;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;


   @TableField(exist = false)
   private String operId;




}