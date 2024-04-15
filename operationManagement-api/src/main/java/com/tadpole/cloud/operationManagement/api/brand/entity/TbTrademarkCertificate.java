package com.tadpole.cloud.operationManagement.api.brand.entity;

import java.math.BigDecimal;

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
* 商标证书管理表
* </p>
*
* @author S20190161
* @since 2023-10-27
*/
@Data
@TableName("TB_TRADEMARK_CERTIFICATE")
@ExcelIgnoreUnannotated
public class TbTrademarkCertificate implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 商标名称 */
    @TableField("TRADE_NAME")
    private String tradeName;

    /** 商标类型：0.文字商标;1.图形商标 */
    @TableField("TRADEMARK_TYPE")
    private Integer trademarkType;

    /** 注册号 */
    @TableField("REGISTER_NUMBER")
    private String registerNumber;

    /** 注册国家 */
    @TableField("REGISTER_COUNTRY")
    private String registerCountry;

    /** 商标大类--取值于字典名称-【商标大类】 */
    @TableField("TRADEMARK_CATEGORY")
    private String trademarkCategory;

    /** 商标小类 */
    @TableField("TRADEMARK_SUB_CLASS")
    private String trademarkSubClass;

    /** 商标权人 -注册公司 */
    @TableField("TRADEMARK_OWNER")
    private String trademarkOwner;

    /** 证书文件 */
    @TableField("CATEGORY_FILE")
    private String categoryFile;

    /** 证书文件名称 */
    @TableField("CATEGORY_FILE_NAME")
    private String categoryFileName;

    /** 有效期start */
    @TableField("TRADEMARK_VALIDITY_TERM_START")
    private Date trademarkValidityTermStart;

    /** 有效期end */
    @TableField("TRADEMARK_VALIDITY_TERM_END")
    private Date trademarkValidityTermEnd;

    /** 保管部门 */
    @TableField("CUSTODY_DEPARTMENT")
    private String custodyDepartment;

    /** 资产编码 */
    @TableField("ASSETS_NO")
    private String assetsNo;

    /** 交接日期 */
    @TableField("HANDOVER_DATE")
    private Date handoverDate;

    /** 交接状态:0.待交接;1.待确认 */
    @TableField("HANDOVER_STATUS")
    private Integer handoverStatus;

    /** 提醒倒计时 */
    @TableField("REMINDER_COUNTDOWN")
    private BigDecimal reminderCountdown;

    /** 补贴-是否可申请资助 */
    @TableField("SUBSIDY_APPLY")
    private Integer subsidyApply;

    /** 补贴-资产编号 */
    @TableField("SUBSIDY_ASSET_NUMBER")
    private String subsidyAssetNumber;

    /** 补贴-资助资金 */
    @TableField("SUBSIDY_SUPPORT_AMOUNT")
    private BigDecimal subsidySupportAmount;

    /** 补贴-到款日期 */
    @TableField("SUBSIDY_DATE_PAYMENT")
    private Date subsidyDatePayment;

    /** 补贴-资助情况 */
    @TableField("SUBSIDY_FINANCING")
    private String subsidyFinancing;

    /** 补贴-办理日期 */
    @TableField("SUBSIDY_HANDLE_DATE")
    private Date subsidyHandleDate;

    /** 补贴-办理人名称 */
    @TableField("SUBSIDY_HANDLE_PER_NAME")
    private String subsidyHandlePerName;

    /** 延期-商标有效期开始 */
    @TableField("RENEWAL_VALIDITY_TRADEMARK_START")
    private Date renewalValidityTrademarkStart;

    /** 延期-商标有效期结束 */
    @TableField("RENEWAL_VALIDITY_TRADEMARK_END")
    private Date renewalValidityTrademarkEnd;

    /** 延期-续展文件 */
    @TableField("RENEWAL_FILE")
    private String renewalFile;

    /** 延期-续展费用 */
    @TableField("RENEWAL_AMOUNT")
    private BigDecimal renewalAmount;

    /** 备注说明 */
    @TableField("REMARK")
    private String remark;

    /** 创建人编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    @TableField("CREATE_NAME")
    private String createName;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("UPDATE_NAME")
    private String updateName;

    @TableField("UPDATE_TIME")
    private Date updateTime;

   @TableField("RENEWALS")
   private String renewals;

}
