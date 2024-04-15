package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesTotal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 长期仓储费 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-10-12
 */
public interface ILongTermStorageFeeChargesService extends IService<LongTermStorageFeeCharges> {

    PageTotalResult<LongTermStorageFeeChargesResult, LongTermStorageFeeChargesTotal> findPageBySpec(LongTermStorageFeeChargesParam param);

    int deleteBatch(LongTermStorageFeeChargesParam param);

    int updateBatch(LongTermStorageFeeChargesParam param);

    List<LongTermStorageFeeCharges> export(LongTermStorageFeeChargesParam param);

    void afreshStorageFee();

    @DataSource(name = "finance")
    List<LongTermStorageFeeChargesResult> emailList();

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    void updateLatestDate(LongTermStorageFeeChargesParam param) throws ParseException;

    @DataSource(name = "finance")
    void fnskuFillLongListing(LongTermStorageFeeChargesParam param);
}
