package com.tadpole.cloud.operationManagement.modular.shopEntity.Provider;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.ShopEntityApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.SiteApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.QueryApplySiteParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbPlatformAccoSiteCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class SiteProvider implements SiteApi {

    @Resource
    private TbPlatformAccoSiteCodeService tbPlatformAccoSiteCodeService ;





    @Override
    public List<String> siteList() {
        QueryApplySiteParam queryApplySiteParam = new QueryApplySiteParam();
        return tbPlatformAccoSiteCodeService.queryApplySite(queryApplySiteParam);
    }

}
