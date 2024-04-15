package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApplicationRecordParam;

import javax.servlet.http.HttpServletResponse;

public interface IStockApplicationRecordService  extends IService<StockPurchaseOrders> {

    ResponseData queryListPage(StockApplicationRecordParam param);

    void exportExcel(HttpServletResponse response, StockApplicationRecordParam param) throws Exception ;

    void exportExcelAnalysis(HttpServletResponse response,StockApplicationRecordParam param) throws Exception;
}
