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
 * 通关产品详细信息 出参类
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
@TableName("TG_BASE_PRODUCT_DETAIL")
public class TgBaseProductDetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("通关产品基础信息ID")
    private BigDecimal pid;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ApiModelProperty("国家名称")
    private String countryName;

    @ApiModelProperty("HSCode")
    private String hsCode;

    @ApiModelProperty("流转税率")
    private BigDecimal changeTaxRate;

    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ApiModelProperty("附加税率")
    private BigDecimal addTaxRate;

    /** 合并状态 0：未合并，1：已合并 */
    @ApiModelProperty("合并状态 0：未合并，1：已合并")
    private String mergeStatus;

    /** 合并ID：合并数据对应合并后的数据ID */
    @ApiModelProperty("合并ID：合并数据对应合并后的数据ID")
    private BigDecimal mergeId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
