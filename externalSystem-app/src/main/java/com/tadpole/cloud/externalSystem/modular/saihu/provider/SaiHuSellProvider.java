package com.tadpole.cloud.externalSystem.modular.saihu.provider;

import cn.stylefeng.guns.cloud.system.api.model.SaihuUser;
import com.tadpole.cloud.externalSystem.api.saihu.model.SaiHuSellApi;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.modular.saihu.service.IAccountService;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: 赛狐销售服务提供者
 * @date: 2024/2/19
 */
@RestController
public class SaiHuSellProvider implements SaiHuSellApi {

    @Autowired
    private ISaleService saleService;

    @Autowired
    private IAccountService accountService;

    /**
     * 赛狐在线产品
     * @return
     */
    @Override
    public SaiHuBaseResult productList(SaiHuProductParam param) throws Exception {
        return saleService.productList(param);
    }

    @Override
    public List<SaihuUser> getAccountList() {
        return accountService.getAccountList();
    }

    @Override
    public void changeAccountStatus(SaihuUser saihuUser) {
        accountService.changeAccountStatus(saihuUser);
    }

    @Override
    public void createAccount(SaihuUser saihuUser) {
        accountService.createAccount(saihuUser);
    }
}
