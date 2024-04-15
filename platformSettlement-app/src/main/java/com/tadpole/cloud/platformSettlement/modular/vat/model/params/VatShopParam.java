package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * 税金测算VAT店铺维度
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("VAT_SHOP")
public class VatShopParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @ApiModelProperty("ACTIVITY_PERIOD")
    private String activityPeriod;

    /** 账号 */
    @ApiModelProperty("EBMS_SHOPS_NAME")
    private String ebmsShopsName;

    /** 站点 */
    @ApiModelProperty("SYS_SITE")
    private String sysSite;

    /** 卖家自行缴纳VAT */
    @ApiModelProperty("SELLER_VAT")
    private BigDecimal sellerVat;

    /** 亚马逊代扣代缴VAT */
    @ApiModelProperty("AMAZON_VAT")
    private BigDecimal amazonVat;

    /** 跨境B2B免税 */
    @ApiModelProperty("FREE_VAT")
    private BigDecimal freeVat;

    /** 核对表生成状态（默认0：未生成，1：已生成） */
    @ApiModelProperty("GENERATE_STATUS")
    private BigDecimal generateStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_PERSON")
    private String createPerson;

    /** 创建时间--默认值：getdate,首次创建 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @ApiModelProperty("UPDATE_PERSON")
    private String updatePerson;

}