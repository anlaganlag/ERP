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
 * 报关自定义外箱表 入参类
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_CUSTOM_BOX_INFO")
public class TgCustomBoxInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 箱型 */
    @ApiModelProperty("箱型")
    private String boxType;

    /** 最小体积 */
    @ApiModelProperty("最小体积")
    private BigDecimal minVolume;

    /** 是否等于最小体积 0：否，1：是 */
    @ApiModelProperty("是否等于最小体积 0：否，1：是")
    private String minEq;

    /** 最大体积 */
    @ApiModelProperty("最大体积")
    private BigDecimal maxVolume;

    /** 是否等于最大体积 0：否，1：是 */
    @ApiModelProperty("是否等于最大体积 0：否，1：是")
    private String maxEq;

    /** 箱子重量（KG） */
    @ApiModelProperty("箱子重量（KG）")
    private BigDecimal boxWeight;

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

    /** 体积 */
    @ApiModelProperty("体积")
    private BigDecimal volume;

    /** 箱子重量（KG）集合 */
    @ApiModelProperty("箱子重量（KG）集合")
    private List<BigDecimal> boxWeightList;

}
