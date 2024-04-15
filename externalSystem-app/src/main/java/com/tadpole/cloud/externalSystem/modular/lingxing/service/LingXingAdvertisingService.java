package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.Keywords;

/**
 * @author: ty
 * @description: 财务Service类
 * @date: 2022/4/25
 */
public interface LingXingAdvertisingService {
    /**
     * 查询广告管理 - 广告组
     * @return
     */
    ResponseData advertisingGroup(AdvertisingGroupReq req) throws Exception;

    /**
     * 查询广告管理 - 用戶搜索詞
     * @return
     */
    ResponseData userSearchWords(UserSearchWordsReq req) throws Exception;

    /**
     * 查询广告管理 - 商品定位
     * @return
     */
    ResponseData commodityPosition(CommodityPositionReq req) throws Exception;

    /**
     * 查询广告管理 - 商品定位
     * @return
     */
    ResponseData advertisingActivity(AdvertisingActivityReq req) throws Exception;

    /**
     * 查询广告管理 - 操作日志
     * @return
     */
    ResponseData operationLog(OperationLogReq req) throws Exception;

    /**
     * 查询广告管理 - 广告
     * @return
     */
    ResponseData advertising(AdvertisingReq req) throws Exception;

    /**
     * 查询广告管理 - 关键词
     * @return
     */
    ResponseData keywords(KeywordsReq req) throws Exception;
    ResponseData insert(Keywords keywords) ;


//    public void test(AdvertisingGroupReq req, LingXingBaseRespData Sellers) throws ParseException;
}
