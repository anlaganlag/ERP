package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LtAliexpressSettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAliexpressSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_ALIEXPRESS_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtAliexpressSettlementReportMapper extends BaseMapper<LtAliexpressSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtAliexpressSettlementReportResult> selectByPage(IPage<LtAliexpressSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtAliexpressSettlementReport> wrapper);

    List<LtAliexpressSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtAliexpressSettlementReport> wrapper);

    List<LtAliexpressSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtAliexpressSettlementReport> wrapper);

    void mergePeople(@Param("period") String period);

    int insertStructure(@Param("param") LtAliexpressSettlementReportParam paramCondition);
}