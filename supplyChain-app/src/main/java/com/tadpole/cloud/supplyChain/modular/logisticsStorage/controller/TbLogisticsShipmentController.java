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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipment;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 亚马逊发货申请记录-EBMS形成记录;(tb_logistics_shipment)表控制层
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "亚马逊发货申请记录-EBMS形成记录")
@RestController
@ApiResource(name = "亚马逊发货申请记录-EBMS形成记录", path = "/tbLogisticsShipment")
public class TbLogisticsShipmentController {
    public final String baseName = "亚马逊发货申请记录-EBMS形成记录";
    public final String queryByIdFunName = baseName + "--通过ID查询亚马逊发货申请记录-EBMS形成记录";
    public final String paginQueryFunName = baseName + "--分页查询亚马逊发货申请记录-EBMS形成记录";
    public final String addFunName = baseName + "--新增亚马逊发货申请记录-EBMS形成记录";
    public final String editFunName = baseName + "--更新亚马逊发货申请记录-EBMS形成记录";
    public final String exportFunName = baseName + "--按查询条件导出亚马逊发货申请记录-EBMS形成记录";
    public final String downloadShipmentPlan = baseName + "--下载发货计划";
    public final String downloadShipmentPlanNew = baseName + "--下载发货计划--新版";
    public final String deleteByIdFunName = baseName + "--通过主键删除亚马逊发货申请记录-EBMS形成记录据";
    public final String deleteByPackCode = baseName + "--Amazon发货申请删除";
    public final String deleteBatchIdsFunName = baseName + "--通过主键批量删除亚马逊发货申请记录-EBMS形成记录";
    public final String createShipmentPlanToShipmentList = baseName + "--基于出货清单生成Plan";
    public final String getLogisticsShipmentListToPlan = baseName + "--获取出货清单信息（未绑定的出货清单）";
    @Resource
    private TbLogisticsShipmentService tbLogisticsShipmentService;
    @Resource
    private TbLogisticsShipmentDetService tbLogisticsShipmentDetService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = queryByIdFunName, response = TbLogisticsShipment.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData queryById(BigDecimal id) {
        return ResponseData.success(tbLogisticsShipmentService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param tbLogisticsShipmentParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = paginQueryFunName, response = TbLogisticsShipment.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsShipmentParam tbLogisticsShipmentParm) {
        //1.分页参数
        Page page = tbLogisticsShipmentParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipmentResult> pageResult = tbLogisticsShipmentService.pageQuery(tbLogisticsShipmentParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsShipment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = addFunName, response = TbLogisticsShipment.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName, opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbLogisticsShipment tbLogisticsShipment) {
        return ResponseData.success(tbLogisticsShipmentService.insert(tbLogisticsShipment));
    }

    /**
     * 更新数据
     *
     * @param tbLogisticsShipment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = editFunName, response = TbLogisticsShipment.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbLogisticsShipmentParam tbLogisticsShipment) {
        TbLogisticsShipment result = tbLogisticsShipmentService.update(tbLogisticsShipment);
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
    @ApiOperation(value = deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(BigDecimal id) {
        if (tbLogisticsShipmentService.deleteById(id)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }

    /**
     * Amazon发货申请删除
     *
     * @param packCode 出货清单号
     * @return 是否成功
     */
    @ApiOperation(value = deleteByPackCode)
    @GetResource(name = deleteByPackCode, path = "/deleteByPackCode" )
    @BusinessLog(title = deleteByPackCode, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteByPackCode(String  packCode) {
        if (tbLogisticsShipmentService.deleteByPackCode(packCode)) {
            return ResponseData.success("Amazon发货申请删除成功");
        }
        return ResponseData.error("Amazon发货申请删除失败");
    }

    /**
     * 批量删除数据
     *
     * @param idList 主键List集合
     * @return 是否成功
     */
    @ApiOperation(value = deleteBatchIdsFunName)
    @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
    @BusinessLog(title = deleteBatchIdsFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatchIds(@RequestBody List<BigDecimal> idList) {
        if (Objects.isNull(idList) || idList.isEmpty()) {
            return ResponseData.error("主键List不能为空");
        }
        return ResponseData.success(tbLogisticsShipmentService.deleteBatchIds(idList));
    }

    /**
     * 导出
     *
     * @param tbLogisticsShipmentParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response = TbLogisticsShipmentResult.class)
    @BusinessLog(title = exportFunName, opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsShipmentParam tbLogisticsShipmentParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsShipmentResult> pageResult = tbLogisticsShipmentService.pageQuery(tbLogisticsShipmentParm, current, size);
        List<TbLogisticsShipmentResult> records = pageResult.getRecords();
        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊发货申请记录-EBMS形成记录.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsShipmentResult.class).sheet("亚马逊发货申请记录-EBMS形成记录").doWrite(records);
        return ResponseData.success();
    }

    /**
     * 下载发货计划
     *
     * @param packCode 筛选条件
     * @return ResponseData
     */
    @GetResource(name = downloadShipmentPlan, path = "/downloadShipmentPlan" )
    @ApiOperation(value = downloadShipmentPlan, response = TbLogisticsShipmentResult.class)
    @BusinessLog(title = downloadShipmentPlan, opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadShipmentPlan(String packCode, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        //1.分页参数  DownloadShipmentPlan
        TbLogisticsShipment shipment = tbLogisticsShipmentService.queryByPackCode(packCode);

        List<TbLogisticsShipmentDet> shipmentDetList = tbLogisticsShipmentDetService.queryByPackCode(packCode);

        //   _db.Tables.TbLogisticsShipments.Where(l => l.PackCode == packCode).Update(x => new TbLogisticsShipment { BusAppStatus = "人工处理" });

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("PlanName\t" + shipment.getPlanName()+"\n");
        strBuf.append("UK".equals(shipment.getShipToCountry()) ? "ShipToCountry\tGB" : "ShipToCountry\t" + shipment.getShipToCountry()+"\n");
        if ("IN".equals(shipment.getShipToCountry()))
        {
            strBuf.append("ShipToSubdivision\t " + "BOM1"+"\n");
        }
        strBuf.append("AddressFieldOne\t" + '"' + shipment.getAddressFieldOne() + '"'+"\n");
        strBuf.append("AddressFieldTwo\t" + '"' + shipment.getAddressFieldTwo() + '"'+"\n");
        strBuf.append("AddressCity\t" + shipment.getAddressCity()+"\n");
        strBuf.append("AddressCountryCode\t" + shipment.getAddressCountryCode()+"\n");
        strBuf.append("AddressStateOrRegion\t" + shipment.getAddressStateOrRegion()+"\n");
        strBuf.append("AddressPostalCode\t" + shipment.getAddressPostalCode()+"\n");
        strBuf.append("AddressDistrict\t" + shipment.getAddressDistrict()+"\n");
        strBuf.append("MerchantSKU\tQuantity"+"\n");
        for (TbLogisticsShipmentDet det : shipmentDetList) {
            strBuf.append(det.getMerchantSku() + "\t" + det.getQuantity()+"\n");
        }
        PrintWriter writer = response.getWriter();
        writer.write(strBuf.toString());
        response.flushBuffer();
        writer.flush();
        writer.close();

    }

    /**
     * 下载发货计划
     *
     * @param packCode 筛选条件
     * @return ResponseData
     */
    @GetResource(name = downloadShipmentPlanNew, path = "/downloadShipmentPlanNew" )
    @ApiOperation(value = downloadShipmentPlanNew, response = TbLogisticsShipmentResult.class)
    @BusinessLog(title = downloadShipmentPlanNew, opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadShipmentPlanNew(String packCode, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        //1.分页参数  DownloadShipmentPlan
        TbLogisticsShipment shipment = tbLogisticsShipmentService.queryByPackCode(packCode);

        List<TbLogisticsShipmentDet> shipmentDetList = tbLogisticsShipmentDetService.queryByPackCode(packCode);

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("Please review the Example tab before you complete this sheet"+"\n");
        strBuf.append(""+"\n");
        strBuf.append("Default prep owner\t" + "Seller"+"\n");
        strBuf.append("Default labeling owner\t" + "Seller"+"\n");
        strBuf.append(""+"\n");
        strBuf.append(""+"\n");
        strBuf.append("\t" + "Optional"+"\n");
        strBuf.append("Merchant SKU\tQuantity\tPrep owner\tLabeling owner"+"\n");

        for (TbLogisticsShipmentDet det : shipmentDetList) {
            strBuf.append(det.getMerchantSku() + "\t" + det.getQuantity()+"\t" + "\t"+"\n");
        }

        PrintWriter writer = response.getWriter();
        writer.write(strBuf.toString());
        response.flushBuffer();
        writer.flush();
        writer.close();

    }

    /**
     * 基于出货清单生成Plan
     *
     * @param shipmentList 实例对象集合
     * @return 实例对象
     */
    @ApiOperation(value = createShipmentPlanToShipmentList, response = TbLogisticsShipment.class)
    @PostResource(name = createShipmentPlanToShipmentList, path = "/createShipmentPlanToShipmentList" )
    @BusinessLog(title = createShipmentPlanToShipmentList, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData createShipmentPlanToShipmentList(@RequestBody List<TbLogisticsShipmentParam> shipmentList) {
        return  tbLogisticsShipmentService.createShipmentPlanToShipmentList(shipmentList);
    }

    /**
     * 获取出货清单信息
     *
     * @param packCode 获取出货清单信息
     * @return 出货清单信息
     */
    @ApiOperation(value = getLogisticsShipmentListToPlan, response = TbLogisticsPackingList.class)
    @GetResource(name = getLogisticsShipmentListToPlan, path = "/getLogisticsShipmentListToPlan" )
    @BusinessLog(title = getLogisticsShipmentListToPlan, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getLogisticsShipmentListToPlan(@RequestParam(required = false) String packCode) {
        return  tbLogisticsShipmentService.getLogisticsShipmentListToPlan(packCode);
    }


}