package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.EbmsOverseasOutWarehouseLogisticsParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasOutWarehouseParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ExportOverseasOutWarehouseResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasOutWarehouseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  海外仓出库管理服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface IOverseasOutWarehouseService extends IService<OverseasOutWarehouse> {

    /**
     * 出货仓名称下拉
     * @return
     */
    List<Map<String, Object>> outWarehouseSelect();

    /**
     * 收货仓名称下拉
     * @return
     */
    List<Map<String, Object>> inWarehouseSelect();

    /**
     * 海外仓出库管理查询列表接口
     */
    PageResult<OverseasOutWarehouseResult> queryListPage(OverseasOutWarehouseParam param);

    /**
     * 海外仓出库管理查询列表数据汇总
     */
    ResponseData queryPageTotal(OverseasOutWarehouseParam param);

    /**
     * 查询出库单详情接口
     */
    List<OverseasOutWarehouseDetailResult> list(OverseasOutWarehouseDetailParam param);

    /**
     * 根据箱型获取箱子信息
     * @param boxType
     * @return
     */
    ResponseData getBoxInfoByBoxType(String boxType);

    /**
     * 海外仓出库管理列表导入接口
     * @param param
     * @param file
     * @param departmentList
     * @param teamList
     * @return
     */
    ResponseData importExcel(OverseasOutWarehouseParam param, MultipartFile file, List<String> departmentList, List<String> teamList);

    /**
     * 创建出库清单接口
     * @param param
     * @param isTask 是否是定时任务
     * @return
     */
    ResponseData createShipmentList(OverseasOutWarehouseParam param, Boolean isTask);

    /**
     * 海外仓出库管理导入保存接口
     * @param params
     * @return
     */
    ResponseData save(OverseasOutWarehouseParam params)  throws Exception;

    /**
     * 海外仓出库管理列表导出接口
     */
    List<ExportOverseasOutWarehouseResult> export(OverseasOutWarehouseParam param);

    /**
     * 获取EBMS物流状态信息
     * @param params
     * @return
     */
    ResponseData receiveLogisticsByEBMS(List<EbmsOverseasOutWarehouseLogisticsParam> params);

    /**
     * 推送EBMS创建出货清单
     * @return
     */
    ResponseData rePushShipmentList();
}
