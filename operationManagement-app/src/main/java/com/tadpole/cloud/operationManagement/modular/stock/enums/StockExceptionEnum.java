package com.tadpole.cloud.operationManagement.modular.stock.enums;

import cn.stylefeng.guns.cloud.model.enums.exp.AbstractBaseException;
import lombok.Getter;

/**
 * @author: ty
 * @description: 备货系统业务异常枚举类
 * @date: 2021/12/7
 */
@Getter
public enum StockExceptionEnum implements AbstractBaseException {
    PRODUCT_LINE_IS_BINDING(10001, "此产品线已经被运营绑定！"),
    DELETE_FAIL(10002, "删除失败！"),
    BATCH_DELETE_FAIL(10003, "批量删除失败！"),
    INSERT_PRODUCT_LINE_ERROR(10004, "产品线分配失败！");

    /**
     * 异常编码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

    StockExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 覆盖方法
    @Override
    public String toString() {
        return this.code + "_" + this.message;
    }

}
