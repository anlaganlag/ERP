package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

/**
 * @author: ty
 * @description: AZ销售订单预结算入参
 * @date: 2022/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPreSettlementParam extends BaseRequest {
    /** 销售日期开始时间 */
    @ApiModelProperty("销售日期开始时间")
    private String purchaseDateStart;

    /** 销售日期结束时间 */
    @ApiModelProperty("销售日期结束时间")
    private String purchaseDateEnd;

    /** 账号 */
    @ApiModelProperty("账号")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点")
    private List<String> sysSites;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private List<String> departments;

    /** Team */
    @ApiModelProperty("Team")
    private List<String> teams;

    /** sku */
    @ApiModelProperty("sku，用英文逗号隔开")
    private List<String> skus;

    @ApiModelProperty("是否异常 0：否，1：是")
    private String isError;
}
