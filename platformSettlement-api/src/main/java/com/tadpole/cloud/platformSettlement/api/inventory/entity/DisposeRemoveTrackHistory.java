package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 销毁移除跟踪历史表
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DISPOSE_REMOVE_TRACK_HISTORY")
@ApiModel(value="DisposeRemoveTrackHistory对象", description="销毁移除跟踪历史表")
public class DisposeRemoveTrackHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("PLATFORM")
    @ApiModelProperty(value = "平台")
    private String platform;

    @TableField("SHOP_NAME")
    @ApiModelProperty(value = "账号")
    private String shopName;

    @TableField("SITE")
    @ApiModelProperty(value = "站点")
    private String site;

    @TableField("DEPARTMENT")
    @ApiModelProperty(value = "事业部")
    private String department;

    @TableField("TEAM")
    @ApiModelProperty(value = "Team")
    private String team;

    @TableField("ASIN")
    @ApiModelProperty(value = "Asin")
    private String asin;

    @TableField("FNSKU")
    @ApiModelProperty(value = "fnsku")
    private String fnsku;

    @TableField("SKU")
    @ApiModelProperty(value = "sku")
    private String sku;

    @TableField("MATERIAL_CODE")
    @ApiModelProperty(value = "物料编码")
    private String materialCode;

    @TableField("ORDER_APPLY_DATE")
    @ApiModelProperty(value = "订单申请日期")
    private Date orderApplyDate;

    @TableField("ORDER_ID")
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @TableField("ORDER_TYPE")
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @TableField("ORDER_STATUS")
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @TableField("APPLY_AMOUNT")
    @ApiModelProperty(value = "申请数量（订单）")
    private String applyAmount;

    @TableField("DISPOSE_AMOUNT")
    @ApiModelProperty(value = "销毁数量（货件）")
    private String disposeAmount;

    @TableField("REMOVE_AMOUNT")
    @ApiModelProperty(value = "移除数量（货件）")
    private String removeAmount;

    @TableField("CANCEL_AMOUNT")
    @ApiModelProperty(value = "取消数量（订单）")
    private String cancelAmount;

    @TableField("ID")
    @ApiModelProperty(value = "ID")
    private BigDecimal id;

    @TableField("UPDATE_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField("UPDATE_BY")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @TableField("A_UPLOAD_DATE")
    @ApiModelProperty(value = "货件报告日期")
    private Date aUploadDate;

    @TableField("B_UPLOAD_DATE")
    @ApiModelProperty(value = "订单报告日期")
    private Date bUploadDate;

    @TableField("REMOVAL_FEE")
    @ApiModelProperty(value = "移除费用")
    private BigDecimal removalFee;

    @TableField("CURRENCY")
    @ApiModelProperty(value = "币别")
    private String currency;

    @TableField("CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField("CREATE_USER")
    @ApiModelProperty(value = "创建人")
    private String createUser;

    @TableField("ORDER_DISPOSED_AMOUNT")
    @ApiModelProperty(value = "订单销毁数量（订单）")
    private String orderDisposedAmount;

    @TableField("ORDER_SHIPPED_AMOUNT")
    @ApiModelProperty(value = "订单移除数量（订单）")
    private String orderShippedAmount;

    @TableField("ORDER_APPLY_DATE_UTC")
    @ApiModelProperty(value = "申请时间UTC（订单）")
    private Date orderApplyDateUtc;

    @TableField("DATA_MONTH")
    @ApiModelProperty(value = "数据月份")
    private String dataMonth;
}
