package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwSourceDataParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwSourceDataResult;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwSourceDataService;
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
 * TbDWSourceData;(Tb_DW_Source_Data)--控制层
 * @author : LSY
 * @date : 2023-8-14
 */
@Slf4j
@Api(tags = "TbDWSourceData接口")
@RestController
@ApiResource(name="TbDWSourceData" , path="/tbDwSourceData")
public class TbDwSourceDataController{
     public final String baseName = "TbDWSourceData";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbDwSourceDataService tbDwSourceDataService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param dwDataObjNum 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbDwSourceData.class)
    @GetResource(name = queryByIdFunName, path = "/queryById", requiredLogin = false)
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(String dwDataObjNum){
        return ResponseData.success(tbDwSourceDataService.queryById(dwDataObjNum));
    }
    
    /** 
     * 分页查询
     *
     * @param tbDwSourceDataParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbDwSourceDataResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbDwSourceDataParam tbDwSourceDataParam){
        //1.分页参数
        Page page = tbDwSourceDataParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbDwSourceDataResult> pageResult = tbDwSourceDataService.paginQuery(tbDwSourceDataParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbDwSourceData.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbDwSourceData tbDwSourceData){
        return ResponseData.success(tbDwSourceDataService.insert(tbDwSourceData));
    }
    
    /** 
     * 更新数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbDwSourceData.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbDwSourceData tbDwSourceData){
        return ResponseData.success(tbDwSourceDataService.update(tbDwSourceData));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param dwDataObjNum 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  String dwDataObjNum){
        return ResponseData.success(tbDwSourceDataService.deleteById(dwDataObjNum));
    }
    
    /**
     * 批量删除数据
     *
     * @param  dwDataObjNumList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> dwDataObjNumList){
      if (Objects.isNull(dwDataObjNumList) || dwDataObjNumList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbDwSourceDataService.deleteBatchIds(dwDataObjNumList));
     }
    
    /**
     * 导出
     *
     * @param tbDwSourceDataParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =TbDwSourceData.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbDwSourceDataParam tbDwSourceDataParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbDwSourceDataResult> pageResult = tbDwSourceDataService.paginQuery(tbDwSourceDataParam, current,size);
       List<TbDwSourceDataResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("TbDWSourceData.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbDwSourceDataResult.class).sheet("TbDWSourceData").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}