package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ty
 * @description: 规则合并结果类
 * @date: 2023/5/30
 */
@Data
public class TgProductMergeRuleResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票品名（英文）
     */
    @ApiModelProperty(hidden = true)
    private String invoiceProNameEn;

    /**
     * 报关材质（英文）
     */
    @ApiModelProperty(hidden = true)
    private String clearMaterialEn;

    /**
     * 国家编码
     */
    @ApiModelProperty(hidden = true)
    private String countryCode;

    /**
     * 国家名称
     */
    @ApiModelProperty(hidden = true)
    private String countryName;

    /**
     * hsCode
     */
    @ApiModelProperty(hidden = true)
    private String hsCode;

    /**
     * 合并分组条数
     */
    @ApiModelProperty(hidden = true)
    private Integer groupCount;

    /**
     * 合并分组源数据ID
     */
    @ApiModelProperty(hidden = true)
    private String ids;

    /**
     * 合并分组源数据明细ID
     */
    @ApiModelProperty(hidden = true)
    private String detailIds;

    /**
     * 合并名称
     */
    @ApiModelProperty("合并名称")
    private String groupMergeName;

    /**
     * 清关品名信息集合
     */
    @ApiModelProperty("清关品名信息集合")
    private List<TgProductMergeResult> invoiceProNameCnList;

}
