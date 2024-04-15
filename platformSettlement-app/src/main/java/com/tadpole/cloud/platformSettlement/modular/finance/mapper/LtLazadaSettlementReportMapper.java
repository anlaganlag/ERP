package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtLazadaSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtLazadaSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtLazadaSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_LAZADA_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtLazadaSettlementReportMapper extends BaseMapper<LtLazadaSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtLazadaSettlementReportResult> selectByPage(IPage<LtLazadaSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtLazadaSettlementReport> wrapper);


    List<LtLazadaSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtLazadaSettlementReport> wrapper);

    List<LtLazadaSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtLazadaSettlementReport> wrapper);

    void mergePeople(@Param("period") String period);

    int insertStructure(@Param("param") LtLazadaSettlementReportParam paramCondition);
}