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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
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
 * 资源-店铺;(Tb_Com_Shop)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-店铺接口")
@RestController
@ApiResource(name="资源-店铺" , path="/tbComShop")
public class TbComShopController{
     public final String baseName = "资源-店铺";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String queryShopNameList = baseName+"--店铺名称下拉List";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String queryShopInfoFunName = baseName+"--查询指定店铺信息";
     public final String paginQueryErrorFunName = baseName+"--分页异常查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String applyShutShopName = baseName+"--申请关闭店铺";
     public final String shutShopName = baseName+"--关闭店铺";
     public final String createOrgCode = baseName+"--生成组织编码";
     public final String syncShopInfo = baseName+"--同步ERP(店铺信息)";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
     public final String queryShopList = baseName+"--账号下拉List";
     public final String querySiteList = baseName+"--站点下拉List";
     public final String queryPlatformList = baseName+"--平台下拉List";
     public final String queryShopFullList = baseName+"--店铺全称下拉List";


    @Resource
    private TbComShopService tbComShopService;

    /**
     * 通过ID查询单条数据
     *
     * @param shopName 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShop.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String shopName){
        return ResponseData.success(tbComShopService.queryById(shopName));
    }

    /**
     * 分页查询
     *
     * @param tbComShopParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopParam tbComShopParam){
        //1.分页参数
        Page page = tbComShopParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }


     /**
      * 查询指定店铺信息
      *
      * @param tbComShopParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value =queryShopInfoFunName ,response =TbComShopResult.class)
     @PostResource(name = queryShopInfoFunName, path = "/queryShopInfo", menuFlag = false)
     @BusinessLog(title = queryShopInfoFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryShopInfo(@RequestBody TbComShopParam tbComShopParam){
         return ResponseData.success(tbComShopService.queryShopInfo(tbComShopParam));
     }

     /**
      * 分页异常查询
      *
      * @param tbComShopParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value =paginQueryErrorFunName ,response =TbComShopResult.class)
     @PostResource(name = paginQueryErrorFunName, path = "/errorList", requiredLogin = true, menuFlag = true)
     @BusinessLog(title = paginQueryErrorFunName,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData paginErrorQuery(@RequestBody(required = false) TbComShopParam tbComShopParam){
         //1.分页参数
         Page page = tbComShopParam.getPageContext();
         long current = page.getCurrent();
         long size = page.getSize();
         //2.分页查询
         /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
         Page<TbComShopResult> pageResult = tbComShopService.paginErrorQuery(tbComShopParam, current,size);
         //3. 分页结果组装
         return ResponseData.success(new PageResult<>(pageResult));
     }


     /**
      * 更新数据
      *
      * @param shopNameList 实例对象List
      * @return 实例对象
      */
     @ApiOperation(value =createOrgCode,response =TbComShop.class)
     @PostResource(name = createOrgCode, path = "/createOrgCode")
     @BusinessLog(title = createOrgCode,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData createOrgCode(@RequestBody (required = false) List<String> shopNameList){
         tbComShopService.createOrgCode(shopNameList);
         return ResponseData.success();
     }


     /**
      * 同步ERP(店铺信息)
      *
      * @param shopNameList 实例对象 list
      * @return 实例对象
      */
     @ApiOperation(value =syncShopInfo,response =TbComShop.class)
     @PostResource(name = syncShopInfo, path = "/syncShopInfo")
     @BusinessLog(title = syncShopInfo,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData syncShopInfo(@RequestBody (required = false) List<String> shopNameList){
         return tbComShopService.syncShopInfo(shopNameList);
     }

    /**
     * 新增数据
     *
     * @param tbComShop 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShop.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShop tbComShop){
        return ResponseData.success(tbComShopService.insert(tbComShop));
    }

    /**
     * 更新数据
     *
     * @param tbComShop 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShop.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShop tbComShop){
        return ResponseData.success(tbComShopService.update(tbComShop));
    }

     @ApiOperation(value =applyShutShopName,response =TbComShop.class)
     @PostResource(name = applyShutShopName, path = "/applyShutShop")
     @BusinessLog(title = applyShutShopName,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData applyShutShop(@RequestBody TbComShop tbComShop){
         return tbComShopService.applyShutShop(tbComShop);
     }


     @ApiOperation(value =shutShopName,response =TbComShop.class)
     @PostResource(name = shutShopName, path = "/shutShop")
     @BusinessLog(title = shutShopName,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData shutShop(@RequestBody TbComShop tbComShop){
         return tbComShopService.shutShop(tbComShop);
     }

    /**
     * 通过主键删除数据
     *
     * @param shopName 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  String shopName){
        return ResponseData.success(tbComShopService.deleteById(shopName));
    }

    /**
     * 批量删除数据
     *
     * @param  shopNameList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> shopNameList){
      if (Objects.isNull(shopNameList) || shopNameList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopService.deleteBatchIds(shopNameList));
     }

    /**
     * 导出
     *
     * @param tbComShopParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShop.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopParam tbComShopParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopResult> pageResult = tbComShopService.paginQuery(tbComShopParam, current,size);
       List<TbComShopResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopResult.class).sheet("资源-店铺").doWrite(records);
        return ResponseData.success();
    }


     /**
      * 店铺名称下拉
      *
      * @param filter 是否过滤出店铺正在变更收款账号的店铺名称，默认不需要过滤
      * @return 实例对象
      */
     @ApiOperation(value =queryShopNameList,response =TbComShop.class)
     @GetResource(name = queryShopNameList, path = "/shopNameList" )
     @BusinessLog(title =queryShopNameList,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData shopNameList(@RequestParam(required = false) Boolean filter){
         if (ObjectUtil.isNull(filter)) {
             filter = false;
         }
         return ResponseData.success(tbComShopService.shopNameList(filter));
     }

     @ApiOperation(value ="获取所有店铺名称",response =TbComShop.class)
     @GetResource(name = "获取所有店铺名称", path = "/getAllShopName" )
     //@BusinessLog(title =queryShopNameList,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData getAllShopName(){

         return ResponseData.success(tbComShopService.getAllShopName());
     }

     /**
      * 店铺账号下拉
      * @return
      */
     @ApiOperation(value =queryShopList)
     @GetResource(name = queryShopList, path = "/shopList" )
     @BusinessLog(title =queryShopList, opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData shopList(){
         return ResponseData.success(tbComShopService.shopList());
     }

     @ApiOperation(value =querySiteList)
     @GetResource(name = querySiteList, path = "/siteList" )
     @BusinessLog(title =querySiteList, opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData siteList(){
         return ResponseData.success(tbComShopService.siteList());
     }

     @ApiOperation(value =queryPlatformList)
     @GetResource(name = queryPlatformList, path = "/plaformList" )
     @BusinessLog(title =queryPlatformList, opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData plaformList(){
         return ResponseData.success(tbComShopService.platformList());
     }

     @ApiOperation(value =queryShopFullList)
     @GetResource(name = queryShopFullList, path = "/shopFullList" )
     @BusinessLog(title =queryShopFullList, opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData shopNameFullList(){
         return ResponseData.success(tbComShopService.shopNameList());
     }
}
