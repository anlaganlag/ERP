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

/**
 * <p>
 * 国家地区信息 入参类
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
@TableName("TG_COUNTRY_INFO")
public class TgCountryInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 中文全称 */
    @ApiModelProperty("中文全称")
    private String countryNameCnFull;

    /** 中文简称 */
    @ApiModelProperty("中文简称")
    private String countryNameCn;

    /** 英文全称 */
    @ApiModelProperty("英文全称")
    private String countryNameEn;

    /** 英文简称 */
    @ApiModelProperty("英文简称")
    private String countryCode;

    /** 亚马逊站点 */
    @ApiModelProperty("亚马逊站点")
    private String site;

    /** 亚马逊链接 */
    @ApiModelProperty("亚马逊链接")
    private String amazonUrl;

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

}
