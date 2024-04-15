package com.tadpole.cloud.operationManagement.modular.shopEntity.Provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.shopEntity.AccountMgtPersonalApi;
import com.tadpole.cloud.operationManagement.api.shopEntity.constants.AccountMgtEnum;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountFlowService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountMgtPersonalService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AccountMgtPersonalProvider implements AccountMgtPersonalApi {

    @Resource
    private AccountMgtPersonalService accountMgtPersonalService;

    @Resource
    private AccountFlowService accountFlowService;

    @Resource
    private TbComEntityService comEntityService;


    @Override
    public List<AccountMgtPersonal> listAccountMgtPersonal(AccountMgtPersonalParam param) throws Exception{
        return accountMgtPersonalService.listAccountMgtPersonal(param);
    }

    @Override
    public ResponseData addOutFlow(AccountFlowParam accountFlow) throws Exception{
        accountFlow.setInOrOut(AccountMgtEnum.FLOW_OUT.getCode());
        return accountFlowService.addFlow(accountFlow);
    }

    @Override
    public ResponseData addInFlow(AccountFlowParam accountFlow) throws Exception{
        accountFlow.setInOrOut(AccountMgtEnum.FLOW_IN.getCode());
        return accountFlowService.addFlow(accountFlow);
    }

    @Override
    public List<TbComEntityResult> listCompanyAccount() throws Exception {
        return this.comEntityService.listCompanyAccount();
    }


}
