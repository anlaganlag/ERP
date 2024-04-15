package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
* 财务分类表
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_FINANCIAL_CLASSIFICATION")
@ExcelIgnoreUnannotated
public class FinancialClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "ID",type = IdType.AUTO)
    @ExcelProperty(value= "ID")
    private BigDecimal id;

    /** 费用编码 */
    @TableField("COST_CODE")
    @ExcelProperty(value= "费用编码")
    private String costCode;

    /** 费用名称 */
    @TableField("COST_NAME")
    @ExcelProperty(value= "费用名称")
    private String costName;

    /** 费用中文名称 */
    @TableField("COST_NAME_CN")
    @ExcelProperty(value= "费用中文名称")
    private String costNameCn;

    /** 交易类型 */
    @TableField("TRADE_TYPE")
    @ExcelProperty(value= "交易类型")
    private String tradeType;

    /** 金额类型 */
    @TableField("AMOUNT_TYPE")
    @ExcelProperty(value= "金额类型")
    private String amountType;

    /** 费用描述 */
    @TableField("COST_DESCRIPTION")
    @ExcelProperty(value= "费用描述")
    private String costDescription;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 财务分类 */
    @TableField("FINANCIAL_CLASSIFICATION")
    @ExcelProperty(value= "财务分类")
    private String financialClassification;

    /** 科目编码 */
    @TableField("SUBJECT_CODE")
    @ExcelProperty(value= "科目编码")
    private String subjectCode;

    /** 科目名称 */
    @TableField("SUBJECT_NAME")
    @ExcelProperty(value= "科目名称")
    private String subjectName;

    /** 科目分类一 */
    @TableField("SUBJECT_CLASSIFICATION_ONE")
    @ExcelProperty(value= "科目分类一")
    private String subjectClassificationOne;

    /** 科目分类二 */
    @TableField("SUBJECT_CLASSIFICATION_TWO")
    @ExcelProperty(value= "科目分类二")
    private String subjectClassificationTwo;

    /** 使用状态 */
    @TableField("STATUS")
    private int status;

    /** 编辑状态 */
    @TableField("EDIT_STATUS")
    private int editStatus;

    /** 使用状态 */
    @TableField("VERIFY_STATUS")
    private int verifyStatus;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 修改时间 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** 分类类型 */
    @TableField("CLASSIFICATION_TYPE")
    @ExcelProperty(value= "分类类型")
    private String classificationType;

    /** 创建人 */
    @TableField("CHANGE_FORBID_BY")
    private String changeForbidBy;

    /** 修改人 */
    @TableField("CHANGE_FORBID_AT")
    private Date changeForbidAt;

    /** 创建人 */
    @TableField("VERIFY_BY")
    private String verifyBy;

    /** 修改人 */
    @TableField("VERIFY_AT")
    private Date verifyAt;

    /** 创建人 */
    @TableField("EDIT_BY")
    private String editBy;

    /** 修改人 */
    @TableField("EDIT_AT")
    private Date editAt;

    /** 流水码 */
    @TableField("WATER_CODE")
    private int waterCode;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;
}