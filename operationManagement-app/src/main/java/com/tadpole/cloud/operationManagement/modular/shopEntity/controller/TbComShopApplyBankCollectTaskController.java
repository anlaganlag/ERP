package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyBankCollectTask;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyBankCollectTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyBankCollectTaskResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyBankCollectTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 资源-店铺申请银行收款任务;(Tb_Com_Shop_Apply_Bank_Collect_Task)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-店铺申请银行收款任务接口")
@RestController
@ApiResource(name="资源-店铺申请银行收款任务" , path="/tbComShopApplyBankCollectTask")
public class TbComShopApplyBankCollectTaskController{
     public final String baseName = "资源-店铺申请银行收款任务";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopApplyBankCollectTaskService tbComShopApplyBankCollectTaskService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetBctId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopApplyBankCollectTask.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysApplyDetBctId){
        return ResponseData.success(tbComShopApplyBankCollectTaskService.queryById(sysApplyDetBctId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComShopApplyBankCollectTaskParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopApplyBankCollectTaskResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopApplyBankCollectTaskParam tbComShopApplyBankCollectTaskParam){
        //1.分页参数
        Page page = tbComShopApplyBankCollectTaskParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopApplyBankCollectTaskResult> pageResult = tbComShopApplyBankCollectTaskService.paginQuery(tbComShopApplyBankCollectTaskParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApplyBankCollectTask 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopApplyBankCollectTask.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopApplyBankCollectTask tbComShopApplyBankCollectTask){
        return ResponseData.success(tbComShopApplyBankCollectTaskService.insert(tbComShopApplyBankCollectTask));
    }
    
    /** 
     * 更新数据
     *
     * @param bankCollectTaskParam 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopApplyBankCollectTask.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopApplyBankCollectTaskParam bankCollectTaskParam){
        return ResponseData.success(tbComShopApplyBankCollectTaskService.update(bankCollectTaskParam));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetBctId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal sysApplyDetBctId){
        return ResponseData.success(tbComShopApplyBankCollectTaskService.deleteById(sysApplyDetBctId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  sysApplyDetBctIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysApplyDetBctIdList){
      if (Objects.isNull(sysApplyDetBctIdList) || sysApplyDetBctIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopApplyBankCollectTaskService.deleteBatchIds(sysApplyDetBctIdList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopApplyBankCollectTaskParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopApplyBankCollectTask.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopApplyBankCollectTaskParam tbComShopApplyBankCollectTaskParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopApplyBankCollectTaskResult> pageResult = tbComShopApplyBankCollectTaskService.paginQuery(tbComShopApplyBankCollectTaskParam, current,size);
       List<TbComShopApplyBankCollectTaskResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺申请银行收款任务.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopApplyBankCollectTaskResult.class).sheet("资源-店铺申请银行收款任务").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}