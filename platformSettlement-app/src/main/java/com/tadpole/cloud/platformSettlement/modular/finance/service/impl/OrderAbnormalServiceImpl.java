package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderAbnormal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderAbnormalResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AmazonOrdersMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.OrderAbnormalMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderAbnormalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * @author: ty
 * @description: 订单异常情况汇总服务实现类
 * @date: 2022/5/6
 */
@Service
@Slf4j
public class OrderAbnormalServiceImpl extends ServiceImpl<OrderAbnormalMapper, OrderAbnormal> implements IOrderAbnormalService {

    @Value("${warehouse_database}")
    private String warehouseDatabase;

    @Resource
    private AmazonOrdersMapper amazonOrdersMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String ORDER_ABNORMAL_KEY = "ORDER_ABNORMAL_KEY";

    @DataSource(name = "finance")
    @Override
    public ResponseData queryPage(OrderAbnormalParam param) {
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getShippedDateStart())){
            param.setShippedDateStart(param.getShippedDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getShippedDateEnd())){
            param.setShippedDateEnd(param.getShippedDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getSettlementDateStart())){
            param.setSettlementDateStart(param.getSettlementDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getSettlementDateEnd())){
            param.setSettlementDateEnd(param.getSettlementDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getReturnDateStart())){
            param.setReturnDateStart(param.getReturnDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getReturnDateEnd())){
            param.setReturnDateEnd(param.getReturnDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getPurchaseDateStart())){
            param.setPurchaseDateStart(param.getPurchaseDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getPurchaseDateEnd())){
            param.setPurchaseDateEnd(param.getPurchaseDateEnd() + end);
        }
        return ResponseData.success(this.baseMapper.queryOrderAbnormalPage(param.getPageContext(), param));
    }

    @DataSource(name = "finance")
    @Override
    public List<OrderAbnormalResult> export(OrderAbnormalParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        String start = " 00:00:00";
        String end = " 23:59:59";
        if(StringUtils.isNotEmpty(param.getShippedDateStart())){
            param.setShippedDateStart(param.getShippedDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getShippedDateEnd())){
            param.setShippedDateEnd(param.getShippedDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getSettlementDateStart())){
            param.setSettlementDateStart(param.getSettlementDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getSettlementDateEnd())){
            param.setSettlementDateEnd(param.getSettlementDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getReturnDateStart())){
            param.setReturnDateStart(param.getReturnDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getReturnDateEnd())){
            param.setReturnDateEnd(param.getReturnDateEnd() + end);
        }
        if(StringUtils.isNotEmpty(param.getPurchaseDateStart())){
            param.setReturnDateStart(param.getPurchaseDateStart() + start);
        }
        if(StringUtils.isNotEmpty(param.getPurchaseDateEnd())){
            param.setReturnDateEnd(param.getPurchaseDateEnd() + end);
        }
        return this.baseMapper.queryOrderAbnormalPage(pageContext, param).getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refresh() {
        if(redisTemplate.hasKey(ORDER_ABNORMAL_KEY)){
            return ResponseData.error("正在刷新中，请稍后再试！");
        }
        log.info("订单异常情况汇总数据生成（刷新）开始------------------->");
        redisTemplate.boundValueOps(ORDER_ABNORMAL_KEY).set("订单异常情况汇总数据生成中", Duration.ofSeconds(600));
        try {
            long start = System.currentTimeMillis();
            //1、先删除原来的数据
            this.remove(null);

            //2、重新生成新的数据
            this.baseMapper.refresh(warehouseDatabase + ".FBA_CUSTOMER_RETURNS", warehouseDatabase + ".FBA_SHIPMENT_SALES");
            log.info("订单异常情况汇总数据生成（刷新）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e){
            log.error(e.getMessage());
        }finally {
            redisTemplate.delete(ORDER_ABNORMAL_KEY);
        }
        return ResponseData.success();
    }
}
