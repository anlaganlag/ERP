package com.tadpole.cloud.operationManagement.api.brand.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

/**
* <p>
* 品牌商标注册进度表
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@Data
@TableName("TB_BRAND_TRADEMARK_REGIS_PROGRESS")
@ExcelIgnoreUnannotated
public class TbBrandTrademarkRegisProgress implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌商标注册表主键 */
    @TableField("REGIS_ID")
    private Long regisId;

    /** 申请费用(含税/元) */
    //@TableField("APPLY_OUTLAY_PLUS_TAX")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal applyOutlayPlusTax;

    /** 第一次付款日期 */
 @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
   //@TableField("PAYMENT_DATE_FIRST")
    private Date paymentDateFirst;

    /** 实付金额 */
    //@TableField("PAYMENT_AMOUNT")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DECIMAL)
    private BigDecimal paymentAmount;

    /** 发票号码 */
    //@TableField("INVOICE_NUMBER")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String invoiceNumber;

    /** 发票文件--支持的文件格{pdf;xls,txt,doc} */
    //@TableField("INVOICE_FILES")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String invoiceFiles;

    /** 合同签订日期 */
    //@TableField("CONTRACT_SIGNING_DATE")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date contractSigningDate;

    /** 合同文件--支持的文件格{pdf;xls,txt,doc} */
    //@TableField("CONTRACT_FILES")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String contractFiles;

    /** 申请日期 */
    //@TableField("APPLY_DATE")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date applyDate;

    /** 申请号 */
    //@TableField("APPLY_NO")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String applyNo;

    /** 获证日期 */
    //@TableField("CERTIFICATE_DATE")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date certificateDate;

    /** 注册号 */
    //@TableField("REGISTER_NUMBER")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String registerNumber;

    /** 商标有效期开始 */
    //@TableField("TRADEMARK_VALIDITY_TERM_START")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date trademarkValidityTermStart;

    /** 商标有效期结束 */
    //@TableField("TRADEMARK_VALIDITY_TERM_END")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date trademarkValidityTermEnd;

    /** 商标状态：0.申请中;1.已受理;2.审查中;3.公告中;4.已下证;5.异常归档 */
    @TableField("TRADEMARK_STATUS")
    private Integer trademarkStatus;

    //@TableField("CREATE_TIME")
    @TableField(updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date createTime;

    @TableField("CREATE_NAME")
    private String createName;


 @TableField("UPDATE_TIME")
 private Date updateTime;

 @TableField("UPDATE_NAME")
 private String  updateName;


 @TableField("REMARK")
 private String remark;


}
