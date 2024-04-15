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
 * 出货清单和亚马逊货件关系映射-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单和亚马逊货件关系映射-明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListBoxRecDetResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** SysDetID */
    @ApiModelProperty(value = "SysDetID")
    @ExcelProperty(value ="SysDetID")
    private BigDecimal sysDetId ;
 
    /** SysID */
    @ApiModelProperty(value = "SysID")
    @ExcelProperty(value ="SysID")
    private BigDecimal sysId ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @ExcelProperty(value ="SKU")
    private String merchantSku ;
 
    /** 上传箱号 */
    @ApiModelProperty(value = "上传箱号")
    @ExcelProperty(value ="上传箱号")
    private String packDetBoxNumUpload ;
 
    /** 原上传箱号 */
    @ApiModelProperty(value = "原上传箱号")
    @ExcelProperty(value ="原上传箱号")
    private String originPackDetBoxNumUpload ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private Integer quantity ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @ExcelProperty(value ="箱条码")
    private String packDetBoxCode ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @ExcelProperty(value ="箱号")
    private Integer packDetBoxNum ;
 
    /** 仓库名称;OwName */
    @ApiModelProperty(value = "仓库名称")
    @ExcelProperty(value ="仓库名称")
    private String owName ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @ExcelProperty(value ="货件实际状态")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @ExcelProperty(value ="系统标识字段新版导入文件")
    private String packListCode ;


}