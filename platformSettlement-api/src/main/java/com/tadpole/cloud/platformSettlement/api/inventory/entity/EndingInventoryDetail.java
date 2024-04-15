package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 期末库存列表
 * </p>
 *
 * @author gal
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ENDING_INVENTORY_DETAIL")
public class EndingInventoryDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 明细表存主表id
   */
  @TableField("OUT_ID")
  private Long outId;

  /**
   * 仓库名称
   */
  @TableField("WAREHOUSE_NAME")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @TableField("WAREHOUSE_CODE")
  private String warehouseCode;

  /**
   * 库存状态
   */
  @TableField("INVENTORY_STATUS")
  private String inventoryStatus;

  /**
   * 事业部
   */
  @TableField("DEPARTMENT")
  private String department;

  /**
   * Team
   */
  @TableField("TEAM")
  private String team;

  /**
   * SKU
   */
  @TableField("SKU")
  private String sku;

  /**
   * 物料编码
   */
  @TableField("MATERIAL_CODE")
  private String materialCode;

  /**
   * 在途数
   */
  @TableField("TRANSIT_NUM")
  private String transitNum;

  /**
   * 物流代发数
   */
  @TableField("LOGISTICS_NUM")
  private String logisticsNum;

  /**
   * 在库数
   */
  @TableField("STOCK_NUM")
  private String stockNum;

  /**
   * 期末库存数量
   */
  @TableField("ENDING_NUM")
  private String endingNum;

  /**
   * 是否作废
   */
  @TableField("IS_CANCEL")
  private Integer isCancel;

  /**
   * 同步状态
   */
  @TableField("SYNC_STATUS")
  private Integer syncStatus;

  /**
   * 创建人
   */
  @TableField("CREATE_USER")
  private String createUser;

  /**
   * 创建时间
   */
  @TableField("CREATE_TIME")
  private Date createTime;

  /**
   * 更新人
   */
  @TableField("UPDATE_USER")
  private String updateUser;

  /**
   * 更新时间
   */
  @TableField("UPDATE_TIME")
  private Date updateTime;

  /**
   * 数据月份
   */
  @TableField("DATA_MONTH")
  private String dataMonth;

  /**
   * 平台_店铺_站点
   */
  @TableField("ORG")
  private String org;

  @TableField("PLATFORM")
  private String platform;

  @TableField("SHOP_NAME")
  private String shopName;

  @TableField("site")
  private String site;

  private String year;

  private String month;

  /**
   * 备用字段，用于生成期末库存列表维度：平台_账号_站点_sku
   */
  @TableField(exist = false)
  private String uniqueOrg;

  /** 库存组织编码 */
  @TableField("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;
}