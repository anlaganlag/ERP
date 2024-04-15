package com.tadpole.cloud.platformSettlement.modular.vat.entity;

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
 * VAT核对表
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
@TableName("VAT_CHECK")
@ExcelIgnoreUnannotated
public class VatCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @TableField("ACTIVITY_PERIOD")
    @ExcelProperty(value ="期间")
    private String activityPeriod;

    /** 账号 */
    @TableField("EBMS_SHOPS_NAME")
    @ExcelProperty(value ="账号")
    private String ebmsShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** 币种 */
    @TableField("TRANSACTION_CURRENCY_CODE")
    @ExcelProperty(value ="币种")
    private String transactionCurrencyCode;

    /** 销售VAT-卖家 */
    @TableField("SALES_VAT_SELLER")
    @ExcelProperty(value ="销售VAT-卖家")
    private BigDecimal salesVatSeller;

    /** 销售VAT-代理/会计师 */
    @TableField("SALES_VAT_AGENT")
    @ExcelProperty(value ="销售VAT-代理/会计师")
    private BigDecimal salesVatAgent;

    /** 销售VAT差异 */
    @TableField("SALES_VAT_DIFFERENCE")
    @ExcelProperty(value ="销售VAT差异")
    private BigDecimal salesVatDifference;

    /** B2B-卖家 */
    @TableField("BTB_SELLER")
    @ExcelProperty(value ="B2B-卖家")
    private BigDecimal btbSeller;

    /** B2B-代理/会计师 */
    @TableField("BTB_AGENT")
    @ExcelProperty(value ="B2B-代理/会计师")
    private BigDecimal btbAgent;

    /** B2B差异 */
    @TableField("BTB_DIFFERENCE")
    @ExcelProperty(value ="B2B差异")
    private BigDecimal btbDifference;

    /** C88/C79 */
    @TableField("CEE")
    @ExcelProperty(value ="C88/C79")
    private BigDecimal cee;

    /** 结余/其它 */
    @TableField("BABLANCE")
    @ExcelProperty(value ="结余/其它")
    private BigDecimal bablance;

    /** 实际缴纳/退税 */
    @TableField("ACTUAL_PAYMENT")
    @ExcelProperty(value ="实际缴纳/退税")
    private BigDecimal actualPayment;

    /** 备注1 */
    @TableField("REMARK_ONE")
    @ExcelProperty(value ="备注1")
    private String remarkOne;

    /** 备注2 */
    @TableField("REMARK_TWO")
    @ExcelProperty(value ="备注2")
    private String remarkTwo;

    /** 创建时间--默认值：getdate,首次创建 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人编号--默认值：创建人编号，首次创建 */
    @TableField("CREATE_PERSON_NO")
    private String createPersonNo;

    /** 创建人名称--默认值：创建人名称，首次创建 */
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    /** 最后更新时间--默认值：getdate */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人编号--默认值：当前修改人编号 */
    @TableField("UPDATE_PERSON_NO")
    private String updatePersonNo;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;

}