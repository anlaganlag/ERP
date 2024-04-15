package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAreaBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAreaBlacklistParam;

import java.util.List;

/**
 * <p>
 * 区域黑名单 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-12-19
 */
public interface IStockAreaBlacklistService extends IService<StockAreaBlacklist> {

    public PageResult<StockAreaBlacklist> areaBlacklist(StockAreaBlacklistParam param);

    List<StockAreaBlacklist> export(StockAreaBlacklistParam param);

    ResponseData checkAreaBlacklist(List<SpecialApplyInfoV3Param> applyInfoV3ParamList);
}
