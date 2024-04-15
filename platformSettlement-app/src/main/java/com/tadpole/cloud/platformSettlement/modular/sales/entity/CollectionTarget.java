package com.tadpole.cloud.platformSettlement.modular.sales.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("COLLECTION_TARGET")
@ExcelIgnoreUnannotated
public class CollectionTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;


    /** 平台 */
    @TableField("PLATFORM")
    @ExcelProperty(value = "平台")
    private String platform;

    /** 事业部 */
    @TableField("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    /** Team */
    @TableField("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    /** 账号 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /** 销售品牌 */
    @TableField("COMPANY_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String companyBrand;


    /** 收缩线 */
    @TableField("RETRACT_LINE")
    @ExcelProperty(value = "收缩线")
    private String retractLine;


    /** 新旧品 */
    @TableField("NEWOLD_PRODUCT")
    @ExcelProperty(value = "新旧品")
    private String newoldProduct;

    /** 一季度 */
    @TableField("SEASON_ONE")
    @ExcelProperty(value = "Q1")
    private BigDecimal seasonOne;

    /** 二季度 */
    @TableField("SEASON_TWO")
    @ExcelProperty(value = "Q2")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @TableField("SEASON_THREE")
    @ExcelProperty(value = "Q3")
    private BigDecimal seasonThree;

    /** 四季度 */
    @TableField("SEASON_FOUR")
    @ExcelProperty(value = "Q4")
    private BigDecimal seasonFour;

    /** US */
    @TableField("US")
    @ExcelProperty(value = "US")
    private BigDecimal us;

    /** CA */
    @TableField("CA")
    @ExcelProperty(value = "CA")
    private BigDecimal ca;

    /** MX */
    @TableField("MX")
    @ExcelProperty(value = "MX")
    private BigDecimal mx;

    /** UK */
    @TableField("UK")
    @ExcelProperty(value = "UK")
    private BigDecimal uk;

    /** DE */
    @TableField("DE")
    @ExcelProperty(value = "DE")
    private BigDecimal de;

    /** FR */
    @TableField("FR")
    @ExcelProperty(value = "FR")
    private BigDecimal fr;

    /** IT */
    @TableField("IT")
    @ExcelProperty(value = "IT")
    private BigDecimal it;

    /** ES */
    @TableField("ES")
    @ExcelProperty(value = "ES")
    private BigDecimal es;

    /** NL */
    @TableField("NL")
    @ExcelProperty(value = "NL")
    private BigDecimal nl;

    /** SE */
    @TableField("SE")
    @ExcelProperty(value = "SE")
    private BigDecimal se;

    /** JP */
    @TableField("JP")
    @ExcelProperty(value = "JP")
    private BigDecimal jp;

    /** AU */
    @TableField("AU")
    @ExcelProperty(value = "AU")
    private BigDecimal au;

    /** AE */
    @TableField("AE")
    @ExcelProperty(value = "AE")
    private BigDecimal ae;

    /** SG */
    @TableField("SG")
    @ExcelProperty(value = "SG")
    private BigDecimal sg;

    /** SA */
    @TableField("SA")
    @ExcelProperty(value = "SA")
    private BigDecimal sa;

    /** IND */
    @TableField("IND")
    @ExcelProperty(value = "IN")
    private BigDecimal ind;

    /** PL */
    @TableField("PL")
    @ExcelProperty(value = "PL")
    private BigDecimal pl;

    /** TR */
    @TableField("TR")
    @ExcelProperty(value = "TR")
    private BigDecimal tr;


    /** BE */
    @TableField("BE")
    @ExcelProperty(value = "BE")
    private BigDecimal be;

    /** EU */
    @TableField("EU")
    @ExcelProperty(value = "EU")
    private BigDecimal eu;

    /** 年度 */
    @TableField("YEAR")
    private BigDecimal year;

    /** 版本 */
    @TableField("VERSION")
    private String version;

    /** 币种 */
    @TableField("CURRENCY")
    private String currency;

    /** 确认状态 */
    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @TableField("CREATE_AT")
    @ExcelProperty(value = "创建日期")
    private Date createAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /** 确认日期 */
    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    /** 确认人 */
    @TableField("CONFIRM_BY")
    private String confirmBy;

    /** 修改日期 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** id */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    public String getD8() {
        return platform+"-"+department+"-"+team+"-"+shopName+"-"+productType+"-"+companyBrand+"-"+retractLine+"-"+newoldProduct;
    }

}