package com.tadpole.cloud.externalSystem.modular.saihu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.api.model.SaihuUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuPageParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuPageDataResult;
import com.tadpole.cloud.externalSystem.modular.saihu.constants.SaihuUrlConstants;
import com.tadpole.cloud.externalSystem.modular.saihu.service.IAccountService;
import com.tadpole.cloud.externalSystem.modular.saihu.utils.SaihuReqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: ty
 * @description: 赛狐token服务实现类
 * @date: 2024/2/19
 */
@Service
@Slf4j
public class AccountServiceImpl  implements IAccountService {

    @Autowired
    private SaihuReqUtil saihuReqUtil;

    @Override
    public List<SaihuUser> getAccountList() {

        List<SaihuUser> saihuUserList = new ArrayList<>();
        //默认第一页
        int pageNo = 1;
        int pageSize = 200;
        Integer totalPage = 0;
        do{
            SaiHuPageParam param = new SaiHuPageParam();
            param.setPageNo(String.valueOf(pageNo));
            param.setPageSize(String.valueOf(pageSize));
            try {
                SaiHuBaseResult result = saihuReqUtil.doPostReq(param, SaihuUrlConstants.ACCOUNT_LIST);
                if(SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(result.getCode())){
                    String jsonDateObject = JSON.toJSONString(result.getData());
                    SaiHuPageDataResult pageData = com.alibaba.fastjson.JSONObject.parseObject(jsonDateObject, SaiHuPageDataResult.class);
                    List<Object> rows = pageData.getRows();
                    Date curDate = DateUtil.date();
                    for (Object obj : rows) {
                        String jsonObject = JSON.toJSONString(obj);
                        SaihuUser saihuUser = JSONObject.parseObject(jsonObject, SaihuUser.class);
                        saihuUserList.add(saihuUser);
                    }
                }
            } catch (Exception e){
                log.error("解析异常，当前页数[{}]，分页条数[{}]，异常信息[{}]", pageNo, pageSize, e);
            }
            pageNo ++;

            } while (pageNo <= totalPage);//小于等于总页数时循环获取数据

            return saihuUserList;
    }


    @Override
    public void changeAccountStatus(SaihuUser saihuUser) {
        try {
            SaiHuBaseResult result = saihuReqUtil.doPostReq(saihuUser, SaihuUrlConstants.ACCOUNT_STATUS);
            if(SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(result.getCode())){
                log.error("赛狐账号状态变更成功！");
            }
        } catch (Exception e){
            log.error("赛狐账号状态变更失败！");
        }
    }

    @Override
    public void createAccount(SaihuUser saihuUser) {
        try {
            SaiHuBaseResult result = saihuReqUtil.doPostReq(saihuUser, SaihuUrlConstants.ACCOUNT_CREATE);
            if(SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(result.getCode())){
                log.error("赛狐账号状态创建成功！");
            }
        } catch (Exception e){
                log.error("赛狐账号状态创建失败！");
        }
    }
}
