package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockApplicationRecordMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApplicationRecordParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApplicationRecordAnalysisResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApplicationRecordResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApplicationRecordService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Service
public class StockApplicationRecordServiceImpl extends ServiceImpl<StockApplicationRecordMapper, StockPurchaseOrders> implements IStockApplicationRecordService {
    @Resource
    private StockApplicationRecordMapper mapper;

    @Autowired
    ResourceLoader resourceLoader;

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    //@Transactional(rollbackFor = Exception.class)

    @Override
    @DataSource(name = "stocking")
    public ResponseData queryListPage(StockApplicationRecordParam param) {
        Page pageContext = param.getPageContext();
        IPage pages = this.baseMapper.queryListPage(pageContext, param);
        return ResponseData.success( new PageResult<>(pages));
    }



    @Override
    @DataSource(name = "stocking")
    public void exportExcel(HttpServletResponse response, StockApplicationRecordParam param) throws Exception {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        List<StockApplicationRecordResult> resultList = this.baseMapper.queryListPage(pageContext, param).getRecords();
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("未找到该对应的数据");
        }

        String filename = "运营核对数据";
        String modelPath = "classpath:template/stockApplicationRecordExcel.xlsx";//模板所在路径

        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        //StockApplicationRecordResult info = resultList.get(0);

        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(resultList, fillConfig, writeSheet);
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            //log.error("事业部审核导出异常:{}", JSONUtil.toJsonStr(e));
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


    @Override
    @DataSource(name = "stocking")
    public void exportExcelAnalysis(HttpServletResponse response, StockApplicationRecordParam param) throws Exception {

        List<StockApplicationRecordAnalysisResult> resultList = this.baseMapper.queryAnalysisResultExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("未找到该对应的数据");
        }

        String filename = "计划分析数据";
        String modelPath = "classpath:template/stockApplicationAnalysisTemplatel.xlsx";//模板所在路径

        org.springframework.core.io.Resource resource = resourceLoader.getResource(modelPath);
        InputStream inputStream = resource.getInputStream();
        //StockApplicationRecordResult info = resultList.get(0);

        ExcelWriter excelWriter = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            String encoderFileName = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + encoderFileName + ".xlsx");

            excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.FALSE).build();
            excelWriter.fill(resultList, fillConfig, writeSheet);
            Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            workbook.setForceFormulaRecalculation(true);

        } catch (Exception e) {
            //log.error("事业部审核导出异常:{}", JSONUtil.toJsonStr(e));
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
}
