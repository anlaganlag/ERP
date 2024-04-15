package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 结算报告 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISettlementReportService extends IService<SettlementReport> {

    PageResult<SettlementReportResult> findPageBySpec(SettlementReportParam param);

    void confirm(SettlementReportParam param);

    ResponseData confirmBatch(SettlementReportParam param);

    SettlementReportResult getQuantity(SettlementReportParam param);

    List<SettlementReportResult> export(SettlementReportParam param);

    ResponseData refreshAmount(SettlementReportParam param);

    ResponseData refreshReturnAmount();

    ResponseData shopSiteToReport(SettlementReportParam param) throws Exception;

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    ResponseData shopSiteToReport1(SettlementReportParam param) throws Exception;
}
