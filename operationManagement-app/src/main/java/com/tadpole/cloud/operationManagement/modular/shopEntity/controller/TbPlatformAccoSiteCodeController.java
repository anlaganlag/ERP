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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbPlatformAccoSiteCode;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.QueryApplySiteParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbPlatformAccoSiteCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbPlatformAccoSiteCodeResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbPlatformAccoSiteCodeService;
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
 * 资源-平台-账号-站点-对应编码配置;(Tb_Platform_Acco_Site_Code)--控制层
 * @author : LSY
 * @date : 2023-8-3
 */
@Slf4j
@Api(tags = "资源-平台-账号-站点-对应编码配置接口")
@RestController
@ApiResource(name="资源-平台-账号-站点-对应编码配置" , path="/tbPlatformAccoSiteCode")
public class TbPlatformAccoSiteCodeController{
     public final String baseName = "资源-平台-账号-站点-对应编码配置";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String queryPlatform = baseName+"--平台查询";
     public final String queryApplyShopName = baseName+"--申请店铺账号查询";
     public final String queryApplySite = baseName+"--申请店铺站点查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";

     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbPlatformAccoSiteCodeService tbPlatformAccoSiteCodeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbPlatformAccoSiteCode.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String undefinedId){
        return ResponseData.success(tbPlatformAccoSiteCodeService.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbPlatformAccoSiteCodeParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbPlatformAccoSiteCodeResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbPlatformAccoSiteCodeParam tbPlatformAccoSiteCodeParam){
        //1.分页参数
        Page page = tbPlatformAccoSiteCodeParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbPlatformAccoSiteCodeResult> pageResult = tbPlatformAccoSiteCodeService.paginQuery(tbPlatformAccoSiteCodeParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }


     @ApiOperation(value =queryPlatform ,response =TbPlatformAccoSiteCodeResult.class)
     @PostResource(name = queryPlatform, path = "/queryPlatform")
     @BusinessLog(title = queryPlatform,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryPlatform(){
         List<String> platList = tbPlatformAccoSiteCodeService.queryPlatform();
         //3. 分页结果组装
         return ResponseData.success(platList);
     }

     @ApiOperation(value =queryApplyShopName ,response =TbPlatformAccoSiteCodeResult.class)
     @PostResource(name = queryApplyShopName, path = "/queryApplyShopName")
     @BusinessLog(title = queryApplyShopName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryApplyShopName(@RequestBody(required = false) QueryApplySiteParam queryApplySiteParam){
         List<String> applyShopName = tbPlatformAccoSiteCodeService.queryApplyShopName(queryApplySiteParam);
         return ResponseData.success(applyShopName);
     }

     @ApiOperation(value =queryApplySite ,response =TbPlatformAccoSiteCodeResult.class)
     @PostResource(name = queryApplySite, path = "/queryApplySite")
     @BusinessLog(title = queryApplySite,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryApplySite(@RequestBody(required = false) QueryApplySiteParam queryApplySiteParam){
         List<String> applySiteList = tbPlatformAccoSiteCodeService.queryApplySite(queryApplySiteParam);
         return ResponseData.success(applySiteList);
     }




     /**
     * 新增数据
     *
     * @param tbPlatformAccoSiteCode 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbPlatformAccoSiteCode.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbPlatformAccoSiteCode tbPlatformAccoSiteCode){
        return ResponseData.success(tbPlatformAccoSiteCodeService.insert(tbPlatformAccoSiteCode));
    }
    
    /** 
     * 更新数据
     *
     * @param tbPlatformAccoSiteCode 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbPlatformAccoSiteCode.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbPlatformAccoSiteCode tbPlatformAccoSiteCode){
        return tbPlatformAccoSiteCodeService.update(tbPlatformAccoSiteCode);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  String undefinedId){
        return ResponseData.success(tbPlatformAccoSiteCodeService.deleteById(undefinedId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  undefinedIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> undefinedIdList){
      if (Objects.isNull(undefinedIdList) || undefinedIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbPlatformAccoSiteCodeService.deleteBatchIds(undefinedIdList));
     }
    
    /**
     * 导出
     *
     * @param tbPlatformAccoSiteCodeParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbPlatformAccoSiteCode.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbPlatformAccoSiteCodeParam tbPlatformAccoSiteCodeParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbPlatformAccoSiteCodeResult> pageResult = tbPlatformAccoSiteCodeService.paginQuery(tbPlatformAccoSiteCodeParam, current,size);
       List<TbPlatformAccoSiteCodeResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-平台-账号-站点-对应编码配置.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbPlatformAccoSiteCodeResult.class).sheet("资源-平台-账号-站点-对应编码配置").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}