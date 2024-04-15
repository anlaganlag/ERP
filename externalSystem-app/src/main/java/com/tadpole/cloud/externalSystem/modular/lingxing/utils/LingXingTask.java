package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.KeywordsMapper;
import com.tadpole.cloud.externalSystem.api.lingxing.model.Shop;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising.KeywordsReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.Keywords;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.ResposeLog;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.impl.KeywordsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LingXingTask implements Runnable {


    private KeywordsReq req;
    private Object ob;
    private List<Map> lists;
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    private KeywordsMapper keywordsMapper;


    public static int FIXED_OFFSET=1000;



    public LingXingTask(KeywordsMapper keywordsMapper,LingXingReqInfoUtil lingXingReqInfoUtil,KeywordsReq req, Object ob, List<Map> lists){
        this.req=req;
        this.ob=ob;
        this.lists=lists;
        this.lingXingReqInfoUtil=lingXingReqInfoUtil;
        this.keywordsMapper=keywordsMapper;
    }


    @DataSource(name = "BIDW")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run() {
        try {
            KeywordsReq reqNew = new KeywordsReq();
            Shop shop = JSONUtil.toBean(JSONUtil.parseObj(ob), Shop.class);
            reqNew.setSid(shop.getSid());
            reqNew.setLength(FIXED_OFFSET);
            KeywordsServiceImpl service = SpringContext.getBean(KeywordsServiceImpl.class);
            //请求日期区间参数
            for (Map map : lists) {
                reqNew.setStart_date((String) map.get("startDate"));
                reqNew.setEnd_date((String) map.get("endDate"));

                for(int k=1;k<3;k++){
                    //广告类型->1：SP广告 2：SB广告
                    reqNew.setType(k);

                    //拉取总记录数、分页数
                    LingXingBaseRespData Records = lingXingReqInfoUtil.doPostReq(reqNew, LingXingUrlConstants.KEYWORDS);
                    Integer totalRecord=Records.getTotal()==null?0:Records.getTotal();


                    ResposeLog resposeLog = new ResposeLog();
                    resposeLog.setLength(totalRecord);
                    resposeLog.setSid(shop.getSid());
                    resposeLog.setStartDate(reqNew.getStart_date());
                    resposeLog.setEndDate(reqNew.getEnd_date());
                    resposeLog.setShop(shop.getName());
                    resposeLog.setSite(shop.getCountry());
                    resposeLog.setType(reqNew.getType());
                    service.saveLog(resposeLog);

                    if (null == totalRecord || totalRecord==0) {
                        log.info(shop.getName() +"---"+  "关键词 requestid:"+ Records.getRequest_id()  + " 参数：" +reqNew.toString() + "返回结果:" + Records.toString());
                        continue;
                    }

                    log.info(shop.getName() +"---"+  "关键词日志" + resposeLog.toString());
                    double pages=Math.ceil(totalRecord/FIXED_OFFSET);

                    for(int p=0;p<=pages;p++) {
                        reqNew.setOffset(p*FIXED_OFFSET);
                        //最后一页
                        if (p == pages) {
                            reqNew.setLength(totalRecord - p * FIXED_OFFSET);
                        }else {
                            reqNew.setLength(FIXED_OFFSET);
                        }
                        //接口分页数据请求
                        LingXingBaseRespData keywordsTypes = lingXingReqInfoUtil.doPostReq(reqNew, LingXingUrlConstants.KEYWORDS);
                        List<Object> keywords = keywordsTypes.getData();
                        List<Keywords> KeywordsData = JSONUtil.toList(JSONUtil.parseArray(keywords).toString(), Keywords.class);


                        if(keywordsTypes.getCode()!=0){
                            log.info(shop.getName() +"---"+  "关键词请求失败 requestid:"+ Records.getRequest_id()  + " 参数：" +reqNew.toString() + "返回结果:" + Records.toString());
                        }
                        //保存数据
                        int finalK = k;
                        KeywordsData=  KeywordsData.stream().map(keyword->{
                            keyword.setShop(shop.getAccount_name());
                            keyword.setSite(shop.getName());
                            keyword.setStartDate(DateUtil.parse(reqNew.getStart_date()));
                            keyword.setEndDate(DateUtil.parse(reqNew.getEnd_date()));
                            keyword.setType(finalK);
                            return keyword;
                        }).collect(Collectors.toList());

                        service.saveBatch(KeywordsData);
                    }
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }





}
