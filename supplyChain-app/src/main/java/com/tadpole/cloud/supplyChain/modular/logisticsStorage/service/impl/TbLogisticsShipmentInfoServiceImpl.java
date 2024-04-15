package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentInfo;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsShipmentInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentInfoParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentInfoResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentInfoService;
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
 * 亚马逊返回的发货申请信息-新版API流程;(tb_logistics_shipment_info)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsShipmentInfoServiceImpl  extends ServiceImpl<TbLogisticsShipmentInfoMapper, TbLogisticsShipmentInfo> implements TbLogisticsShipmentInfoService {
    @Resource
    private TbLogisticsShipmentInfoMapper tbLogisticsShipmentInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkShipId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentInfo queryById(BigDecimal pkShipId){
        return tbLogisticsShipmentInfoMapper.selectById(pkShipId);
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
    public Page<TbLogisticsShipmentInfoResult> paginQuery(TbLogisticsShipmentInfoParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsShipmentInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentInfo::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipmentInfo::getPlanName, param.getPlanName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusShipmentId()),TbLogisticsShipmentInfo::getBusShipmentId, param.getBusShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusShipmentName()),TbLogisticsShipmentInfo::getBusShipmentName, param.getBusShipmentName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusPlanId()),TbLogisticsShipmentInfo::getBusPlanId, param.getBusPlanId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusShipTo()),TbLogisticsShipmentInfo::getBusShipTo, param.getBusShipTo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusWhoWillPrep()),TbLogisticsShipmentInfo::getBusWhoWillPrep, param.getBusWhoWillPrep());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusPrepType()),TbLogisticsShipmentInfo::getBusPrepType, param.getBusPrepType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusWhoWillLabel()),TbLogisticsShipmentInfo::getBusWhoWillLabel, param.getBusWhoWillLabel());
        //2. 执行分页查询
        Page<TbLogisticsShipmentInfoResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsShipmentInfoResult> selectResult = tbLogisticsShipmentInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentInfo 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentInfo insert(TbLogisticsShipmentInfo tbLogisticsShipmentInfo){
        tbLogisticsShipmentInfoMapper.insert(tbLogisticsShipmentInfo);
        return tbLogisticsShipmentInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentInfo update(TbLogisticsShipmentInfoParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsShipmentInfo> wrapper = new LambdaUpdateChainWrapper<TbLogisticsShipmentInfo>(tbLogisticsShipmentInfoMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentInfo::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipmentInfo::getPlanName, param.getPlanName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentId()),TbLogisticsShipmentInfo::getBusShipmentId, param.getBusShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentName()),TbLogisticsShipmentInfo::getBusShipmentName, param.getBusShipmentName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusPlanId()),TbLogisticsShipmentInfo::getBusPlanId, param.getBusPlanId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipTo()),TbLogisticsShipmentInfo::getBusShipTo, param.getBusShipTo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusWhoWillPrep()),TbLogisticsShipmentInfo::getBusWhoWillPrep, param.getBusWhoWillPrep());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusPrepType()),TbLogisticsShipmentInfo::getBusPrepType, param.getBusPrepType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusWhoWillLabel()),TbLogisticsShipmentInfo::getBusWhoWillLabel, param.getBusWhoWillLabel());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsShipmentInfo::getPkShipId, param.getPkShipId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPkShipId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkShipId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkShipId){
        int total = tbLogisticsShipmentInfoMapper.deleteById(pkShipId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param pkShipIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkShipIdList) {
        int delCount = tbLogisticsShipmentInfoMapper.deleteBatchIds(pkShipIdList);
        if (pkShipIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}