package com.tadpole.cloud.externalSystem.modular.ebms.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.EbmsBaseApi;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsService;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcomshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: EBMS基础信息服务提供者
 * @date: 2023/4/17
 */
@RestController
public class EbmsBaseProvider implements EbmsBaseApi {

    @Autowired
    private IEbmsService ebmsService;

    @Autowired
    private ITbcomshopService service;


    @Override
    public EbmsUserInfo getUserInfoByAccount(String account) {
        return ebmsService.getUserInfoByAccount(account);
    }

    @Override
    public ResponseData getSaleBrand() {
        return ebmsService.getSaleBrand();
    }

    @Override
    public List<TbcomshopResult> getShopList(TbcomshopParam param) {
        return service.getShopList(param);
    }

    @Override
    public List<String> getPlatformList() {
        return service.getPlatformList();
    }
}
