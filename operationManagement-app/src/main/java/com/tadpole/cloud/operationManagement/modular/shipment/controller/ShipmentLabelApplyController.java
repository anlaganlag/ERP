package com.tadpole.cloud.operationManagement.modular.shipment.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentLabelApply;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentLabelApplyParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentLabelApplyResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ShipmentLabelApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 发货标签申请;(SHIPMENT_LABEL_APPLY)表控制层
 * @author : LSY
 * @date : 2024-3-21
 */
@Api(tags = "发货标签申请")
@RestController
@ApiResource(name = "发货标签申请", path="/shipmentLabelApply")
public class ShipmentLabelApplyController{
     public final String baseName = "发货标签申请";
     public final String queryByIdFunName = baseName+"--通过ID查询发货标签申请";
     public final String paginQueryFunName = baseName+"--分页查询发货标签申请";
     public final String addFunName = baseName+"--新增发货标签申请";
     public final String addBatchFunName = baseName+"--批量新增保存数据";
     public final String editFunName = baseName+"--更新发货标签申请";
     public final String exportFunName = baseName+"--按查询条件导出发货标签申请";
     public final String deleteByIdFunName = baseName+"--通过主键删除发货标签申请据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除发货标签申请";
     public final String syncLabelToK3 = baseName+"--同步发货标签到金蝶K3（多条）保存提交 + 提交";
     public final String importCreateSku = baseName+"--导入创建发货标签申请";
     public final String shipmentLabelApplyTemplate = baseName+"--发货标签-导入模板下载";
    @Resource
    private ShipmentLabelApplyService shipmentLabelApplyService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= ShipmentLabelApply.class)
    @GetResource(name = queryByIdFunName, path = "/queryByundefinedId")
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(shipmentLabelApplyService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param shipmentLabelApplyParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=ShipmentLabelApply.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody ShipmentLabelApplyParam shipmentLabelApplyParm ){
        //1.分页参数
       Page page = shipmentLabelApplyParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<ShipmentLabelApplyResult> pageResult = shipmentLabelApplyService.paginQuery(shipmentLabelApplyParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param shipmentLabelApply 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =ShipmentLabelApply.class)
    @PostResource(name = addFunName, path = "/add")
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody ShipmentLabelApply shipmentLabelApply){
        return ResponseData.success(shipmentLabelApplyService.insert(shipmentLabelApply));
    }

     /**
      * 批量新增保存数据
      *
      * @param shipmentLabelApplyList 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =addBatchFunName,response =ShipmentLabelApply.class)
     @PostResource(name = addBatchFunName, path = "/addBatch",  requiredLogin = true)
     @BusinessLog(title = addBatchFunName,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData  addBatch(@RequestBody List<ShipmentLabelApply> shipmentLabelApplyList){

         return ResponseData.success(shipmentLabelApplyService.addBatch(shipmentLabelApplyList));
     }


    
    /** 
     * 更新数据
     *
     * @param shipmentLabelApply 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =ShipmentLabelApply.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody ShipmentLabelApplyParam shipmentLabelApply){
        ShipmentLabelApply result = shipmentLabelApplyService.update(shipmentLabelApply);
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
    @GetResource(name = deleteByIdFunName, path = "/deleteById")
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(BigDecimal id){
         if (shipmentLabelApplyService.deleteById(id)) {
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
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds")
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<BigDecimal> idList){
      if (Objects.isNull(idList) || idList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(shipmentLabelApplyService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param shipmentLabelApplyParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export")
    @ApiOperation(value = exportFunName, response =ShipmentLabelApplyResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody ShipmentLabelApplyParam shipmentLabelApplyParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<ShipmentLabelApplyResult> pageResult = shipmentLabelApplyService.paginQuery(shipmentLabelApplyParm, current,size);
       List<ShipmentLabelApplyResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("发货标签申请.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ShipmentLabelApplyResult.class).sheet("发货标签申请").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 同步发货标签到金蝶K3
      *
      * @param shipmentLabelApplyList 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =syncLabelToK3,response =ShipmentLabelApply.class)
     @PostResource(name = syncLabelToK3, path = "/syncLabelToK3",  requiredLogin = true)
     @BusinessLog(title = syncLabelToK3,opType = LogAnnotionOpTypeEnum.ADD)
     public ResponseData  syncLabelToK3(@RequestBody List<ShipmentLabelApply> shipmentLabelApplyList){
         return shipmentLabelApplyService.syncLabelToK3(shipmentLabelApplyList);
     }

    /**
     * 导入创建发货标签申请
     *
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = importCreateSku, path = "/importCreateSku", requiredPermission = false)
    @ApiOperation(value = importCreateSku, response = ShipmentLabelApply.class)
    @BusinessLog(title = importCreateSku ,opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importCreateSku(@RequestParam("file") MultipartFile file) throws IOException {
        return shipmentLabelApplyService.importCreateSku(file);
    }

    /**
     * 导入模板下载
     * @param response
     */


    @PostResource(name = shipmentLabelApplyTemplate, path = "/shipmentLabelApplyTemplate", requiredPermission = false)
    @ApiOperation(value = shipmentLabelApplyTemplate, response = ShipmentLabelApply.class)
    @BusinessLog(title = shipmentLabelApplyTemplate ,opType = LogAnnotionOpTypeEnum.EXPORT)
    public void shipmentLabelApplyTemplate(HttpServletResponse response) {
        String path = "/template/shipmentLabelApply.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }
}