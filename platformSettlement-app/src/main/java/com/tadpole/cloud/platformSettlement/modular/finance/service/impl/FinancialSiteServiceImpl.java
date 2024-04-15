package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialSite;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbAmazonMarketplace;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.FinancialSiteMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialSiteParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialSiteService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 财务站点信息 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@Service
public class FinancialSiteServiceImpl extends ServiceImpl<FinancialSiteMapper, FinancialSite> implements IFinancialSiteService {

  @DataSource(name = "finance")
  @Override
  public PageResult<FinancialSiteResult> findPageBySpec(FinancialSiteParam param) {
    Page pageContext = param.getPageContext();

    IPage<FinancialSiteResult> page = this.baseMapper.findPageBySpec(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "finance")
  @Override
  public List<FinancialSiteResult> exportFinancialSiteList(FinancialSiteParam param) {

    List<FinancialSiteResult> page = this.baseMapper.exportFinancialSiteList(param);
    return page;
  }

  @DataSource(name = "EBMS")
  @Override
  public List<TbAmazonMarketplace> findSiteEbms() {
    return this.baseMapper.findSiteEbms();
  }

  @DataSource(name = "finance")
  @Override
  public void autoAnalysis(List<TbAmazonMarketplace> list) {
    for (TbAmazonMarketplace tbAmazonMarketplace : list) {
      this.baseMapper.autoAnalysis(tbAmazonMarketplace);
    }
  }

  @DataSource(name = "finance")
  @Override
  public List<FinancialSiteResult> getPlatform() {
    return this.baseMapper.getPlatform();
  }

  @DataSource(name = "finance")
  @Override
  public List<FinancialSiteResult> getSite() {
    return this.baseMapper.getSite();
  }

  @DataSource(name = "finance")
  @Override
  public void updateSite(FinancialSiteParam param) {
    UpdateWrapper<FinancialSite> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .set("ORIGINAL_CURRENCY", param.getOriginalCurrency()).set("TAX_RATE", param.getTaxRate());

    this.baseMapper.update(null, updateWrapper);
  }
}
