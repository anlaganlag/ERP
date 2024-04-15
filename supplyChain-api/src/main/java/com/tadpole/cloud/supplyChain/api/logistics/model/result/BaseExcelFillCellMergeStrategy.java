package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 动态合并单元格
 * @date: 2023/6/30
 */
@NoArgsConstructor
public class BaseExcelFillCellMergeStrategy implements CellWriteHandler {

    /**
     * 合并类型 0：单元格合并行（向上合并），1：单元格合并列（向左合并）
     */
    private int mergeType;

    /**
     * 模板列表【单元格合并行】标识的列数
     */
    private int signColumnIndex;

    /**
     * 需要合并的列
     */
    private int[] mergeColumnIndex;

    /**
     * 开始合并的行号
     */
    private int mergeRowIndex;

    /**
     * 向左合并单元格的信息：Map<合并单元格的列下标, 向左合并单元格数>
     */
    private List<Map<Integer, Integer>> mergeColumnList;


    /**
     * 动态合并单元格
     * @param mergeType 合并类型 0：单元格合并行（向上合并），1：单元格合并列（向左合并）
     * @param signColumnIndex 模板列表【单元格合并行】标识的列数
     * @param mergeColumnIndex 需要合并的列（需要向上合并单元格的列数）
     * @param mergeRowIndex 开始合并的行号
     * @param mergeColumnList 向左合并单元格的信息：Map<合并单元格的列下标, 向左合并单元格数>
     */
    public BaseExcelFillCellMergeStrategy(int mergeType, int signColumnIndex, int[] mergeColumnIndex, int mergeRowIndex, List<Map<Integer, Integer>> mergeColumnList) {
        this.mergeType = mergeType;
        this.signColumnIndex = signColumnIndex;
        this.mergeColumnIndex = mergeColumnIndex;
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnList = mergeColumnList;
    }


    /**
     * 根据标识判断当前单元格向上合并
     *
     * @param writeSheetHolder 当前上下文
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        //获取上一行的最后一个单元格的数据，1：向上合并单元格，非1：不用向上合并单元格
        Row preRow = cell.getSheet().getRow(curRowIndex - 1);
        short preLastCellNum = preRow.getLastCellNum();
        Cell preCell = preRow.getCell(preLastCellNum - 1);
        String isMergeUpCell = preCell.getStringCellValue();
        //向上合并单元格
        if("1".equals(isMergeUpCell)){
            cell.setCellType(CellType.BLANK);
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }

    /**
     * 根据标识判断当前单元格向左合并
     *
     * @param writeSheetHolder 当前上下文
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     * @param mergeColumnSize      向左合并单元格数
     */
    private void mergeWithPrevCol(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex, int mergeColumnSize) {
        //获取上一行的最后一个单元格的数据，1：向左合并单元格，非1：不用向左合并单元格
        Row preRow = cell.getSheet().getRow(curRowIndex - 1);
        short preLastCellNum = preRow.getLastCellNum();
        Cell preCell = preRow.getCell(preLastCellNum - 1);
        String isMergeUpCell = preCell.getStringCellValue();
        if(curRowIndex == mergeRowIndex || "1".equals(isMergeUpCell)){
            cell.setCellType(CellType.BLANK);
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex, curColIndex - mergeColumnSize)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex, curRowIndex, curColIndex - mergeColumnSize, curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }

    /**
     * 根据标识判断当前单元格向上合并
     *
     * @param writeSheetHolder 当前上下文
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     */
    private void mergeWithPrevColTemp(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        //每行的curColIndex=2的都需要向前合并1个单元格
        if(curRowIndex >= mergeRowIndex){
            if(curColIndex == 2 || curColIndex == 6){
                cell.setCellType(CellType.BLANK);
                int mergeCell = 0;
                if(curColIndex == 2){
                    mergeCell = 1;
                }
                if(curColIndex == 6){
                    mergeCell = 3;
                }
                Sheet sheet = writeSheetHolder.getSheet();
                List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
                boolean isMerged = false;
                for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                    CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                    // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                    if (cellRangeAddr.isInRange(curRowIndex, curColIndex - mergeCell)) {
                        sheet.removeMergedRegion(i);
                        cellRangeAddr.setLastRow(curRowIndex);
                        sheet.addMergedRegion(cellRangeAddr);
                        isMerged = true;
                    }
                }
                // 若上一个单元格未被合并，则新增合并单元
                if (!isMerged) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex, curRowIndex, curColIndex - mergeCell, curColIndex);
                    sheet.addMergedRegion(cellRangeAddress);
                }
            }
        }
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();

        //0：单元格合并行（向上合并）
        if(mergeType == 0){
            if (curRowIndex > mergeRowIndex) {
                //处理合并单元格
                for (int columnIndex : mergeColumnIndex) {
                    if (curColIndex == columnIndex) {
                        mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                        break;
                    }
                }

                Row preRow = cell.getSheet().getRow(curRowIndex - 1);
                short lastCellNum = preRow.getLastCellNum();

                if(lastCellNum == curColIndex + 1){
                    //移除第一列单元格合并标识
                    preRow.removeCell(preRow.getCell(curColIndex));
                }
            }

            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            Row curRow = cell.getSheet().getRow(curRowIndex);
            if (signColumnIndex == curColIndex + 1 && "-1" == cell.getStringCellValue()) {
                curRow.removeCell(cell);
            }
        }

        //单元格合并列（向左合并）
        if(mergeType == 1){
            if (curRowIndex >= mergeRowIndex) {
                //处理合并单元格
                for (Map<Integer, Integer> mergeColumnMap : mergeColumnList) {
                    Integer mergeColumnSize = mergeColumnMap.get(curColIndex);
                    if (mergeColumnSize != null) {
                        mergeWithPrevCol(writeSheetHolder, cell, curRowIndex, curColIndex, mergeColumnSize);
                        break;
                    }
                }

                Row preRow = cell.getSheet().getRow(curRowIndex - 1);
                short lastCellNum = preRow.getLastCellNum();
                if(lastCellNum == curColIndex + 1 && lastCellNum == signColumnIndex){
                    //移除第一列单元格合并标识
                    preRow.removeCell(preRow.getCell(curColIndex));
                }
            }

            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            Row curRow = cell.getSheet().getRow(curRowIndex);
            if (signColumnIndex == curColIndex + 1 && "-1" == cell.getStringCellValue()) {
                curRow.removeCell(cell);
            }
        }

        //单元格合并列（向上合并、向左合并同时存在），先写死，后面有需要再优化
        if(mergeType == 2){
            //处理合并列
            if (curColIndex == 2 || curColIndex == 6) {
                mergeWithPrevColTemp(writeSheetHolder, cell, curRowIndex, curColIndex);
            }

            if (curRowIndex > mergeRowIndex) {
                //处理合并单元格
                for (int columnIndex : mergeColumnIndex) {
                    if (curColIndex == columnIndex) {
                        mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                        break;
                    }
                }

                Row preRow = cell.getSheet().getRow(curRowIndex - 1);
                short lastCellNum = preRow.getLastCellNum();
                if(lastCellNum == curColIndex + 1){
                    //移除第一列单元格合并标识
                    preRow.removeCell(preRow.getCell(curColIndex));
                }
            }

            //处理最后一行且最后一单元格的数据，需删除合并标识单元格
            Row curRow = cell.getSheet().getRow(curRowIndex);
            if (signColumnIndex == curColIndex + 1 && "-1" == cell.getStringCellValue()) {
                curRow.removeCell(cell);
            }
        }
    }
}
