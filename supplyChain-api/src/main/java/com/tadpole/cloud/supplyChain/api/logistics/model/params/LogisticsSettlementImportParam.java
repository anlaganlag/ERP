package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 物流实际结算Excel导入入参类
 * @date: 2022/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsSettlementImportParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value ="发货批次号")
    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    @ExcelProperty(value ="备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelProperty(value ="导入异常备注")
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ApiModelProperty(value = "导入异常备注")
    private String uploadRemark;
}
