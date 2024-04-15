package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceChargeStandByVolume;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPriceChargeStandByVolumeMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceChargeStandByVolumeParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceChargeStandByVolumeResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPriceChargeStandByVolumeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流价格按体积计费的--暂时不需要;(tb_logistics_price_charge_stand_by_volume)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPriceChargeStandByVolumeServiceImpl  extends ServiceImpl<TbLogisticsPriceChargeStandByVolumeMapper, TbLogisticsPriceChargeStandByVolume> implements TbLogisticsPriceChargeStandByVolumeService {
    @Resource
    private TbLogisticsPriceChargeStandByVolumeMapper tbLogisticsPriceChargeStandByVolumeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpcsvId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByVolume queryById(BigDecimal lpcsvId){
        return tbLogisticsPriceChargeStandByVolumeMapper.selectById(lpcsvId);
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
    public Page<TbLogisticsPriceChargeStandByVolumeResult> paginQuery(TbLogisticsPriceChargeStandByVolumeParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPriceChargeStandByVolume> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceChargeStandByVolume::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpcsvCurrSystem()),TbLogisticsPriceChargeStandByVolume::getLpcsvCurrSystem, param.getLpcsvCurrSystem());
        //2. 执行分页查询
        Page<TbLogisticsPriceChargeStandByVolumeResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPriceChargeStandByVolumeResult> selectResult = tbLogisticsPriceChargeStandByVolumeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceChargeStandByVolume 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByVolume insert(TbLogisticsPriceChargeStandByVolume tbLogisticsPriceChargeStandByVolume){
        tbLogisticsPriceChargeStandByVolumeMapper.insert(tbLogisticsPriceChargeStandByVolume);
        return tbLogisticsPriceChargeStandByVolume;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByVolume update(TbLogisticsPriceChargeStandByVolumeParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPriceChargeStandByVolume> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPriceChargeStandByVolume>(tbLogisticsPriceChargeStandByVolumeMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceChargeStandByVolume::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpcsvCurrSystem()),TbLogisticsPriceChargeStandByVolume::getLpcsvCurrSystem, param.getLpcsvCurrSystem());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPriceChargeStandByVolume::getLpcsvId, param.getLpcsvId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLpcsvId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpcsvId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lpcsvId){
        int total = tbLogisticsPriceChargeStandByVolumeMapper.deleteById(lpcsvId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lpcsvIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lpcsvIdList) {
        int delCount = tbLogisticsPriceChargeStandByVolumeMapper.deleteBatchIds(lpcsvIdList);
        if (lpcsvIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}