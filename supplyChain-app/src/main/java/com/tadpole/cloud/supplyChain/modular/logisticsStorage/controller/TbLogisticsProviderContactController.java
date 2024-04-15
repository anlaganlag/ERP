package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProviderContact;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderContactParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderContactResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsProviderContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

 /**
 * 物流供应商联系信息;(tb_logistics_provider_contact)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流供应商联系信息")
@RestController
@ApiResource(name = "物流供应商联系信息", path="/tbLogisticsProviderContact")
public class TbLogisticsProviderContactController{
     public final String baseName = "物流供应商联系信息";
     public final String queryByIdFunName = baseName+"--通过ID查询物流供应商联系信息";
     public final String queryByLpCode = baseName+"--通过物流商编码lpCode查询联系人";
     public final String paginQueryFunName = baseName+"--分页查询物流供应商联系信息";
     public final String addFunName = baseName+"--新增物流供应商联系信息";
     public final String editFunName = baseName+"--更新物流供应商联系信息";
     public final String exportFunName = baseName+"--按查询条件导出物流供应商联系信息";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流供应商联系信息据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流供应商联系信息";
    @Resource
    private TbLogisticsProviderContactService tbLogisticsProviderContactService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpContactId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsProviderContact.class)
    @GetResource(name = queryByIdFunName, path = "/queryBylpContactId" )
    public ResponseData  queryById(BigDecimal lpContactId){
        return ResponseData.success(tbLogisticsProviderContactService.queryById(lpContactId));
    }

     /**
      * 通过物流商编码lp_code查询联系人
      *
      * @param lpCode 物流商编码
      * @return lIST实例对象
      */
     @ApiOperation(value =queryByLpCode,response= TbLogisticsProviderContact.class)
     @GetResource(name = queryByLpCode, path = "/queryByLpCode" )
     public ResponseData  queryByLpCode(String lpCode){
         return ResponseData.success(tbLogisticsProviderContactService.queryByLpCode(lpCode));
     }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsProviderContactParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsProviderContact.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsProviderContactParam tbLogisticsProviderContactParm ){
        //1.分页参数
       Page page = tbLogisticsProviderContactParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsProviderContactResult> pageResult = tbLogisticsProviderContactService.paginQuery(tbLogisticsProviderContactParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsProviderContact 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsProviderContact.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsProviderContact tbLogisticsProviderContact){
        return ResponseData.success(tbLogisticsProviderContactService.insert(tbLogisticsProviderContact));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsProviderContact 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsProviderContact.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsProviderContactParam tbLogisticsProviderContact){
        TbLogisticsProviderContact result = tbLogisticsProviderContactService.update(tbLogisticsProviderContact);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpContactId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal lpContactId){
         if (tbLogisticsProviderContactService.deleteById(lpContactId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  lpContactIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> lpContactIdList){
      if (Objects.isNull(lpContactIdList) || lpContactIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsProviderContactService.deleteBatchIds(lpContactIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsProviderContactParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsProviderContactResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsProviderContactParam tbLogisticsProviderContactParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsProviderContactResult> pageResult = tbLogisticsProviderContactService.paginQuery(tbLogisticsProviderContactParm, current,size);
       List<TbLogisticsProviderContactResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流供应商联系信息.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsProviderContactResult.class).sheet("物流供应商联系信息").doWrite(records);
        return ResponseData.success();
    }
}