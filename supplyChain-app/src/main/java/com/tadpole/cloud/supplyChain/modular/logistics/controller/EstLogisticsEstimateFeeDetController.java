package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import java.util.List;
import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetUploadParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeDetService;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeDetMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeDetService;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.excel.EasyExcel;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * ;(EST_LOGISTICS_ESTIMATE_FEE_DET)表控制层
 * @author : LSY
 * @date : 2024-3-14
 */
@Api(tags = "物流费用测算明细")
@RestController
@ApiResource(name = "物流费用测算明细", path="/estLogisticsEstimateFeeDet")
public class EstLogisticsEstimateFeeDetController{
     public final String baseName = "物流测算费用明细";
     public final String queryByIdFunName = baseName+"--通过ID查询";
     public final String paginQueryFunName = baseName+"--分页查询";
     public final String addFunName = baseName+"--新增";
     public final String editFunName = baseName+"--更新";
     public final String exportFunName = baseName+"--按查询条件导出";
     public final String deleteByIdFunName = baseName+"--通过主键删除据";
     public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除";
    @Resource
    private EstLogisticsEstimateFeeDetService estLogisticsEstimateFeeDetService;


    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value =queryByIdFunName,response=EstLogisticsEstimateFeeDet.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid", requiredLogin = false)
    public ResponseData  queryById(String id){
        return ResponseData.success(estLogisticsEstimateFeeDetService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param estLogisticsEstimateFeeDetParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =paginQueryFunName ,response=EstLogisticsEstimateFeeDet.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true,requiredPermission = false,requiredLogin = false)
    @BusinessLog(title = paginQueryFunName,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDetParm ){
        //1.分页参数
       Page page = estLogisticsEstimateFeeDetParm.getPageContext();
       long current = page.getCurrent();
       long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<EstLogisticsEstimateFeeDetResult> pageResult = estLogisticsEstimateFeeDetService.paginQuery(estLogisticsEstimateFeeDetParm, current,size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }
    
    /** 
     * 新增数据
     *
     * @param estLogisticsEstimateFeeDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =addFunName,response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = addFunName, path = "/add",  requiredLogin = false)
    @BusinessLog(title = addFunName,opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  add(@RequestBody EstLogisticsEstimateFeeDet estLogisticsEstimateFeeDet){
        return ResponseData.success(estLogisticsEstimateFeeDetService.insert(estLogisticsEstimateFeeDet));
    }


    @ApiOperation(value ="生成ShipmentId",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "生成ShipmentId", path = "/generateShipmentId",  requiredLogin = false)
    @BusinessLog(title = "生成ShipmentId")
    public ResponseData  generateShipmentId(String estId){
        return ResponseData.success(estLogisticsEstimateFeeDetService.generateShipmentId(estId));
    }
    
    /** 
     * 更新数据
     *
     * @param estLogisticsEstimateFeeDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value =editFunName,response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = editFunName, path = "/update",  requiredLogin = false)
    @BusinessLog(title = editFunName,opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  edit(@RequestBody EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDet){
        EstLogisticsEstimateFeeDet result = estLogisticsEstimateFeeDetService.update(estLogisticsEstimateFeeDet);
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
    @GetResource(name = deleteByIdFunName, path = "/deleteById", requiredLogin = false)
    @BusinessLog(title = deleteByIdFunName,opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData  deleteById(String id){
         if (estLogisticsEstimateFeeDetService.deleteById(id)) {
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
     @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds", requiredLogin = false)
     @BusinessLog(title = deleteBatchIdsFunName,opType = LogAnnotionOpTypeEnum.DELETE)
     public ResponseData deleteBatchIds(@RequestBody  List<String> idList){
      if (Objects.isNull(idList) || idList.isEmpty()) {
             return ResponseData.error("主键List不能为空");
        }
       return ResponseData.success(estLogisticsEstimateFeeDetService.deleteBatchIds(idList));
     }
     /**
     * 导出
     *
     * @param estLogisticsEstimateFeeDetParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export",   requiredLogin = false)
    @ApiOperation(value = exportFunName, response =EstLogisticsEstimateFeeDetResult.class)
    @BusinessLog(title = exportFunName,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDetParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        List<EstLogisticsEstimateFeeDetExportResult> pageResult = estLogisticsEstimateFeeDetService.exportList(estLogisticsEstimateFeeDetParm);
        if (Objects.isNull(pageResult) || pageResult.size()==0) {
            return    ResponseData.success();
        }
        String fileName = "FBA头程费用测算"+ DateUtil.format(DateUtil.date(), "yyyyMMdd") +".xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String((fileName+".xlsx").getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), EstLogisticsEstimateFeeDetExportResult.class).sheet("").doWrite(pageResult);
        return ResponseData.success();
    }

    @ApiOperation(value ="exportList" ,response=EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "exportList", path = "/exportList")
    @BusinessLog(title = "exportList",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportList(@RequestBody EstLogisticsEstimateFeeDetParam estLogisticsEstimateFeeDetParm ){
        //1.分页参数
        Page page = estLogisticsEstimateFeeDetParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        return ResponseData.success(estLogisticsEstimateFeeDetService.exportList(estLogisticsEstimateFeeDetParm));
    }





    @ParamValidator
    @PostResource(name = "导入packList", path = "/uploadPackListOld", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导入packList")
    @BusinessLog(title = "费用测算-导入packList",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadPackList(@ModelAttribute EstLogisticsEstimateFeeDetUploadParam param, @RequestParam(value = "file") MultipartFile file) throws IOException, InvalidFormatException {
        return estLogisticsEstimateFeeDetService.uploadPackList(param,file);
    }


    @ParamValidator
    @PostResource(name = "导入packList二进制接收", path = "/uploadPackList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导入packList二进制接收")
    @BusinessLog(title = "费用测算-导入packList二进制接收",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadPackListByBin(HttpServletRequest request,
                                            @RequestParam(value = "file",required = false)MultipartFile multipartFile ,
                                            @RequestPart EstLogisticsEstimateFeeDetUploadParam param) throws IOException, InvalidFormatException {
        return estLogisticsEstimateFeeDetService.uploadPackList(param,multipartFile);
    }





    @ParamValidator
    @PostResource(name = "导入packList返回json", path = "/uploadPackListJson", requiredPermission = false)
    @ApiOperation(value = "导入packList返回json")
    @BusinessLog(title = "费用测算-导入packList返回json",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadPackListJson(@RequestParam(value = "file") MultipartFile file) throws IOException, InvalidFormatException {
        return estLogisticsEstimateFeeDetService.uploadPackListJson(file);
    }


    @ApiOperation(value ="测算单条费用",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "测算单条费用", path = "/singleCostEst",  requiredLogin = false)
    @BusinessLog(title = "测算单条费用",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  singleCostEst(@RequestBody EstLogisticsEstimateFeeDetParam param){
        return ResponseData.success(estLogisticsEstimateFeeDetService.singleCostEst(param));
    }

    @ApiOperation(value ="测算人工录入单条费用",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "测算人工录入单条费用", path = "/entrySingleCostEst",  requiredLogin = false)
    @BusinessLog(title = "测算人工录入单条费用",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  entrySingleCostEst(@RequestBody EstLogisticsEstimateFeeDetParam param){
        return ResponseData.success(estLogisticsEstimateFeeDetService.entrySingleCostEst(param));
    }


    @ApiOperation(value ="获取物流供应商价格",response = LsLogisticsPriceResult.class)
    @PostResource(name = "获取物流供应商价格", path = "/getLpPrice",  requiredLogin = false)
    @BusinessLog(title = "获取物流供应商价格")
    public ResponseData  getLpPrice(@RequestBody EstLogisticsEstimateFeeDetParam param){
        return ResponseData.success(estLogisticsEstimateFeeDetService.getLpPrice(param));
    }


    @ApiOperation(value ="测算全部费用",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "测算全部费用", path = "/allCostEst",  requiredLogin = false)
    @BusinessLog(title = "测算全部费用",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData  allCostEst(@RequestBody List<EstLogisticsEstimateFeeDetParam> params){
        return estLogisticsEstimateFeeDetService.allCostEst(params);
    }


    @ApiOperation(value ="shipTo下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "shipTo下拉", path = "/shipToList",  requiredLogin = false)
    @BusinessLog(title = "shipTo下拉")
    public ResponseData  shipToList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.shipToList());
    }

    @ApiOperation(value ="getPostCode",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "getPostCode", path = "/getPostCode",  requiredLogin = false)
    @BusinessLog(title = "getPostCode")
    public ResponseData  getPostCode(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.getPostCodeMap());
    }

    @ApiOperation(value ="FBA配置费币种下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "FBA配置费币种下拉", path = "/currencyList",  requiredLogin = false)
    @BusinessLog(title = "FBA配置费币种下拉")
    public ResponseData  currencyList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.currencyList());
    }



    @ApiOperation(value ="获取物流地址簿信息",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "获取物流地址簿信息", path = "/getLogisticsInfo",  requiredLogin = false)
    @BusinessLog(title = "获取物流地址簿信息")
    public ResponseData  getLogisticsInfo(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.getLogisticsInfo());
    }

    @ApiOperation(value ="物流商名称下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "物流商名称下拉", path = "/logisticsProviderList",  requiredLogin = false)
    @BusinessLog(title = "物流商名称下拉")
    public ResponseData  logisticsProviderSelect(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.logisticsProviderList());
    }

    @ApiOperation(value ="站点下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "站点下拉", path = "/siteList",  requiredLogin = false)
    @BusinessLog(title = "站点下拉")
    public ResponseData  siteList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.siteList());
    }

    @ApiOperation(value ="货运方式1下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "货运方式1下拉", path = "/freightCompanyList",  requiredLogin = false)
    @BusinessLog(title = "货运方式1下拉")
    public ResponseData  freightCompanyList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.freightCompanyList());
    }

    @ApiOperation(value ="运输方式下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "运输方式下拉", path = "/transportTypeList",  requiredLogin = false)
    @BusinessLog(title = "运输方式下拉")
    public ResponseData  transportTypeList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.transportTypeList());
    }
    @ApiOperation(value ="物流渠道下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "物流渠道下拉", path = "/logisticsChannelList",  requiredLogin = false)
    @BusinessLog(title = "物流渠道下拉")
    public ResponseData  logisticsChannelList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.logisticsChannelList());
    }
    @ApiOperation(value ="红蓝单下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "红蓝单下拉", path = "/orderTypeList",  requiredLogin = false)
    @BusinessLog(title = "红蓝单下拉")
    public ResponseData  orderTypeList(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.orderTypeList());
    }


    @ApiOperation(value ="分区号下拉",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "分区号下拉", path = "/lspNumList",  requiredLogin = false)
    @BusinessLog(title = "分区号下拉")
    public ResponseData  lspNumList(@RequestBody EstLogisticsEstimateFeeDetParam param){
        return ResponseData.success(estLogisticsEstimateFeeDetService.lspNumList(param));
    }

    @ApiOperation(value ="查询固定汇率",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "查询固定汇率", path = "/getFixedRate",  requiredLogin = false)
    @BusinessLog(title = "查询固定汇率")
    public ResponseData  getFixedRate(){
        return ResponseData.success(estLogisticsEstimateFeeDetService.getFixedRate());
    }



    @ApiOperation(value ="shipmentId删除",response =EstLogisticsEstimateFeeDet.class)
    @GetResource(name = "shipmentId删除", path = "/delByShipmentId",  requiredLogin = false)
    @BusinessLog(title = "shipmentId删除")
    public ResponseData  delByShipmentId(String estId,String shipmentId){
        return estLogisticsEstimateFeeDetService.delByShipmentId(estId,shipmentId);
    }


    @ApiOperation(value ="shipmentId更新",response =EstLogisticsEstimateFeeDet.class)
    @PostResource(name = "shipmentId更新", path = "/updateByShipmentId",  requiredLogin = false)
    @BusinessLog(title = "shipmentId更新")
    public ResponseData  updateByShipmentId(@RequestBody  EstLogisticsEstimateFeeDetParam param){
        return estLogisticsEstimateFeeDetService.updateByShipmentId(param);
    }





}