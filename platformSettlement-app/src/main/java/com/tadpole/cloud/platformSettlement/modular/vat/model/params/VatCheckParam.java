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
import java.util.List;

/**
 * <p>
 * 账号检查表
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
@TableName("VAT_SHOP_CHECK")
public class VatCheckParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @ApiModelProperty("期间")
    private String activityPeriod;

    /** 开始月份 */
    @ApiModelProperty("开始月份")
    private String startMonth;

    /** 结束月份 */
    @ApiModelProperty("结束月份")
    private String endMonth;

    /** 账号 */
    @ApiModelProperty("账号")
    private String ebmsShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 币种 */
    @ApiModelProperty("币种")
    private String transactionCurrencyCode;

    /** 销售VAT-卖家 */
    @ApiModelProperty("销售VAT-卖家")
    private BigDecimal salesVatSeller;

    /** 销售VAT-代理/会计师 */
    @ApiModelProperty("销售VAT-代理/会计师")
    private BigDecimal salesVatAgent;

    /** 销售VAT差异 */
    @ApiModelProperty("销售VAT差异")
    private BigDecimal salesVatDifference;

    /** B2B-卖家 */
    @ApiModelProperty("B2B-卖家")
    private BigDecimal btbSeller;

    /** B2B-代理/会计师 */
    @ApiModelProperty("B2B-代理/会计师")
    private BigDecimal btbAgent;

    /** B2B差异 */
    @ApiModelProperty("B2B差异")
    private BigDecimal btbDifference;

    /** C88/C79 */
    @ApiModelProperty("C88/C79")
    private BigDecimal cee;

    /** 结余/其它 */
    @ApiModelProperty("结余/其它")
    private BigDecimal bablance;

    /** 实际缴纳/退税 */
    @ApiModelProperty("实际缴纳/退税")
    private BigDecimal actualPayment;

    /** 备注1 */
    @ApiModelProperty("备注1")
    private String remarkOne;

    /** 备注2 */
    @ApiModelProperty("备注2")
    private String remarkTwo;

    /** 创建时间--默认值：getdate,首次创建 */
    @ApiModelProperty("创建时间--默认值：getdate,首次创建")
    private Date createTime;

    /** 创建人编号--默认值：创建人编号，首次创建 */
    @ApiModelProperty("创建人编号--默认值：创建人编号，首次创建")
    private String createPersonNo;

    /** 创建人名称--默认值：创建人名称，首次创建 */
    @ApiModelProperty("创建人名称--默认值：创建人名称，首次创建")
    private String createPersonName;

    /** 最后更新时间--默认值：getdate */
    @ApiModelProperty("最后更新时间--默认值：getdate")
    private Date updateTime;

    /** 最后更新人编号--默认值：当前修改人编号 */
    @ApiModelProperty("最后更新人编号--默认值：当前修改人编号")
    private String updatePersonNo;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @ApiModelProperty("最后更新人名称--默认值：当前修改人名称")
    private String updatePersonName;

    /** 账号集合 */
    @ApiModelProperty("账号集合")
    private List<String> ebmsShopsNames;

    /** 站点集合 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 币别集合 */
    @ApiModelProperty("币别集合")
    private List<String> transactionCurrencyCodes;

}