package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外仓入库管理入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOverseasInWarehouseDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** FNSKU */
    @ApiModelProperty("FN_SKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 实际到货数量 */
    @ApiModelProperty("实际到货数量")
    private BigDecimal actualQuantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("物料名称")
    private String materialName;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    private String team;

    /** 需求人 */
    @ApiModelProperty("需求人")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 建议发货方式 */
    @ApiModelProperty("建议发货方式")
    private String suggestTransType;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** 签收状态 */
    @ApiModelProperty("签收状态")
    private String confirmStatus;

    /** 签收时间 */
    @ApiModelProperty("签收时间")
    private Date confirmTime;

    /** 签收人 */
    @ApiModelProperty("签收人")
    private String confirmUser;

    /** 箱长 */
    @ApiModelProperty("箱长")
    private BigDecimal packDetBoxLength ;

    /** 箱宽 */
    @ApiModelProperty("箱宽")
    private BigDecimal packDetBoxWidth;

    /** 箱高 */
    @ApiModelProperty("箱高")
    private BigDecimal packDetBoxHeight;

    /** 重量 */
    @ApiModelProperty("重量")
    private BigDecimal packDetBoxWeight ;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    /** 发货状态：未发货、发货中*/
    @ApiModelProperty("发货状态：未发货、发货中")
    private String shipStatus;

    /** 物流跟踪状态：已发货，出口报关中，国外清关中，尾程派送中，已签收，物流完结*/
    @ApiModelProperty("物流跟踪状态：已发货，出口报关中，国外清关中，尾程派送中，已签收，物流完结")
    private String logisticsStatus;

    /** 调拨单号 */
    @ApiModelProperty("调拨单号")
    private String packDirectCode;
}