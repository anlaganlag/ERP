package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import java.util.List;
import javax.annotation.Resource;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbAmazonInGoodsQtyNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbAmazonInGoodsQtyNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbAmazonInGoodsQtyNewV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbAmazonInGoodsQtyNewV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
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
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;
import java.math.BigDecimal;

 /**
 * Amazon在途库存报表;(Tb_Amazon_In_Goods_Qty_New_V2)表控制层
 * @author : LSY
 * @date : 2024-3-18
 */
@Api(tags = "Amazon在途库存报表")
@RestController
@ApiResource(name = "Amazon在途库存报表", path="/tbAmazonInGoodsQtyNewV2")
public class TbAmazonInGoodsQtyNewV2Controller{
     public final String baseName = "Amazon在途库存报表";
     public final String queryByIdFunName = baseName+"--通过ID查询Amazon在途库存报表";
     public final String paginQueryFunName = baseName+"--分页查询Amazon在途库存报表";
     public final String addFunName = baseName+"--新增Amazon在途库存报表";
     public final String editFunName = baseName+"--更新Amazon在途库存报表";
     public final String exportFunName = baseName+"--按查询条件导出Amazon在途库存报表";
     public final String deleteByIdFunName = baseName+"--通过主键删除Amazon在途库存报表据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除Amazon在途库存报表";
    @Resource
    private TbAmazonInGoodsQtyNewV2Service tbAmazonInGoodsQtyNewV2Service;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbAmazonInGoodsQtyNewV2.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(tbAmazonInGoodsQtyNewV2Service.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbAmazonInGoodsQtyNewV2Parm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbAmazonInGoodsQtyNewV2.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbAmazonInGoodsQtyNewV2Param tbAmazonInGoodsQtyNewV2Parm ){
        //1.分页参数
       Page page = tbAmazonInGoodsQtyNewV2Parm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbAmazonInGoodsQtyNewV2Result> pageResult = tbAmazonInGoodsQtyNewV2Service.paginQuery(tbAmazonInGoodsQtyNewV2Parm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbAmazonInGoodsQtyNewV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbAmazonInGoodsQtyNewV2.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbAmazonInGoodsQtyNewV2 tbAmazonInGoodsQtyNewV2){
        return ResponseData.success(tbAmazonInGoodsQtyNewV2Service.insert(tbAmazonInGoodsQtyNewV2));
    }
    
    /** 
     * 更新数据
     *
     * @param tbAmazonInGoodsQtyNewV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbAmazonInGoodsQtyNewV2.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbAmazonInGoodsQtyNewV2Param tbAmazonInGoodsQtyNewV2){
        TbAmazonInGoodsQtyNewV2 result = tbAmazonInGoodsQtyNewV2Service.update(tbAmazonInGoodsQtyNewV2);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal id){
         if (tbAmazonInGoodsQtyNewV2Service.deleteById(id)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  idList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> idList){
      if (Objects.isNull(idList) || idList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbAmazonInGoodsQtyNewV2Service.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param tbAmazonInGoodsQtyNewV2Parm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbAmazonInGoodsQtyNewV2Result.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbAmazonInGoodsQtyNewV2Param tbAmazonInGoodsQtyNewV2Parm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbAmazonInGoodsQtyNewV2Result> pageResult = tbAmazonInGoodsQtyNewV2Service.paginQuery(tbAmazonInGoodsQtyNewV2Parm, current,size);
       List<TbAmazonInGoodsQtyNewV2Result> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("Amazon在途库存报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbAmazonInGoodsQtyNewV2Result.class).sheet("Amazon在途库存报表").doWrite(records);
        return ResponseData.success();
    }
}