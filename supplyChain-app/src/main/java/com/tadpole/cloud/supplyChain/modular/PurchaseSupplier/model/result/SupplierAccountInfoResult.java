package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 供应商-供应商信息 出参类
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
@ExcelIgnoreUnannotated
@TableName("SUPPLIER_ACCOUNT_INFO")
public class SupplierAccountInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("系统信息-系统编号")
    private long id;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-供应商编码")
    private String sysSupCode;


    @ApiModelProperty("基本信息-账户类型 值域{公户,私户}")
    private String sysAccountType;


    @ApiModelProperty("基本信息-开户行")
    private String sysOpenBank;


    @ApiModelProperty("基本信息-银行账号")
    private String sysBankAccountNum;


    @ApiModelProperty("基本信息-账号户名")
    private String sysAccountName;


    @ApiModelProperty("基本信息-银行网点")
    private String sysBankOutlet;


    @ApiModelProperty("基本信息-联行号")
    private String sysInterbankNum;


    @ApiModelProperty("基本信息-SwiftCode")
    private String sysSwiftCode;


    @ApiModelProperty("基本信息-币别 值域{人民币,...}")
    private String sysCurrency;


    @ApiModelProperty("基本信息-支付宝账号")
    private String sysAlipayAccount;


    @ApiModelProperty("基本信息-收款委托书")
    private String sysCollAuthorLetter;

}
