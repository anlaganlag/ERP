package com.tadpole.cloud.platformSettlement.api.finance.model.params;



import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;


import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
* <p>
    * Amazon广告费用账单
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
@Data
@TableName("AMAZON_ADS_INVOICES")
public class AmazonAdsInvoicesParam extends BaseRequest implements Serializable, BaseValidatingParam {


    @ApiModelProperty("")
    private List<String> accountName;

    @ApiModelProperty("")
    private List<String>  countryCode;

    private String startDate;
    private String endDate;

}
