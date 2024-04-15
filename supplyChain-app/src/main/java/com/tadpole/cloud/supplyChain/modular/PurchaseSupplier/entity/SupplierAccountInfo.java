package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 供应商-供应商信息 实体类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SUPPLIER_ACCOUNT_INFO")
@ExcelIgnoreUnannotated
public class SupplierAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
    @TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private long id;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-供应商编码 */
    @TableField("SYS_SUP_CODE")
    private String sysSupCode;

    /** 基本信息-账户类型 值域{"公户","私户"} */
    @TableField("SYS_ACCOUNT_TYPE")
    private String sysAccountType;

    /** 基本信息-开户行 */
    @TableField("SYS_OPEN_BANK")
    private String sysOpenBank;

    /** 基本信息-银行账号 */
    @TableField("SYS_BANK_ACCOUNT_NUM")
    private String sysBankAccountNum;

    /** 基本信息-账号户名 */
    @TableField("SYS_ACCOUNT_NAME")
    private String sysAccountName;

    /** 基本信息-银行网点 */
    @TableField("SYS_BANK_OUTLET")
    private String sysBankOutlet;

    /** 基本信息-联行号 */
    @TableField("SYS_INTERBANK_NUM")
    private String sysInterbankNum;

    /** 基本信息-SwiftCode */
    @TableField("SYS_SWIFT_CODE")
    private String sysSwiftCode;

    /** 基本信息-币别 值域{"人民币",...} */
    @TableField("SYS_CURRENCY")
    private String sysCurrency;

    /** 基本信息-支付宝账号 */
    @TableField("SYS_ALIPAY_ACCOUNT")
    private String sysAlipayAccount;

    /** 基本信息-收款委托书 */
    @TableField("SYS_COLL_AUTHOR_LETTER")
    private String sysCollAuthorLetter;

    /** 基本信息-创建人 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 基本信息-是否默认 */
    @TableField("IS_DEFAULT")
    private String isDefault;
}