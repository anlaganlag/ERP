package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsWay;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsWayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsWayResult;

import java.util.List;

/**
 * <p>
 * 设置物流方式 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-09-13
 */
public interface ILogisticsWayService extends IService<LogisticsWay> {
    ResponseData queryListPage(LogisticsWayParam param);
    List<LogisticsWayResult> exportExcel(LogisticsWayParam param);
    ResponseData add(LogisticsWayParam param, String controllerName);
    ResponseData delete(List<Integer> idList, String controllerName);
    ResponseData edit(LogisticsWayParam param, String controllerName);
    ResponseData uploadExcel(List<LogisticsWayParam> paramList, String controllerName);
}
