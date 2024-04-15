package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsAccount;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsAccountMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsAccountParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsAccountResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物流账号;(tb_logistics_account)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsAccountServiceImpl  extends ServiceImpl<TbLogisticsAccountMapper, TbLogisticsAccount> implements TbLogisticsAccountService{
    @Resource
    private TbLogisticsAccountMapper tbLogisticsAccountMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lcCode 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsAccount queryById(String lcCode){
        return tbLogisticsAccountMapper.selectById(lcCode);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsAccountResult> paginQuery(TbLogisticsAccountParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsAccount::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsAccount::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getComNameCn()),TbLogisticsAccount::getComNameCn, param.getComNameCn());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLcState()),TbLogisticsAccount::getLcState, param.getLcState());
        //2. 执行分页查询
        Page<TbLogisticsAccountResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsAccountResult> selectResult = tbLogisticsAccountMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsAccount 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsAccount insert(TbLogisticsAccount tbLogisticsAccount){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        tbLogisticsAccount.setSysAddDate(DateUtil.date());
        tbLogisticsAccount.setSysPerName(loginUser.getName());
        tbLogisticsAccountMapper.insert(tbLogisticsAccount);
        return tbLogisticsAccount;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsAccount update(TbLogisticsAccountParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsAccount> wrapper = new LambdaUpdateChainWrapper<TbLogisticsAccount>(tbLogisticsAccountMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsAccount::getLpCode, param.getLpCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComNameCn()),TbLogisticsAccount::getComNameCn, param.getComNameCn());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLcState()),TbLogisticsAccount::getLcState, param.getLcState());
        wrapper.set(TbLogisticsAccount::getSysUpdDatetime, DateUtil.date());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(TbLogisticsAccount::getSysPerName, loginUser.getName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsAccount::getLcCode, param.getLcCode());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLcCode());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lcCode 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String lcCode){
        int total = tbLogisticsAccountMapper.deleteById(lcCode);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lcCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> lcCodeList) {
        int delCount = tbLogisticsAccountMapper.deleteBatchIds(lcCodeList);
        if (lcCodeList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "logistics")
    @Override
    public List<String> lcCodeList(String lcState) {
        List<String> resultList = new ArrayList<>();
        LambdaQueryWrapper<TbLogisticsAccount> wrapper = new LambdaQueryWrapper<TbLogisticsAccount>();
        if (ObjectUtil.isNotEmpty(lcState)) {
            wrapper.eq(TbLogisticsAccount::getLcState, lcState);
        }
        wrapper.isNotNull(TbLogisticsAccount::getLcState);
        List<TbLogisticsAccount> accountList = tbLogisticsAccountMapper.selectList(wrapper);

        if (ObjectUtil.isNotEmpty(accountList)) {
             resultList=  accountList.stream().map(TbLogisticsAccount::getLcCode).collect(Collectors.toList());
        }
        return resultList;

    }
}