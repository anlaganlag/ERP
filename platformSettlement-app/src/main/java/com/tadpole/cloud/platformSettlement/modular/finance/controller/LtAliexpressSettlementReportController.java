package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LtAliexpressSettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAliexpressSettlementReportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bSettlementReportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAliexpressSettlementReportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.ShopEntityConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtAliexpressSettlementReportService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.PersonAllocService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.SiteConsumer;


;

 /**
 * ;(LT_ALIEXPRESS_SETTLEMENT_REPORT)表控制层
 * @author : LSY
 * @date : 2023-12-22
 */
@Api(tags = "小平台结算报告-速卖通平台")
@RestController
@ApiResource(name = "小平台结算报告-速卖通平台", path="/ltAliexpressSettlementReport")
public class LtAliexpressSettlementReportController{
     public final String baseName = "小平台结算报告-速卖通平台";
     public final String queryByIdFunName = baseName+"--通过ID查询";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增";
     public final String editFunName = baseName+"--更新";
     public final String exportFunName = baseName+"--按查询条件导出";
     public final String deleteByIdFunName = baseName+"--通过主键删除据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除";
    @Resource
    private LtAliexpressSettlementReportService ltAliexpressSettlementReportService;

     @Resource
     private PersonAllocService personAllocService;

     @Resource
     private ShopEntityConsumer shopEntityConsumer;

     @Resource
     private SiteConsumer siteConsumer;




    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=LtAliexpressSettlementReport.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId", requiredLogin = false)
    public ResponseData  queryById(String undefinedId){
        return ResponseData.success(ltAliexpressSettlementReportService.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param ltAliexpressSettlementReportParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=LtAliexpressSettlementReport.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody LtAliexpressSettlementReportParam ltAliexpressSettlementReportParm ){
        //1.分页参数
       Page page = ltAliexpressSettlementReportParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<LtAliexpressSettlementReportResult> pageResult = ltAliexpressSettlementReportService.paginQuery(ltAliexpressSettlementReportParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param ltAliexpressSettlementReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =LtAliexpressSettlementReport.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody LtAliexpressSettlementReport ltAliexpressSettlementReport){
        return ResponseData.success(ltAliexpressSettlementReportService.insert(ltAliexpressSettlementReport));
    }
    
    /** 
     * 更新数据
     *
     * @param ltAliexpressSettlementReport 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =LtAliexpressSettlementReport.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody LtAliexpressSettlementReportParam ltAliexpressSettlementReport){
        LtAliexpressSettlementReport result = ltAliexpressSettlementReportService.update(ltAliexpressSettlementReport);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String undefinedId){
         if (ltAliexpressSettlementReportService.deleteById(undefinedId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  undefinedIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> undefinedIdList){
      if (Objects.isNull(undefinedIdList) || undefinedIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(ltAliexpressSettlementReportService.deleteBatchIds(undefinedIdList));
     }
     /**
     * 导出
     *
     * @param ltAliexpressSettlementReportParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =LtAliexpressSettlementReport.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody LtAliexpressSettlementReportParam ltAliexpressSettlementReportParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        List<LtAliexpressSettlementReport> records = ltAliexpressSettlementReportService.paginExport(ltAliexpressSettlementReportParm);

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("小平台结算报告-速卖通平台.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LtAliexpressSettlementReport.class).sheet("").doWrite(records);
        return ResponseData.success();
    }

     @ParamValidator
     @PostResource(name = "速卖通小平台结算报告-导入", path = "/import")
     @ApiOperation(value = "速卖通小平台结算报告-导入")
     @BusinessLog(title = "速卖通小平台结算报告-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
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
         return ltAliexpressSettlementReportService.importSettlementReport(file,departmentList,teamList,shopList,siteList);

     }


//     @PostResource(name = "计算人工成", path = "/getPeopleCost",requiredPermission = false)
//     @ApiOperation(value = "计算人工成", response = SettlementReportAdjustResult.class)
//     public ResponseData getPeopleCost(LtAliexpressSettlementReportParam param) throws Exception {
//         return  ltAliexpressSettlementReportService.getPeopleCost(param);
//     }

     @PostResource(name = "下载模板", path = "/downloadTemplate", requiredLogin = false)
     @ApiOperation(value = "下载模板", response = LtAliexpressSettlementReport.class)
     @BusinessLog(title = "下载模板", opType = LogAnnotionOpTypeEnum.EXPORT)
     public ResponseData downloadTemplate(@RequestBody LtAliexpressSettlementReportParam param, HttpServletResponse response) throws IOException {
         List<LtAliexpressSettlementReportExport> records = ltAliexpressSettlementReportService.downloadExport(param);

         response.addHeader("Content-Disposition", "attachment;filename=" + new String("小平台结算报告-速卖通下载模板.xlsx".getBytes("utf-8"), "ISO8859-1"));
         EasyExcel.write(response.getOutputStream(), LtAliexpressSettlementReportExport.class).sheet("").doWrite(records);
         return ResponseData.success();
     }

     @PostResource(name = "自动计算人工成本", path = "/autoCalPeopleCost",requiredPermission = false)
     @ApiOperation(value = "自动计算人工成本", response =  LtAliexpressSettlementReportResult.class)
     public ResponseData autoCalPeopleCost(@RequestBody LtAliexpressSettlementReportParam param) throws Exception {
         return  ltAliexpressSettlementReportService.autoCalPeopleCost(param);
     }

     @PostResource(name = "是否导入报告", path = "/haveReport", requiredPermission = false, requiredLogin = false)
     @ApiOperation(value = "是否导入报告", response = LtAliexpressSettlementReportResult.class)
     @BusinessLog(title = "是否导入报告", opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData haveReport(@RequestBody LtAliexpressSettlementReportParam param) {
         return ltAliexpressSettlementReportService.haveReport(param);
     }

     @PostResource(name = "插入架构", path = "/insertStructure", requiredPermission = false, requiredLogin = false)
     @ApiOperation(value = "插入架构", response = StatementIncomeResult.class)
     @BusinessLog(title = "插入架构", opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData insertStructure(@RequestBody LtAliexpressSettlementReportParam param) {
         return ltAliexpressSettlementReportService.insertStructure(param);
     }

}