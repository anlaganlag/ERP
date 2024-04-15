package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportFinal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportFinalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportFinalResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementReportFinalMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportFinalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 结算报告 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class SettlementReportFinalServiceImpl extends ServiceImpl<SettlementReportFinalMapper, SettlementReportFinal> implements ISettlementReportFinalService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 批量确认结算报告标识
     */
    @Value("${rediskey.confirmSettlement}")
    public String confirmSettlement;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementReportFinalResult> findPageBySpec(SettlementReportFinalParam param) {
        Page pageContext = param.getPageContext();

        IPage<SettlementReportFinalResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public void confirm(SettlementReportFinalParam param) {

        QueryWrapper<SettlementReportFinal> qs = new QueryWrapper<>();
        qs.eq("id", param.getId());
        SettlementReportFinal check = this.baseMapper.selectOne(qs);
        if (check.getConfirmStatus().equals(BigDecimal.ZERO)) {
            SettlementReportFinal ss = new SettlementReportFinal();

            ss.setId(param.getId().toString());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
            ss.setConfirmDate(new Date());

            this.baseMapper.updateById(ss);

        }

    }

    @DataSource(name = "finance")
    @Override
    public ResponseData getFinalReport(SettlementReportFinalParam param) {
        try {
        if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
            return ResponseData.error("无会计区间!");
        }
        //按维度合并报告和调整报告
//        this.baseMapper.mergeAdjustReport(param);
        //刷字段


        return ResponseData.success();
    } catch(Exception e)    {
        log.error("生成失败!:" + e.getMessage());
        return ResponseData.error("生成失败!:" + e.getMessage());
    }

}

    @DataSource(name = "finance")
    @Override
    public ResponseData confirmBatch(SettlementReportFinalParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmSettlement);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<SettlementReportFinal> queryWrapper=new QueryWrapper<>();
            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            List<SettlementReportFinal> ss=this.list(queryWrapper);
            queryWrapper.clear();

            if (CollUtil.isEmpty(ss)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量保存
            for(SettlementReportFinal pa:ss){
                param.setId(new BigDecimal(pa.getId()));
                this.confirm(param);
            }

            return ResponseData.success(ss);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }
        //for (SettlementReportFinalParam param : params) {
        //    this.confirm(param);
        //}
    }

//    @DataSource(name = "finance")
//    @Override
//    public ResponseData refreshAmount(SettlementReportFinalParam param) {
//        //kindle广告费
//        this.baseMapper.updateKindleFee(param);
//        //海外税费
//        this.baseMapper.updateOverSeasFee(param);
//        //回款
//        this.baseMapper.updateCollecTionFee(param);
//        //Profit
//        this.baseMapper.updateProfitFee(param);
//        //刷退货数量
//        this.baseMapper.updateReturnAmount();
//        return ResponseData.success();
//    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refreshReturnAmount() {
        this.baseMapper.updateReturnAmount();
        return ResponseData.success();
    }



    @DataSource(name = "finance")
    @Override
    public List<SettlementReportFinalResult> export(SettlementReportFinalParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SettlementReportFinalResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public SettlementReportFinalResult getQuantity(SettlementReportFinalParam param) {
        return this.baseMapper.getQuantity(param);
    }
}
