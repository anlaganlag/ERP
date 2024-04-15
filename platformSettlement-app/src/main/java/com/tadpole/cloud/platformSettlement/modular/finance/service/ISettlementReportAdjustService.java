package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportExportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 结算报告 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISettlementReportAdjustService extends IService<SettlementReportAdjust> {

    PageResult<SettlementReportAdjustResult> findPageBySpec(SettlementReportAdjustParam param);

    ResponseData confirm(SettlementReportAdjustParam param);

    ResponseData confirmBatch(SettlementReportAdjustParam param);

    SettlementReportAdjustResult getQuantity(SettlementReportAdjustParam param);

    List<SettlementReportExportResult> export(SettlementReportAdjustParam param);

//    ResponseData refreshAmount(SettlementReportAdjustParam param);

//    ResponseData refreshReturnAmount();

    ResponseData importSettlementReportAdjust(MultipartFile file);
    ResponseData adjustReportByMerge(SettlementReportAdjustParam param);

    List<Map<String,String>> adjustRepeatDimension(SettlementReportAdjustParam param);

    @DataSource(name = "finance")
    ResponseData fillPeopleCost(SettlementReportAdjustParam param);

    ResponseData getFinalReportSql(SettlementReportAdjustParam param);

    ResponseData fillPeopleCostNew(SettlementReportAdjustParam param);

    ResponseData adjustReport(SettlementReportAdjustParam param);


    @DataSource(name = "finance")
    int updatePeopleCostZero(SettlementReportAdjustParam param);
}
