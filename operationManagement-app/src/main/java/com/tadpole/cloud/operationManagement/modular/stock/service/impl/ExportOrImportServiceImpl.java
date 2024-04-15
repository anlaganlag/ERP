package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.excel.CustomCellWriteHandler;
import com.tadpole.cloud.operationManagement.modular.stock.excel.CustomCellWriteSpecialHandler;
import com.tadpole.cloud.operationManagement.modular.stock.excel.PurchaseOrdersCellWriteHandler;
import com.tadpole.cloud.operationManagement.modular.stock.excel.TeamVerifyCellWriteHandler;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.OperApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.*;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersExcelResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.operationManagement.modular.stock.utils.StockCalcRules;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 备货相关的导入导出
 */
@Service
@Slf4j
public class ExportOrImportServiceImpl implements IExportOrImportService {

    public static int BATCH_SIZE = StockConstant.BATCH_SIZE;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private IOperApplyInfoService operApplyInfoService;



    @Autowired
    private OperApplyInfoMapper mapper;


    @Autowired
    private SpecialApplyInfoMapper specialMapper;

    @Autowired
    private ITeamVerifService teamVerifService;


    @Autowired
    private ISpecialApplyInfoService specialApplyInfoService;

    @Autowired
    private IPurchaseOrdersService purchaseOrdersService;


    @DataSource(name = "stocking")
    @Override
    public ResponseData operImport(MultipartFile file) {
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OperApplyInfoExcelParam>();
            EasyExcel.read(buffer, OperApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<OperApplyInfoExcelParam> coverDataList = listener.getDataList();
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //过滤掉黑名单的数据
            List<OperApplyInfoExcelParam> dataList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getNote())).collect(Collectors.toList());

            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("除去黑名单数据后，导入数据为空，导入失败！");
            }

            //todo:异常数据处理
//            List<CollectionTarget> errorList = new ArrayList<>();

            ArrayList<OperApplyInfo> operList = this.getOperApplyInfos(dataList, false);

            operApplyInfoService.updateBatchById(operList);

            return ResponseData.success("导入成功！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);

                }
            }
        }
    }

    @DataSource(name = "stocking")
    @Override
    public void operExport(HttpServletResponse response) throws Exception {
        String filename = "运营申请导出模板";
        String modelPath = "classpath:template/operModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();

        List<OperApplyInfo> applyInfoList = operApplyInfoService.operExport(StockConstant.OPER_STOCK_STATUS_WAIT);

        if (ObjectUtil.isEmpty(applyInfoList)) {
            throw new Exception("未找到该用户权限对应的数据");
        }
        OperApplyInfo applyInfo = applyInfoList.get(0);

        //填充单个的值,当前月往后计算7个月 的数字
//        LocalDate date = applyInfo.getBizdate();
        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                map.put("curMonth", monthValue);
                continue;
            }
            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, mothInt);
        }

        //待填充的多个sheet页的list，都加入此map
        Map<String, List<?>> dataMap = new HashMap<>();



        List<OperApplyInfoExcelParam> resultList = new ArrayList<>();

        for (OperApplyInfo info : applyInfoList) {
            OperApplyInfoExcelParam result = BeanUtil.copyProperties(info, OperApplyInfoExcelParam.class);

            result.setStockQtyArea(BigDecimal.ZERO);
//            result.setSupplycle(0);
            result.setSalesDemand(BigDecimal.ZERO);
            result.setTurnoverDaysArea(BigDecimal.ZERO);
            result.setOperCoversSalesDate(new Date());
            BigDecimal lastPreMonthQty = result.getLastPreMonthQty();
            if (ObjectUtil.isNull(lastPreMonthQty) || ObjectUtil.isNull(result.getCurPreMonthQty())
                    || lastPreMonthQty.equals(BigDecimal.ZERO) || result.getCurPreMonthQty().equals(BigDecimal.ZERO)) {
                result.setCurPreDivideLastPreMonth(BigDecimal.ZERO);
            } else {
                result.setCurPreDivideLastPreMonth(result.getCurPreMonthQty().divide(result.getLastPreMonthQty(), 4, BigDecimal.ROUND_HALF_UP));
            }

            resultList.add(result);
        }
        dataMap.put("0", resultList);

        //填充报表,并下载
        this.fillReportWithEasyExcel(response, dataMap, map, filename, inputStream);

    }

    @DataSource(name = "stocking")
    @Override
    public void operExportFast(HttpServletResponse response) throws Exception {

        String filename = "运营申请导出模板";
        String modelPath = "classpath:template/operModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        boolean note = false;

        List<OperApplyInfoExcelParam> applyInfoList = operApplyInfoService.operExportFast(StockConstant.OPER_STOCK_STATUS_WAIT, note);

        if (ObjectUtil.isEmpty(applyInfoList)) {
            throw new Exception("未找到该用户权限对应的数据");
        }
        OperApplyInfoExcelParam applyInfo = applyInfoList.get(0);

        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                map.put("curMonth", monthValue);
                continue;
            }
            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, mothInt);
        }

        List<OperApplyInfoExcelParam> resultList = new ArrayList<>();

        for (OperApplyInfoExcelParam result : applyInfoList) {

            result.setStockQtyArea(BigDecimal.ZERO);
//            result.setSupplycle(0);
            result.setSalesDemand(BigDecimal.ZERO);
            result.setTurnoverDaysArea(BigDecimal.ZERO);
            result.setOperCoversSalesDate(new Date());
            BigDecimal lastPreMonthQty = result.getLastPreMonthQty();
            if (ObjectUtil.isNull(lastPreMonthQty) || ObjectUtil.isNull(result.getCurPreMonthQty())
                    || lastPreMonthQty.equals(BigDecimal.ZERO) || result.getCurPreMonthQty().equals(BigDecimal.ZERO)) {
                result.setCurPreDivideLastPreMonth(BigDecimal.ZERO);
            } else {
                result.setCurPreDivideLastPreMonth(result.getCurPreMonthQty().divide(result.getLastPreMonthQty(), 4, BigDecimal.ROUND_HALF_UP));
            }
            resultList.add(result);
        }


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new CustomCellWriteHandler()).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);

          /*  int size = resultList.size();
            if (size< 5000) {
                excelWriter.fill(resultList, fillConfig, writeSheet);

            }else {
                int forCunt = BigDecimal.valueOf(Math.ceil(size / 5000d)).intValue() ;
//                List<OperApplyInfoExcelParam> list = resultList.stream().collect(Collectors.toList());
                for (int i = 0; i < forCunt; i++) {

                    List<OperApplyInfoExcelParam> upList = new ArrayList<>();
                    if (i == forCunt - 1) {
                        upList = resultList.subList(i * 5000, resultList.size());
                    } else {
                        upList = resultList.subList(i * 5000, (i + 1) * 5000);
                    }

                    excelWriter.fill(upList, fillConfig, writeSheet);
//                    excelWriter.write(upList, writeSheet);
                    System.out.println("第"+i+"次5000条数据输出");
                }
            }*/
            excelWriter.fill(resultList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("运营申请导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            excelWriter.finish();
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }

    }


    @DataSource(name = "stocking")
    @Override
    public void operExportFast2(HttpServletResponse response) throws Exception {

        String filename = "运营申请导出模板";
        String modelPath = "classpath:template/operModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        boolean note = false;

        List<OperApplyInfoExcelExportParam> quryApplyInfoList = operApplyInfoService.operExportFast2(StockConstant.OPER_STOCK_STATUS_WAIT, note);


        if (ObjectUtil.isEmpty(quryApplyInfoList)) {
            throw new Exception("未找到该用户权限对应的数据");
        }

        //过滤掉
        //        物料黑名单,数据为0不做备货推荐
        //                ,数据为0不做备货推荐
        //        区域黑名单,数据为0不做备货推荐

        List<OperApplyInfoExcelExportParam> applyInfoList = quryApplyInfoList.stream().filter(a -> {
            if (ObjectUtil.isNull(a.getNote()) || ObjectUtil.isEmpty(a.getNote())) {
                return true;
            } else if (a.getNote().contains(StockConstant.BLACKLIST_OBSOLETE)) {
                return true;
            }  else if (!a.getNote().contains("数据为0不做备货推荐")) {
                return true;
            }else {
                return false;
            }
        }).collect(Collectors.toList());


        if (ObjectUtil.isEmpty(applyInfoList)) {
            throw new Exception("过滤掉数据为0不做备货推荐的记录后没有数据可以导出");
        }

        OperApplyInfoExcelExportParam applyInfo = applyInfoList.get(0);


        Date bizdate = applyInfo.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份

        //填充单个的值,当前月往后计算7个月 的数字
//        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                map.put("curMonth", bizLocalDate.getMonthValue());
                continue;
            }
            if (i < 4) {
                map.put("curMonthMinus" + i, bizLocalDate.minusMonths(Long.valueOf(i)).getMonthValue());
            }
//            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, bizLocalDate.plusMonths(Long.valueOf(i)).getMonthValue());
        }


/*

        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                map.put("curMonth", monthValue);
                continue;
            }
            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, mothInt);
        }
*/

        List<OperApplyInfoExcelExportParam> resultList = new ArrayList<>();

        for (int i = 0; i < applyInfoList.size(); i++) {
            OperApplyInfoExcelExportParam result = applyInfoList.get(i);
            int rowNo = i + 5;
            BigDecimal lastPreMonthQty = result.getLastPreMonthQty();
            if (ObjectUtil.isNull(lastPreMonthQty) || ObjectUtil.isNull(result.getCurPreMonthQty())
                    || lastPreMonthQty.equals(BigDecimal.ZERO) || result.getCurPreMonthQty().equals(BigDecimal.ZERO)) {
                result.setCurPreDivideLastPreMonth(BigDecimal.ZERO);
            } else {
                result.setCurPreDivideLastPreMonth(result.getCurPreMonthQty().divide(result.getLastPreMonthQty(), 4, BigDecimal.ROUND_HALF_UP));
            }
            //设置公式
            StringBuffer buffer = new StringBuffer();

//            OperApplyInfoExcelExportParam param = BeanUtil.copyProperties(result, OperApplyInfoExcelExportParam.class);

            result.setTotalBackDays(buffer.append("SUM(BL").append(rowNo).append(":BR").append(rowNo).append(")").toString());
            buffer.setLength(0);
            result.setRecomBackOverDays(buffer.append("(A").append(rowNo).append("+BK").append(rowNo).append(")").toString());
//            buffer.setLength(0);
//            result.setOperExpectedDate(buffer.append("A").append(rowNo).append("+BK").append(rowNo).append("+BO").append(rowNo).toString());
            buffer.setLength(0);
            result.setExtraDays(buffer.append("CC").append(rowNo).append("-BK").append(rowNo).toString());
            buffer.setLength(0);
            result.setSalesDemand(buffer.append("IFERROR(SUM(OFFSET($BV").append(rowNo).append(",0,0,1,(MATCH(MONTH($CH").append(rowNo).append(
                    "),$BV$1:$CB$1,0)-1)))+SUM(OFFSET($BV").append(rowNo).append(",0,(MATCH(MONTH($CH").append(rowNo).append(
                    "),$BV$1:$CB$1,0))-1,1,1))*DAY($CH").append(rowNo).append(")/30-$BV").append(rowNo).append("*DAY($A").append(rowNo).append(")/30,0)").toString());
            buffer.setLength(0);
            result.setOperCoversSalesDate(buffer.append("(A").append(rowNo).append("+CC").append(rowNo).append(")").toString());
            buffer.setLength(0);
            result.setStockQtyArea(buffer.append("IF((CG").append(rowNo).append("-AA").append(rowNo).append(")<0,0,CG").append(rowNo).append("-AA").append(rowNo).append(")").toString());
            buffer.setLength(0);
            result.setTurnoverDaysArea(buffer.append("IFERROR(ROUND((CI").append(rowNo).append("+AA").append(rowNo).append(")/BG").append(rowNo).append(",0),99999)").toString());
        }


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new CustomCellWriteHandler()).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);

            excelWriter.fill(applyInfoList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("运营申请导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }

    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData specialImport(MultipartFile file) {
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SpecialApplyInfoExcelParam>();
            EasyExcel.read(buffer, SpecialApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<SpecialApplyInfoExcelParam> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //todo:异常数据处理
//            List<CollectionTarget> errorList = new ArrayList<>();

            //对导入的数据的id进行校验
            ResponseData checkResult = specialApplyInfoService.checkData(dataList);
            if (ObjectUtil.isEmpty(dataList)) {
                return checkResult;
            }

            ArrayList<SpecialApplyInfo> operList = this.getSpecialApplyInfos(dataList);
//            List<SpecialApplyInfo> newDataList = new ArrayList<>();
//            for (SpecialApplyInfoExcelParam specialApplyInfoExcelParam : dataList) {
//                SpecialApplyInfo newData = BeanUtil.copyProperties(specialApplyInfoExcelParam,SpecialApplyInfo.class);
//                newDataList.add(newData);
//            }

            specialApplyInfoService.saveOrUpdateBatch(operList);

            return checkResult;
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }


    @DataSource(name = "stocking")
    @Override
    public void specialExport(HttpServletResponse response, SpecialApplyInfoReqVO param) throws Exception {

        String filename = "特殊备货申请导出模板";
        String modelPath = "classpath:template/specialModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();

        List<SpecialApplyInfoResult> applyInfoList = specialApplyInfoService.specialExport(param);

        if (ObjectUtil.isEmpty(applyInfoList)) {
            throw new Exception("未找到该用户权限对应的数据");
        }
        SpecialApplyInfoResult applyInfo = applyInfoList.get(0);

        //填充单个的值,当前月往后计算7个月 的数字
/*
        int monthValue = DateUtil.date().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                map.put("curMonth", monthValue);
                continue;
            }
            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, mothInt);
        }
*/


        Date bizdate = applyInfo.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份

        //填充单个的值,当前月往后计算7个月 的数字
//        int monthValue = applyInfo.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                map.put("curMonth", bizLocalDate.getMonthValue());
                continue;
            }
            if (i < 4) {
                map.put("curMonthMinus" + i, bizLocalDate.minusMonths(Long.valueOf(i)).getMonthValue());
            }
//            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, bizLocalDate.plusMonths(Long.valueOf(i)).getMonthValue());
        }


        //待填充的多个sheet页的list，都加入此map
        Map<String, List<?>> dataMap = new HashMap<>();

        //填充sheet1
        // 模拟数据 todo:权限过滤查找该用户能使用的数据


        List<SpecialApplyInfoExcelParam> resultList = new ArrayList<>();
        HashMap<String, String> billTypeMap = new HashMap<>();
        billTypeMap.put("XPBH", "新品备货");
        billTypeMap.put("XMBH", "项目备货");
        billTypeMap.put("JJBH", "紧急备货");

        for (SpecialApplyInfoResult info : applyInfoList) {
            SpecialApplyInfoExcelParam result = BeanUtil.copyProperties(info, SpecialApplyInfoExcelParam.class);

            result.setRecomBackOverDays(new Date());
            BigDecimal lastPreMonthQty = result.getLastPreMonthQty();
            if (ObjectUtil.isNull(lastPreMonthQty) || ObjectUtil.isNull(result.getCurPreMonthQty())
                    || lastPreMonthQty.equals(BigDecimal.ZERO) || result.getCurPreMonthQty().equals(BigDecimal.ZERO)) {
                result.setCurPreDivideLastPreMonth(BigDecimal.ZERO);
            } else {
                result.setCurPreDivideLastPreMonth(result.getCurPreMonthQty().divide(result.getLastPreMonthQty(), 4, BigDecimal.ROUND_HALF_UP));
            }
//            result.setBillType(billTypeMap.getOrDefault(info.getBillType(),"");
            result.setBillType(billTypeMap.getOrDefault(info.getBillType(), ""));


            resultList.add(result);
        }
        dataMap.put("0", resultList);

        //填充报表,并下载
//        this.fillReportWithEasyExcel(response, dataMap, map, filename, inputStream);


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new CustomCellWriteSpecialHandler()).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);


            excelWriter.fill(resultList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("特殊备货导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            excelWriter.finish();
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }

    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operImportComit(MultipartFile file) {
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OperApplyInfoExcelParam>();
            EasyExcel.read(buffer, OperApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<OperApplyInfoExcelParam> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //todo:异常数据处理
//            List<CollectionTarget> errorList = new ArrayList<>();

            //保存
            ArrayList<OperApplyInfo> operList = this.getOperApplyInfos(dataList, true);
            operApplyInfoService.updateBatchById(operList, 3000);
            //提交处理

            //提交下一节点集合
            List<TeamVerif> TeamVerifLists = new ArrayList<>();


            TreeSet<String> platformMmaterialCodeTeamAreaSet = new TreeSet<>();

            dataList.stream().forEach(i -> {
                platformMmaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());
            });

            if (CollectionUtil.isNotEmpty(platformMmaterialCodeTeamAreaSet)) {


                int size = platformMmaterialCodeTeamAreaSet.size();
                if (size < BATCH_SIZE) {
                    mapper.updateStockAreaList(platformMmaterialCodeTeamAreaSet);
                } else {
                    int forCunt = BigDecimal.valueOf(Math.ceil(size / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                    List<String> list = platformMmaterialCodeTeamAreaSet.stream().collect(Collectors.toList());
                    for (int i = 0; i < forCunt; i++) {

                        List<String> upList = new ArrayList<>();
                        if (i == forCunt - 1) {
                            upList = list.subList(i * BATCH_SIZE, list.size());
                        } else {
                            upList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                        }

                        mapper.updateStockAreaList(upList.stream().collect(Collectors.toSet()));
                    }
                }

                for (String platformMaterialCodeTeamArea : platformMmaterialCodeTeamAreaSet) {
                    //取出物料、team、区域明细汇总
                    OperApplyInfo commitApply = mapper.selectCommit(platformMaterialCodeTeamArea);

                    if (ObjectUtil.isNotEmpty(commitApply)) {
                        TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);
                        teamVerif.setOperCurMonthQty(commitApply.getOperCurMonthQty());
                        teamVerif.setOperCurMonthAdd1Qty(commitApply.getOperCurMonthAdd1Qty());
                        teamVerif.setOperCurMonthAdd2Qty(commitApply.getOperCurMonthAdd2Qty());
                        teamVerif.setOperCurMonthAdd3Qty(commitApply.getOperCurMonthAdd3Qty());
                        teamVerif.setOperCurMonthAdd4Qty(commitApply.getOperCurMonthAdd4Qty());
                        teamVerif.setOperCurMonthAdd5Qty(commitApply.getOperCurMonthAdd5Qty());
                        teamVerif.setOperCurMonthAdd6Qty(commitApply.getOperCurMonthAdd6Qty());

                        teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_WAIT);
                        if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                   
                            teamVerif.setVersionDes("");
                        }
                        TeamVerifLists.add(teamVerif);
                        StockCalcRules.teamInitValue(teamVerif);
                        teamVerifService.save(teamVerif);

                        //修改状态为已申请  反写id
                        mapper.updateCommitStatus(platformMaterialCodeTeamArea, teamVerif.getId().toString());
                    }
                }
            }
            return ResponseData.success("导入提交成功！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operImportComit2(MultipartFile file) {

        BufferedInputStream buffer = null;
        String userName = this.getUserName();
        String userAccount = this.getUserAccount();

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OperApplyInfoExcelParam>();
            EasyExcel.read(buffer, OperApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<OperApplyInfoExcelParam> coverDataList = listener.getDataList();
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //todo:异常数据处理
//            List<CollectionTarget> errorList = new ArrayList<>();

            //保存
//            ArrayList<OperApplyInfo> operList = this.getOperApplyInfos(dataList,true);
//            operApplyInfoService.updateBatchById(operList);

            //过滤掉黑名单的数据
            List<OperApplyInfoExcelParam> dataList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getNote())).collect(Collectors.toList());

            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("除去黑名单数据后，导入数据为空，导入失败！");
            }

            Date date = new Date();
            dataList.forEach(d -> {
                d.setApplyPersonName(userName);
                d.setApplyPersonNo(userAccount);
                d.setApplyDate(date);
            });


            int applySize = dataList.size();
            if (applySize < BATCH_SIZE) {
                mapper.importCommitBatch(dataList);
            } else {
                int forCunt = BigDecimal.valueOf(Math.ceil(applySize / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                List<OperApplyInfoExcelParam> upList = new ArrayList<>();
                for (int i = 0; i < forCunt; i++) {

                    if (i == forCunt - 1) {
                        upList = dataList.subList(i * BATCH_SIZE, applySize);
                    } else {
                        upList = dataList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    }
                    mapper.importCommitBatch(upList);
                }
            }

            //提交处理

            //提交下一节点集合
            List<TeamVerif> TeamVerifLists = new ArrayList<>();


            TreeSet<String> platformMaterialCodeTeamAreaSet = new TreeSet<>();

            dataList.stream().forEach(i -> {
                platformMaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());
            });

            if (CollectionUtil.isNotEmpty(platformMaterialCodeTeamAreaSet)) {

                int size = platformMaterialCodeTeamAreaSet.size();
                if (size < BATCH_SIZE) {
                    mapper.updateStockAreaList(platformMaterialCodeTeamAreaSet);
                } else {
                    int forCunt = BigDecimal.valueOf(Math.ceil(size / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                    List<String> list = platformMaterialCodeTeamAreaSet.stream().collect(Collectors.toList());
                    for (int i = 0; i < forCunt; i++) {

                        List<String> upList = new ArrayList<>();
                        if (i == forCunt - 1) {
                            upList = list.subList(i * BATCH_SIZE, list.size());
                        } else {
                            upList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                        }

                        mapper.updateStockAreaList(upList.stream().collect(Collectors.toSet()));
                    }
                }

                for (String platformMaterialCodeTeamArea : platformMaterialCodeTeamAreaSet) {
                    //取出物料、team、区域明细汇总
                    OperApplyInfo commitApply = mapper.selectCommit(platformMaterialCodeTeamArea);

                    if (ObjectUtil.isNotEmpty(commitApply)) {
                        TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);
                        teamVerif.setOperCurMonthQty(commitApply.getOperCurMonthQty());
                        teamVerif.setOperCurMonthAdd1Qty(commitApply.getOperCurMonthAdd1Qty());
                        teamVerif.setOperCurMonthAdd2Qty(commitApply.getOperCurMonthAdd2Qty());
                        teamVerif.setOperCurMonthAdd3Qty(commitApply.getOperCurMonthAdd3Qty());
                        teamVerif.setOperCurMonthAdd4Qty(commitApply.getOperCurMonthAdd4Qty());
                        teamVerif.setOperCurMonthAdd5Qty(commitApply.getOperCurMonthAdd5Qty());
                        teamVerif.setOperCurMonthAdd6Qty(commitApply.getOperCurMonthAdd6Qty());

                        teamVerif.setVerifStatus(StockConstant.TEAM_STOCK_STATUS_WAIT);
                        if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                            teamVerif.setVersionDes("");
                        }

//                        this.initValue(teamVerif);
                        StockCalcRules.teamInitValue(teamVerif);

                        TeamVerifLists.add(teamVerif);

                        teamVerifService.save(teamVerif);

                        //修改状态为已申请  反写id
                        mapper.updateCommitStatus(platformMaterialCodeTeamArea, teamVerif.getId().toString());
                    }
                }
            }
            return ResponseData.success("导入提交成功！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operImportComit3(MultipartFile file) {

        BufferedInputStream buffer = null;
        String userName = this.getUserName();
        String userAccount = this.getUserAccount();

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OperApplyInfoExcelParam>();
            EasyExcel.read(buffer, OperApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<OperApplyInfoExcelParam> coverDataList = listener.getDataList();
            log.info("excel解析出来数据" + coverDataList.size());
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("解析出来的数据为空，导入失败！");
            }

            //todo:异常数据处理？

            //过滤掉黑名单的数据
            List<OperApplyInfoExcelParam> dataList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getNote())).collect(Collectors.toList());

            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("除去黑名单数据后，导入数据为空，导入失败！");
            }

            log.info("除去黑名单数据条数：" + dataList.size());

            Date date = new Date();
            dataList.forEach(d -> {
                d.setApplyPersonName(userName);
                d.setApplyPersonNo(userAccount);
                d.setApplyDate(date);
            });

//            List<List<T>> splitList = this.splitList(dataList);

            int applySize = dataList.size();
            if (applySize < BATCH_SIZE) {
                mapper.importCommitBatch(dataList);
            } else {
                int forCunt = BigDecimal.valueOf(Math.ceil(applySize / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                List<OperApplyInfoExcelParam> upList = new ArrayList<>();
                for (int i = 0; i < forCunt; i++) {

                    if (i == forCunt - 1) {
                        upList = dataList.subList(i * BATCH_SIZE, applySize);
                    } else {
                        upList = dataList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    }
                    mapper.importCommitBatch(upList);
                }
            }

            log.info("运营申请批量提交成功条数：" + dataList.size());
            //提交处理

            //提交下一节点集合 更新提交状态
            TreeSet<String> platformMaterialCodeTeamAreaSet = new TreeSet<>();

            dataList.stream().forEach(i -> {
                platformMaterialCodeTeamAreaSet.add(i.getPlatform() + i.getMaterialCode() + i.getTeam() + i.getArea());
            });

            if (CollectionUtil.isEmpty(platformMaterialCodeTeamAreaSet)) {
                return ResponseData.error("数据异常没有可以合并提交到team审核的数据");
            }

            log.info("检查是否全部都提交materialCodeTeamArea 去重条数：" + platformMaterialCodeTeamAreaSet.size());

            int size = platformMaterialCodeTeamAreaSet.size();
            if (size < BATCH_SIZE) {
                mapper.updateStockAreaList(platformMaterialCodeTeamAreaSet);
            } else {
                int forCunt = BigDecimal.valueOf(Math.ceil(size / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                List<String> list = platformMaterialCodeTeamAreaSet.stream().collect(Collectors.toList());
                for (int i = 0; i < forCunt; i++) {

                    List<String> upList = new ArrayList<>();
                    if (i == forCunt - 1) {
                        upList = list.subList(i * BATCH_SIZE, list.size());
                    } else {
                        upList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    }

                    mapper.updateStockAreaList(upList.stream().collect(Collectors.toSet()));
                }
            }

            log.info("检查是否全部都提交materialCodeTeamArea 去重条数：" + platformMaterialCodeTeamAreaSet.size());

            //查找所有已经提交的申请记录
            long begin = System.currentTimeMillis();
            List<OperApplyInfo> sumList = new ArrayList<>();
            if (size < BATCH_SIZE) {
                //取出物料、team、区域明细汇总
                List<OperApplyInfo> commitApplyList = mapper.batchSelectCommit(platformMaterialCodeTeamAreaSet.stream().collect(Collectors.toList()));
                sumList.addAll(commitApplyList);
            } else {
                int forCunt = BigDecimal.valueOf(Math.ceil(size / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
                List<String> list = platformMaterialCodeTeamAreaSet.stream().collect(Collectors.toList());
                for (int i = 0; i < forCunt; i++) {

                    List<String> upList = new ArrayList<>();
                    if (i == forCunt - 1) {
                        upList = list.subList(i * BATCH_SIZE, list.size());
                    } else {
                        upList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    }

                    List<OperApplyInfo> commitApplyList = mapper.batchSelectCommit(upList);
                    sumList.addAll(commitApplyList);
                }
            }


            log.info("查找所有已经提交的申请记录：" + sumList.size() + "耗时：" + (System.currentTimeMillis() - begin));

            //生成team审核的数据

            List<TeamVerif> teamVerifList = new ArrayList<>();
            Date teamInitDate = new Date();
            for (OperApplyInfo commitApply : sumList) {
                if (ObjectUtil.isNotEmpty(commitApply)) {
                    TeamVerif teamVerif = BeanUtil.copyProperties(commitApply, TeamVerif.class);
                    teamVerif.setOperCurMonthQty(commitApply.getOperCurMonthQty());
                    teamVerif.setOperCurMonthAdd1Qty(commitApply.getOperCurMonthAdd1Qty());
                    teamVerif.setOperCurMonthAdd2Qty(commitApply.getOperCurMonthAdd2Qty());
                    teamVerif.setOperCurMonthAdd3Qty(commitApply.getOperCurMonthAdd3Qty());
                    teamVerif.setOperCurMonthAdd4Qty(commitApply.getOperCurMonthAdd4Qty());
                    teamVerif.setOperCurMonthAdd5Qty(commitApply.getOperCurMonthAdd5Qty());
                    teamVerif.setOperCurMonthAdd6Qty(commitApply.getOperCurMonthAdd6Qty());
                    if (ObjectUtil.isNull(teamVerif.getSalesStockDays())) {
                        teamVerif.setSalesStockDays(BigDecimal.ZERO);
                    }
                    teamVerif.setVerifStatus(StockConstant.VERIFY_WAIT);
                    teamVerif.setCreateTime(teamInitDate);
                    if (ObjectUtil.isNull(teamVerif.getVersionDes())) {
                        teamVerif.setVersionDes("");
                    }

//                    this.initValue(teamVerif);
                    StockCalcRules.teamInitValue(teamVerif);

                    teamVerifList.add(teamVerif);

                }
            }

            log.info("生成team审核的数据：" + teamVerifList.size());

            teamVerifService.saveBatch(teamVerifList, BATCH_SIZE * 6);

            log.info("保存team审核的数据：" + teamVerifList.size());

            //回写 team审核id到运营申请记录表
            List<Map<String, String>> platformMaterialCodeTeamAreaList = new ArrayList<>();
            ArrayList<String> teamVerifIdList = new ArrayList<>();

            teamVerifList.forEach(t -> {
                HashMap<String, String> map = new HashMap<>();
                StringBuffer bf = new StringBuffer();
                String key = bf.append(t.getPlatform()).append(t.getMaterialCode()).append(t.getTeam()).append(t.getArea()).toString();
                map.put("platformMaterialCodeTeamArea", key);
                map.put("teamVerifId", String.valueOf(t.getId().longValue()));
                platformMaterialCodeTeamAreaList.add(map);
                teamVerifIdList.add(key);
            });

            int tsize = platformMaterialCodeTeamAreaList.size();

            if (tsize < BATCH_SIZE) {
                mapper.batchUpdateCommitStatus(platformMaterialCodeTeamAreaList, teamVerifIdList);
            } else {
                int forCunt = BigDecimal.valueOf(Math.ceil(tsize / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();

                for (int i = 0; i < forCunt; i++) {

                    List<Map<String, String>> tempUpList = new ArrayList<>();
                    List<String> tempUpList2 = new ArrayList<>();
                    if (i == forCunt - 1) {
                        tempUpList = platformMaterialCodeTeamAreaList.subList(i * BATCH_SIZE, tsize);
                        tempUpList2 = teamVerifIdList.subList(i * BATCH_SIZE, tsize);
                    } else {
                        tempUpList = platformMaterialCodeTeamAreaList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                        tempUpList2 = teamVerifIdList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    }
                    mapper.batchUpdateCommitStatus(tempUpList, tempUpList2);
                }
            }

            log.info("回写team审核id到运营申请记录表条数：" + platformMaterialCodeTeamAreaList.size());

            return ResponseData.success("导入提交成功！");
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！{}", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常{}", e);
                }
            }
        }
    }

    /**
     * 批量处理，拆分每次处理的数据量
     *
     * @param list
     * @return
     */
    private List splitList(List<T> list) {
        List<List<T>> resultList = new ArrayList<>();
        int size = list.size();
        if (size < BATCH_SIZE) {
            resultList.add(list);
            return resultList;
        } else {
            int forCunt = BigDecimal.valueOf(Math.ceil(size / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();

            for (int i = 0; i < forCunt; i++) {

                List<T> tempList = new ArrayList<>();
                if (i == forCunt - 1) {
                    tempList = list.subList(i * BATCH_SIZE, size);
                } else {
                    tempList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                }
                resultList.add(tempList);
            }
        }
        return resultList;
    }

    /**
     * team 审核部分属性值初始化
     *
     * @param teamVerif
     */
    private void initValue(TeamVerif teamVerif) {
        BigDecimal salesDemand = teamVerif.getSalesDemand().max(teamVerif.getTotalVolume());

        BigDecimal dayavgqty = teamVerif.getDayavgqty();

        //补充 申请区域备货后周转天数  销售需求3/日均销量D4
        if (ObjectUtil.isNull(salesDemand) || salesDemand.compareTo(BigDecimal.ZERO) == 0) {
            teamVerif.setTurnoverDays(BigDecimal.ZERO);
        } else if (ObjectUtil.isNull(dayavgqty) || dayavgqty.compareTo(BigDecimal.ZERO) == 0) {
            teamVerif.setTurnoverDays(new BigDecimal(99999));
        } else {
            teamVerif.setTurnoverDays(salesDemand.divide(dayavgqty, 4, RoundingMode.HALF_UP));
        }

        //计算AZ超180天库龄数量占比
        if (teamVerif.getInStockQty().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setOverD180InvAgeQtyRate(teamVerif.getOverD180InvAgeQty()
                    .divide(teamVerif.getInStockQty(), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            teamVerif.setOverD180InvAgeQtyRate(new BigDecimal(99999));
        }
        //AZ海外总库存供货天数
        if (teamVerif.getPreSaleQty().compareTo(BigDecimal.ZERO) != 0 && teamVerif.getTotalBackDays().compareTo(BigDecimal.ZERO) != 0) {
            teamVerif.setPrepareDays(teamVerif.getTotalVolume().divide(teamVerif.getPreSaleQty().divide(teamVerif.getTotalBackDays(), 8, BigDecimal.ROUND_DOWN), 0, BigDecimal.ROUND_DOWN));
        } else {
            teamVerif.setPrepareDays(new BigDecimal(99999));
        }
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData specialImportComit(MultipartFile file) {
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SpecialApplyInfoExcelParam>();
            EasyExcel.read(buffer, SpecialApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            List<SpecialApplyInfoExcelParam> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("导入数据为空，导入失败！");
            }

            //todo:异常数据处理
//            List<CollectionTarget> errorList = new ArrayList<>();

            //对导入的数据的id进行校验
            ResponseData checkResult = specialApplyInfoService.checkData(dataList);
            if (ObjectUtil.isEmpty(dataList)) {
                return checkResult;
            }


            //保存
            ArrayList<SpecialApplyInfo> specialList = this.getSpecialApplyInfos(dataList);
            specialApplyInfoService.updateBatchById(specialList);
            ArrayList<SpecialApplyReqVO> specialReqVOList = this.getSpecialReqVOList(specialList);

            if (specialReqVOList.size() >= 1000) {
                return ResponseData.error("特殊备货导入提交大于1000条");
            }

            if (specialReqVOList.size() == 0) {
                return ResponseData.error("特殊备货无可提交的数据");
            }

            specialApplyInfoService.commitBatch(specialReqVOList);


            //提交处理

            //提交下一节点集合


            return checkResult;
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }
    }


    /**
     * EasyExcel 填充表
     *
     * @param response
     * @param sheetAndDataMap key:sheet页，value:填充的list集合
     * @param map             填充单个的值
     * @param filename        文件名
     * @param inputStream     文件流.
     */
    public  void fillReportWithEasyExcel(HttpServletResponse response, Map<String, List<?>> sheetAndDataMap,
                                               Map<String, Integer> map, String filename, InputStream inputStream) {
        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");


            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            for (Map.Entry<String, List<?>> entry : sheetAndDataMap.entrySet()) {
                List<?> value = entry.getValue();
                WriteSheet writeSheet = EasyExcel.writerSheet(Integer.valueOf(entry.getKey())).registerWriteHandler(new CustomCellWriteHandler()).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
                excelWriter.fill(map, writeSheet);
                excelWriter.fill(value, fillConfig, writeSheet);

                //设置强制计算公式：不然公式会以字符串的形式显示在excel中
                Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
                workbook.setForceFormulaRecalculation(true);

            }

        } catch (Exception e) {
            log.error(JSONUtil.toJsonStr(e));
        } finally {
            excelWriter.finish();
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }
    }

    /**
     * EasyExcel 填充表
     *
     * @param response
     * @param sheetAndDataMap key:sheet页，value:填充的list集合
     * @param map             填充单个的值
     * @param filename        文件名
     * @param inputStream     文件流.
     */
    public static void fillReportWithEasyExcelFast(HttpServletResponse response, Map<String, List<?>> sheetAndDataMap,
                                                   Map<String, Integer> map, String filename, InputStream inputStream) {
        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");


            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            for (Map.Entry<String, List<?>> entry : sheetAndDataMap.entrySet()) {
                List<?> value = entry.getValue();
                WriteSheet writeSheet = EasyExcel.writerSheet(Integer.valueOf(entry.getKey())).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(map, writeSheet);


/*

                int size = value.size();
                if (size< 5000) {
                    excelWriter.fill(value, fillConfig, writeSheet);
                }else {
                    int forCunt = BigDecimal.valueOf(Math.ceil(size / 5000d)).intValue() ;
                    List<?> list = value.stream().collect(Collectors.toList());
                    for (int i = 0; i < forCunt; i++) {

                        List<String> upList = new ArrayList<>();
                        if (i == forCunt - 1) {
                            upList = list.subList(i * BATCH_SIZE, list.size());
                        } else {
                            upList = list.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                        }

                        mapper.updateStockAreaList(upList.stream().collect(Collectors.toSet()));
                    }
                }
*/


//                excelWriter.fill(value, fillConfig, writeSheet);
                excelWriter.finish();
                //设置强制计算公式：不然公式会以字符串的形式显示在excel中
//                Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
//                workbook.setForceFormulaRecalculation(true);

            }

        } catch (Exception e) {
            log.error(JSONUtil.toJsonStr(e));
        } finally {
//            excelWriter.finish();
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }
    }


    @DataSource(name = "stocking")
    private ArrayList<OperApplyInfo> coverOperApplyInfo(List<OperApplyInfoExcelParam> dataList, Boolean isCommit) {

        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        ArrayList<OperApplyInfo> operList = new ArrayList<>();
        Date date = new Date();
        for (OperApplyInfoExcelParam excelParam : dataList) {
            OperApplyInfo applyInfo = this.needUpdateOperApplyInfo(new OperApplyInfo(), excelParam, userAccount, userName, isCommit);
            operList.add(applyInfo);
        }
        return operList;
    }


    @DataSource(name = "stocking")
    private ArrayList<OperApplyInfo> getOperApplyInfos(List<OperApplyInfoExcelParam> dataList, Boolean isCommit) {

        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        ArrayList<OperApplyInfo> operList = new ArrayList<>();

        for (OperApplyInfoExcelParam excelParam : dataList) {
            OperApplyInfo applyInfo = this.needUpdateOperApplyInfo(new OperApplyInfo(), excelParam, userAccount, userName, isCommit);

            operList.add(applyInfo);
        }
        return operList;
    }


    @DataSource(name = "stocking")
    private OperApplyInfo needUpdateOperApplyInfo(OperApplyInfo applyInfo, OperApplyInfoExcelParam excelParam, String userAccount, String userName, Boolean isCommit) {
        Date date = new Date();
//        OperApplyInfo applyInfo = new OperApplyInfo();
        applyInfo.setId(excelParam.getId());
        applyInfo.setOperCurMonthQty(excelParam.getOperCurMonthQty());
        applyInfo.setOperCurMonthAdd1Qty(excelParam.getOperCurMonthAdd1Qty());
        applyInfo.setOperCurMonthAdd2Qty(excelParam.getOperCurMonthAdd2Qty());
        applyInfo.setOperCurMonthAdd3Qty(excelParam.getOperCurMonthAdd3Qty());
        applyInfo.setOperCurMonthAdd4Qty(excelParam.getOperCurMonthAdd4Qty());
        applyInfo.setOperCurMonthAdd5Qty(excelParam.getOperCurMonthAdd5Qty());
        applyInfo.setOperCurMonthAdd6Qty(excelParam.getOperCurMonthAdd6Qty());
        applyInfo.setSalesStockDays(excelParam.getSalesStockDays().setScale(0,BigDecimal.ROUND_HALF_UP));
        applyInfo.setOperExpectedDate(excelParam.getOperExpectedDate());
        if (ObjectUtil.isNotEmpty(excelParam.getStockReason())) {
            applyInfo.setStockReason(excelParam.getStockReason());
        }
        applyInfo.setExtraDays(excelParam.getExtraDays().setScale(0,BigDecimal.ROUND_HALF_UP));
        applyInfo.setSalesDemand(excelParam.getSalesDemand().setScale(0,BigDecimal.ROUND_HALF_UP));
        applyInfo.setOperCoversSalesDate(excelParam.getOperCoversSalesDate());
        applyInfo.setStockQtyArea(excelParam.getStockQtyArea().setScale(0,BigDecimal.ROUND_HALF_UP));
        applyInfo.setTurnoverDaysArea(excelParam.getTurnoverDaysArea().setScale(0,BigDecimal.ROUND_HALF_UP));
        applyInfo.setUpdateTime(date);
        applyInfo.setApplyDate(date);
        applyInfo.setApplyPersonNo(userAccount);
        applyInfo.setApplyPersonName(userName);
        if (isCommit && (StringUtils.isEmpty(applyInfo.getNote()) || StockConstant.BLACKLIST_OBSOLETE.equals(applyInfo.getNote())) ) { //非黑名单 才允许提交//2022-10-18-建议淘汰产品允许提交
            applyInfo.setStockStatus(StockConstant.OPER_STOCK_STATUS_COMIT);
        }
        return applyInfo;
    }

    @DataSource(name = "stocking")
    private ArrayList<SpecialApplyReqVO> getSpecialReqVOList(List<SpecialApplyInfo> dataList) {

        String userAccount = this.getUserAccount();
        String userName = this.getUserName();


        ArrayList<SpecialApplyReqVO> operList = new ArrayList<>();
        for (SpecialApplyInfo excelParam : dataList) {
            if (ObjectUtil.isEmpty(excelParam.getSalesDemand()) || excelParam.getSalesDemand().intValue() == 0) {
                continue;
            }
            SpecialApplyReqVO applyInfo = new SpecialApplyReqVO();
            applyInfo.setId(excelParam.getId().intValue());
////            applyInfo.setCurMonthQty(excelParam.getCurMonthQty());
////            applyInfo.setCurMonthAdd1Qty(excelParam.getCurMonthAdd1Qty());
////            applyInfo.setCurMonthAdd2Qty(excelParam.getCurMonthAdd2Qty());
////            applyInfo.setCurMonthAdd3Qty(excelParam.getCurMonthAdd3Qty());
////            applyInfo.setCurMonthAdd4Qty(excelParam.getCurMonthAdd4Qty());
////            applyInfo.setCurMonthAdd5Qty(excelParam.getCurMonthAdd5Qty());
////            applyInfo.setCurMonthAdd6Qty(excelParam.getCurMonthAdd6Qty());
//            applyInfo.setSalesStockDays(excelParam.getSalesStockDays());
//            applyInfo.setOperExpectedDate(excelParam.getOperExpectedDate());
//            applyInfo.setStockReason(excelParam.getStockReason());
//            applyInfo.setExtraDays(excelParam.getExtraDays());
//            applyInfo.setSalesDemand(excelParam.getSalesDemand());
////            applyInfo.setOperCoversSalesDate(excelParam.getOperCoversSalesDate());
//            applyInfo.setStockQtyArea(excelParam.getStockQtyArea());
////            applyInfo.setTurnoverDaysArea(excelParam.getTurnoverDaysArea());
//            applyInfo.setUpdateTime(date);
//            applyInfo.setApplyDate(date);
//            applyInfo.setApplyPersonNo(userAccount);
//            applyInfo.setApplyPersonName(userName);
//            applyInfo.setId(Integer.valueOf(IdWorker.getIdStr()));
//            applyInfo.setPlatform(excelParam.getPlatform());
//            applyInfo.setDepartment(excelParam.getDepartment());
            applyInfo.setTeam(excelParam.getTeam());
            applyInfo.setPlatform(excelParam.getPlatform());
            applyInfo.setArea(excelParam.getArea());
            applyInfo.setMaterialCode(excelParam.getMaterialCode());
//            applyInfo.setAsin(excelParam.getAsin());
//            applyInfo.setDemandDeliveryType(excelParam.getDemandDeliveryType());

//            String billType = excelParam.getBillType();
//
//            switch (billType) {
//                case "'日常备货'":
//                    billType = "RCBH";
//                    break;
//                case "紧急备货":
//                    billType = "JJBH";
//                    break;
//                case "项目备货":
//                    billType = "XMBH";
//                    break;
//                case "新品备货":
//                    billType = "XPBH";
//                default:
//                    break;
//            }
//            applyInfo.setBillType(billType);
            operList.add(applyInfo);
        }
        return operList;
    }


    @DataSource(name = "stocking")
    private ArrayList<SpecialApplyInfo> getSpecialApplyInfos(List<SpecialApplyInfoExcelParam> dataList) {
        ArrayList<SpecialApplyInfo> operList = new ArrayList<>();
        LambdaQueryWrapper<SpecialApplyInfo> wrapper = new LambdaQueryWrapper<>();
        HashMap<String, String> billTypeMap = new HashMap<>();
        billTypeMap.put("新品备货", "XPBH");
        billTypeMap.put("项目备货", "XMBH");
        billTypeMap.put("紧急备货", "JJBH");
        for (SpecialApplyInfoExcelParam excelParam : dataList) {
            if (ObjectUtil.isEmpty(excelParam.getSalesDemand()) || excelParam.getSalesDemand().intValue() == 0) {
                continue;
            }
            wrapper.eq(SpecialApplyInfo::getId, excelParam.getId())
                    //无note即非黑名单
                    .isNull(SpecialApplyInfo::getNote);
            SpecialApplyInfo applyInfo = specialMapper.selectOne(wrapper);
            wrapper.clear();
            if (ObjectUtil.isEmpty(applyInfo)) {
                continue;
            }
            String billType = excelParam.getBillType();
            applyInfo.setBillType(billTypeMap.getOrDefault(billType, "类型错误"));
            if (applyInfo.getBillType().equals("类型错误")) {
                continue;
            }
            Date now = DateUtil.date();
            BeanUtil.copyProperties(excelParam, applyInfo);
            applyInfo.setUpdateTime(now);
            applyInfo.setApplyDate(now);
            applyInfo.setApplyPersonNo(this.getUserAccount());
            applyInfo.setApplyPersonName(this.getUserName());
            applyInfo.setBillType(billTypeMap.getOrDefault(billType, ""));
            applyInfo.setOperCoversSalesDate(DateUtil.offsetDay(now, applyInfo.getSalesDemand().intValue()));
            applyInfo.setNote(null);

            operList.add(applyInfo);
        }
        return operList;
    }


    @DataSource(name = "stocking")
    @Override
    public void teamVerifyExport(HttpServletResponse response) throws Exception {

        String filename = "Team审核导入导出模板";
        String modelPath = "classpath:template/teamModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        boolean note = false;

        List<TeamVerifyExcelExportParam> teamVerifyList = teamVerifService.teamVerifyExport(StockConstant.TEAM_STOCK_STATUS_WAIT);


        if (ObjectUtil.isEmpty(teamVerifyList)) {
            throw new Exception("未找到该用户权限对应的数据");
        }
        TeamVerifyExcelExportParam teamVerify = teamVerifyList.get(0);

        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = teamVerify.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                map.put("curMonth", monthValue);
                continue;
            }
            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, mothInt);
        }

        List<OperApplyInfoExcelExportParam> resultList = new ArrayList<>();

        for (int i = 0; i < teamVerifyList.size(); i++) {
            TeamVerifyExcelExportParam result = teamVerifyList.get(i);

            int rowNo = i + 5;
            BigDecimal lastPreMonthQty = result.getLastPreMonthQty();
            if (ObjectUtil.isNull(lastPreMonthQty) || ObjectUtil.isNull(result.getCurPreMonthQty())
                    || lastPreMonthQty.equals(BigDecimal.ZERO) || result.getCurPreMonthQty().equals(BigDecimal.ZERO)) {
                result.setCurPreDivideLastPreMonth(BigDecimal.ZERO);
            } else {
                result.setCurPreDivideLastPreMonth(result.getCurPreMonthQty().divide(result.getLastPreMonthQty(), 4, BigDecimal.ROUND_HALF_UP));
            }
            //设置公式
            StringBuffer buffer = new StringBuffer();

//            OperApplyInfoExcelExportParam param = BeanUtil.copyProperties(result, OperApplyInfoExcelExportParam.class);

            result.setTotalBackDays(buffer.append("SUM(BK").append(rowNo).append(":BQ").append(rowNo).append(")").toString());
            buffer.setLength(0);
            result.setRecomBackOverDays(buffer.append("(A").append(rowNo).append("+BJ").append(rowNo).append(")").toString());
//            buffer.setLength(0);
//            result.setOperExpectedDate(buffer.append("A").append(rowNo).append("+BK").append(rowNo).append("+BO").append(rowNo).toString());
            buffer.setLength(0);
            result.setExtraDays(buffer.append("CB").append(rowNo).append("-BJ").append(rowNo).toString());
            buffer.setLength(0);
            result.setSalesDemand(buffer.append("IFERROR(SUM(OFFSET($BU").append(rowNo).append(",0,0,1,(MATCH(MONTH($CG").append(rowNo).append(
                    "),$BU$1:$CB$1,0)-1)))+SUM(OFFSET($BU").append(rowNo).append(",0,(MATCH(MONTH($CG").append(rowNo).append(
                    "),$BU$1:$CB$1,0))-1,1,1))*DAY($CG").append(rowNo).append(")/30-$BU").append(rowNo).append("*DAY($A").append(rowNo).append(")/30,0)").toString());
            buffer.setLength(0);
            result.setOperCoversSalesDate(buffer.append("(A").append(rowNo).append("+CB").append(rowNo).append(")").toString());
            buffer.setLength(0);
            result.setStockQtyArea(buffer.append("IF((CF").append(rowNo).append("-Z").append(rowNo).append(")<0,0,CF").append(rowNo).append("-Z").append(rowNo).append(")").toString());
            buffer.setLength(0);
//            =IFERROR(ROUND((MAX(CF5,Z5)/BF5),0),99999)
            result.setTurnoverDaysArea(buffer.append("IFERROR(ROUND((MAX(CF").append(rowNo).append(",Z").append(rowNo).append(")/BF").append(rowNo).append("),0),99999)").toString());
        }


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new TeamVerifyCellWriteHandler()).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);

            excelWriter.fill(teamVerifyList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("运营申请导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }

    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData teamVerifyImport(MultipartFile file) {
        BufferedInputStream buffer = null;


        List<TeamVerifyExcelParam> coverDataList = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<TeamVerifyExcelParam>();
            EasyExcel.read(buffer, TeamVerifyExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            coverDataList = listener.getDataList();
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("导入数据为空！");
            }
        } catch (IOException e) {
            return ResponseData.error("上传文件解析异常：" + JSONUtil.toJsonStr(e.getMessage()));
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }

        try {

            //过滤掉黑名单的数据
            List<TeamVerifyExcelParam> noteDataList = coverDataList.stream()
                    .filter(d -> ObjectUtil.isEmpty(d.getNote()) || d.getNote().contains(StockConstant.BLACKLIST_OBSOLETE)).collect(Collectors.toList());

            if (CollectionUtil.isEmpty(noteDataList)) {
                return ResponseData.error("除去黑名单数据后，导入数据为空,请检查数据后再提交！");
            }


            //过滤数据id为空的
            List<TeamVerifyExcelParam> dataList = noteDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getId())).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(dataList)) {
                return ResponseData.error("导入文件有缺失数据ID，不能正确导入，请检查文件！");
            }


            List<BigDecimal> idList = noteDataList.stream().map(o -> o.getId()).collect(Collectors.toList());
            List<TeamVerif> updateTeamList = new ArrayList<>();
            List<List<BigDecimal>> splitIds = CollectionUtil.split(idList, BATCH_SIZE);
            for (List<BigDecimal> ids : splitIds) {
                updateTeamList.addAll(teamVerifService.getBaseMapper().selectBatchIds(ids));
            }


            if (ObjectUtil.isEmpty(updateTeamList)) {
                return ResponseData.error("根据表格的数据id,没能在数据库找到相应的记录，请检查id是否正确新值");
            }

            List<TeamVerif> comitedData = updateTeamList.stream()
                    .filter(t -> ! StockConstant.TEAM_STOCK_STATUS_WAIT.equals(t.getVerifStatus())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(comitedData)) {
                List<BigDecimal> commitedIds = comitedData.stream().map(c -> c.getId()).collect(Collectors.toList());
                return ResponseData.error(500,"以下数据ID已经提交，请勿重复提交!",commitedIds);
            }


            //id是否都找到数据
            List<BigDecimal> queryIdList = updateTeamList.stream().map(o -> o.getId()).collect(Collectors.toList());


            if (queryIdList.size() != idList.size()) {
                boolean b = idList.removeAll(queryIdList);
                return ResponseData.error("根据表格的数据id,以下数据id未找到推荐信息【" + idList + "】请检查后再提交");
            }


            updateTeamList = this.coverTeamVerif(updateTeamList, noteDataList);
            teamVerifService.updateBatchById(updateTeamList);
            return ResponseData.success(updateTeamList);
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！--->>"+e.getMessage());
        }
    }

    @DataSource(name = "stocking")
    private List<TeamVerif> coverTeamVerif(List<TeamVerif> updateTeamList, List<TeamVerifyExcelParam> noteDataList) {
        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        Map<BigDecimal, List<TeamVerifyExcelParam>> listMap = noteDataList.stream().collect(Collectors.groupingBy(TeamVerifyExcelParam::getId));

        ArrayList<OperApplyInfo> teamList = new ArrayList<>();
        Date date = new Date();
        for (TeamVerif teamVerif : updateTeamList) {
            TeamVerifyExcelParam excelParam = listMap.get(teamVerif.getId()).get(0);
            this.needUpdateTeamVerif(teamVerif, excelParam, userAccount, userName, false);
        }
        return updateTeamList;
    }


    @DataSource(name = "stocking")
    private void needUpdateTeamVerif(TeamVerif teamVerif, TeamVerifyExcelParam excelParam, String userAccount, String userName, boolean isCommit) {
        Date date = new Date();
//        teamVerif.setId(excelParam.getId());
        teamVerif.setOperCurMonthQty(excelParam.getOperCurMonthQty());
        teamVerif.setOperCurMonthAdd1Qty(excelParam.getOperCurMonthAdd1Qty());
        teamVerif.setOperCurMonthAdd2Qty(excelParam.getOperCurMonthAdd2Qty());
        teamVerif.setOperCurMonthAdd3Qty(excelParam.getOperCurMonthAdd3Qty());
        teamVerif.setOperCurMonthAdd4Qty(excelParam.getOperCurMonthAdd4Qty());
        teamVerif.setOperCurMonthAdd5Qty(excelParam.getOperCurMonthAdd5Qty());
        teamVerif.setOperCurMonthAdd6Qty(excelParam.getOperCurMonthAdd6Qty());
        teamVerif.setSalesStockDays(excelParam.getSalesStockDays().setScale(0,BigDecimal.ROUND_HALF_UP));
        teamVerif.setOperExpectedDate(excelParam.getOperExpectedDate());
        if (ObjectUtil.isNotEmpty(excelParam.getStockReason())) {
            teamVerif.setStockReason(  excelParam.getStockReason());
        }
        teamVerif.setExtraDays(excelParam.getExtraDays().setScale(0,BigDecimal.ROUND_HALF_UP));
        teamVerif.setSalesDemand(excelParam.getSalesDemand().setScale(0,BigDecimal.ROUND_HALF_UP));
        teamVerif.setOperCoversSalesDate(excelParam.getOperCoversSalesDate());
        teamVerif.setStockQtyArea(excelParam.getStockQtyArea().setScale(0,BigDecimal.ROUND_HALF_UP));
        teamVerif.setTurnoverDaysArea(excelParam.getTurnoverDaysArea().setScale(0,BigDecimal.ROUND_HALF_UP));
        teamVerif.setUpdateTime(date);
        teamVerif.setVerifDate(date);
        teamVerif.setVerifPersonNo(userAccount);
        teamVerif.setVerifPersonName(userName);
        if (isCommit && StringUtils.isEmpty(teamVerif.getNote())) { //非黑名单 才允许提交
            teamVerif.setVerifStatus(StockConstant.VERIFY_SUCESS);
        }

    }


    @DataSource(name = "stocking")
    private ArrayList<TeamVerif> getTeamVerifs(List<TeamVerifyExcelParam> dataList, Boolean isCommit) {

        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        ArrayList<TeamVerif> teamVerifList = new ArrayList<>();
        Date date = new Date();
        for (TeamVerifyExcelParam excelParam : dataList) {
            TeamVerif teamVerif = new TeamVerif();
            teamVerif.setId(excelParam.getId());
            teamVerif.setOperCurMonthQty(excelParam.getOperCurMonthQty());
            teamVerif.setOperCurMonthAdd1Qty(excelParam.getOperCurMonthAdd1Qty());
            teamVerif.setOperCurMonthAdd2Qty(excelParam.getOperCurMonthAdd2Qty());
            teamVerif.setOperCurMonthAdd3Qty(excelParam.getOperCurMonthAdd3Qty());
            teamVerif.setOperCurMonthAdd4Qty(excelParam.getOperCurMonthAdd4Qty());
            teamVerif.setOperCurMonthAdd5Qty(excelParam.getOperCurMonthAdd5Qty());
            teamVerif.setOperCurMonthAdd6Qty(excelParam.getOperCurMonthAdd6Qty());
            teamVerif.setSalesStockDays(excelParam.getSalesStockDays());
            teamVerif.setOperExpectedDate(excelParam.getOperExpectedDate());
            teamVerif.setStockReason(excelParam.getStockReason());
            teamVerif.setExtraDays(excelParam.getExtraDays());
            teamVerif.setSalesDemand(excelParam.getSalesDemand());
            teamVerif.setOperCoversSalesDate(excelParam.getOperCoversSalesDate());
            teamVerif.setStockQtyArea(excelParam.getStockQtyArea());
            teamVerif.setTurnoverDaysArea(excelParam.getTurnoverDaysArea());
            teamVerif.setUpdateTime(date);
            teamVerif.setVerifDate(date);
            teamVerif.setVerifPersonNo(userAccount);
            teamVerif.setVerifPersonName(userName);
            if (isCommit && StringUtils.isEmpty(teamVerif.getNote())) { //非黑名单 才允许提交
                teamVerif.setVerifStatus(StockConstant.VERIFY_SUCESS);
            }

            teamVerifList.add(teamVerif);
        }
        return teamVerifList;
    }


    @DataSource(name = "stocking")
    @Override
    public void purchaseOrdersExport(HttpServletResponse response, @RequestBody OperApplyInfoReqVO param) throws Exception {
        List<PurchaseOrdersResult> resultList = purchaseOrdersService.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("采购申请单导出数据为空!");
        }

        List<PurchaseOrdersExcelResult> excelResultList = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < resultList.size(); i++) {
            PurchaseOrdersResult result = resultList.get(i);
            PurchaseOrdersExcelResult excelResult = BeanUtil.copyProperties(result, PurchaseOrdersExcelResult.class);
            //        申请备货后周转天数    =实时计算：（AZ海外总库存AA+国内可用库存AD+采购未完成数量AE+申请审核中数量AF+采购申请数量BM)/日均销量AU
//            excelResult.setTurnoverDays(buffer.append("IFERROR(ROUND((CH").append(rowNo).append("+Z").append(rowNo).append(")/BF").append(rowNo).append(",0),99999)").toString());
            int rowNo = i + 3;

            String valueStr = buffer.append("=IFERROR(ROUND(SUM(AB").append(rowNo).append(",AE").append(rowNo).append(",AF")
                    .append(rowNo).append(",AG").append(rowNo).append(",BN").append(rowNo).append(")/AV").append(rowNo).append(",0),99999)").toString();
//            excelResult.setTurnoverDays(valueStr);
            excelResultList.add(excelResult);
            buffer.setLength(0);
        }


        String filename = "事业部审核导出模板";
        String modelPath = "classpath:template/departmentModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();


        PurchaseOrdersExcelResult info = excelResultList.get(0);


        Date bizdate = info.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份


        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = info.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                map.put("curMonth", bizLocalDate.getMonthValue());
                continue;
            }
            if (i < 4) {
                map.put("curMonthMinus" + i, bizLocalDate.minusMonths(Long.valueOf(i)).getMonthValue());
            }
//            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, bizLocalDate.plusMonths(Long.valueOf(i)).getMonthValue());
        }


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new PurchaseOrdersCellWriteHandler()).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);

            excelWriter.fill(excelResultList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("事业部审核导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }

    }

    @DataSource(name = "stocking")
    @Override
    public void purchaseOrdersExportV3(HttpServletResponse response, OperApplyInfoReqV3 param) throws Exception {
        List<PurchaseOrdersResult> resultList = purchaseOrdersService.exportExcelV3(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("采购申请单导出数据为空!");
        }

        List<PurchaseOrdersExcelResult> excelResultList = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < resultList.size(); i++) {
            PurchaseOrdersResult result = resultList.get(i);
            PurchaseOrdersExcelResult excelResult = BeanUtil.copyProperties(result, PurchaseOrdersExcelResult.class);
            //        申请备货后周转天数    =实时计算：（AZ海外总库存AA+国内可用库存AD+采购未完成数量AE+申请审核中数量AF+采购申请数量BM)/日均销量AU
//            excelResult.setTurnoverDays(buffer.append("IFERROR(ROUND((CH").append(rowNo).append("+Z").append(rowNo).append(")/BF").append(rowNo).append(",0),99999)").toString());
            int rowNo = i + 3;

            String valueStr = buffer.append("=IFERROR(ROUND(SUM(AB").append(rowNo).append(",AE").append(rowNo).append(",AF")
                    .append(rowNo).append(",AG").append(rowNo).append(",BN").append(rowNo).append(")/AV").append(rowNo).append(",0),99999)").toString();
//            excelResult.setTurnoverDays(valueStr);
            excelResultList.add(excelResult);
            buffer.setLength(0);
        }


        String filename = "事业部审核导出模板";
        String modelPath = "classpath:template/departmentModel.xlsx";//模板所在路径

        Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();


        PurchaseOrdersExcelResult info = excelResultList.get(0);


        Date bizdate = info.getBizdate();//推荐日期
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//推荐日期月份


        //填充单个的值,当前月往后计算7个月 的数字
        int monthValue = info.getBizdate().getMonth() + 1;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                map.put("curMonth", bizLocalDate.getMonthValue());
                continue;
            }
            if (i < 4) {
                map.put("curMonthMinus" + i, bizLocalDate.minusMonths(Long.valueOf(i)).getMonthValue());
            }
//            int mothInt = ((monthValue + i) % 12 == 0) ? 12 : (monthValue + i) % 12;
            map.put("curMonthAdd" + i, bizLocalDate.plusMonths(Long.valueOf(i)).getMonthValue());
        }


        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码，所有通过后端的文件下载都可以如此处理
            //建议加上该段，否则可能会出现前端无法获取Content-disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).registerWriteHandler(new PurchaseOrdersCellWriteHandler()).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(map, writeSheet);

            excelWriter.fill(excelResultList, fillConfig, writeSheet);
            //设置强制计算公式：不然公式会以字符串的形式显示在excel中
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            log.error("事业部审核导出异常:{}", JSONUtil.toJsonStr(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(JSONUtil.toJsonStr(e));
            }
        }
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData purchaseOrdersImport(MultipartFile file) {

        BufferedInputStream buffer = null;
        String userName = this.getUserName();
        String userAccount = this.getUserAccount();

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<PurchaseOrdersExcelParam>();
            EasyExcel.read(buffer, PurchaseOrdersExcelParam.class, listener).headRowNumber(2).sheet()
                    .doRead();

            List<PurchaseOrdersExcelParam> coverDataList = listener.getDataList();
            log.info("excel解析出来数据" + coverDataList.size());
            if (CollectionUtil.isEmpty(coverDataList)) {
                return ResponseData.error("解析出来的数据为空，导入失败！");
            }

            Map<String, String> errorMap = new HashMap<>();

            List<PurchaseOrders> updateOrderList = new ArrayList<>();

            Date date = new Date();

            for (PurchaseOrdersExcelParam excelParam : coverDataList) {
                PurchaseOrders order = purchaseOrdersService.getById(excelParam.getId());
                if (ObjectUtil.isNull(order)) {
                    errorMap.put(excelParam.getId(), "数据id未找到对应的采购申请记录");
                    continue;
                }

                if (order.getOrderStatus() != StockConstant.ORDER_STATUS_WAIT) {
                    errorMap.put(excelParam.getId(), "该采购申请单已经不处于事业部待审核状态，不能导入更新！");
                    continue;
                }

                order.setTurnoverDays(excelParam.getTurnoverDays());
                order.setPurchaseApplyQty(excelParam.getPurchaseApplyQty());

                if (ObjectUtil.isNotEmpty(excelParam.getPurchaseReason())) {
                    order.setPurchaseReason(excelParam.getPurchaseReason());
                }
                order.setUpdateTime(date);
                updateOrderList.add(order);
            }

            if (ObjectUtil.isNotEmpty(errorMap)) {
                return ResponseData.error(500, "以下数据导入异常", errorMap);
            }

            if (ObjectUtil.isNotEmpty(updateOrderList)) {
                List<List<PurchaseOrders>> splitData = CollectionUtil.split(updateOrderList, BATCH_SIZE);
                for (List<PurchaseOrders> upData : splitData) {
                    purchaseOrdersService.updateBatchById(upData);
                }
                return ResponseData.success(updateOrderList);
            }

        } catch (Exception e) {
            return ResponseData.error(JSONUtil.toJsonStr(e));
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常{}", e);
                }
            }
        }

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operImportV3(MultipartFile file) {

        BufferedInputStream buffer = null;
        List<OperApplyInfoExcelParam> coverDataList = null;
        try {

            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<OperApplyInfoExcelParam>();
            EasyExcel.read(buffer, OperApplyInfoExcelParam.class, listener).headRowNumber(4).sheet()
                    .doRead();

            coverDataList = listener.getDataList();
        } catch (IOException e) {
            return ResponseData.error("上传文件解析异常：" + JSONUtil.toJsonStr(e.getMessage()));
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入Excel关闭流异常", e);
                }
            }
        }

        if (CollectionUtil.isEmpty(coverDataList)) {
            return ResponseData.error("导入数据为空，导入失败！");
        }

        if (coverDataList.size()>BATCH_SIZE) {
//            return ResponseData.error("导入数据条数超过"+BATCH_SIZE+"条，请提交小于限制条数的文件");
        }

        //除去黑名单数据
        List<OperApplyInfoExcelParam> noteDataList = coverDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getNote())||StockConstant.BLACKLIST_OBSOLETE.equals(d.getNote())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(noteDataList)) {
            return ResponseData.error("除去黑名单数据后，导入数据为空！");
        }

        //过滤数据id为空的
        List<OperApplyInfoExcelParam> dataList = noteDataList.stream().filter(d -> ObjectUtil.isEmpty(d.getId())).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(dataList)) {
            return ResponseData.error("导入文件有缺失数据ID，不能正确导入，请检查文件！");
        }

        try {

            List<BigDecimal> idList = noteDataList.stream().map(o -> o.getId()).collect(Collectors.toList());
            List<OperApplyInfo> updateOperInofList = new ArrayList<>();

            List<List<BigDecimal>> splitData = CollectionUtil.split(idList, BATCH_SIZE);
            for (List<BigDecimal> ids : splitData) {
                updateOperInofList.addAll(operApplyInfoService.getBaseMapper().selectBatchIds(ids));
            }


            if (ObjectUtil.isEmpty(updateOperInofList)) {
                return ResponseData.error("根据表格的数据id,没能在数据库找到相应的记录，请检查id是否是最新值");
            }

            List<OperApplyInfo> commitedDataList = updateOperInofList.stream()
                    .filter(opi -> StockConstant.OPER_STOCK_STATUS_WAIT != opi.getStockStatus()).collect(Collectors.toList());

            if (ObjectUtil.isNotEmpty(commitedDataList)) {
                List<BigDecimal> commitedIds = commitedDataList.stream().map(c -> c.getId()).collect(Collectors.toList());
                return ResponseData.error(500,"以下数据ID已经提交，请勿重复提交!",commitedIds);
            }

            //id是否都找到数据
            List<BigDecimal> queryIdList = updateOperInofList.stream().map(o -> o.getId()).collect(Collectors.toList());


            if (queryIdList.size() != idList.size()) {
                boolean b = idList.removeAll(queryIdList);
                return ResponseData.error("根据表格的数据id,以下数据id未找到推荐信息【" + idList + "】请检查后再提交");
            }



//            ArrayList<OperApplyInfo> operList = this.getOperApplyInfos(dataList, false);

//            List<OperApplyInfoExcelParam> errorDataList = new ArrayList<>();

            updateOperInofList = this.coverOperApplyInfo(updateOperInofList, noteDataList);

            ResponseData r =    operApplyInfoService.updateBatchOperApplyInfo(updateOperInofList);
//            operApplyInfoService.updateBatchById(updateOperInofList);

//            List<OperApplyInfo> flashDataList = operApplyInfoService.getBaseMapper().selectBatchIds(idList);

            return ResponseData.success(updateOperInofList);
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e.getMessage());
            return ResponseData.error("文件解析数据成功，存储数据库失败：" + e.getMessage());
        }
    }


    @DataSource(name = "stocking")
    private List<OperApplyInfo> coverOperApplyInfo(List<OperApplyInfo> updateOperInofList, List<OperApplyInfoExcelParam> dataList) {

        Map<BigDecimal, List<OperApplyInfoExcelParam>> listMap = dataList.stream().collect(Collectors.groupingBy(OperApplyInfoExcelParam::getId));

        String userAccount = this.getUserAccount();
        String userName = this.getUserName();

        for (OperApplyInfo info : updateOperInofList) {
            if (ObjectUtil.isEmpty(listMap.get(info.getId()))) {
                continue;
            }
            OperApplyInfoExcelParam excelParam = listMap.get(info.getId()).get(0);
            this.needUpdateOperApplyInfo(info, excelParam, userAccount, userName, false);
        }
        return updateOperInofList;
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }
}
