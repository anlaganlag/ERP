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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbReceivedInvenrotyAnalysisV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbReceivedInvenrotyAnalysisV2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbReceivedInvenrotyAnalysisV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbReceivedInvenrotyAnalysisV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbReceivedInvenrotyAnalysisV2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
 /**
 * 来货报告;(Tb_Received_Invenroty_Analysis_V2)表服务实现类
 * @author : LSY
 * @date : 2024-3-18
 */
@Service
@Transactional
@Slf4j
public class TbReceivedInvenrotyAnalysisV2ServiceImpl  extends ServiceImpl<TbReceivedInvenrotyAnalysisV2Mapper, TbReceivedInvenrotyAnalysisV2> implements TbReceivedInvenrotyAnalysisV2Service{
    @Resource
    private TbReceivedInvenrotyAnalysisV2Mapper tbReceivedInvenrotyAnalysisV2Mapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbReceivedInvenrotyAnalysisV2 queryById(String undefinedId){
        return tbReceivedInvenrotyAnalysisV2Mapper.selectById(undefinedId);
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
    public Page<TbReceivedInvenrotyAnalysisV2Result> paginQuery(TbReceivedInvenrotyAnalysisV2Param param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbReceivedInvenrotyAnalysisV2> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbReceivedInvenrotyAnalysisV2::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbReceivedInvenrotyAnalysisV2::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getItemSku()),TbReceivedInvenrotyAnalysisV2::getItemSku, param.getItemSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbReceivedInvenrotyAnalysisV2::getShipmentId, param.getShipmentId());
        //2. 执行分页查询
        Page<TbReceivedInvenrotyAnalysisV2Result> pagin = new Page<>(current , size , true);
        IPage<TbReceivedInvenrotyAnalysisV2Result> selectResult = tbReceivedInvenrotyAnalysisV2Mapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbReceivedInvenrotyAnalysisV2 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbReceivedInvenrotyAnalysisV2 insert(TbReceivedInvenrotyAnalysisV2 tbReceivedInvenrotyAnalysisV2){
        tbReceivedInvenrotyAnalysisV2Mapper.insert(tbReceivedInvenrotyAnalysisV2);
        return tbReceivedInvenrotyAnalysisV2;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbReceivedInvenrotyAnalysisV2 update(TbReceivedInvenrotyAnalysisV2Param param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbReceivedInvenrotyAnalysisV2> wrapper = new LambdaUpdateChainWrapper<TbReceivedInvenrotyAnalysisV2>(tbReceivedInvenrotyAnalysisV2Mapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbReceivedInvenrotyAnalysisV2::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbReceivedInvenrotyAnalysisV2::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getItemSku()),TbReceivedInvenrotyAnalysisV2::getItemSku, param.getItemSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbReceivedInvenrotyAnalysisV2::getShipmentId, param.getShipmentId());
        //2. 设置主键，并更新
//        wrapper.eq(TbReceivedInvenrotyAnalysisV2::getUndefinedId, param.getUndefinedId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
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
        int total = tbReceivedInvenrotyAnalysisV2Mapper.deleteById(undefinedId);
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
        int delCount = tbReceivedInvenrotyAnalysisV2Mapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}