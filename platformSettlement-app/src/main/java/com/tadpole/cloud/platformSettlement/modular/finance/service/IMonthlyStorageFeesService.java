package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonthlyStorageFees;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailListingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesTotal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 月储存费用 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-10-12
 */
public interface IMonthlyStorageFeesService extends IService<MonthlyStorageFees> {

    PageTotalResult<MonthlyStorageFeesResult, MonthlyStorageFeesTotal> findPageBySpec(MonthlyStorageFeesParam param);

    int deleteBatch(MonthlyStorageFeesParam param);

    int updateBatch(MonthlyStorageFeesParam param);

    List<MonthlyStorageFees> export(MonthlyStorageFeesParam param);

    void afreshStorageFee();

    @DataSource(name = "finance")
    void fnskuFillMonListing(MonthlyStorageFeesParam param);



    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    void updateLatestDate(MonthlyStorageFeesParam param) throws ParseException;

    @DataSource(name = "finance")
    List<MonthlyStorageFeesResult> emailList();
}
