package com.tadpole.cloud.platformSettlement.api.finance.entity;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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
* 销毁移除费用统计
* </p>
*
* @author S20190161
* @since 2022-10-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TOTAL_DESTROY_FEE")
@ExcelIgnoreUnannotated
public class TotalDestroyFee implements Serializable {

 private static final long serialVersionUID = 1L;

 @TableId(value = "ID", type = IdType.AUTO)
 private Long id;
 @TableField("SETTLEMENT_ID")
 private BigDecimal settlementId;

 @TableField(exist = false)
 @ExcelProperty("SKU是否正常")
 private String normal;

 @TableField(exist = false)
 @ExcelProperty("数据分类")
 private String dataType;

 @TableField(exist = false)
 @ExcelProperty("OrderId分类排序")
 private Integer lev;

 @DateTimeFormat("yyyy-MM")
 @ExcelProperty("会计期间")
 @TableField("DURATION")
 private Date duration;

 @ExcelProperty("orderId")
 @TableField("ORDER_ID")
 private String orderId;

 @ExcelProperty("账号")
 @TableField("SYS_SHOPS_NAME")
 private String sysShopsName;

 @ExcelProperty("站点")
 @TableField("SYS_SITE")
 private String sysSite;

 @ExcelProperty("币别")
 @TableField("CURRENCY")
 private String currency;

 @ExcelProperty("sku")
 @TableField("SKU")
 private String sku;

 @ExcelProperty("事业部")
 @TableField("DEPARTMENT")
 private String department;

 @ExcelProperty("team")
 @TableField("TEAM")
 private String team;

 @ExcelProperty("物料")
 @TableField("MATERIAL_CODE")
 private String materialCode;

 @ExcelProperty("类目")
 @TableField("CATEGORY")
 private String category;

 @ExcelProperty("运营大类")
 @TableField("PRODUCT_TYPE")
 private String productType;


 @ExcelProperty("销毁费")
 @TableField("DISPOSAL_FEE")
 private BigDecimal disposalFee;

 @ExcelProperty("移除费")
 @TableField("RETURN_FEE")
 private BigDecimal returnFee;

 @TableField(exist = false)
 @ExcelProperty("状态")
 private String state;

 //1.未确认2.手动确认3.自动确认4.已下推
 @TableField("STATUS")
 private Integer status;

 @TableField("SOURCE")
 private Integer source;

 @TableField("TYPE")
 private Integer type;

 @TableField("CREATE_TIME")
 private Date createTime;

 @TableField("CREATE_USER")
 private String createUser;

 @DateTimeFormat("yyyy-MM-dd")
 @ExcelProperty("修改时间")
 @TableField("UPDATE_TIME")
 private Date updateTime;

 @TableField("UPDATE_USER")
 private String updateUser;

 @TableField("REMARK")
 private String remark;

 @TableField("FNSKU")
 private String fnsku;


 @TableField(exist = false)
 private String siteDimension;

 public String getSiteDimension() {
  return DateUtil.format(duration, "yyyy-MM") + sysShopsName + sysSite;
 }

 @TableField("SETTLE_PERIOD")
 private String settlePeriod;

}