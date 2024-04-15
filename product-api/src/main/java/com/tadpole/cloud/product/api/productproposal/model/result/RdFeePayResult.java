package com.tadpole.cloud.product.api.productproposal.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class RdFeePayResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请编号")
    private String sysFeeAppCode;

    @ApiModelProperty("支付类型")
    private String sysFeePayType;

    @ApiModelProperty("审批日期/退货日期")
    private Date sysDate;

    @ApiModelProperty("采购负责人编号")
    private String sysPurPerCode;

    @ApiModelProperty("采购负责人姓名")
    private String sysPurPerName;

    @ApiModelProperty("申请信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysFeePaySc;

    @ApiModelProperty("申请信息-费用合计")
    private BigDecimal sysFeePayTotalFee;

    @ApiModelProperty("操作类型")
    private String sysFeeOprType;

}
