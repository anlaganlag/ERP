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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsGoodsListReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsReceiveReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
* 出货清单信息-金蝶+海外仓;(tb_logistics_packing_list)表控制层
* @author : LSY
* @date : 2023-12-29
*/
@Api(tags = "物流报表管理")
@RestController
@ApiResource(name = "物流报表管理", path="/tbLogisticsReport")
public class TbLogisticsReportController {
    public final String baseName = "物流报表管理";
    public final String logisticsShipmentReport = "--出货清单报表";
    public final String logisticsGoodsListReport = "--货物清单报表";
    public final String logisticsReceiveReport = "--收货差异报表";
    public final String exportLogisticsShipmentReport = "--出货清单报表-导出";
    public final String exportLogisticsGoodsListReport = "--货物清单报表-导出";
    public final String exportLogisticsReceiveReport = "--收货差异报表--导出";


   @Resource
   private TbLogisticsPackingListService tbLogisticsPackingListService;

    @Autowired
    ResourceLoader resourceLoader;


   /**
    * 出货清单报表
    *
    * @param param 筛选条件
    * @return 查询结果
    */
   @ApiOperation(value =logisticsShipmentReport ,response=TbLogisticsPackingList.class)
   @PostResource(name = logisticsShipmentReport, path = "/logisticsShipmentReport", menuFlag = true)
   @BusinessLog(title = logisticsShipmentReport,opType = LogAnnotionOpTypeEnum.QUERY)
   public ResponseData getLogisticsShipmentReport(@RequestBody TbLogisticsShipmentReportParam param ){
       //2.分页查询
       /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
       PageResult<TbLogisticsShipmentReportResult> pageResult = tbLogisticsPackingListService.getLogisticsShipmentReport(param,false);
       //3. 分页结果组装
       return ResponseData.success(pageResult);
   }


    /**
     * 出货清单报表-导出
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =exportLogisticsShipmentReport ,response=TbLogisticsShipmentReportResult.class)
    @PostResource(name = exportLogisticsShipmentReport, path = "/exportLogisticsShipmentReport", menuFlag = false)
    @BusinessLog(title = exportLogisticsShipmentReport,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportLogisticsShipmentReport(@RequestBody TbLogisticsShipmentReportParam param,HttpServletResponse response ) throws Exception{
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<TbLogisticsShipmentReportResult> pageResult = tbLogisticsPackingListService.getLogisticsShipmentReport(param,true);
        List<TbLogisticsShipmentReportResult> resultRows = pageResult.getRows();
        if (ObjectUtil.isEmpty(resultRows)) {
            return ResponseData.error("未找到导出的数据");
        }
        TbLogisticsShipmentReportResult initResult = new TbLogisticsShipmentReportResult();
        TbLogisticsShipmentReportResult reduceResult = resultRows.stream().reduce(initResult, (r1, r2) -> {
            r1.setReturnBox(r1.getReturnBox() + r2.getReturnBox());
            r1.setReturnProduct(r1.getReturnProduct() + r2.getReturnProduct());
            r1.setUnShippedProduct(r1.getUnShippedProduct() + r2.getUnShippedProduct());
            r1.setShippedProduct(r1.getShippedProduct() + r2.getShippedProduct());
            r1.setUnShippedBox(r1.getUnShippedBox() + r2.getUnShippedBox());
            r1.setShippedBox(r1.getShippedBox() + r2.getShippedBox());
            r1.setProductSum(r1.getProductSum() + r2.getProductSum());
            r1.setBoxSum(r1.getBoxSum() + r2.getBoxSum());
            return r1;
        });

        reduceResult.setFbaTurnDateStr("合计");
        resultRows.add(reduceResult);

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("出货清单报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsShipmentReportResult.class).sheet("出货清单报表").doWrite(resultRows);
        //3. 分页结果组装
        return ResponseData.success();
    }


    /**
     * 货物清单报表
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =logisticsGoodsListReport ,response=LogisticsGoodsListViewModel.class)
    @PostResource(name = logisticsGoodsListReport, path = "/logisticsGoodsListReport", menuFlag = true)
    @BusinessLog(title = logisticsGoodsListReport,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getLogisticsGoodsListReport(@RequestBody TbLogisticsGoodsListReportParam param ){
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<LogisticsGoodsListViewModel> pageResult = tbLogisticsPackingListService.getLogisticsGoodsListReport(param,false);
        //3. 分页结果组装
        return ResponseData.success(pageResult);
    }


    /**
     * 货物清单报表-导出
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =exportLogisticsGoodsListReport ,response=LogisticsGoodsListViewModel.class)
    @PostResource(name = exportLogisticsGoodsListReport, path = "/exportLogisticsGoodsListReport", menuFlag = false)
    @BusinessLog(title = exportLogisticsGoodsListReport,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportLogisticsGoodsListReport(@RequestBody TbLogisticsGoodsListReportParam param,HttpServletResponse response ) throws Exception{
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<LogisticsGoodsListViewModel> pageResult = tbLogisticsPackingListService.getLogisticsGoodsListReport(param,true);
        List<LogisticsGoodsListViewModel> resultRows = pageResult.getRows();
        if (ObjectUtil.isEmpty(resultRows)) {
            return ResponseData.error("未找到导出的数据");
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("货物清单.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LogisticsGoodsListViewModel.class).sheet("货物清单").doWrite(resultRows);
        //3. 分页结果组装
        return ResponseData.success();
    }

    /**
     * 收货差异报表
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =logisticsReceiveReport ,response=LogisticsReceiveReportViewModel.class)
    @PostResource(name = logisticsReceiveReport, path = "/logisticsReceiveReport", menuFlag = true)
    @BusinessLog(title = logisticsReceiveReport,opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getLogisticsReceiveReport(@RequestBody TbLogisticsReceiveReportParam param ){
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<LogisticsReceiveReportViewModel> pageResult = tbLogisticsPackingListService.getLogisticsReceiveReport(param,false);
        //3. 分页结果组装
        return ResponseData.success(pageResult);
    }


    /**
     * 收货差异报表-导出
     *
     * @param param 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value =exportLogisticsReceiveReport ,response=LogisticsReceiveReportViewModel.class)
    @PostResource(name = exportLogisticsReceiveReport, path = "/exportLogisticsReceiveReport", menuFlag = false)
    @BusinessLog(title = exportLogisticsReceiveReport,opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportLogisticsReceiveReport(@RequestBody TbLogisticsReceiveReportParam param,HttpServletResponse response ) throws Exception{
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        PageResult<LogisticsReceiveReportViewModel> pageResult = tbLogisticsPackingListService.getLogisticsReceiveReport(param,true);
        List<LogisticsReceiveReportViewModel> resultRows = pageResult.getRows();
        if (ObjectUtil.isEmpty(resultRows)) {
            return ResponseData.error("未找到导出的数据");
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("收货差异报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LogisticsReceiveReportViewModel.class).sheet("收货差异报表").doWrite(resultRows);
        //3. 分页结果组装
        return ResponseData.success();
    }



}