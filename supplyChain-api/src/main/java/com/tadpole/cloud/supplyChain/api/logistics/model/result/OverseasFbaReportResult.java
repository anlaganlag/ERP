package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 亚马逊发海外仓报表出参类
 * @date: 2022/10/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasFbaReportResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** FBA源库ID */
    @ApiModelProperty("FBA源库ID")
    private BigDecimal parentId;

    /** 明细ID */
    @ApiModelProperty("明细ID")
    private BigDecimal detailId;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    @ExcelProperty(value ="入库单号")
    private String inOrder;

    /** 建单时间 */
    @ApiModelProperty("建单时间")
    @ExcelProperty(value ="建单时间")
    private String shipmentDate;

    /** 平台 */
    @ApiModelProperty("平台")
    @ExcelProperty(value ="平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    @ExcelProperty(value ="业务类型")
    private String operateType;

    /** 应入库数量 */
    @ApiModelProperty("应入库数量")
    @ExcelProperty(value ="应入库数量")
    private BigDecimal shouldInventoryQuantity;

    /** 已到货数量 */
    @ApiModelProperty("已到货数量")
    @ExcelProperty(value ="已到货数量")
    private BigDecimal alreadyInventoryQuantity;

    /** 未到货数量 */
    @ApiModelProperty("未到货数量")
    @ExcelProperty(value ="未到货数量")
    private BigDecimal notInventoryQuantity;

    /** SKU种类数量 */
    @ApiModelProperty("SKU种类数量")
    @ExcelProperty(value ="SKU种类数量")
    private BigDecimal skuNum;

    /** 总箱数 */
    @ApiModelProperty("总箱数")
    @ExcelProperty(value ="总箱数")
    private BigDecimal totalPackageNum;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    @ExcelProperty(value ="出货仓名称")
    private String outWarehouseName;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称")
    @ExcelProperty(value ="收货仓名称")
    private String inWarehouseName;

    /** 签收状态 待签收，部分签收，签收完成 */
    @ApiModelProperty("签收状态")
    @ExcelProperty(value ="签收状态")
    private String confirmStatus;

    /** 签收时间 */
    @ApiModelProperty("签收时间")
    @ExcelProperty(value ="签收时间")
    private String confirmTime;

    /** 签收数量 */
    @ApiModelProperty("签收数量")
    @ExcelProperty(value ="签收数量")
    private BigDecimal actualQuantity;

    /** 签收人 */
    @ApiModelProperty("签收人")
    @ExcelProperty(value ="签收人")
    private String confirmUser;

    /** 备注 */
    @ApiModelProperty("备注")
    @ExcelProperty(value ="备注")
    private String remark;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    @ExcelProperty(value ="FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** 需求部门 */
    @ApiModelProperty("需求部门")
    @ExcelProperty(value ="需求部门")
    private String department;

    /** 需求Team */
    @ApiModelProperty("需求Team")
    @ExcelProperty(value ="需求Team")
    private String team;

    /** 需求人 */
    @ApiModelProperty("需求人")
    @ExcelProperty(value ="需求人")
    private String needsUser;

    /** 承运商 */
    @ApiModelProperty("承运商")
    @ExcelProperty(value ="承运商")
    private String logisticsCompany;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    @ExcelProperty(value ="物流单号")
    private String logisticsNum;
}
