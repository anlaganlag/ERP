package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 结算报告审核状态
 * @date: 2021/10/30
 */
@Getter
public enum SettlementReportCheckStatus {

    WSH(0, "未审核"),
    YSH(1, "已审核"),
    WTG(2, "未通过" );

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    SettlementReportCheckStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static int getEnum(String value) {
        SettlementReportCheckStatus resultEnum = null;
        Integer code = null;
        SettlementReportCheckStatus[] enumAry = SettlementReportCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals("value")) {
                resultEnum = enumAry[i];
                code = resultEnum.getCode();
                break;
            }
        }
        return code;
    }
}
