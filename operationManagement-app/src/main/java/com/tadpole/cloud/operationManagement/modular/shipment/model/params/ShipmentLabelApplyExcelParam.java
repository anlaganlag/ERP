package com.tadpole.cloud.operationManagement.modular.shipment.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 发货标签打印申请EXCEL导入
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ShipmentLabelApplyExcelParam implements Serializable {

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

 /** 发货数量 */

 @ExcelProperty(value = "条码数量")
 private Integer printQty;


 @ExcelProperty(value = "发货方式")
 private String transportationType;
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