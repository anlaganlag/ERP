package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 海外仓出库管理明细入参类
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
public class OverseasOutWarehouseDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** 箱号上传名称 */
    @ApiModelProperty("箱号上传名称")
    private String packageNumName;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 数量 */
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    private String team;

    /** 需求人员 */
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
    @ExcelProperty(value ="箱型")
    @ApiModelProperty("箱型")
    private String packBoxType;

    /** 重量 */
    @ExcelProperty(value ="重量")
    @ApiModelProperty("重量")
    private BigDecimal weight;

    /** 长 */
    @ExcelProperty(value ="长")
    @ApiModelProperty("长")
    private BigDecimal length;

    /** 宽 */
    @ExcelProperty(value ="宽")
    @ApiModelProperty("宽")
    private BigDecimal width;

    /** 高 */
    @ExcelProperty(value ="高")
    @ApiModelProperty("高")
    private BigDecimal height;

    /** 体积 */
    @ExcelProperty(value ="体积")
    @ApiModelProperty("体积")
    private BigDecimal volume;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String uploadRemark;

}