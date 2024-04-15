package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtWalmartSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtWalmartSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtWalmartSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtWalmartSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_WALMART_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtWalmartSettlementReportMapper extends BaseMapper<LtWalmartSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtWalmartSettlementReportResult> selectByPage(IPage<LtWalmartSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtWalmartSettlementReport> wrapper);

    List<LtWalmartSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtWalmartSettlementReport> wrapper);

    List<LtWalmartSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtWalmartSettlementReport> wrapper);


    List<LtWalmartSettlementReportResult> mergeWalmartSettleNallocStruct(@Param("param") LtWalmartSettlementReportParam paramCondition);

    int insertStructure(@Param("param") LtWalmartSettlementReportParam paramCondition);

    int updatePeopleCostZero(@Param("param") LtWalmartSettlementReportParam paramCondition);
}