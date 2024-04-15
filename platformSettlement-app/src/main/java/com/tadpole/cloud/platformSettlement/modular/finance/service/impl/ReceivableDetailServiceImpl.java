package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.model.exp.ServiceException;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReceivableDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReceivableDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReceivableDetailDetailService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReceivableDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* <p>
* 应收明细 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class ReceivableDetailServiceImpl extends ServiceImpl<ReceivableDetailMapper, ReceivableDetail> implements IReceivableDetailService {

    @Autowired
    IReceivableDetailService receivableDetailService;

    @Autowired
    IReceivableDetailDetailService receivableDetailDetailService;

    @Resource
    InitialBalanceMapper initialBalanceMapper;

    @Resource
    ReceivableDetailMapper receivableDetailMapper;

    @Resource
    ReceivableDetailDetailMapper receivableDetailDetailMapper;

    @Resource
    StatementIncomeMapper statementIncomeMapper;

    @Resource
    PaymentConfirmHandlingMapper paymentConfirmHandlingMapper;

    @DataSource(name = "finance")
    @Override
    public void generateReceivable(ReceivableDetail param) {
        this.baseMapper.generateReceivable(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<ReceivableDetailResult> queryListPage(ReceivableDetail param) {
        //param初始param 空时推荐最早的审核
        if(param.getShopName()==null){
            ReceivableDetail detail = this.baseMapper.getEarly();
            if(detail!=null){
                param.setShopName(detail.getShopName());
                param.setSite(detail.getSite());
                param.setFiscalPeriod(detail.getFiscalPeriod());
            }else{
                //如果审核完了,默认一条已审核的
                ReceivableDetail detailCheck = this.baseMapper.getEarlyCheck();
                param.setShopName(detailCheck.getShopName());
                param.setSite(detailCheck.getSite());
                param.setFiscalPeriod(detailCheck.getFiscalPeriod());
            }
        }

        return this.baseMapper.queryListPage(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<ReceivableDetailResult> list(ReceivableDetailParam param) {
        return this.baseMapper.list(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<ReceivableDetail> verifyList(ReceivableDetailParam param) {
        return this.baseMapper.verifyList(param);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData verify(ReceivableDetail param) {

        if (param.getId() == null || "0".equals(param.getId().toString())) {
            return ResponseData.error("ID不能为空");
        }
        ReceivableDetail receivableDetail = this.baseMapper.selectById(param.getId());

        if (receivableDetail.getFiscalPeriod() == null)
        {
            return ResponseData.error("会计期间不能为空");
        }

        //会计期间日期转换
        String curDateStr=receivableDetail.getFiscalPeriod()+"-01";
        Date date= DateUtil.parse(curDateStr);

        DateTime oldDate=DateUtil.offsetMonth(date,-1);
        DateTime newDate=DateUtil.offsetMonth(date,1);
        String premonth=DateUtil.format(oldDate,"yyyy-MM");
        String nextmonth=DateUtil.format(newDate,"yyyy-MM");

        //查询上个会计期间期末应收余额
        QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM_NAME",receivableDetail.getPlatformName()).eq("SHOP_NAME",receivableDetail.getShopName())
                .eq("SITE",receivableDetail.getSite())
                .eq("FISCAL_PERIOD",premonth)
                .eq("STATUS",0);
        ReceivableDetail  prereceivableDetail = this.baseMapper.selectOne(queryWrapper);
        queryWrapper.clear();

        //效验
        if (prereceivableDetail!=null)
        {
            return ResponseData.error("上个会计期间应收明细未审核！");
        }

        //查询未审核收入记录
        QueryWrapper<StatementIncome> incomeQueryWrapper = new QueryWrapper<>();
        incomeQueryWrapper.eq("PLATFORM_NAME",receivableDetail.getPlatformName())
                .eq("SHOP_NAME",receivableDetail.getShopName())
                .eq("SITE",receivableDetail.getSite())
                .eq("FISCAL_PERIOD",receivableDetail.getFiscalPeriod())
                .and(wq->wq
                        .eq("SYNC_STATUS",0)
                        .or()
                        .eq("SYNC_STATUS",1));
        List<StatementIncome> income = statementIncomeMapper.selectList(incomeQueryWrapper);
        if (income.size()>0)
        {
            return ResponseData.error("存在收入记录未审核！");
        }

        //查询未审核回款确认
        QueryWrapper<PaymentConfirmHandling> payQueryWrapper = new QueryWrapper<>();
        payQueryWrapper.eq("PLATFORM_NAME",receivableDetail.getPlatformName())
                .eq("SHOP_NAME",receivableDetail.getShopName())
                .eq("SITE",receivableDetail.getSite())
                .eq("FISCAL_PERIOD",receivableDetail.getFiscalPeriod())
                .and(wq->wq
                .eq("VERIFY_STATUS",0)
                .or()
                .eq("VERIFY_STATUS",1));
        List<PaymentConfirmHandling> paymentConfirm = paymentConfirmHandlingMapper.selectList(payQueryWrapper);
        if (paymentConfirm.size()>0)
        {
            return ResponseData.error("存在回款确认未审核！");
        }
        //更新审核状态
        UpdateWrapper<ReceivableDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId()).set("STATUS",1);
        this.baseMapper.update(null,updateWrapper);

        //生成下个会计期间应收明细头部信息
        queryWrapper.eq("PLATFORM_NAME",receivableDetail.getPlatformName()).eq("SHOP_NAME",receivableDetail.getShopName())
                .eq("SITE",receivableDetail.getSite()).eq("FISCAL_PERIOD",nextmonth);
        ReceivableDetail  nextreceivableDetail = this.baseMapper.selectOne(queryWrapper);

        if(nextreceivableDetail==null)
        {
            ReceivableDetail detailParam = new ReceivableDetail();
            detailParam.setPlatformName(receivableDetail.getPlatformName());
            detailParam.setShopName(receivableDetail.getShopName());
            detailParam.setSite(receivableDetail.getSite());
            detailParam.setFiscalPeriod(nextmonth);
            detailParam.setInitialReceiveAmount(receivableDetail.getEndtermReceivableAmount());
            detailParam.setReceivableAmount(BigDecimal.ZERO);
            detailParam.setReceiveAmount(BigDecimal.ZERO);
            detailParam.setEndtermReceivableAmount(receivableDetail.getEndtermReceivableAmount());
            this.save(getEntity(detailParam));
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refresh(ReceivableDetail detailParam) throws Exception {
        //应收明细主表ID
        QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());
        ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);


        //更新应收合计金额
        UpdateWrapper<ReceivableDetail> updateWrapper=new UpdateWrapper<>();
        //更新应收明细
        UpdateWrapper<ReceivableDetailDetail> updateDetailWrapper=new UpdateWrapper<>();

        //期初余额
        QueryWrapper<InitialBalance> inbqueryWrapper=new QueryWrapper<>();
        inbqueryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());
        InitialBalance initialBalance = initialBalanceMapper.selectOne(inbqueryWrapper);

        //日期转换
        DateFormat format2=new SimpleDateFormat("yyyy-MM");
        Date date=new Date();
        try{
            date=format2.parse(detailParam.getFiscalPeriod());
        }catch(Exception e){
            e.printStackTrace();
        }
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        c.add(Calendar.MONTH,-1);
        String premonth=format.format(c.getTime());

        //上月会计期间期末应收余额
        QueryWrapper<ReceivableDetail> preAccountWrapper=new QueryWrapper<>();
        preAccountWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",premonth);
        ReceivableDetail  preEndreceivable = receivableDetailService.getBaseMapper().selectOne(preAccountWrapper);

        QueryWrapper<ReceivableDetailDetail> detailqueryWrapper=new QueryWrapper<>();
        QueryWrapper<ReceivableDetailDetail> amountqueryWrapper=new QueryWrapper<>();
        detailqueryWrapper.eq("RECEIVABLE_ID",receivableDetail.getId())
                .isNull("SETTLEMENT_NO")
                .orderByAsc("ID");
        List<ReceivableDetailDetail> list=receivableDetailDetailMapper.selectList(detailqueryWrapper);
        //清空
        detailqueryWrapper.clear();
       if(list.size()>0){
           for(int i=0;i<list.size();i++) {
               BigDecimal settmentNo=BigDecimal.ZERO;
               BigDecimal amountBalance=BigDecimal.ZERO;
               BigDecimal InitialReceiveAmount=BigDecimal.ZERO;
               //应收明细会计期间最大结算单序号
               BigDecimal maxNumber=receivableDetailMapper.selectMaxNumber(receivableDetail.getId());

               detailqueryWrapper.clear();
               detailqueryWrapper.eq("RECEIVABLE_ID",receivableDetail.getId())
                       .eq("SETTLEMENT_NO",maxNumber==null?BigDecimal.ZERO:maxNumber)
                       .orderByDesc("SETTLEMENT_NO");
               ReceivableDetailDetail receiDetail = receivableDetailDetailMapper.selectOne(detailqueryWrapper
                       .apply("rownum={0}",1));

               //应收明细历史结算期末余额
               ReceivableDetailDetail prereceiDetail = receivableDetailDetailMapper.selectOne(detailqueryWrapper
                       .eq("SETTLEMENT_NO",receiDetail==null?BigDecimal.ZERO:receiDetail.getSettlementNo().subtract(BigDecimal.ONE))
                       .orderByDesc("CREATE_AT").apply("rownum={0}",1));

               //结算单序号
                if(receiDetail!=null && receiDetail.getSettlementNo()!=null)
                {
                        //历史结算序号期末余额
                        amountBalance = (list.get(i).getCurrentReserveAmount()!=null?list.get(i).getCurrentReserveAmount():BigDecimal.ZERO)
                                .add(receiDetail.getBalance()!=null?receiDetail.getBalance():BigDecimal.ZERO)
                                .add(list.get(i).getSuccessfulCharge()!=null?list.get(i).getSuccessfulCharge():BigDecimal.ZERO)
                                .subtract(list.get(i).getReceiveAmount()!=null?list.get(i).getReceiveAmount():BigDecimal.ZERO);


                    settmentNo=receiDetail.getSettlementNo().add(BigDecimal.ONE);
                }
                else
                {
                    //上月期末余额
                    if(preEndreceivable!=null && preEndreceivable.getEndtermReceivableAmount()!=null)
                    {
                        amountBalance=(list.get(i).getCurrentReserveAmount()!=null?list.get(i).getCurrentReserveAmount():BigDecimal.ZERO)
                                .add(preEndreceivable.getEndtermReceivableAmount())
                                .add(list.get(i).getSuccessfulCharge()!=null?list.get(i).getSuccessfulCharge():BigDecimal.ZERO)
                                .subtract(list.get(i).getReceiveAmount()!=null?list.get(i).getReceiveAmount():BigDecimal.ZERO);
                        InitialReceiveAmount=preEndreceivable.getEndtermReceivableAmount();
                    }
                    else
                    {
                        //期初余额
                        if(initialBalance!=null)
                        {
                            amountBalance=(list.get(i).getCurrentReserveAmount()!=null?list.get(i).getCurrentReserveAmount():BigDecimal.ZERO)
                                    .add(initialBalance.getInitialBalance())
                                    .add(list.get(i).getSuccessfulCharge()!=null?list.get(i).getSuccessfulCharge():BigDecimal.ZERO)
                                    .subtract(list.get(i).getReceiveAmount()!=null?list.get(i).getReceiveAmount():BigDecimal.ZERO);
                            InitialReceiveAmount=initialBalance.getInitialBalance();
                        }
                        else
                        {
                            throw new ServiceException(500, "未设置期初余额！");
                        }
                    }
                    settmentNo=BigDecimal.ZERO;

                    //期初应收金额
                    updateWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName())
                            .eq("SHOP_NAME",detailParam.getShopName())
                            .eq("SITE",detailParam.getSite())
                            .eq("FISCAL_PERIOD",detailParam.getFiscalPeriod())
                            .set("INITIAL_RECEIVE_AMOUNT",InitialReceiveAmount);
                    receivableDetailMapper.update(null,updateWrapper);
                    updateWrapper.clear();
                }
                //应收明细刷结算单序号、期末应收
               updateDetailWrapper.eq("RECEIVABLE_ID",list.get(i).getReceivableId())
                       .eq("INCOME_TYPE",list.get(i).getIncomeType())
                       .eq("ID",list.get(i).getId())
                       .set("SETTLEMENT_NO",settmentNo)
                       .set("BALANCE",amountBalance!=null?amountBalance:BigDecimal.ZERO);
               receivableDetailDetailMapper.update(null,updateDetailWrapper);
               updateDetailWrapper.clear();

               //应收明细金额合计
               amountqueryWrapper.select("nvl(sum(CURRENT_RESERVE_AMOUNT),0) as CURRENT_RESERVE_AMOUNT,nvl(sum(RECEIVE_AMOUNT),0) as RECEIVE_AMOUNT ,nvl(sum(SUCCESSFUL_CHARGE),0) as SUCCESSFUL_CHARGE");
               amountqueryWrapper.eq("RECEIVABLE_ID",receivableDetail.getId());
                ReceivableDetailDetail detailDetail = receivableDetailDetailService.getOne(amountqueryWrapper);
               //期末应收金额、本期应收合计
               updateWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName())
                       .eq("SHOP_NAME",detailParam.getShopName())
                       .eq("SITE",detailParam.getSite())
                       .eq("FISCAL_PERIOD",detailParam.getFiscalPeriod())
                       .set("RECEIVABLE_AMOUNT",detailDetail.getCurrentReserveAmount().add(detailDetail.getSuccessfulCharge()))
                       .set("RECEIVE_AMOUNT",receivableDetailDetailService.getOne(amountqueryWrapper).getReceiveAmount())
                       .set("ENDTERM_RECEIVABLE_AMOUNT",amountBalance!=null?amountBalance:BigDecimal.ZERO);
               receivableDetailMapper.update(null,updateWrapper);
               updateWrapper.clear();

           }
       }

    }

    private ReceivableDetail getEntity(ReceivableDetail param) {
        ReceivableDetail entity = new ReceivableDetail();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }

}
