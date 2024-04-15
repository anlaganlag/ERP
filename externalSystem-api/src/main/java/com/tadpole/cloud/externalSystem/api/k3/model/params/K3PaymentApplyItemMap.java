package com.tadpole.cloud.externalSystem.api.k3.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: K3物流费付款申请单明细
 * @date: 2023/12/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K3PaymentApplyItemMap implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 结算方式 */
    @JSONField(name="FSETTLETYPEID", serializeUsing = TransferSerializer.class, ordinal = 1)
    private String FSETTLETYPEID;

    /** 对方银行账号(必填项) */
    @JSONField(name="FEACHBANKACCOUNT", ordinal = 2)
    private String FEACHBANKACCOUNT;

    /** 申请付款金额 */
    @JSONField(name="FAPPLYAMOUNTFOR", ordinal = 3)
    private String FAPPLYAMOUNTFOR;

    /** 付款用途 */
    @JSONField(name="FPAYPURPOSEID", serializeUsing = TransferSerializer.class, ordinal = 4)
    private String FPAYPURPOSEID;

    /** 到期日(必填项) */
    @JSONField(name="FENDDATE", ordinal = 5)
    private String FENDDATE;

    /** 期望付款日期(必填项) */
    @JSONField(name="FEXPECTPAYDATE", ordinal = 6)
    private String FEXPECTPAYDATE;

    /** 对方账户名称(必填项) */
    @JSONField(name="FEACHCCOUNTNAME", ordinal = 7)
    private String FEACHCCOUNTNAME;

    /** 对方开户行(必填项) */
    @JSONField(name="FEACHBANKNAME", ordinal = 8)
    private String FEACHBANKNAME;

    /** 备注 */
    @JSONField(name="FDescription", ordinal = 9)
    private String FDescription;

    /** 未付款金额 */
    @JSONField(name="FUnpaidAmount", ordinal = 11)
    private String FUnpaidAmount;

    /** 付费事项 */
    @JSONField(name="F_BSC_Text", ordinal = 12)
    private String F_BSC_Text;

    /** 应付比例(%) */
    @JSONField(name="F_BSC_Decimal", ordinal = 13)
    private String F_BSC_Decimal;

    /** 发票代码 */
    @JSONField(name="F_BSC_CODE", ordinal = 14)
    private String F_BSC_CODE;

    /** 发票号码NO */
    @JSONField(name="F_BSC_Invoice", ordinal = 15)
    private String F_BSC_Invoice;

    /** 对账金额 */
    @JSONField(name="F_BSC_Amount", ordinal = 16)
    private String F_BSC_Amount;

    /** 开票日期 */
    @JSONField(name="F_BSC_Date", ordinal = 17)
    private String F_BSC_Date;

}
