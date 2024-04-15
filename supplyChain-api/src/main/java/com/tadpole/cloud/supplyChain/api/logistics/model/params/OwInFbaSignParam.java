package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓入库批量签收FBA退海外仓
 * @date: 2022/9/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OwInFbaSignParam {
    @ExcelProperty(value = "平台（必填）")
    @ApiModelProperty("平台（必填）")
    private String platform;

    @ExcelProperty(value = "账号（必填）")
    @ApiModelProperty("账号（必填）")
    private String sysShopsName;

    @ExcelProperty(value = "站点（必填）")
    @ApiModelProperty("站点（必填）")
    private String sysSite;

    /**
     * 收货仓名称（导入的）
     */
    @ExcelProperty(value = "收货仓名称（必填）")
    @ApiModelProperty("收货仓名称（必填）")
    private String warehouseName;

    @ExcelProperty(value = "SKU（必填）")
    @ApiModelProperty("SKU（必填）")
    private String sku;

    /**
     * FNSKU：六位码（fnSku后六位）
     */
    @ExcelProperty(value = "FNSKU（必填）")
    @ApiModelProperty("FNSKU（必填）")
    private String fnSku;

    @ExcelProperty(value = "签收数量（必填）")
    @ApiModelProperty("签收数量（必填）")
    private BigDecimal signQuantity;

    @ExcelProperty(value = "需求部门（必填）")
    @ApiModelProperty("需求部门（必填）")
    private String department;

    @ExcelProperty(value = "需求Team（必填）")
    @ApiModelProperty("需求Team（必填）")
    private String team;

    @ExcelProperty(value = "备注（非必填）")
    @ApiModelProperty("备注（非必填）")
    private String remark;

    @ExcelProperty(value = "导入校验备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ApiModelProperty("导入校验备注")
    private String uploadRemark;

    @ApiModelProperty("海外仓入库主表id")
    private BigDecimal id;

    @ApiModelProperty("海外仓入库明细表id")
    private BigDecimal detailId;

    @ApiModelProperty("入库订单号")
    private String inOrder;

    @ApiModelProperty("应入库数量")
    private BigDecimal shouldInventoryQuantity;

    @ApiModelProperty("已到货数量")
    private BigDecimal alreadyInventoryQuantity;

    @ApiModelProperty("未到货数量")
    private BigDecimal notInventoryQuantity;

    @ApiModelProperty("明细数量")
    private BigDecimal quantity;

    @ApiModelProperty("明细实际到货数量")
    private BigDecimal actualQuantity;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("临时站点 [CA，MX：US]，[UK，FR，IT，ES，PL，CZ，SE，AT：DE]")
    private String sysSiteTemp;

    @ApiModelProperty("销毁移除货件ID")
    private BigDecimal fbaId;

    /**
     * FNSKU全称
     */
    @ApiModelProperty(hidden = true)
    private String fullFnSku;

    /**
     * 收货仓名称（查询出来的）
     */
    @ApiModelProperty(hidden = true)
    private String inWarehouseName;

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
}
