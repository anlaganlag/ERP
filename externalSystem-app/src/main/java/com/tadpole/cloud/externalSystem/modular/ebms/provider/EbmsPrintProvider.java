package com.tadpole.cloud.externalSystem.modular.ebms.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.EbmsBaseApi;
import com.tadpole.cloud.externalSystem.api.ebms.EbmsPrintApi;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.PrintKfyLabelParam;
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
public class EbmsPrintProvider implements EbmsPrintApi {

    @Autowired
    private IEbmsService ebmsService;

    @Override
    public ResponseData printKfyLabel(List<PrintKfyLabelParam> params) {
        return ebmsService.printKfyLabel(params);
    }

}
