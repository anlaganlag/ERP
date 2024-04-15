package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-设置-提案等级审批设置 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_TL_SETTING")
@ExcelIgnoreUnannotated
public class RdTlSetting implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统实时时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统实时时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 登陆用户员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 登陆用户员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 提案等级 值域{“S”,"A","B","C","D"} */
    @TableField("SYS_TA_LEVEL")
    private String sysTaLevel;

    /** 首单采购数量 */
    @TableField("SYS_FIRST_ORDER_PUR_QTY")
    private BigDecimal sysFirstOrderPurQty;

    /** 首单采购金额 */
    @TableField("SYS_FIRST_ORDER_PUR_AMOUNT")
    private BigDecimal sysFirstOrderPurAmount;

    /** 是否自动审批 值域{“是”,"否"} */
    @TableField("SYS_IS_AUTO_APPROVE")
    private String sysIsAutoApprove;

}