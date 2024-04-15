package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import java.util.List;
import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbComOverseasWarehouse;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbComOverseasWarehouseService;
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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbComOverseasWarehouseParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbComOverseasWarehouseResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbComOverseasWarehouseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbComOverseasWarehouseService;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;
import java.math.BigDecimal;

 /**
 * 海外仓信息;(Tb_Com_Overseas_Warehouse)表控制层
 * @author : LSY
 * @date : 2024-1-19
 */
@Api(tags = "海外仓信息")
@RestController
@ApiResource(name = "海外仓信息", path="/tbComOverseasWarehouse")
public class TbComOverseasWarehouseController{
     public final String baseName = "海外仓信息";
     public final String queryByIdFunName = baseName+"--通过ID查询海外仓信息";
     public final String paginQueryFunName = baseName+"--分页查询海外仓信息";
     public final String addFunName = baseName+"--新增海外仓信息";
     public final String editFunName = baseName+"--更新海外仓信息";
     public final String exportFunName = baseName+"--按查询条件导出海外仓信息";
     public final String deleteByIdFunName = baseName+"--通过主键删除海外仓信息据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除海外仓信息";
    @Resource
    private TbComOverseasWarehouseService tbComOverseasWarehouseService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param owName 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbComOverseasWarehouse.class)
    @GetResource(name = queryByIdFunName, path = "/queryByowName" )
    public ResponseData  queryById(String owName){
        return ResponseData.success(tbComOverseasWarehouseService.queryById(owName));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComOverseasWarehouseParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbComOverseasWarehouse.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComOverseasWarehouseParam tbComOverseasWarehouseParm ){
        //1.分页参数
       Page page = tbComOverseasWarehouseParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComOverseasWarehouseResult> pageResult = tbComOverseasWarehouseService.paginQuery(tbComOverseasWarehouseParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComOverseasWarehouse 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComOverseasWarehouse.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbComOverseasWarehouse tbComOverseasWarehouse){
        return ResponseData.success(tbComOverseasWarehouseService.insert(tbComOverseasWarehouse));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComOverseasWarehouse 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComOverseasWarehouse.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbComOverseasWarehouseParam tbComOverseasWarehouse){
        TbComOverseasWarehouse result = tbComOverseasWarehouseService.update(tbComOverseasWarehouse);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param owName 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String owName){
         if (tbComOverseasWarehouseService.deleteById(owName)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  owNameList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> owNameList){
      if (Objects.isNull(owNameList) || owNameList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComOverseasWarehouseService.deleteBatchIds(owNameList));
     }
     /**
     * 导出
     *
     * @param tbComOverseasWarehouseParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComOverseasWarehouseResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComOverseasWarehouseParam tbComOverseasWarehouseParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComOverseasWarehouseResult> pageResult = tbComOverseasWarehouseService.paginQuery(tbComOverseasWarehouseParm, current,size);
       List<TbComOverseasWarehouseResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓信息.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComOverseasWarehouseResult.class).sheet("海外仓信息").doWrite(records);
        return ResponseData.success();
    }
}