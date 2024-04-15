package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.tadpole.cloud.product.api.productproposal.model.result.RdFeePayResult;
import com.tadpole.cloud.product.modular.consumer.ProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdFeePaymentMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdMoldOpenPfaMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFeePaymentService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdMoldOpenPfaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-开模费付款申请 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdFeePaymentServiceImpl implements IRdFeePaymentService {

    @Resource
    private RdFeePaymentMapper mapper;

    @Resource
    private ProposalServiceConsumer proposalServiceConsumer;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdFeePayResult> listRdFeePay() {
        return this.mapper.listRdFeePay();
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData listAccountMgtPersonal() throws Exception {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        AccountMgtPersonalParam param = new AccountMgtPersonalParam();
        List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(param);
        String account = loginUser.getAccount();
        List<AccountMgtPersonal> result = accountMgtPersonals.stream().filter(l->l.getState().equals("enable") && l.getAccType().equals("支付宝") && l.getCustodyUserNo().equals(account)).collect(Collectors.toList());
        return ResponseData.success(result);
    }
}
