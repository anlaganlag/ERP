package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProductDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 产品基本信息编辑入参
 * @date: 2023/5/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TgBaseProductEditParam extends BaseRequest implements Serializable, BaseValidatingParam {

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

    /** 海关编码 */
    @ApiModelProperty("海关编码")
    private String customsCode;

    /** 是否带磁 0：否，1：是 */
    @ApiModelProperty("是否带磁 0：否，1：是")
    private String isMagnet;

    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    private String essentialFactorTemp;

    @ApiModelProperty("退税率")
    private BigDecimal taxRefund;

    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    @ApiModelProperty("产品明细信息集合")
    private List<TgBaseProductDetail> detailList;

    @ApiModelProperty("备注")
    private String remark;
}
