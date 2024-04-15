package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 供应商-供应商信息 入参类
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
public class SupplierAccountInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
    @ApiModelProperty("系统编号")
    private long id;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-供应商编码 */
    @ApiModelProperty("系统信息-供应商编码")
    private String sysSupCode;

    /** 基本信息-账户类型 值域{"公户","私户"} */
    @ApiModelProperty("基本信息-账户类型 值域{公户,私户}")
    private String sysAccountType;

    /** 基本信息-开户行 */
    @ApiModelProperty("基本信息-开户行")
    private String sysOpenBank;

    /** 基本信息-银行账号 */
    @ApiModelProperty("基本信息-银行账号")
    private String sysBankAccountNum;

    /** 基本信息-账号户名 */
    @ApiModelProperty("基本信息-账号户名")
    private String sysAccountName;

    /** 基本信息-银行网点 */
    @ApiModelProperty("基本信息-银行网点")
    private String sysBankOutlet;

    /** 基本信息-联行号 */
    @ApiModelProperty("基本信息-联行号")
    private String sysInterbankNum;

    /** 基本信息-SwiftCode */
    @ApiModelProperty("基本信息-SwiftCode")
    private String sysSwiftCode;

    /** 基本信息-币别 值域{"人民币",...} */
    @ApiModelProperty("基本信息-币别 值域{人民币,...}")
    private String sysCurrency;

    /** 基本信息-支付宝账号 */
    @ApiModelProperty("基本信息-支付宝账号")
    private String sysAlipayAccount;

    /** 基本信息-收款委托书 */
    @ApiModelProperty("基本信息-收款委托书")
    private String sysCollAuthorLetter;

}
