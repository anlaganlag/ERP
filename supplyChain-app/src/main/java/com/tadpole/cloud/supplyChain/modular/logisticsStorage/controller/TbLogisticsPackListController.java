package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CloseLogisticsInGoodsParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CreateBindPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsInGoodsModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResultModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * 亚马逊货件;(tb_logistics_pack_list)表控制层
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊货件")
@RestController
@ApiResource(name = "亚马逊货件", path="/tbLogisticsPackList")
@Slf4j
public class TbLogisticsPackListController{
     public final String baseName = "亚马逊货件";
     public final String queryByIdFunName = baseName+"--通过ID查询亚马逊货件";
     public final String paginQueryFunName = baseName+"--分页查询亚马逊货件";
     public final String addFunName = baseName+"--新增亚马逊货件";
     public final String editFunName = baseName+"--更新亚马逊货件";
     public final String exportFunName = baseName+"--下载PackList文件";
     public final String deleteByIdFunName = baseName+"--通过主键删除亚马逊货件据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除亚马逊货件";
     public final String getLogisticsPackListDetail = baseName+"--通过ShipmentID查询亚马逊货件明细数据";
     public final String getLogisticsPackListDetailNew = baseName+"--通过ShipmentID查询亚马逊货件明细数据--新的PackList";
     public final String getSingleBoxRec = baseName+"--根据sku,shipmentId查找该sku所在箱的信息";
     public final String getSingleBoxRecNew = baseName+"--根据sku,shipmentId查找该sku所在箱的信息--新接口";
     public final String packListImportNew = baseName+"--新版--导入PackList";
     public final String createBindPackListNew = baseName+"--PackList绑定出货清单(国内仓)（新版绑定）";
     public final String newCancelPackList = baseName+"--PackList作废(新版PackList)";
     public final String editShipmentInfo = baseName+"--编辑ShipmentId与ShipTo";
     public final String shipmentRealStatusChange = baseName+"--调整货件状态";
     public final String queryLogisticsInGoodsList = baseName+"--通过ShipmentID查询来货信息 sku维度";
     public final String closeLogisticsInGoods = baseName+"--关闭在途的商品sku维度";
    @Resource
    private TbLogisticsPackListService tbLogisticsPackListService;

     @Resource
     com.tadpole.cloud.supplyChain.modular.manage.consumer.FileConsumer fileConsumer;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=TbLogisticsPackList.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData  queryById(BigDecimal id){
        return ResponseData.success(tbLogisticsPackListService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tbLogisticsPackListParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=TbLogisticsPackList.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsPackListParam tbLogisticsPackListParm ){
        //1.分页参数
       Page page = tbLogisticsPackListParm.getPageContext();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<TbLogisticsPackListResultModel> pageResult = tbLogisticsPackListService.pageQuery(tbLogisticsPackListParm);
        //3. 分页结果组装
        return ResponseData.success(pageResult);
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =TbLogisticsPackList.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody TbLogisticsPackList tbLogisticsPackList){
        return ResponseData.success(tbLogisticsPackListService.insert(tbLogisticsPackList));
    }
    
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackList 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =TbLogisticsPackList.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody TbLogisticsPackListParam tbLogisticsPackList){
        TbLogisticsPackList result = tbLogisticsPackListService.update(tbLogisticsPackList);
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
         if (tbLogisticsPackListService.deleteById(id)) {
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
       return ResponseData.success(tbLogisticsPackListService.deleteBatchIds(idList));
     }

     /**
      * 通过ShipmentID查询来货信息 sku维度
      *
      * @param shipmentId 主键
      * @return 实例对象
      */
     @ApiOperation(value =queryLogisticsInGoodsList,response= LogisticsInGoodsModel.class)
     @GetResource(name = queryLogisticsInGoodsList, path = "/queryLogisticsInGoodsList" )
     public ResponseData  queryLogisticsInGoodsList(String shipmentId){
         return tbLogisticsPackListService.queryLogisticsInGoodsList(shipmentId);
     }


     /**
      * 通过ShipmentID查询亚马逊货件明细数据
      *
      * @param shipmentId 主键
      * @return 实例对象
      */
     @ApiOperation(value =getLogisticsPackListDetail,response=TbLogisticsPackList.class)
     @GetResource(name = getLogisticsPackListDetail, path = "/getLogisticsPackListDetail" )
     public ResponseData  getLogisticsPackListDetail(String shipmentId){
         return ResponseData.success(tbLogisticsPackListService.getLogisticsPackListDetail(shipmentId));
     }

     /**
      * 通过ShipmentID查询亚马逊货件明细数据--新的PackList
      *
      * @param packListCode 主键
      * @return 实例对象
      */
     @ApiOperation(value =getLogisticsPackListDetailNew,response=TbLogisticsPackList.class)
     @GetResource(name = getLogisticsPackListDetailNew, path = "/getLogisticsPackListDetailNew" )
     public ResponseData  getLogisticsPackListDetailNew(String packListCode){
         return ResponseData.success(tbLogisticsPackListService.getLogisticsPackListDetailNew(packListCode));
     }

     /**
      * 根据sku，shipmentId查找该sku所在箱的信息
      *
      * @param shipmentId
      * @param sku
      * @return 实例对象
      */
     @ApiOperation(value =getSingleBoxRec,response=TbLogisticsPackList.class)
     @GetResource(name = getSingleBoxRec, path = "/getSingleBoxRec" )
     public ResponseData  getSingleBoxRec(String shipmentId,String sku){
         return ResponseData.success(tbLogisticsPackListService.getSingleBoxRec(shipmentId,sku));
     }

     /**
      * 根据sku，packListCode查找该sku所在箱的信息 --新接口
      *
      * @param packListCode
      * @param sku
      * @return 实例对象
      */
     @ApiOperation(value =getSingleBoxRecNew,response=TbLogisticsPackList.class)
     @GetResource(name = getSingleBoxRecNew, path = "/getSingleBoxRecNew" )
     public ResponseData  getSingleBoxRecNew(String packListCode,String sku){
         return ResponseData.success(tbLogisticsPackListService.getSingleBoxRecNew(packListCode,sku));
     }

     /**
      * 新版--导入PackList
      * @param multipartFile
      * @param param
      * @return
      */
     @PostResource(name = packListImportNew, path = "/packListImportNew", requiredPermission = false)
     @ApiOperation(value =packListImportNew )
     @BusinessLog(title = packListImportNew,opType = LogAnnotionOpTypeEnum.IMPORT)
     public ResponseData packListImportNew( HttpServletRequest request,
                                            @RequestParam(value = "file",required = false)MultipartFile multipartFile ,
                                            @RequestPart TbLogisticsPackListParam param) throws Exception {
         return tbLogisticsPackListService.packListImportNew(param,multipartFile);
     }


     /**
      * PackList绑定出货清单(国内仓)（新版绑定）
      *
      * @param createBindPackListParam 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =createBindPackListNew,response =ResponseData.class)
     @PostResource(name = createBindPackListNew, path = "/createBindPackListNew" )
     @BusinessLog(title = createBindPackListNew,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData  createBindPackListNew(@RequestBody CreateBindPackListParam createBindPackListParam){
         return tbLogisticsPackListService.createBindPackListNew(createBindPackListParam);
     }

     /**
      * PackList作废(新版PackList)
      *
      * @param packListCode
      * @return 实例对象
      */
     @ApiOperation(value =newCancelPackList,response=TbLogisticsPackList.class)
     @GetResource(name = newCancelPackList, path = "/newCancelPackList" )
     public ResponseData  newCancelPackList(String packListCode){
         return tbLogisticsPackListService.newCancelPackList(packListCode);
     }

     /**
      * 编辑ShipmentId与ShipTo
      *
      * @param param 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =editShipmentInfo,response =ResponseData.class)
     @PostResource(name = editShipmentInfo, path = "/editShipmentInfo" )
     @BusinessLog(title = editShipmentInfo,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData  editShipmentInfo(@RequestBody TbLogisticsPackListParam param){
         if (ObjectUtil.isEmpty(param.getShipTo())) {
             return ResponseData.error("ShipTo不能为空");
         }
         String shipTo = param.getShipTo().trim();
         shipTo= shipTo.substring(0, shipTo.lastIndexOf(";"));
         param.setShipTo(shipTo);
         return tbLogisticsPackListService.editShipmentInfo(param);
     }


     /**
      * 调整货件状态
      *
      * @param param 实例对象
      * @return 实例对象
      */
     @ApiOperation(value =shipmentRealStatusChange,response =ResponseData.class)
     @PostResource(name = shipmentRealStatusChange, path = "/shipmentRealStatusChange" )
     @BusinessLog(title = shipmentRealStatusChange,opType = LogAnnotionOpTypeEnum.UPDATE)
     public ResponseData  shipmentRealStatusChange(@RequestBody TbLogisticsPackListParam param){
         return tbLogisticsPackListService.shipmentRealStatusChange(param);
     }

     /**
      * 导出
      *
      * @param packListCode 筛选条件
      * @return ResponseData
      */
     @GetResource(name = exportFunName, path = "/export" )
     @ApiOperation(value = exportFunName, response =TbLogisticsPackListResult.class)
     @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
     public ResponseData export(@RequestParam String packListCode,@RequestParam(required = false) String shopNameSimple ,String packTempName,HttpServletResponse response) throws IOException {

         String fileName = packListCode+"_PackList.xlsx";
         Workbook workbook =  tbLogisticsPackListService.getWorkbook(packListCode, shopNameSimple,packTempName);

         if (ObjectUtil.isNull(workbook)) {
             return ResponseData.error("获取packList上传的原始模板失败");
         }

         workbook=  tbLogisticsPackListService.fillData(workbook,packListCode);

         if (ObjectUtil.isNull(workbook)) {
             log.error("没有绑定出货清单,能现在原来上传的");
             workbook = tbLogisticsPackListService.getWorkbook(packListCode, shopNameSimple,packTempName);;
         }

         OutputStream outputStream = null;
         try {
             response.setCharacterEncoding("UTF-8");
             //xlsx类型
             response.setContentType("application/vnd.ms-excel");
             //设置响应首部 Access-Control-Expose-Headers 就是控制“暴露”的开关
             response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
             response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
             outputStream = response.getOutputStream();
             workbook.write(outputStream);
         } catch (IOException e) {
             log.error("packList文件流输出异常");
         } finally {
             if(outputStream != null){
                 try {
                     outputStream.close();
                 } catch (IOException e) {
                     log.error("下载packList文件流关闭异常");
                 }
             }
         }

         return ResponseData.success();
     }


    /**
     * 关闭在途的商品--sku维度
     *
     * @param model 主键
     * @return 实例对象
     */
    @PostResource(name = closeLogisticsInGoods, path = "/closeLogisticsInGoods")
    @ApiOperation(value = closeLogisticsInGoods, response =TbLogisticsPackListResult.class)
    @BusinessLog(title = closeLogisticsInGoods,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  closeLogisticsInGoods(@RequestBody CloseLogisticsInGoodsParam model){
        return tbLogisticsPackListService.closeLogisticsInGoods(model);
    }


 }