package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPrice;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPriceMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPriceParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPriceResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPriceService;
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
 * 物流价格--暂时不需要;(tb_logistics_price)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPriceServiceImpl  extends ServiceImpl<TbLogisticsPriceMapper, TbLogisticsPrice> implements TbLogisticsPriceService {
    @Resource
    private TbLogisticsPriceMapper tbLogisticsPriceMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param logpId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPrice queryById(BigDecimal logpId){
        return tbLogisticsPriceMapper.selectById(logpId);
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
    public Page<TbLogisticsPriceResult> paginQuery(TbLogisticsPriceParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPrice> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPrice::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsPrice::getLcCode, param.getLcCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsPrice::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsPrice::getLpSimpleName, param.getLpSimpleName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsPrice::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsPrice::getLspNum, param.getLspNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryAreaName()),TbLogisticsPrice::getCountryAreaName, param.getCountryAreaName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsPrice::getLogTraMode1, param.getLogTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsPrice::getLogTraMode2, param.getLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogSeaTraRoute()),TbLogisticsPrice::getLogSeaTraRoute, param.getLogSeaTraRoute());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogSeaTraConType()),TbLogisticsPrice::getLogSeaTraConType, param.getLogSeaTraConType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsPrice::getLogRedOrBlueList, param.getLogRedOrBlueList());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogGoodCharacter()),TbLogisticsPrice::getLogGoodCharacter, param.getLogGoodCharacter());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogpChargType()),TbLogisticsPrice::getLogpChargType, param.getLogpChargType());
        //2. 执行分页查询
        Page<TbLogisticsPriceResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPriceResult> selectResult = tbLogisticsPriceMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPrice 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPrice insert(TbLogisticsPrice tbLogisticsPrice){
        tbLogisticsPriceMapper.insert(tbLogisticsPrice);
        return tbLogisticsPrice;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPrice update(TbLogisticsPriceParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPrice> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPrice>(tbLogisticsPriceMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsPrice::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsPrice::getLcCode, param.getLcCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsPrice::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsPrice::getLpSimpleName, param.getLpSimpleName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsPrice::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsPrice::getLspNum, param.getLspNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryAreaName()),TbLogisticsPrice::getCountryAreaName, param.getCountryAreaName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsPrice::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsPrice::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraRoute()),TbLogisticsPrice::getLogSeaTraRoute, param.getLogSeaTraRoute());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraConType()),TbLogisticsPrice::getLogSeaTraConType, param.getLogSeaTraConType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsPrice::getLogRedOrBlueList, param.getLogRedOrBlueList());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogGoodCharacter()),TbLogisticsPrice::getLogGoodCharacter, param.getLogGoodCharacter());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogpChargType()),TbLogisticsPrice::getLogpChargType, param.getLogpChargType());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPrice::getLogpId, param.getLogpId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLogpId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param logpId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal logpId){
        int total = tbLogisticsPriceMapper.deleteById(logpId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param logpIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> logpIdList) {
        int delCount = tbLogisticsPriceMapper.deleteBatchIds(logpIdList);
        if (logpIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}