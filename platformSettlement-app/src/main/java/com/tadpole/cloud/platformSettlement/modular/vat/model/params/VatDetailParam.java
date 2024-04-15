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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 税金测算VAT明细
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
@TableName("VAT_DETAIL")
public class VatDetailParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @ApiModelProperty("期间")
    private String activityPeriod;

    /** 账号 */
    @ApiModelProperty("账号")
    private String ebmsShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 币种 */
    @ApiModelProperty("币种")
    private String transactionCurrencyCode;

    /** 发货国 */
    @ApiModelProperty("发货国")
    private BigDecimal saleDepartCountry;

    /** 目的国 */
    @ApiModelProperty("目的国")
    private BigDecimal saleArrivalCountry;

    /** Maketplace */
    @ApiModelProperty("Maketplace")
    private BigDecimal maketplace;

    /** seller-发货国B2B */
    @ApiModelProperty("seller-发货国B2B")
    private BigDecimal saleDepartCountryB;

    /** 店铺维度生成状态（默认0：未生成，1：已生成） */
    @ApiModelProperty("店铺维度生成状态（默认0：未生成，1：已生成）")
    private BigDecimal generateStatus;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createPerson;

    /** 创建时间--默认值：getdate,首次创建 */
    @ApiModelProperty("创建时间--默认值：getdate,首次创建")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @ApiModelProperty("最后更新时间--默认值：getdate")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @ApiModelProperty("最后更新人名称--默认值：当前修改人名称")
    private String updatePerson;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> ebmsShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 创建人名稱 */
    @ApiModelProperty("创建人名稱")
    private String createPersonName;
}