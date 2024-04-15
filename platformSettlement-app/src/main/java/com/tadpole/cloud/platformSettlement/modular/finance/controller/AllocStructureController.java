package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AllocStructureResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

 /**
 * ;(ALLOC_STRUCTURE)表控制层
 * @author : LSY
 * @date : 2023-12-19
 */
@Api(tags = "分摊架构")
@RestController
@ApiResource(name = "分摊架构", path="/allocStructure")
public class AllocStructureController{
     public final String baseName = "分摊架构";
     public final String queryByIdFunName = baseName+"--通过ID查询";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增";
     public final String doAllocStructure = baseName+"--生成分摊架构";
     public final String calPlatformAlloc = baseName+"--计算平台分摊";
     public final String editFunName = baseName+"--更新";
     public final String exportFunName = baseName+"--按查询条件导出";
     public final String deleteByIdFunName = baseName+"--通过主键删除据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除";
    @Resource
    private AllocStructureService allocStructureService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=AllocStructure.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId", requiredLogin = false)
    public ResponseData  queryById(String undefinedId){
        return ResponseData.success(allocStructureService.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param allocStructureParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=AllocStructure.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody AllocStructureParam allocStructureParm ){
        //1.分页参数
       Page page = allocStructureParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AllocStructureResult> pageResult = allocStructureService.paginQuery(allocStructureParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 实现分摊架构
     *
     * @param allocStructure 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =doAllocStructure,response =AllocStructure.class)
    @PostResource(name = doAllocStructure, path = "/doAllocStructure",  requiredLogin = false)
    @BusinessLog(title = doAllocStructure,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  doAllocStructure(@RequestBody AllocStructure allocStructure){
        return allocStructureService.doAllocStructure(allocStructure);
    }

     @ParamValidator
     @PostResource(name = "分摊架构-导入", path = "/import")
     @ApiOperation(value = "分摊架构-导入")
     @BusinessLog(title = "分摊架构-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
         return allocStructureService.importExcel(file);
     }

     @PostResource(name = "分摊架构-批量更新", path = "/updateBatch")
     @ApiOperation(value = "分摊架构-批量更新")
     @BusinessLog(title = "分摊架构-批量更新",opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData updateBatch(@RequestBody List<AllocStructure> allocStructureList) {
         return allocStructureService.updateBatch(allocStructureList);
     }



     @ApiOperation(value =addFunName,response =AllocStructure.class)
     @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
     @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData  add(@RequestBody AllocStructure allocStructure){
         return ResponseData.success(allocStructureService.insert(allocStructure));
     }
    
    /** 
     * 更新数据
     *
     * @param allocStructure 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =AllocStructure.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody AllocStructureParam allocStructure){
        AllocStructure result = allocStructureService.update(allocStructure);
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
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String undefinedId){
         if (allocStructureService.deleteById(undefinedId)) {
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
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> undefinedIdList){
      if (Objects.isNull(undefinedIdList) || undefinedIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(allocStructureService.deleteBatchIds(undefinedIdList));
     }
     /**
     * 导出
     *
     * @param allocStructureParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response = AllocStructureResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody AllocStructureParam allocStructureParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AllocStructureResult> pageResult = allocStructureService.paginQuery(allocStructureParm, current,size);
       List<AllocStructureResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("分摊架构.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AllocStructureResult.class).sheet("").doWrite(records);
        return ResponseData.success();
    }


     @ParamValidator
     @PostResource(name = "分摊架构确认", path = "/confirm")
     @ApiOperation(value = "分摊架构确认")
     @BusinessLog(title = "分摊架构确认",opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData confirm(@RequestBody AllocStructureParam param) {
         return allocStructureService.confirm(param);
     }


     @ParamValidator
     @PostResource(name = "更新上月分摊架构", path = "/fillLastMonAllocRation")
     @ApiOperation(value = "更新上月分摊架构")
     @BusinessLog(title = "更新上月分摊架构",opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData fillLastMonAllocRation(@RequestBody AllocStructure param) {
         return allocStructureService.fillLastMonAllocRation(param);
     }
}