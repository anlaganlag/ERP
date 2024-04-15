package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsFuelRate;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsFuelRateMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsFuelRateService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsFuelRateResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsFuelRateParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流燃料费率;(tb_logistics_fuel_rate)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsFuelRateServiceImpl  extends ServiceImpl<TbLogisticsFuelRateMapper, TbLogisticsFuelRate> implements TbLogisticsFuelRateService{
    @Resource
    private TbLogisticsFuelRateMapper tbLogisticsFuelRateMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lfrId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsFuelRate queryById(BigDecimal lfrId){
        return tbLogisticsFuelRateMapper.selectById(lfrId);
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
    public Page<TbLogisticsFuelRateResult> paginQuery(TbLogisticsFuelRateParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsFuelRate> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsFuelRate::getLpCode, param.getLpCode());
        //2. 执行分页查询
        Page<TbLogisticsFuelRateResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsFuelRateResult> selectResult = tbLogisticsFuelRateMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsFuelRate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsFuelRate insert(TbLogisticsFuelRate tbLogisticsFuelRate){
        tbLogisticsFuelRateMapper.insert(tbLogisticsFuelRate);
        return tbLogisticsFuelRate;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsFuelRate update(TbLogisticsFuelRateParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsFuelRate> wrapper = new LambdaUpdateChainWrapper<TbLogisticsFuelRate>(tbLogisticsFuelRateMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsFuelRate::getLpCode, param.getLpCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsFuelRate::getLfrId, param.getLfrId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLfrId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lfrId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lfrId){
        int total = tbLogisticsFuelRateMapper.deleteById(lfrId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lfrIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lfrIdList) {
        int delCount = tbLogisticsFuelRateMapper.deleteBatchIds(lfrIdList);
        if (lfrIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}