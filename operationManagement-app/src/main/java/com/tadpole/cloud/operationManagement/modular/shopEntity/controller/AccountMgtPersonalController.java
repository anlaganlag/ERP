package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

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
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountMgtPersonalResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountMgtPersonalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

 /**
 * 个人账户管理;(ACCOUNT_MGT_PERSONAL)表控制层
 * @author : LSY
 * @date : 2023-11-10
 */
@Api(tags = "个人账户管理")
@RestController
@ApiResource(name = "个人账户管理", path="/accountMgtPersonal")
public class AccountMgtPersonalController{
     public final String baseName = "个人账户管理";
     public final String queryByIdFunName = baseName+"--通过ID查询个人账户管理";
     public final String paginQueryFunName = baseName+"--分页查询个人账户管理";
     public final String addFunName = baseName+"--新增个人账户管理";
     public final String editFunName = baseName+"--更新个人账户管理";
     public final String exportFunName = baseName+"--按查询条件导出个人账户管理";
     public final String deleteByIdFunName = baseName+"--通过主键删除个人账户管理";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除个人账户管理";
    @Resource
    private AccountMgtPersonalService accountMgtPersonalService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=AccountMgtPersonal.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(String id){
        return ResponseData.success(accountMgtPersonalService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param accountMgtPersonalParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=AccountMgtPersonal.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody AccountMgtPersonalParam accountMgtPersonalParm ){
        //1.分页参数
       Page page = accountMgtPersonalParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AccountMgtPersonalResult> pageResult = accountMgtPersonalService.paginQuery(accountMgtPersonalParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param accountMgtPersonal 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =AccountMgtPersonal.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody AccountMgtPersonal accountMgtPersonal){
        return ResponseData.success(accountMgtPersonalService.insert(accountMgtPersonal));
    }
    
    /** 
     * 更新数据
     *
     * @param accountMgtPersonal 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =AccountMgtPersonal.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody AccountMgtPersonalParam accountMgtPersonal){
        AccountMgtPersonal result = accountMgtPersonalService.update(accountMgtPersonal);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    

     /**
     * 导出
     *
     * @param accountMgtPersonalParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =AccountMgtPersonalResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody AccountMgtPersonalParam accountMgtPersonalParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AccountMgtPersonalResult> pageResult = accountMgtPersonalService.paginQuery(accountMgtPersonalParm, current,size);
       List<AccountMgtPersonalResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("个人账户管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AccountMgtPersonalResult.class).sheet("个人账户管理").doWrite(records);
        return ResponseData.success();
    }
}