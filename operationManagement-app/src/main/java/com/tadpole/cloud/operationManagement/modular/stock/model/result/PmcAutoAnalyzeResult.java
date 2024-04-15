package com.tadpole.cloud.operationManagement.modular.stock.model.result;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * PMC审核自动分析结果
 *
 * @author lsy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PmcAutoAnalyzeResult {

    @ApiModelProperty("k3订单id")
    private String k3orderId;// -- ,

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("team")
    private String team;

    @ApiModelProperty("是否含税:1含税，0不含税 ")
    private Integer includeTax;

    @ApiModelProperty("includeTax->1-HS,0-BHS")
    private String includeTaxName;

    @ApiModelProperty("税率")
    private String taxRate;// --税率,

    /** 下单方式 */
    @ApiModelProperty("下单方式")
    private String createOrderType;

    @ApiModelProperty("k3单据编号")
    private String billNo;//--k3单据编号,

    @ApiModelProperty("供应商编号")
    private String adviceSupplierId;// --供应编号,

    @ApiModelProperty("供应商名称")
    private String adviceSupplier;// --供应商,

    @ApiModelProperty("采购员工号")
    private String purchasePersonId;// --采购员工号,

    @ApiModelProperty("采购员")
    private String purchasePerson;// -- 采购员,

    @ApiModelProperty("k3订单的备注")
    private String note;// -- k3订单的备注,

    @ApiModelProperty("最近一次下单时间")
    private Date orderLastTime;// -- 最近一次下单时间

    @ApiModelProperty("分组字段合并Team+物料编码")
    private String  mergeGroupField;

    @ApiModelProperty("物控专员")
    private String  matControlPerson;


}
