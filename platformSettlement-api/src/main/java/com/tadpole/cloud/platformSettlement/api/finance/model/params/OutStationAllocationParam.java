package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 补贴汇总表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutStationAllocationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    private String incomeType;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @ApiModelProperty("SKU")
    @ExcelProperty(value= "sku")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    private String department;

    @ApiModelProperty("TEAM")
    private String team;

    @ApiModelProperty("SALES_BRAND")
    private String salesBrand;

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;

    @ApiModelProperty("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    @ApiModelProperty("OTHER_ADVERTISEMENTS")
    private BigDecimal otherAdvertisements;

    @ApiModelProperty("BRUSHING_VALUE")
    private BigDecimal brushingValue;

    @ApiModelProperty("BRUSHING_SERVICE_CHARGE")
    private BigDecimal brushingServiceCharge;

    @ApiModelProperty("LOCAL_LOGISTICS_FEE")
    private BigDecimal localLogisticsFee;

    @ApiModelProperty("OVERSEAS_WAREHOUSE_FEE")
    private BigDecimal overseasWarehouseFee;

    @ApiModelProperty("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    @ApiModelProperty("DISPOSE_LOGISTICS_FEE")
    private BigDecimal disposeLogisticsFee;

    @ApiModelProperty("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    @ApiModelProperty("MOLD_OPENING_COST")
    private BigDecimal moldOpeningCost;

    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    private List skus;

    private List sites;

    

}