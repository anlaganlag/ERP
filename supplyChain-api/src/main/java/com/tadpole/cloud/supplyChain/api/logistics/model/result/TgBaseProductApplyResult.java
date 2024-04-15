package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 报关产品信息导出
 * @date: 2023/8/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TgBaseProductApplyResult  implements Serializable {

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

    @ExcelProperty(value ="海关编码")
    @ApiModelProperty("海关编码")
    private String customsCode;

    @ExcelProperty(value ="要素")
    @ApiModelProperty("要素")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    private String essentialFactorTemp;

    @ExcelProperty(value ="退税率")
    @ApiModelProperty("退税率")
    private BigDecimal taxRefund;

    @ExcelProperty(value ="毛利率")
    @ApiModelProperty("毛利率")
    private BigDecimal grossProfitRate;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

}
