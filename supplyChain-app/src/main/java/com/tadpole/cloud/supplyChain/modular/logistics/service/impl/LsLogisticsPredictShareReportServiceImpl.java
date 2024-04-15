package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsPredictShareReport;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsPredictShareReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsPredictShareReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsPredictShareReportMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsPredictShareReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  物流投入预估分摊报表服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
@Slf4j
@Service
public class LsLogisticsPredictShareReportServiceImpl extends ServiceImpl<LsLogisticsPredictShareReportMapper, LsLogisticsPredictShareReport> implements ILsLogisticsPredictShareReportService {

    @Resource
    private LsLogisticsPredictShareReportMapper mapper;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLogisticsPredictShareReportParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public List<LsLogisticsPredictShareReportResult> export(LsLogisticsPredictShareReportParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile file) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            ExcelReader excelReader = EasyExcel.read(buffer).build();
            BaseExcelListener listener0 = new BaseExcelListener<LsLogisticsPredictShareReport>();
            ReadSheet readSheet0 = EasyExcel.readSheet(0).head(LsLogisticsPredictShareReport.class).registerReadListener(listener0).build();
            excelReader.read(readSheet0);
            excelReader.finish();

            List<LsLogisticsPredictShareReport> dataList0 = listener0.getDataList();
            log.info("导入物流投入预估分摊报表数据[{}]", JSONObject.toJSON(dataList0));
            if(CollectionUtil.isEmpty(dataList0)){
                return ResponseData.error("导入物流投入预估分摊报表数据为空，导入失败！");
            }
            List<LsLogisticsPredictShareReport> normalList = new ArrayList<>();
            List<LsLogisticsPredictShareReport> errorSheet0List = new ArrayList<>();
            String name = LoginContext.me().getLoginUser().getName();
            this.validExcel(dataList0, errorSheet0List, normalList, name);
            if(CollectionUtil.isEmpty(errorSheet0List)){
                this.updateBatchById(normalList);
                return ResponseData.success();
            } else {
                String fileName = dealImportErrorList(dataList0);
                return ResponseData.error(fileName);
            }
        } catch (Exception e) {
            log.error("导入物流投入预估分摊报表处理异常，导入失败！", e);
            return ResponseData.error("导入物流投入预估分摊报表处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入物流投入预估分摊报表关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList0
     * @param errorSheet0List
     * @param normalList
     * @param name
     */
    private void validExcel(List<LsLogisticsPredictShareReport> dataList0, List<LsLogisticsPredictShareReport> errorSheet0List, List<LsLogisticsPredictShareReport> normalList, String name){
        Date nowDate = DateUtil.date();
        for (LsLogisticsPredictShareReport sheet0Param: dataList0) {
            if(StringUtils.isBlank(sheet0Param.getLogisticsNo()) || sheet0Param.getPredictTaxFee() == null){
                sheet0Param.setUploadRemark("物流单号和预估税费为必填项！");
                errorSheet0List.add(sheet0Param);
                continue;
            }

            LambdaQueryWrapper<LsLogisticsPredictShareReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LsLogisticsPredictShareReport :: getLogisticsNo, sheet0Param.getLogisticsNo());
            List<LsLogisticsPredictShareReport> predictShareReportList = this.list(queryWrapper);
            if(CollectionUtil.isEmpty(predictShareReportList)){
                sheet0Param.setUploadRemark("未查询到此物流单号！");
                errorSheet0List.add(sheet0Param);
                continue;
            }
            if("0".equals(predictShareReportList.get(0).getPredictTaxFeeType()) && predictShareReportList.get(0).getPredictTaxFee() != null){
                sheet0Param.setUploadRemark("此物流单号的预估税费为系统生成且已经有值，不能编辑！");
                errorSheet0List.add(sheet0Param);
                continue;
            }
            for (LsLogisticsPredictShareReport predictShareReport : predictShareReportList) {
                LsLogisticsPredictShareReport updatePredictShareReport = new LsLogisticsPredictShareReport();
                updatePredictShareReport.setId(predictShareReport.getId());
                updatePredictShareReport.setPredictTaxFee(sheet0Param.getPredictTaxFee());
                updatePredictShareReport.setPredictTaxFeeType("1");//导入
                updatePredictShareReport.setUpdateTime(nowDate);
                updatePredictShareReport.setUpdateUser(name);
                normalList.add(updatePredictShareReport);
            }
        }
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<LsLogisticsPredictShareReport> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        ExcelWriter excelWriter = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            excelWriter = EasyExcel.write(out).build();
            WriteSheet writeSheet0 = EasyExcel.writerSheet(0).head(LsLogisticsPredictShareReport.class).build();
            excelWriter.write(errorList, writeSheet0);
        } catch (FileNotFoundException e) {
            log.error("导入物流投入预估分摊报表异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("导入物流投入预估分摊报表异常数据导出流关闭异常", e);
                }
            }
            if(excelWriter != null){
                excelWriter.finish();
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData generateBtpReport() {
        String dataMonth = DateUtil.format(DateUtil.offsetMonth(DateUtil.date(), -1), DatePattern.NORM_MONTH_PATTERN);
        mapper.generateBtpReport(dataMonth);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData generateEBMSReport(List<LsLogisticsPredictShareReport> param) {
        log.info("接收EBMS物流投入预估分摊报表，记录数[{}]", param.size());
        Date nowDate = DateUtil.date();
        String dataMonth = DateUtil.format(DateUtil.offsetMonth(nowDate, -1), DatePattern.NORM_MONTH_PATTERN);
        String dataMonthDay = dataMonth + "-01";//上个月1号

        //查询固定汇率
        FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
        rateParam.setEffectDate(DateUtil.parse(dataMonthDay, DatePattern.NORM_DATE_PATTERN));
        List<FixedExchangeRate> rateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
        Map<String, List<FixedExchangeRate>> rateListMap = rateList.stream().collect(Collectors.groupingBy(FixedExchangeRate :: getOriginalCurrency, LinkedHashMap::new, Collectors.toList()));
        List<LsLogisticsPredictShareReport> saveList = new ArrayList<>();
        List<LsLogisticsPredictShareReport> updateList = new ArrayList<>();
        for (LsLogisticsPredictShareReport predictShareReport : param) {
            if(!dataMonth.equals(predictShareReport.getDataMonth())){
                return ResponseData.error("数据月份不正确，正确的数据月份应为：" + dataMonth);
            }
            if(StringUtils.isBlank(predictShareReport.getCurrency())){
                return ResponseData.error("币别不能为空");
            }
            //非CNY币制转换成CNY
            if(!"CNY".equals(predictShareReport.getCurrency())){
                List<FixedExchangeRate> fixedExchangeRateList = rateListMap.get(predictShareReport.getCurrency());
                if(CollectionUtil.isEmpty(fixedExchangeRateList)){
                    log.error("接收EBMS物流投入预估分摊报表" + dataMonthDay + "，币别：" + predictShareReport.getCurrency() + "汇率信息不存在！");
                    return ResponseData.error(dataMonthDay + "，币别：" + predictShareReport.getCurrency() + "汇率信息不存在！");
                }
                predictShareReport.setTransportCost(fixedExchangeRateList.get(0).getDirectRate().multiply(predictShareReport.getTransportCost()).setScale(2, RoundingMode.HALF_UP));
            }

            LambdaQueryWrapper<LsLogisticsPredictShareReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LsLogisticsPredictShareReport :: getLogisticsNo, predictShareReport.getLogisticsNo())
                    .eq(LsLogisticsPredictShareReport :: getOrderNo, predictShareReport.getOrderNo())
                    .eq(LsLogisticsPredictShareReport :: getSku, predictShareReport.getSku())
                    .eq(StringUtils.isNotBlank(predictShareReport.getPackDirectCode()), LsLogisticsPredictShareReport :: getPackDirectCode, predictShareReport.getPackDirectCode())
                    .eq(LsLogisticsPredictShareReport :: getDataType, "EBMS")
                    .eq(LsLogisticsPredictShareReport :: getDataMonth, dataMonth);
            LsLogisticsPredictShareReport shareReport = this.getOne(queryWrapper);
            if(shareReport == null){
                predictShareReport.setDataType("EBMS");
                predictShareReport.setPredictTaxFeeType("0");
                saveList.add(predictShareReport);
            }else{
                predictShareReport.setId(shareReport.getId());
                predictShareReport.setUpdateTime(nowDate);
                //EBMS预估税费有值且JCERP人工导入值的情况，以系统数据为准进行覆盖
                if(predictShareReport.getPredictTaxFee() != null && "1".equals(shareReport.getPredictTaxFeeType())){
                    predictShareReport.setPredictTaxFeeType("0");//系统
                }
                //EBMS预估税费没有值且JCERP人工导入值的情况，则以人工导入为准
                if(predictShareReport.getPredictTaxFee() == null && "1".equals(shareReport.getPredictTaxFeeType())){
                    predictShareReport.setPredictTaxFee(shareReport.getPredictTaxFee());
                }
                updateList.add(predictShareReport);
            }
        }
        if(CollectionUtil.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }
        if(CollectionUtil.isNotEmpty(updateList)){
            this.updateBatchById(updateList);
        }
        log.info("接收EBMS物流投入预估分摊报表执行完成");
        return ResponseData.success();
    }

}
