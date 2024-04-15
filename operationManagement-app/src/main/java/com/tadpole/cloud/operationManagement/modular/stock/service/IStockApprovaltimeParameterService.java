package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockApprovaltimeParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApprovaltimeParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;

import java.util.List;

/**
 * <p>
 * 日常备货流程审核节点参数设置 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface IStockApprovaltimeParameterService extends IService<StockApprovaltimeParameter> {

    List<StockApprovaltimeParameterResult> findPageBySpec(StockApprovaltimeParameterParam param);



    ResponseData update(List<StockApprovaltimeParameterParam> param);

    StockApprovaltimeParameterResult findByAuditorCode(String yyry);
}
