package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.constants.AccountMgtEnum;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.AccountMgtPersonalMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountMgtPersonalResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountMgtPersonalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 个人账户管理;(ACCOUNT_MGT_PERSONAL)表服务实现类
 *
 * @author : LSY
 * @date : 2023-11-10
 */
@Service
@Transactional
public class AccountMgtPersonalServiceImpl extends ServiceImpl<AccountMgtPersonalMapper, AccountMgtPersonal> implements AccountMgtPersonalService {
    @Resource
    private AccountMgtPersonalMapper accountMgtPersonalMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountMgtPersonal queryById(String id) {
        return accountMgtPersonalMapper.selectById(id);
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
    public Page<AccountMgtPersonalResult> paginQuery(AccountMgtPersonalParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<AccountMgtPersonal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccType()), AccountMgtPersonal::getAccType, param.getAccType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccNo()), AccountMgtPersonal::getAccNo, param.getAccNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccName()), AccountMgtPersonal::getAccName, param.getAccName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccBank()), AccountMgtPersonal::getAccBank, param.getAccBank());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAccCurrency()), AccountMgtPersonal::getAccCurrency, param.getAccCurrency());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getState()), AccountMgtPersonal::getState, param.getState());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getUserDepartment()), AccountMgtPersonal::getUserDepartment, param.getUserDepartment());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCustodyUserNo()), AccountMgtPersonal::getCustodyUserNo, param.getCustodyUserNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCustodyUserName()), AccountMgtPersonal::getCustodyUserName, param.getCustodyUserName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaintainerNo()), AccountMgtPersonal::getMaintainerNo, param.getMaintainerNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMaintainerName()), AccountMgtPersonal::getMaintainerName, param.getMaintainerName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCreatedBy()), AccountMgtPersonal::getCreatedBy, param.getCreatedBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getUpdatedBy()), AccountMgtPersonal::getUpdatedBy, param.getUpdatedBy());
        //2. 执行分页查询
        Page<AccountMgtPersonalResult> pagin = new Page<>(current, size, true);
        IPage<AccountMgtPersonalResult> selectResult = accountMgtPersonalMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param personalAccountMgt 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountMgtPersonal insert(AccountMgtPersonal personalAccountMgt) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        String userName = loginUser.getName();
        String account = loginUser.getAccount();

        personalAccountMgt.setCreatedBy(userName);
        personalAccountMgt.setCreatedTime(new Date());
        personalAccountMgt.setMaintainerName(userName);
        personalAccountMgt.setMaintainerNo(account);
        personalAccountMgt.setId(IdWorker.getIdStr());
        accountMgtPersonalMapper.insert(personalAccountMgt);
        return queryById(personalAccountMgt.getId());
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public AccountMgtPersonal update(AccountMgtPersonalParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AccountMgtPersonal> wrapper = new LambdaUpdateChainWrapper<AccountMgtPersonal>(accountMgtPersonalMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getUserDepartment()), AccountMgtPersonal::getUserDepartment, param.getUserDepartment());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCustodyUserNo()), AccountMgtPersonal::getCustodyUserNo, param.getCustodyUserNo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCustodyUserName()), AccountMgtPersonal::getCustodyUserName, param.getCustodyUserName());
        //作废或者禁用的情况
        if (ObjectUtil.isNotEmpty(param.getState())) {
            wrapper.set(AccountMgtPersonal::getState, param.getState());
            wrapper.set(AccountMgtPersonal::getCancelTime, new Date());
        }
        //2. 设置主键，并更新
        wrapper.eq(AccountMgtPersonal::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(AccountMgtPersonal::getMaintainerNo, loginUser.getAccount());
        wrapper.set(AccountMgtPersonal::getMaintainerName, loginUser.getName());
        wrapper.set(AccountMgtPersonal::getUpdatedTime, new Date());
        wrapper.set(AccountMgtPersonal::getUpdatedBy, loginUser.getName());
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
        int total = accountMgtPersonalMapper.deleteById(id);
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
        int delCount = accountMgtPersonalMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}