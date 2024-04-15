package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
public class EndingInventoryResult implements Serializable, BaseValidatingParam {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  private Long id;

  /**
   * 单据编号
   */
  @ApiModelProperty("BILL_CODE")
  @ExcelProperty(value = "单据编号")
  private String billCode;

  /**
   * 年份
   */
  @ApiModelProperty("YEAR")
  @ExcelProperty(value = "年份")
  private String year;

  /**
   * 月份
   */
  @ApiModelProperty("MONTH")
  @ExcelProperty(value = "月份")
  private String month;

  /**
   * 平台
   */
  @ApiModelProperty("PLATFORM")
  @ExcelProperty(value = "平台")
  private String platform;

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

  @ApiModelProperty("ORG")
  private String org;

  /**
   * 库存组织编码
   */
  @ApiModelProperty("INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  /**
   * WAREHOUSE_NAME
   */
  @ApiModelProperty("WAREHOUSE_NAME")
  @ExcelProperty(value = "仓库名称")
  private String warehouseName;

  /**
   * 仓库编码
   */
  @ApiModelProperty("WAREHOUSE_CODE")
  @ExcelProperty(value = "仓库编码")
  private String warehouseCode;

  /**
   * 仓库编码
   */
  @ApiModelProperty("INVENTORY_STATUS")
  @ExcelProperty(value = "库存状态")
  private String inventoryStatus;

  /**
   * 仓库编码
   */
  @ApiModelProperty("DEPARTMENT")
  @ExcelProperty(value = "事业部")
  private String department;

  /**
   * 仓库编码
   */
  @ApiModelProperty("TEAM")
  @ExcelProperty(value = "Team")
  private String team;

  /**
   * 仓库编码
   */
  @ApiModelProperty("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  /**
   * 仓库编码
   */
  @ApiModelProperty("MATERIAL_CODE")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  /**
   * 在途数
   */
  @ApiModelProperty(value = "TRANSIT_NUM")
  @ExcelProperty(value = "在途数")
  private String transitNum;

  /**
   * 物流待发数
   */
  @ApiModelProperty(value = "LOGISTICS_NUM")
  @ExcelProperty(value = "物流待发数")
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

  @ApiModelProperty("IS_CANCEL")
  @ExcelProperty(value = "是否作废")
  private String isCancel;

  @ApiModelProperty("SYNC_STATUS")
  @ExcelProperty(value = "同步状态")
  private String syncStatus;

  /**
   * 更新时间
   */
  @ApiModelProperty(value = "UPDATE_TIME")
  private Date updateTime;

  /**
   * 更新人
   */
  @ApiModelProperty(value = "UPDATE_USER")
  private String updateUser;

  @ApiModelProperty("OUT_ID")
  private Long outId;
}
