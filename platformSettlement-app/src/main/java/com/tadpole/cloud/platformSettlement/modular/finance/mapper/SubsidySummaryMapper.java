package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SubsidySummary;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SubsidySummaryParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 补贴汇总表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface SubsidySummaryMapper extends BaseMapper<SubsidySummary> {

    Page<SubsidySummaryResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") SubsidySummaryParam param);

    List<SubsidySummaryResult> queryList(@Param("paramCondition") SubsidySummaryParam param);

    void updateToReport(@Param("paramCondition") SubsidySummary ss);

    List<SubsidySummaryParam> queryConfirm(@Param("paramCondition") SubsidySummaryParam param);

    SubsidySummaryResult getQuantity(@Param("paramCondition") SubsidySummaryParam param);

    void deleteBatch(@Param("paramCondition")  SubsidySummaryParam param);

    void updateToReportBatch(@Param("paramCondition") SubsidySummaryParam param);
}
