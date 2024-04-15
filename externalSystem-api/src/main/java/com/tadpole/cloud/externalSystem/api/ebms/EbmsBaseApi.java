package com.tadpole.cloud.externalSystem.api.ebms;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: ty
 * @description: EBMS基础信息API
 * @date: 2023/4/17
 */
@RequestMapping("/ebmsBaseApi")
public interface EbmsBaseApi {

    /**
     * 根据工号获取EBMS员工信息
     * @param account 工号
     * @return
     */
    @RequestMapping(value = "/getUserInfoByAccount", method = RequestMethod.POST)
    EbmsUserInfo getUserInfoByAccount(@RequestParam String account);

    /**
     * 获取销售品牌
     * @return
     */
    @RequestMapping(value = "/getSaleBrand", method = RequestMethod.GET)
    ResponseData getSaleBrand();

    /**
     * 获取店铺列表
     * @return
     */
    @RequestMapping(value = "/getShopList", method = RequestMethod.GET)
    List<TbcomshopResult> getShopList(TbcomshopParam param);

    /**
     * 获取平台列表
     * @return
     */
    @RequestMapping(value = "/getPlatformList", method = RequestMethod.GET)
    List<String> getPlatformList();
}
