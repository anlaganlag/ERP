package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author: ty
 * @description: 订单源报告数据监控请求参数
 * @date: 2022/5/6
 */
@Data
@ApiModel
public class OrderDataSourceMonitorParam extends BaseRequest implements Serializable, BaseValidatingParam {

    @ApiModelProperty(value = "账号")
    private String sysShopsName;

    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty(value = "销售日期开始时间")
    private String purchaseDateStart;

    @ApiModelProperty(value = "销售日期结束时间")
    private String purchaseDateEnd;
}
