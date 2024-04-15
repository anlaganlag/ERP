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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimDetToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimDetToAmazonParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimDetToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsClaimDetToAmazonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 亚马逊物流索赔明细;(tb_logistics_claim_det_to_amazon)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊物流索赔明细")
@RestController
@ApiResource(name = "亚马逊物流索赔明细", path="/tbLogisticsClaimDetToAmazon")
public class TbLogisticsClaimDetToAmazonController{
     public final String baseName = "亚马逊物流索赔明细";
     public final String queryByIdFunName = baseName+"--通过ID查询亚马逊物流索赔明细";
     public final String paginQueryFunName = baseName+"--分页查询亚马逊物流索赔明细";
     public final String addFunName = baseName+"--新增亚马逊物流索赔明细";
     public final String editFunName = baseName+"--更新亚马逊物流索赔明细";
     public final String exportFunName = baseName+"--按查询条件导出亚马逊物流索赔明细";
     public final String deleteByIdFunName = baseName+"--通过主键删除亚马逊物流索赔明细据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除亚马逊物流索赔明细";
    @Resource
    private TbLogisticsClaimDetToAmazonService tbLogisticsClaimDetToAmazonService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkClaimDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsClaimDetToAmazon.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypkClaimDetId" )
    public ResponseData  queryById(BigDecimal pkClaimDetId){
        return ResponseData.success(tbLogisticsClaimDetToAmazonService.queryById(pkClaimDetId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsClaimDetToAmazonParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsClaimDetToAmazon.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsClaimDetToAmazonParam tbLogisticsClaimDetToAmazonParm ){
        //1.分页参数
       Page page = tbLogisticsClaimDetToAmazonParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsClaimDetToAmazonResult> pageResult = tbLogisticsClaimDetToAmazonService.paginQuery(tbLogisticsClaimDetToAmazonParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsClaimDetToAmazon 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsClaimDetToAmazon.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsClaimDetToAmazon tbLogisticsClaimDetToAmazon){
        return ResponseData.success(tbLogisticsClaimDetToAmazonService.insert(tbLogisticsClaimDetToAmazon));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsClaimDetToAmazon 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsClaimDetToAmazon.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsClaimDetToAmazonParam tbLogisticsClaimDetToAmazon){
        TbLogisticsClaimDetToAmazon result = tbLogisticsClaimDetToAmazonService.update(tbLogisticsClaimDetToAmazon);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkClaimDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal pkClaimDetId){
         if (tbLogisticsClaimDetToAmazonService.deleteById(pkClaimDetId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  pkClaimDetIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> pkClaimDetIdList){
      if (Objects.isNull(pkClaimDetIdList) || pkClaimDetIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsClaimDetToAmazonService.deleteBatchIds(pkClaimDetIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsClaimDetToAmazonParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsClaimDetToAmazonResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsClaimDetToAmazonParam tbLogisticsClaimDetToAmazonParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsClaimDetToAmazonResult> pageResult = tbLogisticsClaimDetToAmazonService.paginQuery(tbLogisticsClaimDetToAmazonParm, current,size);
       List<TbLogisticsClaimDetToAmazonResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊物流索赔明细.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsClaimDetToAmazonResult.class).sheet("亚马逊物流索赔明细").doWrite(records);
        return ResponseData.success();
    }
}