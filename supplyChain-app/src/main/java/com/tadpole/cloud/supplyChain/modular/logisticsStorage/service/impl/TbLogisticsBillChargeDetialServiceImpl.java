package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillChargeDetial;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBillChargeDetialMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBillChargeDetialService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillChargeDetialResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillChargeDetialParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流账单费用明细;(tb_logistics_bill_charge_detial)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBillChargeDetialServiceImpl  extends ServiceImpl<TbLogisticsBillChargeDetialMapper, TbLogisticsBillChargeDetial> implements TbLogisticsBillChargeDetialService{
    @Resource
    private TbLogisticsBillChargeDetialMapper tbLogisticsBillChargeDetialMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillChargeDetial queryById(BigDecimal id){
        return tbLogisticsBillChargeDetialMapper.selectById(id);
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
    public Page<TbLogisticsBillChargeDetialResult> paginQuery(TbLogisticsBillChargeDetialParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBillChargeDetial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillChargeDetial::getBillNum, param.getBillNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillChargeDetial::getLhrOddNum, param.getLhrOddNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChargeType()),TbLogisticsBillChargeDetial::getChargeType, param.getChargeType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChargeName()),TbLogisticsBillChargeDetial::getChargeName, param.getChargeName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCurrency()),TbLogisticsBillChargeDetial::getCurrency, param.getCurrency());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getRemarks()),TbLogisticsBillChargeDetial::getRemarks, param.getRemarks());
        //2. 执行分页查询
        Page<TbLogisticsBillChargeDetialResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBillChargeDetialResult> selectResult = tbLogisticsBillChargeDetialMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillChargeDetial 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillChargeDetial insert(TbLogisticsBillChargeDetial tbLogisticsBillChargeDetial){
        tbLogisticsBillChargeDetialMapper.insert(tbLogisticsBillChargeDetial);
        return tbLogisticsBillChargeDetial;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillChargeDetial update(TbLogisticsBillChargeDetialParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBillChargeDetial> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBillChargeDetial>(tbLogisticsBillChargeDetialMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillChargeDetial::getBillNum, param.getBillNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillChargeDetial::getLhrOddNum, param.getLhrOddNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChargeType()),TbLogisticsBillChargeDetial::getChargeType, param.getChargeType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChargeName()),TbLogisticsBillChargeDetial::getChargeName, param.getChargeName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCurrency()),TbLogisticsBillChargeDetial::getCurrency, param.getCurrency());
         wrapper.set(ObjectUtil.isNotEmpty(param.getRemarks()),TbLogisticsBillChargeDetial::getRemarks, param.getRemarks());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBillChargeDetial::getId, param.getId());
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
        int total = tbLogisticsBillChargeDetialMapper.deleteById(id);
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
        int delCount = tbLogisticsBillChargeDetialMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}