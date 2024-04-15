package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-开发样盘点明细 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_INV_DET")
@ExcelIgnoreUnannotated
public class RdSampleInvDet implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编码 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-盘点编号 */
    @TableField("SYS_INV_CODE")
    private String sysInvCode;

    /** 系统信息-处置结果 值域{“封存中”,"已报失",“已销毁”} */
    @TableField("SYS_INV_DISPOSAL_RESULT")
    private String sysInvDisposalResult;

    /** 系统信息-样品数量 */
    @TableField("SYS_INV_SAMPLE_QTY")
    private BigDecimal sysInvSampleQty;

    /** 系统信息-累积样品费用 */
    @TableField("SYS_INV_ACCU_FEE")
    private BigDecimal sysInvAccuFee;

}