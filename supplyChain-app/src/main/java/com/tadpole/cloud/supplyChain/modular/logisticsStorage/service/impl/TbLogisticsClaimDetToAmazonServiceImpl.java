package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsClaimDetToAmazon;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsClaimDetToAmazonMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsClaimDetToAmazonService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsClaimDetToAmazonResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsClaimDetToAmazonParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 亚马逊物流索赔明细;(tb_logistics_claim_det_to_amazon)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsClaimDetToAmazonServiceImpl  extends ServiceImpl<TbLogisticsClaimDetToAmazonMapper, TbLogisticsClaimDetToAmazon> implements TbLogisticsClaimDetToAmazonService{
    @Resource
    private TbLogisticsClaimDetToAmazonMapper tbLogisticsClaimDetToAmazonMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkClaimDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimDetToAmazon queryById(BigDecimal pkClaimDetId){
        return tbLogisticsClaimDetToAmazonMapper.selectById(pkClaimDetId);
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
    public Page<TbLogisticsClaimDetToAmazonResult> paginQuery(TbLogisticsClaimDetToAmazonParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsClaimDetToAmazon> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPkClaimId()),TbLogisticsClaimDetToAmazon::getPkClaimId, param.getPkClaimId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusIsPod()),TbLogisticsClaimDetToAmazon::getBusIsPod, param.getBusIsPod());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusComWarehouseName()),TbLogisticsClaimDetToAmazon::getBusComWarehouseName, param.getBusComWarehouseName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLhrOddNumb()),TbLogisticsClaimDetToAmazon::getBusLhrOddNumb, param.getBusLhrOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLhrState()),TbLogisticsClaimDetToAmazon::getBusLhrState, param.getBusLhrState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogTraMode2()),TbLogisticsClaimDetToAmazon::getBusLogTraMode2, param.getBusLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusSku()),TbLogisticsClaimDetToAmazon::getBusSku, param.getBusSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusRemark()),TbLogisticsClaimDetToAmazon::getBusRemark, param.getBusRemark());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogTraMode1()),TbLogisticsClaimDetToAmazon::getBusLogTraMode1, param.getBusLogTraMode1());
        //2. 执行分页查询
        Page<TbLogisticsClaimDetToAmazonResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsClaimDetToAmazonResult> selectResult = tbLogisticsClaimDetToAmazonMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsClaimDetToAmazon 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimDetToAmazon insert(TbLogisticsClaimDetToAmazon tbLogisticsClaimDetToAmazon){
        tbLogisticsClaimDetToAmazonMapper.insert(tbLogisticsClaimDetToAmazon);
        return tbLogisticsClaimDetToAmazon;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsClaimDetToAmazon update(TbLogisticsClaimDetToAmazonParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsClaimDetToAmazon> wrapper = new LambdaUpdateChainWrapper<TbLogisticsClaimDetToAmazon>(tbLogisticsClaimDetToAmazonMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPkClaimId()),TbLogisticsClaimDetToAmazon::getPkClaimId, param.getPkClaimId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusIsPod()),TbLogisticsClaimDetToAmazon::getBusIsPod, param.getBusIsPod());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusComWarehouseName()),TbLogisticsClaimDetToAmazon::getBusComWarehouseName, param.getBusComWarehouseName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLhrOddNumb()),TbLogisticsClaimDetToAmazon::getBusLhrOddNumb, param.getBusLhrOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLhrState()),TbLogisticsClaimDetToAmazon::getBusLhrState, param.getBusLhrState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogTraMode2()),TbLogisticsClaimDetToAmazon::getBusLogTraMode2, param.getBusLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusSku()),TbLogisticsClaimDetToAmazon::getBusSku, param.getBusSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusRemark()),TbLogisticsClaimDetToAmazon::getBusRemark, param.getBusRemark());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogTraMode1()),TbLogisticsClaimDetToAmazon::getBusLogTraMode1, param.getBusLogTraMode1());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsClaimDetToAmazon::getPkClaimDetId, param.getPkClaimDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPkClaimDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkClaimDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkClaimDetId){
        int total = tbLogisticsClaimDetToAmazonMapper.deleteById(pkClaimDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param pkClaimDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkClaimDetIdList) {
        int delCount = tbLogisticsClaimDetToAmazonMapper.deleteBatchIds(pkClaimDetIdList);
        if (pkClaimDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}