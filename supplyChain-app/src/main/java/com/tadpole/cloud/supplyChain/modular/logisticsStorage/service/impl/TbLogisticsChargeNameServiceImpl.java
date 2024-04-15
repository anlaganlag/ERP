package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsChargeName;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsChargeNameMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsChargeNameService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsChargeNameResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsChargeNameParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流费用名称;(tb_logistics_charge_name)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsChargeNameServiceImpl  extends ServiceImpl<TbLogisticsChargeNameMapper, TbLogisticsChargeName> implements TbLogisticsChargeNameService{
    @Resource
    private TbLogisticsChargeNameMapper tbLogisticsChargeNameMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsChargeName queryById(BigDecimal id){
        return tbLogisticsChargeNameMapper.selectById(id);
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
    public Page<TbLogisticsChargeNameResult> paginQuery(TbLogisticsChargeNameParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsChargeName> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStandardName()),TbLogisticsChargeName::getStandardName, param.getStandardName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogisticsName()),TbLogisticsChargeName::getLogisticsName, param.getLogisticsName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChargeType()),TbLogisticsChargeName::getChargeType, param.getChargeType());
        //2. 执行分页查询
        Page<TbLogisticsChargeNameResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsChargeNameResult> selectResult = tbLogisticsChargeNameMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsChargeName 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsChargeName insert(TbLogisticsChargeName tbLogisticsChargeName){
        tbLogisticsChargeNameMapper.insert(tbLogisticsChargeName);
        return tbLogisticsChargeName;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsChargeName update(TbLogisticsChargeNameParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsChargeName> wrapper = new LambdaUpdateChainWrapper<TbLogisticsChargeName>(tbLogisticsChargeNameMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getStandardName()),TbLogisticsChargeName::getStandardName, param.getStandardName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogisticsName()),TbLogisticsChargeName::getLogisticsName, param.getLogisticsName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChargeType()),TbLogisticsChargeName::getChargeType, param.getChargeType());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsChargeName::getId, param.getId());
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
        int total = tbLogisticsChargeNameMapper.deleteById(id);
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
        int delCount = tbLogisticsChargeNameMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}