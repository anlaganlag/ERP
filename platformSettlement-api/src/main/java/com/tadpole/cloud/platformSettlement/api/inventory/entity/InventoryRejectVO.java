package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 驳回意见信息
 * </p>
 *
 * @author gal
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class InventoryRejectVO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  private String jobNumber;

  private String rejectDate;

  private String remark;
}