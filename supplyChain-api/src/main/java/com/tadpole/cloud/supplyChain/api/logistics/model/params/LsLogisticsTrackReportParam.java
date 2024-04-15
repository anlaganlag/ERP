package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ty
 * @description: 物流跟踪报表入参类
 * @date: 2023/12/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LsLogisticsTrackReportParam extends BaseRequest implements Serializable, BaseValidatingParam {

    @ApiModelProperty("平台集合")
    private List<String> platformList;

    @ApiModelProperty("账号集合")
    private List<String> sysShopsNameList;

    @ApiModelProperty("站点集合")
    private List<String> sysSiteList;

    @ApiModelProperty("物流单号集合")
    private List<String> logisticsNoList;

    @ApiModelProperty("ShipmentID集合")
    private List<String> shipmentIdList;

    @ApiModelProperty("发货方式1集合")
    private List<String> freightCompanyList;

    @ApiModelProperty("运输方式集合")
    private List<String> transportTypeList;

    @ApiModelProperty("发货仓集合")
    private List<String> shipmentWarehouseList;

    @ApiModelProperty("发货批次号集合")
    private List<String> shipmentNumList;

    /** 发货开始日期 */
    @ApiModelProperty("发货开始日期")
    private String shipmentStartDate;

    /** 发货结束日期 */
    @ApiModelProperty("发货结束日期")
    private String shipmentEndDate;

}
