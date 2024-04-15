package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveTrackHistoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackHistoryResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.DisposeRemoveTrackHistoryMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveTrackHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 销毁移除跟踪历史表 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
@Service
@Slf4j
public class DisposeRemoveTrackHistoryServiceImpl extends ServiceImpl<DisposeRemoveTrackHistoryMapper, DisposeRemoveTrackHistory> implements IDisposeRemoveTrackHistoryService {

    @Autowired
    private DisposeRemoveTrackHistoryMapper mapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 生成销毁移除跟踪历史表KEY
     */
    private static final String TO_REMOVE_TRACK_HISTORY = "TO_REMOVE_TRACK_HISTORY";

    @DataSource(name = "warehouse")
    @Override
    public PageResult<DisposeRemoveTrackHistoryResult> trackHistoryList(DisposeRemoveTrackHistoryParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        return new PageResult<>(mapper.trackHistoryList(PageFactory.defaultPage(), param));
    }

    @DataSource(name = "warehouse")
    @Override
    public List<DisposeRemoveTrackHistoryResult> trackHistoryExport(DisposeRemoveTrackHistoryParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.trackHistoryList(pageContext, param).getRecords();
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData getTrackHistoryQuantity(DisposeRemoveTrackHistoryParam param) {
        List<String> orderTypes = param.getOrderTypes();
        List<String> orderStatuss = param.getOrderStatuss();
        if(CollectionUtil.isNotEmpty(orderTypes)){
            if(orderTypes.contains("空")){
                param.setOrderType("空");
            }
        }
        if(CollectionUtil.isNotEmpty(orderStatuss)){
            if(orderStatuss.contains("空")) {
                param.setOrderStatus("空");
            }
        }
        return ResponseData.success(this.baseMapper.getTrackHistoryQuantity(param));
    }

    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData generateHistoryTrack() {
        //redis里面取值则为正在生成!
        if(redisTemplate.hasKey(TO_REMOVE_TRACK_HISTORY)){
            return ResponseData.error("销毁移除跟踪历史表数据正在生成中，请稍后再试!");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.date());
        calendar.add(Calendar.MONTH, -1);
        String dataMonth = DateUtil.format(calendar.getTime(), "yyyy-MM");
        LambdaQueryWrapper<DisposeRemoveTrackHistory> historyWrapper = new LambdaQueryWrapper<>();
        historyWrapper.eq(DisposeRemoveTrackHistory :: getDataMonth, dataMonth);
        int counts = mapper.selectCount(historyWrapper);
        if(counts > 0){
            log.error(dataMonth + "销毁移除跟踪历史表数据已存在!");
            return ResponseData.success(dataMonth + "销毁移除跟踪历史表数据已存在!");
        }
        try{
            redisTemplate.boundValueOps(TO_REMOVE_TRACK_HISTORY).set("销毁移除跟踪历史表数据正在生成中", Duration.ofSeconds(600));
            //生成上一个月末数据
            this.baseMapper.generateHistoryTrack(dataMonth);
            return ResponseData.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseData.error("销毁移除跟踪历史表数据生成失败!" + e);
        }finally {
            redisTemplate.delete(TO_REMOVE_TRACK_HISTORY);
        }
    }
}
