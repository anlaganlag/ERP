package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtB2bAliSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtB2bAliSettlementReportResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ;(LT_B2B_ALI_SETTLEMENT_REPORT)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@Mapper
public interface LtB2bAliSettlementReportMapper extends BaseMapper<LtB2bAliSettlementReport> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<LtB2bAliSettlementReportResult> selectByPage(IPage<LtB2bAliSettlementReportResult> page, @Param(Constants.WRAPPER) Wrapper<LtB2bAliSettlementReport> wrapper);

    List<LtB2bAliSettlementReport> exportByPage(@Param(Constants.WRAPPER) Wrapper<LtB2bAliSettlementReport> wrapper);

}