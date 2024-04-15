package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@TableName("FBA_SHIPMENT_SALES")
@ExcelIgnoreUnannotated
public class FbaShipmentSales implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField("ORIGINAL_TASK_NAME")
  @ExcelProperty(value = "源仓任务名称")
  private String originalTaskName;

  @TableField("FILE_PATH")
  @ExcelProperty(value = "文件存放路径")
  private String filePath;

  @TableField("SHIPMENT_DATE")
  @ExcelProperty(value = "shipmentDate")
  private Date shipmentDate;

  /**
   * 买家SKU
   */
  @TableField("SKU")
  @ExcelProperty(value = "sku")
  private String sku;

  @TableField("FNSKU")
  @ExcelProperty(value = "fnsku")
  private String fnsku;

  @TableField("ASIN")
  @ExcelProperty(value = "asin")
  private String asin;

  /**
   * 运营中心编号
   */
  @TableField("FULFILLMENT_CENTER_ID")
  @ExcelProperty(value = "fulfillmentCenterId")
  private String fulfillmentCenterId;

  /**
   * 数量
   */
  @TableField("QUANTITY")
  @ExcelProperty(value = "quantity")
  private BigDecimal quantity;

  /**
   * 亚马逊订单编号
   */
  @TableField("AMAZON_ORDER_ID")
  @ExcelProperty(value = "amazonOrderId")
  private String amazonOrderId;

  /**
   * 货币
   */
  @TableField("CURRENCY")
  @ExcelProperty(value = "currency")
  private String currency;

  /**
   * 每件商品价格
   */
  @TableField("ITEM_PRICE_PER_UNIT")
  @ExcelProperty(value = "itemPricePerUnit")
  private BigDecimal itemPricePerUnit;

  /**
   * 配送价格
   */
  @TableField("SHIPPING_PRICE")
  @ExcelProperty(value = "shippingPrice")
  private BigDecimal shippingPrice;

  /**
   * 买家为礼品包装支付的金额
   */
  @TableField("GIFT_WRAP_PRICE")
  @ExcelProperty(value = "giftWrapPrice")
  private BigDecimal giftWrapPrice;

  /**
   * 配送城市
   */
  @TableField("SHIP_CITY")
  @ExcelProperty(value = "shipCity")
  private String shipCity;

  /**
   * 配送州
   */
  @TableField("SHIP_STATE")
  @ExcelProperty(value = "shipState")
  private String shipState;

  /**
   * 配送邮政编码
   */
  @TableField("SHIP_POSTAL_CODE")
  @ExcelProperty(value = "shipPostalCode")
  private String shipPostalCode;

  /**
   * 创建日期
   */
  @TableField("CREATE_TIME")
  @ExcelProperty(value = "createTime")
  private Date createTime;

  /**
   * 上传日期
   */
  @TableField("UPLOAD_DATE")
  @ExcelProperty(value = "uploadDate")
  private Date uploadDate;

  /**
   * 账号
   */
  @TableField("SYS_SHOPS_NAME")
  @ExcelProperty(value = "sysShopsName")
  private String sysShopsName;

  /**
   * 站点
   */
  @TableField("SYS_SITE")
  @ExcelProperty(value = "sysSite")
  private String sysSite;

  @TableField("SYS_SELLER_ID")
  @ExcelProperty(value = "sysSellerId")
  private String sysSellerId;

  /**
   * 平台
   */
  @TableField("PLATFORM")
  @ExcelProperty(value = "platform")
  private String platform;

  @TableField("VERIFY_AT")
  private String verifyAt;

  @TableField("VERIFY_BY")
  private String verifyBy;

  @TableField("VERIFY_STATUS")
  private String verifyStatus;

  @ExcelProperty(value = "审核状态")
  @TableField(exist = false)
  private String verifyStatusCN;

  @TableField("UPDATE_BY")
  private String updateBy;

  @TableField("GENERATE_STATUS")
  private Integer generateStatus;

  @ExcelProperty(value = "生成状态")
  @TableField(exist = false)
  private String generateStatusCN;

  @TableField("BILL_CODE")
  private String billCode;

  @TableField("SALES_ORGANIZATION")
  @ExcelProperty(value = "销售组织")
  private String salesOrganization;

  @TableField("SALES_ORGANIZATION_CODE")
  @ExcelProperty(value = "销售组织编码")
  private String salesOrganizationCode;

  @TableField("INVENTORY_ORGANIZATION_CODE")
  @ExcelProperty(value = "库存组织编码")
  private String inventoryOrganizationCode;

  @TableField("WAREHOUSE_NAME")
  private String warehouseName;

  @TableField("WAREHOUSE_CODE")
  private String warehouseCode;

  @TableField("ORG")
  private String org;

  @TableField("UPLOAD_MARK")
  private String uploadMark;

  @TableField(exist = false)
  @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
  @ExcelProperty(value = "导入错误备注")
  private String uploadRemark;
}