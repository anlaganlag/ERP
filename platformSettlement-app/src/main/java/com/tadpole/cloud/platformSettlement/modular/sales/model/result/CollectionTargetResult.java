package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 回款目标
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class CollectionTargetResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value ="平台")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value ="事业部")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    @ExcelProperty(value ="Team")
    private String team;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /**
     * 运营大类
     */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /**
     * 销售品牌
     */
    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String companyBrand;

    /** 收缩线 */
    @ApiModelProperty("RETRACT_LINE")
    @ExcelProperty(value = "收缩线")
    private String retractLine;

    /** 新旧品 */
    @ApiModelProperty("newold_product")
    @ExcelProperty(value = "新旧品")
    private String newoldProduct;

    /** 年度销售目标 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("YEAR_TARGET")
    @ExcelProperty(value = "年度回款目标")
    private BigDecimal yearTarget;

    /** 一季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_ONE")
    @ExcelProperty(value ="Q1")
    private BigDecimal seasonOne;

    /** 二季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_TWO")
    @ExcelProperty(value ="Q2")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_THREE")
    @ExcelProperty(value ="Q3")
    private BigDecimal seasonThree;

    /** 四季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_FOUR")
    @ExcelProperty(value ="Q4")
    private BigDecimal seasonFour;

    /** US */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("US")
    @ExcelProperty(value ="US")
    private BigDecimal us;

    /** CA */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("CA")
    @ExcelProperty(value ="CA")
    private BigDecimal ca;

    /** MX */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("MX")
    @ExcelProperty(value ="MX")
    private BigDecimal mx;

    /** UK */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("UK")
    @ExcelProperty(value ="UK")
    private BigDecimal uk;

    /** DE */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("DE")
    @ExcelProperty(value ="DE")
    private BigDecimal de;

    /** FR */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("FR")
    @ExcelProperty(value ="FR")
    private BigDecimal fr;

    /** IT */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("IT")
    @ExcelProperty(value ="IT")
    private BigDecimal it;

    /** ES */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("ES")
    @ExcelProperty(value ="ES")
    private BigDecimal es;

    /** NL */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("NL")
    @ExcelProperty(value ="NL")
    private BigDecimal nl;

    /** SE */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SE")
    @ExcelProperty(value ="SE")
    private BigDecimal se;

    /** JP */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("JP")
    @ExcelProperty(value ="JP")
    private BigDecimal jp;

    /** AU */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("AU")
    @ExcelProperty(value ="AU")
    private BigDecimal au;

    /** AE */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("AE")
    @ExcelProperty(value ="AE")
    private BigDecimal ae;

    /** SG */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SG")
    @ExcelProperty(value ="SG")
    private BigDecimal sg;

    /** SA */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SA")
    @ExcelProperty(value ="SA")
    private BigDecimal sa;

    /** IND */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("IND")
    @ExcelProperty(value ="IN")
    private BigDecimal ind;

    /** PL */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("PL")
    @ExcelProperty(value ="PL")
    private BigDecimal pl;

    /** TR */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("TR")
    @ExcelProperty(value ="TR")
    private BigDecimal tr;

    /** BE */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("BE")
    @ExcelProperty(value ="BE")
    private BigDecimal be;

    /** EU */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("EU")
    @ExcelProperty(value ="EU")
    private BigDecimal eu;

    /** 年度 */
    @ApiModelProperty("YEAR")
    @ExcelProperty(value ="年度")
    private BigDecimal year;

    /** 版本 */
    @ApiModelProperty("VERSION")
    @ExcelProperty(value ="版本")
    private String version;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 确认状态 */
    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建日期 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value ="创建日期")
    private Date createAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value ="创建人")
    private String createBy;

    /** 确认日期 */
    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    /** 确认人 */
    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    /** 修改日期 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;
}