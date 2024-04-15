package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlement;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementDetail;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementExportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementPageTotalResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsDataStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsOperationBillStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsOperationTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LogisticsSettlementMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementDetailService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementRecordService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementService;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

/**
 * @author: ty
 * @description: 物流实际结算
 * @date: 2022/11/14
 */
@Service
@Slf4j
public class LogisticsSettlementServiceImpl extends ServiceImpl<LogisticsSettlementMapper, LogisticsSettlement> implements ILogisticsSettlementService {

    /**
     * ip地址
     */
    @Value("${smb.url}")
    private String url;
    /**
     * 用户名
     */
    @Value("${smb.username}")
    private String username;
    /**
     * 密码
     */
    @Value("${smb.password}")
    private String password;
    @Autowired
    private ILogisticsSettlementService logisticsSettlementService;
    @Autowired
    private ILogisticsSettlementDetailService logisticsSettlementDetailService;
    @Autowired
    private ILogisticsSettlementRecordService logisticsSettlementRecordService;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;
    @Autowired
    private LogisticsSettlementMapper logisticsSettlementMapper;

    private static final String SPECIAL_STR = "#N/A";

    @Override
    @DataSource(name = "logistics")
    public PageTotalResult<LogisticsSettlementResult, LogisticsSettlementPageTotalResult> queryListPage(LogisticsSettlementParam param) {
        if(StringUtils.isNotBlank(param.getLogisticsSettlementOrder())
                || StringUtils.isNotBlank(param.getLogisticsBillOrder())
                || StringUtils.isNotBlank(param.getTaxBillOrder())
                || (StringUtils.isNotBlank(param.getLogisticsErpDateStart()) && StringUtils.isNotBlank(param.getLogisticsErpDateEnd()))
                || (StringUtils.isNotBlank(param.getTaxErpDateStart()) && StringUtils.isNotBlank(param.getTaxErpDateEnd()))
        ){
            //查询需要明细表
            param.setNeedDetail("true");
        }
        if(StringUtils.isNotEmpty(param.getShipmentDateEnd())){
            param.setShipmentDateEnd(param.getShipmentDateEnd() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getLogisticsErpDateEnd())){
            param.setLogisticsErpDateEnd(param.getLogisticsErpDateEnd() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getTaxErpDateEnd())){
            param.setTaxErpDateEnd(param.getTaxErpDateEnd() + " 23:59:59");
        }

        Page<LogisticsSettlementResult> pageResult = this.baseMapper.queryListPage(getPageContext(), param);
        LogisticsSettlementPageTotalResult pageTotalResult = this.baseMapper.queryTotalPage(param);
        return new PageTotalResult(pageResult, pageTotalResult);
    }

    @Override
    @DataSource(name = "logistics")
    public List<LogisticsSettlementExportResult> export(LogisticsSettlementParam param) {
        if(StringUtils.isNotEmpty(param.getShipmentDateEnd())){
            param.setShipmentDateEnd(param.getShipmentDateEnd() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getLogisticsErpDateEnd())){
            param.setLogisticsErpDateEnd(param.getLogisticsErpDateEnd() + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(param.getTaxErpDateEnd())){
            param.setTaxErpDateEnd(param.getTaxErpDateEnd() + " 23:59:59");
        }
        return this.baseMapper.exportList(param);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData uploadRemark(MultipartFile file) {
        log.info("物流实际结算备注导入处理开始");
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LogisticsSettlementImportParam>();
            EasyExcel.read(buffer, LogisticsSettlementImportParam.class, listener).sheet()
                    .doRead();

            List<LogisticsSettlementImportParam> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("物流实际结算备注导入数据为空，导入失败！");
            }

            List<LogisticsSettlementImportParam> errorList = new ArrayList<>();
            this.validUploadRemark(dataList, errorList);

            //返回物流实际结算备注导入数据集合
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                //部分导入成功
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据，导入失败！", fileName);
            }else{
                return ResponseData.success(dataList);
            }
        } catch (Exception e) {
            log.error("物流实际结算备注导入Excel处理异常，导入失败！", e);
            return ResponseData.error("物流实际结算备注导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("物流实际结算备注导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 物流实际结算备注导入数据校验处理
     * @param dataList
     * @return
     */
    private List<LogisticsSettlementImportParam> validUploadRemark(List<LogisticsSettlementImportParam> dataList, List<LogisticsSettlementImportParam> errorList) {
        Iterator<LogisticsSettlementImportParam> iterator = dataList.listIterator();
        while(iterator.hasNext()) {
            LogisticsSettlementImportParam param = iterator.next();
            //验证基础信息
            StringBuffer validInfo = new StringBuffer();
            if(param.getShipmentNum() == null){
                //不为空校验
                validInfo.append("发货批次号为必填项");
            } else {
                //根据发货批次号查询主表数据
                LambdaQueryWrapper<LogisticsSettlement> query = new LambdaQueryWrapper<>();
                query.eq(LogisticsSettlement :: getShipmentNum, param.getShipmentNum());
                LogisticsSettlement logisticsSettlement = logisticsSettlementService.getOne(query);
                if(logisticsSettlement == null){
                    validInfo.append("发货批次号不存在！");
                }
            }
            if(StringUtils.isNotEmpty(validInfo)){
                param.setUploadRemark(validInfo.toString());
                errorList.add(param);
            }
        }
        return dataList;
    }

    /**
     * 物流实际结算备注导入异常数据导出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<LogisticsSettlementImportParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, LogisticsSettlementImportParam.class)
                    .sheet("导入结果").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("物流实际结算备注导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("物流实际结算备注导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData uploadRemarkSave(List<LogisticsSettlementImportParam> params) {
        List<LogisticsSettlementImportParam> errorList = new ArrayList<>();
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;
        for (LogisticsSettlementImportParam update : params) {
            if (StringUtils.isNotEmpty(update.getUploadRemark())) {
                errorList.add(update);
                continue;
            }
            LambdaUpdateWrapper<LogisticsSettlement> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(LogisticsSettlement :: getShipmentNum, update.getShipmentNum())
                    .set(LogisticsSettlement :: getRemark, update.getRemark())
                    .set(LogisticsSettlement :: getUpdateUser, accountAndName)
                    .set(LogisticsSettlement :: getUpdateTime, DateUtil.date());
            logisticsSettlementService.update(updateWrapper);
        }
        if(CollectionUtil.isNotEmpty(errorList)){
            String fileName = this.dealImportErrorList(errorList);
            if(errorList.size() == params.size()){
                //全部失败
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败！", fileName);
            }
            //部分导入成功
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
        }
        return ResponseData.success("物流实际结算备注导入保存成功！");
    }

    @Override
    public ResponseData readSmbExcel(String fileDate) {
        log.info("读取物流实际结算对账数据(手动/定时)处理开始");
        String prePath = "/仓库财务共享/报关物流记录";
        if(StringUtils.isNotEmpty(fileDate)){
            //读取指定年月数据
            if(!fileDate.contains("-")){
                return ResponseData.error("参数异常！");
            }
            String [] yearMonthArr = fileDate.split("-");
            String year = yearMonthArr[0];
            String month = yearMonthArr[1];
            String fileName = "物流发货统计对账表" + month + "月-" + year + ".xlsx";
            String path = prePath + "/" + year + "/" + year + "年物流发货统计对账表/物流发货对账表（总表）/";

            //读取共享网盘文件并处理数据
            return dealSmbExcel(fileName, path, fileDate);
        }else{
            StringBuffer msgInfo = new StringBuffer();
            for (int i = -23; i <= 0; i++) {
                //每天定时读取最近24个月数据
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, i);
                String yearMonth = DateUtil.format(calendar.getTime(), DatePattern.NORM_MONTH_PATTERN);
                String [] yearMonthArr = yearMonth.split("-");
                String year = yearMonthArr[0];
                String month = yearMonthArr[1];
                String fileName = "物流发货统计对账表" + month + "月-" + year + ".xlsx";
                String path = prePath + "/" + year + "/" + year + "年物流发货统计对账表/物流发货对账表（总表）/";

                //读取共享网盘文件并处理数据
                ResponseData resp = dealSmbExcel(fileName, path, yearMonth);
                if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                    msgInfo.append(resp.getMessage());
                }
            }
            if(StringUtils.isNotBlank(msgInfo)){
                return ResponseData.error(msgInfo.toString());
            }
            return ResponseData.success();
        }
    }

    /**
     * 读取共享网盘文件并处理数据
     * @param fileName
     * @param path
     * @param dataMonths
     */
    public ResponseData dealSmbExcel(String fileName, String path, String dataMonths){
        BufferedInputStream buffer = null;
        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);
            SmbFile smbFilePath = new SmbFile("smb://"+ url + path, auth);
            SmbFile smbFile = new SmbFile("smb://"+ url + path + fileName, auth);
            log.info("读取[{}]月份网盘Excel数据，文件路径[{}]", dataMonths, "smb://"+ url + path + fileName);
            if(!smbFilePath.exists()){
                log.error("共享网盘路径错误！文件路径[{}]", "smb://"+ url + path);
                return ResponseData.error("共享网盘路径错误！文件路径: smb://" + url + path);
            }
            if(!smbFile.exists()) {
                log.error("共享网盘文件不存在！文件名称[{}]", fileName);
                return ResponseData.error("共享网盘文件不存在！文件名称:" + fileName);
            }

            buffer = new BufferedInputStream(smbFile.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LogisticsSettlementDetailImportParam>();
            EasyExcel.read(buffer, LogisticsSettlementDetailImportParam.class, listener).sheet().headRowNumber(2).doRead();

            List<LogisticsSettlementDetailImportParam> dataList = listener.getDataList();
            if(CollectionUtil.isEmpty(dataList)){
                log.error("读取物流实际结算对账数据为空！");
                return ResponseData.error("读取物流实际结算对账数据为空！");
            }
            log.info("读取网盘Excel数据[{}]", JSONObject.toJSON(dataList));

            //导入数据处理
            //导入Excel发货批次号维度判断值是否全填，全部有值对账状态则为：已对账，否则为：未对账
            Map<String, String> billStatusMap = new HashMap<>();
            logisticsSettlementService.dealDetailImportParam(dataMonths, dataList, billStatusMap);

            //明细数据处理
            logisticsSettlementService.dealDetail(dataMonths, dataList, billStatusMap);
            return ResponseData.success();
        } catch (Exception e) {
            log.error("读取物流实际结算对账数据处理异常！", e);
            return ResponseData.error("读取物流实际结算对账数据处理异常！异常信息：" + e.getMessage());
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("物流实际结算备注导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 初始处理Excel数据
     * @param dataMonths
     * @param dataList
     * @param billStatusMap
     */
    @Override
    @DataSource(name = "finance")
    public void dealDetailImportParam(String dataMonths, List<LogisticsSettlementDetailImportParam> dataList, Map<String, String> billStatusMap){
        //给相同发货批次号的数据加上序号
        Map<String, Integer> shipmentNumSeqNoMap = new HashMap<>();
        for (LogisticsSettlementDetailImportParam detailReportParam : dataList) {
            //将读取Excel的字符类型转为数字类型
            if(StringUtils.isNotBlank(detailReportParam.getLogisticsCountFeeStr()) && !detailReportParam.getLogisticsCountFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setLogisticsCountFee(new BigDecimal(detailReportParam.getLogisticsCountFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getUnitPriceStr()) && !detailReportParam.getUnitPriceStr().contains(SPECIAL_STR)){
                detailReportParam.setUnitPrice(new BigDecimal(detailReportParam.getUnitPriceStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getLogisticsFeeStr()) && !detailReportParam.getLogisticsFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setLogisticsFee(new BigDecimal(detailReportParam.getLogisticsFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getDtpStr()) && !detailReportParam.getDtpStr().contains(SPECIAL_STR)){
                detailReportParam.setDtp(new BigDecimal(detailReportParam.getDtpStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getTariffFeeStr()) && !detailReportParam.getTariffFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setTariffFee(new BigDecimal(detailReportParam.getTariffFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getClearTariffFeeStr()) && !detailReportParam.getClearTariffFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setClearTariffFee(new BigDecimal(detailReportParam.getClearTariffFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getBusySeasonFeeStr()) && !detailReportParam.getBusySeasonFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setBusySeasonFee(new BigDecimal(detailReportParam.getBusySeasonFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getOilFeeStr()) && !detailReportParam.getOilFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setOilFee(new BigDecimal(detailReportParam.getOilFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getOthersFeeStr()) && !detailReportParam.getOthersFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setOthersFee(new BigDecimal(detailReportParam.getOthersFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getProductFeeStr()) && !detailReportParam.getProductFeeStr().contains(SPECIAL_STR)){
                detailReportParam.setProductFee(new BigDecimal(detailReportParam.getProductFeeStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getChangeTaxStr()) && !detailReportParam.getChangeTaxStr().contains(SPECIAL_STR)){
                detailReportParam.setChangeTax(new BigDecimal(detailReportParam.getChangeTaxStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getDutyStr()) && !detailReportParam.getDutyStr().contains(SPECIAL_STR)){
                detailReportParam.setDuty(new BigDecimal(detailReportParam.getDutyStr()));
            }
            if(StringUtils.isNotBlank(detailReportParam.getVatStr()) && !detailReportParam.getVatStr().contains(SPECIAL_STR)){
                detailReportParam.setVat(new BigDecimal(detailReportParam.getVatStr()));
            }

            //设置数据月份
            detailReportParam.setDataMonths(dataMonths);
            detailReportParam.setDataStatus(LogisticsDataStatusEnum.NORMAL.getCode());
            //没有发货批次号的数据过滤
            String shipmentNum = detailReportParam.getShipmentNum();
            if(StringUtils.isEmpty(shipmentNum)){
                continue;
            }
            Integer seqNo = shipmentNumSeqNoMap.get(shipmentNum) == null ? 1 : shipmentNumSeqNoMap.get(shipmentNum) + 1;
            detailReportParam.setSeqNo(seqNo);
            shipmentNumSeqNoMap.put(shipmentNum, seqNo);

            if(billStatusMap.keySet().contains(shipmentNum)){
                if(StringUtils.isBlank(detailReportParam.getLogisticsErpDate())
                        || StringUtils.isBlank(detailReportParam.getLogisticsBillOrder())
                        || StringUtils.isBlank(detailReportParam.getTaxErpDate())
                        || StringUtils.isBlank(detailReportParam.getTaxBillOrder())){
                    billStatusMap.put(shipmentNum, "未对账");
                }
            } else {
                if(StringUtils.isBlank(detailReportParam.getLogisticsErpDate())
                        || StringUtils.isBlank(detailReportParam.getLogisticsBillOrder())
                        || StringUtils.isBlank(detailReportParam.getTaxErpDate())
                        || StringUtils.isBlank(detailReportParam.getTaxBillOrder())){
                    billStatusMap.put(shipmentNum, "未对账");
                } else {
                    billStatusMap.put(shipmentNum, "已对账");
                }
            }

            //获取ERP汇率数据并计算合计
            MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);//四舍五入保留4位小数
            Boolean logisticsRate = false;//是否有物流汇率
            Boolean hasLogisticsFeeVal = true;//物流相关费是否都有值
            BigDecimal totalLogisticsFee = null;//物流相关费（根据物流费币制通过汇率计算）
            if(detailReportParam.getLogisticsFee() == null
                    && detailReportParam.getDtp() == null
                    && detailReportParam.getTariffFee() == null
                    && detailReportParam.getClearTariffFee() == null
                    && detailReportParam.getBusySeasonFee() == null
                    && detailReportParam.getOilFee() == null
                    && detailReportParam.getOthersFee() == null
                    && detailReportParam.getProductFee() == null){
                hasLogisticsFeeVal = false;
            }
            String logisticsCurrency = detailReportParam.getLogisticsCurrency();
            if(hasLogisticsFeeVal && StringUtils.isNotBlank(logisticsCurrency)){
                BigDecimal logisticsFee = detailReportParam.getLogisticsFee() == null ? BigDecimal.ZERO : detailReportParam.getLogisticsFee();
                BigDecimal dtp = detailReportParam.getDtp() == null ? BigDecimal.ZERO : detailReportParam.getDtp();
                BigDecimal tariffFee = detailReportParam.getTariffFee() == null ? BigDecimal.ZERO : detailReportParam.getTariffFee();
                BigDecimal clearTariffFee = detailReportParam.getClearTariffFee() == null ? BigDecimal.ZERO : detailReportParam.getClearTariffFee();
                BigDecimal busySeasonFee = detailReportParam.getBusySeasonFee() == null ? BigDecimal.ZERO : detailReportParam.getBusySeasonFee();
                BigDecimal oilFee = detailReportParam.getOilFee() == null ? BigDecimal.ZERO : detailReportParam.getOilFee();
                BigDecimal othersFee = detailReportParam.getOthersFee() == null ? BigDecimal.ZERO : detailReportParam.getOthersFee();
                BigDecimal productFee = detailReportParam.getProductFee() == null ? BigDecimal.ZERO : detailReportParam.getProductFee();
                totalLogisticsFee = logisticsFee.add(dtp).add(tariffFee).add(clearTariffFee).add(busySeasonFee).add(oilFee).add(othersFee).add(productFee);
                if("CNY".equals(logisticsCurrency.toUpperCase())){
                    logisticsRate = true;
                }else{
                    FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
                    rateParam.setOriginalCurrency(logisticsCurrency.toUpperCase());
                    rateParam.setEffectDate(DateUtil.parse(detailReportParam.getShipmentDate(), "yyyy/MM/dd"));
                    FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
                    if(fixedExchangeRate != null && fixedExchangeRate.getDirectRate() != null){
                        totalLogisticsFee.multiply(fixedExchangeRate.getDirectRate());
                        logisticsRate = true;
                    }
                }
            }

            Boolean taxRate = false;//是否有税费汇率
            Boolean hasTaxFeeVal = true;//税费相关费是否都有值
            BigDecimal totalTaxFee = null;//税费相关费（根据税费币制通过汇率计算）
            if(detailReportParam.getChangeTax() == null && detailReportParam.getDuty() == null){
                hasTaxFeeVal = false;
            }
            String taxCurrency = detailReportParam.getTaxCurrency();
            if(hasTaxFeeVal && StringUtils.isNotBlank(taxCurrency)){
                BigDecimal changeTax = detailReportParam.getChangeTax() == null ? BigDecimal.ZERO : detailReportParam.getChangeTax();
                BigDecimal duty = detailReportParam.getDuty() == null ? BigDecimal.ZERO : detailReportParam.getDuty();
                totalTaxFee = changeTax.add(duty);
                if("CNY".equals(taxCurrency.toUpperCase())){
                    taxRate = true;
                }else{
                    FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
                    rateParam.setOriginalCurrency(taxCurrency.toUpperCase());
                    rateParam.setEffectDate(DateUtil.parse(detailReportParam.getShipmentDate(), "yyyy/MM/dd"));
                    FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
                    if(fixedExchangeRate == null && fixedExchangeRate.getDirectRate() != null){
                        totalTaxFee.multiply(fixedExchangeRate.getDirectRate());
                        taxRate = true;
                    }
                }
            }

            //计算合计
            if((hasLogisticsFeeVal || hasTaxFeeVal) && ((hasLogisticsFeeVal && logisticsRate) || (hasTaxFeeVal && taxRate))){
                totalLogisticsFee = totalLogisticsFee == null ? BigDecimal.ZERO : totalLogisticsFee;
                totalTaxFee = totalTaxFee == null ? BigDecimal.ZERO : totalTaxFee;
                BigDecimal totalFee = totalLogisticsFee.add(totalTaxFee, mathContext);
                detailReportParam.setTotalFee(totalFee);
            }
        }
    }

    /**
     * 处理明细数据
     * @param dataMonths
     * @param dataList
     * @param billStatusMap
     */
    @Override
    @DataSource(name = "logistics")
    @Transactional
    public void dealDetail(String dataMonths, List<LogisticsSettlementDetailImportParam> dataList, Map<String, String> billStatusMap) throws ParseException {
        //更新主表系统自动对账状态
        List<LogisticsSettlement> billStatusList = new ArrayList<>();
        //新增明细集合
        List<LogisticsSettlementDetail> insertDetailList = new ArrayList<>();
        //更新明细集合
        List<LogisticsSettlementDetail> updateDetailList = new ArrayList<>();
        //更新明细修改记录集合
        List<LogisticsSettlementRecord> updateRecordList = new ArrayList<>();
        for (LogisticsSettlementDetailImportParam detailReportParam : dataList) {
            //没有发货批次号的数据过滤
            String shipmentNum = detailReportParam.getShipmentNum();
            if(StringUtils.isEmpty(shipmentNum)){
                continue;
            }

            //根据发货批次号查询主表数据
            LambdaQueryWrapper<LogisticsSettlement> qw = new LambdaQueryWrapper<>();
            qw.eq(LogisticsSettlement :: getShipmentNum, shipmentNum);
            LogisticsSettlement logisticsSettlement = logisticsSettlementService.getOne(qw);
            //主数据没有发货批次号的数据过滤
            if(logisticsSettlement == null){
                log.error("未查询到发货批次号主表数据:" + shipmentNum);
                continue;
            }

            //人工操作对账后，系统不做自动对账
            if(StringUtils.isEmpty(logisticsSettlement.getOperationType())){

                //物流费ERP申请日期+物流费单据编号+税费ERP申请日期+税费单据编号都有维护后，该状态为：已对账
                String billStatus = billStatusMap.get(shipmentNum);
                if(StringUtils.isNotBlank(billStatus) && !billStatus.equals(logisticsSettlement.getBillStatus())){
                    LogisticsSettlement settlement = new LogisticsSettlement();
                    settlement.setId(logisticsSettlement.getId());
                    settlement.setBillStatus(billStatus);
                    settlement.setUpdateTime(DateUtil.date());
                    billStatusList.add(settlement);
                }
            }

            //根据发货批次号查询明细数据
            LambdaQueryWrapper<LogisticsSettlementDetail> detailQw = new LambdaQueryWrapper<>();
            detailQw.eq(LogisticsSettlementDetail :: getShipmentNum, shipmentNum)
                    .eq(LogisticsSettlementDetail :: getSeqNo, detailReportParam.getSeqNo());
            LogisticsSettlementDetail detail = logisticsSettlementDetailService.getOne(detailQw);

            if(detail == null){
                //新增
                LogisticsSettlementDetail insertDetail = new LogisticsSettlementDetail();
                BeanUtils.copyProperties(detailReportParam, insertDetail);
                insertDetail.setLogisticsErpDate(detailReportParam.getLogisticsErpDate() == null ? null : DateUtils.parseDate(detailReportParam.getLogisticsErpDate(), "yyyy/MM/dd"));
                insertDetail.setTaxErpDate(detailReportParam.getTaxErpDate() == null ? null : DateUtils.parseDate(detailReportParam.getTaxErpDate(), "yyyy/MM/dd"));
                insertDetail.setCreateTime(DateUtil.date());
                insertDetailList.add(insertDetail);
            } else{
                //比较数据是否相同,不相同则更新数据并记录更新记录,相同则不做处理
                LogisticsSettlementDetail updateDetail = new LogisticsSettlementDetail();
                BeanUtils.copyProperties(detailReportParam, updateDetail);
                updateDetail.setId(detail.getId());
                updateDetail.setLogisticsErpDate(detailReportParam.getLogisticsErpDate() == null ? null : DateUtils.parseDate(detailReportParam.getLogisticsErpDate(), "yyyy/MM/dd"));
                updateDetail.setTaxErpDate(detailReportParam.getTaxErpDate() == null ? null : DateUtils.parseDate(detailReportParam.getTaxErpDate(), "yyyy/MM/dd"));
                //忽略比较的实体属性名称
                List<String> ignoreNameList = new ArrayList<>();
                ignoreNameList.add("id");
                ignoreNameList.add("createTime");
                ignoreNameList.add("createUser");
                ignoreNameList.add("updateTime");
                ignoreNameList.add("updateUser");
                if(!compareBean(detail, updateDetail, ignoreNameList)){
                    //明细修改前的数据入库明细修改记录
                    LogisticsSettlementRecord insertRecord = new LogisticsSettlementRecord();
                    BeanUtils.copyProperties(detail, insertRecord);
                    insertRecord.setId(null);
                    insertRecord.setParentId(detail.getId());
                    //创建时间未原数据的创建时间,更新时间为当前时间
                    insertRecord.setUpdateTime(DateUtil.date());
                    updateRecordList.add(insertRecord);

                    //更新明细
                    BeanUtils.copyProperties(updateDetail, detail);
                    detail.setUpdateTime(DateUtil.date());
                    updateDetailList.add(detail);
                }
            }
        }

        //新增明细数据
        if(CollectionUtil.isNotEmpty(insertDetailList)){
            for (LogisticsSettlementDetail insertDetail : insertDetailList) {
                logisticsSettlementDetailService.save(insertDetail);
            }
        }
        //更新明细数据
        if(CollectionUtil.isNotEmpty(updateDetailList)){
            for (LogisticsSettlementDetail detail : updateDetailList) {
                logisticsSettlementDetailService.updateDetailById(detail);
            }
        }
        //新增修改记录
        if(CollectionUtil.isNotEmpty(updateRecordList)){
            for (LogisticsSettlementRecord updateRecord : updateRecordList) {
                logisticsSettlementRecordService.save(updateRecord);
            }
        }
        //处理主表系统对账状态
        if(CollectionUtil.isNotEmpty(billStatusList)){
            for (LogisticsSettlement updateSettlement : billStatusList) {
                logisticsSettlementService.updateById(updateSettlement);
            }
        }

        //根据数据月份查询明细数据,数据库有明细的Excel没有明细的,置空,并把原数据记录明细修改记录,同时重置主表物流计费两差异
        LambdaQueryWrapper<LogisticsSettlementDetail> detailQw = new LambdaQueryWrapper<>();
        detailQw.eq(LogisticsSettlementDetail :: getDataMonths, dataMonths)
                .eq(LogisticsSettlementDetail :: getDataStatus, LogisticsDataStatusEnum.NORMAL.getCode());
        List<LogisticsSettlementDetail> dataDetailList = logisticsSettlementDetailService.list(detailQw);
        Iterator<LogisticsSettlementDetail> iterator = dataDetailList.iterator();
        while(iterator.hasNext()) {
            LogisticsSettlementDetail dataDetail = iterator.next();
            for (LogisticsSettlementDetailImportParam excelDetail : dataList) {
                if(dataDetail.getShipmentNum().equals(excelDetail.getShipmentNum()) && dataDetail.getSeqNo().equals(excelDetail.getSeqNo())){
                    iterator.remove();
                }
            }
        }

        //剩下的是数据库有记录但Excel没有的，将数据状态更新为0：删除，同时记录到修改记录
        //更新主表集合
        Set<String> shipmentNumSet = new HashSet<>();
        //更新置空明细集合
        List<BigDecimal> detailIdList = new ArrayList<>();
        //更新置空明细修改记录集合
        List<LogisticsSettlementRecord> updateEmptyRecordList = new ArrayList<>();
        Iterator<LogisticsSettlementDetail> emptyIterator = dataDetailList.iterator();
        while(emptyIterator.hasNext()) {
            LogisticsSettlementDetail detail = emptyIterator.next();
            LogisticsSettlementRecord updateRecord = new LogisticsSettlementRecord();
            BeanUtils.copyProperties(detail, updateRecord);
            updateRecord.setId(null);
            updateRecord.setParentId(detail.getId());
            updateEmptyRecordList.add(updateRecord);

            detailIdList.add(detail.getId());

            //更新主表为未对账和置空操作类型
            shipmentNumSet.add(detail.getShipmentNum());
        }
        //更新明细数据
        if(CollectionUtil.isNotEmpty(detailIdList)){
            //根据ID批量更新明细数据状态为0：删除
            logisticsSettlementDetailService.batchUpdateByIds(detailIdList, dataMonths);
        }
        //新增修改记录
        if(CollectionUtil.isNotEmpty(updateEmptyRecordList)){
            for (LogisticsSettlementRecord insertRecord : updateEmptyRecordList) {
                logisticsSettlementRecordService.save(insertRecord);
            }
        }

        //更新主表为未对账和置空操作类型
        if(CollectionUtil.isNotEmpty(shipmentNumSet)){
            this.baseMapper.batchUpdateStatus(shipmentNumSet);
        }
    }

    /**
     * 比较两个实体属性值是否一致
     * @param b1 实体1
     * @param b2 实体2
     * @param ignoreNameList 忽略比较的字段名称集合
     * @param <T>
     * @return
     */
    public static <T> Boolean compareBean(T b1, T b2, List<String> ignoreNameList) {
        if(b1.getClass() != b2.getClass()){
            return false;
        }
        try {
            Boolean isSame = true;
            //获取对象所有的属性
            Class clazz = b1.getClass();
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                // 属性名
                String name = pd.getName();
                if(CollectionUtil.isNotEmpty(ignoreNameList) && ignoreNameList.contains(name)){
                    continue;
                }

                //get方法
                Method readMethod = pd.getReadMethod();
                if(readMethod.getReturnType() == BigDecimal.class){
                    //获取属性值
                    BigDecimal pValue1 = (BigDecimal) readMethod.invoke(b1);
                    BigDecimal pValue2 = (BigDecimal) readMethod.invoke(b2);
                    if(pValue1 == null && pValue2 == null){
                        continue;
                    }
                    if(pValue1 != null && pValue2 == null){
                        isSame = false;
                        break;
                    }
                    if(pValue1 == null && pValue2 != null){
                        isSame = false;
                        break;
                    }
                    if(pValue1.compareTo(pValue2) == 0){
                        continue;
                    }else{
                        isSame = false;
                        break;
                    }
                }else{
                    //获取属性值
                    Object pValue1 = readMethod.invoke(b1);
                    Object pValue2 = readMethod.invoke(b2);
                    if(pValue1 == null && pValue2 == null){
                        continue;
                    }
                    if(pValue1 != null && pValue2 == null){
                        isSame = false;
                        break;
                    }
                    if(pValue1 == null && pValue2 != null){
                        isSame = false;
                        break;
                    }
                    if(pValue1.equals(pValue2)){
                        continue;
                    }else{
                        isSame = false;
                        break;
                    }
                }
            }
            return isSame;
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData logisticsSettlement(LogisticsSettlement param) {
        //获取当前操作用户信息
        String account = LoginContext.me().getLoginUser().getAccount();
        String name = LoginContext.me().getLoginUser().getName();
        String accountAndName = account + "_" + name;
        LogisticsSettlement logisticsSettlement = this.baseMapper.selectById(param.getId());
        if(logisticsSettlement == null){
            return ResponseData.error("不存在实际结算对账表主数据");
        }
        if(StringUtils.isBlank(logisticsSettlement.getShipmentNum())){
            return ResponseData.error("不存在实际结算对账表主发货批次号");
        }

        LambdaQueryWrapper<LogisticsSettlementDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(LogisticsSettlementDetail :: getShipmentNum, logisticsSettlement.getShipmentNum());
        List<LogisticsSettlementDetail> detailList = logisticsSettlementDetailService.list(detailWrapper);
        if(CollectionUtil.isEmpty(detailList)){
            return ResponseData.error("不存在实际结算对账表明细数据，不允许操作对账");
        }

        LambdaUpdateWrapper<LogisticsSettlement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LogisticsSettlement :: getId, param.getId())
                    .set(LogisticsSettlement :: getUpdateUser, accountAndName)
                    .set(LogisticsSettlement :: getUpdateTime, DateUtil.date());
        //完成对账
        if(LogisticsOperationTypeEnum.COMPLETE.getName().equals(param.getOperationType())){
            updateWrapper.and(wrapper -> wrapper.eq(LogisticsSettlement :: getBillStatus, LogisticsOperationBillStatusEnum.NOT.getName()).or().isNull(LogisticsSettlement :: getBillStatus))
                    .set(LogisticsSettlement :: getBillStatus, LogisticsOperationBillStatusEnum.ALREADY.getName())
                    .set(LogisticsSettlement :: getOperationType, LogisticsOperationTypeEnum.COMPLETE.getName());
        }
        //重新对账
        if(LogisticsOperationTypeEnum.RESET.getName().equals(param.getOperationType())){
            updateWrapper.and(wrapper -> wrapper.eq(LogisticsSettlement :: getBillStatus, LogisticsOperationBillStatusEnum.ALREADY.getName()).or().isNull(LogisticsSettlement :: getBillStatus))
                    .set(LogisticsSettlement :: getBillStatus, LogisticsOperationBillStatusEnum.NOT.getName())
                    .set(LogisticsSettlement :: getOperationType, LogisticsOperationTypeEnum.RESET.getName());
        }
        this.baseMapper.update(null, updateWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchLogisticsSettlement(List<LogisticsSettlement> params) {
        for (LogisticsSettlement param : params) {
            this.logisticsSettlement(param);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData contractNoSelect() {
        return ResponseData.success(this.baseMapper.contractNoSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData freightCompanySelect() {
        return ResponseData.success(this.baseMapper.freightCompanySelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData transportTypeSelect() {
        return ResponseData.success(this.baseMapper.transportTypeSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData logisticsChannelSelect() {
        return ResponseData.success(this.baseMapper.logisticsChannelSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData orderTypeSelect() {
        return ResponseData.success(this.baseMapper.orderTypeSelect());
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData receiveLogisticsSettlement(List<EbmsLogisticsSettlementParam> params) {
        for (EbmsLogisticsSettlementParam param : params) {
            if(!"CNY".equals(param.getCurrency())){
                //四舍五入保留4位小数
                MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);
                param.setPredictUnitPrice(param.getDirectRate().multiply(param.getPredictUnitPrice(), mathContext));
                param.setPredictLogisticsFee(param.getDirectRate().multiply(param.getPredictLogisticsFee(), mathContext));
                param.setPredictOilFee(param.getDirectRate().multiply(param.getPredictOilFee(), mathContext));
                param.setPredictBusySeasonFee(param.getDirectRate().multiply(param.getPredictBusySeasonFee(), mathContext));
                param.setPredictOthersFee(param.getDirectRate().multiply(param.getPredictOthersFee(), mathContext));
                param.setPredictTariffFee(param.getDirectRate().multiply(param.getPredictTariffFee(), mathContext));
                param.setPredictClearTariffFee(param.getDirectRate().multiply(param.getPredictClearTariffFee(), mathContext));
//                param.setPredictTaxFee(param.getDirectRate().multiply(param.getPredictTaxFee(), mathContext));//已经是CNY
                param.setPredictTotalFee(param.getDirectRate().multiply(param.getPredictTotalFee(), mathContext));
            }

            LambdaQueryWrapper<LogisticsSettlement> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LogisticsSettlement :: getShipmentNum, param.getShipmentNum());
            LogisticsSettlement logisticsSettlement = this.baseMapper.selectOne(queryWrapper);
            if(logisticsSettlement == null){
                //新增
                LogisticsSettlement insertLogisticsSettlement = new LogisticsSettlement();
                BeanUtils.copyProperties(param, insertLogisticsSettlement);
                insertLogisticsSettlement.setBillStatus(LogisticsOperationBillStatusEnum.NOT.getName());
                this.baseMapper.insert(insertLogisticsSettlement);
            }else{
                //更新
                BeanUtils.copyProperties(param, logisticsSettlement);
                this.baseMapper.updateById(logisticsSettlement);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData deleteLogisticsSettlement(List<LogisticsSettlementDetailParam> params) {
        for (LogisticsSettlementDetailParam param : params) {
            if(StringUtils.isBlank(param.getShipmentNum())){
                return ResponseData.error("发货批次号入参为空");
            }
            LambdaQueryWrapper<LogisticsSettlement> settlementWrapper = new LambdaQueryWrapper();
            settlementWrapper.eq(LogisticsSettlement :: getShipmentNum, param.getShipmentNum());
            this.baseMapper.delete(settlementWrapper);

            LambdaQueryWrapper<LogisticsSettlementDetail> detailWrapper = new LambdaQueryWrapper();
            detailWrapper.eq(LogisticsSettlementDetail :: getShipmentNum, param.getShipmentNum());
            logisticsSettlementDetailService.remove(detailWrapper);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData refreshSignDate() {
        //获取签收日期为null的数据
        LambdaQueryWrapper<LogisticsSettlement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(LogisticsSettlement :: getSignDate);
        List<LogisticsSettlement> list = logisticsSettlementService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            List<List<LogisticsSettlement>> lists = ListUtil.split(list, 1000);
            for (List<LogisticsSettlement> subList : lists) {
                List<LogisticsSettlement> signDateList = logisticsSettlementService.getEbmsSignDate(subList);
                if(CollectionUtil.isNotEmpty(signDateList)){
                    logisticsSettlementMapper.refreshSignDate(signDateList);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogisticsSettlement> getEbmsSignDate(List<LogisticsSettlement> param){
        return logisticsSettlementMapper.getEbmsSignDate(param);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
