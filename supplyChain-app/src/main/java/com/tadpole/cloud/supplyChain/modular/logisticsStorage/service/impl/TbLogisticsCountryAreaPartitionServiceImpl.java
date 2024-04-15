package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsCountryAreaPartition;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsCountryAreaPartitionMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsCountryAreaPartitionService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsCountryAreaPartitionResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsCountryAreaPartitionParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流国家区域划分;(tb_logistics_country_area_partition)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsCountryAreaPartitionServiceImpl  extends ServiceImpl<TbLogisticsCountryAreaPartitionMapper, TbLogisticsCountryAreaPartition> implements TbLogisticsCountryAreaPartitionService{
    @Resource
    private TbLogisticsCountryAreaPartitionMapper tbLogisticsCountryAreaPartitionMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryAreaPartition queryById(BigDecimal id){
        return tbLogisticsCountryAreaPartitionMapper.selectById(id);
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
    public Page<TbLogisticsCountryAreaPartitionResult> paginQuery(TbLogisticsCountryAreaPartitionParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsCountryAreaPartition> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsCountryAreaPartition::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsCountryAreaPartition::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsCountryAreaPartition::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryAreaName()),TbLogisticsCountryAreaPartition::getCountryAreaName, param.getCountryAreaName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsCountryAreaPartition::getLspNum, param.getLspNum());
        //2. 执行分页查询
        Page<TbLogisticsCountryAreaPartitionResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsCountryAreaPartitionResult> selectResult = tbLogisticsCountryAreaPartitionMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsCountryAreaPartition 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryAreaPartition insert(TbLogisticsCountryAreaPartition tbLogisticsCountryAreaPartition){
        tbLogisticsCountryAreaPartitionMapper.insert(tbLogisticsCountryAreaPartition);
        return tbLogisticsCountryAreaPartition;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsCountryAreaPartition update(TbLogisticsCountryAreaPartitionParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsCountryAreaPartition> wrapper = new LambdaUpdateChainWrapper<TbLogisticsCountryAreaPartition>(tbLogisticsCountryAreaPartitionMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsCountryAreaPartition::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsCountryAreaPartition::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsCountryAreaPartition::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryAreaName()),TbLogisticsCountryAreaPartition::getCountryAreaName, param.getCountryAreaName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLspNum()),TbLogisticsCountryAreaPartition::getLspNum, param.getLspNum());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsCountryAreaPartition::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
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
        int total = tbLogisticsCountryAreaPartitionMapper.deleteById(id);
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
        int delCount = tbLogisticsCountryAreaPartitionMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}