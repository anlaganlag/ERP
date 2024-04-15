package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
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
 * 物流单对账明细 入参类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_NO_CHECK_DETAIL")
public class LsLogisticsNoCheckDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @ExcelProperty(value ="发货批次号*")
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /** 物流费 */
    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    /** 物流费币制 */
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 报关费 */
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 燃油附加费 */
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 产品附加费（附加费及杂费） */
    @ApiModelProperty("产品附加费（附加费及杂费）")
    private BigDecimal othersFee;

    /** 产品附加费（附加费及杂费）备注 */
    @ApiModelProperty("产品附加费（附加费及杂费）备注")
    private String othersFeeRemark;

    /** DUTY/201 */
    @ApiModelProperty("DUTY/201")
    private BigDecimal taxFee;

    /** 合计（CNY） */
    @ApiModelProperty("合计（CNY）")
    private BigDecimal totalFee;

    /** DTP */
    @ApiModelProperty("DTP")
    private BigDecimal dtp;

    /** 燃油费率（%） */
    @ExcelProperty(value ="燃油费率（%）")
    @ApiModelProperty("燃油费率（%）")
    private BigDecimal oilFeePercent;

    /** 杂费 */
    @ApiModelProperty("杂费")
    private BigDecimal sundryFee;

    /** 物流对账单号 */
    @ExcelProperty(value ="物流对账单号*")
    @ApiModelProperty("物流对账单号")
    private String logisticsCheckOrder;

    /** 流转税（VAT/TAX/205） */
    @ApiModelProperty("流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    /** 税费币制 */
    @ApiModelProperty("税费币制")
    private String taxCurrency;

    /** 税号 */
    @ExcelProperty(value ="税号")
    @ApiModelProperty("税号")
    private String taxOrder;

    /** C88单号 */
    @ExcelProperty(value ="C88单号")
    @ApiModelProperty("C88单号")
    private String c88;

    /** C88备注 */
    @ExcelProperty(value ="C88备注")
    @ApiModelProperty("C88备注")
    private String c88Remark;

    /** VAT原币金额 */
    @ExcelProperty(value ="VAT原币金额")
    @ApiModelProperty("VAT原币金额")
    private BigDecimal vat;

    /** 税费发票号码 */
    @ExcelProperty(value ="税费发票号码")
    @ApiModelProperty("税费发票号码")
    private String taxInvoiceOrder;

    /** 物流费ERP申请日期 */
    @ApiModelProperty("物流费ERP申请日期")
    private Date logisticsErpDate;

    /** 物流费单据编号 */
    @ApiModelProperty("物流费单据编号")
    private String logisticsBillOrder;

    /** 税费ERP申请日期 */
    @ApiModelProperty("税费ERP申请日期")
    private Date taxErpDate;

    /** 税费单据编号 */
    @ApiModelProperty("税费单据编号")
    private String taxBillOrder;

    /** 序号 */
    @ApiModelProperty("序号")
    private Integer orderNum;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 备注 */
    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    /** 导入异常信息备注 */
    @ExcelProperty(value ="导入异常信息备注")
    @ApiModelProperty("导入异常信息备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 11)
    private String uploadRemark;

}
