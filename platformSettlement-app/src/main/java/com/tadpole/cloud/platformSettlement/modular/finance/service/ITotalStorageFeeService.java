package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalStorageFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalStorageFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalStorageFeeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalStorageFeeTotal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 仓储费合计 数据来源 定期生成 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-10-14
 */
public interface ITotalStorageFeeService extends IService<TotalStorageFee> {

    PageTotalResult<TotalStorageFeeResult, TotalStorageFeeTotal> findPageBySpec(TotalStorageFeeParam param);

    void afreshCount(String durDate);

    List<TotalStorageFee> export(TotalStorageFeeParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData pushStorageToManualAllocSQL(TotalStorageFeeParam param);



    ResponseData pushStorageToManualAlloc0(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData pushStorageToManualAlloc1(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData pushStorageToManualAlloc2(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData assignAllocLineStatus(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData fillAllocLineVales(StationManualAllocationParam param);



    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData getSkuStorageDetail(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData insertOverStorage(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    Double toInsertdetailSum(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    Integer noTicked(StationManualAllocationParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData pushStorageToManualAllocSql(StationManualAllocationParam param);





    @DataSource(name = "finance")
    void updateStorageSrc(StationManualAllocationParam param);
}
