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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityBankEquipment;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentEditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityBankEquipmentParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.BankEquipmentCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityBankEquipmentResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityBankEquipmentService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityService;
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
 * 资源-公司实体银行设备;(Tb_Com_Entity_Bank_Equipment)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-公司实体银行设备接口")
@RestController
@ApiResource(name="资源-公司实体银行设备" , path="/tbComEntityBankEquipment")
public class TbComEntityBankEquipmentController{
     public final String baseName = "资源-公司实体银行设备";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String paginQueryDetailName = baseName+"--明细表查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String bankEquipmentCount = baseName+"--银行设备统计";
     public final String bankEquipmentCountExport = baseName+"--银行设备统计导出";
    @Resource
    private TbComEntityBankEquipmentService tbComEntityBankEquipmentService;

     @Resource
     private TbComEntityService tbComEntityService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkBeCode 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComEntityBankEquipment.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal pkBeCode){
        return ResponseData.success(tbComEntityBankEquipmentService.queryById(pkBeCode));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComEntityParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComEntityBankEquipmentResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComEntityParam tbComEntityParam){
        //1.分页参数
        Page page = tbComEntityParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityBankEquipmentResult> tbComEntityResultPage = tbComEntityBankEquipmentService.paginQuery(tbComEntityParam, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(tbComEntityResultPage));
    }


     @ApiOperation(value =paginQueryDetailName ,response =TbComEntityBankEquipmentResult.class)
     @PostResource(name = paginQueryDetailName, path = "/queryDetail")
     @BusinessLog(title = paginQueryDetailName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryDetail(@RequestBody TbComEntityBankEquipmentParam tbComEntityBankEquipmentParam){
         return tbComEntityBankEquipmentService.queryList(tbComEntityBankEquipmentParam);
     }


     /**
      * 分页查询
      *
      * @param tbComEntityBankEquipmentParam 筛选条件
      * @return 查询结果
      */
//     @ApiOperation(value =paginQueryFunName ,response =TbComEntityBankEquipmentResult.class)
//     @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
//     @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
//     public ResponseData paginQuery(@RequestBody TbComEntityBankEquipmentParam tbComEntityBankEquipmentParam){
//         //1.分页参数
//         Page page = tbComEntityBankEquipmentParam.getPageContext();
//         long current = page.getCurrent();
//         long size = page.getSize();
//         //2.分页查询
//         /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
//         Page<TbComEntityBankEquipmentResult> pageResult = tbComEntityBankEquipmentService.paginQuery(tbComEntityBankEquipmentParam, current,size);
//         //3. 分页结果组装
//         return ResponseData.success(new PageResult<>(pageResult));
//     }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityBankEquipment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComEntityBankEquipment.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComEntityBankEquipment tbComEntityBankEquipment){
        return ResponseData.success(tbComEntityBankEquipmentService.insert(tbComEntityBankEquipment));
    }
    
    /** 
     * 更新数据
     *
     * @return 实例对象
     */
//    @ApiOperation(value =editFunName,response =TbComEntityBankEquipment.class)
//    @PostResource(name = editFunName, path = "/update")
//    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
//    public ResponseData edit(@RequestBody TbComEntityBankEquipment tbComEntityBankEquipment){
//        return ResponseData.success(tbComEntityBankEquipmentService.update(tbComEntityBankEquipment));
//    }


     @ApiOperation(value =editFunName,response =TbComEntityBankEquipment.class)
     @PostResource(name = editFunName, path = "/update")
     @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData edit(@RequestBody TbComEntityBankEquipmentEditParam tbComEntityBankEquipmentEditParam){
         return ResponseData.success(tbComEntityBankEquipmentService.update(tbComEntityBankEquipmentEditParam));
     }




    /** 
     * 通过主键删除数据
     *
     * @param pkBeCode 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal pkBeCode){
        return ResponseData.success(tbComEntityBankEquipmentService.deleteById(pkBeCode));
    }
    
    /**
     * 批量删除数据
     *
     * @param  pkBeCodeList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> pkBeCodeList){
      if (Objects.isNull(pkBeCodeList) || pkBeCodeList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComEntityBankEquipmentService.deleteBatchIds(pkBeCodeList));
     }
    
    /**
     * 导出
     *
     * @param tbComEntityBankEquipmentParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComEntityBankEquipment.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComEntityBankEquipmentParam tbComEntityBankEquipmentParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityBankEquipmentResult> pageResult = tbComEntityBankEquipmentService.paginQuery(tbComEntityBankEquipmentParam, current,size);
       List<TbComEntityBankEquipmentResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-公司实体银行设备.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComEntityBankEquipmentResult.class).sheet("资源-公司实体银行设备").doWrite(records);
        return ResponseData.success();
    }



     /**
      * 按查询条件统计导出
      * @param param
      * @return 按查询条件统计导出
      */
     @ApiOperation(value =bankEquipmentCount ,response = TbComEntityBankEquipmentResult.class)
     @PostResource(name = bankEquipmentCount, path = "/bankEquipmentCount", requiredLogin = true, menuFlag = true)
     @BusinessLog(title = bankEquipmentCount,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData bankEquipmentCount(@RequestBody TbComEntityBankEquipmentParam param){

         List<BankEquipmentCountResult> records = tbComEntityBankEquipmentService.bankEquipmentCount(param);
         if (Objects.isNull(records) || records.size()==0) {
             return    ResponseData.success();
         }
         return ResponseData.success(records);
     }


     /**
      * 按查询条件统计导出
      * @param param
      * @return 按查询条件统计导出
      */
     @ApiOperation(value =bankEquipmentCountExport ,response = TbComEntityBankEquipmentResult.class)
     @PostResource(name = bankEquipmentCountExport, path = "/countExport", requiredLogin = true, menuFlag = true)
     @BusinessLog(title = bankEquipmentCountExport,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData countExport(@RequestBody TbComEntityBankEquipmentParam param, HttpServletResponse response)throws IOException{

         List<BankEquipmentCountResult> records = tbComEntityBankEquipmentService.bankEquipmentCount(param);
         if (Objects.isNull(records) || records.size()==0) {
             return    ResponseData.success();
         }
         response.addHeader("Content-Disposition", "attachment;filename=" + new String("银行设备数量统计.xlsx".getBytes("utf-8"),"ISO8859-1"));
         EasyExcel.write(response.getOutputStream(), BankEquipmentCountResult.class).sheet("银行设备数量统计").doWrite(records);
         return ResponseData.success();
     }
    
    
    
}