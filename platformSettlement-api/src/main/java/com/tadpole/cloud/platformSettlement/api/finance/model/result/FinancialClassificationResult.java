package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* <p>
* 财务分类表
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class FinancialClassificationResult implements Serializable {

    private static final long serialVersionUID = 1L;

     /** id */
    @ApiModelProperty("ID")
    @ExcelProperty(value= "ID")
    private BigDecimal id;

    /** 费用编码 */
    @ApiModelProperty("COST_CODE")
    @ExcelProperty(value= "费用编码")
    private String costCode;

    /** 费用名称 */
    @ApiModelProperty("COST_NAME")
    @ExcelProperty(value= "费用名称")
    private String costName;

    /** 费用中文名称 */
    @ApiModelProperty("COST_NAME_CN")
    @ExcelProperty(value= "费用中文名称")
    private String costNameCn;

    /** 交易类型 */
    @ApiModelProperty("TRADE_TYPE")
    @ExcelProperty(value= "交易类型")
    private String tradeType;

    /** 金额类型 */
    @ApiModelProperty("AMOUNT_TYPE")
    @ExcelProperty(value= "金额类型")
    private String amountType;

    /** 费用描述 */
    @ApiModelProperty("COST_DESCRIPTION")
    @ExcelProperty(value= "费用描述")
    private String costDescription;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 财务分类 */
    @ApiModelProperty("FINANCIAL_CLASSIFICATION")
    @ExcelProperty(value= "财务分类")
    private String financialClassification;

    /** 科目编码 */
    @ApiModelProperty("SUBJECT_CODE")
    @ExcelProperty(value= "科目编码")
    private String subjectCode;

    /** 科目名称 */
    @ApiModelProperty("SUBJECT_NAME")
    @ExcelProperty(value= "科目名称")
    private String subjectName;

    /** 科目分类一 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_ONE")
    @ExcelProperty(value= "科目分类一")
    private String subjectClassificationOne;

    /** 科目分类二 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_TWO")
    @ExcelProperty(value= "科目分类二")
    private String subjectClassificationTwo;

    /** 流水码 */
    @TableField("WATER_CODE")
    @ExcelProperty(value= "流水号")
    private int waterCode;

    /** 使用状态 */
    @ApiModelProperty("STATUS")
    @ExcelProperty(value= "使用状态")
    private String status;

    /** 编辑状态 */
    @ApiModelProperty("EDIT_STATUS")
    @ExcelProperty(value= "编辑状态")
    private String editStatus;

    /** 审核状态 */
    @ApiModelProperty("VERIFY_STATUS")
    @ExcelProperty(value= "审核状态")
    private String verifyStatus;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 分类类型 */
    @ApiModelProperty("CLASSIFICATION_TYPE")
    @ExcelProperty(value= "分类类型")
    private String classificationType;

    /** 创建人 */
    @ApiModelProperty("CHANGE_FORBID_BY")
    private String changeForbidBy;

    /** 修改人 */
    @ApiModelProperty("CHANGE_FORBID_AT")
    private String changeForbidAt;

    /** 创建人 */
    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    /** 修改人 */
    @ApiModelProperty("VERIFY_AT")
    private String verifyAt;

    /** 创建人 */
    @ApiModelProperty("EDIT_BY")
    private String editBy;

    /** 修改人 */
    @ApiModelProperty("EDIT_AT")
    private String editAt;

}