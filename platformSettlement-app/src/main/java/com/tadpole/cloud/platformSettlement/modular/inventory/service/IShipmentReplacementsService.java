package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ShipmentReplacements;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ShipmentReplacementsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ShipmentReplacementsResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ShipmentReplacementsTotal;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-06-08
 */
public interface IShipmentReplacementsService extends IService<ShipmentReplacements> {
    PageTotalResult<ShipmentReplacementsResult, ShipmentReplacementsTotal> findPageBySpec(ShipmentReplacementsParam param);
    List<ShipmentReplacements> export(ShipmentReplacementsParam param);
}
