package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ExcelIgnoreUnannotated
public class SaftyDayValue implements Serializable {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** STOCK_SAFTY_DAY_SUMMARY表对应数据id */
    @TableField("SUMMARY_ID")
    private BigDecimal summaryId;

    /** 排序编号 */
    @TableField("SORT_NUM")
    private BigDecimal sortNum;

    /** 日均销量低 */
    @TableField("SALES_LOW")
    private BigDecimal salesLow;

    /** 日均销量高 */
    @TableField("SALES_HIGH")
    private BigDecimal salesHigh;

    /** 安全销售天数 */
    @TableField("SAFE_SALE_DAYS")
    private BigDecimal safeSaleDays;

    /** 订货天数 */
    @TableField("ORDER_GOODS_DAYS")
    private BigDecimal orderGoodsDays;

    /** 是否启用（0：禁用,1：启用）默认：1 */
    @TableField("IS_ENABLE")
    private int isEnable;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @TableField("IS_DELETE")
    private int isDelete;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}