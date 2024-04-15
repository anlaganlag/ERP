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
* 长期仓储费
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
@TableName("LONG_TERM_STORAGE_FEE_CHARGES")
@ExcelIgnoreUnannotated
public class LongTermStorageFeeCharges implements Serializable {

 private static final long serialVersionUID = 1L;

 @TableId(value = "ID", type = IdType.AUTO)
 private Long id;

 @TableField("TITLE")
 private String title;

 /**
  * 状态
  */
 @TableField("CONDITION")
 private String condition;

 /**
  * 国家
  */
 @TableField("COUNTRY")
 private String country;

 /**
  * 是否加入轻小商品计划
  */
 @TableField("ENROLLED_IN_SMALL_AND_LIGHT")
 private String enrolledInSmallAndLight;

 @TableField("STATUS")
 private BigDecimal status;

 @TableField("ORIGINAL_TASK_NAME")
 private String originalTaskName;

 @TableField("FILE_PATH")
 private String filePath;

 @TableField("CREATE_TIME")
 private Date createTime;

 /**
  * 报告文件上传日期(生成上传报告任务的日期)
  */
 @TableField("UPLOAD_DATE")
 private Date uploadDate;

 @ExcelProperty("期间")
 @DateTimeFormat("yyyy-MM")
 @TableField("SNAPSHOT_DATE")
 private Date snapshotDate;

 @ExcelProperty("账号")
 @TableField("SYS_SHOPS_NAME")
 private String sysShopsName;

 @ExcelProperty("站点")
 @TableField("SYS_SITE")
 private String sysSite;

 @ExcelProperty("ASIN")
 @TableField("ASIN")
 private String asin;

 @ExcelProperty("SKU")
 @TableField("SKU")
 private String sku;

 @ExcelProperty("FNSKU")
 @TableField("FNSKU")
 private String fnsku;

 @TableField("department")
 @ExcelProperty("事业部")
 private String department;

 @TableField("team")
 @ExcelProperty("Team")
 private String team;

 @ExcelProperty("物料")
 @TableField("MATERIAL_CODE")
 private String materialCode;

 @ExcelProperty("单个体积")
 @TableField("单个体积")
 private BigDecimal unitVolume;

 @ExcelProperty("体积单位")
 @TableField("VOLUME_UNIT")
 private String volumeUnit;

 @ExcelProperty({"12个月以上长期仓储费","长期仓储费"})
 @TableField("长期仓储费(12个月)")
 private BigDecimal mo12LongTermsStorageFee;

 @ExcelProperty({"12个月以上长期仓储费","收费商品数量"})
 @TableField("收费库存数量(12个月")
 private Long qtyCharged12Mo;

 @ExcelProperty({"6个月以上长期仓储费","长期仓储费"})
 @TableField("长期仓储费(6个月)")
 private BigDecimal mo6LongTermsStorageFee;

 @ExcelProperty({"6个月以上长期仓储费","收费商品数量"})
 @TableField("收费库存数量(6个月)")
 private Long qtyCharged6Mo;

 @ExcelProperty("币别")
 @TableField("CURRENCY")
 private String currency;

 @TableField(exist = false)
 @ExcelProperty("仓储费原币")
 private BigDecimal longTermsStorageFee;

 @ExcelProperty("仓储费美金")
 @TableField("STORAGE_FEE")
 private BigDecimal storageFee;

 @ExcelProperty("仓储费扣费（美金）")
 @TableField("STORAGE_DETAIL_FEE")
 private BigDecimal storageDetailFee;

 @TableField(exist = false)
 @ExcelProperty("状态")
 private String state;
 @ExcelProperty("库龄")
 @TableField("库龄")
 private String surchargeAgeTier;
 @ExcelProperty("仓储费费率")
 @TableField("仓储费费率")
 private BigDecimal rateSurcharge;
}
