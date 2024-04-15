package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopShroffAccountParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopShroffAccountResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopShroffAccountService;
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
 * 资源-信用卡账号管理;(Tb_Com_Shop_Shroff_Account)--控制层
 * @author : LSY
 * @date : 2023-8-3
 */
@Slf4j
@Api(tags = "资源-信用卡账号管理接口")
@RestController
@ApiResource(name="资源-信用卡账号管理" , path="/tbComShopShroffAccount")
public class TbComShopShroffAccountController{
     public final String baseName = "资源-信用卡账号管理";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String addBatchFunName = baseName+"--批量变更店铺信用卡号";
     public final String changeRecordFunName = baseName+"--根据店铺名称查询信用卡变更记录";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopShroffAccountService tbComShopShroffAccountService;
    @Resource
    private TbComShopService tbComShopService;

    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopShroffAccount.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysId){
        return ResponseData.success(tbComShopShroffAccountService.queryById(sysId));
    }
    
    /** 
     * 分页查询
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopParam.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopParam param){
        if (ObjectUtil.isNull(param)) {
             param = new TbComShopParam();
        }
        param.setShopState("正常");
        //1.分页参数
        Page page = param.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(param, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopShroffAccount 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopShroffAccount.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopShroffAccount tbComShopShroffAccount){
        return ResponseData.success(tbComShopShroffAccountService.insert(tbComShopShroffAccount));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopShroffAccount 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopShroffAccount.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopShroffAccount tbComShopShroffAccount){
        return ResponseData.success(tbComShopShroffAccountService.update(tbComShopShroffAccount));
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
        return ResponseData.success(tbComShopShroffAccountService.deleteById(sysId));
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
       return ResponseData.success(tbComShopShroffAccountService.deleteBatchIds(sysIdList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopShroffAccountParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopShroffAccount.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopShroffAccountParam tbComShopShroffAccountParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopShroffAccountResult> pageResult = tbComShopShroffAccountService.paginQuery(tbComShopShroffAccountParam, current,size);
       List<TbComShopShroffAccountResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-信用卡账号管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopShroffAccountResult.class).sheet("资源-信用卡账号管理").doWrite(records);
        return ResponseData.success();
    }


     /**
      * 批量变更店铺信用卡号
      *
      * @param shroffAccountList
      * @return 实例对象
      */
     @ApiOperation(value =addBatchFunName,response =TbComShopShroffAccount.class)
     @PostResource(name = addBatchFunName, path = "/addBatch")
     @BusinessLog(title = addBatchFunName,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData addBatch(@RequestBody List<TbComShopShroffAccount> shroffAccountList){
         return tbComShopShroffAccountService.addBatch(shroffAccountList);
     }


     /**
      * 根据店铺名称查询信用卡变更记录
      *
      * @param shopName
      * @return 实例对象
      */
     @ApiOperation(value =changeRecordFunName,response =TbComShopShroffAccount.class)
     @PostResource(name = changeRecordFunName, path = "/shroffAccountChangeRecord")
     @BusinessLog(title = changeRecordFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData shroffAccountChangeRecord(String shopName){
        List<TbComShopShroffAccount> resultList= tbComShopShroffAccountService.shroffAccountChangeRecord(shopName);
         return ResponseData.success(resultList);
     }






 }