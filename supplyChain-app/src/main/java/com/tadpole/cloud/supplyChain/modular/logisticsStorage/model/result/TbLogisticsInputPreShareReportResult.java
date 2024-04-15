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
 * 物流投入预估分担报告-暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流投入预估分担报告-暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsInputPreShareReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 物流价格明细编号 */
    @ApiModelProperty(value = "物流价格明细编号")
    @ExcelProperty(value ="物流价格明细编号")
    private BigDecimal pkLogisrId ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String busLogisrPlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String busLogisrShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String busLogisrCountryCode ;
 
    /** 需求部门 */
    @ApiModelProperty(value = "需求部门")
    @ExcelProperty(value ="需求部门")
    private String busLogisrDepart ;
 
    /** 需求Team */
    @ApiModelProperty(value = "需求Team")
    @ExcelProperty(value ="需求Team")
    private String busLogisrTeam ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String busLogisrMatCode ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @ExcelProperty(value ="运营大类")
    private String busLogisrMatOperateCate ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private Integer busLogisrQty ;
 
    /** 单价 */
    @ApiModelProperty(value = "单价")
    @ExcelProperty(value ="单价")
    private BigDecimal busLogisrUnitPrice ;
 
    /** 费用分摊 */
    @ApiModelProperty(value = "费用分摊")
    @ExcelProperty(value ="费用分摊")
    private BigDecimal busLogisrCostShare ;
 
    /** 发货日期 */
    @ApiModelProperty(value = "发货日期")
    @ExcelProperty(value ="发货日期")
    private Date busLhrSendGoodDate ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="创建时间")
    private Date sysCreateDate ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    @ExcelProperty(value ="创建人姓名")
    private String sysPerName ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    @ExcelProperty(value ="创建人编号")
    private String sysPerCode ;
 
    /** 计费币种 */
    @ApiModelProperty(value = "计费币种")
    @ExcelProperty(value ="计费币种")
    private String busLogComfirmBillCurrency ;


}