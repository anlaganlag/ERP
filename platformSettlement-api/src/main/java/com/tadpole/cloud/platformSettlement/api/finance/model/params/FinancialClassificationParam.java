package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

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
public class FinancialClassificationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 费用编码 */
    @ApiModelProperty("COST_CODE")
    private String costCode;

    /** 费用名称 */
    @ApiModelProperty("COST_NAME")
    private String costName;

    /** 费用中文名称 */
    @ApiModelProperty("COST_NAME_CN")
    private String costNameCn;

    /** 交易类型 */
    @ApiModelProperty("TRADE_TYPE")
    private String tradeType;

    /** 金额类型 */
    @ApiModelProperty("AMOUNT_TYPE")
    private String amountType;

    /** 费用描述 */
    @ApiModelProperty("COST_DESCRIPTION")
    private String costDescription;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 财务分类 */
    @ApiModelProperty("FINANCIAL_CLASSIFICATION")
    private String financialClassification;

    /** 科目编码 */
    @ApiModelProperty("SUBJECT_CODE")
    private String subjectCode;

    /** 科目名称 */
    @ApiModelProperty("SUBJECT_NAME")
    private String subjectName;

    /** 科目分类一 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_ONE")
    private String subjectClassificationOne;

    /** 科目分类二 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_TWO")
    private String subjectClassificationTwo;

    /** 使用状态 */
    @ApiModelProperty("STATUS")
    private Integer status;

    /** 编辑状态 */
    @ApiModelProperty("EDIT_STATUS")
    private Integer editStatus;

    /** 使用状态 */
    @ApiModelProperty("VERIFY_STATUS")
    private Integer verifyStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 分类类型 */
    @ApiModelProperty("CLASSIFICATION_TYPE")
    private String classificationType;

    /** 创建人 */
    @ApiModelProperty("CHANGE_FORBID_BY")
    private String changeForbidBy;

    /** 创建人 */
    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    /** 创建人 */
    @ApiModelProperty("EDIT_BY")
    private String editBy;
}