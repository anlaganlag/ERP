package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

 /**
 * 物流供应商;(tb_logistics_provider)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流供应商")
@RestController
@ApiResource(name = "物流供应商", path="/tbLogisticsProvider")
public class TbLogisticsProviderController{
     public final String baseName = "物流供应商";
     public final String queryByIdFunName = baseName+"--通过ID查询物流供应商";
     public final String paginQueryFunName = baseName+"--分页查询物流供应商";
     public final String addFunName = baseName+"--新增物流供应商";
     public final String editFunName = baseName+"--更新物流供应商";
     public final String exportFunName = baseName+"--按查询条件导出物流供应商";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流供应商据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流供应商";
     public final String synLogisticsBusinessFromERP = baseName+"--同步金蝶k3物流商信息";
     public final String lpCodeList = baseName+"--物流商编码List";
     public final String lpNameList = baseName+"--物流商名称List";
     public final String lpSimpleNameList = baseName+"--物流商简称List";
    @Resource
    private TbLogisticsProviderService tbLogisticsProviderService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpCode 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsProviderResult.class)
    @GetResource(name = queryByIdFunName, path = "/queryBylpCode" )
    public ResponseData  queryById(String lpCode){
        return ResponseData.success(tbLogisticsProviderService.queryById(lpCode));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsProviderParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response= TbLogisticsProviderResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsProviderParam tbLogisticsProviderParm ){
        //1.分页参数
       Page page = tbLogisticsProviderParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsProviderResult> pageResult = tbLogisticsProviderService.paginQuery(tbLogisticsProviderParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsProvider 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsProviderResult.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsProvider tbLogisticsProvider){
        return tbLogisticsProviderService.insert(tbLogisticsProvider);
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsProvider 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsProviderResult.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsProviderParam tbLogisticsProvider){
        TbLogisticsProvider result = tbLogisticsProviderService.update(tbLogisticsProvider);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpCode 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String lpCode){
         if (tbLogisticsProviderService.deleteById(lpCode)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  lpCodeList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> lpCodeList){
      if (Objects.isNull(lpCodeList) || lpCodeList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsProviderService.deleteBatchIds(lpCodeList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsProviderParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsProviderResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsProviderParam tbLogisticsProviderParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsProviderResult> pageResult = tbLogisticsProviderService.paginQuery(tbLogisticsProviderParm, current,size);
       List<TbLogisticsProviderResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流供应商.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsProviderResult.class).sheet("物流供应商").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 同步金蝶k3系统里面的物流商信息
      *
      * @return 实例对象
      */
     @ApiOperation(value =synLogisticsBusinessFromERP,response=TbLogisticsProviderResult.class)
     @GetResource(name = synLogisticsBusinessFromERP, path = "/synLogisticsBusinessFromERP" )
     public ResponseData  synLogisticsBusinessFromERP(){
         return tbLogisticsProviderService.synLogisticsBusinessFromERP();
     }

     /**
      * 物流商公共查询下拉--物流商编码
      *
      * @return 实例对象
      */
     @ApiOperation(value =lpCodeList,response=TbLogisticsProviderResult.class)
     @GetResource(name = lpCodeList, path = "/lpCodeList" )
     public ResponseData  lpCodeList(){
         return tbLogisticsProviderService.lpCodeList();
     }

     /**
      * 物流商公共查询下拉--物流商名称
      *
      * @return 实例对象
      */
     @ApiOperation(value =lpNameList,response=TbLogisticsProviderResult.class)
     @GetResource(name = lpNameList, path = "/lpNameList" )
     public ResponseData  lpCodeNameList(){
         return tbLogisticsProviderService.lpNameList();
     }

     /**
      * 物流商公共查询下拉--物流商简称
      *
      * @return 实例对象
      */
     @ApiOperation(value =lpSimpleNameList,response=TbLogisticsProviderResult.class)
     @GetResource(name = lpSimpleNameList, path = "/lpSimpleNameList" )
     public ResponseData  lpSimpleNameList(){
         return tbLogisticsProviderService.lpSimpleNameList();
     }
}