package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsNoFeeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsTrackReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsTrackReportResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  BTB物流单服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
public interface ILsBtbLogisticsNoService extends IService<LsBtbLogisticsNo> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(LsBtbLogisticsNoParam param);

    /**
     * 新建物流单
     * @param param
     * @return
     */
    ResponseData add(LsBtbLogisticsNoParam param);

    /**
     * 编辑保存
     * @param param
     * @return
     */
    ResponseData edit(LsBtbLogisticsNoParam param);

    /**
     * 删除
     * @param param
     * @return
     */
    ResponseData del(LsBtbLogisticsNoParam param);

    /**
     * 导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 导出
     * @param param
     * @return
     */
    List<LsBtbLogisticsNoResult> export(LsBtbLogisticsNoParam param);

    /**
     * 签收
     * @param param
     * @return
     */
    ResponseData sign(LsBtbLogisticsNoParam param);

    /**
     * BTB物流跟踪报表分页查询列表
     */
    ResponseData queryBtbPage(LsLogisticsTrackReportParam param);

    /**
     * EBMS物流跟踪报表分页查询列表
     */
    ResponseData queryEbmsPage(LsLogisticsTrackReportParam param);

    /**
     * BTB物流跟踪报表查询列表
     */
    List<LsLogisticsTrackReportResult> queryBtbList(LsLogisticsTrackReportParam param);

    /**
     * EBMS物流跟踪报表查询列表
     */
    List<LsLogisticsTrackReportResult> queryEbmsList(LsLogisticsTrackReportParam param);

    /**
     * 物流跟踪报表导出
     * @param param
     * @return
     */
    List<LsLogisticsTrackReportResult> exportLogisticsTrack(LsLogisticsTrackReportParam param);

    /**
     * 发货仓下拉
     * @return
     */
    ResponseData shipmentWarehouseSelect();

    /**
     * BTB发货仓下拉
     */
    List<BaseSelectResult> btbShipmentWarehouseList();

    /**
     * EBMS发货仓下拉
     */
    List<BaseSelectResult> ebmsShipmentWarehouseList();

    /**
     * 比较两个实体属性值是否一致
     * @param b1 实体1
     * @param b2 实体2
     * @param ignoreNameList 忽略比较的字段名称集合
     * @param <T>
     * @return
     */
    <T> Boolean compareBean(T b1, T b2, List<String> ignoreNameList);

    /**
     * 生成物流单费用更新记录
     * @param insertRecord
     * @param oldFee
     */
    void generateLogisticsFeeRecord(LsLogisticsNoRecord insertRecord, LogisticsNoFeeParam oldFee);

}
