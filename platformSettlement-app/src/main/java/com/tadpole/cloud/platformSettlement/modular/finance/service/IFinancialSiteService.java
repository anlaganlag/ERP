package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialSite;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbAmazonMarketplace;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialSiteParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 财务站点信息 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IFinancialSiteService extends IService<FinancialSite> {

  PageResult<FinancialSiteResult> findPageBySpec(FinancialSiteParam param);

  List<FinancialSiteResult> exportFinancialSiteList(FinancialSiteParam param);


  List<TbAmazonMarketplace> findSiteEbms();

  void autoAnalysis(List<TbAmazonMarketplace> list);

  List<FinancialSiteResult> getPlatform();

  List<FinancialSiteResult> getSite();

  
  void updateSite(FinancialSiteParam param);
}
