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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopTaxnCatManage;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopTaxnCatManageParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopTaxnCatManageResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopTaxnCatManageService;
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
 * 资源-税号类别管理;(Tb_Com_Shop_Taxn_Cat_Manage)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-税号类别管理接口")
@RestController
@ApiResource(name="资源-税号类别管理" , path="/tbComShopTaxnCatManage")
public class TbComShopTaxnCatManageController{
     public final String baseName = "资源-税号类别管理";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String taxnChangeRecordFunName = baseName+"--根据店铺名查询税号变更记录List";
    @Resource
    private TbComShopTaxnCatManageService tbComShopTaxnCatManageService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopTaxnCatManage.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysId){
        return ResponseData.success(tbComShopTaxnCatManageService.queryById(sysId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComShopTaxnCatManageParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopTaxnCatManageResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopTaxnCatManageParam tbComShopTaxnCatManageParam){
        //1.分页参数
        Page page = tbComShopTaxnCatManageParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopTaxnCatManageResult> pageResult = tbComShopTaxnCatManageService.paginQuery(tbComShopTaxnCatManageParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopTaxnCatManage.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopTaxnCatManage tbComShopTaxnCatManage){
        return ResponseData.success(tbComShopTaxnCatManageService.insert(tbComShopTaxnCatManage));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopTaxnCatManage.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopTaxnCatManage tbComShopTaxnCatManage){
        return ResponseData.success(tbComShopTaxnCatManageService.update(tbComShopTaxnCatManage));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal sysId){
        return ResponseData.success(tbComShopTaxnCatManageService.deleteById(sysId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  sysIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysIdList){
      if (Objects.isNull(sysIdList) || sysIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopTaxnCatManageService.deleteBatchIds(sysIdList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopTaxnCatManageParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopTaxnCatManage.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopTaxnCatManageParam tbComShopTaxnCatManageParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopTaxnCatManageResult> pageResult = tbComShopTaxnCatManageService.paginQuery(tbComShopTaxnCatManageParam, current,size);
       List<TbComShopTaxnCatManageResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-税号类别管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopTaxnCatManageResult.class).sheet("资源-税号类别管理").doWrite(records);
        return ResponseData.success();
    }



     /**
      * 根据店铺名称查询信用卡变更记录
      *
      * @param shopName
      * @return 实例对象
      */
     @ApiOperation(value =taxnChangeRecordFunName,response = TbComShopShroffAccount.class)
     @PostResource(name = taxnChangeRecordFunName, path = "/taxnChangeRecord")
     @BusinessLog(title = taxnChangeRecordFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData taxnChangeRecord(String shopName){
         List<TbComShopTaxnCatManage> resultList= tbComShopTaxnCatManageService.taxnChangeRecord(shopName);
         return ResponseData.success(resultList);
     }
    
}