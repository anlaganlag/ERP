package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
@ExcelIgnoreUnannotated
public class AllocStructureResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    @ExcelProperty(value ="id")
    private String id ;


   @ApiModelProperty(value = "分摊ID")
   @ExcelProperty(value ="分摊ID")
   private String allocId ;
 
    /** 期间 */
    @ApiModelProperty(value = "期间")
    @ExcelProperty(value ="期间")
    private String period ;
 
    /** 三级部门 */
    @ApiModelProperty(value = "三级部门")
    @ExcelProperty(value ="三级部门")
    private String dept3 ;
 
    /** 四级部门 */
    @ApiModelProperty(value = "四级部门")
    @ExcelProperty(value ="四级部门")
    private String dept4 ;
 
    /** 待分摊人数 */
    @ApiModelProperty(value = "待分摊人数")
    @ExcelProperty(value ="待分摊人数")
    private BigDecimal toAllocNum ;
 
    /**  */
    @ApiModelProperty(value = "原人数")
    @ExcelProperty(value ="原人数")
    private BigDecimal oriNum ;
 
    /** 分摊值 */
    @ApiModelProperty(value = "分摊值")
    @ExcelProperty(value ="分摊值")
    private BigDecimal allocNum ;
 
    /** 分摊后人数 */
    @ApiModelProperty(value = "分摊后人数")
    @ExcelProperty(value ="分摊后人数")
    private BigDecimal allocedNum ;
 
    /** 亚马逊分摊比例 */
    @ApiModelProperty(value = "亚马逊分摊比例")
    @ExcelProperty(value ="亚马逊比例")
    private BigDecimal amazonRatio ;
 
    /** 乐天分摊比例 */
    @ApiModelProperty(value = "乐天分摊比例")
    @ExcelProperty(value ="乐天比例")
    private BigDecimal rakutenRatio ;
 
    /** 沃尔玛分摊比例 */
    @ApiModelProperty(value = "沃尔玛分摊比例")
    @ExcelProperty(value ="沃尔玛比例")
    private BigDecimal walmartRatio ;
 

 
    /** Lazada分摊比例 */
    @ApiModelProperty(value = "Lazada分摊比例")
    @ExcelProperty(value ="Lazada比例")
    private BigDecimal LazadaRatio ;
 
    /** Shopee分摊比例 */
    @ApiModelProperty(value = "Shopee分摊比例")
    @ExcelProperty(value ="Shopee比例")
    private BigDecimal shopeeRatio ;
 
    /** 速卖通分摊比例 */
    @ApiModelProperty(value = "速卖通分摊比例")
    @ExcelProperty(value ="速卖通比例")
    private BigDecimal smtRatio ;

   @ApiModelProperty(value = "Shopify分摊比例")
   @ExcelProperty(value ="Shopify比例")
   private BigDecimal shopifyRatio ;

   @ApiModelProperty("B2B比例")
   @ExcelProperty(value ="B2B比例")
   private BigDecimal b2bRatio ;


    @ApiModelProperty("拼多多分摊")
    private BigDecimal temuAlloc ;

    @ApiModelProperty("拼多多比例")
    private BigDecimal temuRatio ;

   @ApiModelProperty("阿里巴巴比例")
   @ExcelProperty(value ="阿里巴巴比例")
   private BigDecimal alibabaRatio ;


  @ApiModelProperty("B2B分摊")
  @ExcelProperty(value ="B2B分摊")
  private BigDecimal b2bAlloc ;


  @ApiModelProperty("阿里巴巴分摊")
  @ExcelProperty(value ="阿里巴巴分摊")
  private BigDecimal alibabaAlloc ;


 
    /** 亚马逊分摊 */
    @ApiModelProperty(value = "亚马逊分摊")
    @ExcelProperty(value ="亚马逊")
    private BigDecimal amazonAlloc ;
 
    /** 乐天分摊 */
    @ApiModelProperty(value = "乐天分摊")
    @ExcelProperty(value ="乐天")
    private BigDecimal rakutenAlloc ;
 
    /** 沃尔玛分摊 */
    @ApiModelProperty(value = "沃尔玛分摊")
    @ExcelProperty(value ="沃尔玛")
    private BigDecimal walmartAlloc ;
 

 
    /** Lazada分摊 */
    @ApiModelProperty(value = "Lazada分摊")
    @ExcelProperty(value ="Lazada")
    private BigDecimal LazadaAlloc ;
 
    /** shopee分摊 */
    @ApiModelProperty(value = "shopee分摊")
    @ExcelProperty(value ="Shopee")
    private BigDecimal shopeeAlloc ;
 
    /** 速卖通分摊 */
    @ApiModelProperty(value = "速卖通分摊")
    @ExcelProperty(value ="速卖通")
    private BigDecimal smtAlloc ;

   @ApiModelProperty(value = "Shopify分摊")
   @ExcelProperty(value ="Shopify")
   private BigDecimal shopifyAlloc ;
 
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

   @ApiModelProperty(value = "")
   private List<String> platformList ;


  @ApiModelProperty(value = "")
  private String platforms ;


  @ApiModelProperty(value = "")
  private String status ;


    @ApiModelProperty(value = "是否结算人力成本")
    private String isLaborCost ;


}