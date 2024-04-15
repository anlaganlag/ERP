package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import java.io.Serializable;

/**
 * <p>
 * AZ结算异常监控
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class CwLingxingSettlementResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableField("ID")
  @ExcelProperty(value = "序号")
  private String ID;

  @TableField("PAYMENT_DATE")
  @ColumnWidth(20)
  @ExcelProperty(value = "订单日期")
  private String paymentDate;

  @TableField("SHOP_NAME")
  @ExcelProperty(value = "账号")
  private String shopName;

  @TableField("SITE")
  @ExcelProperty(value = "站点")
  private String site;

  @TableField("AMAZON_ORDER_ID")
  @ColumnWidth(20)
  @ExcelProperty(value = "交易ID")
  private String amazonOrderId;

  @TableField("PAYMENT_AMOUNT")
  @ColumnWidth(20)
  @ExcelProperty(value = "客户付款金额")
  private String paymentAmount;

  @TableField("SETTLEMENT_AMOUNT")
  @ColumnWidth(20)
  @ExcelProperty(value = "应结算金额")
  private String settlementAmount;

  @TableField("AUTUAL_SETTLEMENT_AMOUNT")
  @ColumnWidth(20)
  @ExcelProperty(value = "实际结算金额")
  private String autualSettlementAmount;

  @TableField("SETTLEMENT_ABNORMAL")
  @ColumnWidth(20)
  @ExcelProperty(value = "是否结算异常")
  private String settlementAbnormal;

}