package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 清关产品信息导出
 * @date: 2023/8/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TgBaseProductClearResult  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("主物料")
    private String mainMaterialCode;

    @ApiModelProperty("转换前物料编码")
    private String preMaterialCode;

    @ExcelProperty(value ="物料编码")
    @ApiModelProperty("物料编码字符")
    private String materialCodeStr;

    @ExcelProperty(value ="开票英文品名")
    @ApiModelProperty("开票品名（英文）")
    private String invoiceProNameEn;

    @ExcelProperty(value ="报关英文材质")
    @ApiModelProperty("报关材质（英文）")
    private String clearMaterialEn;

    @ExcelProperty(value ="带磁")
    @ApiModelProperty("带磁")
    private String isMagnetDesc;

    @ExcelProperty(value ="国家")
    @ApiModelProperty("国家名称")
    private String countryName;

    @ExcelProperty(value ="HSCode")
    @ApiModelProperty("HSCode")
    private String hsCode;

}
