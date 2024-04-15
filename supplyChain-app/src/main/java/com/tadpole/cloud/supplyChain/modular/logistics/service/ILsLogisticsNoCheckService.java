package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoCheck;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport0Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport1Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 物流单对账 服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
public interface ILsLogisticsNoCheckService extends IService<LsLogisticsNoCheck> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(LsLogisticsNoCheckParam param);

    /**
     * 分页查询列表合计
     * @param param
     * @return
     */
    ResponseData queryPageTotal(LsLogisticsNoCheckParam param);

    /**
     * 导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 批量锁定/解锁
     * @param param
     * @return
     */
    ResponseData batchLock(LsLogisticsNoCheckLockParam param);

    /**
     * 批量同步锁定/解锁EBMS
     * @param shipmentNumList
     * @param lockStatus
     * @return
     */
    void batchLockEbms(List<String> shipmentNumList, String lockStatus);

    /**
     * 批量对账完成
     * @param param
     * @return
     */
    ResponseData batchCheck(LsLogisticsNoCheckLockParam param);

    /**
     * 导出预估
     * @param param
     * @return
     */
    List<LsLogisticsNoCheckExport0Result> export(LsLogisticsNoCheckParam param);

    /**
     * 导出实际
     * @param param
     * @return
     */
    List<LsLogisticsNoCheckExport1Result> exportDetail(LsLogisticsNoCheckParam param);

    /**
     * 编辑保存
     * @param param
     * @return
     */
    ResponseData edit(LsLogisticsNoCheckDetailParam param);

    /**
     * 生成物流费
     * @param param
     * @return
     */
    ResponseData generateLogisticsFee(List<LsLogisticsNoCheckDetailParam> param);

    /**
     * 接收EBMS物流单数据
     * @param params
     * @return
     */
    ResponseData receiveLogisticsCheck(List<EbmsLogisticsCheckParam> params);

    /**
     * EBMS删除物流单
     * @param params
     * @return
     */
    ResponseData deleteEbmsCheck(List<LogisticsSettlementDetailParam> params);

    /**
     * 定时获取EBMS物流跟踪表更新物流对账签收日期
     * @return
     */
    ResponseData refreshSignDate();

    /**
     * 获取EBMS物流跟踪表的签收日期
     * @param param
     * @return
     */
    List<LsLogisticsNoCheck> getEbmsSignDate(List<LsLogisticsNoCheck> param);

    /**
     * 物流对账单号校验
     * @param param
     * @return
     */
    ResponseData validateCheckOrder(LsLogisticsNoCheckDetailParam param);

}
