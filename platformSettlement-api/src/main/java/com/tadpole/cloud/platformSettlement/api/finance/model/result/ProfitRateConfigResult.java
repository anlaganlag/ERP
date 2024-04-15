package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 利润率参数管理响应参数
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ProfitRateConfigResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("平台")
    @ExcelProperty(value ="平台")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("事业部")
    @ExcelProperty(value ="事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    @ExcelProperty(value ="Team")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType;

    /** 广告占比 */
    @ApiModelProperty("广告占比")
    @ExcelProperty(value ="广告占比")
    private String adRateStr;

    /** 去年公摊占比 */
    @ApiModelProperty("去年公摊占比")
    @ExcelProperty(value ="去年公摊占比")
    private String shareRateStr;

    /** 平台其他费用占比（除FBA费、佣金、仓储） */
    @ApiModelProperty("平台其他费用占比（除FBA费、佣金、仓储）")
    @ExcelProperty(value ="平台其他费用占比（除FBA费、佣金、仓储）")
    private String otherRateStr;

    /** 参数 */
    @ApiModelProperty("参数")
    @ExcelProperty(value ="参数")
    private String paramStr;

    /** 目标利润率 */
    @ApiModelProperty("目标利润率")
    @ExcelProperty(value ="目标利润率")
    private String targetRateStr;

    /** 红线利润率(BI) */
    @ApiModelProperty("红线利润率(BI)")
    @ExcelProperty(value ="红线利润率(BI)")
    private String warningRateBIStr;

    /** 红线利润率(预结算) */
    @ApiModelProperty("红线利润率(预结算)")
    @ExcelProperty(value ="红线利润率(预结算)")
    private String warningRatePreSettlementStr;

    /** 生效期间 */
    @ApiModelProperty("生效期间")
    @ExcelProperty(value ="生效期间")
    private String validDate;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateBy;

    /** 导入备注 */
    @ApiModelProperty("导入备注")
    @ExcelProperty(value ="导入备注")
    private String uploadRemark;
}