package com.tadpole.cloud.externalSystem.modular.mabang.controller;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentDetailParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bPaymentDetailResult;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * B2B客户付款明细;(B2B_PAYMENT_DETAIL)--控制层
 * @author : LSY
 * @date : 2023-9-14
 */
@Slf4j
@Api(tags = "B2B客户付款明细接口")
@RestController
@ApiResource(name="B2B客户付款明细" , path="/b2bPaymentDetail")
public class B2bPaymentDetailController{
     public final String baseName = "B2B客户付款明细";
     public final String queryByPlatformOrderIdFunName = baseName+"--通过PlatformOrderId查单付款明细";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String operSubmit = baseName+"--运营提交付款明细";
     public final String confirm = baseName+"--财务确认";
     public final String syncPaymentDetail = baseName+"--同步付款明细到k3";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String queryK3BankInfoByOrgCode = baseName+"--根据财务组织编码查找收款银行信息";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private B2bPaymentDetailService b2bPaymentDetailService;

     @Resource
     private B2bPaymentService b2bPaymentService;


    
    /** 
     * 通过PlatformOrderId查单付款明细
     *
     * @param platformOrderId 平台订单编号
     * @return 实例对象List
     */
    @ApiOperation(value =queryByPlatformOrderIdFunName,response = B2bPaymentDetailResult.class)
    @GetResource(name = queryByPlatformOrderIdFunName, path = "/queryByPlatformOrderId", requiredLogin = false)
    @BusinessLog(title =queryByPlatformOrderIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByPlatformOrderId(String platformOrderId){
        return ResponseData.success(b2bPaymentDetailService.queryByPlatformOrderId(platformOrderId));
    }
    
    /** 
     * 分页查询
     *
     * @param b2bPaymentDetailParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response = B2bPaymentDetailResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody B2bPaymentDetailParam b2bPaymentDetailParam){
        //1.分页参数
        Page page = b2bPaymentDetailParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bPaymentDetailResult> pageResult = b2bPaymentDetailService.paginQuery(b2bPaymentDetailParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param b2bPaymentDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =B2bPaymentDetail.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody B2bPaymentDetail b2bPaymentDetail){
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();
        b2bPaymentDetail.setCreatedBy(loginUserInfo.getName());
        return ResponseData.success(b2bPaymentDetailService.insert(b2bPaymentDetail));
    }
    
    /** 
     * 更新数据
     *
     * @param b2bPaymentDetail 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =B2bPaymentDetail.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody B2bPaymentDetail b2bPaymentDetail){
        return ResponseData.success(b2bPaymentDetailService.update(b2bPaymentDetail));
    }

     /**
      * 运营提交付款明细
      *
      * @param b2bPaymentDetail 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =operSubmit,response =B2bPaymentDetail.class)
     @PostResource(name = operSubmit, path = "/operSubmit")
     @BusinessLog(title = operSubmit,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData operSubmit(@RequestBody B2bPaymentDetail b2bPaymentDetail){

         return b2bPaymentDetailService.operSubmit(b2bPaymentDetail);
     }

     /**
      * 财务确认付款
      *
      * @param parm 财务确认付款
      * @return 实例对象
      */
     @ApiOperation(value =confirm,response =B2bPaymentDetail.class)
     @PostResource(name = confirm, path = "/confirm")
     @BusinessLog(title = confirm,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData confirm(@RequestBody B2bPaymentDetail parm){
         return b2bPaymentDetailService.financeConfirm(parm);
     }

    /**
     * 同步失败了，再次同步
     *
     * @param id 付款明细id
     * @return 实例对象
     */
    @ApiOperation(value =syncPaymentDetail,response =B2bPaymentDetail.class)
    @GetResource(name = syncPaymentDetail, path = "/syncPaymentDetail")
    @BusinessLog(title = syncPaymentDetail,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData syncPaymentDetail(@RequestParam  BigDecimal id){
        return b2bPaymentDetailService.syncPaymentDetail(id);
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
    public ResponseData deleteById(@RequestParam  BigDecimal id){
        return ResponseData.success(b2bPaymentDetailService.deleteById(id));
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
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> idList){
      if (Objects.isNull(idList) || idList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(b2bPaymentDetailService.deleteBatchIds(idList));
     }
    
    /**
     * 导出
     *
     * @param b2bPaymentDetailParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =B2bPaymentDetail.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody B2bPaymentDetailParam b2bPaymentDetailParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<B2bPaymentDetailResult> pageResult = b2bPaymentDetailService.paginQuery(b2bPaymentDetailParam, current,size);
       List<B2bPaymentDetailResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("B2B客户付款明细.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), B2bPaymentDetailResult.class).sheet("B2B客户付款明细").doWrite(records);
        return ResponseData.success();
    }



    /**
     * 根据财务组织编码查找收款银行信息
     *
     * @param  orgCode  财务组织编码
     * @return
     */
    @ApiOperation(value =queryK3BankInfoByOrgCode)
    @GetResource(name = queryK3BankInfoByOrgCode, path = "/queryK3BankInfoByOrgCode", requiredLogin = false)
    @BusinessLog(title = queryK3BankInfoByOrgCode,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData queryK3BankInfoByOrgCode(@RequestParam  String orgCode,String shopName){
        return ResponseData.success(b2bPaymentDetailService.queryK3BankInfoByOrgCode(orgCode,shopName));
    }

    
}