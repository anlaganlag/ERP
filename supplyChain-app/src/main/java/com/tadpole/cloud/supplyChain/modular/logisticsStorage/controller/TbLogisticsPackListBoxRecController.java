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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListBoxRecService;
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
 * 出货清单和亚马逊货件关系映射表;(tb_logistics_pack_list_box_rec)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
    @Api(tags = "出货清单和亚马逊货件关系映射表")
@RestController
@ApiResource(name = "出货清单和亚马逊货件关系映射表", path="/tbLogisticsPackListBoxRec")
public class TbLogisticsPackListBoxRecController{
     public final String baseName = "出货清单和亚马逊货件关系映射表";
     public final String queryByIdFunName = baseName+"--通过ID查询出货清单和亚马逊货件关系映射表";
     public final String paginQueryFunName = baseName+"--分页查询出货清单和亚马逊货件关系映射表";
     public final String addFunName = baseName+"--新增出货清单和亚马逊货件关系映射表";
     public final String editFunName = baseName+"--更新出货清单和亚马逊货件关系映射表";
     public final String exportFunName = baseName+"--按查询条件导出出货清单和亚马逊货件关系映射表";
     public final String deleteByIdFunName = baseName+"--通过主键删除出货清单和亚马逊货件关系映射表据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除出货清单和亚马逊货件关系映射表";
    public final String amazRecState = baseName+"--亚马逊货件接收状态下拉List";
    @Resource
    private TbLogisticsPackListBoxRecService tbLogisticsPackListBoxRecService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackListBoxRec.class)
    @GetResource(name = queryByIdFunName, path = "/queryBysysId" )
    public ResponseData  queryById(BigDecimal sysId){
        return ResponseData.success(tbLogisticsPackListBoxRecService.queryById(sysId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackListBoxRecParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackListBoxRec.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackListBoxRecParam tbLogisticsPackListBoxRecParm ){
        //1.分页参数
       Page page = tbLogisticsPackListBoxRecParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListBoxRecResult> pageResult = tbLogisticsPackListBoxRecService.paginQuery(tbLogisticsPackListBoxRecParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRec 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackListBoxRec.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackListBoxRec tbLogisticsPackListBoxRec){
        return ResponseData.success(tbLogisticsPackListBoxRecService.insert(tbLogisticsPackListBoxRec));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListBoxRec 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackListBoxRec.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackListBoxRecParam tbLogisticsPackListBoxRec){
        TbLogisticsPackListBoxRec result = tbLogisticsPackListBoxRecService.update(tbLogisticsPackListBoxRec);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal sysId){
         if (tbLogisticsPackListBoxRecService.deleteById(sysId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  sysIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysIdList){
      if (Objects.isNull(sysIdList) || sysIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsPackListBoxRecService.deleteBatchIds(sysIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackListBoxRecParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackListBoxRecResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackListBoxRecParam tbLogisticsPackListBoxRecParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListBoxRecResult> pageResult = tbLogisticsPackListBoxRecService.paginQuery(tbLogisticsPackListBoxRecParm, current,size);
       List<TbLogisticsPackListBoxRecResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单和亚马逊货件关系映射表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackListBoxRecResult.class).sheet("出货清单和亚马逊货件关系映射表").doWrite(records);
        return ResponseData.success();
    }

    @GetResource(name = amazRecState, path = "/amazRecStateList" )
    @ApiOperation(value = amazRecState, response = TbLogisticsPackListResult.class)
    @BusinessLog(title = amazRecState,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData amazRecStateList() {
        return tbLogisticsPackListBoxRecService.amazRecStateList();
    }
}