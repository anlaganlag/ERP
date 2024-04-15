package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* <p>
* 月储存费用
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MONTHLY_STORAGE_FEES")
public class MonthlyStorageFeesParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** Asin */
    @ApiModelProperty("ASIN")
    private String asin;

    /** Fnsku */
    @ApiModelProperty("FNSKU")
    private String fnsku;

    /** 费用月份 */
    @ApiModelProperty("MONTH_OF_CHARGE")
    private String monthOfCharge;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("STATUS")
    private Integer status;

    @ApiModelProperty("物料")
    private String materialCode;

    private List<String> sysSites;

    private List<String> sysShopsNames;

    private String sysSite;

    private String sysShopsName;

    @ApiModelProperty("会计期间")
    private String startDur;

    @ApiModelProperty("会计期间")
    private String endDur;


    @ApiModelProperty("事业部")
    private String department;

    @ApiModelProperty("Team")
    private String team;

    @ApiModelProperty("Team")
    private String updateTime;


    private List<String> emailList;


}
