package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwTaskAutoCreateParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwTaskAutoCreateResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwTaskAutoCreate;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwTaskAutoCreateService;

 /**
 * TbDwTaskAutoCreate;(Tb_DW_Task_Auto_Create)--控制层
 * @author : LSY
 * @date : 2023-8-14
 */
@Slf4j
@Api(tags = "TbDwTaskAutoCreate接口")
@RestController
@ApiResource(name="TbDwTaskAutoCreate" , path="/tbDwTaskAutoCreate")
public class TbDwTaskAutoCreateController{
     public final String baseName = "TbDwTaskAutoCreate";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbDwTaskAutoCreateService tbDwTaskAutoCreateService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDwId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbDwTaskAutoCreate.class)
    @GetResource(name = queryByIdFunName, path = "/queryById", requiredLogin = false)
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysDwId){
        return ResponseData.success(tbDwTaskAutoCreateService.queryById(sysDwId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbDwTaskAutoCreateParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbDwTaskAutoCreateResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbDwTaskAutoCreateParam tbDwTaskAutoCreateParam){
        //1.分页参数
        Page page = tbDwTaskAutoCreateParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbDwTaskAutoCreateResult> pageResult = tbDwTaskAutoCreateService.paginQuery(tbDwTaskAutoCreateParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbDwTaskAutoCreate 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbDwTaskAutoCreate.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbDwTaskAutoCreate tbDwTaskAutoCreate){
        return ResponseData.success(tbDwTaskAutoCreateService.insert(tbDwTaskAutoCreate));
    }
    
    /** 
     * 更新数据
     *
     * @param tbDwTaskAutoCreate 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbDwTaskAutoCreate.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbDwTaskAutoCreate tbDwTaskAutoCreate){
        return ResponseData.success(tbDwTaskAutoCreateService.update(tbDwTaskAutoCreate));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDwId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal sysDwId){
        return ResponseData.success(tbDwTaskAutoCreateService.deleteById(sysDwId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  sysDwIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysDwIdList){
      if (Objects.isNull(sysDwIdList) || sysDwIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbDwTaskAutoCreateService.deleteBatchIds(sysDwIdList));
     }
    
    /**
     * 导出
     *
     * @param tbDwTaskAutoCreateParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =TbDwTaskAutoCreate.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbDwTaskAutoCreateParam tbDwTaskAutoCreateParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbDwTaskAutoCreateResult> pageResult = tbDwTaskAutoCreateService.paginQuery(tbDwTaskAutoCreateParam, current,size);
       List<TbDwTaskAutoCreateResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("TbDwTaskAutoCreate.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbDwTaskAutoCreateResult.class).sheet("TbDwTaskAutoCreate").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}