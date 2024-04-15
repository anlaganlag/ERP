package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 规则合并入参
 * @date: 2023/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TgProductMergeRuleParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** detailIdList */
    @ApiModelProperty("detailId集合")
    private List<TgProductMergeIdsParam> idList;

    /** 合并条件集合 */
    @ApiModelProperty("合并条件集合")
    private List<String> mergeRuleList;

    /** 开票品名（英文） */
    @ApiModelProperty(hidden = true)
    private String invoiceProNameEn;

    /** 报关材质（英文） */
    @ApiModelProperty(hidden = true)
    private String clearMaterialEn;

    /** 国家编码 */
    @ApiModelProperty(hidden = true)
    private String countryCode;

    /** HSCode */
    @ApiModelProperty(hidden = true)
    private String hsCode;

    /** 源数据ID集合 */
    @ApiModelProperty(hidden = true)
    private List<BigDecimal> sourceIdList;
}
