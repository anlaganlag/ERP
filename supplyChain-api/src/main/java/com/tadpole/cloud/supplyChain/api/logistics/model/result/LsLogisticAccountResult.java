package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 物流信息下拉出参类
 * @date: 2023/11/16
 */
@Data
public class LsLogisticAccountResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ApiModelProperty("物流账号")
    private String lcCode;

    @ApiModelProperty("国家分区站点")
    private String countryCode;

    @ApiModelProperty("国家分区号")
    private String lspNum;

    @ApiModelProperty("收货分区")
    private String countryAreaName;
}
