package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationAutoAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationAutoAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationAutoAllocationResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 站内自动分摊表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface IStationAutoAllocationService extends IService<StationAutoAllocation> {

    PageResult<StationAutoAllocationResult> findPageBySpec(StationAutoAllocationParam param);

    List<StationAutoAllocationResult> export(StationAutoAllocationParam param);

    ResponseData confirm(StationAutoAllocationParam param);

    void confirmManual(List<StationAutoAllocation> params);

    ResponseData confirmBatch(StationAutoAllocationParam param);

    StationAutoAllocationResult getQuantity(StationAutoAllocationParam param);

    List<SettlementDetailUsdResult> getExitSkuMoney(SettlementDetailUsdParam param);
}
