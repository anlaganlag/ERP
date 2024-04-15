package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising.*;
import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.Shop;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.*;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingAdvertisingService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingxingTaskUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: ty
 * @description: 财务Service实现类
 * @date: 2022/4/25
 */
@Slf4j
@Service
public class LingXingAdvertisingServiceImpl implements LingXingAdvertisingService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Autowired
    LingXingBaseInfoServiceImpl lingXingBaseInfoService;

    @Resource
    AdvertisingGroupMapper advertisingGroupMapper;

    @Resource
    UserSearchWordsMapper userSearchWordsMapper;

    @Resource
    CommodityPositionMapper commodityPositionMapper;

    @Resource
    AdvertisingActivityMapper advertisingActivityMapper;

    @Resource
    AdvertisingMapper advertisingMapper;

    @Resource
    OperationLogMapper operationLogMapper;

    @Resource
    KeywordsMapper keywordsMapper;



    @Resource
    LingxingTaskUtil taskUtil;

    public SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    public static  int DAY_RANGE= 15;

    public static int FIXED_OFFSET=1000;

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData advertisingGroup(AdvertisingGroupReq req) throws Exception {

        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间

        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);
        req.setEnd_date(formatter.format(date));

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getStart_date()!=null && !req.getStart_date().equals("")  && !req.getStart_date().equals("string")){
            beginDate = req.getStart_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<AdvertisingGroup>  queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("START_DATE",DateUtil.parse(beginDate));
            advertisingGroupMapper.delete(queryWrapper);
        }

        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();
            //遍历店铺
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());
                //遍历日期区间
                for (Map map : lists) {
                    req.setStart_date((String) map.get("startDate"));
                    req.setEnd_date((String) map.get("endDate"));

                    //页数
                    req.setType(1);
                    req.setLength(1);
                    LingXingBaseRespData advTotal = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_GROUP);
                    Integer totalRecord=advTotal.getTotal();
                    if (null == totalRecord || totalRecord==0) {
                        log.info(shop.getName() +"---"+  "广告组 requestid:"+ advTotal.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advTotal.toString());
                        continue;
                    }
                    double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                    //type 1
                    for(int k=0;k<=pages;k++){

                        req.setType(1);
                        req.setOffset(k*FIXED_OFFSET);
                        if(k==pages){
                            req.setLength(totalRecord-k*FIXED_OFFSET);
                        }else {
                            req.setLength(FIXED_OFFSET);
                        }
                        LingXingBaseRespData advertisings = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_GROUP);

                        List<Object> ads = advertisings.getData();
                        List<AdvertisingGroup> AdvertisingGroupOnes = JSONUtil.toList(JSONUtil.parseArray(ads).toString(), AdvertisingGroup.class);

                        String[] nameArr = shop.getName().split("-|_|/");
                        if(advertisings.getCode()!=0){
                            log.info(shop.getName() +"---"+  "广告组请求失败 requestid:"+ advertisings.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advertisings.toString());
                        }
                        for (AdvertisingGroup group : AdvertisingGroupOnes) {
                            if(nameArr.length != 0){
                                group.setSite(nameArr[1]);
                            }else{
                                group.setSite(shop.getName());
                            }
                            group.setShop(shop.getAccount_name());
                            group.setStartDate(DateUtil.parse(req.getStart_date()));
                            group.setEndDate(DateUtil.parse(req.getEnd_date()));
                            group.setType(1);
                            advertisingGroupMapper.insert(group);
                        }
                    }

                    //页数
                    req.setType(3);
                    req.setLength(1);
                    advTotal = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_GROUP);
                    totalRecord=advTotal.getTotal();
                    if (null == totalRecord || totalRecord==0) {
                        log.info(shop.getName() +"---"+  "广告组 requestid:"+ advTotal.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advTotal.toString());
                        continue;
                    }
                    pages=Math.ceil(totalRecord/FIXED_OFFSET);
                    //type 3
                    for(int p=0;p<=pages;p++) {
                        req.setType(3);
                        req.setOffset(p*FIXED_OFFSET);
                        if(p==pages){
                            req.setLength(totalRecord-p*FIXED_OFFSET);
                        }else {
                            req.setLength(FIXED_OFFSET);
                        }
                        LingXingBaseRespData advertisings3 = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_GROUP);
                        log.info(advertisings3.toString());
                        List<Object> ads3 = advertisings3.getData();
                        List<AdvertisingGroup> AdvertisingGroupThrees = JSONUtil.toList(JSONUtil.parseArray(ads3).toString(), AdvertisingGroup.class);
                        if(advertisings3.getCode()!=0){
                            log.info(shop.getName() +"---"+  "广告组请求失败 requestid:"+ advertisings3.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advertisings3.toString());
                        }
                        String[] nameArr = shop.getName().split("-|_|/");

                        for (AdvertisingGroup group : AdvertisingGroupThrees) {
                            if(nameArr.length != 0){
                                group.setSite(nameArr[1]);
                            }else{
                                group.setSite(shop.getName());
                            }
                            group.setShop(shop.getAccount_name());
                            group.setStartDate(DateUtil.parse(req.getStart_date()));
                            group.setEndDate(DateUtil.parse(req.getEnd_date()));
                            group.setType(3);
                            advertisingGroupMapper.insert(group);
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData userSearchWords(UserSearchWordsReq req) throws Exception {
        /**
         * 广告管理->用户搜索关键词
         **/
        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间


        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);
        req.setEnd_date(formatter.format(date));

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getStart_date()!=null && !req.getStart_date().equals("")  && !req.getStart_date().equals("string")){
            beginDate = req.getStart_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<UserSearchWords>  queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("START_DATE",DateUtil.parse(beginDate));
            userSearchWordsMapper.delete(queryWrapper);
        }

        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();

            //获取店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());
                //请求日期区间参数
                for (Map map : lists) {
                    req.setStart_date((String) map.get("startDate"));
                    req.setEnd_date((String) map.get("endDate"));

                    for (int j=1;j<=3;j++) {
                        //搜索词类型->1：关键词产生 2：商品产生 3：自动产生
                        req.setQuery_type(j);
                       for(int k=1;k<=2;k++){
                           //广告类型->1：SP广告 2：SB广告
                           req.setType(k);

                           //拉取总记录数、分页数
                           req.setLength(1);
                           LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.USER_SEARCH_WORDS);

                           if(Records.getTotal()==null){
                               log.info("qqqqqqqqqqqqqqqq " + req.getStart_date() +  "返回null" + Records.toString());
                           }
                           Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                           if (null == totalRecord || totalRecord==0) {
                               log.info(shop.getName() +"---"+  "用户搜索词 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString() + "返回结果:" + Records.toString());
                               continue;
                           }
                           double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                           for(int p=0;p<=pages;p++)
                           {
                               req.setOffset(p*FIXED_OFFSET);
                               //最后一页
                               if(p==pages){
                                   req.setLength(totalRecord-p*FIXED_OFFSET);
                               }else {
                                   req.setLength(FIXED_OFFSET);
                               }
                               //接口分页数据请求
                               LingXingBaseRespData userSearchWordTypes = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.USER_SEARCH_WORDS);
                               List<Object> searchWords = userSearchWordTypes.getData();
                               List<UserSearchWords> UserSearchWordsData = JSONUtil.toList(JSONUtil.parseArray(searchWords).toString(), UserSearchWords.class);

                               if(userSearchWordTypes.getCode()!=0){
                                   log.info(shop.getName() +"---"+  "用户搜索词请求失败 requestid:"+ userSearchWordTypes.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + userSearchWordTypes.toString());
                               }

                               String[] nameArr = shop.getName().split("-|_|/");
                               //保存数据
                               for (UserSearchWords words : UserSearchWordsData) {

                                   if(nameArr.length != 0){
                                       words.setSite(nameArr[1]);
                                   }else{
                                       words.setSite(shop.getName());
                                   }

                                   words.setShop(shop.getAccount_name());
                                   words.setStartDate(DateUtil.parse(req.getStart_date()));
                                   words.setEndDate(DateUtil.parse(req.getEnd_date()));
                                   words.setType(k);
                                   words.setQueryType(j);
                                   userSearchWordsMapper.insert(words);
                               }
                           }

                       }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData commodityPosition(CommodityPositionReq req) throws Exception {
        /**
         * 广告管理->商品定位
         **/
        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间


        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);
        req.setEnd_date(formatter.format(date));

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getStart_date()!=null && !req.getStart_date().equals("")  && !req.getStart_date().equals("string")){
            beginDate = req.getStart_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<CommodityPosition>  queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("START_DATE",DateUtil.parse(beginDate));
            commodityPositionMapper.delete(queryWrapper);
        }

        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();


            //获取店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());
                //请求日期区间参数
                for (Map map : lists) {
                    req.setStart_date((String) map.get("startDate"));
                    req.setEnd_date((String) map.get("endDate"));

                    for(int k=1;k<=3;k++){
                        //广告类型->1：SP广告 2：SB广告 3:SD广告
                        req.setType(k);

                        //拉取总记录数、分页数
                        LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.COMMODITY_POSITION);
                        Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                        if (null == totalRecord || totalRecord==0) {
                            log.info(shop.getName() +"---"+  "商品定位 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString());
                            continue;
                        }
                        double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                        for(int p=0;p<=pages;p++) {
                            req.setOffset(p*FIXED_OFFSET);
                            //最后一页
                            if (p == pages) {
                                req.setLength(totalRecord - p * FIXED_OFFSET);
                            }else {
                                req.setLength(FIXED_OFFSET);
                            }
                            //接口分页数据请求
                            LingXingBaseRespData commodityPositionTypes = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.COMMODITY_POSITION);
                            List<Object> positions = commodityPositionTypes.getData();
                            List<CommodityPosition> CommodityPositionData = JSONUtil.toList(JSONUtil.parseArray(positions).toString(), CommodityPosition.class);

                            if(commodityPositionTypes.getCode()!=0){
                                log.info(shop.getName() +"---"+  "商品定位请求失败 requestid:"+ commodityPositionTypes.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + commodityPositionTypes.toString());
                            }

                            String[] nameArr = shop.getName().split("-|_|/");
                            //保存数据
                            for (CommodityPosition position : CommodityPositionData) {

                                if(nameArr.length != 0){
                                    position.setSite(nameArr[1]);
                                }else{
                                    position.setSite(shop.getName());
                                }
                                position.setRequestId(commodityPositionTypes.getRequest_id());
                                position.setShop(shop.getAccount_name());
                                position.setStartDate(DateUtil.parse(req.getStart_date()));
                                position.setEndDate(DateUtil.parse(req.getEnd_date()));
                                position.setType(k);
                                commodityPositionMapper.insert(position);
                            }
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData advertisingActivity(AdvertisingActivityReq req) throws Exception {
        /**
         * 广告管理->广告活动
         **/
        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间

        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);
        req.setEnd_date(formatter.format(date));

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getStart_date()!=null && !req.getStart_date().equals("")  && !req.getStart_date().equals("string")){
            beginDate = req.getStart_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<AdvertisingActivity>  queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("START_DATE_PARAM",DateUtil.parse(beginDate));
            advertisingActivityMapper.delete(queryWrapper);
        }

        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();

            //获取店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());
                //请求日期区间参数
                for (Map map : lists) {
                    req.setStart_date((String) map.get("startDate"));
                    req.setEnd_date((String) map.get("endDate"));

                    for(int k=1;k<=3;k++){
                        //广告类型->1：SP广告 2：SB广告 3:SD广告
                        req.setType(k);

                        //拉取总记录数、分页数
                        LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_ACTIVITY);
                        Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                        if (null == totalRecord || totalRecord==0) {
                            log.info(shop.getName() +"---"+  "广告活动 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString());
                            continue;
                        }
                        double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                        for(int p=0;p<=pages;p++) {
                            req.setOffset(p*FIXED_OFFSET);
                            //最后一页
                            if (p == pages) {
                                req.setLength(totalRecord - p * FIXED_OFFSET);
                            }else {
                                req.setLength(FIXED_OFFSET);
                            }
                            //接口分页数据请求
                            LingXingBaseRespData advertisingActivityTypes = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING_ACTIVITY);
                            List<Object> positions = advertisingActivityTypes.getData();
                            List<AdvertisingActivity> AdvertisingActivityData = JSONUtil.toList(JSONUtil.parseArray(positions).toString(), AdvertisingActivity.class);

                            if(advertisingActivityTypes.getCode()!=0){
                                log.info(shop.getName() +"---"+  "广告活动请求失败 requestid:"+ advertisingActivityTypes.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advertisingActivityTypes.toString());
                            }

                            String[] nameArr = shop.getName().split("-|_|/");

                            //保存数据
                            for (AdvertisingActivity activity : AdvertisingActivityData) {
                                if(nameArr.length != 0){
                                    activity.setSite(nameArr[1]);
                                }else{
                                    activity.setSite(shop.getName());
                                }
                                activity.setShop(shop.getAccount_name());
                                activity.setStartDateParam(DateUtil.parse(req.getStart_date()));
                                activity.setEndDateParam(DateUtil.parse(req.getEnd_date()));
                                activity.setType(k);
                                advertisingActivityMapper.insert(activity);
                            }
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData advertising(AdvertisingReq req) throws Exception {
        /**
         * 广告管理->广告
         **/

        //定义报告类型数组
        int[] arr={1,3};

        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间




        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getEvent_date()!=null && !req.getEvent_date().equals("")  && !req.getEvent_date().equals("string")){
            beginDate = req.getEvent_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<Advertising> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("EVENT_DATE", DateUtil.parse(beginDate));
            advertisingMapper.delete(queryWrapper);
        }


        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){

            List<Object> datas = Sellers.getData();
            //店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());

                for (Map map : lists) {

                    if(req.getEvent_date()!=null && !req.getEvent_date().equals("")  && !req.getEvent_date().equals("string")){
                        req.setEvent_date((String) map.get("startDate"));
                    }else {
                        req.setEvent_date((String) map.get("endDate"));
                    }
                    for(int i=0;i<arr.length;i++)
                    {
                        req.setType(arr[i]);

                        //拉取总记录数、分页数
                        LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING);
                        Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                        if (null == totalRecord || totalRecord==0) {
                            log.info(shop.getName() +"---"+  "广告 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString());
                            continue;
                        }
                        double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                        for(int p=0;p<=pages;p++) {
                            req.setOffset(p*FIXED_OFFSET);
                            //最后一页
                            if (p == pages) {
                                req.setLength(totalRecord - p * FIXED_OFFSET);
                            }else {
                                req.setLength(FIXED_OFFSET);
                            }
                            //接口分页数据请求
                            LingXingBaseRespData advertisings = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ADVERTISING);
                            List<Object> ads = advertisings.getData();
                            List<Advertising> AdvertisingOnes = JSONUtil.toList(JSONUtil.parseArray(ads).toString(), Advertising.class);

                            if(advertisings.getCode()!=0){
                                log.info(shop.getName() +"---"+  "广告请求失败 requestid:"+ advertisings.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + advertisings.toString());
                            }

                            String[] nameArr = shop.getName().split("-|_|/");
                            for (Advertising advers : AdvertisingOnes) {
                                if(nameArr.length != 0){
                                    advers.setSite(nameArr[1]);
                                }else{
                                    advers.setSite(shop.getName());
                                }
                                advers.setShop(shop.getAccount_name());
                                advers.setEventDate(DateUtil.parse(req.getEvent_date()));
                                advers.setType(arr[i]);
                                advertisingMapper.insert(advers);
                            }
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operationLog(OperationLogReq req) throws Exception {
        /**
         * 广告管理->操作日志
         **/
        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();

            //获取店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());

                for(int k=1;k<6;k++){
                    //广告类型->1：SP广告 2：SB广告 3:SD广告 4：广告设置 5：SBV广告
                    req.setType(k);

                    //拉取总记录数、分页数
                    LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.OPERATION_LOG);
                    Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                    if (null == totalRecord || totalRecord==0) {
                        log.info(shop.getName() +"---"+  "操作日志 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString());
                        continue;
                    }
                    double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                    for(int p=0;p<=pages;p++) {
                        req.setOffset(p*FIXED_OFFSET);
                        //最后一页
                        if (p == pages) {
                            req.setLength(totalRecord - p * FIXED_OFFSET);
                        }else {
                            req.setLength(FIXED_OFFSET);
                        }
                        //接口分页数据请求
                        LingXingBaseRespData operationLogTypes = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.OPERATION_LOG);
                        List<Object> opelogs = operationLogTypes.getData();
                        List<OperationLog> OperationLogData = JSONUtil.toList(JSONUtil.parseArray(opelogs).toString(), OperationLog.class);
                        //保存数据
                        for (OperationLog opelog : OperationLogData) {
                            opelog.setShop(shop.getAccount_name());
                            opelog.setSite(shop.getName());
                            opelog.setType(k);
                            operationLogMapper.insert(opelog);
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData keywordsTask(KeywordsReq req) throws Exception {
        /**
         * 广告管理->关键词
         **/
        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间

        //删除 DAY_RANGE 天内的数据，重新拉取
        QueryWrapper<Keywords>  queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("START_DATE",DateUtil.parse(beginDate));
        keywordsMapper.delete(queryWrapper);
        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();

        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();

            Date date = new Date(System.currentTimeMillis());
            String endDate =formatter.format(date);
            req.setEnd_date(formatter.format(date));

            List<Map> lists =   dateRange(endDate, DAY_RANGE);

            //获取店铺SID
            for(Object ob :datas) {
                Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
                req.setSid(shop.getSid());
                //请求日期区间参数
                for (Map map : lists) {
                    req.setStart_date((String) map.get("startDate"));
                    req.setEnd_date((String) map.get("endDate"));

                    for(int k=1;k<3;k++){
                        //广告类型->1：SP广告 2：SB广告
                        req.setType(k);

                        //拉取总记录数、分页数
                        LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.KEYWORDS);
                        Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();
                        if (null == totalRecord || totalRecord==0) {
                            log.info(shop.getName() +"---"+  "关键词 requestid:"+ Records.getRequest_id()  + " 参数：" +req.toString() + "返回结果:" + Records.toString());
                            continue;
                        }
                        double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                        for(int p=0;p<=pages;p++) {
                            req.setOffset(p*FIXED_OFFSET);
                            //最后一页
                            if (p == pages) {
                                req.setLength(totalRecord - p * FIXED_OFFSET);
                            }else {
                                req.setLength(FIXED_OFFSET);
                            }
                            //接口分页数据请求
                            LingXingBaseRespData keywordsTypes = lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.KEYWORDS);
                            List<Object> keywords = keywordsTypes.getData();
                            List<Keywords> KeywordsData = JSONUtil.toList(JSONUtil.parseArray(keywords).toString(), Keywords.class);
                            //保存数据
                            for (Keywords keyword : KeywordsData) {
                                keyword.setShop(shop.getAccount_name());
                                keyword.setSite(shop.getName());
                                keyword.setStartDate(DateUtil.parse(req.getStart_date()));
                                keyword.setEndDate(DateUtil.parse(req.getEnd_date()));
                                keyword.setType(k);
                                keywordsMapper.insert(keyword);
                            }
                        }
                    }
                }
            }
        }
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData keywords(KeywordsReq req) throws Exception {
        /**
         * 广告管理->关键词
         **/
        Date dNow = new Date();   //当前时间Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -DAY_RANGE);  //设置为前一天
        String beginDate = formatter.format(calendar.getTime());//DAY_RANGE天前的时间



        Date date = new Date(System.currentTimeMillis());
        String endDate =formatter.format(date);
        req.setEnd_date(formatter.format(date));

        List<Map> lists =   dateRange(endDate, DAY_RANGE);

        if(req.getStart_date()!=null && !req.getStart_date().equals("")  && !req.getStart_date().equals("string")){
            beginDate = req.getStart_date();
            //取当月数据
            lists =   dateMonthRange(beginDate);
        }else{
            //删除 DAY_RANGE 天内的数据，重新拉取
            QueryWrapper<Keywords>  queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("START_DATE",DateUtil.parse(beginDate));
            keywordsMapper.delete(queryWrapper);
        }

        LingXingBaseRespData Sellers =  lingXingBaseInfoService.getSellerLists();
        req.setLength(FIXED_OFFSET);
        //请求成功
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(Sellers.getCode())){
            List<Object> datas = Sellers.getData();
            log.error(datas.toString());

            //获取店铺SID
            for(Object ob :datas) {

                taskUtil.doTask(keywordsMapper,lingXingReqInfoUtil,req,ob,lists);

            }
        }
        log.info("----------"+  "关键词拉取结束：" + new Date().toString() );
        return ResponseData.success();
    }

    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insert(Keywords keywords) {
        if (keywordsMapper.insert(keywords)>0) {
            ResponseData.success();
        }
        return ResponseData.error("fail");
    }


    public List<Map> dateMonthRange(String beginDate) {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

        Date start = DateUtil.parse(beginDate);

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(start);//把当前时间赋给日历

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Map> lists =new ArrayList<>();

        for(int i=0;i<days;i++){
            Map<String,String> map =new HashMap<>();
            calendar.add(Calendar.DATE,i);
            map.put("startDate",formatter.format(calendar.getTime()));
            calendar.add(Calendar.DATE,1);
            map.put("endDate",formatter.format(calendar.getTime()));
            calendar.setTime(start);//把初始时间赋给日历
            lists.add(map);
        }

        return lists;
    }


    public List<Map> dateRange(String endDate, int days) throws ParseException {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

        List<Map> lists =new ArrayList<>();


        Map<String,String> map =new HashMap<>();
        Date dNow = formatter.parse(endDate);   //当前时间Date dBefore = new Date();

        Date dBefore = formatter.parse(endDate);

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        String startDate = formatter.format(dBefore);     // 开始日期

        endDate = endDate==null? formatter.format(dNow):endDate; // 结束日期

        map.put("startDate",startDate);
        map.put("endDate",endDate);
        lists.add(map);

        if(days>1){
            lists.addAll(dateRange(startDate,days-1));
        }

        return lists;
    }
}
