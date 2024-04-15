package com.tadpole.cloud.operationManagement.modular.stock.vo.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 日常备货运营申请导入VO类
 * @date: 2021/11/4
 */
@Data
@ExcelIgnoreUnannotated
public class StockRecommendationAreaExcelVO implements Serializable {

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "平台")
    private String platformName;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "Team")
    private String team;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "区域")
    private String area;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "需求备货天数")
    private Integer requireDay;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "销售需求")
    private Integer salesRequire;

    @ColumnWidth(15)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "申请区域备货数量")
    private Integer applyNumber;

    @ColumnWidth(50)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @ExcelProperty(value= "申请备货原因")
    private String applyReason;

    @ColumnWidth(70)
    @ContentFontStyle(color = 10, fontHeightInPoints = 10)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;

    @ApiModelProperty("需求人")
    private String requireBy;
}
