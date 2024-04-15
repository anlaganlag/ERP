package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPriceSurchargeItems;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPriceSurchargeItemsMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceSurchargeItemsParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceSurchargeItemsResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPriceSurchargeItemsService;
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
 * 物流价格附加项目--暂时不需要;(tb_logistics_price_surcharge_items)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPriceSurchargeItemsServiceImpl  extends ServiceImpl<TbLogisticsPriceSurchargeItemsMapper, TbLogisticsPriceSurchargeItems> implements TbLogisticsPriceSurchargeItemsService {
    @Resource
    private TbLogisticsPriceSurchargeItemsMapper tbLogisticsPriceSurchargeItemsMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpsiId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceSurchargeItems queryById(BigDecimal lpsiId){
        return tbLogisticsPriceSurchargeItemsMapper.selectById(lpsiId);
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
    public Page<TbLogisticsPriceSurchargeItemsResult> paginQuery(TbLogisticsPriceSurchargeItemsParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPriceSurchargeItems> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceSurchargeItems::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpsiItemType()),TbLogisticsPriceSurchargeItems::getLpsiItemType, param.getLpsiItemType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpsiItemName()),TbLogisticsPriceSurchargeItems::getLpsiItemName, param.getLpsiItemName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpsiCurrSystem()),TbLogisticsPriceSurchargeItems::getLpsiCurrSystem, param.getLpsiCurrSystem());
        //2. 执行分页查询
        Page<TbLogisticsPriceSurchargeItemsResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPriceSurchargeItemsResult> selectResult = tbLogisticsPriceSurchargeItemsMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPriceSurchargeItems 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceSurchargeItems insert(TbLogisticsPriceSurchargeItems tbLogisticsPriceSurchargeItems){
        tbLogisticsPriceSurchargeItemsMapper.insert(tbLogisticsPriceSurchargeItems);
        return tbLogisticsPriceSurchargeItems;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPriceSurchargeItems update(TbLogisticsPriceSurchargeItemsParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPriceSurchargeItems> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPriceSurchargeItems>(tbLogisticsPriceSurchargeItemsMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPriceSurchargeItems::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpsiItemType()),TbLogisticsPriceSurchargeItems::getLpsiItemType, param.getLpsiItemType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpsiItemName()),TbLogisticsPriceSurchargeItems::getLpsiItemName, param.getLpsiItemName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpsiCurrSystem()),TbLogisticsPriceSurchargeItems::getLpsiCurrSystem, param.getLpsiCurrSystem());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPriceSurchargeItems::getLpsiId, param.getLpsiId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLpsiId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lpsiId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lpsiId){
        int total = tbLogisticsPriceSurchargeItemsMapper.deleteById(lpsiId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lpsiIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lpsiIdList) {
        int delCount = tbLogisticsPriceSurchargeItemsMapper.deleteBatchIds(lpsiIdList);
        if (lpsiIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}