package com.tadpole.cloud.platformSettlement.api.finance.model.result;

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
 * ;
 * @author : LSY
 * @date : 2023-12-25
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtB2bSettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    private String id ;

    @ExcelProperty(value ="平台")
    private String platform;


    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String billCode ;

    @ApiModelProperty(value = "")
    @ExcelProperty(value ="事业部")
    private String department ;

    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="Team")
    private String team ;


    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String newMatCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String category ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String productType ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String productName ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String style ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String mainMaterial ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String design ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String companyBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String fitBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String model ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String color ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String sizes ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String packing ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String version ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String type ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String salesBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String orderNo ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal qty ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String currency ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal saleUsd ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal firstTripFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal fbaFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal cost ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal costAdd ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal domesticUnsalableInventory ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal profit ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal incentiveFund ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String period ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String fulfillment ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal costUnitPrice ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal taxPrice ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String customer ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String customerYear ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String newOldCustomer ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String remark ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String collectionAccount ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date collectionDate ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String paymentRemark ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String periodCalYear ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String salesman ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal revenueRation ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal peopleNum ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal peopleCost ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal saleOri ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal smallPackLogFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal serviceFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private BigDecimal realCollection ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String createBy ;
 
    /**  */

    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal confirmStatus ;
 
    /**  */



}