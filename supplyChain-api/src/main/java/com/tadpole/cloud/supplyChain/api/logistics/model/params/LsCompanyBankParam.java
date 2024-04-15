package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 物流商银行信息
 * @date: 2023/12/4
 */
@Data
public class LsCompanyBankParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lsCompanyCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lsCompanyName;

    /** 银行账号 */
    @ApiModelProperty("银行账号")
    private String bankAccNo;

    /** 银行账户名称 */
    @ApiModelProperty("银行账户名称")
    private String bankAccName;

    /** 银行开户行 */
    @ApiModelProperty("银行开户行")
    private String bankName;

}
