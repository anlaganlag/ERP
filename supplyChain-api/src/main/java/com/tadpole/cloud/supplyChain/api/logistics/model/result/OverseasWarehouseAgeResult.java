package com.tadpole.cloud.supplyChain.api.logistics.model.result;

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
 *  海外仓库龄报表出参类
 * </p>
 *
 * @author ty
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasWarehouseAgeResult implements Serializable, BaseValidatingParam {

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

    /** 运营大类 */
    @ExcelProperty(value ="运营大类")
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ExcelProperty(value ="产品名称")
    @ApiModelProperty("产品名称")
    private String productName;

    /** 事业部 */
    @ExcelProperty(value ="事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ExcelProperty(value ="Team")
    @ApiModelProperty("Team")
    private String team;

    /** 签收数量 */
    @ExcelProperty(value ="签收数量")
    @ApiModelProperty("签收数量")
    private BigDecimal signQuantity;

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

    /** 签收时间 */
    @ExcelProperty(value ="签收时间")
    @ApiModelProperty("签收时间")
    private Date signDate;

    /** 库龄 */
    @ExcelProperty(value ="库龄")
    @ApiModelProperty("库龄")
    private Integer warehouseAge;
}
