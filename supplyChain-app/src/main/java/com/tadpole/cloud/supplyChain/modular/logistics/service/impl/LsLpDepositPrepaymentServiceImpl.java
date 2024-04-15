package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsProvider;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepayment;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepaymentRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLpDepositPrepaymentMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsProviderService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLpDepositPrepaymentRecordService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLpDepositPrepaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 物流商押金&预付 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@Service
public class LsLpDepositPrepaymentServiceImpl extends ServiceImpl<LsLpDepositPrepaymentMapper, LsLpDepositPrepayment> implements ILsLpDepositPrepaymentService {

    @Resource
    private LsLpDepositPrepaymentMapper mapper;
    @Autowired
    private ILsLogisticsProviderService logisticsProviderService;
    @Autowired
    private ILsLpDepositPrepaymentRecordService recordService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLpDepositPrepaymentParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(LsLpDepositPrepaymentParam param) {
        if((StringUtils.isBlank(param.getDepositCurrency()) || param.getDepositAmt() == null) && (StringUtils.isBlank(param.getPaymentCurrency()) || param.getPrepaymentAmt() == null)){
            return ResponseData.error("押金和预付款至少填一项值，新增失败！");
        }

        LambdaQueryWrapper<LsLpDepositPrepayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LsLpDepositPrepayment :: getLpCode, param.getLpCode())
                .eq(LsLpDepositPrepayment :: getPaymentCurrency, param.getPaymentCurrency());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此物流商押金&预付款信息，新增失败！");
        }

        LambdaQueryWrapper<LsLogisticsProvider> lpQueryWrapper = new LambdaQueryWrapper<>();
        lpQueryWrapper.eq(LsLogisticsProvider :: getLpCode, param.getLpCode());
        //物流商管理没有的先新增
        if(logisticsProviderService.count(lpQueryWrapper) == 0){
            LsLogisticsProvider lp = new LsLogisticsProvider();
            lp.setLpCode(param.getLpCode());
            lp.setLpName(param.getLpName());
            lp.setLpSimpleName(param.getLpSimpleName());
            lp.setLpAddress(param.getLpAddress());
            lp.setLpUniSocCreCode(param.getLpUniSocCreCode());
            lp.setUpdateTime(param.getUpdateTime());
            logisticsProviderService.save(lp);
        }

        LsLpDepositPrepayment insertDp = new LsLpDepositPrepayment();
        BeanUtils.copyProperties(param, insertDp);
        String name = LoginContext.me().getLoginUser().getName();
        insertDp.setCreateUser(name);
        this.save(insertDp);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(LsLpDepositPrepaymentParam param) {
        LsLpDepositPrepayment depositPrepayment = this.getById(param.getId());
        if(depositPrepayment == null){
            return ResponseData.error("不存在物流商押金&预付记录！");
        }
        LambdaQueryWrapper<LsLpDepositPrepaymentRecord> recordQW = new LambdaQueryWrapper<>();
        recordQW.eq(LsLpDepositPrepaymentRecord :: getPid, depositPrepayment.getId())
                .eq(LsLpDepositPrepaymentRecord :: getOptType, "付款");
        //存在物流商押金&预付扣除记录的不允许删除
        if(recordService.count(recordQW) > 0){
            return ResponseData.error("存在物流商押金&预付付款记录的不允许操作删除！");
        }
        this.removeById(param.getId());
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(LsLpDepositPrepaymentParam param) {
        if((StringUtils.isBlank(param.getDepositCurrency()) || param.getDepositAmt() == null) && (StringUtils.isBlank(param.getPaymentCurrency()) || param.getPrepaymentAmt() == null)){
            return ResponseData.error("押金和预付款至少填一项值，新增失败！");
        }
        LsLpDepositPrepayment oldDepositPrepayment = this.getById(param.getId());
        if(oldDepositPrepayment == null){
            return ResponseData.error("不存在此物流商押金&预付记录！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        LambdaUpdateWrapper<LsLpDepositPrepayment> prepaymentUw = new LambdaUpdateWrapper<>();
        prepaymentUw.eq(LsLpDepositPrepayment :: getId, oldDepositPrepayment.getId())
                .set(LsLpDepositPrepayment :: getDepositAmt, param.getDepositAmt() == null ? "" : param.getDepositAmt())
                .set(LsLpDepositPrepayment :: getDepositCurrency, param.getDepositCurrency() == null ? "" : param.getDepositCurrency())
                .set(LsLpDepositPrepayment :: getPrepaymentAmt, param.getPrepaymentAmt() == null ? "" : param.getPrepaymentAmt())
                .set(LsLpDepositPrepayment :: getPaymentCurrency, param.getPaymentCurrency() == null ? "" : param.getPaymentCurrency())
                .set(LsLpDepositPrepayment :: getRemark, param.getRemark() == null ? "" : param.getRemark())
                .set(LsLpDepositPrepayment :: getUpdateTime, DateUtil.date())
                .set(LsLpDepositPrepayment :: getUpdateUser, name);
        this.update(prepaymentUw);

        LsLpDepositPrepaymentRecord record = new LsLpDepositPrepaymentRecord();
        record.setPid(param.getId());
        record.setLpCode(oldDepositPrepayment.getLpCode());
        record.setCreateUser(name);
        record.setOptType("编辑");
        StringBuffer sb = new StringBuffer();
        sb.append("编辑");
        BigDecimal paramDepositAmt = param.getDepositAmt() == null ? BigDecimal.ZERO : param.getDepositAmt();
        BigDecimal oldDepositAmt = oldDepositPrepayment.getDepositAmt() == null ? BigDecimal.ZERO : oldDepositPrepayment.getDepositAmt();
        if(paramDepositAmt.compareTo(oldDepositAmt) != 0){
            sb.append("【押金】").append(oldDepositPrepayment.getDepositAmt()).append("->").append(param.getDepositAmt()).append("，");
        }
        if(StringUtils.compare(oldDepositPrepayment.getDepositCurrency(), param.getDepositCurrency()) != 0){
            sb.append("【押金币种】").append(oldDepositPrepayment.getDepositCurrency()).append("->").append(param.getDepositCurrency()).append("，");
        }
        BigDecimal paramPrepaymentAmt = param.getPrepaymentAmt() == null ? BigDecimal.ZERO : param.getPrepaymentAmt();
        BigDecimal oldPrepaymentAmt = oldDepositPrepayment.getPrepaymentAmt() == null ? BigDecimal.ZERO : oldDepositPrepayment.getPrepaymentAmt();
        if(paramPrepaymentAmt.compareTo(oldPrepaymentAmt) != 0){
            sb.append("【预付款】").append(oldDepositPrepayment.getPrepaymentAmt()).append("->").append(param.getPrepaymentAmt()).append("，");
        }
        if(StringUtils.compare(oldDepositPrepayment.getPaymentCurrency(), param.getPaymentCurrency()) != 0){
            sb.append("【预付款币种】").append(oldDepositPrepayment.getPaymentCurrency()).append("->").append(param.getPaymentCurrency()).append("，");
        }
        if(StringUtils.compare(oldDepositPrepayment.getRemark(), param.getRemark()) != 0){
            sb.append("【备注】").append(oldDepositPrepayment.getRemark()).append("->").append(param.getRemark()).append("，");
        }
        String optDetail = sb.toString();
        if("，".equals(optDetail.substring(optDetail.length() - 1))){
            optDetail = optDetail.substring(0, optDetail.length() - 1);
        }
        record.setOptDetail(optDetail);
        recordService.save(record);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLpDepositPrepaymentResult> export(LsLpDepositPrepaymentParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

}
