package com.tadpole.cloud.operationManagement.api.brand.model.result;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
* <p>
* 品牌商标追踪报表
* </p>
* @author S20190161
* @since 2023-10-19
*/
@ExcelIgnoreUnannotated
@Data
public class TbBrandTrademarkReportResult {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商标名称")
    @ExcelProperty("商标名称")
    private String tradeName;
    @ExcelProperty("注册号")
    @ApiModelProperty("注册号")
    private String registerNumber;

    @ApiModelProperty("状态")
    private Integer trademarkStatus;

    @ExcelProperty("状态")
    private String trademarkStatusCn;

    @ExcelProperty("注册国家/地区")
    @ApiModelProperty("注册国家/地区")
    private String registerCountry;
    @ExcelProperty("商标权人")
    @ApiModelProperty("商标权人")
    private String companyName;
    @ExcelProperty("大类")
    @ApiModelProperty("大类")
    private String trademarkCategory;
    @ExcelProperty("小类")
    @ApiModelProperty("小类")
    private String trademarkSubClass;
    @ExcelProperty("申请日期")
    @ApiModelProperty("申请日期")
    private Date applyDate;
    @ExcelProperty("第一次付款日期")
    @ApiModelProperty("第一次付款日期")
    private Date paymentDateFirst;
    @ExcelProperty("实付金额")
    @ApiModelProperty("实付金额")
    private Double paymentAmount;
    @ExcelProperty("总金额")
    @ApiModelProperty("总金额")
    private Double applyOutlayPlusTax;
    @ExcelProperty("获证日期")
    @ApiModelProperty("获证日期")
    private Date certificateDate;
    @ExcelProperty("商标有效期")
    @ApiModelProperty("商标有效期")
    private Date trademarkValidityTermEnd;
    @ExcelProperty("资产编码")
    @ApiModelProperty("资产编码")
    private String assetsNo;

    @ExcelProperty("跟进人")
    @ApiModelProperty("跟进人")
    private String createName;


}
