package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipToRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipToRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipToRecResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipToRecService;
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
 * 平台货件接收地址;(tb_logistics_ship_to_rec)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "平台货件接收地址")
@RestController
@ApiResource(name = "平台货件接收地址", path="/tbLogisticsShipToRec")
public class TbLogisticsShipToRecController{
     public final String baseName = "平台货件接收地址";
     public final String queryByIdFunName = baseName+"--通过ID查询平台货件接收地址";
     public final String paginQueryFunName = baseName+"--分页查询平台货件接收地址";
     public final String addFunName = baseName+"--新增平台货件接收地址";
     public final String batchAddFunName = baseName+"--批量新增平台货件接收地址";
     public final String editFunName = baseName+"--更新平台货件接收地址";
     public final String exportFunName = baseName+"--按查询条件导出平台货件接收地址";
     public final String deleteByIdFunName = baseName+"--通过主键删除平台货件接收地址据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除平台货件接收地址";
     public final String logRecHouseNameList = baseName+"--收货仓名称List 下拉框";
    @Resource
    private TbLogisticsShipToRecService tbLogisticsShipToRecService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lfrId 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsShipToRec.class)
    @GetResource(name = queryByIdFunName, path = "/queryBylfrId" )
    public ResponseData  queryById(BigDecimal lfrId){
        return ResponseData.success(tbLogisticsShipToRecService.queryById(lfrId));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsShipToRecParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsShipToRec.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsShipToRecParam tbLogisticsShipToRecParm ){
        //1.分页参数
       Page page = tbLogisticsShipToRecParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipToRecResult> pageResult = tbLogisticsShipToRecService.paginQuery(tbLogisticsShipToRecParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipToRec 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsShipToRec.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsShipToRec tbLogisticsShipToRec){
        return ResponseData.success(tbLogisticsShipToRecService.insert(tbLogisticsShipToRec));
    }

     /**
      * 批量新增数据
      *
      * @param tbLogisticsShipToRecList 实例对象List
      * @return 实例对象
      */
     @ApiOperation(value =batchAddFunName,response =TbLogisticsShipToRec.class)
     @PostResource(name = batchAddFunName, path = "/batchAdd" )
     @BusinessLog(title = batchAddFunName,opType = LogAnnotionOpTypeEnum.ADD)
     @DataSource(name = "logistics")
     public ResponseData batchAdd(@RequestBody List<TbLogisticsShipToRec> tbLogisticsShipToRecList){
         return tbLogisticsShipToRecService.batchAdd(tbLogisticsShipToRecList);
     }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipToRec 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsShipToRec.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsShipToRecParam tbLogisticsShipToRec){
        TbLogisticsShipToRec result = tbLogisticsShipToRecService.update(tbLogisticsShipToRec);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lfrId 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal lfrId){
         if (tbLogisticsShipToRecService.deleteById(lfrId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  lfrIdList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> lfrIdList){
      if (Objects.isNull(lfrIdList) || lfrIdList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsShipToRecService.deleteBatchIds(lfrIdList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsShipToRecParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsShipToRecResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsShipToRecParam tbLogisticsShipToRecParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipToRecResult> pageResult = tbLogisticsShipToRecService.paginQuery(tbLogisticsShipToRecParm, current,size);
       List<TbLogisticsShipToRecResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("地址簿.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsShipToRecResult.class).sheet("地址簿").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 地址簿导入模板下载
      * @param response
      */
     @GetResource(name = "地址簿导入模板下载", path = "/downloadTemplate", requiredPermission = false )
     @ApiOperation("地址簿导入模板下载")
     public void downloadTemplate(HttpServletResponse response) {
         new ExcelUtils().downloadExcel(response, "/template/地址簿导入模板.xlsx");
     }

     /**
      * 公共下拉--收货仓名称
      */
     @GetResource(name = logRecHouseNameList, path = "/logRecHouseNameList", requiredPermission = false )
     @ApiOperation(value = logRecHouseNameList, response =TbLogisticsShipToRecResult.class)
     public ResponseData logRecHouseNameList() {
        return ResponseData.success(tbLogisticsShipToRecService.logRecHouseNameList()) ;
     }
}