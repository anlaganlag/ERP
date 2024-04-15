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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRecDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListBoxRecDetService;
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
 * 出货清单和亚马逊货件关系映射-明细;(tb_logistics_pack_list_box_rec_det)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "出货清单和亚马逊货件关系映射-明细")
@RestController
@ApiResource(name = "出货清单和亚马逊货件关系映射-明细", path="/tbLogisticsPackListBoxRecDet")
public class TbLogisticsPackListBoxRecDetController{
     public final String baseName = "出货清单和亚马逊货件关系映射-明细";
     public final String queryByIdFunName = baseName+"--通过ID查询出货清单和亚马逊货件关系映射-明细";
     public final String paginQueryFunName = baseName+"--分页查询出货清单和亚马逊货件关系映射-明细";
     public final String addFunName = baseName+"--新增出货清单和亚马逊货件关系映射-明细";
     public final String editFunName = baseName+"--更新出货清单和亚马逊货件关系映射-明细";
     public final String exportFunName = baseName+"--按查询条件导出出货清单和亚马逊货件关系映射-明细";
     public final String batchUpdate = baseName+"--批量更新发货数量";
     public final String deleteByIdFunName = baseName+"--通过主键删除出货清单和亚马逊货件关系映射-明细据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除出货清单和亚马逊货件关系映射-明细";
    @Resource
    private TbLogisticsPackListBoxRecDetService tbLogisticsPackListBoxRecDetService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackListBoxRecDet.class)
    @GetResource(name = queryByIdFunName, path = "/queryBysysDetId" )
    public ResponseData  queryById(BigDecimal sysDetId){
        return ResponseData.success(tbLogisticsPackListBoxRecDetService.queryById(sysDetId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackListBoxRecDetParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackListBoxRecDet.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackListBoxRecDetParam tbLogisticsPackListBoxRecDetParm ){
        //1.分页参数
       Page page = tbLogisticsPackListBoxRecDetParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListBoxRecDetResult> pageResult = tbLogisticsPackListBoxRecDetService.paginQuery(tbLogisticsPackListBoxRecDetParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRecDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackListBoxRecDet.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackListBoxRecDet tbLogisticsPackListBoxRecDet){
        return ResponseData.success(tbLogisticsPackListBoxRecDetService.insert(tbLogisticsPackListBoxRecDet));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListBoxRecDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackListBoxRecDet.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackListBoxRecDetParam tbLogisticsPackListBoxRecDet){
        TbLogisticsPackListBoxRecDet result = tbLogisticsPackListBoxRecDetService.update(tbLogisticsPackListBoxRecDet);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal sysDetId){
         if (tbLogisticsPackListBoxRecDetService.deleteById(sysDetId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  sysDetIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> sysDetIdList){
      if (Objects.isNull(sysDetIdList) || sysDetIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsPackListBoxRecDetService.deleteBatchIds(sysDetIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackListBoxRecDetParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackListBoxRecDetResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackListBoxRecDetParam tbLogisticsPackListBoxRecDetParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackListBoxRecDetResult> pageResult = tbLogisticsPackListBoxRecDetService.paginQuery(tbLogisticsPackListBoxRecDetParm, current,size);
       List<TbLogisticsPackListBoxRecDetResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单和亚马逊货件关系映射-明细.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackListBoxRecDetResult.class).sheet("出货清单和亚马逊货件关系映射-明细").doWrite(records);
        return ResponseData.success();
    }


     /**
      * 批量更新发货数量
      *
      * @param detParamList 实例对象 list
      * @return 实例对象
      */
     @ApiOperation(value =batchUpdate,response =TbLogisticsPackListBoxRecDet.class)
     @PostResource(name = batchUpdate, path = "/batchUpdate" )
     @BusinessLog(title = batchUpdate,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData  batchUpdate(@RequestBody List<TbLogisticsPackListBoxRecDetParam> detParamList ){
         return tbLogisticsPackListBoxRecDetService.batchUpdate(detParamList);
     }
}