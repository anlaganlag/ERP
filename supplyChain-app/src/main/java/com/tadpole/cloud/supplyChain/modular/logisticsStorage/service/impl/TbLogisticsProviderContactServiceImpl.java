package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProviderContact;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsProviderContactMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderContactParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderContactResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsProviderContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 物流供应商联系信息;(tb_logistics_provider_contact)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsProviderContactServiceImpl  extends ServiceImpl<TbLogisticsProviderContactMapper, TbLogisticsProviderContact> implements TbLogisticsProviderContactService {
    @Resource
    private TbLogisticsProviderContactMapper tbLogisticsProviderContactMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpContactId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsProviderContact queryById(BigDecimal lpContactId){
        return tbLogisticsProviderContactMapper.selectById(lpContactId);
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
    public Page<TbLogisticsProviderContactResult> paginQuery(TbLogisticsProviderContactParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsProviderContact> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsProviderContact::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpContactName()),TbLogisticsProviderContact::getLpContactName, param.getLpContactName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpContactDuty()),TbLogisticsProviderContact::getLpContactDuty, param.getLpContactDuty());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpContactTel()),TbLogisticsProviderContact::getLpContactTel, param.getLpContactTel());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpContactEmail()),TbLogisticsProviderContact::getLpContactEmail, param.getLpContactEmail());
        //2. 执行分页查询
        Page<TbLogisticsProviderContactResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsProviderContactResult> selectResult = tbLogisticsProviderContactMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsProviderContact 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsProviderContact insert(TbLogisticsProviderContact tbLogisticsProviderContact){
        tbLogisticsProviderContactMapper.insert(tbLogisticsProviderContact);
        return tbLogisticsProviderContact;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsProviderContact update(TbLogisticsProviderContactParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsProviderContact> wrapper = new LambdaUpdateChainWrapper<TbLogisticsProviderContact>(tbLogisticsProviderContactMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsProviderContact::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpContactName()),TbLogisticsProviderContact::getLpContactName, param.getLpContactName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpContactDuty()),TbLogisticsProviderContact::getLpContactDuty, param.getLpContactDuty());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpContactTel()),TbLogisticsProviderContact::getLpContactTel, param.getLpContactTel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpContactEmail()),TbLogisticsProviderContact::getLpContactEmail, param.getLpContactEmail());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsProviderContact::getLpContactId, param.getLpContactId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLpContactId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpContactId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lpContactId){
        int total = tbLogisticsProviderContactMapper.deleteById(lpContactId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lpContactIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lpContactIdList) {
        int delCount = tbLogisticsProviderContactMapper.deleteBatchIds(lpContactIdList);
        if (lpContactIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsProviderContact> queryByLpCode(String lpCode) {
         LambdaQueryWrapper<TbLogisticsProviderContact> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsProviderContact::getLpCode,lpCode);
        return tbLogisticsProviderContactMapper.selectList(queryWrapper);
     }


     @DataSource(name = "k3cloud")
     @Override
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     public List<TbLogisticsProvider> getLogisticsProvider() {
         return tbLogisticsProviderContactMapper.getLogisticsProvider();
     }

     @DataSource(name = "k3cloud")
     @Override
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     public List<TbLogisticsProviderContact> getLogisticsProviderContact() {
         return tbLogisticsProviderContactMapper.getLogisticsProviderContact();
     }
 }