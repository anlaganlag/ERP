package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesVolumeTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesVolumeTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesVolumeTargetResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 销售额目标 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
public interface SalesVolumeTargetMapper extends BaseMapper<SalesVolumeTarget> {

    List<SalesVolumeTargetResult> findPageBySpec(@Param("paramCondition") SalesVolumeTargetParam paramCondition);

    SalesVolumeTargetResult getQuantity(@Param("paramCondition") SalesVolumeTargetParam paramCondition);

    /**
     * 获取特定年份最大版本号
     */
    @Select("SELECT 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) FROM  SALES_VOLUME_TARGET WHERE YEAR = #{year}")
    String selectMaxVersionByYear(String year);

}
