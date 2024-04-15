package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 亚马逊物流买家货件销售
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
public class FbaShipmentSalesVO implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 亚马逊订单编号
   */
  @TableField("AMAZON_ORDER_ID")
  @ExcelProperty(value = "amazonOrderId")
  private String amazonOrderId;

  @TableField("SALES_ORGANIZATION")
  @ExcelProperty(value = "销售组织")
  private String salesOrganization;

  @TableField("SALES_ORGANIZATION_CODE")
  @ExcelProperty(value = "销售组织编码")
  private String salesOrganizationCode;

  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "导入错误备注")
  private String uploadRemark;
}