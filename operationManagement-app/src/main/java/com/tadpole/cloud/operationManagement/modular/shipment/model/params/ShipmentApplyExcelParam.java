package com.tadpole.cloud.operationManagement.modular.shipment.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 发货申请EXCEL导入
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ShipmentApplyExcelParam implements Serializable {

 private static final long serialVersionUID = 1L;



 /** 账号 */

 @ExcelProperty(value = "账号")
 private String sysShopsName;

 /** 站点 */

 @ExcelProperty(value = "站点")
 private String sysSite;

 /** SKU */

 @ExcelProperty(value = "SKU")
 private String sku;



 /** FBA号 */

 @ExcelProperty(value = "FBA号")
 private String fbaNo;

 /** 调入仓库;接收仓库 */

 @ExcelProperty(value = "调入仓库")
 private String receiveWarehouse;

 /** 发货数量 */

 @ExcelProperty(value = "发货数量")
 private BigDecimal sendQty;

 /** 发货方式;运输方式 */

 @ExcelProperty(value = "发货方式")
 private String transportationType;

 @ExcelProperty(value = "发货点")
 private String deliverypoint;

 /** UNW类型 */

 @ExcelProperty(value = "UNW类型")
 private String unwType;

 /** 备注1 */

 @ExcelProperty(value = "备注1")
 private String remark1;

 /** 备注2 */

 @ExcelProperty(value = "备注2")
 private String remark2;

 /** 备注3 */

 @ExcelProperty(value = "备注3")
 private String remark3;

}