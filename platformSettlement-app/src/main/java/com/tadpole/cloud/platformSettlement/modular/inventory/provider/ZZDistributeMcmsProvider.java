package com.tadpole.cloud.platformSettlement.modular.inventory.provider;

import com.tadpole.cloud.platformSettlement.api.inventory.ZZDistributeMcmsApi;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IZZDistributeMcmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: K3物料分配入库服务提供者
 * @date: 2023/4/14
 */
@RestController
public class ZZDistributeMcmsProvider implements ZZDistributeMcmsApi {

    @Autowired
    private IZZDistributeMcmsService zZDistributeMcmsService;

    @Override
    public void add(ZZDistributeMcms param) {
        zZDistributeMcmsService.add(param);
    }

    @Override
    public void saveMatBatch(List<ZZDistributeMcms> param) {
        zZDistributeMcmsService.saveMatBatch(param);
    }
}
