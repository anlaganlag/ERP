package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.KeywordsMapper;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising.KeywordsReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@Slf4j
public class LingxingTaskUtil {

    private KeywordsReq req;
    private Object ob;
    private List<Map> lists;
    private KeywordsMapper keywordsMapper;
    private LingXingReqInfoUtil lingXingReqInfoUtil;

//    public static final ExecutorService excutor = Executors.newCachedThreadPool();
    public static final ThreadPoolExecutor excutor = new ThreadPoolExecutor(2,2,48, TimeUnit.HOURS,
        new LinkedBlockingQueue<>());

    public LingxingTaskUtil(){
    }

    public LingxingTaskUtil(KeywordsMapper keywordsMapper,LingXingReqInfoUtil lingXingReqInfoUtil,KeywordsReq req, Object ob, List<Map> lists){
        this.req=req;
        this.ob=ob;
        this.lists=lists;
        this.lingXingReqInfoUtil=lingXingReqInfoUtil;
        this.keywordsMapper=keywordsMapper;
    }


    @DataSource(name = "BIDW")
    public void doTask(KeywordsMapper keywordsMapper,LingXingReqInfoUtil lingXingReqInfoUtil,KeywordsReq req, Object ob, List<Map> lists) {
        try {
            LingXingTask logObjectTask = new LingXingTask(keywordsMapper,lingXingReqInfoUtil,req, ob, lists);
            excutor.execute(logObjectTask);
        } catch (Exception ex) {
            log.error(JSONUtil.toJsonStr(ex));
            ex.printStackTrace();
        }
    }
}
