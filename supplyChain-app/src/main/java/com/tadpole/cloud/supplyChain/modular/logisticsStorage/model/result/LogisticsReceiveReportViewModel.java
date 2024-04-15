package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "收货差异报表结果类",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsReceiveReportViewModel implements Serializable {
    private static final long serialVersionUID = 1L;

//

    @ApiModelProperty(value = "发货日期")
    @ExcelProperty("发货日期")
    private Date lhrSendGoodDate;

    @ApiModelProperty(value = "账号")
    @ExcelProperty("账号")
    private String shopSimpleName;

    @ApiModelProperty(value = "站点")
    @ExcelProperty("站点")
    private String countryCode;


    @ApiModelProperty(value = "shipmentID")
    @ExcelProperty("shipmentID")
    private String shipmentID;

    @ApiModelProperty(value = "亚马逊接收状态")
    @ExcelProperty("亚马逊接收状态")
    private String amaRecState;

    @ApiModelProperty(value = "SKU")
    @ExcelProperty("SKU")
    private String itemSku;

    @ApiModelProperty(value = "ERP发货数量")
    @ExcelProperty("ERP发货数量")
    private Integer erpQty;

    @ApiModelProperty(value = "Shipment上传数量")
    @ExcelProperty("Shipment上传数量")
    private Integer shipmentQty;

    @ApiModelProperty(value = "亚马逊收货数量")
    @ExcelProperty("亚马逊收货数量")
    private Integer receiveQty;

    @ApiModelProperty(value = "接收差异")
    @ExcelProperty("接收差异")
    private Integer cyQty;

}
