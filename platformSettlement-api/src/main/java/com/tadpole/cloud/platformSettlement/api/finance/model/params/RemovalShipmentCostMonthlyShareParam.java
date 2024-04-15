package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
* <p>
* 销毁移除成本月分摊请求参数
* </p>
*
* @author ty
* @since 2022-05-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemovalShipmentCostMonthlyShareParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @ApiModelProperty("会计期间")
    private String fiscalPeriod;

    /** 账号 */
    @ApiModelProperty("账号")
    private List<String> sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private List<String> sysSite;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private List<String> department;

    /** Team */
    @ApiModelProperty("Team")
    private List<String> team;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private List<String> productType;

    /** 二级标签 */
    @ApiModelProperty("二级标签")
    private List<String> styleSecondLabel;

    /** 确认状态 0：未确认，1：已确认，-1：已作废 */
    @ApiModelProperty("确认状态 0：未确认，1：已确认，2：已作废")
    private List<String> confirmStatus;

    @ApiModelProperty("订单类型")
    private List<String> orderType;

    @ApiModelProperty("摊销期")
    private String shareNum;

    @ApiModelProperty("Order Id")
    private String orderId;

    @ApiModelProperty("开始期间 yyyy-mm")
    private String startDateFormat;

    @ApiModelProperty("结束期间 yyyy-mm")
    private String endDateFormat;

    @ApiModelProperty("刷listing异常 0：否，1：是")
    private String isListingError;

    @ApiModelProperty("刷单位成本 0：否，1：是")
    private String refreshUnitPrice;

    @ApiModelProperty(value = "sku", hidden = true)
    private String sku;

    @ApiModelProperty(value = "sku集合")
    private List<String> skus;

    @ApiModelProperty(value = "物料编码" , hidden = true)
    private String materialCode;

    @ApiModelProperty(value = "操作人" , hidden = true)
    private String operator;
}