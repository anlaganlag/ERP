package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWayAnalysisNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbBscOverseasWayAnalysisNewV2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbBscOverseasWayAnalysisNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayAnalysisNewV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbBscOverseasWayAnalysisNewV2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发货单数据;(Tb_Bsc_Overseas_Way_Analysis_New_V2)表服务实现类
 * @author : LSY
 * @date : 2024-3-18
 */
@Service
@Transactional
@Slf4j
public class TbBscOverseasWayAnalysisNewV2ServiceImpl  extends ServiceImpl<TbBscOverseasWayAnalysisNewV2Mapper, TbBscOverseasWayAnalysisNewV2> implements TbBscOverseasWayAnalysisNewV2Service {
    @Resource
    private TbBscOverseasWayAnalysisNewV2Mapper tbBscOverseasWayAnalysisNewV2Mapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWayAnalysisNewV2 queryById(String undefinedId){
        return tbBscOverseasWayAnalysisNewV2Mapper.selectById(undefinedId);
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
    public Page<TbBscOverseasWayAnalysisNewV2Result> paginQuery(TbBscOverseasWayAnalysisNewV2Param param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbBscOverseasWayAnalysisNewV2> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),TbBscOverseasWayAnalysisNewV2::getSku, param.getSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMatCode()),TbBscOverseasWayAnalysisNewV2::getMatCode, param.getMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbBscOverseasWayAnalysisNewV2::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbBscOverseasWayAnalysisNewV2::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbBscOverseasWayAnalysisNewV2::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDeliverType()),TbBscOverseasWayAnalysisNewV2::getDeliverType, param.getDeliverType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStatus()),TbBscOverseasWayAnalysisNewV2::getStatus, param.getStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbBscOverseasWayAnalysisNewV2::getShipmentRealStatus, param.getShipmentRealStatus());
        //2. 执行分页查询
        Page<TbBscOverseasWayAnalysisNewV2Result> pagin = new Page<>(current , size , true);
        IPage<TbBscOverseasWayAnalysisNewV2Result> selectResult = tbBscOverseasWayAnalysisNewV2Mapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbBscOverseasWayAnalysisNewV2 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWayAnalysisNewV2 insert(TbBscOverseasWayAnalysisNewV2 tbBscOverseasWayAnalysisNewV2){
        tbBscOverseasWayAnalysisNewV2Mapper.insert(tbBscOverseasWayAnalysisNewV2);
        return tbBscOverseasWayAnalysisNewV2;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWayAnalysisNewV2 update(TbBscOverseasWayAnalysisNewV2Param param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbBscOverseasWayAnalysisNewV2> wrapper = new LambdaUpdateChainWrapper<TbBscOverseasWayAnalysisNewV2>(tbBscOverseasWayAnalysisNewV2Mapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSku()),TbBscOverseasWayAnalysisNewV2::getSku, param.getSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMatCode()),TbBscOverseasWayAnalysisNewV2::getMatCode, param.getMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbBscOverseasWayAnalysisNewV2::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbBscOverseasWayAnalysisNewV2::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbBscOverseasWayAnalysisNewV2::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverType()),TbBscOverseasWayAnalysisNewV2::getDeliverType, param.getDeliverType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getStatus()),TbBscOverseasWayAnalysisNewV2::getStatus, param.getStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbBscOverseasWayAnalysisNewV2::getShipmentRealStatus, param.getShipmentRealStatus());
        return null;
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String undefinedId){
        int total = tbBscOverseasWayAnalysisNewV2Mapper.deleteById(undefinedId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> undefinedIdList) {
        int delCount = tbBscOverseasWayAnalysisNewV2Mapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}