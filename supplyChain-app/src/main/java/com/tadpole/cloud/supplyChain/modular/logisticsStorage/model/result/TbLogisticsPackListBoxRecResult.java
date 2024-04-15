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
 * 出货清单和亚马逊货件关系映射表;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单和亚马逊货件关系映射表",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListBoxRecResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal sysId ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @ExcelProperty(value ="出货仓类型")
    private String comWarehouseType ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @ExcelProperty(value ="出货仓名称")
    private String owName ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @ExcelProperty(value ="收货仓名称")
    private String shipTo ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
 
    /** 亚马逊接收状态 */
    @ApiModelProperty(value = "亚马逊接收状态")
    @ExcelProperty(value ="亚马逊接收状态")
    private String amaRecState ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @ExcelProperty(value ="最后更新日期")
    private Date sysUpdateDate ;
 
    /** CreateTime */
    @ApiModelProperty(value = "CreateTime")
    @ExcelProperty(value ="CreateTime")
    private Date createTime ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @ExcelProperty(value ="货件实际状态")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @ExcelProperty(value ="系统标识字段新版导入文件")
    private String packListCode ;


}