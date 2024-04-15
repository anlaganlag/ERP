package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopReportResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
* 资源-店铺;(Tb_Com_Shop)--控制层
* @author : LSY
* @date : 2023-7-28
*/
@Slf4j
@Api(tags = "资源-店铺报告接口")
@RestController
@ApiResource(name="资源-店铺报告" , path="/tbComShopReport")
public class TbComShopReportController {
    public final String baseName = "资源-店铺报告";
    public final String queryShopNameList = baseName+"--店铺名称下拉List";
    public final String queryComEntity = baseName+"--查询公司实体信息";
    public final String queryTaxNum = baseName+"--查询税号信息";
    public final String paginQueryFunName = baseName+"--查询物流店铺报告";
    public final String paginQueryLogFunName = baseName+"--物流店铺报告分页查询";
    public final String exportFunName = baseName+"--按查询条件导出数据";


   @Resource
   private TbComShopService tbComShopService;


    @Resource
    private TbComEntityService tbComEntityService;


    @Resource
    private TbComTaxNumService tbComTaxNumService;


   /**
    * 分页查询
    *
    * @param tbComShopParam 筛选条件
    * @return 查询结果
    */
   @ApiOperation(value =paginQueryFunName ,response =TbComShopResult.class)
   @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
   @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
   public ResponseData paginQuery(@RequestBody TbComShopParam tbComShopParam){
       //1.分页参数
       Page page = tbComShopParam.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
       //2.分页查询
       /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
       Page<TbComShopReportResult> pageResult = tbComShopService.paginQueryShopReport(tbComShopParam, current,size);
       //3. 分页结果组装
       return ResponseData.success(new PageResult<>(pageResult));
   }


//    /**
//     * 分页查询
//     *
//     * @param tbComShopParam 筛选条件
//     * @return 查询结果
//     */
//    @ApiOperation(value =paginQueryLogFunName ,response =TbComShopResult.class)
//    @PostResource(name = paginQueryLogFunName, path = "/logList", requiredLogin = true, menuFlag = true)
//    @BusinessLog(title = paginQueryLogFunName,opType = LogAnnotionOpTypeEnum.QUERY)
//    public ResponseData paginLogQuery(@RequestBody TbComShopParam tbComShopParam){
//        //1.分页参数
//        Page page = tbComShopParam.getPageContext();
//        long current = page.getCurrent();
//        long size = page.getSize();
//        //2.分页查询
//        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
//        Page<TbComShopLogisticsReportResult> pageResult = tbComShopService.TbComShopLogisticsReportResult(tbComShopParam, current,size);
//        //3. 分页结果组装
//        return ResponseData.success(new PageResult<>(pageResult));
//    }




   /**
    * 导出
    *
    * @param tbComShopParam 筛选条件
    * @return ResponseData
    */
   @PostResource(name = exportFunName, path = "/export" )
   @ApiOperation(value = exportFunName, response =TbComShop.class)
   @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
   public ResponseData export(@RequestBody TbComShopParam tbComShopParam, HttpServletResponse response) throws IOException {
       //1.分页参数
       long current = 1L;
       long size = Integer.MAX_VALUE;
       //2.分页查询
       /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
       Page<TbComShopReportResult> pageResult = tbComShopService.paginQueryShopReport(tbComShopParam, current,size);
      List<TbComShopReportResult> records=  pageResult.getRecords();
       if (Objects.isNull(records) || records.size()==0) {
           return    ResponseData.success();
       }
       response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺.xlsx".getBytes("utf-8"),"ISO8859-1"));
       EasyExcel.write(response.getOutputStream(), TbComShopReportResult.class).sheet("资源-店铺").doWrite(records);
       return ResponseData.success();
   }


//    /**
//     * 店铺名称下拉
//     *
//     * @param filter 是否过滤出店铺正在变更收款账号的店铺名称，默认不需要过滤
//     * @return 实例对象
//     */
//    @ApiOperation(value =queryShopNameList,response =TbComShop.class)
//    @GetResource(name = queryShopNameList, path = "/shopNameList")
//    @BusinessLog(title =queryShopNameList,opType = LogAnnotionOpTypeEnum.QUERY)
//    public ResponseData shopNameList(@RequestParam(required = false) Boolean filter){
//        if (ObjectUtil.isNull(filter)) {
//            filter = false;
//        }
//        return ResponseData.success(tbComShopService.shopNameList(filter));
//    }



    @ApiOperation(value =queryComEntity,response =TbComShop.class)
    @GetResource(name = queryComEntity, path = "/queryComEntityInfo" )
    @BusinessLog(title =queryComEntity,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryComEntityInfo(@RequestParam String comNameCn){
        return ResponseData.success(tbComEntityService.queryById(comNameCn));
    }


    @ApiOperation(value =queryTaxNum,response =TbComShop.class)
    @GetResource(name = queryTaxNum, path = "/queryTaxNumInfo" )
    @BusinessLog(title =queryTaxNum,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryTaxNumInfo(@RequestParam String shopName){
        return ResponseData.success(tbComTaxNumService.queryById(shopName));
    }


}