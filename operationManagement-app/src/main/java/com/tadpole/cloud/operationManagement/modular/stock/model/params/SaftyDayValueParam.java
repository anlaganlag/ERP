package com.tadpole.cloud.operationManagement.modular.stock.model.params;

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
    * 安全天数参数值
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_SAFTY_DAY_VALUE")
public class SaftyDayValueParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** STOCK_SAFTY_DAY_SUMMARY表对应数据id */
    @ApiModelProperty("STOCK_SAFTY_DAY_SUMMARY表对应数据id")
    private BigDecimal summaryId;

    /** 排序编号 */
    @ApiModelProperty("排序编号")
    private BigDecimal sortNum;

    /** 日均销量低 */
    @ApiModelProperty("日均销量低")
    private BigDecimal salesLow;

    /** 日均销量高 */
    @ApiModelProperty("日均销量高")
    private BigDecimal salesHigh;

    /** 安全销售天数 */
    @ApiModelProperty("安全销售天数")
    private BigDecimal safeSaleDays;

    /** 订货天数 */
    @ApiModelProperty("订货天数")
    private BigDecimal orderGoodsDays;

    /** 是否启用（0：禁用,1：启用）默认：1 */
    @ApiModelProperty("是否启用（0：禁用,1：启用）默认：1")
    private int isEnable;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("修改人")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}