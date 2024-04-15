package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
public class AdvertisingBudgetResult implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private BigDecimal id;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

  /**
   * 事业部
   */
  @ApiModelProperty("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * Team
   */
  @ApiModelProperty("TEAM")
  @ExcelProperty(value = "Team")
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

  /**
   * 年度预算
   */
  @ContentStyle(dataFormat = 2)
  @ExcelProperty(value = "年度预算")
  private BigDecimal yearMoney;

  /**
   * 广告占比
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("ADVERTISING_PROPORTION")
  @ExcelProperty(value = "广告占比")
  private BigDecimal advertisingProportion;

  /**
   * 一季度占比
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_ONE_PROPORTION")
  @ExcelProperty(value = "一季度占比")
  private BigDecimal seasonOneProportion;

  /**
   * 二季度占比
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_TWO_PROPORTION")
  @ExcelProperty(value = "二季度占比")
  private BigDecimal seasonTwoProportion;

  /**
   * 三季度占比
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_THREE_PROPORTION")
  @ExcelProperty(value = "三季度占比")
  private BigDecimal seasonThreeProportion;

  /**
   * 四季度占比
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_FOUR_PROPORTION")
  @ExcelProperty(value = "四季度占比")
  private BigDecimal seasonFourProportion;

  /**
   * 一季度金额
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_ONE_MONEY")
  @ExcelProperty(value = "一季度金额")
  private BigDecimal seasonOneMoney;

  /**
   * 二季度金额
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_TWO_MONEY")
  @ExcelProperty(value = "二季度金额")
  private BigDecimal seasonTwoMoney;

  /**
   * 三季度金额
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_THREE_MONEY")
  @ExcelProperty(value = "三季度金额")
  private BigDecimal seasonThreeMoney;

  /**
   * 四季度金额
   */
  @ContentStyle(dataFormat = 2)
  @ApiModelProperty("SEASON_FOUR_MONEY")
  @ExcelProperty(value = "四季度金额")
  private BigDecimal seasonFourMoney;

  /**
   * 年度
   */
  @ApiModelProperty("YEAR")
  @ExcelProperty(value = "年度")
  private BigDecimal year;

  /**
   * 版本
   */
  @ApiModelProperty("VERSION")
  @ExcelProperty(value = "版本")
  private String version;

  /**
   * 币种
   */
  @ApiModelProperty("CURRENCY")
  @ExcelProperty(value = "币种")
  private String currency;

  /**
   * 确认状态
   */
  @ApiModelProperty("CONFIRM_STATUS")
  private BigDecimal confirmStatus;

  /**
   * 确认状态中文信息 已确认和未确认
   */
  @ApiModelProperty("CONFIRM_STATUS_TXT")
  @ExcelProperty(value = "确认状态文本")
  private String confirmStatusTxt;

  /**
   * 创建日期
   */
  @ApiModelProperty("CREATE_AT")
  @ExcelProperty(value = "创建日期")
  private Date createAt;

  /**
   * 创建人
   */
  @ApiModelProperty("CREATE_BY")
  @ExcelProperty(value = "创建人")
  private String createBy;

  /**
   * 确认日期
   */
  @ApiModelProperty("CONFIRM_DATE")
  @ExcelProperty(value = "确认日期")
  private Date confirmDate;

  /**
   * 确认人
   */
  @ApiModelProperty("CONFIRM_BY")
  @ExcelProperty(value = "确认人")
  private String confirmBy;

  /**
   * 修改日期
   */
  @ApiModelProperty("UPDATE_AT")
  @ExcelProperty(value = "修改日期")
  private Date updateAt;

  /**
   * 修改人
   */
  @ApiModelProperty("UPDATE_BY")
  @ExcelProperty(value = "修改人")
  private String updateBy;
}