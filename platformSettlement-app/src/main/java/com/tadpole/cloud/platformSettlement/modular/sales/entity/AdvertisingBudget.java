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
 * 广告预算
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ADVERTISING_BUDGET")
@ExcelIgnoreUnannotated
public class AdvertisingBudget implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "导入错误备注")
  private String uploadRemark;


  /**
   * id
   */
  @TableId(value = "ID", type = IdType.AUTO)
  private BigDecimal id;


  /**
   * 平台
   */
  @TableField("PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

  /**
   * 事业部
   */
  @TableField("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * Team
   */
  @TableField("TEAM")
  @ExcelProperty(value = "Team")
  private String team;

  /** 账号 */
  @TableField("SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  /**
   * 运营大类
   */
  @TableField("PRODUCT_TYPE")
  @ExcelProperty(value = "运营大类")
  private String productType;

  /**
   * 销售品牌
   */
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

  /**
   * 广告占比
   */
  @TableField("ADVERTISING_PROPORTION")
  @ExcelProperty(value = "广告占比")
  private BigDecimal advertisingProportion;

  /**
   * 一季度占比
   */
  @TableField("SEASON_ONE_PROPORTION")
  @ExcelProperty(value = "Q1占比")
  private BigDecimal seasonOneProportion;

  /**
   * 二季度占比
   */
  @TableField("SEASON_TWO_PROPORTION")
  @ExcelProperty(value = "Q2占比")
  private BigDecimal seasonTwoProportion;

  /**
   * 三季度占比
   */
  @TableField("SEASON_THREE_PROPORTION")
  @ExcelProperty(value = "Q3占比")
  private BigDecimal seasonThreeProportion;

  /**
   * 四季度占比
   */
  @TableField("SEASON_FOUR_PROPORTION")
  @ExcelProperty(value = "Q4占比")
  private BigDecimal seasonFourProportion;


  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "Q1销售额")
  private BigDecimal salVolSeasonOne;

  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "Q2销售额")
  private BigDecimal salVolSeasonTwo;


  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "Q3销售额")
  private BigDecimal salVolSeasonThree;


  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "Q4销售额")
  private BigDecimal salVolSeasonFour;

  /**
   * 一季度金额
   */
  @TableField("SEASON_ONE_MONEY")
  private BigDecimal seasonOneMoney;

  /**
   * 二季度金额
   */
  @TableField("SEASON_TWO_MONEY")
  private BigDecimal seasonTwoMoney;

  /**
   * 三季度金额
   */
  @TableField("SEASON_THREE_MONEY")
  private BigDecimal seasonThreeMoney;

  /**
   * 四季度金额
   */
  @TableField("SEASON_FOUR_MONEY")
  private BigDecimal seasonFourMoney;


  /**
   * 年度
   */
  @TableField("YEAR")
  private String year;

  /**
   * 版本
   */
  @TableField("VERSION")
  private String version;

  /**
   * 币种
   */
  @TableField("CURRENCY")
  private String currency;

  /**
   * 确认状态
   */
  @TableField("CONFIRM_STATUS")
  private BigDecimal confirmStatus;

  /**
   * 创建日期
   */
  @TableField("CREATE_AT")
  private Date createAt;

  /**
   * 创建人
   */
  @TableField("CREATE_BY")
  private String createBy;

  /**
   * 确认日期
   */
  @TableField("CONFIRM_DATE")
  private Date confirmDate;

  /**
   * 确认人
   */
  @TableField("CONFIRM_BY")
  private String confirmBy;

  /**
   * 修改日期
   */
  @TableField("UPDATE_AT")
  private Date updateAt;

  /**
   * 修改人
   */
  @TableField("UPDATE_BY")
  private String updateBy;






}