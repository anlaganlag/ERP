package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * AZ库存调整列表(增加)
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
@TableName("View_OrgCode_To_Bi")
@ExcelIgnoreUnannotated

public class ErpOrgCode implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  @TableField("OrganizationCode")
  private String organizationCode;

  /**
   * 名称
   */
  @TableField("OrganizationName")
  private String organizationName;

  /**
   * 类型
   */
  @TableField("OrganizationType")
  private String organizationType;

  /**
   * 组织
   */
  @TableField("UseOrganization")
  private String useOrganization;
}