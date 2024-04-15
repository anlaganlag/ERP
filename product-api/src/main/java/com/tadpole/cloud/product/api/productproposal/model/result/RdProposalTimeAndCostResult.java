package com.tadpole.cloud.product.api.productproposal.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提案-提案管理 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
//@TableName("RD_PROPOSAL")
public class RdProposalTimeAndCostResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提案编号")
    private String sysTaCode;

    @ApiModelProperty("拿样任务编号")
    private String sysTsTaskCode;

    @ApiModelProperty("系统信息-拿样任务状态 值域{'待发布','拿样中','关闭'}")
    private String sysTsTaskStatus;

    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;

    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    @ApiModelProperty("统计信息-费用合计(费用合计=支付费用合计-退款费用合计)")
    private BigDecimal sysTsTotalFee;

    @ApiModelProperty("统计信息-拿样历时天数")
    private BigDecimal sysTsSampleDuration;

    @ApiModelProperty("统计信息-拿样超时天数")
    private BigDecimal sysTsSampleTimeout;

    @ApiModelProperty("拿样任务支出费用")
    private BigDecimal sysTsPayFee;

    @ApiModelProperty("拿样任务退回费用")
    private BigDecimal sysTsRefFee;

    @ApiModelProperty("拿样任务类型")
    private String sysTsFeeType;

}
