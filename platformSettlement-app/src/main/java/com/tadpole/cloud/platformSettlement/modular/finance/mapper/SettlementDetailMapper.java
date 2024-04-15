package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* settlement明细数据 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface SettlementDetailMapper extends BaseMapper<SettlementDetail> {
    List<SettlementResult> getSettlementList(SettlementParam param);

    /**
     * 根据settlementId获取settlement明细数据
     * @param settlementId
     * @return
     */
    List<SettlementDetail> getSettlementDetailList(String settlementId);

    /**
     * 根据settlementId刷新财务分类
     * @param paramCondition
     */
    void refreshFinancialClass(@Param("paramCondition") SettlementDetailParam paramCondition);

    /**
     * 根据时间和settlementId汇总金额
     * @param param
     * @return
     */
    SettlementDetail getSettlementMoney(@Param("paramCondition") SettlementDetail param);

    /**
     * 根据settlementId更新settlement主表状态为2：已刷财务分类
     * @param paramCondition
     */
    void updateStatus(@Param("paramCondition") SettlementDetailParam paramCondition);

    /**
     * 未刷成功的财务分类写入财务分类表
     * @param paramCondition
     */
    void InsertFinancialClass(@Param("paramCondition") SettlementDetailParam paramCondition);

    /**
     * 重新刷新增的财务分类到结算明细
     * @param paramCondition
     */
    void FillFinancialClass(@Param("paramCondition") SettlementDetailParam paramCondition);

    /**
     * 非Amazon平台费
     * @param param
     * @return
     */
    SettlementDetail getNotAmazonFee(@Param("paramCondition") SettlementDetail param);

    List<SettlementDetail> getPlatformAmazonFee(@Param("paramCondition") SettlementDetail param);

    List<DetailResult> refreshFailure(@Param("paramCondition") StatementIncomeParam param);

    /**
     * 更新Settlement源报告站点为空的站点
     * @param param
     */
    void updateSite(@Param("paramCondition") SettlementReportCheck param);
}
