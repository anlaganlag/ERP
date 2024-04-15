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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbReceivedInvenrotyAnalysisV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbReceivedInvenrotyAnalysisV2Service;
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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbReceivedInvenrotyAnalysisV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbReceivedInvenrotyAnalysisV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbReceivedInvenrotyAnalysisV2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbReceivedInvenrotyAnalysisV2Service;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;
import java.math.BigDecimal;

 /**
 * 来货报告;(Tb_Received_Invenroty_Analysis_V2)表控制层
 * @author : LSY
 * @date : 2024-3-18
 */
@Api(tags = "来货报告")
@RestController
@ApiResource(name = "来货报告", path="/tbReceivedInvenrotyAnalysisV2")
public class TbReceivedInvenrotyAnalysisV2Controller{
     public final String baseName = "来货报告";
     public final String queryByIdFunName = baseName+"--通过ID查询来货报告";
     public final String paginQueryFunName = baseName+"--分页查询来货报告";
     public final String addFunName = baseName+"--新增来货报告";
     public final String editFunName = baseName+"--更新来货报告";
     public final String exportFunName = baseName+"--按查询条件导出来货报告";
     public final String deleteByIdFunName = baseName+"--通过主键删除来货报告据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除来货报告";
    @Resource
    private TbReceivedInvenrotyAnalysisV2Service tbReceivedInvenrotyAnalysisV2Service;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbReceivedInvenrotyAnalysisV2.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId" )
    public ResponseData  queryById(String undefinedId){
        return ResponseData.success(tbReceivedInvenrotyAnalysisV2Service.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbReceivedInvenrotyAnalysisV2Parm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbReceivedInvenrotyAnalysisV2.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbReceivedInvenrotyAnalysisV2Param tbReceivedInvenrotyAnalysisV2Parm ){
        //1.分页参数
       Page page = tbReceivedInvenrotyAnalysisV2Parm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbReceivedInvenrotyAnalysisV2Result> pageResult = tbReceivedInvenrotyAnalysisV2Service.paginQuery(tbReceivedInvenrotyAnalysisV2Parm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbReceivedInvenrotyAnalysisV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbReceivedInvenrotyAnalysisV2.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbReceivedInvenrotyAnalysisV2 tbReceivedInvenrotyAnalysisV2){
        return ResponseData.success(tbReceivedInvenrotyAnalysisV2Service.insert(tbReceivedInvenrotyAnalysisV2));
    }
    
    /** 
     * 更新数据
     *
     * @param tbReceivedInvenrotyAnalysisV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbReceivedInvenrotyAnalysisV2.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbReceivedInvenrotyAnalysisV2Param tbReceivedInvenrotyAnalysisV2){
        TbReceivedInvenrotyAnalysisV2 result = tbReceivedInvenrotyAnalysisV2Service.update(tbReceivedInvenrotyAnalysisV2);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String undefinedId){
         if (tbReceivedInvenrotyAnalysisV2Service.deleteById(undefinedId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  undefinedIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> undefinedIdList){
      if (Objects.isNull(undefinedIdList) || undefinedIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbReceivedInvenrotyAnalysisV2Service.deleteBatchIds(undefinedIdList));
     }
     /**
     * 导出
     *
     * @param tbReceivedInvenrotyAnalysisV2Parm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbReceivedInvenrotyAnalysisV2Result.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbReceivedInvenrotyAnalysisV2Param tbReceivedInvenrotyAnalysisV2Parm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbReceivedInvenrotyAnalysisV2Result> pageResult = tbReceivedInvenrotyAnalysisV2Service.paginQuery(tbReceivedInvenrotyAnalysisV2Parm, current,size);
       List<TbReceivedInvenrotyAnalysisV2Result> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("来货报告.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbReceivedInvenrotyAnalysisV2Result.class).sheet("来货报告").doWrite(records);
        return ResponseData.success();
    }
}