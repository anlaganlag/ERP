package com.tadpole.cloud.supplyChain.modular.logistics.utils;

/**
 * todo
 *
 * @author:idea
 * @version:2022/8/11 15:43
 */

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelAnalysisHelper<T>{

    public List<T> getList(MultipartFile file, Class<T> clazz) {
        return getList(file, clazz, 0, 1);
    }

    public List<T> getList(MultipartFile file, Class<T> clazz, Integer sheetNo, Integer headRowNumber) {
        EasyExcelListener<T> listener = new EasyExcelListener<>(headRowNumber);
        try {
            EasyExcel.read(file.getInputStream(), clazz, listener).extraRead(CellExtraTypeEnum.MERGE).sheet(sheetNo).headRowNumber(headRowNumber).doRead();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        List<CellExtra> extraMergeInfoList = listener.getExtraMergeInfoList();
        if (CollectionUtil.isEmpty(extraMergeInfoList)) {
            return listener.getData();
        }
        List<T> data = explainMergeData(listener.getData(), extraMergeInfoList, headRowNumber, file);
        return data;
    }


    /**
     * 处理合并单元格
     *
     * @param data               解析数据
     * @param extraMergeInfoList 合并单元格信息
     * @param headRowNumber      起始行
     * @return 填充好的解析数据
     */
    private List<T> explainMergeData(List<T> data, List<CellExtra> extraMergeInfoList, Integer headRowNumber, MultipartFile file) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            XSSFRow row = sheetAt.getRow(headRowNumber - 1);
            List<String> columnNames = new ArrayList<>();
            for (int rowNum = 0; rowNum <= row.getLastCellNum() - 1; rowNum++) {
                columnNames.add(row.getCell(rowNum).getStringCellValue());
            }
            //循环所有合并单元格信息
            extraMergeInfoList.forEach(cellExtra -> {
                int firstRowIndex = cellExtra.getFirstRowIndex() - headRowNumber;
                int lastRowIndex = cellExtra.getLastRowIndex() - headRowNumber;
                int firstColumnIndex = cellExtra.getFirstColumnIndex();
                int lastColumnIndex = cellExtra.getLastColumnIndex();
                //获取初始值
                if (firstRowIndex < data.size()) {
                    Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, data, columnNames);
                    //设置值
                    for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                        for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                            setInitValueToList(initValue, i, j, data, columnNames);
                        }
                    }
                }
            });
        } catch (IOException e) {
            log.error("文件【{}】解析失败【{}】", file, e);
        }
        return data;
    }

    /**
     * 设置合并单元格的值
     *
     * @param filedValue  值
     * @param rowIndex    行
     * @param columnIndex 列
     * @param data        解析数据
     */
    public void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<T> data, List<String> columnNames) {
        T object = data.get(rowIndex);

        for (Field field : object.getClass().getDeclaredFields()) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String names = "";
                for (String s : annotation.value()) {
                    names = names + s;
                }
                int index = columnNames.indexOf(names);
                if (index == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("解析数据时发生异常,请联系管理员！");
                    }
                }
            }
        }
    }


    /**
     * 获取合并单元格的初始值
     * rowIndex对应list的索引
     * columnIndex对应实体内的字段
     *
     * @param firstRowIndex    起始行
     * @param firstColumnIndex 起始列
     * @param data             列数据
     * @param nameList         excel表头名称
     * @return 初始值
     */
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<T> data, List<String> nameList) {
        Object filedValue = null;
        T object = data.get(firstRowIndex);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            //提升反射性能，关闭安全检查
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String names = "";
                for (String s : annotation.value()) {
                    names = names + s;
                }
                int index = nameList.indexOf(names);
                if (index == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("解析数据时发生异常,请联系管理员！");
                    }
                }
            }
        }
        return filedValue;
    }
}
