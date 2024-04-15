package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 报关单、清关单导入sheet1
 * @date: 2023/6/27
 */
@Data
@ExcelIgnoreUnannotated
public class TgCustomsApplyImportSheet1Param extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 箱号 */
    @ExcelProperty(value ="箱号")
    @ApiModelProperty("箱号")
    private String boxNo;

    /** 箱号上传名称 */
    @ExcelProperty(value ="箱号上传名称")
    @ApiModelProperty("箱号上传名称")
    private String boxNoName;

    /** 箱型 */
    @ExcelProperty(value ="箱型")
    @ApiModelProperty("箱型")
    private String boxType;

    /** 重量 */
    @ExcelProperty(value ="重量")
    @ApiModelProperty("重量")
    private BigDecimal weight;

    /** 长 */
    @ExcelProperty(value ="长")
    @ApiModelProperty("长")
    private BigDecimal goodsLong;

    /** 宽 */
    @ExcelProperty(value ="宽")
    @ApiModelProperty("宽")
    private BigDecimal wide;

    /** 高 */
    @ExcelProperty(value ="高")
    @ApiModelProperty("高")
    private BigDecimal high;

    /** 出货清单号 */
    @ExcelProperty(value ="出货清单号")
    @ApiModelProperty("出货清单号")
    private String packCode;

    /** 箱子合并后的箱子个数 */
    private BigDecimal boxNum;

    /** 箱子合并后的重量 */
    private BigDecimal packageWeight;

}
