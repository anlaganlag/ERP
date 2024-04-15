package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import cn.stylefeng.guns.cloud.model.enums.exp.AbstractBaseException;

/**
 * 校验失败枚举
 * 枚举命名规则：模块名缩写(3位数)_接口名缩写(3位数)_方法名缩写
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/10/13 <br>
 */
public enum VerifyExceptionEnum implements AbstractBaseException {

    FIN_COM_DEL( "已确认数据禁止批量删除"),
    FIN_COM_UPD( "数据已确认，无须重复确认"),
    FIN_MSF_DB( "期间+账号+站点必选"),
    FIN_MSF_UB( "期间+账号必选"),
    FIN_TDF_ADD( "分摊销毁费用不能大于汇总行销毁费用"),
    FIN_TDF_ADR( "分摊移除费用不能大于汇总行移除费用"),


    NULL("冗余")
    ;

    VerifyExceptionEnum(String message) {
        this.code = 504;
        this.message = message;
    }

    private final Integer code;

    private final String message;


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

