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
 * 清关价格折算 入参类
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
@TableName("TG_CUSTOMS_PRICE_COEFF_RULE")
public class TgCustomsPriceCoeffRuleParam extends BaseRequest implements Serializable, BaseValidatingParam {

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

    @ApiModelProperty("HSCode")
    private String hsCode;

    /** 价格类型 */
    @ApiModelProperty("价格类型")
    private String priceType;

    /** 最小关税率 */
    @ApiModelProperty("最小关税率")
    private BigDecimal minCustomsRate;

    /** 是否等于最小关税率 0：否，1：是 */
    @ApiModelProperty("是否等于最小关税率 0：否，1：是")
    private String minEq;

    /** 最大关税率 */
    @ApiModelProperty("最大关税率")
    private BigDecimal maxCustomsRate;

    /** 是否等于最大关税率 0：否，1：是 */
    @ApiModelProperty("是否等于最大关税率 0：否，1：是")
    private String maxEq;

    /** 区间类型：区间，非区间 */
    @ApiModelProperty("区间类型：区间，非区间")
    private String intervalType;

    /** 清关折算率 */
    @ApiModelProperty("清关折算率")
    private BigDecimal customsCoeff;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

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

    /** 国家编码集合 */
    @ApiModelProperty("国家编码集合")
    private List<String> countryCodeList;

    /** 关税率 */
    @ApiModelProperty("关税率")
    private BigDecimal taxRate;
}
