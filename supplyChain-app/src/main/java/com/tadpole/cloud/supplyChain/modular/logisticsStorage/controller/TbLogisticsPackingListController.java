package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceCalcDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceCalcParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListK3Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

 /**
 * 出货清单信息-金蝶+海外仓;(tb_logistics_packing_list)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "出货清单信息-金蝶+海外仓")
@RestController
@ApiResource(name = "出货清单信息-金蝶+海外仓", path="/tbLogisticsPackingList")
public class TbLogisticsPackingListController{
     public final String baseName = "出货清单信息-金蝶+海外仓";
     public final String queryByIdFunName = baseName+"--通过ID查询出货清单信息-金蝶+海外仓";
     public final String getLogisticsShipmentListDetail = baseName+"--通过packCode查询出货清单明细List";
     public final String getClearanceData = baseName+"--通过packCode获取通关数据";
     public final String paginQueryFunName = baseName+"--分页查询出货清单信息-金蝶+海外仓";
     public final String addFunName = baseName+"--新增出货清单信息-金蝶+海外仓";
     public final String editFunName = baseName+"--更新出货清单信息-金蝶+海外仓";
     public final String exportFunName = baseName+"--按查询条件导出出货清单信息-金蝶+海外仓";
     public final String deleteByIdFunName = baseName+"--通过主键删除出货清单信息-金蝶+海外仓据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除出货清单信息-金蝶+海外仓";
     public final String transformWarehouse = baseName+"--转仓操作";
     public final String fbaBackhaulErp = baseName+"--FBA回传至ERP";
     public final String returnWarehouse = baseName+"--返仓";
     public final String delShipmentList = baseName+"--作废出货清单";
     public final String updateShipmentStatus = baseName+"--回传FBA后更新出货清单状态";
     public final String lpsr = "物流商发货申请提醒";
     public final String lpsrDet = "物流商发货申请提醒--明细项";
     public final String createLogisticsOrder = "物流商发货申请提醒--申请发货--保存并同步通过数据";
     public final String getLogisticsShipmentList = "获取packList能关联出的出货清单";
     public final String receiveK3PushPackingList = "接收金蝶推送的出货清单信息";
     public final String clearanceCalc = "清关税费测算";
    @Resource
    private TbLogisticsPackingListService tbLogisticsPackingListService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packCode 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response= TbLogisticsPackingList.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypackCode" )
    public ResponseData  queryById(String packCode){
        return ResponseData.success(tbLogisticsPackingListService.queryById(packCode));
    }

     /**
      * 通过packCode查询出货清单明细List
      *
      * @param packCode 主键
      * @return 实例对象
      */
     @ApiOperation(value =getLogisticsShipmentListDetail,response= TbLogisticsPackingListResultDet1AndDet2.class)
     @GetResource(name = getLogisticsShipmentListDetail, path = "/getLogisticsShipmentListDetail" )
     public ResponseData  getLogisticsShipmentListDetail(String packCode){
         return ResponseData.success(tbLogisticsPackingListService.getLogisticsShipmentListDetail(packCode));
     }

     /**
      * 通过packCode获取通关数据
      *
      * @param packCode 主键
      * @return 实例对象
      */
     @ApiOperation(value =getClearanceData,response= TbLogisticsPackingList.class)
     @GetResource(name = getClearanceData, path = "/getClearanceData" )
     public ResponseData  getClearanceData(String packCode,HttpServletResponse response) throws Exception {
         List<TbClearancModel> tbClearanceModelList = tbLogisticsPackingListService.getClearanceData(packCode);
         TbLogisticsPackingListResultDet1AndDet2 det1AndDet2s = tbLogisticsPackingListService.getLogisticsShipmentListDetail(packCode);
         List<TbLogisticsPackingListDet1> det1s = det1AndDet2s.getTbLogisticsPackingListDet1s();
         List<TbLogisticsPackingListClearanceDte1> listClearanceDte1s = BeanUtil.copyToList(det1s, TbLogisticsPackingListClearanceDte1.class);

         if (ObjectUtil.isNotEmpty(tbClearanceModelList) && ObjectUtil.isNotNull(listClearanceDte1s)) {
             String fileName = packCode + "_通过数据.xlsx";
             response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO8859-1"));

             ExcelWriter excelWriter = EasyExcel
                     .write(response.getOutputStream())
                     .build();
             WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "通关信息").head(TbClearancModel.class).build();
             WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "重量信息").head(TbLogisticsPackingListClearanceDte1.class).build();

             excelWriter.write(tbClearanceModelList, writeSheet1);
             excelWriter.write(listClearanceDte1s, writeSheet2);
             excelWriter.finish();

//             EasyExcel.write(response.getOutputStream(), TbClearancModel.class).sheet(0,"通关信息").doWrite(tbClearancModelList);
//             EasyExcel.write(0,response.getOutputStream(), TbLogisticsPackingListClearanceDte1.class).sheet(1,"重量信息").doWrite(listClearanceDte1s);
             return ResponseData.success();
         }
         return ResponseData.success(tbLogisticsPackingListService.getClearanceData(packCode));
     }


    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackingListParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackingList.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackingListParam tbLogisticsPackingListParam ){
        //1.分页参数
       Page page = tbLogisticsPackingListParam.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListResult> pageResult = tbLogisticsPackingListService.pageQuery(tbLogisticsPackingListParam, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackingList.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackingList tbLogisticsPackingList){
        return ResponseData.success(tbLogisticsPackingListService.insert(tbLogisticsPackingList));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackingList.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackingListParam tbLogisticsPackingList){
        TbLogisticsPackingList result = tbLogisticsPackingListService.update(tbLogisticsPackingList);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param packCode 主键
     * @return 是否成功
     */
    @ApiOperation(value =deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String packCode){
         if (tbLogisticsPackingListService.deleteById(packCode)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }
     /**
     * 批量删除数据
     *
     * @param  packCodeList 主键List集合
     * @return 是否成功
     */
     @ApiOperation(value =deleteBatchIdsFunName)
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> packCodeList){
      if (Objects.isNull(packCodeList) || packCodeList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(tbLogisticsPackingListService.deleteBatchIds(packCodeList));
     }
     /**
     * 导出
     *
     * @param tbLogisticsPackingListParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response =TbLogisticsPackingListResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsPackingListParam tbLogisticsPackingListParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsPackingListResult> pageResult = tbLogisticsPackingListService.pageQuery(tbLogisticsPackingListParm, current,size);
       List<TbLogisticsPackingListResult> records=  pageResult.getRecords();
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单信息-金蝶+海外仓.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsPackingListResult.class).sheet("出货清单信息-金蝶+海外仓").doWrite(records);
        return ResponseData.success();
    }

     /**
      * 转仓操作
      *
      * @param param 筛选条件
      * @return ResponseData
      */
     @PostResource(name = transformWarehouse, path = "/transformWarehouse" )
     @ApiOperation(value = transformWarehouse, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = transformWarehouse,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData transformWarehouse(@RequestBody TbLogisticsPackingListParam param) {
         //1.分页参数

         return tbLogisticsPackingListService.transformWarehouse(param);
     }

     /**
      * FBA回传至ERP
      *
      * @param packCode 出货清单号
      * @param shipmentID fba号
      * @return ResponseData
      */
     @GetResource(name = fbaBackhaulErp, path = "/fbaBackhaulErp" )
     @ApiOperation(value = fbaBackhaulErp, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = fbaBackhaulErp,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData fbaBackhaulErp(String packCode, String shipmentID) {
         return tbLogisticsPackingListService.fbaBackhaulErp(packCode,shipmentID);
     }

     /**
      * 返仓
      *
      * @param packCode 出货清单号
      * @param packStoreHouseType 仓库类型
      * @return ResponseData
      */
     @GetResource(name = returnWarehouse, path = "/returnWarehouse" )
     @ApiOperation(value = returnWarehouse, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = returnWarehouse,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData returnWarehouse(String packCode, String packStoreHouseType) {
         return tbLogisticsPackingListService.returnWarehouse(packCode,packStoreHouseType);
     }

     /**
      * 作废出货清单
      *
      * @param packCode 出货清单号
      * @return ResponseData
      */
     @GetResource(name = delShipmentList, path = "/delShipmentList" )
     @ApiOperation(value = delShipmentList, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = delShipmentList,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData delShipmentList(String packCode) {
         return tbLogisticsPackingListService.delShipmentList(packCode);
     }

     /**
      * 回传FBA后更新出货清单状态
      *
      * @param packCode 出货清单号
      * @param comWarehouseType
      * @param packUploadState
      * @param shipmentID
      * @param shipTo
      * @return ResponseData
      */
     @GetResource(name = updateShipmentStatus, path = "/updateShipmentStatus" )
     @ApiOperation(value = updateShipmentStatus, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = updateShipmentStatus,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData updateShipmentStatus(String packCode, String comWarehouseType, String packUploadState, String shipmentID, String shipTo) {
       boolean result= tbLogisticsPackingListService.updateShipmentStatus(packCode, packUploadState, shipmentID, shipTo);
         if (result) {
             if ("亚马逊仓".equals(comWarehouseType) || ("海外仓".equals(comWarehouseType) && packCode.contains("ECHQD"))) {
                 ResponseData rd = tbLogisticsPackingListService.generateDirectOrder(packCode);
                 if (!rd.getSuccess()) {
                     return rd;
                 }
             }
             return ResponseData.success("出货清单状态更新成功");
         } else {
             return ResponseData.error("出货清单状态更新失败");
         }
     }

     /**
      * 物流商发货申请提醒
      *LPSR: Logistics Provider Shipment Request Reminder
      * @param packStoreHouseType
      * @param packStoreHouseName
      * @return 查询结果
      */
     @ApiOperation(value =lpsr ,response= LogisticsReceiveReportViewModel.class)
     @GetResource(name = lpsr, path = "/lpsr", menuFlag = true)
     @BusinessLog(title = lpsr,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData lpsr(@RequestParam(required = false) String packStoreHouseType, @RequestParam(required = false) String packStoreHouseName) {
         return tbLogisticsPackingListService.lpsr(packStoreHouseType, packStoreHouseName);
     }

     /**
      * 物流商发货申请提醒明细项
      *
      * @param packStoreHouseType
      * @param packStoreHouseName
      * @return 查询结果
      */
     @ApiOperation(value =lpsrDet ,response=LogisticsReceiveReportViewModel.class)
     @GetResource(name = lpsrDet, path = "/lpsrDet", menuFlag = false)
     @BusinessLog(title = lpsrDet,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData lpsrDet(@RequestParam(required = false) String packStoreHouseType, @RequestParam(required = false) String packStoreHouseName) {
         return ResponseData.success(tbLogisticsPackingListService.lpsrDet(packStoreHouseType, packStoreHouseName));
     }

     /**
      * 物流商发货申请提醒--申请发货--保存并同步通过数据
      *
      * @param param 筛选条件
      * @return ResponseData
      */
     @PostResource(name = createLogisticsOrder, path = "/createLogisticsOrder" )
     @ApiOperation(value = createLogisticsOrder, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = createLogisticsOrder,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData createLogisticsOrder(@RequestBody TbLogisticsListToHeadRouteParam param) {
         if (param == null)
         {
             return ResponseData.error( "参数不合法不能为null");
         }
         if (ObjectUtil.isEmpty(param.getCountryCode()))
         {
             return ResponseData.error( "站点不能为空");
         }
         if (ObjectUtil.isEmpty(param.getElePlatformName()))
         {
             return ResponseData.error( "电商平台不能为空");
         }
         if (ObjectUtil.isEmpty(param.getShopNameSimple()))
         {
             return ResponseData.error( "账号不能为空");
         }
         return tbLogisticsPackingListService.createLogisticsOrder(param);
     }


     /**
      * 获取packList能关联出的出货清单
      *
      * @param shopNameSimple
      * @param countryCode
      * @param comWarehouseName
      * @return 查询结果
      */
     @ApiOperation(value =getLogisticsShipmentList ,response=TbLogisticsPackingList.class)
     @GetResource(name = getLogisticsShipmentList, path = "/getLogisticsShipmentList", menuFlag = false)
     @BusinessLog(title = getLogisticsShipmentList,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData getLogisticsShipmentList(String shopNameSimple, String countryCode, String comWarehouseName) {
         return ResponseData.success(tbLogisticsPackingListService.getLogisticsShipmentList(shopNameSimple, countryCode,comWarehouseName));
     }



     /**
      * 接收金蝶推送的出货清单信息
      *
      * @param request 筛选条件
      * @return ResponseData
      */
     @PostResource(name = receiveK3PushPackingList, path = "/receiveK3PushPackingList",requiredLogin = true )
     @ApiOperation(value = receiveK3PushPackingList, response =TbLogisticsPackingListResult.class)
     @BusinessLog(title = receiveK3PushPackingList,opType = LogAnnotionOpTypeEnum.ADD)
     public HashMap<String, Object> receiveK3PushPackingList(@RequestBody TbLogisticsPackingListK3Param request) throws Exception {

         ResponseData responseData = tbLogisticsPackingListService.receiveK3PushPackingList(request);

         HashMap<String, Object> resultMap = new HashMap<>();
         if (responseData.getSuccess()) {
             resultMap.put("code", 0);
             resultMap.put("msg", "出货清单状态更新成功");
             resultMap.put("data", responseData.getData());
         } else {
             resultMap.put("code", -1);
             resultMap.put("msg", responseData.getMessage());
             resultMap.put("data", responseData.getData());
         }
         return resultMap;
     }


     /**
      * 清关税费测算
      *
      * @param param 筛选条件
      * @return ResponseData
      */
     @PostResource(name = clearanceCalc, path = "/clearanceCalc" )
     @ApiOperation(value = clearanceCalc, response = TgCustomsClearanceCalcDetailParam.class)
     @BusinessLog(title = clearanceCalc,opType = LogAnnotionOpTypeEnum.QUERY)
     public ResponseData clearanceCalc(@RequestBody TgCustomsClearanceCalcParam param) throws Exception {

         return tbLogisticsPackingListService.initClearanceCalc(param);
     }
}