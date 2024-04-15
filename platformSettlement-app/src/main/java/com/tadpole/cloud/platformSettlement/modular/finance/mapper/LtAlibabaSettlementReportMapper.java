package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAlibabaSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAlibabaSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAlibabaSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtAlibabaSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtB2bSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_ALIBABA_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-25
 */
@Mapper
public interface LtAlibabaSettlementReportMapper extends BaseMapper<LtAlibabaSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtAlibabaSettlementReportResult> selectByPage(IPage<LtAlibabaSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtAlibabaSettlementReport> wrapper);


    List<LtAlibabaSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtAlibabaSettlementReport> wrapper);

    List<LtAlibabaSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtAlibabaSettlementReport> wrapper);

    void mergePeople(@Param("period") String period);

    int insertStructure(@Param("param") LtAlibabaSettlementReportParam paramCondition);
}