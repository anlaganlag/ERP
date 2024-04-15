package com.tadpole.cloud.externalSystem.api.ebms;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.PrintKfyLabelParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: ty
 * @description: EBMS基础信息API
 * @date: 2023/4/17
 */
@RequestMapping("/ebmsPrintApi")
public interface EbmsPrintApi {

    /**
     * 打印开发样条码
     */
    @RequestMapping(value = "/printKfyLabel", method = RequestMethod.POST)
    ResponseData printKfyLabel(@RequestBody List<PrintKfyLabelParam> params);

}
