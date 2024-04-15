package com.tadpole.cloud.platformSettlement.api.inventory.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    *
    * </p>
*
* @author S20190161
* @since 2023-06-08
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SHIPMENT_REPLACEMENTS")
public class ShipmentReplacementsResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty("")
    private Date shipmentDate;


    @ApiModelProperty("")
    private String sku;


    @ApiModelProperty("")
    private String asin;


    @ApiModelProperty("")
    private String fulfillmentCenterId;


    @ApiModelProperty("")
    private String originalFulfillmentCenterId;


    @ApiModelProperty("")
    private Integer quantity;


    @ApiModelProperty("")
    private Integer replacementReasonCode;

    @TableField(exist = false)
    private Integer replacementReasonName;


    @ApiModelProperty("")
    private String replacementAmazonOrderId;


    @ApiModelProperty("")
    private String originalAmazonOrderId;


    @ApiModelProperty("")
    private String originalTaskName;


    @ApiModelProperty("")
    private String filePath;


    @ApiModelProperty("")
    private String sysSellerId;


    @ApiModelProperty("")
    private Date createTime;


    @ApiModelProperty("")
    private String sysSite;


    @ApiModelProperty("")
    private String sysShopsName;

}
