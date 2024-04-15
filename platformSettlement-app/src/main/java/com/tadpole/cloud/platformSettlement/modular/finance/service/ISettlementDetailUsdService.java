package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 结算单明细(美金) 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISettlementDetailUsdService extends IService<SettlementDetailUsd> {

    PageResult<SettlementDetailUsdResult> findPageBySpec(SettlementDetailUsdParam param);

    List<SettlementDetailUsdResult> export(SettlementDetailUsdParam param);

    List<SettlementDetail> getSettlementMoney(SettlementDetail param);

    ResponseData confirm(SettlementDetailUsdParam param) throws Exception;


    ResponseData unconfirm(SettlementDetailUsdParam param) throws Exception;

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData deleteAlloc(SettlementDetailUsdParam param) throws Exception;

    ResponseData confirmBatch(SettlementDetailUsdParam param) throws Exception;

    SettlementDetailUsdResult getQuantity(SettlementDetailUsdParam param);

    ResponseData afreshExchangeRate(SettlementDetailUsdParam param) throws Exception;

    void refreshExchangeRate() throws Exception;

}
