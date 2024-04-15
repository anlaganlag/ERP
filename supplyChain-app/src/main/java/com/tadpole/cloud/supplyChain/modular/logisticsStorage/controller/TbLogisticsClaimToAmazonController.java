package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyBankCollectTaskResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimToAmazonParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.DownloadLogisticsAmazonClaimsViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsAmazonClaimsEmailViewModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsClaimToAmazonService;
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
 * 亚马逊物流索赔;(tb_logistics_claim_to_amazon)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊物流索赔")
@RestController
@ApiResource(name = "亚马逊物流索赔", path="/tbLogisticsClaimToAmazon")
public class TbLogisticsClaimToAmazonController{
     public final String baseName = "亚马逊物流索赔";
     public final String queryByIdFunName = baseName+"--通过ID查询亚马逊物流索赔";
     public final String queryLogisticsAmazonClaimsEmail = baseName+"--查看索赔邮件内容模板";
     public final String downloadLogisticsAmazonClaims = baseName+"--下载索赔附件模板";
     public final String markLogisticsAmazonClaims = baseName+"--标记申请索赔";
     public final String paginQueryFunName = baseName+"--分页查询亚马逊物流索赔";
     public final String addFunName = baseName+"--新增亚马逊物流索赔";
     public final String editFunName = baseName+"--更新亚马逊物流索赔";
     public final String exportFunName = baseName+"--按查询条件导出亚马逊物流索赔";
     public final String deleteByIdFunName = baseName+"--通过主键删除亚马逊物流索赔据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除亚马逊物流索赔";
    @Resource
    private TbLogisticsClaimToAmazonService tbLogisticsClaimToAmazonService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkClaimId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsClaimToAmazon.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypkClaimId" )
    public ResponseData  queryById(String pkClaimId){
        return ResponseData.success(tbLogisticsClaimToAmazonService.queryById(pkClaimId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsClaimToAmazonParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsClaimToAmazon.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsClaimToAmazonParam tbLogisticsClaimToAmazonParm ){
        //1.分页参数
       Page page = tbLogisticsClaimToAmazonParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsClaimToAmazonResult> pageResult = tbLogisticsClaimToAmazonService.paginQuery(tbLogisticsClaimToAmazonParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsClaimToAmazon 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsClaimToAmazon.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsClaimToAmazon tbLogisticsClaimToAmazon){
        return ResponseData.success(tbLogisticsClaimToAmazonService.insert(tbLogisticsClaimToAmazon));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsClaimToAmazon 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsClaimToAmazon.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsClaimToAmazonParam tbLogisticsClaimToAmazon){
        TbLogisticsClaimToAmazon result = tbLogisticsClaimToAmazonService.update(tbLogisticsClaimToAmazon);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkClaimId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String pkClaimId){
         if (tbLogisticsClaimToAmazonService.deleteById(pkClaimId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  pkClaimIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> pkClaimIdList){
      if (Objects.isNull(pkClaimIdList) || pkClaimIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsClaimToAmazonService.deleteBatchIds(pkClaimIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsClaimToAmazonParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsClaimToAmazonResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsClaimToAmazonParam tbLogisticsClaimToAmazonParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsClaimToAmazonResult> pageResult = tbLogisticsClaimToAmazonService.paginQuery(tbLogisticsClaimToAmazonParm, current,size);
       List<TbLogisticsClaimToAmazonResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊物流索赔.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsClaimToAmazonResult.class).sheet("亚马逊物流索赔").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 查看索赔邮件内容模板
      *
      * @param pkClaimID pkClaimID
      * @return 实例对象
      */
     @ApiOperation(value =queryLogisticsAmazonClaimsEmail,response = LogisticsAmazonClaimsEmailViewModel.class)
     @PostResource(name = queryLogisticsAmazonClaimsEmail, path = "/queryLogisticsAmazonClaimsEmail" )
     @BusinessLog(title = queryLogisticsAmazonClaimsEmail,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData  queryLogisticsAmazonClaimsEmail( String pkClaimID){
         List<LogisticsAmazonClaimsEmailViewModel> result = tbLogisticsClaimToAmazonService.queryLogisticsAmazonClaimsEmail(pkClaimID);
             return ResponseData.success(result);
     }

     /**
      * 下载索赔附件模板
      *
      * @param pkClaimID pkClaimID
      * @return 实例对象
      */
     @ApiOperation(value =downloadLogisticsAmazonClaims,response = LogisticsAmazonClaimsEmailViewModel.class)
     @PostResource(name = downloadLogisticsAmazonClaims, path = "/downloadLogisticsAmazonClaims" )
     @BusinessLog(title = downloadLogisticsAmazonClaims,opType = LogAnnotionOpTypeEnum.EXPORT)
     public ResponseData  downloadLogisticsAmazonClaims( String pkClaimID,HttpServletResponse response) throws IOException{
         List<LogisticsAmazonClaimsEmailViewModel> emailViewModelResult = tbLogisticsClaimToAmazonService.queryLogisticsAmazonClaimsEmail(pkClaimID);
         if (ObjectUtil.isEmpty(emailViewModelResult)) {
             return ResponseData.success();
         }
         String planName = emailViewModelResult.get(0).getPlanName();
         if (ObjectUtil.isEmpty(planName)) {
             planName = "Amazon索赔附件模板.xlsx";
         } else {
             planName = planName + ".xlsx";
         }
         List<DownloadLogisticsAmazonClaimsViewModel> downloadResult = tbLogisticsClaimToAmazonService.downloadLogisticsAmazonClaims(pkClaimID);
         response.addHeader("Content-Disposition", "attachment;filename=" + new String(planName.getBytes("utf-8"),"ISO8859-1"));
         EasyExcel.write(response.getOutputStream(), DownloadLogisticsAmazonClaimsViewModel.class).sheet(0).doWrite(downloadResult);

         return ResponseData.success();
     }

     /**
      * 标记申请索赔
      *
      * @param param
      * @return 实例对象
      */
     @ApiOperation(value =markLogisticsAmazonClaims,response = LogisticsAmazonClaimsEmailViewModel.class)
     @PostResource(name = markLogisticsAmazonClaims, path = "/markLogisticsAmazonClaims" )
     @BusinessLog(title = markLogisticsAmazonClaims,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData  markLogisticsAmazonClaims( @RequestBody TbLogisticsClaimToAmazonParam param){
         return tbLogisticsClaimToAmazonService.markLogisticsAmazonClaims(param);
     }

}