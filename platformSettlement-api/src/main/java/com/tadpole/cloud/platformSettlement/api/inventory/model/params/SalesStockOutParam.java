package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class SalesStockOutParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  @ApiModelProperty("OUT_ID")
  private BigDecimal outId;

  @ApiModelProperty("BILL_CODE")
  private String billCode;

  /**
   * 年份
   */
  @ApiModelProperty("YEAR")
  private String year;

  /**
   * 月份
   */
  @ApiModelProperty("MONTH")
  private String month;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  private String platform;

  /**
   * 账号
   */
  @ApiModelProperty("SHOP_NAME")
  private String shopName;

  /**
   * 站点
   */
  @ApiModelProperty("SITE")
  private String site;

  /**
   * 库存组织编码
   */
  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  private String inventoryOrganizationCode;

  /**
   * 库存状态
   */
  @ApiModelProperty("INVENTORY_STATUS")
  private String inventoryStatus;

  /**
   * WAREHOUSE_NAME
   */
  @ApiModelProperty("WAREHOUSE_NAME")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @ApiModelProperty("WAREHOUSE_CODE")
  private String warehouseCode;

  /**
   * 事业部
   */
  @ApiModelProperty("DEPARTMENT")
  private String department;

  /**
   * Team
   */
  @ApiModelProperty("Team")
  private String team;

  /**
   * SKU
   */
  @ApiModelProperty("SKU")
  private String sku;

  /**
   * 物料编码
   */
  @ApiModelProperty("MATERIAL_CODE")
  private String materialCode;

  private String[] material;

  /**
   * 销售组织
   */
  @ApiModelProperty("SALES_ORGANIZATION")
  private String salesOrganization;

  /**
   * 销售组织编码
   */
  @ApiModelProperty("SALES_ORGANIZATION_CODE")
  private String salesOrganizationCode;

  /**
   * 销售出库数量
   */
  @ApiModelProperty("SALES_STOCKOUT_AMOUNT")
  private String salesStockoutAmount;

  /**
   * 是否作废
   */
  @ApiModelProperty("IS_CANCEL")
  private Integer isCancel;

  /**
   * 同步状态
   */
  @ApiModelProperty("SYNC_STATUS")
  private Integer syncStatus;

  /**
   * 最后操作时间
   */
  @ApiModelProperty("UPDATE_AT")
  private Date updateAt;

  /**
   * 最后操作人
   */
  @ApiModelProperty("UPDATE_BY")
  private String updateBy;

  private Boolean isDefect;

  private List skus;

  private List years;

  private List months;

  private List platforms;

  private List shopNames;

  private List sites;

  private List departments;

  private List teams;

  private List<String> emailList;

  private List<String> idList;

  private List syncStatuss;

  /**
   * 任务类型 0：手动同步，1：定时同步
   */
  private Integer syncType;
}
