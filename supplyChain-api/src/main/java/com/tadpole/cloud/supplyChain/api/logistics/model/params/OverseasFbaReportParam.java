package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author: ty
 * @description: 亚马逊发海外仓报表入参类
 * @date: 2022/10/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasFbaReportParam {

    private static final long serialVersionUID = 1L;

    /** 平台 */
    @ApiModelProperty("平台集合")
    private List<String> platforms;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 业务类型 */
    @ApiModelProperty("业务类型集合")
    private List<String> operateTypes;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓集合")
    private List<String> inWarehouseNames;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物流编码 */
    @ApiModelProperty("物流编码")
    private String materialCode;

    /** 建单开始时间 */
    @ApiModelProperty("建单开始时间")
    private String shipmentStartDate;

    /** 建单结束时间 */
    @ApiModelProperty("建单结束时间")
    private String shipmentEndDate;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    private String confirmStartDate;

    /** 签收结束时间 */
    @ApiModelProperty("签收结束时间")
    private String confirmEndDate;
}
