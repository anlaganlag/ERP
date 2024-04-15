package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 新增采购单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseAddParm {
    //api:pur-do-add-purchase

    @ApiModelProperty("仓库名称")
    private String warehouseName;                                   //仓库名称  必填

    @ApiModelProperty("供应商名称")
    private String providerName;                                    //供应商名称 必填

    @ApiModelProperty("员工名称")
    private String employeeName;                                    //员工名称 必填

    @ApiModelProperty("自定义单据号")
    private String orderBillNO;                                     //自定义单据号,最长字符20

    @ApiModelProperty("采购运费")
    private String expressMoney;                                    //采购运费

    @ApiModelProperty("到货天数")
    private String estimatedTime;                                   //到货天数

    @ApiModelProperty("快递方式")
    private String expressType;                                     //快递方式

    @ApiModelProperty("快递单号")
    private String expressId;                                       //快递单号

    @ApiModelProperty("采购备注")
    private String content;                                         //采购备注

    @ApiModelProperty("是否计算采购在途，1不计算 2计算")
    private String notCalculate;                                    //是否计算采购在途，1不计算 2计算

    @ApiModelProperty("生成的采购单将自动提交采购，1自动 2不自动")
    private String isAutoSubmitCheck;                               //生成的采购单将自动提交采购，1自动 2不自动

    @ApiModelProperty("商品信息list")
    private String stockList;

}
