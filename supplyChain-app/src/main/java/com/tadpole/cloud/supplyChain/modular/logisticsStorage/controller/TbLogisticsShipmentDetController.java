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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentDetService;
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
 * 亚马逊发货申请记录-明细项;(tb_logistics_shipment_det)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊发货申请记录-明细项")
@RestController
@ApiResource(name = "亚马逊发货申请记录-明细项", path="/tbLogisticsShipmentDet")
public class TbLogisticsShipmentDetController{
     public final String baseName = "亚马逊发货申请记录-明细项";
     public final String queryByIdFunName = baseName+"--通过ID查询亚马逊发货申请记录-明细项";
     public final String queryByPackCode = baseName+"--根据出货清单号查询发货计划明细项List";
     public final String paginQueryFunName = baseName+"--分页查询亚马逊发货申请记录-明细项";
     public final String addFunName = baseName+"--新增亚马逊发货申请记录-明细项";
     public final String editFunName = baseName+"--更新亚马逊发货申请记录-明细项";
     public final String exportFunName = baseName+"--按查询条件导出亚马逊发货申请记录-明细项";
     public final String deleteByIdFunName = baseName+"--通过主键删除亚马逊发货申请记录-明细项据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除亚马逊发货申请记录-明细项";
    @Resource
    private TbLogisticsShipmentDetService tbLogisticsShipmentDetService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shipDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsShipmentDet.class)
    @GetResource(name = queryByIdFunName, path = "/queryByshipDetId" )
    public ResponseData  queryById(BigDecimal shipDetId){
        return ResponseData.success(tbLogisticsShipmentDetService.queryById(shipDetId));
    }

     /**
      * 根据出货清单号查询发货计划明细项List
      *
      * @param packCode 筛选条件
      * @return 查询结果
      */
     @ApiOperation(value =queryByPackCode ,response=TbLogisticsShipmentDet.class)
     @GetResource(name = queryByPackCode, path = "/getLogisticsShipmentPlanDetail", menuFlag = true)
     @BusinessLog(title = queryByPackCode,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData queryByPackCode( String packCode ){
         return ResponseData.success(tbLogisticsShipmentDetService.queryByPackCode(packCode));
     }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsShipmentDetParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsShipmentDet.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsShipmentDetParam tbLogisticsShipmentDetParm ){
        //1.分页参数
       Page page = tbLogisticsShipmentDetParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipmentDetResult> pageResult = tbLogisticsShipmentDetService.paginQuery(tbLogisticsShipmentDetParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsShipmentDet.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsShipmentDet tbLogisticsShipmentDet){
        return ResponseData.success(tbLogisticsShipmentDetService.insert(tbLogisticsShipmentDet));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipmentDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsShipmentDet.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsShipmentDetParam tbLogisticsShipmentDet){
        TbLogisticsShipmentDet result = tbLogisticsShipmentDetService.update(tbLogisticsShipmentDet);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param shipDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal shipDetId){
         if (tbLogisticsShipmentDetService.deleteById(shipDetId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  shipDetIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> shipDetIdList){
      if (Objects.isNull(shipDetIdList) || shipDetIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsShipmentDetService.deleteBatchIds(shipDetIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsShipmentDetParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsShipmentDetResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsShipmentDetParam tbLogisticsShipmentDetParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipmentDetResult> pageResult = tbLogisticsShipmentDetService.paginQuery(tbLogisticsShipmentDetParm, current,size);
       List<TbLogisticsShipmentDetResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊发货申请记录-明细项.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsShipmentDetResult.class).sheet("亚马逊发货申请记录-明细项").doWrite(records);
        return ResponseData.success();
    }
}