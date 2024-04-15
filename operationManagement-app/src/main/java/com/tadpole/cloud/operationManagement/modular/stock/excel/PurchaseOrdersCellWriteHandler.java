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
public class PurchaseOrdersCellWriteHandler extends AbstractColumnWidthStyleStrategy implements CellWriteHandler {


    private  Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);


    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                       CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        setValue(cell);
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        //设置公式
        setFormula(cell);
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }


    private void setValue(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 2 ){
            return;
        }

        int cellNo = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if (cellNo == 67) {
            int rowNo = cell.getRowIndex()+1;
            StringBuffer buffer = new StringBuffer();
            //        申请备货后周转天数    =实时计算：（AZ海外总库存AA+国内可用库存AD+采购未完成数量AE+申请审核中数量AF+采购申请数量BM)/日均销量AU
            //=IFERROR(ROUND(SUM(AA2,AD2,AE2,AF2,BM2)/AU2,0),99999)
            String valueStr = buffer.append("IFERROR(ROUND(SUM(AB").append(rowNo).append(",AE").append(rowNo).append(",AF")
                    .append(rowNo).append(",AG").append(rowNo).append(",BN").append(rowNo).append(")/AV").append(rowNo).append(",0),99999)").toString();
//            String stringCellValue = cell.getStringCellValue();
            cell.setCellValue(valueStr);
        }
    }


    private void setFormula(Cell cell) {
        // 这里可以对cell进行任何操作
        //需要设置公式的行开始
        if (cell.getRowIndex() < 2 ){
            return;
        }

        int cellNo = cell.getColumnIndex() + 1;
        //需要设置公式的列
        if (cellNo == 67) {
            int rowNo = cell.getRowIndex()+1;
//            String cellValue = cell.getStringCellValue();
            StringBuffer buffer = new StringBuffer();
            //        申请备货后周转天数    =实时计算：（AZ海外总库存AA+国内可用库存AD+采购未完成数量AE+申请审核中数量AF+采购申请数量BM)/日均销量AU
            //=IFERROR(ROUND(SUM(AA3,AD3,AE3,AF3,BM3)/AU3,0),99999)
            String valueStr = buffer.append("IFERROR(ROUND(SUM(AB").append(rowNo).append(",AE").append(rowNo).append(",AF")
                    .append(rowNo).append(",AG").append(rowNo).append(",BN").append(rowNo).append(")/AV").append(rowNo).append(",0),99999)").toString();
//            cell.setCellValue(valueStr);
//            String stringCellValue = cell.getStringCellValue();
            cell.setCellFormula(valueStr);
//            cell.setCellFormula(cellValue);

            System.out.println("进入第" + cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");

        }
    }

}