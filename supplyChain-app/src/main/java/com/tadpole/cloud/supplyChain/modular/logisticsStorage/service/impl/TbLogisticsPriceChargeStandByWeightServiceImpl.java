package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceChargeStandByWeight;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPriceChargeStandByWeightMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceChargeStandByWeightParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceChargeStandByWeightResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPriceChargeStandByWeightService;
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
 * 物流价格按重量计费的--暂时不需要;(tb_logistics_price_charge_stand_by_weight)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPriceChargeStandByWeightServiceImpl  extends ServiceImpl<TbLogisticsPriceChargeStandByWeightMapper, TbLogisticsPriceChargeStandByWeight> implements TbLogisticsPriceChargeStandByWeightService {
    @Resource
    private TbLogisticsPriceChargeStandByWeightMapper tbLogisticsPriceChargeStandByWeightMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpcswId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByWeight queryById(BigDecimal lpcswId){
        return tbLogisticsPriceChargeStandByWeightMapper.selectById(lpcswId);
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
    public Page<TbLogisticsPriceChargeStandByWeightResult> paginQuery(TbLogisticsPriceChargeStandByWeightParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPriceChargeStandByWeight> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceChargeStandByWeight::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpcswCurrSystem()),TbLogisticsPriceChargeStandByWeight::getLpcswCurrSystem, param.getLpcswCurrSystem());
        //2. 执行分页查询
        Page<TbLogisticsPriceChargeStandByWeightResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPriceChargeStandByWeightResult> selectResult = tbLogisticsPriceChargeStandByWeightMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceChargeStandByWeight 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByWeight insert(TbLogisticsPriceChargeStandByWeight tbLogisticsPriceChargeStandByWeight){
        tbLogisticsPriceChargeStandByWeightMapper.insert(tbLogisticsPriceChargeStandByWeight);
        return tbLogisticsPriceChargeStandByWeight;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceChargeStandByWeight update(TbLogisticsPriceChargeStandByWeightParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPriceChargeStandByWeight> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPriceChargeStandByWeight>(tbLogisticsPriceChargeStandByWeightMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceChargeStandByWeight::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpcswCurrSystem()),TbLogisticsPriceChargeStandByWeight::getLpcswCurrSystem, param.getLpcswCurrSystem());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPriceChargeStandByWeight::getLpcswId, param.getLpcswId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLpcswId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpcswId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lpcswId){
        int total = tbLogisticsPriceChargeStandByWeightMapper.deleteById(lpcswId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lpcswIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lpcswIdList) {
        int delCount = tbLogisticsPriceChargeStandByWeightMapper.deleteBatchIds(lpcswIdList);
        if (lpcswIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}