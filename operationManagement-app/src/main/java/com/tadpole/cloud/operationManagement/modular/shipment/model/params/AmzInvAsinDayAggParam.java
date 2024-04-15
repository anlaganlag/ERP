package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


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
    * 店铺销量库存表
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMZ_INV_ASIN_DAY_AGG")
public class AmzInvAsinDayAggParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 日期 */
    @ApiModelProperty("日期")
    private Date bizdate;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 区域 */
    @ApiModelProperty("区域")
    private String preArea;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 销售品牌 */
    @ApiModelProperty("销售品牌")
    private String salesBrand;

    /** 店铺近7天销量 */
    @ApiModelProperty("店铺近7天销量")
    private BigDecimal day7qty;

    /** 店铺近30天销量 */
    @ApiModelProperty("店铺近30天销量")
    private BigDecimal day30qty;

    /** 店铺海外总库存 */
    @ApiModelProperty("店铺海外总库存")
    private BigDecimal azOverseaTotalAty;

    /** 店铺可售库存 */
    @ApiModelProperty("店铺可售库存")
    private BigDecimal azAvailQty;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

   /**
    * 站点
    */
   @ApiModelProperty("SYS_SITE")
   private String sysSite;

}
