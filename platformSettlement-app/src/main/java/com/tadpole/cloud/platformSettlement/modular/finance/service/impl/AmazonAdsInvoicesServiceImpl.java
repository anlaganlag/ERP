package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonAdsInvoices;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.AmazonAdsInvoicesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AmazonAdsInvoicesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AmazonAdsInvoicesTotal;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AmazonAdsInvoicesMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IAmazonAdsInvoicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* <p>
    * Amazon广告费用账单 服务实现类
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
@Service
public class AmazonAdsInvoicesServiceImpl extends ServiceImpl<AmazonAdsInvoicesMapper, AmazonAdsInvoices> implements IAmazonAdsInvoicesService {

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private AmazonAdsInvoices total=new AmazonAdsInvoices();
    @DataSource(name = "finance")
    @Override
    public PageTotalResult<AmazonAdsInvoicesResult, AmazonAdsInvoices> findPageBySpec(AmazonAdsInvoicesParam param) {
        Page pageContext = getPageContext();



        IPage<AmazonAdsInvoicesResult> page =  this.baseMapper.selecMyPage(pageContext,param);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1){
            QueryWrapper<AmazonAdsInvoices> queryWrapper=queryWrapper(param);
            //查询汇总统计
            queryWrapper.select("sum(amount) amount",
                    "sum(tax) tax"
            );
             total = this.baseMapper.selectOne(queryWrapper);

        }
        return new PageTotalResult<>(page,total);
    }
    @DataSource(name = "finance")
    @Override
    public List<AmazonAdsInvoicesResult> export(AmazonAdsInvoicesParam param) {
       List<AmazonAdsInvoicesResult> list =  this.baseMapper.export(param);
      return  list;
    }

    @Override
    public void afreshStorageFee() {

    }

    private QueryWrapper<AmazonAdsInvoices> queryWrapper(AmazonAdsInvoicesParam param){
        QueryWrapper<AmazonAdsInvoices> queryWrapper=new QueryWrapper<>();

        if (param.getAccountName() !=null && param.getAccountName().size()>0){
            queryWrapper.in("account_name",param.getAccountName());
        }
        if (param.getCountryCode()!=null && param.getCountryCode().size()>0){
            queryWrapper.in("country_code",param.getCountryCode());
        }

        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())){
            Date startDate = DateUtil.parse(param.getStartDate(), "yyyy-MM-dd");
            Date endDate= DateUtil.parse(param.getEndDate(), "yyyy-MM-dd");
            queryWrapper.lambda().ge(AmazonAdsInvoices::getInvoiceDate,startDate)
                    .lt(AmazonAdsInvoices::getInvoiceDate,endDate);
        }
        return queryWrapper;
    }
}
