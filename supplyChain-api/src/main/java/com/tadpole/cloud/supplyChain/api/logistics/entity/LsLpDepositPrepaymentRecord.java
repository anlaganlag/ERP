package com.tadpole.cloud.supplyChain.api.logistics.entity;

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
 * 物流商押金&预付操作记录 实体类
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LP_DEPOSIT_PREPAYMENT_RECORD")
@ExcelIgnoreUnannotated
public class LsLpDepositPrepaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流商押金&预付id */
    @TableField("PID")
    private BigDecimal pid;

    /** 物流商编码 */
    @TableField("LP_CODE")
    private String lpCode;

    /** 操作类型：付款，编辑 */
    @TableField("OPT_TYPE")
    private String optType;

    /** 操作详情 */
    @TableField("OPT_DETAIL")
    private String optDetail;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}