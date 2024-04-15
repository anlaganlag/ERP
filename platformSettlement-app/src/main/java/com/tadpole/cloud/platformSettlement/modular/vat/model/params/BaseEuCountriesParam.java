package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 基础信息-欧盟国家参数列表
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
@TableName("BASE_EU_COUNTRIES")
public class BaseEuCountriesParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 欧盟国家id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家缩写 */
    @ApiModelProperty("COUNTRY")
    private String country;
    /** 国家缩写 */
    @ApiModelProperty("COUNTRY")
    private List<String> countrys;

    /** 国家英文 */
    @ApiModelProperty("COUNTRY_EN")
    private String countryEn;

    /** 国家中文 */
    @ApiModelProperty("COUNTRY_CN")
    private String countryCn;
    /** 国家中文 */
    @ApiModelProperty("COUNTRY_CN")
    private List<String> countryCns;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    /** 状态->0:正常，1：删除||作废; */
    @ApiModelProperty("IS_DELETE")
    private BigDecimal status;
}