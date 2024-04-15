package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockParameterResult;

import java.util.List;

/**
 * <p>
 * 备货通用参数 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface IStockParameterService extends IService<StockParameter> {

    List<StockParameterResult> findPageBySpec(StockParameterParam param);


    ResponseData update(List<StockParameterParam> params);

}
