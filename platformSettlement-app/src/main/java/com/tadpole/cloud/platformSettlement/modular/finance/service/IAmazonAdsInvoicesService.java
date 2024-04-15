package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonAdsInvoices;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.AmazonAdsInvoicesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.*;


import java.util.List;

/**
 * <p>
 * Amazon广告费用账单 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-07-13
 */
public interface IAmazonAdsInvoicesService extends IService<AmazonAdsInvoices> {

    PageTotalResult<AmazonAdsInvoicesResult, AmazonAdsInvoices> findPageBySpec(AmazonAdsInvoicesParam param);

    List<AmazonAdsInvoicesResult> export(AmazonAdsInvoicesParam param);

    void afreshStorageFee();
}
