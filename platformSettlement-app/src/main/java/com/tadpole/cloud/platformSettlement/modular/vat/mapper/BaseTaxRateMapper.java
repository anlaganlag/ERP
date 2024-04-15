package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxRate;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxRateResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 * 基础信息-税率表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface BaseTaxRateMapper extends BaseMapper<BaseTaxRate> {

    Page<BaseTaxRateResult> queryListPage(@Param("page") Page page, @Param("paramCondition") BaseTaxRateParam paramCondition);

    @Select("SELECT count(1) FROM VAT_SALES_DETAIL t WHERE t.ACTIVITY_PERIOD = #{period} AND t.SYS_SITE = #{site} AND t.GENERATE_STATUS = 0")
    int queryNotGeneratedPeriod(String period,String site);

    @Select("SELECT MAX(TRUNC(TO_DATE(ACTIVITY_PERIOD||'-01','yyyy-mm-dd') ,'mm'))  FROM VAT_SALES_DETAIL WHERE SYS_SITE = #{site} AND GENERATE_STATUS = 1")
    Date queryGeneratedMaxPeriod(String site);

    //int queryIsGeneratedPeriod(String period,int isExists);
}
