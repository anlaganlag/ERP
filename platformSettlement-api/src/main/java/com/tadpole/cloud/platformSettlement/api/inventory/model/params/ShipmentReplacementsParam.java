package com.tadpole.cloud.platformSettlement.api.inventory.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
    *
    * </p>
*
* @author S20190161
* @since 2023-06-08
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SHIPMENT_REPLACEMENTS")
public class ShipmentReplacementsParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("")
    private String sku;

    private List<String> skus;

    @ApiModelProperty("")
    private String asin;


    @ApiModelProperty("换货原因 0 其他；1 丢失；2 有缺陷；3 配送损坏；4 配送错误；5 配送丢失；6 发货丢失；7 买错商品；8 配送地址错误；9 配送错误（地址正确）；10DC/FC处理中损坏；11 签收未收到；12 政策不允许/买家原因")
    private List<Integer> replacementReasonCode;

    @ApiModelProperty("换货订单号")
    private String replacementAmazonOrderId;

    @ApiModelProperty("原始订单号")
    private String originalAmazonOrderId;

    @ApiModelProperty("站点")
    private List<String> sysSite;

    @ApiModelProperty("账号")
    private List<String> sysShopsName;

    @ApiModelProperty("会计期间")
    private String startDur;
    @ApiModelProperty("会计期间")
    private String endDur;

    @ApiModelProperty("来源0默认所有数据 1AZ费用索赔")
    private int source;

}
