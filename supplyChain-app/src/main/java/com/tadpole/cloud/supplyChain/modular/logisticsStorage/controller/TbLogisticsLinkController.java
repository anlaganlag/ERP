package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsLink;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsLinkMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsLinkParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsLinkResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

 /**
 * 物流信息查询;(tb_logistics_link)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流信息查询")
@RestController
@ApiResource(name = "物流信息查询", path="/tbLogisticsLink")
public class TbLogisticsLinkController{
     public final String baseName = "物流信息查询";
     public final String queryByIdFunName = baseName+"--通过ID查询物流信息查询";
     public final String paginQueryFunName = baseName+"--分页查询物流信息查询";
     public final String addFunName = baseName+"--新增物流信息查询";
     public final String editFunName = baseName+"--更新物流信息查询";
     public final String exportFunName = baseName+"--按查询条件导出物流信息查询";
     public final String deleteByIdFunName = baseName+"--通过主键删除物流信息查询据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除物流信息查询";
    @Resource
    private TbLogisticsLinkService tbLogisticsLinkService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsLink.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(tbLogisticsLinkService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsLinkParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsLink.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsLinkParam tbLogisticsLinkParm ){
        //1.分页参数
       Page page = tbLogisticsLinkParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsLinkResult> pageResult = tbLogisticsLinkService.paginQuery(tbLogisticsLinkParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsLink 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsLink.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsLink tbLogisticsLink){
        return ResponseData.success(tbLogisticsLinkService.insert(tbLogisticsLink));
    }
    
    /** 
     * 更新数据
     *
     * @param linkParam 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsLink.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    @DataSource(name = "logistics")
    public ResponseData  edit(@RequestBody TbLogisticsLinkParam linkParam){
       return tbLogisticsLinkService.insertOrUpdate(linkParam);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal id){
         if (tbLogisticsLinkService.deleteById(id)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  idList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> idList){
      if (Objects.isNull(idList) || idList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsLinkService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsLinkParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsLinkResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsLinkParam tbLogisticsLinkParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsLinkResult> pageResult = tbLogisticsLinkService.paginQuery(tbLogisticsLinkParm, current,size);
       List<TbLogisticsLinkResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流信息查询.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsLinkResult.class).sheet("物流信息查询").doWrite(records);
        return ResponseData.success();
    }
}