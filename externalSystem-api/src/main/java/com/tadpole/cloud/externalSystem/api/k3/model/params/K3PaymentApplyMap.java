package com.tadpole.cloud.externalSystem.api.k3.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer2;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializerStaff;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: K3物流费付款申请单
 * @date: 2023/12/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K3PaymentApplyMap implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 数据id */
    @JSONField(name = "FID")
    private BigDecimal FID;

    /** 单据编号(JCERP生成) */
    @JSONField(name = "FBillNo", ordinal = 1)
    private String FBillNo;

    /** 单据状态(必填项) */
    @JSONField(name = "FDOCUMENTSTATUS", ordinal = 2)
    private String FDOCUMENTSTATUS;

    /** 结算组织(必填项) */
    @JSONField(name = "FSETTLEORGID", serializeUsing = TransferSerializer.class, ordinal = 3)
    private String FSETTLEORGID;

    /** 币别(必填项) */
    @JSONField(name = "FCURRENCYID", serializeUsing = TransferSerializer.class, ordinal = 4)
    private String FCURRENCYID;

    /** 申请日期(必填项) */
    @JSONField(name = "FDATE", ordinal = 5)
    private String FDATE;

    /** 单据类型(必填项) */
    @JSONField(name = "FBILLTYPEID", serializeUsing = TransferSerializer.class, ordinal = 6)
    private String FBILLTYPEID;

    /** 作废状态(必填项) */
    @JSONField(name = "FCANCELSTATUS", ordinal = 7)
    private String FCANCELSTATUS;

    /** 往来单位类型(必填项) */
    @JSONField(name = "FCONTACTUNITTYPE", ordinal = 8)
    private String FCONTACTUNITTYPE;

    /** 往来单位(必填项) */
    @JSONField(name = "FCONTACTUNIT", serializeUsing = TransferSerializer.class, ordinal = 9)
    private String FCONTACTUNIT;

    /** 收款单位类型(必填项) */
    @JSONField(name = "FRECTUNITTYPE", ordinal = 10)
    private String FRECTUNITTYPE;

    /** 收款单位(必填项) */
    @JSONField(name = "FRECTUNIT", serializeUsing = TransferSerializer.class, ordinal = 11)
    private String FRECTUNIT;

    /** 付款组织(必填项) */
    @JSONField(name = "FPAYORGID", serializeUsing = TransferSerializer.class, ordinal = 12)
    private String FPAYORGID;

    /** 汇率 */
    @JSONField(name = "FEXCHANGERATE", ordinal = 13)
    private String FEXCHANGERATE;

    /** 申请组织(必填项) */
    @JSONField(name = "FAPPLYORGID", serializeUsing = TransferSerializer.class, ordinal = 14)
    private String FAPPLYORGID;

    /** 结算币别(必填项) */
    @JSONField(name = "FSETTLECUR", serializeUsing = TransferSerializer.class, ordinal = 15)
    private String FSETTLECUR;

    /** 结算汇率 */
    @JSONField(name = "FSETTLERATE", ordinal = 16)
    private String FSETTLERATE;

    /** 供应链公司(必填项) */
    @JSONField(name = "F_BSC_Supply", serializeUsing = TransferSerializer.class, ordinal = 17)
    private String F_BSC_Supply;

    /** 摘要 */
    @JSONField(name = "F_BSC_Abstract", ordinal = 18)
    private String F_BSC_Abstract;

    /** 付款类型(必填项) */
    @JSONField(name = "F_BSC_Assistant", serializeUsing = TransferSerializer.class, ordinal = 19)
    private String F_BSC_Assistant;

    /** 是否预付 */
    @JSONField(name = "F_BSC_Text2", ordinal = 20)
    private String F_BSC_Text2;

    /** 是否均有发票 */
    @JSONField(name = "F_BSC_Combo", ordinal = 21)
    private String F_BSC_Combo;

    /** 申请人员(必填项) */
    @JSONField(name = "F_Apply_person", serializeUsing = TransferSerializerStaff.class, ordinal = 22)
    private String F_Apply_person;

    /** 是否需要补充发票(必填项) */
    @JSONField(name = "F_BSC_Combo1", ordinal = 23)
    private String F_BSC_Combo1;

    /** 创建人 */
    @JSONField(name = "FCREATORID", serializeUsing = TransferSerializer2.class, ordinal = 24)
    private String FCREATORID;

    /** 修改人 */
    @JSONField(name = "FMODIFIERID", serializeUsing = TransferSerializer2.class, ordinal = 25)
    private String FMODIFIERID;

    @JSONField(name = "FPAYAPPLYENTRY",ordinal = 26)
    private List<K3PaymentApplyItemMap> FPAYAPPLYENTRY;

}
