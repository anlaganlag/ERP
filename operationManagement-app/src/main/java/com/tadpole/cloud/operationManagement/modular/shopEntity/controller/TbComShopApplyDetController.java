package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Objects;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyDetResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyDet;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyDetService;

 /**
 * 资源-店铺申请详情表;(Tb_Com_Shop_Apply_Det)--控制层
 * @author : LSY
 * @date : 2023-7-28
 */
@Slf4j
@Api(tags = "资源-店铺申请详情表接口")
@RestController
@ApiResource(name="资源-店铺申请详情表" , path="/tbComShopApplyDet")
public class TbComShopApplyDetController{
     public final String baseName = "资源-店铺申请详情表";
     public final String queryByIdFunName = baseName+"--通过ID查询单条数据";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增数据";
     public final String editFunName = baseName+"--更新数据";
     public final String deleteByIdFunName = baseName+"--通过主键删除数据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除数据";
     public final String exportFunName = baseName+"--按查询条件导出数据";
    @Resource
    private TbComShopApplyDetService tbComShopApplyDetService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response =TbComShopApplyDet.class)
    @GetResource(name = queryByIdFunName, path = "/queryById")
    @BusinessLog(title =queryByIdFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(BigDecimal sysApplyDetId){
        return ResponseData.success(tbComShopApplyDetService.queryById(sysApplyDetId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbComShopApplyDetParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response =TbComShopApplyDetResult.class)
    @PostResource(name = paginQueryFunName, path = "/list", requiredLogin = true, menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopApplyDetParam tbComShopApplyDetParam){
        //1.分页参数
        Page page = tbComShopApplyDetParam.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopApplyDetResult> pageResult = tbComShopApplyDetService.paginQuery(tbComShopApplyDetParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopApplyDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbComShopApplyDet.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbComShopApplyDet tbComShopApplyDet){
        return ResponseData.success(tbComShopApplyDetService.insert(tbComShopApplyDet));
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopApplyDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbComShopApplyDet.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShopApplyDet tbComShopApplyDet){
        return ResponseData.success(tbComShopApplyDetService.update(tbComShopApplyDet));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestParam  BigDecimal sysApplyDetId){
        return ResponseData.success(tbComShopApplyDetService.deleteById(sysApplyDetId));
    }
    
    /**
     * 批量删除数据
     *
     * @param  sysApplyDetIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysApplyDetIdList){
      if (Objects.isNull(sysApplyDetIdList) || sysApplyDetIdList.size()==0) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbComShopApplyDetService.deleteBatchIds(sysApplyDetIdList));
     }
    
    /**
     * 导出
     *
     * @param tbComShopApplyDetParam 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbComShopApplyDet.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopApplyDetParam tbComShopApplyDetParam, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbComShopApplyDetResult> pageResult = tbComShopApplyDetService.paginQuery(tbComShopApplyDetParam, current,size);
       List<TbComShopApplyDetResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺申请详情表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopApplyDetResult.class).sheet("资源-店铺申请详情表").doWrite(records);
        return ResponseData.success();
    }
    
    
    
    
}