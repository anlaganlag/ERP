package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class EndingInventoryParam extends BaseRequest implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  /**
   * 单据编号
   */
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

  /**
   * 在途数
   */
  @ApiModelProperty(value = "TRANSIT_NUM")
  @ExcelProperty(value = "在途数")
  private String transitNum;

  /**
   * 物流代发数
   */
  @ApiModelProperty(value = "LOGISTICS_NUM")
  @ExcelProperty(value = "物流代发数")
  private String logisticsNum;

  /**
   * 在库数
   */
  @ApiModelProperty(value = "STOCK_NUM")
  @ExcelProperty(value = "在库数")
  private String stockNum;

  /**
   * 期末库存数量
   */
  @ApiModelProperty(value = "ENDING_NUM")
  @ExcelProperty(value = "期末库存数量")
  private String endingNum;

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

  @ApiModelProperty("OUT_ID")
  private Long outId;

  /**
   * 更新时间
   */
  @ApiModelProperty("更新时间")
  private Date updateTime;

  /**
   * 更新人
   */
  @ApiModelProperty("更新人")
  private String updateUser;

  private Boolean isDefect;

  private String monValue;

  private List skus;

  private List years;

  private List months;

  private List platforms;

  private List shopNames;

  private List sites;

  private List departments;

  private List teams;

  private List syncStatuss;

  private String[] material;

  private List<String> emailList;

  private List<String> idList;
}
