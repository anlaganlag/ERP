package com.tadpole.cloud.product.api.productplan.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 预提案-改良 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_PRE_PROPOSAL_UP")
public class RdPreProposalUpResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("预案编号")
    private String sysYaCode;


    @ApiModelProperty("预案设计-组序号")
    private BigDecimal sysGNum;


    @ApiModelProperty("预案设计-改良点")
    private String sysImprovePoint;


    @ApiModelProperty("预案设计-改良点图示")
    private String sysImprovePointPic;


    @ApiModelProperty("预案设计-严重程度 值域{'','建议','一般','重点'}")
    private String sysSeverity;


    @ApiModelProperty("预案设计-是否采纳 值域{'采纳','不采纳'}")
    private String sysIsAdopt;


    @ApiModelProperty("预案设计-不采纳说明")
    private String sysNotAdoptDesc;

}
