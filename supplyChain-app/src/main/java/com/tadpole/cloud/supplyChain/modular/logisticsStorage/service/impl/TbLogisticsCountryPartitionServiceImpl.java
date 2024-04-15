package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsCountryPartition;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsCountryPartitionMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsCountryPartitionParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsCountryPartitionResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsCountryPartitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
/**
 * 物流国家划分;(tb_logistics_country_partition)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsCountryPartitionServiceImpl  extends ServiceImpl<TbLogisticsCountryPartitionMapper, TbLogisticsCountryPartition> implements TbLogisticsCountryPartitionService{
    @Resource
    private TbLogisticsCountryPartitionMapper tbLogisticsCountryPartitionMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryPartition queryById(BigDecimal id){
        return tbLogisticsCountryPartitionMapper.selectById(id);
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
    public Page<TbLogisticsCountryPartitionResult> paginQuery(TbLogisticsCountryPartitionParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsCountryPartition> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsCountryPartition::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsCountryPartition::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsCountryPartition::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsCountryPartition::getLspNum, param.getLspNum());
        //2. 执行分页查询
        Page<TbLogisticsCountryPartitionResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsCountryPartitionResult> selectResult = tbLogisticsCountryPartitionMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsCountryPartition 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryPartition insert(TbLogisticsCountryPartition tbLogisticsCountryPartition){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        tbLogisticsCountryPartition.setSysPerName(loginUser.getName());
        tbLogisticsCountryPartition.setSysAddDate(DateUtil.date());
        tbLogisticsCountryPartitionMapper.insert(tbLogisticsCountryPartition);
        return tbLogisticsCountryPartition;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryPartition update(TbLogisticsCountryPartitionParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsCountryPartition> wrapper = new LambdaUpdateChainWrapper<TbLogisticsCountryPartition>(tbLogisticsCountryPartitionMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsCountryPartition::getSysPerName, param.getSysPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsCountryPartition::getLpCode, param.getLpCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsCountryPartition::getCountryCode, param.getCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsCountryPartition::getLspNum, param.getLspNum());
        wrapper.set(TbLogisticsCountryPartition::getSysUpdDatetime, DateUtil.date());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(TbLogisticsCountryPartition::getSysPerName, loginUser.getName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsCountryPartition::getId, param.getId());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id){
        int total = tbLogisticsCountryPartitionMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbLogisticsCountryPartitionMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}