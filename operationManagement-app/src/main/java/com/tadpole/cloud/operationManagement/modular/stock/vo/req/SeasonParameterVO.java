package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;


/**
 * 季节系数查询参数
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeasonParameterVO extends BaseRequest {

    /** 平台 */
    @ApiModelProperty("平台")
    private List<String> platformName;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private List<String> department;

    /** Team */
    @ApiModelProperty("Team")
    private List<String> team;

    /** 区域 */
    @ApiModelProperty("区域")
    private List<String> area;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private List<String> productType;

    /** 适用品牌 */
    @ApiModelProperty("适用品牌")
    private List<String> brand;

    /** 型号 */
    @ApiModelProperty("型号")
    private List<String> model;

    /** 节日标签 */
    @ApiModelProperty("节日标签")
    private List<String> festivalLabel;

    /** 季节标签 */
    @ApiModelProperty("季节标签")
    private List<String> season;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private List<String> productName;



}
