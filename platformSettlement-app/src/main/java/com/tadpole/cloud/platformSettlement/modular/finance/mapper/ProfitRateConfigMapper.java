package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ProfitRateConfig;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ProfitRateConfigParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ProfitRateConfigResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  利润率参数管理Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
public interface ProfitRateConfigMapper extends BaseMapper<ProfitRateConfig> {

    /**
     * 利润率参数管理
     * @param param
     * @return
     */
    Page<ProfitRateConfigResult> queryPage(@Param("page") Page page, @Param("param") ProfitRateConfigParam param);

    /**
     * 历史利润率查询列表
     * @param param
     * @return
     */
    Page<ProfitRateConfigResult> queryHistoryPage(@Param("page") Page page, @Param("param") ProfitRateConfigParam param);
}
