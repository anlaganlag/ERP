package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
* FBA存货存储超额费用报告
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_STORAGE_OVERAGE_FEES")
@ExcelIgnoreUnannotated
public class InventoryStorageOverageFees implements Serializable {

 private static final long serialVersionUID = 1L;

 @TableId(value = "ID", type = IdType.AUTO)
 private BigDecimal id;

 /**
  * 国家代码
  */
 @TableField("COUNTRY_CODE")
 private String countryCode;

 /**
  * 收费率
  */
 @ExcelProperty("费率")
 @TableField("CHARGE_RATE")
 private BigDecimal chargeRate;

 @TableField("STATUS")
 private BigDecimal status;

 @TableField("ORIGINAL_TASK_NAME")
 private String originalTaskName;

 @TableField("FILE_PATH")
 private String filePath;

 @TableField("CREATE_TIME")
 private Date createTime;

 @TableField("UPLOAD_DATE")
 private Date uploadDate;

 @ExcelProperty("期间")
 @DateTimeFormat("yyyy-MM")
 @TableField("CHARGED_DATE")
 private Date chargedDate;

 @ExcelProperty("账号")
 @TableField("SYS_SHOPS_NAME")
 private String sysShopsName;

 @ExcelProperty("站点")
 @TableField("SYS_SITE")
 private String sysSite;

 @ExcelProperty("存储类型")
 @TableField("STORAGE_TYPE")
 private String storageType;

 @ExcelProperty("总体积")
 @TableField("STORAGE_USAGE_VOLUME")
 private BigDecimal storageUsageVolume;

 @TableField("体积单位")
 private String volumeUnit;

 @ExcelProperty("限制体积")
 @TableField("STORAGE_LIMIT_VOLUME")
 private BigDecimal storageLimitVolume;

 @ExcelProperty("收费体积")
 @TableField("OVERAGE_VOLUME")
 private BigDecimal overageVolume;

 @ExcelProperty("币种")
 @TableField("CURRENCY_CODE")
 private String currencyCode;

 @ExcelProperty("超库容费（原币）")
 @TableField("CHARGED_FEE_AMOUNT")
 private BigDecimal chargedFeeAmount;

 @ExcelProperty("仓储费美金")
 @TableField("STORAGE_FEE")
 private BigDecimal storageFee;

 @TableField(exist = false)
 @ExcelProperty("状态")
 private String state;

 @ExcelProperty("超库容扣费美金")
 @TableField("STORAGE_DETAIL_FEE")
 private BigDecimal storageDetailFee;
}
