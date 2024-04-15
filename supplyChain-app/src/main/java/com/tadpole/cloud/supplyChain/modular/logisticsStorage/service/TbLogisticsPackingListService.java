package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceCalcParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出货清单信息-金蝶+海外仓;(tb_logistics_packing_list)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackingListService extends IService<TbLogisticsPackingList> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packCode 主键
     * @return 实例对象
     */
    TbLogisticsPackingList queryById(String packCode);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackingList 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackingListResult> pageQuery(TbLogisticsPackingListParam tbLogisticsPackingList, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingList 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingList insert(TbLogisticsPackingList tbLogisticsPackingList);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackingList 实例对象
     * @return 实例对象
     */
    TbLogisticsPackingList update(TbLogisticsPackingListParam tbLogisticsPackingList);
    /** 
     * 通过主键删除数据
     *
     * @param packCode 主键
     * @return 是否成功
     */
    boolean deleteById(String packCode);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param packCodeList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> packCodeList);

    /**
     * 根据出货清单号查询，出货清单信息明细1,2
     *
     * @param packCode
     * @return
     */
    TbLogisticsPackingListResultDet1AndDet2 getLogisticsShipmentListDetail(String packCode);

    /**
     * 获取通过数据
     * @param packCode
     * @return
     */
    List<TbClearancModel> getClearanceData(String packCode);

    /**
     * 出货清单 转仓
     * @param param
     * @return
     */
    ResponseData transformWarehouse(TbLogisticsPackingListParam param);

    /**
     * 创建转仓清单明细【来源出货清单】
     * @param packCode
     * @param transferInHouseCode
     * @param transferInHouseName
     * @param transferOutHouseCode
     * @param transferOutHouseName
     * @param lastID
     * @param boxNumList
     * @return
     */

    int createLogisticsTransferRecordDetFromShipment(String packCode, String transferInHouseCode, String transferInHouseName,
                                                     String transferOutHouseCode, String transferOutHouseName, BigDecimal lastID, List<Integer> boxNumList);

    /**
     * 转仓
     * @param packCode
     * @param countryCode
     * @param packStoreHouseType
     * @param packStoreHouseName
     * @param packUploadState
     * @return
     */
    boolean transformWarehouse(String packCode, String countryCode, String packStoreHouseType, String packStoreHouseName, String packUploadState);

    /**
     * FBA回传至ERP
     * @param packCode
     * @param shipmentID
     * @return
     */
    ResponseData fbaBackhaulErp(String packCode, String shipmentID);


    /**
     * 返仓
     * @param packCode
     * @param packStoreHouseType
     * @return
     */
    ResponseData returnWarehouse(String packCode, String packStoreHouseType);

    /**
     * 作废出货清单
     * @param packCode
     * @return
     */
    ResponseData delShipmentList(String packCode);

    /**
     * 更新出货清单状态
     * @param packCode
     * @param packUploadState
     * @param shipmentID
     * @param shipTo
     * @return
     */
    boolean updateShipmentStatus(String packCode, String packUploadState, String shipmentID, String shipTo);

    /**
     * 生成直接调拨单
     * @param packCode
     * @return
     */
    ResponseData generateDirectOrder(String packCode);

    PageResult<TbLogisticsShipmentReportResult> getLogisticsShipmentReport(TbLogisticsShipmentReportParam param,boolean isExport);

    /**
     * 货物清单报表查询
     * @param reqVO
     * @param isExport
     * @return
     */
    PageResult<LogisticsGoodsListViewModel> getLogisticsGoodsListReport(TbLogisticsGoodsListReportParam reqVO, boolean isExport);


    /**
     * 收货差异报表查询
     * @param reqVO
     * @param isExport
     * @return
     */
    PageResult<LogisticsReceiveReportViewModel> getLogisticsReceiveReport(TbLogisticsReceiveReportParam reqVO, boolean isExport);

    /**
     * 物流商发货申请提醒
     * @param packStoreHouseType
     * @param packStoreHouseName
     * @return
     */
    ResponseData lpsr(String packStoreHouseType, String packStoreHouseName);

    /**
     * 物流商发货提醒明细项
     * @param packStoreHouseType
     * @param packStoreHouseName
     * @return
     */
    List<TbShipmentApplyDetModel> lpsrDet(String packStoreHouseType, String packStoreHouseName);

    /**
     * 物流商发货申请提醒--申请发货--保存并同步通过数据
     * @param request
     * @return
     */
    ResponseData createLogisticsOrder(TbLogisticsListToHeadRouteParam request);

    /**
     * 更新物流单 信息
     *
     * @param request
     * @return
     */
    public ResponseData updateLogisticsOrder(TbLogisticsListToHeadRouteParam request);

    /**
     * 根据批次号和快递单号 更新出货清单信息 状态
     * @param lhrCode
     * @param lhrOddNumb
     * @param packLogState
     * @return
     */
    int updatePackLogState(String lhrCode, String lhrOddNumb, String packLogState);

    /**
     * 根据批次号 查询出货清单信息
     * @param lhrCode
     * @return
     */
    List<TbLogisticsPackingList> queryByLhrCode(String lhrCode);

    /**
     * 获取packList能关联出的出货清单
     * @param shopNameSimple
     * @param countryCode
     * @param comWarehouseName
     * @return
     */
    List<TbLogisticsPackingList> getLogisticsShipmentList(String shopNameSimple, String countryCode, String comWarehouseName);

    int upPackUploadStateAndPackAbnormalStatus(String packCode, String packUploadState, int packAbnormalStatus);

    /**
     * 接收金蝶推送的出货清单信息
     * @param request
     * @return
     */
    ResponseData receiveK3PushPackingList(TbLogisticsPackingListK3Param request) throws Exception;

    boolean clearPackRecord(String packCode);

    /**
     * 清关税费测算
     * @param param
     * @return
     */
    ResponseData initClearanceCalc(TgCustomsClearanceCalcParam param);


    /**
     * 推送物流实际结算任务
     *
     * @param headerRouteDetList
     * @param model
     * @param logisticsListToEndRouteModel
     * @param logFeeWeight                 计费重量
     * @return
     */
    public ResponseData receiveLogisticsSettlement(List<TbLogisticsListToHeadRouteDetParam> headerRouteDetList,
                                                   TbLogisticsListToHeadRoute model,
                                                   TbLogisticsListToEndRoute logisticsListToEndRouteModel,
                                                   BigDecimal logFeeWeight);
}