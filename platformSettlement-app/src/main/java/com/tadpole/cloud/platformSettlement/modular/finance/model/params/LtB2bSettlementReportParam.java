package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

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
public class LtB2bSettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    private String id ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String billCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String newMatCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String category ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String productType ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String productName ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String style ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String mainMaterial ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String design ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String companyBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String fitBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String model ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String color ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String sizes ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String packing ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String version ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String type ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String salesBrand ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String orderNo ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal qty ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String currency ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal saleUsd ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal firstTripFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal fbaFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal cost ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal costAdd ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal domesticUnsalableInventory ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal profit ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal incentiveFund ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String period ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String fulfillment ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal costUnitPrice ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal taxPrice ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String customer ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String customerYear ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String newOldCustomer ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String remark ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String collectionAccount ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date collectionDate ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String paymentRemark ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String periodCalYear ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String salesman ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal revenueRation ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal peopleNum ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal peopleCost ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal saleOri ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal smallPackLogFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal serviceFee ;
 
    /**  */
    @ApiModelProperty(value = "")
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
    @ApiModelProperty(value = "")
    private String department ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String team ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal confirmStatus ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String platform ;




}