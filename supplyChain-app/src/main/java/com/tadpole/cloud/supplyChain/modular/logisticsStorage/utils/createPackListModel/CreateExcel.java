package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.vo.BaseEntityTypeConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CreateExcel {
    private Integer boxTotal;
    private Integer skuTotal;
    private String  shipmentId;
    private String  shipmentName;
    private String  warehouseCode;
    private List<AmzBoxInfoDTO> boxInfoList = new ArrayList<>();
    private List<AmzBoxSkuInfoDTO> boxSkuInfoList = new ArrayList<>();

    public CreateExcel() {
    }

    public CreateExcel(AmzInBoundShipmentDTO amzInboundShipment, List<AmzBoxInfoDTO> boxInfoList, List<AmzBoxSkuInfoDTO> boxSkuInfoList) {
        this.boxInfoList = boxInfoList;
        this.boxSkuInfoList = boxSkuInfoList;
        this.boxTotal = amzInboundShipment.getMaxBoXnum();
        this.skuTotal = amzInboundShipment.getToTalSkus();
        this.shipmentId = amzInboundShipment.getShipmentId();
        this.shipmentName = amzInboundShipment.getBusShipmentName();
        this.warehouseCode = amzInboundShipment.getShipTo();
    }

    public String create(String path)  {
        //todo:判断文件是否存在

        //路劲++exec文件名
        String outputPath = path+shipmentId+".xlsx";
        File file = new File(outputPath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        //1 获取所有列对象（固定对象+动态生成的对象）
        List<ExcelExportEntity> beanList = getExcelExportEntityList();

        //组装excel导出sku基础数据对象
        List<ExcelProductInfo> epList = new ArrayList<>();

        //按sku分组
        Map<String, List<AmzBoxSkuInfoDTO>> boxSkuInfoMap = boxSkuInfoList.stream().collect(Collectors.groupingBy(AmzBoxSkuInfoDTO::getMerchantSKU));

        //组装excel导出sku基础数据对象

        AtomicInteger totalUnits = new AtomicInteger();//该发货计划总共发货数量

        boxSkuInfoMap.forEach((sku,listBoxSku)->{

            int sum = listBoxSku.stream().mapToInt(AmzBoxSkuInfoDTO::getQuantity).sum();//实际装箱数量
            totalUnits.addAndGet(sum);
            ExcelProductInfo excelProductInfo = ExcelProductInfo.builder()
                    .sku(listBoxSku.get(0).getMerchantSKU())
                    .asin(listBoxSku.get(0).getAsin())
                    .fnsku(listBoxSku.get(0).getFnsku())
                    .title(listBoxSku.get(0).getTitle())
                    .externalId("")
                    .boxedQTY(sum)
                    .expectedQTY(sum)//发货数量
                    .whoWillPrep("None Required")
                    .prepType("--")
                    .whoWillLabel("Merchant")
                    .build();
            epList.add(excelProductInfo);
        });

        //表尾对象数据
        epList.add(ExcelProductInfo.builder().sku("Plan ID: ").build());

        //Ship To: SBD1		Weight of box (kg)									6	7	8	9

        epList.add(ExcelProductInfo.builder().sku("Ship To: "+warehouseCode).title("Weight of box (kg)").build());

        //Total SKUs: 4		Box length (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().sku("Total SKUs: "+skuTotal).title("Box length (cm)").build());

        //Total Units: 800		Box width (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().sku("Total Units: "+totalUnits).title("Box width (cm)").build());

        //Box height (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().title("Box height (cm)").build());

        // 生成需要输出到excel的数据dtoList
        List<Map<String, Object>> dtoLst = new ArrayList<Map<String, Object>>();

        for (ExcelProductInfo item : epList)
            try {

                Map<String, Object> objectMap = objectToMap(item);
                //动态添装箱信息,默认都为空
                for (int i = 1; i <= boxTotal; i++) {
                    objectMap.put("box" + i, null);
                }

                //实际值
                String sku = (String) objectMap.get("sku");
                List<AmzBoxSkuInfoDTO> boxSkuInfos = boxSkuInfoList
                        .stream()
                        .filter(boxSkuInfo -> boxSkuInfo.getMerchantSKU().equals(sku))
                        .collect(Collectors.toList());

                for (AmzBoxSkuInfoDTO boxSkuInfo : boxSkuInfos) {
                    Integer boxNo = boxSkuInfo.getPackDetBoxNum();
                    Integer quantity = boxSkuInfo.getQuantity();
                    if (quantity > 0) {
                        objectMap.put("box" + boxNo, quantity);
                    }
                }

                String title = (String) objectMap.get("title");
                //尾部数据处理
                addData(boxTotal,sku,title,boxInfoList, objectMap);

                dtoLst.add(objectMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        return  this.writeExcelToStream(skuTotal,outputPath, beanList, dtoLst);
    }

    public byte[] createNew()  {
        //todo:判断文件是否存在

        //1 获取所有列对象（固定对象+动态生成的对象）
        List<ExcelExportEntity> beanList = getExcelExportEntityList();

        //组装excel导出sku基础数据对象
        List<ExcelProductInfo> epList = new ArrayList<>();

        //按sku分组
        Map<String, List<AmzBoxSkuInfoDTO>> boxSkuInfoMap = boxSkuInfoList.stream().collect(Collectors.groupingBy(AmzBoxSkuInfoDTO::getMerchantSKU));

        //组装excel导出sku基础数据对象

        AtomicInteger totalUnits = new AtomicInteger();//该发货计划总共发货数量

        boxSkuInfoMap.forEach((sku,listBoxSku)->{

            int sum = listBoxSku.stream().mapToInt(AmzBoxSkuInfoDTO::getExpectedQty).sum();//实际装箱数量
            totalUnits.addAndGet(sum);
            ExcelProductInfo excelProductInfo = ExcelProductInfo.builder()
                    .sku(listBoxSku.get(0).getMerchantSKU())
                    .asin(listBoxSku.get(0).getAsin())
                    .fnsku(listBoxSku.get(0).getFnsku())
                    .title(listBoxSku.get(0).getTitle())
                    .externalId("")
                    .boxedQTY(sum)
                    .expectedQTY(sum)//发货数量
                    .whoWillPrep("None Required")
                    .prepType("--")
                    .whoWillLabel("Merchant")
                    .build();
            epList.add(excelProductInfo);
        });

        //表尾对象数据
        epList.add(ExcelProductInfo.builder().sku("Plan ID: ").build());

        //Ship To: SBD1		Weight of box (kg)									6	7	8	9

        epList.add(ExcelProductInfo.builder().sku("Ship To: "+warehouseCode).title("Weight of box (kg)").build());

        //Total SKUs: 4		Box length (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().sku("Total SKUs: "+skuTotal).title("Box length (cm)").build());

        //Total Units: 800		Box width (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().sku("Total Units: "+totalUnits).title("Box width (cm)").build());

        //Box height (cm)									16	17	18	19
        epList.add(ExcelProductInfo.builder().title("Box height (cm)").build());

        // 生成需要输出到excel的数据dtoList
        List<Map<String, Object>> dtoLst = new ArrayList<Map<String, Object>>();

        for (ExcelProductInfo item : epList)
            try {

                Map<String, Object> objectMap = objectToMap(item);
                //动态添装箱信息,默认都为空
                for (int i = 1; i <= boxTotal; i++) {
                    objectMap.put("box" + i, null);
                }

                //实际值
                String sku = (String) objectMap.get("sku");
                List<AmzBoxSkuInfoDTO> boxSkuInfos = boxSkuInfoList
                        .stream()
                        .filter(boxSkuInfo -> boxSkuInfo.getMerchantSKU().equals(sku))
                        .collect(Collectors.toList());

                for (AmzBoxSkuInfoDTO boxSkuInfo : boxSkuInfos) {
                    Integer boxNo = boxSkuInfo.getPackDetBoxNum();
                    Integer quantity = boxSkuInfo.getExpectedQty();
                    if (quantity > 0) {
                        objectMap.put("box" + boxNo, quantity);
                    }
                }

                String title = (String) objectMap.get("title");
                //尾部数据处理
                addData(boxTotal,sku,title,boxInfoList, objectMap);

                dtoLst.add(objectMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        return  this.writeExcelToStreamNew(skuTotal, beanList, dtoLst);
    }

    /**
     * 获取所有列对象（固定对象+动态生成的对象）
     * @return
     */
    private List<ExcelExportEntity> getExcelExportEntityList() {
        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();

        beanList.add(new ExcelExportEntity("Merchant SKU", "sku"));
        beanList.add(new ExcelExportEntity("ASIN", "asin"));
        beanList.add(new ExcelExportEntity("Title", "title"));
        beanList.add(new ExcelExportEntity("FNSKU", "fnsku"));
        beanList.add(new ExcelExportEntity("External ID", "externalId"));
        beanList.add(new ExcelExportEntity("Who will prep?", "whoWillPrep"));
        beanList.add(new ExcelExportEntity("Prep Type", "prepType"));
        beanList.add(new ExcelExportEntity("Who will label?", "whoWillLabel"));

        ExcelExportEntity excelExportEntity = new ExcelExportEntity("Expected QTY", "expectedQTY");
        excelExportEntity.setType(BaseEntityTypeConstants.DOUBLE_TYPE);
        beanList.add(excelExportEntity);

        ExcelExportEntity excelExportEntity2 = new ExcelExportEntity("Boxed QTY", "boxedQTY");
        excelExportEntity2.setType(BaseEntityTypeConstants.DOUBLE_TYPE);
        beanList.add(excelExportEntity2);

        beanList.add(new ExcelExportEntity("", "nullValue"));


        //增加动态列
        for (int i = 1; i <=boxTotal; i++) {

            ExcelExportEntity entity = new ExcelExportEntity("Box " + i + " - QTY", "box" + i);
            entity.setType(BaseEntityTypeConstants.DOUBLE_TYPE);
            beanList.add(entity);
        }
        return beanList;
    }

    private static void addData(Integer boxTotal, String sku, String title, List<AmzBoxInfoDTO> listBox, Map<String, Object> objectMap) {

        for (Integer i = 1; i <= boxTotal; i++) {

            int finalI = i;

            if (StringUtils.isNoneEmpty(sku) && sku.contains("Plan ID:")) {
                objectMap.put("box" + i, "Box "+i);
                continue;
            }

            if (StringUtils.isNoneEmpty(sku) && sku.contains("Ship To")) {
                Double weight = listBox.stream().filter(b -> b.getPackDetBoxNum().equals(finalI)).map(AmzBoxInfoDTO::getPackDetBoxWeight).findAny().get();
                objectMap.put("box" + i, weight);
                continue;
            }
            if (StringUtils.isNoneEmpty(sku) && sku.contains("Total SKUs")) {
                Double  weight= listBox.stream().filter(b->b.getPackDetBoxNum().equals(finalI)).map(AmzBoxInfoDTO::getPackDetBoxLength).findAny().get();
                objectMap.put("box" + i, weight);
                continue;
            }
            if (StringUtils.isNoneEmpty(sku) && sku.contains("Total Units")) {
                Double  weight= listBox.stream().filter(b->b.getPackDetBoxNum().equals(finalI)).map(AmzBoxInfoDTO::getPackDetBoxWidth).findAny().get();
                objectMap.put("box" + i, weight);
                continue;
            }
            if (StringUtils.isNoneEmpty(title) && title.contains("Box height (cm)")) {
                Double  weight= listBox.stream().filter(b->b.getPackDetBoxNum().equals(finalI)).map(AmzBoxInfoDTO::getPackDetBoxHeight).findAny().get();
                objectMap.put("box" + i, weight);
                continue;
            }

        }
        return;

    }

    /**
     * 写入excel到流
     *
     */
    private String writeExcelToStream(Integer skutotals, String outputPath, List<ExcelExportEntity> entity, List<Map<String, Object>> dtoLst) {

        try {
            ExportParams exportParams = new ExportParams();

            // easyPoi操作excel
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity, dtoLst);

            Sheet sheet = workbook.getSheetAt(0);
            sheet.shiftRows(0, sheet.getLastRowNum(), 3);//向下移动3行用于创建表头

            int lastRowNum = sheet.getLastRowNum();
            sheet.shiftRows(lastRowNum-4,lastRowNum,1); //表格最后5行数据和 装箱数据 有一个空行 需要向下移动1行满足
            workbook.setSheetName(0,"Pack List");

            //创建表头
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("Shipment ID");
            row1.createCell(1).setCellValue(shipmentId);
            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("Name: "+shipmentName);

            //合并单元格
            //CellRangeAddress(第几行开始，第几行结束，第几列开始，第几列结束)
            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

            //1表头AB合并
            for (int i =0 ; i < 10+skutotals; i++) {//4 是sku个数
                if (i > 0 && i < 3) {
                    CellRangeAddress craOne = new CellRangeAddress(i, i, 0, 1);
                    cellRangeAddressList.add(craOne);
                    continue;
                }

                if (i > (4 + skutotals)) {
                    CellRangeAddress craOne1 = new CellRangeAddress(i, i, 0, 1);
                    CellRangeAddress craOne2 = new CellRangeAddress(i, i, 2, 10);
                    cellRangeAddressList.add(craOne1);
                    cellRangeAddressList.add(craOne2);
                }
            }

            for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                workbook.getSheet("Pack List").addMergedRegion(cellRangeAddress);
            }

            //Metadata sheet
            Sheet metadataSheet = workbook.createSheet();
            Row row = metadataSheet.createRow(0);
            row.createCell(0).setCellValue("Locale");
            row.createCell(1).setCellValue("zh_CN");
            workbook.setSheetName(1,"Metadata");

            OutputStream outputStream = new FileOutputStream(outputPath);
            // 写入到流中
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            return "Success";

        } catch (Exception e) {
            return "Error";
        }
    }

    /**
     * 写入excel到流
     *
     */
    private byte[] writeExcelToStreamNew(Integer skutotals, List<ExcelExportEntity> entity, List<Map<String, Object>> dtoLst) {

        try {
            ExportParams exportParams = new ExportParams();

            // easyPoi操作excel
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity, dtoLst);

            Sheet sheet = workbook.getSheetAt(0);
            sheet.shiftRows(0, sheet.getLastRowNum(), 3);//向下移动3行用于创建表头

            int lastRowNum = sheet.getLastRowNum();
            sheet.shiftRows(lastRowNum-4,lastRowNum,1); //表格最后5行数据和 装箱数据 有一个空行 需要向下移动1行满足
            workbook.setSheetName(0,"Pack List");

            //创建表头
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("Shipment ID");
            row1.createCell(1).setCellValue(shipmentId);
            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("Name: "+shipmentName);

            //合并单元格
            //CellRangeAddress(第几行开始，第几行结束，第几列开始，第几列结束)
            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

            //1表头AB合并
            for (int i =0 ; i < 10+skutotals; i++) {//4 是sku个数
                if (i > 0 && i < 3) {
                    CellRangeAddress craOne = new CellRangeAddress(i, i, 0, 1);
                    cellRangeAddressList.add(craOne);
                    continue;
                }

                if (i > (4 + skutotals)) {
                    CellRangeAddress craOne1 = new CellRangeAddress(i, i, 0, 1);
                    CellRangeAddress craOne2 = new CellRangeAddress(i, i, 2, 10);
                    cellRangeAddressList.add(craOne1);
                    cellRangeAddressList.add(craOne2);
                }
            }

            for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                workbook.getSheet("Pack List").addMergedRegion(cellRangeAddress);
            }

            //Metadata sheet
            Sheet metadataSheet = workbook.createSheet();
            Row row = metadataSheet.createRow(0);
            row.createCell(0).setCellValue("Locale");
            row.createCell(1).setCellValue("zh_CN");
            workbook.setSheetName(1,"Metadata");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 写入到流中
            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }

        return map;
    }


}
