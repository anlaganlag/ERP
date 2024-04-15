package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopifySettlementReportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.ShopEntityConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtRakutenSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopifySettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtShopifySettlementReportService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.PersonAllocService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.SiteConsumer;


/**
 * Shopify小平台结算报告;(LT_SHOPIFY_SETTLEMENT_REPORT)表控制层
 * @author : LSY
 * @date : 2023-12-23
 */
@Api(tags = "小平台结算报告-Shopify平台")
@RestController
@ApiResource(name = "小平台结算报告-Shopify平台", path="/ltShopifySettlementReport")
public class LtShopifySettlementReportController{
     public final String baseName = "小平台结算报告-Shopify平台";
     public final String queryByIdFunName = baseName+"--通过ID查询Shopify小平台结算报告";
     public final String paginQueryFunName = baseName+"--分页查询Shopify小平台结算报告";
     public final String addFunName = baseName+"--新增Shopify小平台结算报告";
     public final String editFunName = baseName+"--更新Shopify小平台结算报告";
     public final String exportFunName = baseName+"--按查询条件导出Shopify小平台结算报告";
     public final String deleteByIdFunName = baseName+"--通过主键删除Shopify小平台结算报告据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除Shopify小平台结算报告";
    @Resource
    private LtShopifySettlementReportService ltShopifySettlementReportService;


     @Resource
     private PersonAllocService personAllocService;

     @Resource
     private ShopEntityConsumer shopEntityConsumer;

     @Resource
     private SiteConsumer siteConsumer;


    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=LtShopifySettlementReport.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid", requiredLogin = false)
    public ResponseData  queryById(String id){
        return ResponseData.success(ltShopifySettlementReportService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param ltShopifySettlementReportParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=LtShopifySettlementReport.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody LtShopifySettlementReportParam ltShopifySettlementReportParm ){
        //1.分页参数
       Page page = ltShopifySettlementReportParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<LtShopifySettlementReportResult> pageResult = ltShopifySettlementReportService.paginQuery(ltShopifySettlementReportParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param ltShopifySettlementReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =LtShopifySettlementReport.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody LtShopifySettlementReport ltShopifySettlementReport){
        return ResponseData.success(ltShopifySettlementReportService.insert(ltShopifySettlementReport));
    }
    
    /** 
     * 更新数据
     *
     * @param ltShopifySettlementReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =LtShopifySettlementReport.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody LtShopifySettlementReportParam ltShopifySettlementReport){
        LtShopifySettlementReport result = ltShopifySettlementReportService.update(ltShopifySettlementReport);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String id){
         if (ltShopifySettlementReportService.deleteById(id)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  idList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> idList){
      if (Objects.isNull(idList) || idList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(ltShopifySettlementReportService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param ltShopifySettlementReportParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =LtShopifySettlementReport.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody LtShopifySettlementReportParam ltShopifySettlementReportParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        List<LtShopifySettlementReport> records = ltShopifySettlementReportService.paginExport(ltShopifySettlementReportParm);

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("Shopify小平台结算报告.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LtShopifySettlementReport.class).sheet("Shopify小平台结算报告").doWrite(records);
        return ResponseData.success();
    }


     @ParamValidator
     @PostResource(name = "Shopify小平台结算报告-导入", path = "/import")
     @ApiOperation(value = "Shopify小平台结算报告-导入")
     @BusinessLog(title = "Shopify小平台结算报告-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData importSettlementReportAdjust(@RequestParam(value = "file", required = true) MultipartFile file) {
         List<String> departmentList= personAllocService.getDepart();
         List<String> teamList= personAllocService.getTeam();


         List<String> shopList= shopEntityConsumer.shopList();
        shopList.add("Glucoracy");
        shopList.add("Pawaboo");
         if (ObjectUtil.isNotEmpty(shopList)) {
             Collections.sort(shopList);
             ;
         }

         List<String> siteList = siteConsumer.siteList();
         List<String> siteListFromShops= shopEntityConsumer.siteList();
         if (ObjectUtil.isNotEmpty(siteListFromShops)) {
             siteList.addAll(siteListFromShops);
         }
         if (ObjectUtil.isNotEmpty(siteList)) {
             siteList = siteList.stream()
                     .distinct()
                     .sorted()
                     .collect(Collectors.toList());              ;
         }
         return ltShopifySettlementReportService.importSettlementReport(file,departmentList,teamList,shopList,siteList);

     }


     @PostResource(name = "自动计算人工成本", path = "/autoCalPeopleCost",requiredPermission = false)
     @ApiOperation(value = "自动计算人工成本", response =  LtShopifySettlementReportResult.class)
     public ResponseData autoCalPeopleCost(@RequestBody LtShopifySettlementReportParam param) throws Exception {
         return  ltShopifySettlementReportService.autoCalPeopleCost(param);
     }


     @PostResource(name = "下载模板", path = "/downloadTemplate", requiredLogin = false)
     @ApiOperation(value = "下载模板", response = LtShopifySettlementReport.class)
     @BusinessLog(title = "下载模板", opType = LogAnnotionOpTypeEnum.EXPORT)
     public ResponseData downloadTemplate(@RequestBody LtShopifySettlementReportParam ltWalmartSettlementReportParm, HttpServletResponse response) throws IOException {
         List<LtShopifySettlementReportExport> records = ltShopifySettlementReportService.downloadExport(ltWalmartSettlementReportParm);

         response.addHeader("Content-Disposition", "attachment;filename=" + new String("小平台结算报告-Shopify下载模板.xlsx".getBytes("utf-8"), "ISO8859-1"));
         EasyExcel.write(response.getOutputStream(), LtShopifySettlementReportExport.class).sheet("").doWrite(records);
         return ResponseData.success();
     }

     @PostResource(name = "是否导入报告", path = "/haveReport", requiredPermission = false, requiredLogin = false)
     @ApiOperation(value = "是否导入报告", response = StatementIncomeResult.class)
     @BusinessLog(title = "是否导入报告", opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData haveReport(@RequestBody LtShopifySettlementReportParam param) {
         return ltShopifySettlementReportService.haveReport(param);
     }

     @PostResource(name = "插入架构", path = "/insertStructure", requiredPermission = false, requiredLogin = false)
     @ApiOperation(value = "插入架构", response = StatementIncomeResult.class)
     @BusinessLog(title = "插入架构", opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData insertStructure(@RequestBody LtShopifySettlementReportParam param) {
         return ltShopifySettlementReportService.insertStructure(param);
     }

}