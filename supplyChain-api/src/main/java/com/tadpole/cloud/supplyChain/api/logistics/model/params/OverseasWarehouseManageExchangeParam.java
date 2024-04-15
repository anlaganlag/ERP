package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓批量换标导入
 * @date: 2022/7/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasWarehouseManageExchangeParam {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value ="原平台")
    @ApiModelProperty("原平台")
    private String oldPlatform;

    @ExcelProperty(value ="原账号")
    @ApiModelProperty("原账号")
    private String oldSysShopsName;

    @ExcelProperty(value ="原站点")
    @ApiModelProperty("原站点")
    private String oldSysSite;

    @ExcelProperty(value ="原仓库名称")
    @ApiModelProperty("原仓库名称")
    private String oldWarehouseName;

    @ExcelProperty(value ="原FNSKU")
    @ApiModelProperty("原FNSKU")
    private String oldFnSku;

    @ExcelProperty(value ="原SKU")
    @ApiModelProperty("原SKU")
    private String oldSku;

    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("原物料编码")
    private String oldMaterialCode;

    @ExcelProperty(value ="换标数量")
    @ApiModelProperty("换标数量")
    private BigDecimal inventoryQuantity;

    @ExcelProperty(value ="新平台")
    @ApiModelProperty("新平台")
    private String platform;

    @ExcelProperty(value ="新账号")
    @ApiModelProperty("新账号")
    private String sysShopsName;

    @ExcelProperty(value ="新站点")
    @ApiModelProperty("新站点")
    private String sysSite;

    @ExcelProperty(value ="新FNSKU")
    @ApiModelProperty("新FNSKU")
    private String fnSku;

    @ExcelProperty(value ="新SKU")
    @ApiModelProperty("新SKU")
    private String sku;

    @ExcelProperty(value ="新仓库名称")
    @ApiModelProperty("新仓库名称")
    private String warehouseName;

    @ExcelProperty(value ="事业部")
    @ApiModelProperty("事业部")
    private String department;

    @ExcelProperty(value ="Team组")
    @ApiModelProperty("Team组")
    private String team;

    @ExcelProperty(value ="需求人员")
    @ApiModelProperty("需求人员")
    private String needsUser;

    @ExcelProperty(value ="备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ApiModelProperty("备注")
    private String uploadRemark;

    @ApiModelProperty("新物料编码")
    private String materialCode;

    /**
     * 原FNSKU校验标签后FNSKU
     */
    @ApiModelProperty("原FNSKU校验标签后FNSKU")
    private String oldValidFnSku;

    /**
     * 海外仓库存管理表ID
     */
    @ApiModelProperty("海外仓库存管理表ID")
    private BigDecimal id;

    /**
     * 换标后账存数量
     */
    @ApiModelProperty("换标后账存数量")
    private BigDecimal newInventoryQuantity;

    /**
     * 原账存数量
     */
    @ApiModelProperty("原账存数量")
    private BigDecimal oldInventoryQuantity;

    /**
     * 调出组织编码
     */
    @ApiModelProperty(hidden = true)
    private String outOrg;

    /**
     * 调出组织名称
     */
    @ApiModelProperty(hidden = true)
    private String outOrgName;

    /**
     * 调入组织编码
     */
    @ApiModelProperty(hidden = true)
    private String inOrg;

    /**
     * 调入组织名称
     */
    @ApiModelProperty(hidden = true)
    private String inOrgName;

    /**
     * 是否跨组织
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeOrg;

    /**
     * 是否跨站点
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeSite;

    /**
     * 是否相同物料编码
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeMaterialCode;

    /**
     * 来货数量
     */
    @ApiModelProperty("来货数量")
    private BigDecimal comeQuantity;
}
