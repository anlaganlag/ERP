package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.ProfitTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.ProfitTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.ProfitTargetResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 利润目标 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
public interface ProfitTargetMapper extends BaseMapper<ProfitTarget> {

    List<ProfitTargetResult> findPageBySpec(@Param("paramCondition") ProfitTargetParam paramCondition);

    ProfitTargetResult getQuantity(@Param("paramCondition") ProfitTargetParam paramCondition);

    /**
     * 获取特定年份最大版本号
     */
    @Select("SELECT case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) end as version FROM  PROFIT_TARGET WHERE YEAR = #{year}")
    String selectMaxVersionByYear(String year);
}
