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
 * 物流体积重量;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流体积重量",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsVolumeWeightResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lvwID */
    @ApiModelProperty(value = "lvwID")
    @ExcelProperty(value ="lvwID")
    private BigDecimal lvwId ;
 
    /** sysAddDate */
    @ApiModelProperty(value = "sysAddDate")
    @ExcelProperty(value ="sysAddDate")
    private Date sysAddDate ;
 
    /** sysPerName */
    @ApiModelProperty(value = "sysPerName")
    @ExcelProperty(value ="sysPerName")
    private String sysPerName ;
 
    /** sysUpdDatetime */
    @ApiModelProperty(value = "sysUpdDatetime")
    @ExcelProperty(value ="sysUpdDatetime")
    private Date sysUpdDatetime ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @ExcelProperty(value ="货运方式2")
    private String logTraMode2 ;
 
    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    @ExcelProperty(value ="箱型")
    private String lvwBoxType ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
    @ExcelProperty(value ="体积单位")
    private String lvwUnit ;
 
    /** 体积重 */
    @ApiModelProperty(value = "体积重")
    @ExcelProperty(value ="体积重")
    private BigDecimal lvwVaule ;


}