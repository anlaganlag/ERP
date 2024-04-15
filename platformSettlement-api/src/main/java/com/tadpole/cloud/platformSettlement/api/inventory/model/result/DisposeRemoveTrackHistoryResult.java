package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 销毁移除跟踪历史表响应参数
 * @date: 2023/2/28
 */
@Data
@ExcelIgnoreUnannotated
@ApiModel
public class DisposeRemoveTrackHistoryResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ExcelProperty(value ="数据月份")
    @ApiModelProperty("数据月份")
    private String dataMonth;

    @ApiModelProperty("期间")
    @ExcelProperty(value ="期间")
    private String fiscalPeriod;

    @ApiModelProperty("平台")
    @ExcelProperty(value ="平台")
    private String platform;

    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String shopName;

    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String site;

    @ApiModelProperty("币别")
    @ExcelProperty(value ="币别")
    private String currency;

    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    @ApiModelProperty("Team")
    @ExcelProperty(value ="Team")
    private String team;

    @ApiModelProperty("asin")
    private String asin;

    @ApiModelProperty("FNSKU")
    @ExcelProperty(value ="FNSKU")
    private String fnsku;

    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    @ApiModelProperty("运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType;

    @ApiModelProperty("产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName;

    @ApiModelProperty("公司品牌")
    @ExcelProperty(value ="公司品牌")
    private String companyBrand;

    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("款式")
    @ExcelProperty(value ="款式")
    private String style;

    @ApiModelProperty("主材料")
    @ExcelProperty(value ="主材料")
    private String mainMaterial;

    @ApiModelProperty("图案")
    @ExcelProperty(value ="图案")
    private String design;

    @ApiModelProperty("型号")
    @ExcelProperty(value ="型号")
    private String model;

    @ApiModelProperty("适用机型")
    @ExcelProperty(value ="适用机型")
    private String type;

    @ApiModelProperty("颜色")
    @ExcelProperty(value ="颜色")
    private String color;

    @ApiModelProperty("类目")
    private String category;

    @ApiModelProperty("尺码")
    private String sizes;

    @ApiModelProperty("包装数量")
    private String packing;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("二级标签")
    private String styleSecondLabel;

    @ApiModelProperty("订单申请日期")
    @ExcelProperty(value ="订单申请日期")
    private Date orderApplyDate;

    @ApiModelProperty("订单ID")
    @ExcelProperty(value ="订单ID")
    private String orderId;

    @ApiModelProperty("订单类型")
    @ExcelProperty(value ="订单类型")
    private String orderType;

    @ApiModelProperty("订单状态")
    @ExcelProperty(value ="订单状态")
    private String orderStatus;

    @ApiModelProperty("申请数量（订单）")
    @ExcelProperty(value ="申请数量（订单）")
    private String applyAmount;

    @ApiModelProperty("取消数量（订单）")
    @ExcelProperty(value ="取消数量（订单）")
    private String cancelAmount;

    @ApiModelProperty("销毁数量（订单）")
    @ExcelProperty(value ="销毁数量（订单）")
    private String orderDisposedAmount;

    @ApiModelProperty("移除数量（订单）")
    @ExcelProperty(value ="移除数量（订单）")
    private String orderShippedAmount;

    @ApiModelProperty("剩余数量（订单）")
    @ExcelProperty(value ="剩余数量（订单）")
    private String surplusAmount;

    @ApiModelProperty("销毁移除费")
    @ExcelProperty(value ="销毁移除费")
    private String destroyFee;

    @ApiModelProperty("销毁移除订单报告日期")
    @ExcelProperty(value ="销毁移除订单报告日期")
    private String orderDateStr;

    @ApiModelProperty("销毁数量（货件）")
    @ExcelProperty(value ="销毁数量（货件）")
    private String disposeAmount;

    @ApiModelProperty("移除数量（货件）")
    @ExcelProperty(value ="移除数量（货件）")
    private String removeAmount;

    @ApiModelProperty("销毁移除货件报告日期")
    @ExcelProperty(value ="销毁移除货件报告日期")
    private String shipmentDateStr;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;
}
