package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销量目标 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
public interface SalesTargetMapper extends BaseMapper<SalesTarget> {

    List<SalesTargetResult> findPageBySpec(@Param("paramCondition") SalesTargetParam paramCondition);

    SalesTargetResult getQuantity(@Param("paramCondition") SalesTargetParam paramCondition);

    /**
     * 获取特定年份最大版本号
     */
    @Select("SELECT 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2)))) FROM  SALES_TARGET  WHERE YEAR = #{year}")
    String selectMaxVersionByYear(String year);

    @Select("SELECT case when MAX(VERSION) is not null then 'V'||TO_CHAR(MAX(TO_NUMBER(SUBSTR(VERSION,2))+1)) ELSE 'V1' end as VERSION FROM sales_target  WHERE year = #{year}")
    String getMaxVersion(String year);

    @Select("SELECT distinct shopNameSimple FROM  tbComshop  WHERE countryCode ='JP'")
    List<String> selectJpShopNameByEbms();

    @Select("SELECT confirm_status FROM sales_target  WHERE year = #{year} and version = #{version} and ROWNUM = 1")
    String isVersionConfirmed(String year,String version);

    @Select("SELECT * FROM  (SELECT version,confirm_status FROM ${salesTable}  WHERE year = ${year}   ORDER BY ID DESC ) WHERE ROWNUM = 1 ")
    Map<String,String> isVersionUnConfirmed(String salesTable,String year);
}
