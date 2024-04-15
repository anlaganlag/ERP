package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* <p>
* 站内手工分摊表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_STATION_MANUAL_ALLOCATION")
public class StationManualAllocationParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ALLOC_ID")
    private BigDecimal allocId;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    private List<String> shopNames;


    /** 站点 */
    @TableField("SITE")
    private String site;

    private List<String> sites;


    @TableField("SKU")
    private String sku;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("TEAM")
    private String team;

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 原币 */
    @TableField("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @TableField("SALES_BRAND")
    private String salesBrand;

    /** advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /** storage_fee */
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    @TableField("CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private LocalDateTime createAt;

    @TableId(value="ID",type = IdType.AUTO)
    private BigDecimal id;

    private List skus;



    /** Storage Fee原值 */
    @TableField("STORAGE_FEE_ORI")
    private BigDecimal storageFeeOri;



    /** 仓储费分摊比率 */
    @TableField("STORAGE_FEE_ALLOC_RATE")
    private BigDecimal storageFeeAllocRate;



    /** 销毁费 */
    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;



    /** 移除费 */
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;






}