package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportCheckParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportCheckResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* AZ结算报告审核 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface SettlementReportCheckMapper extends BaseMapper<SettlementReportCheck> {
    /**
     * AZ结算报告审核列表
     * @param page
     * @param paramCondition
     * @return
     */
    Page<SettlementReportCheckResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SettlementReportCheckParam paramCondition);

    /**
     * 汇款银行下拉
     * @return
     */
    List<SettlementReportCheckResult> bankList();

    /**
     * 导出AZ结算报告审核列表
     * @param paramCondition
     * @return
     */
    List<SettlementReportCheckResult> exportSettlementReportCheckList( @Param("paramCondition") SettlementReportCheckParam paramCondition);

    /**
     * 删除对应的Settlement/Data Range汇总解析数据
     * @param paramCondition
     */
    void deleteData(@Param("paramCondition") SettlementReportCheckParam paramCondition);

    /**
     * 删除对应的Settlement/Data Range明细解析数据
     * @param paramCondition
     */
    void deleteDataDetail(@Param("paramCondition") SettlementReportCheckParam paramCondition);
}
