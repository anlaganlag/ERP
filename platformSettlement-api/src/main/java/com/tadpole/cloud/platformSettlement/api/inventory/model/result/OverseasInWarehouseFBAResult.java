package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 获取FBA退海外仓数据
 * @date: 2022/7/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasInWarehouseFBAResult {

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String operateType;

    /** 应入库数量 */
    @ApiModelProperty("应入库数量")
    private BigDecimal shouldInventoryQuantity;

    /** SKU种类数量 */
    @ApiModelProperty("SKU种类数量")
    private BigDecimal skuNum;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    private String outWarehouseName;

    /** 签收状态 待签收、部分签收、全部签收完成 */
    @ApiModelProperty("签收状态 待签收、部分签收、全部签收完成")
    private String confirmStatus;

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

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 建议发货方式 */
    @ApiModelProperty("建议发货方式")
    private String suggestTransType;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** FBA移除时间 */
    @ApiModelProperty("FBA移除时间")
    private Date shipmentDate;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;
}
