package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 出货清单报备结果类
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单报备结果类",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentReportResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回传日期")
    @ExcelIgnore
    Date fbaTurnDate;

    @ApiModelProperty(value = "回传日期字符串形式")
    @ExcelProperty("回传日期")
    String fbaTurnDateStr;

    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty("出货清单号")
    String packCode;

    @ApiModelProperty(value = "账号")
    @ExcelProperty("账号")
    String shopNameSimple ;

    @ApiModelProperty(value = "站点")
    @ExcelProperty("站点")
    String countryCode ;

    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty("ShipmentID")
    String shipmentID;

    @ApiModelProperty(value = "收货仓名称")
    @ExcelProperty("收货仓名称")
    String shipTo;

    @ApiModelProperty(value = "出货仓")
    @ExcelProperty("出货仓")
    String comWarehouseName;

    @ApiModelProperty(value = "收货仓")
    @ExcelProperty("收货仓")
    String packStoreHouseName ;

    @ApiModelProperty(value = "箱件总数")
    @ExcelProperty("箱件总数")
    Integer boxSum=0;

    @ApiModelProperty(value = "产品总数")
    @ExcelProperty("产品总数")
    Integer productSum=0 ;

    @ApiModelProperty(value = "已发箱件数")
    @ExcelProperty("已发箱件数")
    Integer shippedBox =0;

    @ApiModelProperty(value = "未发箱件数")
    @ExcelProperty("未发箱件数")
    Integer unShippedBox=0;

    @ApiModelProperty(value = "已发产品数")
    @ExcelProperty("已发产品数")
    Integer shippedProduct=0 ;

    @ApiModelProperty(value = "未发产品数")
    @ExcelProperty("未发产品数")
    Integer unShippedProduct=0 ;

    @ApiModelProperty(value = "退回箱件数")
    @ExcelProperty("退回箱件数")
    Integer returnBox=0;

    @ApiModelProperty(value = "退回产品数")
    @ExcelProperty("退回产品数")
    Integer returnProduct=0;


}
