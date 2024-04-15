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
 * 发货单数据;
 * @author : LSY
 * @date : 2024-3-18
 */
@ApiModel(value = "发货单数据",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbBscOverseasWayAnalysisNewV2Result implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String matCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @ExcelProperty(value ="FBA号")
    private String shipmentId ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @ExcelProperty(value ="运输方式")
    private String deliverType ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value ="状态")
    private String status ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @ExcelProperty(value ="货件实际状态")
    private String shipmentRealStatus ;
 
    /** 出货数量 */
    @ApiModelProperty(value = "出货数量")
    @ExcelProperty(value ="出货数量")
    private Integer deliveryNum ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @ExcelProperty(value ="同步时间")
    private Date syncTime ;


}