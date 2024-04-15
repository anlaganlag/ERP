package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.RefundOrdersReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonRefundOrders;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderPreSettlement;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderPreSettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderPreSettlementResult;
import com.tadpole.cloud.platformSettlement.modular.finance.consumer.LingXingSourceReportConsumer;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.LxShopSynType;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.OrderPreSettlementMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ICwLingxingShopInfoService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxAmazonRefundOrdersService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxShopSynRecordService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderPreSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ty
 * @description: 销售订单预结算服务实现类
 * @date: 2022/5/30
 */
@Slf4j
@Service
public class OrderPreSettlementServiceImpl extends ServiceImpl<OrderPreSettlementMapper, OrderPreSettlement> implements IOrderPreSettlementService {

    @Autowired
    private OrderPreSettlementMapper orderPreSettlementMapper;

    @Autowired
    private LingXingSourceReportConsumer lingXingSourceReportConsumer;

    @Autowired
    private ICwLingxingShopInfoService lingxingShopInfoService;

    @Autowired
    private ILxShopSynRecordService lxShopSynRecordService;

    @Autowired
    private ILxAmazonRefundOrdersService lxAmazonRefundOrdersService;

    @DataSource(name = "finance")
    @Override
    public ResponseData queryPage(OrderPreSettlementParam param) {
        return ResponseData.success(orderPreSettlementMapper.queryOrderPreSettlementPage(param.getPageContext(), param));
    }

    @DataSource(name = "finance")
    @Override
    public List<OrderPreSettlementResult> export(OrderPreSettlementParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        return orderPreSettlementMapper.queryOrderPreSettlementPage(pageContext, param).getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData getTotalQuantity(OrderPreSettlementParam param) {
        return ResponseData.success(orderPreSettlementMapper.getTotalQuantity(param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refresh() {
        log.info("刷新（生成销售订单预结算数据）开始------------------->");
        long start = System.currentTimeMillis();
        orderPreSettlementMapper.refresh();
        log.info("刷新（生成销售订单预结算数据）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData getLxRefundOrders(String synDate, String key) {
        //退货订单请求参数
        RefundOrdersReq param = new RefundOrdersReq();
        param.setDate_type(1);//默认取退货时间
        param.setLength(200000);//默认200000
        LxShopSynRecord lxShopSynRecord = new LxShopSynRecord();

        lxShopSynRecord.setSynType(LxShopSynType.REFUND.getCode());
        String defaultDate = null;//下载报告时间区间
        if(StringUtils.isNotEmpty(synDate)){
            //手动处理下载数据
            if(!"refundOrders@Lx610".equals(key)){
                return ResponseData.error("手动处理领星Settlement源文件下载异常");
            }
            defaultDate = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(synDate, DatePattern.PURE_DATE_PATTERN), -1), DatePattern.NORM_DATE_PATTERN);
        }else{
            //系统自动处理下载数据
            synDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
            //没有指定，则系统默认执行所有的店铺获取前一天的文件
            defaultDate = DateUtil.format(DateUtil.yesterday(), DatePattern.NORM_DATE_PATTERN);
        }
        param.setStart_date(defaultDate);
        param.setEnd_date(defaultDate);
        lxShopSynRecord.setSynDate(synDate);

        //获取需要获取领星退货订单店铺sid
        List<CwLingxingShopInfo> lingXingShopList = lingxingShopInfoService.getLxShopInfoBySynType(lxShopSynRecord);
        if(CollectionUtil.isNotEmpty(lingXingShopList)){
            for (CwLingxingShopInfo lingXingShopInfo : lingXingShopList) {
                param.setSid(lingXingShopInfo.getSid().longValue());
                dealLxSettlement(param, lingXingShopInfo, synDate);
            }
        }
        return ResponseData.success();
    }

    /**
     * 获取领星亚马逊源报表-退货订单处理
     * @param param
     * @param lingXingShopInfo
     * @param synDate
     */
    @DataSource(name = "finance")
    private void dealLxSettlement(RefundOrdersReq param, CwLingxingShopInfo lingXingShopInfo, String synDate) {
        UpdateWrapper<LxShopSynRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SID", lingXingShopInfo.getSid())
                .eq("SYN_DATE", synDate)
                .eq("SYN_TYPE", LxShopSynType.REFUND.getCode());

        LxShopSynRecord updateShopSynRecord = new LxShopSynRecord();
        updateShopSynRecord.setSynStatus(LxShopSynStatus.ERROR.getCode());//异常

        //根据店铺sid获取领星昨天的退货订单数据
        try {
            List<LxAmazonRefundOrders> refundOrdersData = new ArrayList<>();
            LingXingBaseRespData lingXingBaseRespData = lingXingSourceReportConsumer.refundOrders(param);
            if (LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(lingXingBaseRespData.getCode())) {
                List<Object> dataObjList = lingXingBaseRespData.getData();
                //判断有没有数据
                if (CollectionUtil.isNotEmpty(dataObjList)) {
                    for (Object dataListObj : dataObjList) {
                        String jsonString = JSON.toJSONString(dataListObj);
                        LxAmazonRefundOrders lxAmazonRefundOrders = JSONObject.parseObject(jsonString, LxAmazonRefundOrders.class);
                        lxAmazonRefundOrders.setShopName(lingXingShopInfo.getShopName());
                        lxAmazonRefundOrders.setSite(lingXingShopInfo.getSite());
                        lxAmazonRefundOrders.setCreateTime(new Date());
                        refundOrdersData.add(lxAmazonRefundOrders);
                    }
                } else {
                    //店铺没有退货数据
                    updateShopSynRecord.setSynStatus(LxShopSynStatus.NONE.getCode());
                }

                //批量保存入库
                if (CollectionUtil.isNotEmpty(refundOrdersData)) {
                    lxAmazonRefundOrdersService.saveBatch(refundOrdersData);
                    //SID对应的领星昨天的退货订单数据处理完成
                    updateShopSynRecord.setSynStatus(LxShopSynStatus.SUCCESS.getCode());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            //处理完账号站点（sid）的退货订单数据后，更新sid的任务执行情况
            updateShopSynRecord.setUpdateDate(new Date());
            lxShopSynRecordService.update(updateShopSynRecord, updateWrapper);
        }
    }
}
