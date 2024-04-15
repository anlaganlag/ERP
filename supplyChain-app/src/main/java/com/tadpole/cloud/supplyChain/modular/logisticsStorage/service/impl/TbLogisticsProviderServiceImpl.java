package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProviderContact;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsProviderMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsProviderContactService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流供应商;(tb_logistics_provider)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsProviderServiceImpl extends ServiceImpl<TbLogisticsProviderMapper, TbLogisticsProvider> implements TbLogisticsProviderService {
    @Resource
    private TbLogisticsProviderMapper tbLogisticsProviderMapper;

    @Resource
    private TbLogisticsProviderContactService providerContactService;

    public static final String DEFAULT_LP_CODE = "ST1000";
    public static final String PRE_LP_CODE = "ST";

    /**
     * 通过ID查询单条数据
     *
     * @param lpCode 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsProvider queryById(String lpCode) {
        return tbLogisticsProviderMapper.selectById(lpCode);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<TbLogisticsProviderResult> paginQuery(TbLogisticsProviderParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()), TbLogisticsProvider::getLpCode, param.getLpCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpName()), TbLogisticsProvider::getLpName, param.getLpName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()), TbLogisticsProvider::getLpSimpleName, param.getLpSimpleName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpAddress()), TbLogisticsProvider::getLpAddress, param.getLpAddress());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpUniSocCreCode()), TbLogisticsProvider::getLpUniSocCreCode, param.getLpUniSocCreCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getForbidStatus()), TbLogisticsProvider::getForbidStatus, param.getForbidStatus());

        //2. 执行分页查询
        Page<TbLogisticsProviderResult> pagin = new Page<>(current, size, true);
        IPage<TbLogisticsProviderResult> selectResult = tbLogisticsProviderMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        if(CollectionUtil.isNotEmpty(selectResult.getRecords())){
            for (TbLogisticsProviderResult record : selectResult.getRecords()) {
                if("A".equals(record.getForbidStatus())){
                    record.setForbidStatusDesc("启用");
                }
                if("B".equals(record.getForbidStatus())){
                    record.setForbidStatusDesc("禁用");
                }
            }
            pagin.setRecords(selectResult.getRecords());
        }
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsProvider 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData insert(TbLogisticsProvider tbLogisticsProvider) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        LambdaQueryWrapper<TbLogisticsProvider> qw = new LambdaQueryWrapper<>();
        qw.eq(TbLogisticsProvider :: getLpName, tbLogisticsProvider.getLpName())
                .or()
                .eq(TbLogisticsProvider :: getLpSimpleName, tbLogisticsProvider.getLpSimpleName());
        List<TbLogisticsProvider> list = tbLogisticsProviderMapper.selectList(qw);
        if(CollectionUtil.isNotEmpty(list)){
            for (TbLogisticsProvider logisticsProvider : list) {
                if(tbLogisticsProvider.getLpName().equals(logisticsProvider.getLpName())){
                    return ResponseData.error("已存在此物流商名称！");
                }
                if(tbLogisticsProvider.getLpSimpleName().equals(logisticsProvider.getLpSimpleName())){
                    return ResponseData.error("已存在此物流商简称！");
                }
            }
        }

        //生成物流商编码
        LambdaQueryWrapper<TbLogisticsProvider> qw1 = new LambdaQueryWrapper<>();
        qw1.likeRight(TbLogisticsProvider :: getLpCode, PRE_LP_CODE)
                .orderByDesc(TbLogisticsProvider :: getLpCode);
        List<TbLogisticsProvider> lpCodeList = tbLogisticsProviderMapper.selectList(qw1);
        if(CollectionUtil.isEmpty(lpCodeList)){
            tbLogisticsProvider.setLpCode(DEFAULT_LP_CODE);
        } else {
            String subLpCode = lpCodeList.get(0).getLpCode().substring(2);
            try {
                Integer nowSubLpCode = Integer.parseInt(subLpCode) + 1;
                tbLogisticsProvider.setLpCode(PRE_LP_CODE + nowSubLpCode);
            } catch (Exception e){
                log.error("物流商编码转换异常：" + e.getMessage());
                return ResponseData.error(lpCodeList.get(0).getLpCode() + "物流商编码转换异常！");
            }
        }
        tbLogisticsProvider.setDataSource("JCERP");
        tbLogisticsProvider.setSysUpdDatetime(DateUtil.date());
        tbLogisticsProvider.setSysUpdUser(loginUser.getName());
        tbLogisticsProviderMapper.insert(tbLogisticsProvider);
        return ResponseData.success();
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsProvider update(TbLogisticsProviderParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsProvider> wrapper = new LambdaUpdateChainWrapper<TbLogisticsProvider>(tbLogisticsProviderMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpName()), TbLogisticsProvider::getLpName, param.getLpName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpSimpleName()), TbLogisticsProvider::getLpSimpleName, param.getLpSimpleName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpAddress()), TbLogisticsProvider::getLpAddress, param.getLpAddress());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpUniSocCreCode()), TbLogisticsProvider::getLpUniSocCreCode, param.getLpUniSocCreCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getForbidStatus()), TbLogisticsProvider::getForbidStatus, param.getForbidStatus());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLogListLinkTemp()), TbLogisticsProvider::getLogListLinkTemp, param.getLogListLinkTemp());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(TbLogisticsProvider::getSysUpdDatetime, DateUtil.date());
        wrapper.set(TbLogisticsProvider::getSysUpdUser, loginUser.getName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsProvider::getLpCode, param.getLpCode());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getLpCode());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param lpCode 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String lpCode) {
        int total = tbLogisticsProviderMapper.deleteById(lpCode);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param lpCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> lpCodeList) {
        int delCount = tbLogisticsProviderMapper.deleteBatchIds(lpCodeList);
        if (lpCodeList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData synLogisticsBusinessFromERP() {
        List<TbLogisticsProvider> providerList = providerContactService.getLogisticsProvider();
        if (ObjectUtil.isEmpty(providerList)) {
            return ResponseData.error("获取k3物流供应商信息失败");
        }
        List<TbLogisticsProvider> saveList = new ArrayList<>();
        List<TbLogisticsProvider> updateList = new ArrayList<>();
        for (TbLogisticsProvider provider : providerList) {
            TbLogisticsProvider providerResult = this.getById(provider.getLpCode());
            if(providerResult == null){
                saveList.add(provider);
            } else {
                updateList.add(provider);
            }
        }
        if(CollectionUtil.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }
        if(CollectionUtil.isNotEmpty(updateList)){
            this.updateBatchById(providerList);
        }

        List<TbLogisticsProviderContact> providerContactList = providerContactService.getLogisticsProviderContact();
        if (ObjectUtil.isEmpty(providerContactList)) {
            return ResponseData.error("获取k3物流供应商联系人信息失败");
        }
        LambdaQueryWrapper<TbLogisticsProviderContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(TbLogisticsProviderContact::getDataSource, "K3");
        providerContactService.remove(wrapper);

        providerContactService.saveBatch(providerContactList);
        return ResponseData.success("k3物流供应商信息刷新成功");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData lpCodeList() {
        return ResponseData.success(tbLogisticsProviderMapper.lpCodeList());
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData lpNameList() {
        return ResponseData.success(tbLogisticsProviderMapper.lpNameList());
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData lpSimpleNameList() {
        return ResponseData.success(tbLogisticsProviderMapper.lpSimpleNameList());
    }


}