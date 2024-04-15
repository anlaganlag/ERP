package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockMonitorParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockMonitorResult;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;

import java.util.List;

/**
 * <p>
 * 备货监控 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-21
 */
public interface IStockMonitorService extends IService<StockMonitor> {

    List<StockMonitorResult> findPageBySpec(StockMonitorParam param);


    ResponseData CreateData(List<StockMonitor> stockMonitorHeadList);

    void deleteByDept(String department);

     List<StockMonitor> getErpData(String department);

    List<StockMonitor> getCurPurQty(String department);

    ResponseData megerCalc(List<StockMonitor> stockMonitorHeadList, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData);
    ResponseData megerCalc2(List<StockMonitor> stockMonitorHeadList, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData);
    void fillTurnOverDay();

    /**
     * 按部门合并计算统计
     * @param stockMonitorDept
     * @param erpData
     * @param curPurQtyData
     * @return
     */
    ResponseData megerCalcDept(StockMonitor stockMonitorDept, List<StockMonitor> erpData, List<StockMonitor> curPurQtyData);
}
