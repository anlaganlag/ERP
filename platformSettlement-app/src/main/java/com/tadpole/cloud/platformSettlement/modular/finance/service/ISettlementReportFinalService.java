package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportFinal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportFinalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportFinalResult;

import java.util.List;

/**
 * <p>
 * 结算报告 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISettlementReportFinalService extends IService<SettlementReportFinal> {

    PageResult<SettlementReportFinalResult> findPageBySpec(SettlementReportFinalParam param);

    void confirm(SettlementReportFinalParam param);

    @DataSource(name = "finance")
    ResponseData getFinalReport(SettlementReportFinalParam param);

    ResponseData confirmBatch(SettlementReportFinalParam param);

    SettlementReportFinalResult getQuantity(SettlementReportFinalParam param);

    List<SettlementReportFinalResult> export(SettlementReportFinalParam param);

//    ResponseData refreshAmount(SettlementReportFinalParam param);

    ResponseData refreshReturnAmount();



}
