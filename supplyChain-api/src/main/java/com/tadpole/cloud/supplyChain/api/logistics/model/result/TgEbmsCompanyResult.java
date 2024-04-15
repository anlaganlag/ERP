package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: EBMS实体公司
 * @date: 2023/5/25
 */
@Data
public class TgEbmsCompanyResult {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公司名称（中文）")
    private String companyNameCn;

    @ApiModelProperty("公司名称（英文）")
    private String companyNameEn;

    @ApiModelProperty("地址（中文）")
    private String addrCn;

    @ApiModelProperty("地址（英文）")
    private String addrEn;

    @ApiModelProperty("海关编码")
    private String customsNum;

    @ApiModelProperty("统一信用代码")
    private String companyNum;

    @ApiModelProperty("公司电话")
    private String comTel;
}
