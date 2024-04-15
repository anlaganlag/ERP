package com.tadpole.cloud.product.api.productproposal.model.params;

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
 * 提案-开发样盘点明细 入参类
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
public class RdSampleInvDetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编码 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-盘点编号 */
    @ApiModelProperty("系统信息-盘点编号")
    private String sysInvCode;

    /** 系统信息-处置结果 值域{“封存中”,"已报失",“已销毁”} */
    @ApiModelProperty("系统信息-处置结果 值域{'封存中','已报失','已销毁'}")
    private String sysInvDisposalResult;

    /** 系统信息-样品数量 */
    @ApiModelProperty("系统信息-样品数量")
    private BigDecimal sysInvSampleQty;

    /** 系统信息-累积样品费用 */
    @ApiModelProperty("系统信息-累积样品费用")
    private BigDecimal sysInvAccuFee;

}
