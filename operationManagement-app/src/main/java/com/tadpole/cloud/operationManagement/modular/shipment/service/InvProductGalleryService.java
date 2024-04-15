package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.InvProductGallery;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ListingSelectParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ListingSelectResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  AmazSKU服务类
 * </p>
 *
 * @author lsy
 * @since 2023-02-03
 */
public interface InvProductGalleryService extends IService<InvProductGallery> {

    /**
     * 多条件查询sku
     * @param invProductGalleryParam
     * @return
     */
    List<InvProductGallery> querySku(InvProductGalleryParam invProductGalleryParam);

    @DataSource(name = "basicdata")
    List<InvProductGallery> querySkuDatalimit(InvProductGalleryParam param);

    /**
     * SKU表通用下拉接口 账号，站点，物料，asin，sku
     * @param param
     * @return
     */
    ListingSelectResult listingSelect(ListingSelectParam param);

    LoginUser getLoginUserInfo();

}
