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
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.PersonAllocParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.result.PersonAllocResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.PersonAllocService;
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
import java.util.Map;
import java.util.Objects;

 /**
 * ;(PERSON_ALLOC)表控制层
 * @author : LSY
 * @date : 2023-12-19
 */
@Api(tags = "分摊人员")
@RestController
@ApiResource(name = "分摊人员", path="/personAlloc")
public class PersonAllocController{
     public final String baseName = "分摊人员";
     public final String queryByIdFunName = baseName+"--通过ID查询";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增";
     public final String editFunName = baseName+"--更新";
     public final String exportFunName = baseName+"--按查询条件导出";
     public final String deleteByIdFunName = baseName+"--通过主键删除据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除";
    @Resource
    private PersonAllocService personAllocService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=PersonAlloc.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId", requiredLogin = false)
    public ResponseData  queryById(String undefinedId){
        return ResponseData.success(personAllocService.queryById(undefinedId));
    }
    
    /** 
     * 分页查询
     *
     * @param personAllocParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=PersonAlloc.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody PersonAllocParam personAllocParm ){
        //1.分页参数
       Page page = personAllocParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<PersonAllocResult> pageResult = personAllocService.paginQuery(personAllocParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param personAlloc 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =PersonAlloc.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody PersonAlloc personAlloc){
        return ResponseData.success(personAllocService.insert(personAlloc));
    }
    
    /** 
     * 更新数据
     *
     * @param personAlloc 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =PersonAlloc.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody PersonAllocParam personAlloc){
        PersonAlloc result = personAllocService.update(personAlloc);
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
         if (personAllocService.deleteById(undefinedId)) {
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
       return ResponseData.success(personAllocService.deleteBatchIds(undefinedIdList));
     }
     /**
     * 导出
     *
     * @param personAllocParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =PersonAllocResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody PersonAllocParam personAllocParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<PersonAllocResult> pageResult = personAllocService.paginQuery(personAllocParm, current,size);
       List<PersonAllocResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("人员分摊表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), PersonAllocResult.class).sheet("").doWrite(records);
        return ResponseData.success();
    }


     @ParamValidator
     @PostResource(name = "人员分摊-导入", path = "/import")
     @ApiOperation(value = "人员分摊-导入")
     @BusinessLog(title = "人员分摊-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
         return personAllocService.importExcel(file);
     }

     @ParamValidator
     @PostResource(name = "获取当月人员分摊", path = "/getCurrent")
     @ApiOperation(value = "获取当月人员分摊")
     @BusinessLog(title = "获取当月人员分摊",opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData getCurrent() {
         return ResponseData.success(personAllocService.getCurrent());
     }


     @ParamValidator
     @PostResource(name = "获取当月人员平台", path = "/getPlatform")
     @ApiOperation(value = "获取当月人员平台")
     @BusinessLog(title = "获取当月人员平台",opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData getPlatform() {
         return ResponseData.success(personAllocService.getPlatform());
     }


     @ParamValidator
     @PostResource(name = "获取当月人员部门", path = "/getDepart")
     @ApiOperation(value = "获取当月人员部门")
     @BusinessLog(title = "获取当月人员部门")
     public ResponseData getDepart() {
         return ResponseData.success(personAllocService.getDepart());
     }



     @ParamValidator
     @PostResource(name = "获取当月人员Team", path = "/getTeam")
     @ApiOperation(value = "获取当月人员Team")
     @BusinessLog(title = "获取当月人员Team")
     public ResponseData getTeam() {
         return ResponseData.success(personAllocService.getTeam());
     }


     @ParamValidator
     @PostResource(name = "初始化当月人员分摊", path = "/initCurrent")
     @ApiOperation(value = "初始化当月人员分摊")
     @BusinessLog(title = "初始化当月人员分摊",opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData initCurrent() {
         List<PersonAlloc> current = personAllocService.getCurrent();
         return personAllocService.initCurrent(current);
     }


     @ParamValidator
     @PostResource(name = "当月人员确认", path = "/confirm")
     @ApiOperation(value = "当月人员确认")
     @BusinessLog(title = "当月人员确认",opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData confirm(@RequestBody List<PersonAlloc> personAllocList) {
         return personAllocService.confirm(personAllocList);
     }


     @ParamValidator
     @PostResource(name = "批量修改", path = "/updateBatch")
     @ApiOperation(value = "批量修改")
     @BusinessLog(title = "批量修改",opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData updateBatch(@RequestBody List<PersonAlloc> personAllocList) {
         return personAllocService.updateBatch(personAllocList);
     }



}