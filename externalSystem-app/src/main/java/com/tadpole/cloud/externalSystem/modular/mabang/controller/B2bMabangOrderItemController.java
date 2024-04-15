package com.tadpole.cloud.externalSystem.modular.mabang.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrderItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrderItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bMabangOrderItemService;
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
 * B2B马帮订单具体商品项item;(B2B_MABANG_ORDER_ITEM)--控制层
 * @author : LSY
 * @date : 2023-9-13
 */
@Slf4j
@Api(tags = "B2B马帮订单具体商品项item接口")
@RestController
@ApiResource(name="B2B马帮订单具体商品项item" , path="/b2bMabangOrderItem")
public class B2bMabangOrderItemController{
     public final String baseName = "B2B马帮订单具体商品项item";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private B2bMabangOrderItemService b2bMabangOrderItemService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response = B2bMabangOrderItem.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String id){
        return ResponseData.success(b2bMabangOrderItemService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param b2bMabangOrderItemParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response = B2bMabangOrderItemResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody B2bMabangOrderItemParam b2bMabangOrderItemParam){
        //1.分页参数
        Page page = b2bMabangOrderItemParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bMabangOrderItemResult> pageResult = b2bMabangOrderItemService.paginQuery(b2bMabangOrderItemParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param b2bMabangOrderItem 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =B2bMabangOrderItem.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody B2bMabangOrderItem b2bMabangOrderItem){
        return ResponseData.success(b2bMabangOrderItemService.insert(b2bMabangOrderItem));
    }
    
    /** 
     * 更新数据
     *
     * @param b2bMabangOrderItem 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =B2bMabangOrderItem.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody B2bMabangOrderItem b2bMabangOrderItem){
        return ResponseData.success(b2bMabangOrderItemService.update(b2bMabangOrderItem));
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
    public ResponseData deleteById(@RequestParam  String id){
        return ResponseData.success(b2bMabangOrderItemService.deleteById(id));
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
      if (Objects.isNull(idList) || idList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(b2bMabangOrderItemService.deleteBatchIds(idList));
     }
    
    /**
     * 导出
     *
     * @param b2bMabangOrderItemParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =B2bMabangOrderItem.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody B2bMabangOrderItemParam b2bMabangOrderItemParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bMabangOrderItemResult> pageResult = b2bMabangOrderItemService.paginQuery(b2bMabangOrderItemParam, current,size);
       List<B2bMabangOrderItemResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("B2B马帮订单具体商品项item.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), B2bMabangOrderItemResult.class).sheet("B2B马帮订单具体商品项item").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}