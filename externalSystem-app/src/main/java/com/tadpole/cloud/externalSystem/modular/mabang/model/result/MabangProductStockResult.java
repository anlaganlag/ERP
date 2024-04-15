package com.tadpole.cloud.externalSystem.modular.mabang.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 马帮商品库存信息
    * </p>
*
* @author lsy
* @since 2023-05-23
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("MABANG_PRODUCT_STOCK")
public class MabangProductStockResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("商品SKU")
    private String stockSku;


    @ApiModelProperty("仓库编号")
    private String warehouseId;


    @ApiModelProperty("仓库名称")
    private String warehouseName;


    @ApiModelProperty("库存总数")
    private BigDecimal stockQuantity;


    @ApiModelProperty("未发货数量")
    private BigDecimal waitingQuantity;


    @ApiModelProperty("调拨在途")
    private BigDecimal allotShippingQuantity;


    @ApiModelProperty("采购在途数量")
    private BigDecimal shippingQuantity;


    @ApiModelProperty("加工在途量")
    private BigDecimal processingQuantity;


    @ApiModelProperty("fba未发货量")
    private BigDecimal fbaWaitingQuantity;


    @ApiModelProperty("仓位")
    private String gridCode;


    @ApiModelProperty("同步方式")
    private String syncType;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
