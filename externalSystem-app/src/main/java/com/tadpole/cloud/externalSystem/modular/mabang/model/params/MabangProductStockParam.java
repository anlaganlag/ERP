package com.tadpole.cloud.externalSystem.modular.mabang.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
@TableName("MABANG_PRODUCT_STOCK")
public class MabangProductStockParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 商品SKU */
    @ApiModelProperty("商品SKU")
    private String stockSku;

    /** 仓库编号 */
    @ApiModelProperty("仓库编号")
    private String warehouseId;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** 库存总数 */
    @ApiModelProperty("库存总数")
    private BigDecimal stockQuantity;

    /** 未发货数量 */
    @ApiModelProperty("未发货数量")
    private BigDecimal waitingQuantity;

    /** 调拨在途 */
    @ApiModelProperty("调拨在途")
    private BigDecimal allotShippingQuantity;

    /** 采购在途数量 */
    @ApiModelProperty("采购在途数量")
    private BigDecimal shippingQuantity;

    /** 加工在途量 */
    @ApiModelProperty("加工在途量")
    private BigDecimal processingQuantity;

    /** fba未发货量 */
    @ApiModelProperty("fba未发货量")
    private BigDecimal fbaWaitingQuantity;

    /** 仓位 */
    @ApiModelProperty("仓位")
    private String gridCode;

    /** 同步方式 */
    @ApiModelProperty("同步方式")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
