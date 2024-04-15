package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外仓入库管理明细出参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasInWarehouseDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

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

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 物流状态 */
    @ApiModelProperty("物流状态")
    private String logisticsStatus;
}