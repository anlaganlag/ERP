package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtRakutenSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtRakutenSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtRakutenSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.DepartTeamProductTypeParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtRakutenSettlementReportParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_RAKUTEN_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtRakutenSettlementReportMapper extends BaseMapper<LtRakutenSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtRakutenSettlementReportResult> selectByPage(IPage<LtRakutenSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtRakutenSettlementReport> wrapper);


    List<LtRakutenSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtRakutenSettlementReport> wrapper);

    List<LtRakutenSettlementReportExport> downloadExport(@Param(Constants.WRAPPER) Wrapper<LtRakutenSettlementReport> wrapper);


    List<LtRakutenSettlementReportResult> mergeRakutenSettleNallocStruct(@Param("param") LtRakutenSettlementReportParam paramCondition);

    int updatePeopleCostZero(@Param("param") LtRakutenSettlementReportParam paramCondition);

    int insertStructure(@Param("param") LtRakutenSettlementReportParam paramCondition);

    List<DepartTeamProductTypeParam> getProductType(@Param("param") DepartTeamProductTypeParam param);


}