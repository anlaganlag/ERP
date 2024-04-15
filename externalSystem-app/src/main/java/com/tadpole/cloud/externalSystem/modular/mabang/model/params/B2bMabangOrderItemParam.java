package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * B2B马帮订单具体商品项item;
 * @author : LSY
 * @date : 2023-9-13
 */
@ApiModel(value = "B2B马帮订单具体商品项item",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class B2bMabangOrderItemParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private String id ;
    
    /** MCMS含义:商品成本价-----马帮含义:商品成本价 */
    @ApiModelProperty(value = "MCMS含义:商品成本价-----马帮含义:商品成本价")
    private BigDecimal costPrice ;
    
    /** MCMS含义:缺货状态(0正在计算是否缺货 1有货 2缺货 3已补货)---马帮含义:缺货订单 0 正在计算是否缺货 、 1有货、2缺货、3 已补货 */
    @ApiModelProperty(value = "MCMS含义:缺货状态(0正在计算是否缺货 1有货 2缺货 3已补货)---马帮含义:缺货订单 0 正在计算是否缺货 、 1有货、2缺货、3 已补货")
    private String hasGoods ;
    
    /** MCMS含义:商品编号(从平台抓取的商品编号)---马帮含义:马帮订单商品编号 */
    @ApiModelProperty(value = "MCMS含义:商品编号(从平台抓取的商品编号)---马帮含义:马帮订单商品编号")
    private String orderItemId ;
    
    /** MCMS含义:马帮商品编号(马帮表中的商品编号ID)---马帮含义:马帮订单商品编号 */
    @ApiModelProperty(value = "MCMS含义:马帮商品编号(马帮表中的商品编号ID)---马帮含义:马帮订单商品编号")
    private String erpOrderItemId ;
    
    /** MCMS含义:是否是组合订单-----马帮含义:是否是组合商品 1.组合 2非组合 */
    @ApiModelProperty(value = "MCMS含义:是否是组合订单-----马帮含义:是否是组合商品 1.组合 2非组合")
    private String isCombo ;
    
    /** MCMS含义:平台itemId-----马帮含义:平台itemId */
    @ApiModelProperty(value = "MCMS含义:平台itemId-----马帮含义:平台itemId")
    private String itemId ;
    
    /** MCMS含义:订单商品备注-----马帮含义:订单商品备注 */
    @ApiModelProperty(value = "MCMS含义:订单商品备注-----马帮含义:订单商品备注")
    private String itemRemark ;
    
    /** MCMS含义:液体和化妆品说明(0非液体 1非液体(化妆品)
2液体(化妆品) 3液体(非化妆品))---马帮含义:0:非液体,2:液体(化妆品),1:非液体(化妆品),3:液体(非化妆品) */
    @ApiModelProperty(value = "MCMS含义:液体和化妆品说明(0非液体 1非液体(化妆品) 液体(化妆品) 3液体(非化妆品))---马帮含义:0:非液体,2:液体(化妆品),1:非液体(化妆品),3:液体(非化妆品)")
    private String noLiquidCosmetic ;
    
    /** MCMS含义:ERP系统关联订单编号，对应订单信息中的erpOrderId(主外键)---马帮含义:ERP系统关联订单编号，对应订单信息中的erpOrderId */
    @ApiModelProperty(value = "MCMS含义:ERP系统关联订单编号，对应订单信息中的erpOrderId(主外键)---马帮含义:ERP系统关联订单编号，对应订单信息中的erpOrderId")
    private String originOrderId ;
    
    /** MCMS含义:商品主图片(url链接)---马帮含义:商品图片 */
    @ApiModelProperty(value = "MCMS含义:商品主图片(url链接)---马帮含义:商品图片")
    private String pictureUrl ;
    
    /** MCMS含义:电商平台商品主图(url链接)---马帮含义:电商平台商品图片 */
    @ApiModelProperty(value = "MCMS含义:电商平台商品主图(url链接)---马帮含义:电商平台商品图片")
    private String originalPictureUrl ;
    
    /** MCMS含义:商品数量(从平台拉取的商品数量)---马帮含义:商品数量 */
    @ApiModelProperty(value = "MCMS含义:商品数量(从平台拉取的商品数量)---马帮含义:商品数量")
    private BigDecimal platformQuantity ;
    
    /** MCMS含义:平台SKU-----马帮含义:平台sku */
    @ApiModelProperty(value = "MCMS含义:平台SKU-----马帮含义:平台sku")
    private String platformSku ;
    
    /** MCMS含义:商品单位(这个字段要抓一下数据看看)---马帮含义:商品单位 */
    @ApiModelProperty(value = "MCMS含义:商品单位(这个字段要抓一下数据看看)---马帮含义:商品单位")
    private String productUnit ;
    
    /** MCMS含义:商品数量(马帮ERP系统生成的商品数量，正常情况下与platformQuantity字段值是一样的)---马帮含义:商品数量 */
    @ApiModelProperty(value = "MCMS含义:商品数量(马帮ERP系统生成的商品数量，正常情况下与platformQuantity字段值是一样的)---马帮含义:商品数量")
    private BigDecimal quantity ;
    
    /** MCMS含义:商品售价-----马帮含义:商品售价 */
    @ApiModelProperty(value = "MCMS含义:商品售价-----马帮含义:商品售价")
    private BigDecimal sellPrice ;
    
    /** MCMS含义:商品原始售价-----马帮含义:商品原始售价 */
    @ApiModelProperty(value = "MCMS含义:商品原始售价-----马帮含义:商品原始售价")
    private BigDecimal sellPriceOrigin ;
    
    /** MCMS含义:商品多属性(这个字段干嘛的？)---马帮含义:商品多属性 */
    @ApiModelProperty(value = "MCMS含义:商品多属性(这个字段干嘛的？)---马帮含义:商品多属性")
    private String specifics ;
    
    /** MCMS含义:商品状态(1未付款 2未发货 3已发货 4已作废)---马帮含义:商品状态 1：未付款  2：未发货  3：已发货  4：已作废 */
    @ApiModelProperty(value = "MCMS含义:商品状态(1未付款 2未发货 3已发货 4已作废)---马帮含义:商品状态 1：未付款  2：未发货  3：已发货  4：已作废")
    private String status ;
    
    /** MCMS含义:仓位-----马帮含义:商品仓位  注意：字段首字母大写，字段赋值可能为空 */
    @ApiModelProperty(value = "MCMS含义:仓位-----马帮含义:商品仓位  注意：字段首字母大写，字段赋值可能为空")
    private String stockGrid ;
    
    /** MCMS含义:库存sku-----马帮含义:库存sku */
    @ApiModelProperty(value = "MCMS含义:库存sku-----马帮含义:库存sku")
    private String stockSku ;
    
    /** MCMS含义:库存SKU状态(1自动创建 2待开发 3正常 4清仓 5停止销售)---马帮含义:状态：1.自动创建2.待开发3.正常4.清仓5.停止销售 */
    @ApiModelProperty(value = "MCMS含义:库存SKU状态(1自动创建 2待开发 3正常 4清仓 5停止销售)---马帮含义:状态：1.自动创建2.待开发3.正常4.清仓5.停止销售")
    private String stockStatus ;
    
    /** MCMS含义:商品仓库编号-----马帮含义:商品仓库编号 */
    @ApiModelProperty(value = "MCMS含义:商品仓库编号-----马帮含义:商品仓库编号")
    private String stockWarehouseId ;
    
    /** MCMS含义:商品名称-----马帮含义:商品名称 */
    @ApiModelProperty(value = "MCMS含义:商品名称-----马帮含义:商品名称")
    private String title ;
    
    /** MCMS含义:平台transactionId(这个字段拉一下值看看)---马帮含义:平台transactionId */
    @ApiModelProperty(value = "MCMS含义:平台transactionId(这个字段拉一下值看看)---马帮含义:平台transactionId")
    private String transactionId ;
    
    /** MCMS含义:单品重量-----马帮含义:商品单品重量 */
    @ApiModelProperty(value = "MCMS含义:单品重量-----马帮含义:商品单品重量")
    private BigDecimal unitWeight ;
    
    /** MCMS含义:商品仓库名称-----马帮含义:商品仓库名称 */
    @ApiModelProperty(value = "MCMS含义:商品仓库名称-----马帮含义:商品仓库名称")
    private String stockWarehouseName ;
    
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime ;
    
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime ;
    

}