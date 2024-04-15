package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 亚马逊物流索赔;
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊物流索赔", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class DownloadLogisticsAmazonClaimsViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    // SKU
    @ApiModelProperty(value = "SKU")
    @ExcelProperty(value ="MSKU")
    public String busSKU;

    // 标题
    @ApiModelProperty(value = "title")
    @ExcelProperty(value ="Title")
    public String title;

    // FNSKU
    @ApiModelProperty(value = "FNSKU")
    @ExcelProperty(value ="FNSKU")
    public String fnsku;

    // 物流发货数量
    @ApiModelProperty(value = "物流发货数量")
    @ExcelProperty(value ="Shipped")
    public Integer busSendQty;

    // 物流接受数量
    @ApiModelProperty(value = "物流接受数量")
    @ExcelProperty(value ="Located")
    public Integer busReceiveQty;

    // 接收差异
    @ApiModelProperty(value = "接收差异")
    @ExcelProperty(value ="Discrepancy")
    public Integer busDiscrepancy;

}