package com.tadpole.cloud.product.api.productproposal.model.result;

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
 * 提案-开发样盘点明细 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_SAMPLE_INV_DET")
public class RdSampleInvDetResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty("系统信息-盘点编号")
    private String sysInvCode;


    @ApiModelProperty("系统信息-处置结果 值域{'封存中','已报失','已销毁'}")
    private String sysInvDisposalResult;


    @ApiModelProperty("系统信息-样品数量")
    private BigDecimal sysInvSampleQty;


    @ApiModelProperty("系统信息-累积样品费用")
    private BigDecimal sysInvAccuFee;

}
