package com.tadpole.cloud.externalSystem.api.saihu.model;

import cn.stylefeng.guns.cloud.system.api.model.SaihuUser;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: ty
 * @description: 赛狐销售API
 * @date: 2024/2/19
 */
@RequestMapping("/saiHuSellApi")
public interface SaiHuSellApi {

    /**
     * 赛狐在线产品
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/productList", method = RequestMethod.POST)
    SaiHuBaseResult productList(@RequestBody SaiHuProductParam param) throws Exception;

    @RequestMapping(value = "/getAccountList", method = RequestMethod.POST)
    List<SaihuUser> getAccountList();

    @RequestMapping(value = "/changeAccountStatus", method = RequestMethod.POST)
    void changeAccountStatus(@RequestBody SaihuUser saihuUser);

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    void createAccount(@RequestBody SaihuUser saihuUser);
}
