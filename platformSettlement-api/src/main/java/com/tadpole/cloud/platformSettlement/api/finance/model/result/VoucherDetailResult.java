package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
* 回款确认凭证明细
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class VoucherDetailResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 摘要 */
    @TableField("FEXPLANATION")
    private String FEXPLANATION;

    /** 科目编码 */
    @TableField("FACCOUNTID")
    private String FACCOUNTID;

    /** 币别 */
    @TableField("FCURRENCYID")
    private String FCURRENCYID;

    /** 汇率类型 */
    @TableField("FEXCHANGERATETYPE")
    private String FEXCHANGERATETYPE;

    /** 汇率 */
    @TableField("FEXCHANGERATE")
    private BigDecimal FEXCHANGERATE;

    /** 贷方金额 */
    @TableField("FUnitId")
    private String FUnitId;

    /** 贷方金额 */
    @TableField("FPrice")
    private BigDecimal FPrice;

    /** 原币金额 */
    @TableField("FQty")
    private BigDecimal FQty;

    /** 原币金额 */
    @TableField("FAMOUNTFOR")
    private BigDecimal FAMOUNTFOR;

    /** 借方金额 */
    @TableField("FDEBIT")
    private BigDecimal FDEBIT;

    /** 贷方金额 */
    @TableField("FCREDIT")
    private BigDecimal FCREDIT;

    /** 贷方金额 */
    @TableField("FSettleTypeID")
    private String FSettleTypeID;

    /** 贷方金额 */
    @TableField("FSETTLENO")
    private String FSETTLENO;

    /** 贷方金额 */
    @TableField("FBUSNO")
    private String FBUSNO;

    /** 组织代码 */
    @TableField("SHOPCODE")
    private String SHOPCODE;

    /** 核算维度 */
    private List FHSData;
}