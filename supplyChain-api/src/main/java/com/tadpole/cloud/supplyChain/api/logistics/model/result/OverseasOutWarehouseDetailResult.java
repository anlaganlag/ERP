package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外仓出库管理明细出参类
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
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class OverseasOutWarehouseDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 箱条码 */
    @ExcelProperty(value ="箱条码", index = 4)
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ExcelProperty(value ="箱号", index = 0)
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** 箱号上传名称 */
//    @ExcelProperty(value ="箱号上传名称")
    @ApiModelProperty("箱号上传名称")
    private String packageNumName;

    /** FNSKU */
    @ExcelProperty(value ="FNSKU", index = 1)
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ExcelProperty(value ="SKU", index = 3)
    @ApiModelProperty("SKU")
    private String sku;

    /** 数量 */
    @ExcelProperty(value ="数量", index = 2)
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 需求部门 */
    @ExcelProperty(value ="需求部门", index = 7)
    @ApiModelProperty("需求部门")
    private String department;

    /** 需求Team */
    @ExcelProperty(value ="需求Team", index = 8)
    @ApiModelProperty("需求Team")
    private String team;

    /** 需求人员 */
    @ExcelProperty(value ="需求人员", index = 9)
    @ApiModelProperty("需求人员")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

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

    /** 箱型 */
    @ExcelProperty(value ="箱型", index = 5)
    @ApiModelProperty("箱型")
    private String packBoxType;

    /** 重量 */
    @ExcelProperty(value ="重量", index = 6)
    @ApiModelProperty("重量")
    private BigDecimal weight;

    /** 长 */
//    @ExcelProperty(value ="长")
    @ApiModelProperty("长")
    private BigDecimal length;

    /** 宽 */
//    @ExcelProperty(value ="宽")
    @ApiModelProperty("宽")
    private BigDecimal width;

    /** 高 */
//    @ExcelProperty(value ="高")
    @ApiModelProperty("高")
    private BigDecimal height;

    /** 体积 */
//    @ExcelProperty(value ="体积")
    @ApiModelProperty("体积")
    private BigDecimal volume;

    @ExcelProperty(value ="导入错误备注", index = 10)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ApiModelProperty("导入错误备注")
    private String uploadRemark;

}