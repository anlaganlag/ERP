package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxList;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.BaseTaxListMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxListParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxListResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseTaxListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础信息-税号列表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Service
public class BaseTaxListServiceImpl extends ServiceImpl<BaseTaxListMapper, BaseTaxList> implements IBaseTaxListService {

    @Resource
    private BaseTaxListMapper mapper;

    @Override
    @DataSource(name = "finance")
    public List<BaseTaxListResult> exportExcel(BaseTaxListParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery(1_000_000, 1, "ASC", "ID");
        Page pageContext = PageFactory.createPage(page);
        Page<BaseTaxListResult> pageResult = this.baseMapper.queryListPage(pageContext, param);
        //返回查询结果
        return pageResult.getRecords();
    }

    @Override
    @DataSource(name = "finance")
    public Page<BaseTaxListResult> queryListPage(BaseTaxListParam param) {
        Page pageContext = param.getPageContext();
        return this.baseMapper.queryListPage(pageContext, param);
    }


    @Override
    @DataSource(name = "stocking")
    public List<BaseTaxList> queryEbmsData() {
        List<Map<String, String>> maps = this.baseMapper.queryTaxNums();
        List<BaseTaxList> btList = new ArrayList<>();
        for (Map<String, String> map : maps) {
            BaseTaxList obj = new BaseTaxList();

            String[] shopName = map.get("SHOPNAME").split("_");
            String taxnOverseas = map.get("TAXNOVERSEAS");//海外税号
            String eleplat = shopName[0];//平台
            String account = shopName[1];//账号
            String country = shopName[2];//站点||国家

            obj.setCountry(country);
            obj.setAccount(account);
            obj.setEleplatformname(eleplat);
            obj.setTaxId(taxnOverseas);
            obj.setIsTaxId(BigDecimal.valueOf(ObjectUtil.isNotEmpty(taxnOverseas) ? 1 : 0));//是否有税号
            obj.setTaxIdStatus(map.get("TAXNSTATE"));//税号状态
            btList.add(obj);
        }
        return btList;
    }


    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public Boolean RefreshData(List<BaseTaxList> ebmsData) {
        this.remove(new QueryWrapper<>());
        return this.saveBatch(ebmsData);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
