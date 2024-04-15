package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * k3银行账户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class K3BankAccountInfo {
    /**
     * 银行账号
     */
    private String bankNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行名称-全称
     */
    private String bankFullName;
    /** 科目编码 */
    private String subjectCode;

    /** 科目全名 */
    private String subjectName;
    /**
     * 科目状态
     */
    private String documentStatus;
    /**
     * 组织编码（财务编码）
     */
    private String orgCode;
}
