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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDetToBox;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListDetToBoxParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetToBoxResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListDetToBoxService;
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
 * 亚马逊货件-明细-多少箱每箱装多少;(tb_logistics_pack_list_det_to_box)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊货件-明细-多少箱每箱装多少")
@RestController
@ApiResource(name = "亚马逊货件-明细-多少箱每箱装多少", path="/tbLogisticsPackListDetToBox")
public class TbLogisticsPackListDetToBoxController{
     public final String baseName = "亚马逊货件-明细-多少箱每箱装多少";
     public final String queryByIdFunName = baseName+"--通过ID查询亚马逊货件-明细-多少箱每箱装多少";
     public final String paginQueryFunName = baseName+"--分页查询亚马逊货件-明细-多少箱每箱装多少";
     public final String addFunName = baseName+"--新增亚马逊货件-明细-多少箱每箱装多少";
     public final String editFunName = baseName+"--更新亚马逊货件-明细-多少箱每箱装多少";
     public final String exportFunName = baseName+"--按查询条件导出亚马逊货件-明细-多少箱每箱装多少";
     public final String deleteByIdFunName = baseName+"--通过主键删除亚马逊货件-明细-多少箱每箱装多少据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除亚马逊货件-明细-多少箱每箱装多少";
    @Resource
    private TbLogisticsPackListDetToBoxService tbLogisticsPackListDetToBoxService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackListDetToBox.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(tbLogisticsPackListDetToBoxService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackListDetToBoxParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackListDetToBox.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackListDetToBoxParam tbLogisticsPackListDetToBoxParm ){
        //1.分页参数
       Page page = tbLogisticsPackListDetToBoxParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListDetToBoxResult> pageResult = tbLogisticsPackListDetToBoxService.paginQuery(tbLogisticsPackListDetToBoxParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListDetToBox 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackListDetToBox.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackListDetToBox tbLogisticsPackListDetToBox){
        return ResponseData.success(tbLogisticsPackListDetToBoxService.insert(tbLogisticsPackListDetToBox));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListDetToBox 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackListDetToBox.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackListDetToBoxParam tbLogisticsPackListDetToBox){
        TbLogisticsPackListDetToBox result = tbLogisticsPackListDetToBoxService.update(tbLogisticsPackListDetToBox);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
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
         if (tbLogisticsPackListDetToBoxService.deleteById(id)) {
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
       return ResponseData.success(tbLogisticsPackListDetToBoxService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackListDetToBoxParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackListDetToBoxResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackListDetToBoxParam tbLogisticsPackListDetToBoxParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListDetToBoxResult> pageResult = tbLogisticsPackListDetToBoxService.paginQuery(tbLogisticsPackListDetToBoxParm, current,size);
       List<TbLogisticsPackListDetToBoxResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊货件-明细-多少箱每箱装多少.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackListDetToBoxResult.class).sheet("亚马逊货件-明细-多少箱每箱装多少").doWrite(records);
        return ResponseData.success();
    }
}