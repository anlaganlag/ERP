package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.LingXingBaseInfoConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.ShopEntityConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynType;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.CwLingxingShopInfoMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ICwLingxingShopInfoService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxShopSynRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  领星亚马逊店铺信息服务实现类
 * </p>
 *
 * @author ty
 * @since 2022-04-29
 */
@Slf4j
@Service
public class CwLingxingShopInfoServiceImpl extends ServiceImpl<CwLingxingShopInfoMapper, CwLingxingShopInfo> implements ICwLingxingShopInfoService {

    @Autowired
    private LingXingBaseInfoConsumer lingXingBaseInfoConsumer;

    @Autowired
    private ShopEntityConsumer shopEntityConsumer;
    @Autowired
    private ILxShopSynRecordService lxShopSynRecordService;

    @DataSource(name = "finance")
    @Override
    public void syncSellerLists() throws Exception {
        LingXingBaseRespData baseRespData = lingXingBaseInfoConsumer.getSellerLists();
        ResponseData amazon_ddd_ca = shopEntityConsumer.queryById("Amazon_DDD_CA");
        String synDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(baseRespData.getCode())){
            List<Object> objList = baseRespData.getData();
            List<CwLingxingShopInfo> entityList = new ArrayList<>();
            for (Object obj : objList) {
                String jsonObject = JSON.toJSONString(obj);
                CwLingxingShopInfo cwLingxingShopInfo = JSONObject.parseObject(jsonObject, CwLingxingShopInfo.class);
                String[] nameArr = cwLingxingShopInfo.getName().split("-|_|/");
                if(nameArr.length != 0){
                    cwLingxingShopInfo.setShopName(nameArr[0]);
                    cwLingxingShopInfo.setSite(nameArr[1]);
                    cwLingxingShopInfo.setCreateDate(new Date());
                    entityList.add(cwLingxingShopInfo);
                }else{
                    log.error("领星店铺名称解析异常，店铺名称[{}]，sid[{}]", cwLingxingShopInfo.getName(), cwLingxingShopInfo.getSid());
                }
            }

            if(CollectionUtil.isNotEmpty(entityList)){
                //1、删除领星店铺信息表
                this.remove(null);

                //2、同步入库最新领星店铺信息
                this.saveBatch(entityList);

                //3、初始化领星店铺维度数据更新信息
                List<String> typeList = LxShopSynType.getEnumValueList();
                if(CollectionUtil.isNotEmpty(typeList)){
                    List<LxShopSynRecord> shopSynList = new ArrayList();
                    for (String type : typeList) {
                        QueryWrapper<LxShopSynRecord> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("SYN_TYPE", type).eq("SYN_DATE", synDate).eq("SYN_STATUS", LxShopSynStatus.SUCCESS.getCode());
                        Integer count = lxShopSynRecordService.count(queryWrapper);
                        if(count == 0){
                            //该类型没有执行同步成功的方可初始化店铺同步信息数据
                            QueryWrapper<LxShopSynRecord> deleteWrapper = new QueryWrapper<>();
                            deleteWrapper.eq("SYN_TYPE", type).eq("SYN_DATE", synDate);
                            lxShopSynRecordService.remove(deleteWrapper);

                            for (CwLingxingShopInfo shopInfo : entityList) {
                                LxShopSynRecord shopSyn = new LxShopSynRecord();
                                shopSyn.setSid(shopInfo.getSid());
                                shopSyn.setSynDate(synDate);
                                shopSyn.setSynType(type);
                                shopSyn.setSynStatus(LxShopSynStatus.DEFAULT.getCode());
                                shopSynList.add(shopSyn);
                            }
                        }
                    }
                    lxShopSynRecordService.saveBatch(shopSynList);
                }
            }
        }
    }

    @Override
    public List<CwLingxingShopInfo> getLxShopInfoBySynType(LxShopSynRecord lxShopSynRecord) {
        return this.baseMapper.getLxShopInfoBySynType(lxShopSynRecord);
    }
}
