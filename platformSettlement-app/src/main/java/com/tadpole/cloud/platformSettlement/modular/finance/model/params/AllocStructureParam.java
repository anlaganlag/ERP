package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ;
 * @author : LSY
 * @date : 2023-12-19
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllocStructureParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    private String id ;
 
    /** 期间 */
    @ApiModelProperty(value = "期间")
    private String period ;
 
    /** 三级部门 */
    @ApiModelProperty(value = "三级部门")
    private String dept3 ;
 
    /** 四级部门 */
    @ApiModelProperty(value = "四级部门")
    private String dept4 ;
 
    /** 待分摊人数 */
    @ApiModelProperty(value = "待分摊人数")
    private BigDecimal toAllocNum ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal oriNum ;
 
    /** 分摊值 */
    @ApiModelProperty(value = "分摊值")
    private BigDecimal allocNum ;
 
    /** 分摊行人数 */
    @ApiModelProperty(value = "分摊行人数")
    private BigDecimal allocedNum ;
 
    /** 亚马逊比率 */
    @ApiModelProperty(value = "亚马逊比率")
    private BigDecimal amazonRatio ;
 
    /** 乐天比例 */
    @ApiModelProperty(value = "乐天比例")
    private BigDecimal rakutenRatio ;
 
    /** 沃尔玛比例 */
    @ApiModelProperty(value = "沃尔玛比例")
    private BigDecimal walmartRatio ;
 
    /** B2B阿里比例 */
    @ApiModelProperty(value = "B2B阿里比例")
    private BigDecimal b2baliRatio ;
 
    /** lazada比例 */
    @ApiModelProperty(value = "lazada比例")
    private BigDecimal lazadaRatio ;
 
    /** shopee比例 */
    @ApiModelProperty(value = "shopee比例")
    private BigDecimal shopeeRatio ;
 
    /** 速卖通比率 */
    @ApiModelProperty(value = "速卖通比率")
    private BigDecimal smtRatio ;

   @ApiModelProperty(value = "Shopify分摊")
   private BigDecimal shopifyRatio ;
 
    /** 亚马逊分摊 */
    @ApiModelProperty(value = "亚马逊分摊")
    private BigDecimal amazonAlloc ;

    @ApiModelProperty("拼多多分摊")
    private BigDecimal temuAlloc ;


    @ApiModelProperty("B2B比例")
    private BigDecimal b2bRatio ;


    @ApiModelProperty("阿里巴巴比例")
    private BigDecimal alibabaRatio ;

    @ApiModelProperty("拼多多比例")
    private BigDecimal temuRatio ;


 
    /** 乐天分摊 */
    @ApiModelProperty(value = "乐天分摊")
    private BigDecimal rakutenAlloc ;
 
    /** 沃尔玛分摊 */
    @ApiModelProperty(value = "沃尔玛分摊")
    private BigDecimal walmartAlloc ;
 
    /** B2B阿里分摊 */
    @ApiModelProperty(value = "B2B阿里分摊")
    private BigDecimal b2baliAlloc ;
 
    /** lazada分摊 */
    @ApiModelProperty(value = "lazada分摊")
    private BigDecimal lazadaAlloc ;
 
    /** shopee分摊 */
    @ApiModelProperty(value = "shopee分摊")
    private BigDecimal shopeeAlloc ;
 
    /** 速卖通分摊 */
    @ApiModelProperty(value = "速卖通分摊")
    private BigDecimal smtAlloc ;


   @ApiModelProperty(value = "Shopify分摊")
   private BigDecimal shopifyAlloc ;


    @ApiModelProperty("B2B比例")
    private BigDecimal b2bAlloc ;


    @ApiModelProperty("阿里巴巴比例")
    private BigDecimal alibabaAlloc ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String createBy ;

   private List<String> confirmIdList;

    private String isLaborCost ;


}