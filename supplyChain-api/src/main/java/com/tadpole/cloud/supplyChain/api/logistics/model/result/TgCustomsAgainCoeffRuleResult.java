package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 清关二次折算 出参类
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
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_AGAIN_COEFF_RULE")
public class TgCustomsAgainCoeffRuleResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ApiModelProperty("国家名称")
    private String countryName;

    @ApiModelProperty("最小清关价格")
    private BigDecimal minCustomsPrice;

    @ApiModelProperty("是否等于最小清关价格")
    private String minEq;

    @ApiModelProperty("最大清关价格")
    private BigDecimal maxCustomsPrice;

    @ApiModelProperty("是否等于最大清关价格")
    private String maxEq;

    /** 区间类型：区间，非区间 */
    @ApiModelProperty("区间类型：区间，非区间")
    private String intervalType;

    @ApiModelProperty("清关币种")
    private String customsCurrency;

    @ApiModelProperty("二次折算率")
    private BigDecimal againCoeff;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
