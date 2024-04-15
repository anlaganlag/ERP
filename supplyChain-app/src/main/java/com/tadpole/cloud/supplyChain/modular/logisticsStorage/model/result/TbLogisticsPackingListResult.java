package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 出货清单信息-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单信息-金蝶+海外仓",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @ExcelProperty(value ="票单号")
    private String packCode ;
 
    /** 装箱日期 */
    @ApiModelProperty(value = "装箱日期")
    @ExcelProperty(value ="装箱日期")
    private Date packDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    @ExcelProperty(value ="员工编号")
    private String packPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    @ExcelProperty(value ="员工姓名")
    private String packPerName ;
 
    /** 收货账号 */
    @ApiModelProperty(value = "收货账号")
    @ExcelProperty(value ="收货账号")
    private String shopNameSimple ;
 
    /** 收货站点 */
    @ApiModelProperty(value = "收货站点")
    @ExcelProperty(value ="收货站点")
    private String countryCode ;
 
    /** 票单上传状态 */
    @ApiModelProperty(value = "票单上传状态")
    @ExcelProperty(value ="票单上传状态")
    private String packUploadState ;
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
    @ExcelProperty(value ="物流状态")
    private String packLogState ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @ExcelProperty(value ="最后更新日期")
    private Date sysUpdDatetime ;
 
    /** Shipment索赔状态 */
    @ApiModelProperty(value = "Shipment索赔状态")
    @ExcelProperty(value ="Shipment索赔状态")
    private String packShipmentClaimState ;
 
    /** 收货仓类型 */
    @ApiModelProperty(value = "收货仓类型")
    @ExcelProperty(value ="收货仓类型")
    private String packStoreHouseType ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @ExcelProperty(value ="收货仓名称")
    private String packStoreHouseName ;
 
    /** 异常状态 */
    @ApiModelProperty(value = "异常状态")
    @ExcelProperty(value ="异常状态")
    private Integer packAbnormalStatus ;
 
    /** 异常原因 */
    @ApiModelProperty(value = "异常原因")
    @ExcelProperty(value ="异常原因")
    private String packAbnormalReason ;
 
    /** 无用 */
    @ApiModelProperty(value = "无用")
    @ExcelProperty(value ="无用")
    private String shipmentId ;
 
    /** isNormal;无用 */
    @ApiModelProperty(value = "isNormal")
    @ExcelProperty(value ="isNormal")
    private Integer isNormal ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @ExcelProperty(value ="出货仓类型")
    private String comWarehouseType ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @ExcelProperty(value ="出货仓名称")
    private String comWarehouseName ;
 
    /** FBA回传日期 */
    @ApiModelProperty(value = "FBA回传日期")
    @ExcelProperty(value ="FBA回传日期")
    private Date fbaTurnDate ;
 
    /** 出货平台 */
    @ApiModelProperty(value = "出货平台")
    @ExcelProperty(value ="出货平台")
    private String comShopPlatName ;
 
    /** 出货账号 */
    @ApiModelProperty(value = "出货账号")
    @ExcelProperty(value ="出货账号")
    private String comShopNameSimple ;
 
    /** 出货站点 */
    @ApiModelProperty(value = "出货站点")
    @ExcelProperty(value ="出货站点")
    private String comCountryCode ;
 
    /** 收货平台 */
    @ApiModelProperty(value = "收货平台")
    @ExcelProperty(value ="收货平台")
    private String shopPlatName ;
 
    /** 发货点 */
    @ApiModelProperty(value = "发货点")
    @ExcelProperty(value ="发货点")
    private String deliveryPointName ;
 
    /** 业务类型 */
    @ApiModelProperty(value = "业务类型")
    @ExcelProperty(value ="业务类型")
    private String billType ;
 
    /** 总重量 */
    @ApiModelProperty(value = "总重量")
    @ExcelProperty(value ="总重量")
    private BigDecimal packTotalWeight ;
 
    /** 总体积 */
    @ApiModelProperty(value = "总体积")
    @ExcelProperty(value ="总体积")
    private BigDecimal packTotalVolume ;
 
    /** 总体积重 */
    @ApiModelProperty(value = "总体积重")
    @ExcelProperty(value ="总体积重")
    private BigDecimal packTotalVolumeWeight ;


    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal sysId ;

    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    private String shipTo ;

    /** 亚马逊接收状态 */
    @ApiModelProperty(value = "亚马逊接收状态")
    private String amaRecState ;

    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    private String shipmentRealStatus ;

    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    private String packListCode ;


}