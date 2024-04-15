package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 发货审核查询结果集
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ExportVerifyListResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("申请批次号")
    @ExcelProperty("申请批次号")
    private String applyBatchNo;

    @ApiModelProperty("申请人")
    @ExcelProperty("申请人")
    private String apllyPerson;

    @ApiModelProperty("申请日期")
    @ExcelProperty("申请日期")
    private Date applyDate;

    @ApiModelProperty("平台")
    @ExcelProperty("平台")
    private String platform;


    @ApiModelProperty("区域")
    @ExcelProperty("区域")
    private String area;

    @ApiModelProperty("ASIN")
    @ExcelProperty("ASIN")
    private String asin;


    @ApiModelProperty("物料编码")
    @ExcelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("运营大类")
    @ExcelProperty("运营大类")
    private String productType;


    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;


    @ApiModelProperty("账号")
    @ExcelProperty("账号")
    private String sysShopsName;


    @ApiModelProperty("站点")
    @ExcelProperty("站点")
    private String sysSite;

    @ApiModelProperty("SKU")
    @ExcelProperty("SKU")
    private String sku;


    @ApiModelProperty("FBA号")
    @ExcelProperty("FBA号")
    private String fbaNo;


    @ApiModelProperty("发货点")
    @ExcelProperty("发货点")
    private String deliverypoint;


    @ApiModelProperty("调入仓库")
    @ExcelProperty("调入仓库")
    private String receiveWarehouse;

    @ApiModelProperty("申请后国内可调拨库存")
    @ExcelProperty("申请后国内可调拨库存")
    private BigDecimal commitedAvailQty;


    @ApiModelProperty("发货数量")
    @ExcelProperty("发货数量")
    private BigDecimal sendQty;


    @ApiModelProperty("发货方式")
    @ExcelProperty("发货方式")
    private String transportationType;

    @ApiModelProperty("UW业务类型")
    @ExcelProperty("UW业务类型")
    private String unwType;

    @ExcelProperty("备注1")
    @ApiModelProperty("备注1")
    private String remark1;

    @ExcelProperty("备注2")
    @ApiModelProperty("备注2")
    private String remark2;


    @ExcelProperty("备注3")
    @ApiModelProperty("备注3")
    private String remark3;


    @ApiModelProperty("同步结果")
    @ExcelProperty("同步结果")
    private String syncResultMsg;

}