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
 * 报关自定义外箱表 出参类
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
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOM_BOX_INFO")
public class TgCustomBoxInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("箱型")
    private String boxType;

    @ApiModelProperty("最小体积")
    private BigDecimal minVolume;

    @ApiModelProperty("是否等于最小体积 0：否，1：是")
    private String minEq;

    @ApiModelProperty("最大体积")
    private BigDecimal maxVolume;

    @ApiModelProperty("是否等于最大体积 0：否，1：是")
    private String maxEq;

    @ApiModelProperty("箱子重量（KG）")
    private BigDecimal boxWeight;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
