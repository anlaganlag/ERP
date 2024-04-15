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
 * 出货清单明细2-装箱内容-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单明细2-装箱内容-金蝶+海外仓",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListDet2Result implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal packDetId ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @ExcelProperty(value ="票单号")
    private String packCode ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @ExcelProperty(value ="SKU")
    private String sku ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private Integer quantity ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String mateCode ;
 
    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    @ExcelProperty(value ="箱号上传名称")
    private String packDetBoxNumUpload ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @ExcelProperty(value ="箱号")
    private Integer packDetBoxNum ;
 
    /** FnSKU */
    @ApiModelProperty(value = "FnSKU")
    @ExcelProperty(value ="FnSKU")
    private String fnSku ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @ExcelProperty(value ="箱条码")
    private String packDetBoxCode ;
 
    /** 拣货单单号 */
    @ApiModelProperty(value = "拣货单单号")
    @ExcelProperty(value ="拣货单单号")
    private String pickListCode ;
 
    /** 需求部门 */
    @ApiModelProperty(value = "需求部门")
    @ExcelProperty(value ="需求部门")
    private String depName ;
 
    /** 需求Team */
    @ApiModelProperty(value = "需求Team")
    @ExcelProperty(value ="需求Team")
    private String teamName ;
 
    /** 需求人员 */
    @ApiModelProperty(value = "需求人员")
    @ExcelProperty(value ="需求人员")
    private String perName ;
 
    /** 建议发货方式 */
    @ApiModelProperty(value = "建议发货方式")
    @ExcelProperty(value ="建议发货方式")
    private String packSugShipMethod ;
 
    /** 调拨申请单号 */
    @ApiModelProperty(value = "调拨申请单号")
    @ExcelProperty(value ="调拨申请单号")
    private String packDirectCode ;


}