package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


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
@ExcelIgnoreUnannotated
@TableName("AMZ_INV_ASIN_DAY_AGG")
public class AmzInvAsinDayAggResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    @ApiModelProperty("日期")
    private Date bizdate;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("区域")
    private String preArea;


    @ApiModelProperty("事业部")
    private String department;


    @ApiModelProperty("Team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("ASIN")
    private String asin;


    @ApiModelProperty("账号")
    private String sysShopsName;


    @ApiModelProperty("销售品牌")
    private String salesBrand;


    @ApiModelProperty("店铺近7天销量")
    private BigDecimal day7qty;


    @ApiModelProperty("店铺近30天销量")
    private BigDecimal day30qty;


    @ApiModelProperty("店铺海外总库存")
    private BigDecimal azOverseaTotalAty;


    @ApiModelProperty("店铺可售库存")
    private BigDecimal azAvailQty;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    /**
     * 站点
     */
    @ApiModelProperty("SYS_SITE")
    private String sysSite;

}
