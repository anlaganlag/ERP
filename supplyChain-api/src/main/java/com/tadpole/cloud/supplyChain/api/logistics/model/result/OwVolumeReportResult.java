package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 库存产品重量体积报表出参
 * @date: 2022/12/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OwVolumeReportResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ExcelProperty(value ="平台")
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @ExcelProperty(value ="仓库名称")
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @ExcelProperty(value ="FNSKU")
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ExcelProperty(value ="SKU")
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 账存数量 */
    @ExcelProperty(value ="账存数量")
    @ApiModelProperty("账存数量")
    private BigDecimal inventoryQuantity;

    /** 单个重量（kg） */
    @ApiModelProperty(value = "单个重量（kg）")
    @ExcelProperty(value ="单个重量（kg）")
    private BigDecimal matTranportWeightOrg;

    /** 单个体积（CBM） */
    @ApiModelProperty(value = "单个体积（CBM）")
    @ExcelProperty(value ="单个体积（CBM）")
    private BigDecimal matBoxVolumeOrg;

    /** 账存总重量（kg） */
    @ApiModelProperty(value = "账存总重量（kg）")
    @ExcelProperty(value ="账存总重量（kg）")
    private BigDecimal totalMatTranportWeightOrg;

    /** 账存总体积（CBM） */
    @ApiModelProperty(value = "账存总体积（CBM）")
    @ExcelProperty(value ="账存总体积（CBM）")
    private BigDecimal totalMatBoxVolumeOrg;
}
