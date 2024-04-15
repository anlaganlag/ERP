package com.tadpole.cloud.product.api.productplan.entity;

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
 * 预提案-改良 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PRE_PROPOSAL_UP")
@ExcelIgnoreUnannotated
public class RdPreProposalUp implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 预案编号 */
    @TableField("SYS_YA_CODE")
    private String sysYaCode;

    /** 预案设计-组序号 */
    @TableField("SYS_G_NUM")
    private BigDecimal sysGNum;

    /** 预案设计-改良点 */
    @TableField("SYS_IMPROVE_POINT")
    private String sysImprovePoint;

    /** 预案设计-改良点图示 */
    @TableField("SYS_IMPROVE_POINT_PIC")
    private String sysImprovePointPic;

    /** 预案设计-严重程度 值域{"","建议","一般","重点"} */
    @TableField("SYS_SEVERITY")
    private String sysSeverity;

    /** 预案设计-是否采纳 值域{"采纳","不采纳"} */
    @TableField("SYS_IS_ADOPT")
    private String sysIsAdopt;

    /** 预案设计-不采纳说明 */
    @TableField("SYS_NOT_ADOPT_DESC")
    private String sysNotAdoptDesc;

}