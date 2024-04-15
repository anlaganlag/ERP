package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopPlatCodeParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopPlatCodeResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopPlatCode;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopPlatCodeService;

 /**
 * 资源-店铺平台编码;(Tb_Com_Shop_Plat_Code)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-店铺平台编码接口")
@RestController
@ApiResource(name="资源-店铺平台编码" , path="/tbComShopPlatCode")
public class TbComShopPlatCodeController{
     public final String baseName = "资源-店铺平台编码";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopPlatCodeService tbComShopPlatCodeService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param elePlatformName 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopPlatCode.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String elePlatformName){
        return ResponseData.success(tbComShopPlatCodeService.queryById(elePlatformName));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComShopPlatCodeParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopPlatCodeResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopPlatCodeParam tbComShopPlatCodeParam){
        //1.分页参数
        Page page = tbComShopPlatCodeParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopPlatCodeResult> pageResult = tbComShopPlatCodeService.paginQuery(tbComShopPlatCodeParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopPlatCode.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopPlatCode tbComShopPlatCode){
        return ResponseData.success(tbComShopPlatCodeService.insert(tbComShopPlatCode));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopPlatCode 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopPlatCode.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopPlatCode tbComShopPlatCode){
        return ResponseData.success(tbComShopPlatCodeService.update(tbComShopPlatCode));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param elePlatformName 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  String elePlatformName){
        return ResponseData.success(tbComShopPlatCodeService.deleteById(elePlatformName));
    }
    
    /**
     * 批量删除数据
     *
     * @param  elePlatformNameList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> elePlatformNameList){
      if (Objects.isNull(elePlatformNameList) || elePlatformNameList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopPlatCodeService.deleteBatchIds(elePlatformNameList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopPlatCodeParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopPlatCode.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopPlatCodeParam tbComShopPlatCodeParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopPlatCodeResult> pageResult = tbComShopPlatCodeService.paginQuery(tbComShopPlatCodeParam, current,size);
       List<TbComShopPlatCodeResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺平台编码.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopPlatCodeResult.class).sheet("资源-店铺平台编码").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}