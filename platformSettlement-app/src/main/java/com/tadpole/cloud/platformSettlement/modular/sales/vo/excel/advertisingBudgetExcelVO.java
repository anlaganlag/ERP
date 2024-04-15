package com.tadpole.cloud.platformSettlement.modular.sales.vo.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import java.io.Serializable;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Data
@ExcelIgnoreUnannotated
public class advertisingBudgetExcelVO implements Serializable{
  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "平台")
  private String platform;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "事业部")
  private String department;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Team")
  private String team;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "运营大类")
  private String productType;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "销售品牌")
  private Integer companyBrand;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "广告占比")
  private Integer advertisingProportion;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q1-占比")
  private String seasonOneProportion;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q2-占比")
  private String seasonTwoProportion;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q3-占比")
  private String seasonThreeProportion;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q4-占比")
  private String seasonFourProportion;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q1-金额")
  private String seasonOneMoney;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q2-金额")
  private String seasonTwoMoney;


  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q3-金额")
  private String seasonThreeMoney;

  @ColumnWidth(15)
  @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
  @ExcelProperty(value= "Q4-金额")
  private String seasonFourMoney;

}
