package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 清关产品合并入参
 * @date: 2023/6/2
 */
@Data
public class TgMergeProductEditParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 开票品名（中文） */
    @ApiModelProperty("开票品名（清关品名）")
    private String invoiceProNameCn;

    /** 开票品名（英文） */
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    /** 报关材质（中文） */
    @ApiModelProperty("报关材质（中文）")
    private String clearMaterialCn;

    /** 报关材质（英文） */
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    /** 明细ID */
    @ApiModelProperty("明细ID")
    private BigDecimal detailId;

    /** Amazon销售价（多个sku取最低价） */
    @ApiModelProperty("Amazon销售价（多个sku取最低价）")
    private BigDecimal amazonSalePrice;

    /** Amazon销售连接 */
    @ApiModelProperty("Amazon销售连接")
    private String amazonSaleLink;

    /** Amazon主图连接 */
    @ApiModelProperty("Amazon主图连接")
    private String amazonPictureLink;
}
