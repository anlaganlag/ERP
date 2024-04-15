package com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询亚马逊源表——Settlement源文件下载请求参数
 * @date: 2022/4/29
 */
@Data
@ApiModel(value="查询亚马逊源表——Settlement源文件下载请求参数")
public class SettlementFileReq {

    @NotNull
    @ApiModelProperty("源文件ID，从文件列表接口获取")
    private Long id;

    @ApiModelProperty("文件内容编码方式，可选值：raw、base64，非必填")
    private String file_data_encode;
}
