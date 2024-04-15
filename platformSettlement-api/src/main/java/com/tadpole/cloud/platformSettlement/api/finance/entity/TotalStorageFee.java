package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 仓储费合计 数据来源 定期生成
* </p>
*
* @author S20190161
* @since 2022-10-14
*/
@Data
@TableName("TOTAL_STORAGE_FEE")
@ExcelIgnoreUnannotated
public class TotalStorageFee implements Serializable {

 private static final long serialVersionUID = 1L;

 @TableId(value = "ID", type = IdType.AUTO)
 private Long id;

 @TableField("CRAETE_TIME")
 private Date craeteTime;

 @DateTimeFormat("yyyy-MM")
 @ExcelProperty("期间")
 @TableField("DURATION")
 private Date duration;

 @ExcelProperty("账号")
 @TableField("SYS_SHOPS_NAME")
 private String sysShopsName;

 @ExcelProperty("站点")
 @TableField("SYS_SITE")
 private String sysSite;

 @ExcelProperty("币别")
 @TableField("CURRENCY")
 private String currency;

 @ExcelProperty({"账单金额","合计仓储费"})
 @TableField("B_TOTALFEE")
 private BigDecimal billTotalFee;

 @ExcelProperty({"账单金额","月仓储费"})
 @TableField("B_STORAGE_FEE")
 private BigDecimal billStorageFee;

 @ExcelProperty({"账单金额","长期仓储费"})
 @TableField("B_STORAGE_LONG_FEE")
 private BigDecimal billStorageLongFee;

 @ExcelProperty({"账单金额","超库容费"})
 @TableField("B_STORAGE_OVERAGE_FEE")
 private BigDecimal billStorageOverageFee;

 @ExcelProperty({"源报告","合计仓储费"})
 @TableField("TOTAL_FEE")
 private BigDecimal totalFee;

 @ExcelProperty({"源报告","月仓储费"})
 @TableField("STORAGE_FEE")
 private BigDecimal storageFee;

 @ExcelProperty({"源报告","长期仓储费"})
 @TableField("STORAGE_LONG_FEE")
 private BigDecimal storageLongFee;

 @ExcelProperty({"源报告","超库容费"})
 @TableField("STORAGE_OVERAGE_FEE")
 private BigDecimal storageOverageFee;

 @ExcelProperty({"差异","合计"})
 @TableField("D_TOTAL_FEE")
 private BigDecimal diffTotalFee;

 @ExcelProperty({"差异","月仓储费"})
 @TableField("D_STORAGE_FEE")
 private BigDecimal diffStorageFee;

 @ExcelProperty({"差异","长期仓储费"})
 @TableField("D_STORAGELONG_FEE")
 private BigDecimal diffStoragelongFee;

 @ExcelProperty({"差异","超库容费"})
 @TableField("D_STORAGE_OVERAGE_FEE")
 private BigDecimal diffStorageOverageFee;

 @ApiModelProperty("站点维度")
 @TableField(exist = false)
 private String siteDimension;


  @ApiModelProperty("是否下推站内手工分摊,0为下推,1已下推")
  @TableField("IS_PUSH")
  private Integer isPush;



 public String getSiteDimension() {
  return siteDimension;
 }




}
