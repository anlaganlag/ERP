package com.tadpole.cloud.externalSystem.modular.saihu.service.impl;

import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.modular.saihu.constants.SaihuUrlConstants;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaleService;
import com.tadpole.cloud.externalSystem.modular.saihu.utils.SaihuReqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 赛狐销售服务实现类
 * @date: 2024/2/19
 */
@Service
@Slf4j
public class SaleServiceImpl implements ISaleService {

    @Autowired
    private SaihuReqUtil saihuReqUtil;

    @Override
    public SaiHuBaseResult productList(SaiHuProductParam param) throws Exception {
        return saihuReqUtil.doPostReq(param, SaihuUrlConstants.PRODUCT);
    }
}
