package com.tadpole.cloud.product.api.productproposal.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提案-开发样任务 出参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class RdSampleTaskTimeAndCostResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("提案编号")
    private String sysTaCode;

    @ApiModelProperty("拿样任务编号")
    private String sysTsTaskCode;

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
