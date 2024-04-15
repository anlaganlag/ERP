package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBill;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBillMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBillService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流账单;(tb_logistics_bill)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBillServiceImpl  extends ServiceImpl<TbLogisticsBillMapper, TbLogisticsBill> implements TbLogisticsBillService{
    @Resource
    private TbLogisticsBillMapper tbLogisticsBillMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBill queryById(BigDecimal id){
        return tbLogisticsBillMapper.selectById(id);
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
    public Page<TbLogisticsBillResult> paginQuery(TbLogisticsBillParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBill> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBill::getBillNum, param.getBillNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBill::getLcCode, param.getLcCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsBill::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsBill::getLpSimpleName, param.getLpSimpleName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPleasePayer()),TbLogisticsBill::getPleasePayer, param.getPleasePayer());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChecker()),TbLogisticsBill::getChecker, param.getChecker());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOperator()),TbLogisticsBill::getOperator, param.getOperator());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOperatorCode()),TbLogisticsBill::getOperatorCode, param.getOperatorCode());
        //2. 执行分页查询
        Page<TbLogisticsBillResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBillResult> selectResult = tbLogisticsBillMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBill 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBill insert(TbLogisticsBill tbLogisticsBill){
        tbLogisticsBillMapper.insert(tbLogisticsBill);
        return tbLogisticsBill;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBill update(TbLogisticsBillParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBill> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBill>(tbLogisticsBillMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBill::getBillNum, param.getBillNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBill::getLcCode, param.getLcCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsBill::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpSimpleName()),TbLogisticsBill::getLpSimpleName, param.getLpSimpleName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPleasePayer()),TbLogisticsBill::getPleasePayer, param.getPleasePayer());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChecker()),TbLogisticsBill::getChecker, param.getChecker());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOperator()),TbLogisticsBill::getOperator, param.getOperator());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOperatorCode()),TbLogisticsBill::getOperatorCode, param.getOperatorCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBill::getId, param.getId());
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
        int total = tbLogisticsBillMapper.deleteById(id);
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
        int delCount = tbLogisticsBillMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}