package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPrice;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceExportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsNewPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 物流商的价格信息;(tb_logistics_new_price)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流商的价格信息")
@RestController
@ApiResource(name = "物流商的价格信息", path="/tbLogisticsNewPrice")
public class TbLogisticsNewPriceController{
     public final String baseName = "物流商的价格信息";
     public final String queryByIdFunName = baseName+"--通过ID查询物流商的价格信息";
     public final String paginQueryFunName = baseName+"--分页查询物流商的价格信息";
     public final String addFunName = baseName+"--新增物流商的价格信息";
     public final String batchAddFunName = baseName+"--批量添加-物流商的价格信息";
     public final String editFunName = baseName+"--更新物流商的价格信息";
     public final String exportFunName = baseName+"--按查询条件导出物流商的价格信息";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流商的价格信息据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流商的价格信息";
     public final String busLspNumList = baseName+"--国家地区下拉框List";
    @Resource
    private TbLogisticsNewPriceService tbLogisticsNewPriceService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogpId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsNewPrice.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypkLogpId" )
    public ResponseData  queryById(BigDecimal pkLogpId){
        return ResponseData.success(tbLogisticsNewPriceService.queryById(pkLogpId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsNewPriceParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsNewPriceResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsNewPriceParam tbLogisticsNewPriceParm ){
        //1.分页参数
       Page page = tbLogisticsNewPriceParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsNewPriceResult> pageResult = tbLogisticsNewPriceService.paginQuery(tbLogisticsNewPriceParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsNewPrice 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsNewPrice.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsNewPrice tbLogisticsNewPrice){
        return ResponseData.success(tbLogisticsNewPriceService.insert(tbLogisticsNewPrice));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsNewPrice 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsNewPrice.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsNewPriceParam tbLogisticsNewPrice){
        TbLogisticsNewPrice result = tbLogisticsNewPriceService.update(tbLogisticsNewPrice);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkLogpId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal pkLogpId){
         if (tbLogisticsNewPriceService.deleteById(pkLogpId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  pkLogpIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> pkLogpIdList){
      if (Objects.isNull(pkLogpIdList) || pkLogpIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsNewPriceService.deleteBatchIds(pkLogpIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsNewPriceParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsNewPriceResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsNewPriceParam tbLogisticsNewPriceParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        List<TbLogisticsNewPriceExportResult> result = tbLogisticsNewPriceService.export(tbLogisticsNewPriceParam);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流商的价格信息.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsNewPriceExportResult.class).sheet("物流商的价格信息").doWrite(result);
        return ResponseData.success();
    }

     /**
      * 物流价格导入模板下载
      * @param response
      */
     @GetResource(name = "物流价格导入模板下载", path = "/downloadTemplate", requiredPermission = false )
     @ApiOperation("物流价格导入模板下载")
     public void downloadTemplate(HttpServletResponse response) {
         new ExcelUtils().downloadExcel(response, "/template/物流价格导入模板.xlsx");
     }

     /**
      * 导入
      * @param file
      * @return
      */
     @ParamValidator
     @PostResource(name = "物流价格导入", path = "/import", requiredPermission = false)
     @ApiOperation(value = "物流价格导入")
     @BusinessLog(title = "物流价格导入",opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
         return tbLogisticsNewPriceService.importExcel(file);
     }

     /**
      * 批量添加-物流商的价格信息
      *
      * @param newPriceList 实例对象List
      * @return 实例对象
      */
     @ApiOperation(value =batchAddFunName,response =TbLogisticsNewPrice.class)
     @PostResource(name = batchAddFunName, path = "/batchAdd",  requiredLogin =true)
     @BusinessLog(title = batchAddFunName,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData  batchAdd(@RequestBody List<TbLogisticsNewPriceExportResult> newPriceList){
         return tbLogisticsNewPriceService.batchAdd(newPriceList);
     }

     /**
      * 国家地区下拉
      *
      * @param busLpCountryCode 站点 类似US CA
      * @return 实例对象
      */
     @ApiOperation(value =busLspNumList,response =TbLogisticsNewPrice.class)
     @GetResource(name = busLspNumList, path = "/busLspNumList",  requiredLogin =true)
     @BusinessLog(title = busLspNumList,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData  busLspNumList(@RequestParam(required = false) String busLpCountryCode ){
         return tbLogisticsNewPriceService.busLspNumList(busLpCountryCode);
     }


}