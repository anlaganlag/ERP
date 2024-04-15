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
 * 物流调拨记录;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流调拨记录",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsTransferRecordResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal sysId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;
 
    /** 调拨方向 */
    @ApiModelProperty(value = "调拨方向")
    @ExcelProperty(value ="调拨方向")
    private String transfersDirection ;
 
    /** 调用类型 */
    @ApiModelProperty(value = "调用类型")
    @ExcelProperty(value ="调用类型")
    private String transfersType ;
 
    /** 调拨来源 */
    @ApiModelProperty(value = "调拨来源")
    @ExcelProperty(value ="调拨来源")
    private String transfersSource ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @ExcelProperty(value ="操作人")
    private String transfersOperator ;
 
    /** 操作人编码 */
    @ApiModelProperty(value = "操作人编码")
    @ExcelProperty(value ="操作人编码")
    private String transfersOperatorCode ;
 
    /** 调出组织编码 */
    @ApiModelProperty(value = "调出组织编码")
    @ExcelProperty(value ="调出组织编码")
    private String transferOutOrganizeCode ;
 
    /** 调出组织名称 */
    @ApiModelProperty(value = "调出组织名称")
    @ExcelProperty(value ="调出组织名称")
    private String transferOutOrganizeName ;
 
    /** 调入组织编码 */
    @ApiModelProperty(value = "调入组织编码")
    @ExcelProperty(value ="调入组织编码")
    private String transferInOrganizeCode ;
 
    /** 调入组织名称 */
    @ApiModelProperty(value = "调入组织名称")
    @ExcelProperty(value ="调入组织名称")
    private String transferInOrganizeName ;
 
    /** 调拨时间 */
    @ApiModelProperty(value = "调拨时间")
    @ExcelProperty(value ="调拨时间")
    private Date transferDate ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value ="状态")
    private String status ;


}