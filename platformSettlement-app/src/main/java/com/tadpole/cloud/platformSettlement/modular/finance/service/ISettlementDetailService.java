package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
/**
 * <p>
 * settlement明细数据 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface ISettlementDetailService extends IService<SettlementDetail> {

    List<SettlementResult> getSettlementList(SettlementParam param);

    List<SettlementDetail> getSettlementDetailList(String settlementId);

    /**
     * 根据时间和settlementId汇总金额
     * @param param
     * @return
     */
    SettlementDetail getSettlementMoney(SettlementDetail param);

    /**
     * 非Amazon平台费
     * @param param
     * @return
     */
    SettlementDetail getNotAmazonFee(SettlementDetail param);

    List<SettlementDetail> getPlatformAmazonFee(SettlementDetail param);

    /**
     * 刷新settlement财务分类明细
     * @param paramCondition
     */
    void refreshFinancialClass(@Param("paramCondition") SettlementDetailParam paramCondition);

    void updateStatus(@Param("paramCondition") SettlementDetailParam paramCondition);

    void InsertFinancialClass(@Param("paramCondition") SettlementDetailParam paramCondition);

    List<DetailResult> refreshFailure(StatementIncomeParam param);

    /**
     * 更新Settlement源报告站点为空的站点
     * @param param
     */
    void updateSite(SettlementReportCheck param);
}
