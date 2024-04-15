package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopeeSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopeeSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ManualAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopeeSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtRakutenSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopeeSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_SHOPEE_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtShopeeSettlementReportMapper extends BaseMapper<LtShopeeSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtShopeeSettlementReportResult> selectByPage(IPage<LtShopeeSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtShopeeSettlementReport> wrapper);

    List<LtShopeeSettlementReportResult> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtShopeeSettlementReport> wrapper);

    List<LtShopeeSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtShopeeSettlementReport> wrapper);


    void mergePeople(@Param("period") String period);

    int insertStructure(@Param("param") LtShopeeSettlementReportParam paramCondition);
}