package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NoStationAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoStationAllocationResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 无需分摊站内费用表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface INoStationAllocationService extends IService<NoStationAllocation> {

    PageResult<NoStationAllocationResult> findPageBySpec(NoStationAllocationParam param);

    List<NoStationAllocationResult> export(NoStationAllocationParam param);

    void confirm(NoStationAllocationParam param);

    ResponseData confirmBatch(NoStationAllocationParam param);

    ResponseData afreshCost(NoStationAllocationParam param) throws ParseException;

    ResponseData refreshCost() throws ParseException;

    NoStationAllocationResult getQuantity(NoStationAllocationParam param);

    List<SettlementDetailUsdResult> getExitSkuStationAllocation(SettlementDetailUsdParam param);

    void updateAmountOrInsert(SettlementDetailUsd usd);
}
