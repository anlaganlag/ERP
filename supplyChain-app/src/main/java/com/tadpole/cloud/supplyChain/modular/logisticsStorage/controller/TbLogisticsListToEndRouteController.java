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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToEndRouteService;
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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToEndRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsListToEndRouteMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToEndRouteService;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;
import java.math.BigDecimal;

 /**
 * 物流单尾程信息;(tb_logistics_list_to_end_route)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流单尾程信息")
@RestController
@ApiResource(name = "物流单尾程信息", path="/tbLogisticsListToEndRoute")
public class TbLogisticsListToEndRouteController{
     public final String baseName = "物流单尾程信息";
     public final String queryByIdFunName = baseName+"--通过ID查询物流单尾程信息";
     public final String paginQueryFunName = baseName+"--分页查询物流单尾程信息";
     public final String addFunName = baseName+"--新增物流单尾程信息";
     public final String editFunName = baseName+"--更新物流单尾程信息";
     public final String exportFunName = baseName+"--按查询条件导出物流单尾程信息";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流单尾程信息据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流单尾程信息";
    @Resource
    private TbLogisticsListToEndRouteService tbLogisticsListToEndRouteService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsListToEndRoute.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(tbLogisticsListToEndRouteService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsListToEndRouteParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsListToEndRoute.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsListToEndRouteParam tbLogisticsListToEndRouteParm ){
        //1.分页参数
       Page page = tbLogisticsListToEndRouteParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsListToEndRouteResult> pageResult = tbLogisticsListToEndRouteService.paginQuery(tbLogisticsListToEndRouteParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToEndRoute 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsListToEndRoute.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsListToEndRoute tbLogisticsListToEndRoute){
        return ResponseData.success(tbLogisticsListToEndRouteService.insert(tbLogisticsListToEndRoute));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsListToEndRoute 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsListToEndRoute.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsListToEndRouteParam tbLogisticsListToEndRoute){
        TbLogisticsListToEndRoute result = tbLogisticsListToEndRouteService.update(tbLogisticsListToEndRoute);
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
         if (tbLogisticsListToEndRouteService.deleteById(id)) {
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
       return ResponseData.success(tbLogisticsListToEndRouteService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsListToEndRouteParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsListToEndRouteResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsListToEndRouteParam tbLogisticsListToEndRouteParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsListToEndRouteResult> pageResult = tbLogisticsListToEndRouteService.paginQuery(tbLogisticsListToEndRouteParm, current,size);
       List<TbLogisticsListToEndRouteResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流单尾程信息.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsListToEndRouteResult.class).sheet("物流单尾程信息").doWrite(records);
        return ResponseData.success();
    }
}