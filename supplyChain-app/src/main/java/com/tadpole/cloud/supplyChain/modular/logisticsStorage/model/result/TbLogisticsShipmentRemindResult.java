package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * TbLogisticsShipmentRemind--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "TbLogisticsShipmentRemind--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentRemindResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** SysID */
    @ApiModelProperty(value = "SysID")
    @ExcelProperty(value ="SysID")
    private BigDecimal sysId ;
 
    /** 出货清单编码 */
    @ApiModelProperty(value = "出货清单编码")
    @ExcelProperty(value ="出货清单编码")
    private String packCode ;
 
    /** 装箱日期 */
    @ApiModelProperty(value = "装箱日期")
    @ExcelProperty(value ="装箱日期")
    private Date packDate ;
 
    /** 收仓库类型 */
    @ApiModelProperty(value = "收仓库类型")
    @ExcelProperty(value ="收仓库类型")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @ExcelProperty(value ="收货仓名称")
    private String packStoreHouseName ;
 
    /** 店铺简称账号 */
    @ApiModelProperty(value = "店铺简称账号")
    @ExcelProperty(value ="店铺简称账号")
    private String shopNameSimple ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @ExcelProperty(value ="国家编码")
    private String countryCode ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;


}