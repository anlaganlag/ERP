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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopDataDown;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopDataDownParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopDataDownResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopDataDownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 资源-店铺数据下载管理;(Tb_Com_Shop_Data_Down)--控制层
 * @author : LSY
 * @date : 2023-8-11
 */
@Slf4j
@Api(tags = "资源-店铺数据下载管理接口")
@RestController
@ApiResource(name="资源-店铺数据下载管理" , path="/tbComShopDataDown")
public class TbComShopDataDownController{
     public final String baseName = "资源-店铺数据下载管理";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String checkRestartTaskFunName = baseName+"--创建店铺下载任务检查创建";
     public final String shopHistoryDataTaskFunName = baseName+"--EBMS历史数据店铺下载任务创建";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopDataDownService tbComShopDataDownService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopDataDown.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal id){
        return ResponseData.success(tbComShopDataDownService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComShopDataDownParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopDataDownResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopDataDownParam tbComShopDataDownParam){
        //1.分页参数
        Page page = tbComShopDataDownParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopDataDownResult> pageResult = tbComShopDataDownService.paginQuery(tbComShopDataDownParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopDataDown 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopDataDown.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopDataDown tbComShopDataDown){
        return ResponseData.success(tbComShopDataDownService.insert(tbComShopDataDown));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopDataDown 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopDataDown.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopDataDown tbComShopDataDown){
        return ResponseData.success(tbComShopDataDownService.update(tbComShopDataDown));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal id){
        return ResponseData.success(tbComShopDataDownService.deleteById(id));
    }
    
    /**
     * 批量删除数据
     *
     * @param  idList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> idList){
      if (Objects.isNull(idList) || idList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopDataDownService.deleteBatchIds(idList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopDataDownParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopDataDown.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopDataDownParam tbComShopDataDownParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopDataDownResult> pageResult = tbComShopDataDownService.paginQuery(tbComShopDataDownParam, current,size);
       List<TbComShopDataDownResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺数据下载管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopDataDownResult.class).sheet("资源-店铺数据下载管理").doWrite(records);
        return ResponseData.success();
    }

     /**
      * EBMS历史数据店铺下载任务创建
      *
      * @param  shopNameSimple
      * @param  isAllShop  1:所有店铺 ，0：部分店铺（MC同步记录中没有任何记录的店铺找出来创建店铺下载任务）
      * @return 是否成功
      */
     @ApiOperation(value =shopHistoryDataTaskFunName)
     @GetResource(name =shopHistoryDataTaskFunName, path = "/shopHistoryDataTask")
     @BusinessLog(title =shopHistoryDataTaskFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData shopHistoryDataTask(@RequestParam(required = false) String shopNameSimple,@RequestParam Integer isAllShop){
         return tbComShopDataDownService.shopHistoryDataTask(shopNameSimple,isAllShop);
     }

     /**
      * 创建店铺下载任务检查创建
      *
      * @param  shopNameSimple
      * @return 是否成功
      */
     @ApiOperation(value =checkRestartTaskFunName)
     @GetResource(name =checkRestartTaskFunName, path = "/checkRestartTask")
     @BusinessLog(title =checkRestartTaskFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData checkRestartTask(@RequestParam(required = false) String shopNameSimple){
         return tbComShopDataDownService.checkRestartTask(shopNameSimple);
     }



}