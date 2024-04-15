package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialSite;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbAmazonMarketplace;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialSiteParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 财务站点信息 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface FinancialSiteMapper extends BaseMapper<FinancialSite> {

    Page<FinancialSiteResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") FinancialSiteParam paramCondition);

    List<FinancialSiteResult> exportFinancialSiteList( @Param("paramCondition") FinancialSiteParam paramCondition);


    List<TbAmazonMarketplace> findSiteEbms();

    void autoAnalysis(@Param("paramCondition") TbAmazonMarketplace paramCondition);


    List<FinancialSiteResult> getPlatform();

    List<FinancialSiteResult> getSite();
}
