package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础信息-税率表
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("BASE_TAX_RATE")
public class BaseTaxRateParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    private BigDecimal id;

    /** 站点 */
    @ApiModelProperty("SITE")
    private List<String> sites;

    @ApiModelProperty("SITE")
    private String site;

    /** 税率 */
    @ApiModelProperty("TAX_RATE")
    private BigDecimal taxRate;

    /** 生效日期 */
    @ApiModelProperty("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 失效日期 */
    @ApiModelProperty("EXPIRATION_DATE")
    private Date expirationDate;

    /** 状态--默认值：1,【0：失效、1：生效】 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;
}