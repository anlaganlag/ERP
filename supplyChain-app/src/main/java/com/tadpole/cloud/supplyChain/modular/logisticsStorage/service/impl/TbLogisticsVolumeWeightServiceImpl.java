package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsVolumeWeight;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsVolumeWeightMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsVolumeWeightParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsVolumeWeightResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsVolumeWeightService;
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
 * 物流体积重量;(tb_logistics_volume_weight)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsVolumeWeightServiceImpl  extends ServiceImpl<TbLogisticsVolumeWeightMapper, TbLogisticsVolumeWeight> implements TbLogisticsVolumeWeightService {
    @Resource
    private TbLogisticsVolumeWeightMapper tbLogisticsVolumeWeightMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lvwId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsVolumeWeight queryById(BigDecimal lvwId){
        return tbLogisticsVolumeWeightMapper.selectById(lvwId);
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
    public Page<TbLogisticsVolumeWeightResult> paginQuery(TbLogisticsVolumeWeightParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsVolumeWeight> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsVolumeWeight::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsVolumeWeight::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsVolumeWeight::getLogTraMode2, param.getLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLvwBoxType()),TbLogisticsVolumeWeight::getLvwBoxType, param.getLvwBoxType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLvwUnit()),TbLogisticsVolumeWeight::getLvwUnit, param.getLvwUnit());
        //2. 执行分页查询
        Page<TbLogisticsVolumeWeightResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsVolumeWeightResult> selectResult = tbLogisticsVolumeWeightMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsVolumeWeight 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsVolumeWeight insert(TbLogisticsVolumeWeight tbLogisticsVolumeWeight){
        tbLogisticsVolumeWeightMapper.insert(tbLogisticsVolumeWeight);
        return tbLogisticsVolumeWeight;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsVolumeWeight update(TbLogisticsVolumeWeightParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsVolumeWeight> wrapper = new LambdaUpdateChainWrapper<TbLogisticsVolumeWeight>(tbLogisticsVolumeWeightMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsVolumeWeight::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsVolumeWeight::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsVolumeWeight::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLvwBoxType()),TbLogisticsVolumeWeight::getLvwBoxType, param.getLvwBoxType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLvwUnit()),TbLogisticsVolumeWeight::getLvwUnit, param.getLvwUnit());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsVolumeWeight::getLvwId, param.getLvwId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLvwId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lvwId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lvwId){
        int total = tbLogisticsVolumeWeightMapper.deleteById(lvwId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lvwIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lvwIdList) {
        int delCount = tbLogisticsVolumeWeightMapper.deleteBatchIds(lvwIdList);
        if (lvwIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}