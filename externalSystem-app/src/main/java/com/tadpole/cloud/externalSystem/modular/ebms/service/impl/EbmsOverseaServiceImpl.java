package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.ebms.constants.EBMSUrlConstants;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsOverseaService;
import com.tadpole.cloud.externalSystem.modular.ebms.utils.EbmsHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 海外仓调用EBMS接口
 * @date: 2023/4/3
 */
@Service
@Slf4j
public class EbmsOverseaServiceImpl implements IEbmsOverseaService {

    @Autowired
    private EbmsHttpUtil ebmsHttpUtil;

    @Override
    public ResponseData getAllBoxInfo(){
        return ebmsHttpUtil.requestEbmsGet(EBMSUrlConstants.GET_ALL_BOX_INFO);
    }

    @Override
    public ResponseData createShipmentList(EbmsOverseasOutWarehouseParam outParam){
        String param = JSON.toJSONString(outParam);
        return ebmsHttpUtil.requestEbmsPost(EBMSUrlConstants.CREATE_SHIPMENT_LIST, param);
    }
}
