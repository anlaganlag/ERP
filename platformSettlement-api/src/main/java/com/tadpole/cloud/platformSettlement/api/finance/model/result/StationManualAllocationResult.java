package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 站内手工分摊表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class StationManualAllocationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @ExcelProperty(value= "被分摊id")
    private BigDecimal id;

    @ApiModelProperty("ALLOC_ID")
    @ExcelProperty(value= "父ID")
    private BigDecimal allocId;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value= "报告类型")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value= "收入确认类型")
    private String incomeType;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    @ExcelProperty(value= "核算币种")
    private String accountingCurrency = "USA";

    @ApiModelProperty("SKU")
    @ExcelProperty(value= "SKU")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value= "Team")
    private String team;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value = "类目")
    private String category;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("STYLE")
    @ExcelProperty(value = "款式")
    private String style;

    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    @ApiModelProperty("DESIGN")
    @ExcelProperty(value = "图案")
    private String design;

    @ApiModelProperty("FIT_BRAND")
    @ExcelProperty(value = "适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("MODEL")
    @ExcelProperty(value = "型号")
    private String model;

    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;

    @ApiModelProperty("COLOR")
    @ExcelProperty(value = "颜色")
    private String color;

    @ApiModelProperty("SIZES")
    @ExcelProperty(value = "尺码")
    private String sizes;

    @ApiModelProperty("PACKING")
    @ExcelProperty(value = "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    @ApiModelProperty("TYPE")
    @ExcelProperty(value = "适用机型")
    private String type;

    @ApiModelProperty("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    @ApiModelProperty("BUYER")
    @ExcelProperty(value = "采购员")
    private String buyer;

    @ApiModelProperty("DEVELOPER")
    @ExcelProperty(value = "开发人员")
    private String developer;

    @ApiModelProperty("ACCOUNT_DATE")
    @ExcelProperty(value = "核算日期")
    private String accountDate;


    /** advertising */
    @ApiModelProperty("ADVERTISING")
    @ExcelProperty(value= "Advertising")
    private BigDecimal advertising;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    @ExcelProperty(value= "Storage_Fee结算数据")
    private BigDecimal storageFee;



    /** Storage Fee原值 */
    @TableField("STORAGE_FEE_ORI")
    @ExcelProperty(value= "Storage_Fee业务数据")
    private BigDecimal storageFeeOri;

    /** 仓储费分摊比率 */
    @TableField("STORAGE_FEE_ALLOC_RATE")
    @ExcelProperty(value= "仓储费分摊比率")
    private BigDecimal storageFeeAllocRate;



    /** 销毁费 */
    @TableField("DISPOSE_FEE")
    @ExcelProperty(value= "销毁费")
    private BigDecimal disposeFee;



    /** 移除费 */
    @TableField("REMOVAL_DEAL")
    @ExcelProperty(value= "移除费")
    private BigDecimal removalDeal;




    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value= "确认状态")
    private String confirmStatusTxt;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value = "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value= "确认时间")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;






    @TableField(exist = false)
    private String siteDimension;

    public String getSiteDimension() {
        return  fiscalPeriod + shopName + site;
    }


}