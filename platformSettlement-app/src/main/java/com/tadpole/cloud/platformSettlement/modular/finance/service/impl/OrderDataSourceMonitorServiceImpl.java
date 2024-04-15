package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderDatasourceMonitor;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderDataSourceMonitorParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderDataSourceMonitorResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AmazonOrdersMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.OrderDatasourceMonitorMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderDataSourceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * @author: ty
 * @description: 销售订单源报告数据监控服务实现类
 * @date: 2022/5/6
 */
@Service
@Slf4j
public class OrderDataSourceMonitorServiceImpl extends ServiceImpl<OrderDatasourceMonitorMapper, OrderDatasourceMonitor> implements IOrderDataSourceMonitorService {

    @Resource
    private AmazonOrdersMapper amazonOrdersMapper;

    @Value("${warehouse_database}")
    private String warehouseDatabase;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String ORDER_DATASOURCE_MONITOR_KEY = "ORDER_DATASOURCE_MONITOR_KEY";

    @DataSource(name = "finance")
    @Override
    public ResponseData queryPage(OrderDataSourceMonitorParam param) {
        return ResponseData.success(this.baseMapper.queryOrderDataSourceMonitorPage(param.getPageContext(), param));
    }

    @DataSource(name = "finance")
    @Override
    public List<OrderDataSourceMonitorResult> export(OrderDataSourceMonitorParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setSize(Integer.MAX_VALUE);
        return this.baseMapper.queryOrderDataSourceMonitorPage(pageContext, param).getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData dealAmazonOrderRepeat(Integer days) {
        //订单去重
        amazonOrdersMapper.dealAmazonOrderRepeat(days);
        //订单明细去重
        amazonOrdersMapper.dealAmazonOrderDetailRepeat(days);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refresh() {
        if(redisTemplate.hasKey(ORDER_DATASOURCE_MONITOR_KEY)){
            return ResponseData.error("正在刷新中，请稍后再试！");
        }
        log.info("订单异常情况汇总数据生成（刷新）开始------------------->");
        redisTemplate.boundValueOps(ORDER_DATASOURCE_MONITOR_KEY).set("订单源报告数据监控数据生成中", Duration.ofSeconds(900));
        try {
            log.info("销售订单源报告数据监控数据生成（刷新）开始------------------->");
            long start = System.currentTimeMillis();
            this.baseMapper.refresh(warehouseDatabase + ".REMOVAL_ORDER_DETAIL");
            log.info("销售订单源报告数据监控数据生成（刷新）结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e){
            log.error(e.getMessage());
        }finally {
            redisTemplate.delete(ORDER_DATASOURCE_MONITOR_KEY);
        }
        return ResponseData.success();
    }
}
