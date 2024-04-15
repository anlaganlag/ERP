package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓批量盘点导入
 * @date: 2022/9/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasWarehouseManageCheckParam {
    @ExcelProperty(value = "平台（必填）")
    @ApiModelProperty("平台（必填）")
    private String platform;

    @ExcelProperty(value = "账号（必填）")
    @ApiModelProperty("账号（必填）")
    private String sysShopsName;

    @ExcelProperty(value = "站点（必填）")
    @ApiModelProperty("站点（必填）")
    private String sysSite;

    @ExcelProperty(value = "仓库名称（必填）")
    @ApiModelProperty("仓库名称（必填）")
    private String warehouseName;

    @ExcelProperty(value = "FNSKU（必填）")
    @ApiModelProperty("FNSKU（必填）")
    private String fnSku;

    @ExcelProperty(value = "SKU（必填）")
    @ApiModelProperty("SKU（必填）")
    private String sku;

    @ExcelProperty(value = "物料编码（必填）")
    @ApiModelProperty("物料编码（必填）")
    private String materialCode;

    @ExcelProperty(value = "账存数量（必填）")
    @ApiModelProperty("账存数量（必填）")
    private BigDecimal inventoryQuantity;

    @ExcelProperty(value = "需求部门（非必填）")
    @ApiModelProperty("需求部门（非必填）")
    private String department;

    @ExcelProperty(value = "需求Team（非必填）")
    @ApiModelProperty("需求Team（非必填）")
    private String team;

    @ExcelProperty(value = "盘点原因（必填）")
    @ApiModelProperty("盘点原因（必填）")
    private String checkReason;

    @ExcelProperty(value = "备注（非必填）")
    @ApiModelProperty("备注（非必填）")
    private String remark;

    @ExcelProperty(value = "导入校验备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ApiModelProperty("导入校验备注")
    private String uploadRemark;

    /**
     * FNSKU校验标签后FNSKU
     */
    @ApiModelProperty("原FNSKU校验标签后FNSKU")
    private String validFnSku;

    /**
     * 海外仓库存管理表ID
     */
    @ApiModelProperty("海外仓库存管理表ID")
    private BigDecimal id;

    /**
     * 调出组织编码
     */
    @ApiModelProperty(hidden = true)
    private String outOrg;

    /**
     * 调入组织编码
     */
    @ApiModelProperty(hidden = true)
    private String inOrg;

    /**
     * 调出组织名称
     */
    @ApiModelProperty(hidden = true)
    private String outOrgName;

    /**
     * 调入组织名称
     */
    @ApiModelProperty(hidden = true)
    private String inOrgName;

    @ApiModelProperty("原账存数量")
    private BigDecimal oldInventoryQuantity;
}