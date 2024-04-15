package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAsinBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAsinBlacklistParam;

import java.util.List;

/**
 * <p>
 * ASIN黑名单 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-12-19
 */
public interface IStockAsinBlacklistService extends IService<StockAsinBlacklist> {

    PageResult<StockAsinBlacklist> asinBlacklist(StockAsinBlacklistParam param);

    List<StockAsinBlacklist> export(StockAsinBlacklistParam param);
}
