package com.tadpole.cloud.product.api.productplan.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 预提案-改良 入参类
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
public class RdPreProposalUpParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 预案编号 */
    @ApiModelProperty("预案编号")
    private String sysYaCode;

    /** 预案设计-组序号 */
    @ApiModelProperty("预案设计-组序号")
    private BigDecimal sysGNum;

    /** 预案设计-改良点 */
    @ApiModelProperty("预案设计-改良点")
    private String sysImprovePoint;

    /** 预案设计-改良点图示 */
    @ApiModelProperty("预案设计-改良点图示")
    private String sysImprovePointPic;

    /** 预案设计-严重程度 值域{"","建议","一般","重点"} */
    @ApiModelProperty("预案设计-严重程度 值域{'','建议','一般','重点'}")
    private String sysSeverity;

    /** 预案设计-是否采纳 值域{"采纳","不采纳"} */
    @ApiModelProperty("预案设计-是否采纳 值域{'采纳','不采纳'}")
    private String sysIsAdopt;

    /** 预案设计-不采纳说明 */
    @ApiModelProperty("预案设计-不采纳说明")
    private String sysNotAdoptDesc;

}
