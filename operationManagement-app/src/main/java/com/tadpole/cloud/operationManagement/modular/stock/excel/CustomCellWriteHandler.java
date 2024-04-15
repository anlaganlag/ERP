package com.tadpole.cloud.operationManagement.modular.stock.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomCellWriteHandler extends AbstractColumnWidthStyleStrategy implements CellWriteHandler {


    private  Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);


    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                       CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {
//        setAfterCellDataConverted(cell);
//        setAfterCellDataConverted2(cell);
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        //设置公式
//        setFormula(cell);
        setFormula2(cell);
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    private void setFormula2(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 4 ){
            return;
        }

        int colNum = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if (colNum == 63 || colNum == 73  || colNum == 84 || colNum == 85 || colNum == 86 || colNum == 87 || colNum == 88) {
            String cellValue = cell.getStringCellValue();
            cell.setCellFormula(cellValue);
            System.out.println("进入第" + cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");

        }
        }


    private void setFormula(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 4 ){
            return;
        }

        int colNum = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if (colNum == 62 || colNum == 72 || colNum == 81 || colNum == 83 || colNum == 84 || colNum == 85 || colNum == 86 || colNum == 87) {


//            System.out.println("进入第" + cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");
            int actualCellRowNum = cell.getRowIndex() + 1;
            StringBuffer buffer = new StringBuffer();
            switch (colNum) {
                case 62:
                    //备货参数：总备货天数D6 =SUM(BK4:BQ4)   ok
//                    cell.setCellFormula("SUM(BK" + actualCellRowNum + ":BQ" + actualCellRowNum+")");
                    cell.setCellFormula(buffer.append("SUM(BK").append(actualCellRowNum).append(":BQ").append(actualCellRowNum).append(")").toString());
                    break;

//                case 70:
                //备货参数：预估销售数量D6
                // =SUMIFS($AQ   5  :$AZ 5 ,$AQ$3:$AZ$3,">="&MONTH($A   5  ),$AQ$3:$AZ$3,"<"&MONTH($BT  5   ))+SUMIFS($AQ  5  :$AZ  5  ,$AQ$3:$AZ$3,">="&MONTH($BT 5 ),$AQ$3:$AZ$3,"<="&MONTH($BT 5 ))*DAY($BT 5 )/30
                // =SUMIFS($AQ   4  :$AZ 4 ,$AQ$3:$AZ$3,">="&MONTH($A   4  ),$AQ$3:$AZ$3,"<"&MONTH($BT  4   ))+SUMIFS($AQ  4  :$AZ  4  ,$AQ$3:$AZ$3,">="&MONTH($BT 4 ),$AQ$3:$AZ$3,"<="&MONTH($BT 4 ))*DAY($BT 4 )/30
                //
//                    cell.setCellFormula("SUMIFS($AQ" + actualCellRowNum + ":$AZ" + actualCellRowNum +",$AQ$3:$AZ$3,\">=\"&MONTH($A"+actualCellRowNum+"),$AQ$3:$AZ$3,\"<\"&MONTH($BT"+actualCellRowNum
//                    +"))+SUMIFS($AQ"+actualCellRowNum+":$AZ"+actualCellRowNum+",$AQ$3:$AZ$3,\">=\"&MONTH($BT"+actualCellRowNum+"),$AQ$3:$AZ$3,\"<=\"&MONTH($BT"+actualCellRowNum+"))*DAY($BT"+actualCellRowNum+")/30");
//                    break;
                case 72:
                    //备货参数:推荐备货覆盖销售日期D6 计算公式=推荐日期+总备货天数D6  =A5+BJ5   ***   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+BJ" + actualCellRowNum);break;
//                    cell.setCellFormula(buffer.append("A").append(actualCellRowNum).append("+BJ").append(actualCellRowNum).toString());
                    cell.setCellValue(buffer.append("(A").append(actualCellRowNum).append("+BJ").append(actualCellRowNum).append(")").toString());
                    break;
                case 81:
                    //运营填写：运营期望交期D6 =A5+BK5+BO5    ***   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+BK" + actualCellRowNum+"+BO"+ actualCellRowNum);break;
//                    cell.setCellFormula(buffer.append("A").append(actualCellRowNum).append("+BK").append(actualCellRowNum).append("+BO").append(actualCellRowNum).toString());
                    cell.setCellValue(buffer.append("A").append(actualCellRowNum).append("+BK").append(actualCellRowNum).append("+BO").append(actualCellRowNum).toString());
                    break;

                case 83:
                    //83列 额外备货天数  =销售需求备货天数-总备货天数D6     =CB4-BJ4   yes
                    cell.setCellFormula(buffer.append("CB").append(actualCellRowNum).append("-BJ").append(actualCellRowNum).toString());
                    break;
                case 84:
                    //" 销售需求：(设：运营需求覆盖销售日期.month()-推荐日期.month()-1 为N；)
                    //实时计算：
                    //  (【本月】销量 * (（30-推荐日期.day())/30)) +
                    //  【本月+1】销量+…+
                    //  【本月+N】销量+
                    //  (【本月+N+1】销量*运营需求覆盖销售日期.day()/30)"
                    //84列 额外备货天数=销售需求备货天数-总备货天数D6     =CB4-BJ4
                    //=SUM(OFFSET($BU 4 ,0,0,1,(MATCH(MONTH($CG 4 ),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU 4 ,0,(MATCH(MONTH($CG 4 ),$BU$1:$CB$1,0))-1,1,1))*DAY($CG 4 )/30-$BU 4 *DAY($A 4 )/30
                    //=SUM(OFFSET($BU 5 ,0,0,1,(MATCH(MONTH($CG 5 ),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU 5 ,0,(MATCH(MONTH($CG 5 ),$BU$1:$CB$1,0))-1,1,1))*DAY($CG 5 )/30-$BU 5 *DAY($A 5 )/30
                    //=SUM(OFFSET($BU 6 ,0,0,1,(MATCH(MONTH($CG 6 ),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU 6 ,0,(MATCH(MONTH($CG 6 ),$BU$1:$CB$1,0))-1,1,1))*DAY($CG 6 )/30-$BU 6 *DAY($A 6 )/30
                    //=SUM(OFFSET($BU 7 ,0,0,1,(MATCH(MONTH($CG 7 ),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU 7 ,0,(MATCH(MONTH($CG 7 ),$BU$1:$CB$1,0))-1,1,1))*DAY($CG 7 )/30-$BU 7 *DAY($A 7 )/30
//                    cell.setCellFormula("IFERROR(SUM(OFFSET($BU" + actualCellRowNum + ",0,0,1,(MATCH(MONTH($CG" + actualCellRowNum+
//                            "),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU"+actualCellRowNum+",0,(MATCH(MONTH($CG"+actualCellRowNum+
//                            "),$BU$1:$CB$1,0))-1,1,1))*DAY($CG"+actualCellRowNum+")/30-$BU"+actualCellRowNum+"*DAY($A"+actualCellRowNum+")/30,0)");
//                    break;

                    cell.setCellFormula(buffer.append("IFERROR(SUM(OFFSET($BU").append(actualCellRowNum).append(",0,0,1,(MATCH(MONTH($CG").append(actualCellRowNum).append(
                            "),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU").append(actualCellRowNum).append(",0,(MATCH(MONTH($CG").append(actualCellRowNum).append(
                            "),$BU$1:$CB$1,0))-1,1,1))*DAY($CG").append(actualCellRowNum).append(")/30-$BU").append(actualCellRowNum).append("*DAY($A").append(actualCellRowNum).append(")/30,0)").toString());
                    break;

                case 85:
                    //85列 销售需求覆盖日期   计算公式=推荐日期+销售需求备货天数  =A6+CB6 *****   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+CB" + actualCellRowNum);
//                    cell.setCellFormula(buffer.append("(A").append(actualCellRowNum).append("+CB").append(actualCellRowNum).append(")").toString());
                    cell.setCellValue(buffer.append("(A").append(actualCellRowNum).append("+CB").append(actualCellRowNum).append(")").toString());
                    break;
                case 86:
                    //86列 申请区域备货数量  =IF((CF5-Z5)<0,0,CF5-Z5)  yes
//                    cell.setCellFormula("IF((CF" + actualCellRowNum + "-Z" + actualCellRowNum+")<0,0,CF"+actualCellRowNum+"-Z"+actualCellRowNum+")");
                    cell.setCellFormula(buffer.append("IF((CF").append(actualCellRowNum).append("-Z").append(actualCellRowNum).append(")<0,0,CF").append(actualCellRowNum).append("-Z").append(actualCellRowNum).append(")").toString());
                    break;
                case 87:
                    //87列 申请区域备货后周转天数   计算公式=（申请区域备货数量+AZ海外总库存D6）/日均销量D6   =(CH4+Z4)/BF4   yes
//                    cell.setCellFormula("IFERROR(ROUND((CH" + actualCellRowNum + "+Z" + actualCellRowNum+")/BF"+actualCellRowNum+",0),99999)");
                    cell.setCellFormula(buffer.append("IFERROR(ROUND((CH").append(actualCellRowNum).append("+Z").append(actualCellRowNum).append(")/BF").append(actualCellRowNum).append(",0),99999)").toString());
                    break;
                default:
                    break;
            }
            buffer.setLength(0);
        } else {
            return;
        }
    }


    private void setAfterCellDataConverted(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 4 ){
            return;
        }

        int colNum = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if ( colNum == 72 || colNum == 81  || colNum == 85 ) {


            System.out.println("进入第" + cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");
            int actualCellRowNum = cell.getRowIndex() + 1;
            StringBuffer buffer = new StringBuffer();
            switch (colNum) {

                case 72:
                    //备货参数:推荐备货覆盖销售日期D6 计算公式=推荐日期+总备货天数D6  =A5+BJ5   ***   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+BJ" + actualCellRowNum);break;
                    cell.setCellFormula(buffer.append("(A").append(actualCellRowNum).append("+BJ").append(actualCellRowNum).append(")").toString());
                    break;
                case 81:
                    //运营填写：期望交期D6 =A5+BK5+BO5    ***   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+BK" + actualCellRowNum+"+BO"+ actualCellRowNum);break;
                    cell.setCellFormula(buffer.append("(A").append(actualCellRowNum).append("+BK").append(actualCellRowNum).append("+BO").append(actualCellRowNum).append(")").toString());
                    break;

                case 85:
                    //85列 销售需求覆盖日期   计算公式=推荐日期+销售需求备货天数  =A6+CB6 *****   no
//                    cell.setCellFormula("A" + actualCellRowNum + "+CB" + actualCellRowNum);
                    cell.setCellFormula(buffer.append("(A").append(actualCellRowNum).append("+CB").append(actualCellRowNum).append(")").toString());
                    break;
                default:
                    break;
            }
            buffer.setLength(0);
        } else {
            return;
        }

    }
    private void setAfterCellDataConverted2(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 4 ){
            return;
        }

        int colNum = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if ( colNum == 72 || colNum == 81  || colNum == 85 ) {
            cell.setCellFormula(cell.getStringCellValue());
            System.out.println("进入第" + cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");

        } else {
            return;
        }

    }
}