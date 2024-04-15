package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ;
 *
 * @author : LSY
 * @date : 2023-12-19
 */
@TableName("ALLOC_STRUCTURE")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class AllocStructure implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @ApiModelProperty("id")
    @ExcelProperty(value = "id")
    @TableField("ID")
    private String id;


    /**
     * 分摊ID
     */

    @ApiModelProperty("分摊ID")
    @TableField("alloc_id")
    @ExcelProperty(value = "分摊ID")
    private String allocId;

    /**
     * 期间
     */

    @ApiModelProperty("期间")
    @ExcelProperty(value = "期间")
    @TableField("PERIOD")
    private String period;

    /**
     * 三级部门
     */

    @ApiModelProperty("三级部门")
    @ExcelProperty(value = "三级部门")
    @TableField("DEPT3")
    private String dept3;

    /**
     * 四级部门
     */

    @ApiModelProperty("四级部门")
    @ExcelProperty(value = "四级部门")
    @TableField("DEPT4")
    private String dept4;

    /**
     * 待分摊人数
     */

    @ApiModelProperty("待分摊人数")
    @ExcelProperty(value = "待分摊人数")
    @TableField("TO_ALLOC_NUM")
    private BigDecimal toAllocNum;

    /**
     *
     */

    @ApiModelProperty("原人数")
    @ExcelProperty(value = "原人数")
    @TableField("ORI_NUM")
    private BigDecimal oriNum;

    /**
     * 分摊值
     */

    @ApiModelProperty("分摊值")
    @ExcelProperty(value = "分摊值")
    @TableField("ALLOC_NUM")
    private BigDecimal allocNum;

    /**
     * 分摊行人数
     */

    @ApiModelProperty("分摊行人数")
    @ExcelProperty(value = "分摊行人数")
    @TableField("ALLOCED_NUM")
    private BigDecimal allocedNum;

    /**
     * 亚马逊比例
     */

    @ApiModelProperty("亚马逊比例")
    @ExcelProperty(value = "亚马逊比例")
    @TableField("AMAZON_RATIO")
    private BigDecimal amazonRatio;

    /**
     * 乐天比例
     */

    @ApiModelProperty("乐天比例")
    @ExcelProperty(value = "乐天比例")
    @TableField("RAKUTEN_RATIO")
    private BigDecimal rakutenRatio ;

    /**
     * 沃尔玛比例
     */

    @ApiModelProperty("沃尔玛比例")
    @ExcelProperty(value = "沃尔玛比例")
    @TableField("WALMART_RATIO")
    private BigDecimal walmartRatio ;



    /**
     * lazada比例
     */

    @ApiModelProperty("lazada比例")
    @ExcelProperty(value = "Lazada比例")
    @TableField("LAZADA_RATIO")
    private BigDecimal lazadaRatio ;

    /**
     * shopee比例
     */

    @ApiModelProperty("shopee比例")
    @ExcelProperty(value = "Shopee比例")
    @TableField("SHOPEE_RATIO")
    private BigDecimal shopeeRatio ;

    /**
     * 速卖通比例
     */

    @ApiModelProperty("速卖通比例")
    @ExcelProperty(value = "速卖通比例")
    @TableField("SMT_RATIO")
    private BigDecimal smtRatio ;


    @ApiModelProperty("Shopify比例")
    @ExcelProperty(value = "Shopify比例")
    @TableField("SHOPIFY_RATIO")
    private BigDecimal shopifyRatio ;


    @ApiModelProperty("B2B比例")
    @ExcelProperty(value = "B2B比例")
    @TableField("B2B_RATIO")
    private BigDecimal b2bRatio ;


    @ApiModelProperty("阿里巴巴比例")
    @ExcelProperty(value = "阿里巴巴比例")
    @TableField("ALIBABA_RATIO")
    private BigDecimal alibabaRatio ;

    @ApiModelProperty("拼多多比例")
    @ExcelProperty(value = "拼多多比例")
    @TableField("TEMU_RATIO")
    private BigDecimal temuRatio ;

    /**
     * 亚马逊分摊
     */

    @ApiModelProperty("亚马逊分摊")
    @ExcelProperty(value = "亚马逊")
    @TableField("AMAZON_ALLOC")
    private BigDecimal amazonAlloc ;

    /**
     * 乐天分摊
     */

    @ApiModelProperty("乐天分摊")
    @ExcelProperty(value = "乐天")
    @TableField("RAKUTEN_ALLOC")
    private BigDecimal rakutenAlloc ;

    /**
     * 沃尔玛分摊
     */

    @ApiModelProperty("沃尔玛分摊")
    @ExcelProperty(value = "沃尔玛")
    @TableField("WALMART_ALLOC")
    private BigDecimal walmartAlloc ;




    @ApiModelProperty("B2B分摊")
    @ExcelProperty(value = "B2B")
    @TableField("B2B_ALLOC")
    private BigDecimal b2bAlloc ;


    @ApiModelProperty("阿里巴巴分摊")
    @ExcelProperty(value = "阿里巴巴")
    @TableField("ALIBABA_ALLOC")
    private BigDecimal alibabaAlloc ;

    /**
     * lazada分摊
     */

    @ApiModelProperty("lazada分摊")
    @ExcelProperty(value = "Lazada")
    @TableField("LAZADA_ALLOC")
    private BigDecimal lazadaAlloc ;

    /**
     * shopee分摊
     */

    @ApiModelProperty("shopee分摊")
    @ExcelProperty(value = "Shopee")
    @TableField("SHOPEE_ALLOC")
    private BigDecimal shopeeAlloc ;

    /**
     * 速卖通分摊
     */

    @ApiModelProperty("速卖通分摊")
    @ExcelProperty(value = "速卖通")
    @TableField("SMT_ALLOC")
    private BigDecimal smtAlloc ;


    @ApiModelProperty("Shopify分摊")
    @ExcelProperty(value = "Shopify")
    @TableField("shopify_alloc")
    private BigDecimal shopifyAlloc ;

    @ApiModelProperty("拼多多分摊")
    @ExcelProperty(value = "拼多多分摊")
    @TableField("TEMU_ALLOC")
    private BigDecimal temuAlloc ;

    @ApiModelProperty("")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     *
     */


    @ApiModelProperty("")
    @TableField("UPDATE_BY")
    private String updateBy;


    @ApiModelProperty("")
    @TableField("CREATE_BY")
    private String createBy;

    /**
     *
     */

    @ApiModelProperty("")
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 确认状态1
     */


    @ApiModelProperty("确认状态")
    @TableField("status")
    private Integer status;


    @ApiModelProperty("分摊平台")
    @TableField("platforms")
    private String platforms;

    private String confirmBy ;
    private Date confirmDate ;


    private String isLaborCost ;

}