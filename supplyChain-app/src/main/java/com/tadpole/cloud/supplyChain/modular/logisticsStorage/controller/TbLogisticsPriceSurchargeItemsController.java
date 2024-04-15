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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceSurchargeItems;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceSurchargeItemsParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceSurchargeItemsResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPriceSurchargeItemsService;
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
 * 物流价格附加项目--暂时不需要;(tb_logistics_price_surcharge_items)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流价格附加项目--暂时不需要")
@RestController
@ApiResource(name = "物流价格附加项目--暂时不需要", path="/tbLogisticsPriceSurchargeItems")
public class TbLogisticsPriceSurchargeItemsController{
     public final String baseName = "物流价格附加项目--暂时不需要";
     public final String queryByIdFunName = baseName+"--通过ID查询物流价格附加项目--暂时不需要";
     public final String paginQueryFunName = baseName+"--分页查询物流价格附加项目--暂时不需要";
     public final String addFunName = baseName+"--新增物流价格附加项目--暂时不需要";
     public final String editFunName = baseName+"--更新物流价格附加项目--暂时不需要";
     public final String exportFunName = baseName+"--按查询条件导出物流价格附加项目--暂时不需要";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流价格附加项目--暂时不需要据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流价格附加项目--暂时不需要";
    @Resource
    private TbLogisticsPriceSurchargeItemsService tbLogisticsPriceSurchargeItemsService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpsiId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsPriceSurchargeItems.class)
    @GetResource(name = queryByIdFunName, path = "/queryBylpsiId" )
    public ResponseData  queryById(BigDecimal lpsiId){
        return ResponseData.success(tbLogisticsPriceSurchargeItemsService.queryById(lpsiId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPriceSurchargeItemsParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPriceSurchargeItems.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPriceSurchargeItemsParam tbLogisticsPriceSurchargeItemsParm ){
        //1.分页参数
       Page page = tbLogisticsPriceSurchargeItemsParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPriceSurchargeItemsResult> pageResult = tbLogisticsPriceSurchargeItemsService.paginQuery(tbLogisticsPriceSurchargeItemsParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceSurchargeItems 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPriceSurchargeItems.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPriceSurchargeItems tbLogisticsPriceSurchargeItems){
        return ResponseData.success(tbLogisticsPriceSurchargeItemsService.insert(tbLogisticsPriceSurchargeItems));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPriceSurchargeItems 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPriceSurchargeItems.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPriceSurchargeItemsParam tbLogisticsPriceSurchargeItems){
        TbLogisticsPriceSurchargeItems result = tbLogisticsPriceSurchargeItemsService.update(tbLogisticsPriceSurchargeItems);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpsiId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal lpsiId){
         if (tbLogisticsPriceSurchargeItemsService.deleteById(lpsiId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  lpsiIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> lpsiIdList){
      if (Objects.isNull(lpsiIdList) || lpsiIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsPriceSurchargeItemsService.deleteBatchIds(lpsiIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPriceSurchargeItemsParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPriceSurchargeItemsResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPriceSurchargeItemsParam tbLogisticsPriceSurchargeItemsParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPriceSurchargeItemsResult> pageResult = tbLogisticsPriceSurchargeItemsService.paginQuery(tbLogisticsPriceSurchargeItemsParm, current,size);
       List<TbLogisticsPriceSurchargeItemsResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流价格附加项目--暂时不需要.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPriceSurchargeItemsResult.class).sheet("物流价格附加项目--暂时不需要").doWrite(records);
        return ResponseData.success();
    }
}