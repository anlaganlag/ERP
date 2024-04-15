package com.tadpole.cloud.externalSystem.api.ebms;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsShopDataDownTaskParam;
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
@RequestMapping("/comShopApi")
public interface ComShopApi {

    /**
     * 获取店铺数据自动下载 报告对象list
     * @return
     */
    @RequestMapping(value = "/getSourceDataObjList", method = RequestMethod.GET)
    List<TbDwSourceData> getSourceDataObjList();


    /**
     * 创建EBMS店铺下载任务
     * @param dataDownParam
     * @return
     */
    @RequestMapping(value = "/syncShopDwDataTask", method = RequestMethod.POST)
    ResponseData syncShopDwDataTask(@RequestBody EbmsShopDataDownTaskParam dataDownParam);


    @RequestMapping(value = "/getMarketplaceIdByPlatformSite", method = RequestMethod.GET)
    ResponseData getMarketplaceIdByPlatformSite(@RequestParam(required = false) String platform, @RequestParam(required = false) String site);

}
