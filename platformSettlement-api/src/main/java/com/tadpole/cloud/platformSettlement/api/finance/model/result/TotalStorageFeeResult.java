package com.tadpole.cloud.platformSettlement.api.finance.model.result;

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
* 仓储费合计 数据来源 定期生成
* </p>
*
* @author S20190161
* @since 2022-10-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TOTAL_STORAGE_FEE")
public class TotalStorageFeeResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("")
    private String sysShopsName;

    @ApiModelProperty("")
    private String sysSite;

    @ApiModelProperty("")
    private String currency;

    @ApiModelProperty("结算报告 月仓储费")
    private BigDecimal billStorageFee;

    @ApiModelProperty("结算报告 长期仓储费")
    private BigDecimal billStorageLongFee;

    @ApiModelProperty("结算报告 超库容费")
    private BigDecimal billStorageOverageFee;

    @ApiModelProperty("结算报告 合计仓储费")
    private BigDecimal billTotalfee;

    @ApiModelProperty("原报告 超库容费")
    private BigDecimal storageOverageFee;

    @ApiModelProperty("原报告 月仓储费")
    private BigDecimal storageFee;

    @ApiModelProperty("原报告 长期仓储费")
    private BigDecimal storageLongFee;

    @ApiModelProperty("原报告 合计仓储费")
    private BigDecimal totalFee;

    @ApiModelProperty("结算报告-原报告=差异月仓储费")
    private BigDecimal diffStorageFee;

    @ApiModelProperty("结算报告-原报告=差异长期仓储费")
    private BigDecimal diffStoragelongFee;

    @ApiModelProperty("结算报告-原报告=差异 超库容费")
    private BigDecimal diffStorageOverageFee;

    @ApiModelProperty("结算报告-原报告=差异 合计仓储费")
    private BigDecimal diffTotalFee;

    @ApiModelProperty("期间(冗余)")
    private Date duration;

    @ApiModelProperty("数据生成时间 ")
    private Date craeteTime;

}
