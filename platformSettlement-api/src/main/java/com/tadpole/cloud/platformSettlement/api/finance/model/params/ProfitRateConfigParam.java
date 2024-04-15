package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 利润率参数管理请求参数
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitRateConfigParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事业部")
    private String department;

    @ApiModelProperty(value = "Team")
    private String team;

    @ApiModelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty(value = "事业部集合")
    private List<String> departments;

    @ApiModelProperty(value = "Team集合")
    private List<String> teams;

    @ApiModelProperty(value = "运营大类集合")
    private List<String> productTypes;
}