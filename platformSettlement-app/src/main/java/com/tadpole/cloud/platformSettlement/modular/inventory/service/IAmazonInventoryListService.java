package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.AmazonInventoryListParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.AmazonInventoryTotal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 庫存列表3.0 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-12-20
 */
public interface IAmazonInventoryListService extends IService<AmazonInventoryList> {
   /**
    * 刷新表数据
    * @author AmteMa
    * @date 2022/12/20
    */
    void afreshCount();

    PageTotalResult<AmazonInventoryList, AmazonInventoryTotal>findPageBySpec(AmazonInventoryListParam param);

    List<AmazonInventoryList> export(AmazonInventoryListParam param);
}
