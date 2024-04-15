package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import java.util.List;
import javax.annotation.Resource;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWayAnalysisNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbBscOverseasWayAnalysisNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayAnalysisNewV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbBscOverseasWayAnalysisNewV2Service;
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
 * 发货单数据;(Tb_Bsc_Overseas_Way_Analysis_New_V2)表控制层
 * @author : LSY
 * @date : 2024-3-18
 */
@Api(tags = "发货单数据")
@RestController
@ApiResource(name = "发货单数据", path="/tbBscOverseasWayAnalysisNewV2")
public class TbBscOverseasWayAnalysisNewV2Controller{
     public final String baseName = "发货单数据";
     public final String queryByIdFunName = baseName+"--通过ID查询发货单数据";
     public final String paginQueryFunName = baseName+"--分页查询发货单数据";
     public final String addFunName = baseName+"--新增发货单数据";
     public final String editFunName = baseName+"--更新发货单数据";
     public final String exportFunName = baseName+"--按查询条件导出发货单数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除发货单数据据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除发货单数据";
    @Resource
    private TbBscOverseasWayAnalysisNewV2Service tbBscOverseasWayAnalysisNewV2Service;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbBscOverseasWayAnalysisNewV2.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId" )
    public ResponseData  queryById(String undefinedId){
        return ResponseData.success(tbBscOverseasWayAnalysisNewV2Service.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbBscOverseasWayAnalysisNewV2Parm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbBscOverseasWayAnalysisNewV2.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbBscOverseasWayAnalysisNewV2Param tbBscOverseasWayAnalysisNewV2Parm ){
        //1.分页参数
       Page page = tbBscOverseasWayAnalysisNewV2Parm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbBscOverseasWayAnalysisNewV2Result> pageResult = tbBscOverseasWayAnalysisNewV2Service.paginQuery(tbBscOverseasWayAnalysisNewV2Parm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbBscOverseasWayAnalysisNewV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbBscOverseasWayAnalysisNewV2.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbBscOverseasWayAnalysisNewV2 tbBscOverseasWayAnalysisNewV2){
        return ResponseData.success(tbBscOverseasWayAnalysisNewV2Service.insert(tbBscOverseasWayAnalysisNewV2));
    }
    
    /** 
     * 更新数据
     *
     * @param tbBscOverseasWayAnalysisNewV2 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbBscOverseasWayAnalysisNewV2.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbBscOverseasWayAnalysisNewV2Param tbBscOverseasWayAnalysisNewV2){
        TbBscOverseasWayAnalysisNewV2 result = tbBscOverseasWayAnalysisNewV2Service.update(tbBscOverseasWayAnalysisNewV2);
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
         if (tbBscOverseasWayAnalysisNewV2Service.deleteById(undefinedId)) {
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
       return ResponseData.success(tbBscOverseasWayAnalysisNewV2Service.deleteBatchIds(undefinedIdList));
     }
     /**
     * 导出
     *
     * @param tbBscOverseasWayAnalysisNewV2Parm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbBscOverseasWayAnalysisNewV2Result.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbBscOverseasWayAnalysisNewV2Param tbBscOverseasWayAnalysisNewV2Parm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbBscOverseasWayAnalysisNewV2Result> pageResult = tbBscOverseasWayAnalysisNewV2Service.paginQuery(tbBscOverseasWayAnalysisNewV2Parm, current,size);
       List<TbBscOverseasWayAnalysisNewV2Result> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("发货单数据.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbBscOverseasWayAnalysisNewV2Result.class).sheet("发货单数据").doWrite(records);
        return ResponseData.success();
    }
}