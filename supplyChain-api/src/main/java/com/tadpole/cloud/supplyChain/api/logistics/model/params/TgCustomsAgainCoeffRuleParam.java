package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 清关二次折算 入参类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_CUSTOMS_AGAIN_COEFF_RULE")
public class TgCustomsAgainCoeffRuleParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家编码 */
    @ApiModelProperty("国家编码")
    private String countryCode;

    /** 国家名称 */
    @ApiModelProperty("国家名称")
    private String countryName;

    /** 最小清关价格 */
    @ApiModelProperty("最小清关价格")
    private BigDecimal minCustomsPrice;

    /** 是否等于最小清关价格 */
    @ApiModelProperty("是否等于最小清关价格")
    private String minEq;

    /** 最大清关价格 */
    @ApiModelProperty("最大清关价格")
    private BigDecimal maxCustomsPrice;

    /** 是否等于最大清关价格 */
    @ApiModelProperty("是否等于最大清关价格")
    private String maxEq;

    /** 区间类型：区间，非区间 */
    @ApiModelProperty("区间类型：区间，非区间")
    private String intervalType;

    /** 清关币种 */
    @ApiModelProperty("清关币种")
    private String customsCurrency;

    /** 二次折算率 */
    @ApiModelProperty("二次折算率")
    private BigDecimal againCoeff;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 清关价格 */
    @ApiModelProperty("清关价格")
    private BigDecimal customsPrice;

    /** 国家编码集合 */
    @ApiModelProperty("国家编码集合")
    private List<String> countryCodeList;

    /** 清关单价 */
    @ApiModelProperty("清关单价")
    private BigDecimal customsUnitPrice;
}
