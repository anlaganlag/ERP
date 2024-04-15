package com.tadpole.cloud.platformSettlement.modular.vat.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 税金测算Sales明细
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("VAT_SALES_DETAIL")
public class VatSalesDetailResult implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;

 /** 汇率id--自增 */
 @TableId(value = "ID", type = IdType.AUTO)
 private BigDecimal id;

 /** 期间 */
 @ApiModelProperty("期间")
 @ExcelProperty(value ="期间")
 private String activityPeriod;

 /** 账号 */
 @ApiModelProperty("账号")
 @ExcelProperty(value ="账号")
 private String sysShopsName;

 /** 站点 */
 @ApiModelProperty("站点")
 @ExcelProperty(value ="站点")
 private String sysSite;

 /** 币种 */
 @ApiModelProperty("币种")
 @ExcelProperty(value ="币种")
 private String transactionCurrencyCode;

 /** 发货国 */
 @ApiModelProperty("发货国")
 @ExcelProperty(value ="Seller-发货国")
 private BigDecimal saleDepartCountry;

 /** 目的国 */
 @ApiModelProperty("目的国")
 @ExcelProperty(value ="Seller-目的国")
 private BigDecimal saleArrivalCountry;

 /** Maketplace */
 @ApiModelProperty("Maketplace")
 @ExcelProperty(value ="Maketplace")
 private BigDecimal maketplace;

 /** seller-发货国B2B */
 @ApiModelProperty("seller-发货国B2B")
 @ExcelProperty(value ="Seller-发货国")
 private BigDecimal saleDepartCountryB;

 /** VAT明细生成状态（默认0：未生成，1：已生成） */
 @ApiModelProperty("VAT明细生成状态（默认0：未生成，1：已生成）")
 @ExcelProperty(value ="VAT明细")
 private String generateStatus;

 /** 创建人 */
 @ApiModelProperty("创建人")
 private String createPerson;

 /** 创建时间--默认值：getdate,首次创建 */
 @ApiModelProperty("创建时间")
 private Date createTime;

 /** 最后更新时间--默认值：getdate */
 @ApiModelProperty("最后更新时间")
 private Date updateTime;

 /** 最后更新人名称--默认值：当前修改人名称 */
 @ApiModelProperty("最后更新人名称")
 private String updatePerson;

}