package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDaySummary;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDaySummaryParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 安全天数概要表 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
public interface SaftyDaySummaryMapper extends BaseMapper<SaftyDaySummary> {

    Page<SaftyDaySummaryResultVO> summaryList(@Param("page")  Page page, @Param("param") SaftyDaySummaryParam param);
    Page<SaftyDaySummaryResultVO> summaryList2(@Param("page")  Page page,  @Param("param") SaftyDaySummaryParam param);

    List<SaftyDaySummaryResult> exportExcel(@Param("param") SaftyDaySummaryParam param);

}
