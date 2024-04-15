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
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bMabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bMabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bMabangOrdersService;
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
 * B2B马帮订单列表;(B2B_MABANG_ORDERS)--控制层
 * @author : LSY
 * @date : 2023-9-13
 */
@Slf4j
@Api(tags = "B2B马帮订单列表接口")
@RestController
@ApiResource(name="B2B马帮订单列表" , path="/b2bMabangOrders")
public class B2bMabangOrdersController{
     public final String baseName = "B2B马帮订单列表";
     public final String queryByPlatformOrderIdFunName = baseName+"--通过PlatformOrderId查询数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String updateCancelOrderFunName = baseName+"--更新作废订单数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String preparingStockOrderList = baseName+"--同步配货中的订单列表";
     public final String preparingStockOrderListNoParam = baseName+"--同步配货中的订单列表-不传参数";
    @Resource
    private B2bMabangOrdersService b2bMabangOrdersService;
    
    /** 
     * 通过PlatformOrderId查询单条数据
     *
     * @param platformOrderId
     * @return 实例对象
     */
    @ApiOperation(value =queryByPlatformOrderIdFunName,response = B2bMabangOrdersResult.class)
    @GetResource(name = queryByPlatformOrderIdFunName, path = "/queryByPlatformOrderId")
    @BusinessLog(title =queryByPlatformOrderIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByPlatformOrderId(String platformOrderId){
        return ResponseData.success(b2bMabangOrdersService.queryByPlatformOrderId(platformOrderId));
    }
    
    /** 
     * 分页查询
     *
     * @param b2bMabangOrdersParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response = B2bMabangOrdersResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody B2bMabangOrdersParam b2bMabangOrdersParam){
        //1.分页参数
        Page page = b2bMabangOrdersParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bMabangOrdersResult> pageResult = b2bMabangOrdersService.paginQuery(b2bMabangOrdersParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param b2bMabangOrders 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =B2bMabangOrders.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody B2bMabangOrders b2bMabangOrders){
        return ResponseData.success(b2bMabangOrdersService.insert(b2bMabangOrders));
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
        return ResponseData.success(b2bMabangOrdersService.deleteById(id));
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
       return ResponseData.success(b2bMabangOrdersService.deleteBatchIds(idList));
     }
    
    /**
     * 导出
     *
     * @param b2bMabangOrdersParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =B2bMabangOrders.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody B2bMabangOrdersParam b2bMabangOrdersParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bMabangOrdersResult> pageResult = b2bMabangOrdersService.paginQuery(b2bMabangOrdersParam, current,size);
       List<B2bMabangOrdersResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("B2B马帮订单列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), B2bMabangOrdersResult.class).sheet("B2B马帮订单列表").doWrite(records);
        return ResponseData.success();
    }



     @PostResource(name = preparingStockOrderList, path = "/preparingStockOrderList",   requiredLogin = false)
     @ApiOperation(value = preparingStockOrderList, response =ResponseData.class)
     @BusinessLog(title = preparingStockOrderList,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData preparingStockOrderList(@RequestBody(required = false) OrderParm orderParm ){
         return  b2bMabangOrdersService.preparingStockOrderList(orderParm);
     }

     @GetResource(name = preparingStockOrderListNoParam, path = "/preparingStockOrderListNoParam",   requiredLogin = false)
     @ApiOperation(value = preparingStockOrderListNoParam, response =ResponseData.class)
     @BusinessLog(title = preparingStockOrderListNoParam,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData preparingStockOrderListNoParam(){
         OrderParm orderParm = new OrderParm();
         return  b2bMabangOrdersService.preparingStockOrderList(orderParm);
     }



     @ApiOperation(value =updateCancelOrderFunName)
     @PostResource(name = updateCancelOrderFunName, path = "/updateCancelOrder", requiredLogin = false)
     @BusinessLog(title = updateCancelOrderFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData updateCancelOrder(@RequestBody  List<String> updateOrderList){
        return b2bMabangOrdersService.updateCancelOrder(updateOrderList);
     }
    
    
}