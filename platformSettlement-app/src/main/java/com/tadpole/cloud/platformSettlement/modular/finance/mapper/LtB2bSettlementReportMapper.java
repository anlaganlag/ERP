package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_B2B_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-25
 */
@Mapper
public interface LtB2bSettlementReportMapper extends BaseMapper<LtB2bSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtB2bSettlementReportResult> selectByPage(IPage<LtB2bSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtB2bSettlementReport> wrapper);


    List<LtB2bSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtB2bSettlementReport> wrapper);

    List<LtB2bSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtB2bSettlementReport> wrapper);

    void mergePeople(@Param("period") String period);

    int insertStructure(@Param("param") LtB2bSettlementReportParam paramCondition);
}