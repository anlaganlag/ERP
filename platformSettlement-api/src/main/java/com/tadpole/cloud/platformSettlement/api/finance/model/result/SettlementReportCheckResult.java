package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * AZ结算报告审核
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated

public class SettlementReportCheckResult implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @ApiModelProperty("ID")
  private BigDecimal id;

  /**
   * 源仓任务名称
   */
  @ApiModelProperty("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM_NAME")
  @ExcelProperty(value = "平台")
  private String platformName;

  /**
   * 账号
   */
  @ApiModelProperty("SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  /**
   * 站点
   */
  @ApiModelProperty("SITE")
  @ExcelProperty(value = "站点")
  private String site;

  /**
   * 报告类型
   */
  @ApiModelProperty("REPORT_TYPE")
  @ExcelProperty(value = "源仓任务名称")
  private String reportType;

  /**
   * 单据类型
   */
  @ApiModelProperty("BILL_TYPE")
  @ExcelProperty(value = "报告类型")
  private String billType;

  /**
   * 结算id
   */
  @ApiModelProperty("SETTLEMENT_ID")
  @ExcelProperty(value = "结算ID")
  private String settlementId;

  /**
   * 开始时间
   */
  @ApiModelProperty("START_TIME")
  @ExcelProperty(value = "开始时间")
  private String startTime;

  /**
   * 结束时间
   */
  @ApiModelProperty("END_TIME")
  @ExcelProperty(value = "结束时间")
  private String endTime;

  /**
   * 汇款日期
   */
  @ApiModelProperty("REMITTANCE_DATE")
  @ExcelProperty(value = "汇款日期")
  private String remittanceDate;

  /**
   * 汇款账号
   */
  @ApiModelProperty("REMITTANCE_ACCOUNT")
  @ExcelProperty(value = "汇款账号")
  private String remittanceAccount;

  /**
   * 汇款银行
   */
  @ApiModelProperty("REMITTANCE_BANK")
  @ExcelProperty(value = "汇款银行")
  private String remittanceBank;

  /**
   * 收款币种
   */
  @ApiModelProperty("PROCEEDS_CURRENCY")
  @ExcelProperty(value = "收款币种")
  private String proceedsCurrency;

  /**
   * 收款金额
   */
  @ApiModelProperty("REMITTANCE_MONEY")
  @ExcelProperty(value = "收款金额")
  private String remittanceMoney;

  /**
   * 原币
   */
  @ApiModelProperty("ORIGINAL_CURRENCY")
  @ExcelProperty(value = "原币")
  private String originalCurrency;

  /**
   * 金额
   */
  @ApiModelProperty("MONEY")
  @ExcelProperty(value = "金额")
  private String money;

  /**
   * 总金额
   */
  @ApiModelProperty("TOTAL_MONEY")
  @ExcelProperty(value = "总金额")
  private String totalMoney;

  /**
   * 审核状态
   */
  @ApiModelProperty("STATUS")
  @ExcelProperty(value = "审核状态")
  private String status;

  /**
   * 审核人员
   */
  @ApiModelProperty("VERIFY_BY")
  @ExcelProperty(value = "审核人员")
  private String verifyBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("CREATE_AT")
  private String createAt;

  /**
   * 修改时间
   */
  @ApiModelProperty("UPDATE_AT")
  private String updateAt;

}