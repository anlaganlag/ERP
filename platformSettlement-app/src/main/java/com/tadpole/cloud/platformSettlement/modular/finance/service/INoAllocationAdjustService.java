package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NoAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 无需分摊调整表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface INoAllocationAdjustService extends IService<NoAllocationAdjust> {
    PageResult<NoAllocationAdjustResult> findPageBySpec(NoAllocationAdjustParam param);

    List<NoAllocationAdjustResult> export(NoAllocationAdjustParam param);

    void confirm(NoAllocationAdjustParam param);

    ResponseData confirmBatch(NoAllocationAdjustParam param);

    ResponseData afreshCost(NoAllocationAdjustParam param);

    ResponseData refreshCost() throws ParseException;

    NoAllocationAdjustResult getQuantity(NoAllocationAdjustParam param);

    List<SettlementDetailUsdResult> getExitSkuAdjust(SettlementDetailUsdParam param);

    void updateAmountOrInsert(SettlementDetailUsd usd);
}
