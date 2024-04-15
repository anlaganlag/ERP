package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.RecomSeasonRatio;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;

/**
 * <p>
 * 季节系数规则表-最细维度匹配 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-23
 */
public interface IRecomSeasonRatioService extends IService<RecomSeasonRatio> {
   RecomSeasonRatio findOneByArea(TeamVerif teamVerif);

    ResponseData findByPurchaseOrderId(PurchaseOrdersParam param);
}
