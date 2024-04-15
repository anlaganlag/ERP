package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonitorGoogdwillParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonitorGoogdwillResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonitorGoogdwill;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.MonitorGoogdwillMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IMonitorGoogdwillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.*;

import java.util.Date;
import java.util.List;

/**
* <p>
    * goodwill监控表	 服务实现类
    * </p>
*
* @author S20190161
* @since 2023-07-17
*/
@Service
public class MonitorGoogdwillServiceImpl extends ServiceImpl<MonitorGoogdwillMapper, MonitorGoogdwill> implements IMonitorGoogdwillService {

    private MonitorGoogdwillTotal total = new MonitorGoogdwillTotal();
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @DataSource(name = "finance")
    @Override
    public PageTotalResult<MonitorGoogdwillResult,MonitorGoogdwillTotal> findPageBySpec(MonitorGoogdwillParam param) {
        Page pageContext = getPageContext();
        var queryWrapper=queryWrapper(param);
        //查询分页
        IPage<MonitorGoogdwillResult> page =  this.baseMapper.selectPage(pageContext,queryWrapper);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1) {
            //查询汇总统计
            queryWrapper.select("sum(ship_num) shipNum", "sum(storage_num) storageNum"
            );
            MonitorGoogdwill fees = this.baseMapper.selectOne(queryWrapper);
            if (fees != null)
                BeanUtils.copyProperties(fees, total);
        }
        return new PageTotalResult<>(page,total);

    }
    @DataSource(name = "finance")
    @Override
    public List<MonitorGoogdwill> export(MonitorGoogdwillParam param) {
        return this.baseMapper.selectList(queryWrapper(param));

    }
    @DataSource(name = "finance")
    @Override
    public void afreshStorageFee() {
        this.baseMapper.afreshStorageFee();
    }
    /**
     * 查询，汇总 查询条件
     * @author AmteMa
     * @date 2022/10/13
     */
    private QueryWrapper<MonitorGoogdwill> queryWrapper(MonitorGoogdwillParam param){
        QueryWrapper<MonitorGoogdwill> queryWrapper=new QueryWrapper<>();

        if (param.getAccountName() !=null && param.getAccountName().size()>0){
            queryWrapper.in("account_name",param.getAccountName());
        }
        if (param.getCountryCode()!=null && param.getCountryCode().size()>0){
            queryWrapper.in("country_code",param.getCountryCode());
        }

        if (StringUtils.isNotEmpty(param.getOrderId())){
            queryWrapper.eq("order_id",param.getOrderId());
        }
        if (StringUtils.isNotEmpty(param.getSku())){
            queryWrapper.eq("sku",param.getSku());
        }
        if (param.getIsClaim()!=null){
            queryWrapper.eq("is_claim",param.getIsClaim());
        }

        if (param.getStatus()!=null){
            queryWrapper.eq("status",param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(MonitorGoogdwill::getPostedDate,startDate)
                    .lt(MonitorGoogdwill::getPostedDate,endDate);
        }
        return queryWrapper;
    }

}
