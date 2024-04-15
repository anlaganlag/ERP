package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * K3仓库物料可用库存数量查询结果
*
* @author lsy
* @since 2022-06-28
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K3AvailableResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("物料名称")
    private String productName;

    @ApiModelProperty("运营大类")
    private String productType;

    /** 可用数量 */
    @ApiModelProperty("可用数量")
    private BigDecimal qty;

    @ApiModelProperty("仓库名称")
    private String stockName;

    @ApiModelProperty("仓库ID")
    private String stockId;

    @ApiModelProperty("仓库编号")
    private String stockNo;

    @ApiModelProperty("仓位编号")
    private String stockLocId;

    /** 最近一次采购单价 */
    @ApiModelProperty("最近一次采购单价")
    private BigDecimal purchasePrice;

    /** 最近一次采购时间 */
    @ApiModelProperty("最近一次采购时间")
    private Date purchaseDate;
}