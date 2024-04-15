package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatSalesDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.GenerateStatusEnum;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.VatSalesDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatSalesDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatSalesDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatSalesDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 税金测算Sales明细 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
@Slf4j
public class VatSalesDetailServiceImpl extends ServiceImpl<VatSalesDetailMapper, VatSalesDetail> implements IVatSalesDetailService {

    @Autowired
    private VatSalesDetailMapper mapper;

    @DataSource(name = "finance")
    @Override
    public PageResult<VatSalesDetailResult> queryListPage(VatSalesDetailParam param) {
        Page pageContext = param.getPageContext();
        IPage<VatSalesDetailResult> page = this.baseMapper.queryListPage(pageContext, param);
        return new PageResult<>(page);
    }

    @Override
    @DataSource(name = "finance")
    public List<VatSalesDetailResult> export(VatSalesDetailParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        List<VatSalesDetailResult> records = this.baseMapper.queryListPage(pageContext, param).getRecords();
        return records;
    }

    @Override
    @DataSource(name = "finance")
    public VatSalesDetailResult getQuantity(VatSalesDetailParam param) {
        VatSalesDetailResult detailToTal = this.baseMapper.listSum(param);
        return detailToTal;
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateVatDetail(VatSalesDetailParam param) {
        String account=LoginContext.me().getLoginUser().getAccount();
        if(StrUtil.isEmpty(param.getActivityPeriod())){
            return ResponseData.error("期间值不为空！");
        }

        param.setCreatePerson(LoginContext.me().getLoginUser().getAccount());
        param.setGenerateStatus(new BigDecimal(GenerateStatusEnum.DEFAULT.getCode()));

        //汇率是否维护检查
        List<VatSalesDetailResult> list = this.baseMapper.notTaxRate(param);
        if(list.size()>0){
            return ResponseData.error(500,"税率表未维护",list);
        }

        try{
            //生成VAT明细
            this.baseMapper.generateVatDetail(param);

            //状态变更->已生成
            LambdaUpdateWrapper<VatSalesDetail> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(VatSalesDetail::getActivityPeriod,param.getActivityPeriod())
                    .eq(VatSalesDetail::getGenerateStatus, GenerateStatusEnum.DEFAULT.getCode())
                    .set(VatSalesDetail::getGenerateStatus,GenerateStatusEnum.SUCCESS.getCode())
                    .set(VatSalesDetail::getUpdatePerson,account)
                    .set(VatSalesDetail::getUpdateTime,new Date());
            mapper.update(null,updateWrapper);

            return ResponseData.success("生成成功！");
        }catch(Exception e){
            log.error("生成VAT明细失败！ ",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("生成失败！");
        }
    }

}
