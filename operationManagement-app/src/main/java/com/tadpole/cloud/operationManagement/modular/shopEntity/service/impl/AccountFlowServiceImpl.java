package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.constants.AccountMgtEnum;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountFlow;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.AccountFlowMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountFlowResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountFlowService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountMgtPersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 账户流水;(ACCOUNT_FLOW)表服务实现类
 *
 * @author : LSY
 * @date : 2023-11-10
 */
@Service
@Transactional
@Slf4j
public class AccountFlowServiceImpl extends ServiceImpl<AccountFlowMapper, AccountFlow> implements AccountFlowService {
    @Resource
    private AccountFlowMapper accountFlowMapper;

    @Resource
    private AccountMgtPersonalService personalService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountFlow queryById(String id) {
        return accountFlowMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public Page<AccountFlowResult> paginQuery(AccountFlowParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<AccountFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFlowId()), AccountFlow::getFlowId, param.getFlowId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBizFlowType()), AccountFlow::getBizFlowType, param.getBizFlowType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBizDataSources()), AccountFlow::getBizDataSources, param.getBizDataSources());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccountId()), AccountFlow::getAccountId, param.getAccountId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccountNo()), AccountFlow::getAccountNo, param.getAccountNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPaymentAccount()), AccountFlow::getPaymentAccount, param.getPaymentAccount());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCurrency()), AccountFlow::getCurrency, param.getCurrency());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCreaterDepartment()), AccountFlow::getCreaterDepartment, param.getCreaterDepartment());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCreatedBy()), AccountFlow::getCreatedBy, param.getCreatedBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCreatedByNo()), AccountFlow::getCreatedByNo, param.getCreatedByNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getUpdatedBy()), AccountFlow::getUpdatedBy, param.getUpdatedBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getUpdatedByNo()), AccountFlow::getUpdatedByNo, param.getUpdatedByNo());
        queryWrapper.eq( AccountFlow::getIsDelete, 0);
        queryWrapper.orderByDesc( AccountFlow::getCreatedTime);
        //2. 执行分页查询
        Page<AccountFlowResult> pagin = new Page<>(current, size, true);
        IPage<AccountFlowResult> selectResult = accountFlowMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountFlow insert(AccountFlow accountFlow) {
        accountFlowMapper.insert(accountFlow);
        return accountFlow;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountFlow update(AccountFlowParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AccountFlow> wrapper = new LambdaUpdateChainWrapper<AccountFlow>(accountFlowMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getFlowId()), AccountFlow::getFlowId, param.getFlowId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBizFlowType()), AccountFlow::getBizFlowType, param.getBizFlowType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBizDataSources()), AccountFlow::getBizDataSources, param.getBizDataSources());
        wrapper.set(ObjectUtil.isNotEmpty(param.getAccountId()), AccountFlow::getAccountId, param.getAccountId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getAccountNo()), AccountFlow::getAccountNo, param.getAccountNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPaymentAccount()), AccountFlow::getPaymentAccount, param.getPaymentAccount());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCurrency()), AccountFlow::getCurrency, param.getCurrency());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCreaterDepartment()), AccountFlow::getCreaterDepartment, param.getCreaterDepartment());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCreatedBy()), AccountFlow::getCreatedBy, param.getCreatedBy());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCreatedByNo()), AccountFlow::getCreatedByNo, param.getCreatedByNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getUpdatedByNo()), AccountFlow::getUpdatedByNo, param.getUpdatedByNo());
        //2. 设置主键，并更新
        wrapper.eq(AccountFlow::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(AccountFlow::getUpdatedTime, new Date());
        wrapper.set(AccountFlow::getUpdatedBy, loginUser.getName());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(String id) {
        int total = accountFlowMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<String> idList) {
        int delCount = accountFlowMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData addFlow(AccountFlowParam accountFlowParam) {

        if (accountFlowParam.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseData.error("流水金额应大于0");
        }

        String userName = "System";
        String account = "System";
        LoginUser loginUser;

        try {
            loginUser = LoginContext.me().getLoginUser();
            if (ObjectUtil.isNotNull(loginUser)) {
                userName = loginUser.getName();
                account = loginUser.getAccount();
            }
        } catch (Exception e) {
            log.error("账户充值--获取登录信息异常-充值信息：{}--异常信息：{}", JSON.toJSONString(accountFlowParam), JSON.toJSONString(e));
            return ResponseData.error(500, "账户充值-获取登录信息异常：{}", JSON.toJSONString(e));
        }

        //资金账户操作上锁
        AccountMgtPersonal accountMgtPersonal = personalService.queryById(accountFlowParam.getAccountId());
        if (ObjectUtil.isNull(accountMgtPersonal)) {
            return ResponseData.error("accountId传参不对");
        }
        if (! accountMgtPersonal.getState().equals(AccountMgtEnum.ACCOUNT_STATE_ENABLE.getName())) {
            return ResponseData.error("账户未启用，数据ID:"+accountMgtPersonal.getId()+"账号:"+accountMgtPersonal.getAccNo());
        }
        if (accountMgtPersonal.getDepositAmount().equals(BigDecimal.ZERO)) {
            //预存金额为0
            if ( ! AccountMgtEnum.BIZ_FLOW_TYPE_DEPOSIT_AMOUNT.getName().equals(accountFlowParam.getBizFlowType())) {
                //非预存金额的流水，需要先预存资金
                return ResponseData.error("账户预存金额为0,请先预存资金,业务流水类型 bizFlowType = deposit_amount");
            }
        }
        if (accountFlowParam.getInOrOut().equals(AccountMgtEnum.FLOW_OUT.getCode())) {
            if (AccountMgtEnum.BIZ_FLOW_TYPE_DEPOSIT_AMOUNT.getName().equals(accountFlowParam.getBizFlowType())) {

                if (accountMgtPersonal.getDepositAmount().compareTo(accountFlowParam.getAmount())<0) {
                    log.error("账户支出--账户预存余额不够支出-余额【{}】--支出金额【{}】", accountMgtPersonal.getDepositAmount(), accountFlowParam.getAmount());
                    return ResponseData.error("账户【预存余额】不够支出");
                }
                log.error("账户支出--账户实时可用余额不够支出-余额【{}】--支出金额【{}】", accountMgtPersonal.getAmount(), accountFlowParam.getAmount());
                return ResponseData.error("账户【实时余额】不够支出");
            }

            if (accountMgtPersonal.getAmount().compareTo(accountFlowParam.getAmount())<0) {
                log.error("账户支出--账户实时可用余额不够支出-余额【{}】--支出金额【{}】", accountMgtPersonal.getAmount(), accountFlowParam.getAmount());
                return ResponseData.error("账户实时可用余额不够支出");
            }
        }


        BigDecimal needAddAmount = BigDecimal.ZERO;
        if (ObjectUtil.isEmpty(accountFlowParam.getCurrency()) || accountFlowParam.getCurrency().equals(accountMgtPersonal.getAccCurrency())) {
            //币别为空默认为账户币别 或者账户币别等于流水币别
            needAddAmount =  accountFlowParam.getAmount().multiply(BigDecimal.valueOf(accountFlowParam.getInOrOut()));
        } else {
            //币别不为空 或者 账户币别不等于流水币别  汇率默认是1
            BigDecimal rate = ObjectUtil.isNull(accountFlowParam.getRate()) ? BigDecimal.ONE : accountFlowParam.getRate();
            needAddAmount = accountFlowParam.getAmount().multiply(rate).multiply(BigDecimal.valueOf(accountFlowParam.getInOrOut()));
        }

        accountMgtPersonal.setAmount(accountMgtPersonal.getAmount().add(needAddAmount));
        //预存金额
        if (AccountMgtEnum.BIZ_FLOW_TYPE_DEPOSIT_AMOUNT.getName().equals(accountFlowParam.getBizFlowType())) {
            accountMgtPersonal.setDepositAmount(accountMgtPersonal.getDepositAmount().add(needAddAmount));
        }
        accountFlowParam.setAccountBalance(accountMgtPersonal.getAmount());

        AccountFlow accountFlow = BeanUtil.copyProperties(accountFlowParam, AccountFlow.class);
        accountFlow.setId(IdWorker.getIdStr());
        accountFlow.setCreatedBy(userName);
        accountFlow.setCreatedByNo(account);
        accountFlow.setCreatedTime(new Date());
        accountFlow.setCreaterDepartment(loginUser.getDepartment());
        accountFlow.setAccountNo(accountMgtPersonal.getAccNo());
        accountFlow.setAccountNo(accountMgtPersonal.getAccNo());
        if (ObjectUtil.isEmpty(accountFlow.getFlowId())) {//没有设置业务流水ID默认等于数据ID
            accountFlow.setFlowId(accountFlow.getId());
        }

        personalService.updateById(accountMgtPersonal);
        accountFlowMapper.insert(accountFlow);
        return ResponseData.success(accountFlow.getId());
    }

}