package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopifySettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtShopifySettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopifySettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopifySettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Shopify小平台结算报告;(LT_SHOPIFY_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-23
 */
@Mapper
public interface LtShopifySettlementReportMapper extends BaseMapper<LtShopifySettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtShopifySettlementReportResult> selectByPage(IPage<LtShopifySettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtShopifySettlementReport> wrapper);


    List<LtShopifySettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtShopifySettlementReport> wrapper);

    List<LtShopifySettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtShopifySettlementReport> wrapper);


    List<LtShopifySettlementReportResult> mergeShopifySettleNallocStruct(@Param("param") LtShopifySettlementReportParam paramCondition);

    int insertStructure(@Param("param") LtShopifySettlementReportParam paramCondition);

    int updatePeopleCostZero(@Param("param") LtShopifySettlementReportParam paramCondition);
}