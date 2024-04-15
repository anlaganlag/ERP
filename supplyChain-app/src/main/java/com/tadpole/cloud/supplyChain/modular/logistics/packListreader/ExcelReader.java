package com.tadpole.cloud.supplyChain.modular.logistics.packListreader;

import cn.hutool.core.util.ObjectUtil;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.BoxDimensionParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {

    // 读取packListExcel
    public List<BoxDimensionParam> readExcel(MultipartFile file) throws IOException, InvalidFormatException {

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            double totalBox = 0;
            int colDimension = 4;
            int skipCol = 12;
            List<List<Double>> listOfDimensions = new ArrayList<>();


            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == 1) {
                        String trim = cell.getStringCellValue().trim();
                        if (trim.contains("包装箱总数")) {
                            int columnIndex = cell.getColumnIndex() + 4;
                            Cell cellTotal = row.getCell(columnIndex);
                            if (cellTotal != null) {
                                if (cellTotal.getCellType() == 0) {
                                    totalBox = cellTotal.getNumericCellValue();
                                } else {
                                    System.out.println("总箱为空");
                                }
                            }
                        }
                        if (trim.contains("包装箱重量（磅）")) {
                            int row0 = cell.getRowIndex();
                            int col0 = cell.getColumnIndex();
                            for (int r = 0; r < colDimension; r++) {
                                int curRow = row0 + r;
                                Row theRow = sheet.getRow(curRow);
                                List<Double> curDimensions = new ArrayList<>();
                                if (theRow != null) {
                                    for (int c = 0; c < totalBox; c++) {
                                        int curCol = col0 + skipCol + c;
                                        Cell cellWeight = theRow.getCell(curCol);
                                        if (cellWeight != null) {
                                            if (cellWeight.getCellType() == 0) {
                                                curDimensions.add(cellWeight.getNumericCellValue());
                                            } else {
                                                System.out.println("包装数据为空");
                                            }
                                        }
                                    }

                                }
                                if (ObjectUtil.isNotEmpty(curDimensions)) {
                                    listOfDimensions.add(curDimensions);
                                }

                            }

                        }
                    }
                }
            }
            List<BoxDimensionParam> boxList = new ArrayList<>();
            Double poundToKgRatio = 0.45359237;
            Double inchToCmRatio = 2.54;
            if (ObjectUtil.isNotEmpty(listOfDimensions)) {
                List<Double> weightList = listOfDimensions.get(0);
                List<Double> widthList = listOfDimensions.get(1);
                List<Double> lengthList = listOfDimensions.get(2);
                List<Double> highList = listOfDimensions.get(3);
                for (int i = 0; i < totalBox; i++) {
                    BoxDimensionParam box = BoxDimensionParam.builder()
                            .id("P1-B" + (i + 1))
                            .weight(weightList.get(i) * poundToKgRatio)
                            .width(widthList.get(i) * inchToCmRatio)
                            .length(lengthList.get(i) * inchToCmRatio)
                            .high(highList.get(i) * inchToCmRatio)
                            .build();
                    boxList.add(box);
                }
            }
            System.out.println("\n>>>>>>>>>>>>>>总箱数:" + totalBox);        // Close the workbook and input stream
            System.out.println("\n>>>>>>>>>>>>>>箱子列表:" + boxList);

            // Close the workbook and input stream
            workbook.close();
            workbook.close();
            inputStream.close();
            return boxList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 读取部分packListExcel
    public List<BoxDimensionParam> readExcelCsv(MultipartFile file) throws IOException, InvalidFormatException {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            InputStream inputStream = file.getInputStream();


//        String encoding = "GBK";
        String encoding = "UTF-8";
            Boolean isBreak = false;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), encoding))) {
                String line;
                int rowNum = 0;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(","); // Assuming the CSV is comma-separated
                    Row row = sheet.createRow(rowNum++);

                    for (int i = 0; i < values.length; i++) {
                        Cell cell = row.createCell(i);
                        if (values[i].contains("�")) {
                            isBreak = true;
                            break;
                        }
                        cell.setCellValue(values[i].replaceAll("^\"|\"$", ""));
                    }
                    if (isBreak) {
                        break;
                    }
                }
            }
            if (isBreak) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "GBK"))) {
                    String line;
                    int rowNum = 0;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(","); // Assuming the CSV is comma-separated
                        Row row = sheet.createRow(rowNum++);
                        for (int i = 0; i < values.length; i++) {
                            Cell cell = row.createCell(i);
                            cell.setCellValue(values[i].replaceAll("^\"|\"$", ""));
                        }

                    }
                }

            }


            double totalBox = 0;
            double totalQty = 0;
            int colDimension = 5;
            int skipCol = 1;
            List<List<Double>> listOfDimensions = new ArrayList<>();
            List<String> listOfBoxNames = new ArrayList<>();


            for (Row row : sheet) {
                for (Cell cell : row) {
                    String trim = cell.getStringCellValue().trim();
                    if (trim.contains("箱子数量")) {
                        int columnIndex = cell.getColumnIndex() + 1;
                        Cell cellTotal = row.getCell(columnIndex);
                        if (cellTotal != null) {
                            String totalBoxString = cellTotal.getStringCellValue();
                            try {
                                totalBox = Double.parseDouble(totalBoxString);
                            } catch (NumberFormatException e) {
                                System.out.println("获取总箱数失败");
                            }
                            break;
                        }
                    }
                    if (trim.contains("商品数量")) {
                        int columnIndex = cell.getColumnIndex() + 1;
                        Cell cellQty = row.getCell(columnIndex);
                        if (cellQty != null) {
                            String totalQtyString = cellQty.getStringCellValue();
                            try {
                                totalQty = Double.parseDouble(totalQtyString);
                            } catch (NumberFormatException e) {
                                System.out.println("获取商品数量失败");
                            }
                            break;
                        }
                    }
                    if (trim.contains("箱子名称")) {
                        int row0 = cell.getRowIndex();
                        int col0 = cell.getColumnIndex();
                        for (int r = 0; r < colDimension; r++) {
                            int curRow = row0 + r;
                            Row theRow = sheet.getRow(curRow);
                            List<Double> curDimensions = new ArrayList<>();
                            if (theRow != null) {
                                for (int c = 0; c < totalBox; c++) {
                                    int curCol = col0 + skipCol + c;
                                    Cell cellWeight = theRow.getCell(curCol);
                                    if (cellWeight != null) {
                                        if (r == 0) {
                                            listOfBoxNames.add(cellWeight.getStringCellValue());
                                        } else {
                                            curDimensions.add(Double.parseDouble(cellWeight.getStringCellValue()));
                                        }
                                    }
                                }

                            }
                            if (ObjectUtil.isNotEmpty(curDimensions)) {
                                listOfDimensions.add(curDimensions);
                            }

                        }

                    }
                }
            }
            List<BoxDimensionParam> boxList = new ArrayList<>();
            if (ObjectUtil.isEmpty(listOfDimensions)) {
                return null;
            }
            //只取Bx部分的x数字
            listOfBoxNames = listOfBoxNames.stream()
                    .map(item -> item.split(" - ")[1].substring(1)).collect(Collectors.toList());
            Double poundToKgRatio = 0.45359237;
            Double inchToCmRatio = 2.54;
            if (ObjectUtil.isNotEmpty(listOfDimensions)) {
                List<Double> weightList = listOfDimensions.get(0);
                List<Double> widthList = listOfDimensions.get(1);
                List<Double> lengthList = listOfDimensions.get(2);
                List<Double> highList = listOfDimensions.get(3);
                for (int i = 0; i < totalBox; i++) {
                    BoxDimensionParam box = BoxDimensionParam.builder()
                            .id(i + "1")
                            .weight(weightList.get(i) * poundToKgRatio)
                            .width(widthList.get(i) * inchToCmRatio)
                            .length(lengthList.get(i) * inchToCmRatio)
                            .high(highList.get(i) * inchToCmRatio)
                            .boxNum(listOfBoxNames.get(i))
                            .boxCount(totalBox)
                            .qty(totalQty)
                            .build();
                    boxList.add(box);
                }
            }
            // 根据数字排序
            boxList = boxList.stream()
                    .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getBoxNum())))
                    .collect(Collectors.toList());
            System.out.println("\n>>>>>>>>>>>>>>总箱数:" + totalBox);        // Close the workbook and input stream
            System.out.println("\n>>>>>>>>>>>>>>商品数量:" + totalQty);        // Close the workbook and input stream
            System.out.println("\n>>>>>>>>>>>>>>箱子列表:" + boxList);        // Close the workbook and input stream
            workbook.close();
            inputStream.close();
            return boxList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
