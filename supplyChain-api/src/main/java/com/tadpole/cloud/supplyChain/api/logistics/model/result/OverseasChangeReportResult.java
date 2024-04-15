package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓换标报表出参类
 * @date: 2022/10/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasChangeReportResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 换标出库ID */
    @ApiModelProperty("换标出库ID")
    private BigDecimal outId;

    /** 换标入库ID */
    @ApiModelProperty("换标入库ID")
    private BigDecimal inId;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    @ExcelProperty(value ="出库单号")
    private String outOrder;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    @ExcelProperty(value ="入库单号")
    private String inOrder;

    /** 操作时间 */
    @ApiModelProperty("操作时间")
    @ExcelProperty(value ="操作时间")
    private String createTime;

    /** 原平台 */
    @ApiModelProperty("原平台")
    @ExcelProperty(value ="原平台")
    private String oldPlatform;

    /** 原账号 */
    @ApiModelProperty("原账号")
    @ExcelProperty(value ="原账号")
    private String oldSysShopsName;

    /** 原站点 */
    @ApiModelProperty("原站点")
    @ExcelProperty(value ="原站点")
    private String oldSysSite;

    /** 原仓库名称 */
    @ApiModelProperty("原仓库名称")
    @ExcelProperty(value ="原仓库名称")
    private String oldWarehouseName;

    /** 原FNSKU */
    @ApiModelProperty("原FNSKU")
    @ExcelProperty(value ="原FNSKU")
    private String oldFnSku;

    /** 原SKU */
    @ApiModelProperty("原SKU")
    @ExcelProperty(value ="原SKU")
    private String oldSku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** 换标数量 */
    @ApiModelProperty("换标数量")
    @ExcelProperty(value ="换标数量")
    private BigDecimal changeInventoryQuantity;

    /** 新平台 */
    @ApiModelProperty("新平台")
    @ExcelProperty(value ="新平台")
    private String newPlatform;

    /** 新账号 */
    @ApiModelProperty("新账号")
    @ExcelProperty(value ="新账号")
    private String newSysShopsName;

    /** 新站点 */
    @ApiModelProperty("新站点")
    @ExcelProperty(value ="新站点")
    private String newSysSite;

    /** 新仓库名称 */
    @ApiModelProperty("新仓库名称")
    @ExcelProperty(value ="新仓库名称")
    private String newWarehouseName;

    /** 新FNSKU */
    @ApiModelProperty("新FNSKU")
    @ExcelProperty(value ="新FNSKU")
    private String newFnSku;

    /** 新SKU */
    @ApiModelProperty("新SKU")
    @ExcelProperty(value ="新SKU")
    private String newSku;

    /** 事业部 */
    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    @ExcelProperty(value ="Team")
    private String team;

    /** 需求人员 */
    @ApiModelProperty("需求人员")
    @ExcelProperty(value ="需求人员")
    private String needsUser;
}
