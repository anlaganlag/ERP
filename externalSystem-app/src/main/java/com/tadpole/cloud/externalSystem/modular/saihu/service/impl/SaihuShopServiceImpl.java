package com.tadpole.cloud.externalSystem.modular.saihu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuPageParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuPageDataResult;
import com.tadpole.cloud.externalSystem.modular.saihu.constants.SaihuUrlConstants;
import com.tadpole.cloud.externalSystem.modular.saihu.mapper.SaihuShopMapper;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaihuShopService;
import com.tadpole.cloud.externalSystem.modular.saihu.utils.SaihuReqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 赛狐店铺列表 服务实现类
 * </p>
 *
 * @author ty
 * @since 2024-02-22
 */
@Slf4j
@Service
public class SaihuShopServiceImpl extends ServiceImpl<SaihuShopMapper, SaihuShop> implements ISaihuShopService {

    @Resource
    private SaihuShopMapper mapper;
    @Autowired
    private SaihuReqUtil saihuReqUtil;

    @DataSource(name = "external")
    @Override
    public ResponseData shopList() {
        //默认第一页
        int pageNo = 1;
        int pageSize = 200;
        Integer totalPage = 0;
        do{
            SaiHuPageParam param = new SaiHuPageParam();
            param.setPageNo(String.valueOf(pageNo));
            param.setPageSize(String.valueOf(pageSize));
            try {
                SaiHuBaseResult result = saihuReqUtil.doPostReq(param, SaihuUrlConstants.SHOP);
                if(SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(result.getCode())){
                    String jsonDateObject = JSON.toJSONString(result.getData());
                    SaiHuPageDataResult pageData = JSONObject.parseObject(jsonDateObject, SaiHuPageDataResult.class);
                    totalPage = Integer.parseInt(pageData.getTotalPage());
                    List<Object> rows = pageData.getRows();
                    Date curDate = DateUtil.date();
                    for (Object obj : rows) {
                        String jsonObject = JSON.toJSONString(obj);
                        SaihuShop saihuShop = JSONObject.parseObject(jsonObject, SaihuShop.class);
                        String name = saihuShop.getName();
                        if(ObjectUtil.isNotEmpty(name)){
                            String[] nameArr = name.split("-|_|/");
                            saihuShop.setUpdateTime(curDate);
                            saihuShop.setShopId(String.valueOf(saihuShop.getId()));//为赛狐的id
                            saihuShop.setId(null);
                            if(nameArr.length != 0){
                                saihuShop.setShopName(nameArr[0]);
                                saihuShop.setSite(nameArr[1]);
                            }else{
                                log.error("赛狐店铺名称解析异常，店铺名称[{}]，id[{}]", name, saihuShop.getId());
                            }
                        }
                        LambdaUpdateWrapper<SaihuShop> uw = new LambdaUpdateWrapper<>();
                        uw.eq(SaihuShop :: getShopId, saihuShop.getShopId());
                        this.saveOrUpdate(saihuShop, uw);
                    }
                    pageNo ++;
                }
            } catch (Exception e){
                pageNo ++;
                log.error("赛狐店铺名称解析异常，当前页数[{}]，分页条数[{}]，异常信息[{}]", pageNo, pageSize, e);
            }
        } while (pageNo <= totalPage);//小于等于总页数时循环获取数据
        return ResponseData.success();
    }

    @Override
    public ResponseData customList() throws Exception {
        SaiHuBaseResult result = saihuReqUtil.doPostReq(null, SaihuUrlConstants.CUSTOM);
        return ResponseData.success();
    }

}
