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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCertificateSeal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCertificateSealParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.CertificateSealCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCertificateSealResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityCertificateSealService;
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
 * 资源-公司实体证件印章;(Tb_Com_Entity_Certificate_Seal)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-公司实体证件印章接口")
@RestController
@ApiResource(name="资源-公司实体证件印章" , path="/tbComEntityCertificateSeal")
public class TbComEntityCertificateSealController{
     public final String baseName = "资源-公司实体证件印章";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String certificateSealCount = baseName+"--证件印章数量统计";
     public final String certificateSealCountExport = baseName+"--证件印章数量统计并导出";
    @Resource
    private TbComEntityCertificateSealService tbComEntityCertificateSealService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComEntityCertificateSeal.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal pkCode){
        return ResponseData.success(tbComEntityCertificateSealService.queryById(pkCode));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComEntityCertificateSealParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComEntityCertificateSealResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComEntityCertificateSealParam tbComEntityCertificateSealParam){
        //1.分页参数
        Page page = tbComEntityCertificateSealParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityCertificateSealResult> pageResult = tbComEntityCertificateSealService.queryPage(tbComEntityCertificateSealParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }


//     @ApiOperation(value =paginQueryFunName ,response =TbComEntityCertificateSealResult.class)
//     @PostResource(name = paginQueryFunName, path = "/queryPage")
//     @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
//     public ResponseData queryPage(@RequestBody TbComEntityCertificateSealParam tbComEntityCertificateSealParam){
//         //1.分页参数
//         Page page = tbComEntityCertificateSealParam.getPageContext();
//         long current = page.getCurrent();
//         long size = page.getSize();
//         //2.分页查询
//         /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
//         Page<TbComEntityCertificateSealResult> pageResult = tbComEntityCertificateSealService.queryPage(tbComEntityCertificateSealParam, current,size);
//         //3. 分页结果组装
//         return ResponseData.success(new PageResult<>(pageResult));
//     }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityCertificateSeal 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComEntityCertificateSeal.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComEntityCertificateSeal tbComEntityCertificateSeal){
        return ResponseData.success(tbComEntityCertificateSealService.insert(tbComEntityCertificateSeal));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityCertificateSeal 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComEntityCertificateSeal.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComEntityCertificateSeal tbComEntityCertificateSeal){
        return ResponseData.success(tbComEntityCertificateSealService.update(tbComEntityCertificateSeal));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkCode 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal pkCode){
        return ResponseData.success(tbComEntityCertificateSealService.deleteById(pkCode));
    }
    
    /**
     * 批量删除数据
     *
     * @param  pkCodeList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> pkCodeList){
      if (Objects.isNull(pkCodeList) || pkCodeList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComEntityCertificateSealService.deleteBatchIds(pkCodeList));
     }
    
    /**
     * 导出
     *
     * @param tbComEntityCertificateSealParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComEntityCertificateSeal.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComEntityCertificateSealParam tbComEntityCertificateSealParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComEntityCertificateSealResult> pageResult = tbComEntityCertificateSealService.paginQuery(tbComEntityCertificateSealParam, current,size);
       List<TbComEntityCertificateSealResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-公司实体证件印章.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComEntityCertificateSealResult.class).sheet("资源-公司实体证件印章").doWrite(records);
        return ResponseData.success();
    }



     /**
      * 按查询条件统计
      *
      * @param param
      * @return 按查询条件统计
      */
     @ApiOperation(value =certificateSealCount ,response = CertificateSealCountResult.class)
     @PostResource(name = certificateSealCount, path = "/certificateSealCount", requiredLogin = true, menuFlag = true)
     @BusinessLog(title = certificateSealCount,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData certificateSealCount(@RequestBody TbComEntityCertificateSealParam param){
         List<CertificateSealCountResult> list = tbComEntityCertificateSealService.certificateSealCount(param);
         return ResponseData.success(list);
     }

     /**
      * 按查询条件统计导出
      * @param param
      * @return 按查询条件统计导出
      */
     @ApiOperation(value =certificateSealCountExport ,response = CertificateSealCountResult.class)
     @PostResource(name = certificateSealCountExport, path = "/countExport", requiredLogin = true, menuFlag = true)
     @BusinessLog(title = certificateSealCountExport,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData countExport(@RequestBody TbComEntityCertificateSealParam param, HttpServletResponse response)throws IOException{

         List<CertificateSealCountResult> records = tbComEntityCertificateSealService.certificateSealCount(param);
         if (Objects.isNull(records) || records.size()==0) {
             return    ResponseData.success();
         }
         response.addHeader("Content-Disposition", "attachment;filename=" + new String("证件印章数量统计.xlsx".getBytes("utf-8"),"ISO8859-1"));
         EasyExcel.write(response.getOutputStream(), CertificateSealCountResult.class).sheet("证件印章数量统计").doWrite(records);
         return ResponseData.success();
     }

}