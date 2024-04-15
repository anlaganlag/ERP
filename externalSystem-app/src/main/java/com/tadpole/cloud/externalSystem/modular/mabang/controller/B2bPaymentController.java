package com.tadpole.cloud.externalSystem.modular.mabang.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPayment;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.B2bMabangOrdersExportResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentDetailService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentService;
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
 * B2B客户付款信息;(B2B_PAYMENT)--控制层
 * @author : LSY
 * @date : 2023-9-14
 */
@Slf4j
@Api(tags = "B2B客户付款信息接口")
@RestController
@ApiResource(name="B2B客户付款信息" , path="/b2bPayment")
public class B2bPaymentController{
     public final String baseName = "B2B客户付款信息";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";

     public final String operClose = baseName+"--运营关闭订单";
     public final String financeClose = baseName+"--财务关闭订单";

    @Resource
    private B2bPaymentService b2bPaymentService;

     @Resource
     private B2bPaymentDetailService b2bPaymentDetailService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response = B2bPayment.class)
    @GetResource(name = queryByIdFunName, path = "/queryById", requiredLogin = false)
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String id){
        return ResponseData.success(b2bPaymentService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param b2bPaymentParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response = B2bMabangOrdersResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody B2bPaymentParam b2bPaymentParam){

        if (ObjectUtil.isNotNull(b2bPaymentParam.getCreateDateEnd())) {
            b2bPaymentParam.setCreateDateEnd(b2bPaymentParam.getCreateDateEnd().plusDays(1L));
        }

        //1.分页参数
        Page page = b2bPaymentParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bMabangOrdersResult> pageResult = b2bPaymentService.paginQuery(b2bPaymentParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param b2bPayment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =B2bPayment.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody B2bPayment b2bPayment){
        return ResponseData.success(b2bPaymentService.insert(b2bPayment));
    }
    
    /** 
     * 更新数据
     *
     * @param b2bPayment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =B2bPayment.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody B2bPayment b2bPayment){
        return ResponseData.success(b2bPaymentService.update(b2bPayment));
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
        return ResponseData.success(b2bPaymentService.deleteById(id));
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
       return ResponseData.success(b2bPaymentService.deleteBatchIds(idList));
     }
    
    /**
     * 导出
     *
     * @param b2bPaymentParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =B2bMabangOrdersResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody B2bPaymentParam b2bPaymentParam, HttpServletResponse response) throws IOException {

        if (ObjectUtil.isNotNull(b2bPaymentParam.getCreateDateEnd())) {
            b2bPaymentParam.setCreateDateEnd(b2bPaymentParam.getCreateDateEnd().plusDays(1L));
        }

        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
       List<B2bMabangOrdersExportResult> records=   b2bPaymentService.export(b2bPaymentParam);
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("B2B客户付款信息.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), B2bMabangOrdersExportResult.class).sheet("B2B客户付款信息").doWrite(records);
        return ResponseData.success();
    }




     /**
      * 运营关闭订单
      *
      * @param platformOrderId 平台订单号
      * @return 运营关闭订单
      */
     @ApiOperation(value =operClose,response = ResponseData.class)
     @GetResource(name = operClose, path = "/operClose")
     @BusinessLog(title = operClose,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData operClose(String platformOrderId  ){
         return b2bPaymentService.orderClose(platformOrderId, MabangConstant.B2B_ORDER_CLOSE_BY_OPER);
     }


     /**
      * 财务关闭订单
      *
      * @param platformOrderId 财务关闭订单
      * @return 财务关闭订单
      */
     @ApiOperation(value =financeClose,response = ResponseData.class)
     @GetResource(name = financeClose, path = "/financeClose")
     @BusinessLog(title = financeClose,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData financeClose(String platformOrderId ){
         return b2bPaymentService.orderClose(platformOrderId,MabangConstant.B2B_ORDER_CLOSE_BY_FINANCE);
     }

 }