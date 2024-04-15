package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBillReportMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBillReportService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillReportParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流账单报告;(tb_logistics_bill_report)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBillReportServiceImpl  extends ServiceImpl<TbLogisticsBillReportMapper, TbLogisticsBillReport> implements TbLogisticsBillReportService{
    @Resource
    private TbLogisticsBillReportMapper tbLogisticsBillReportMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillReport queryById(BigDecimal id){
        return tbLogisticsBillReportMapper.selectById(id);
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
    public Page<TbLogisticsBillReportResult> paginQuery(TbLogisticsBillReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBillReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillReport::getLhrOddNum, param.getLhrOddNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsBillReport::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsBillReport::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBillReport::getLcCode, param.getLcCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsBillReport::getLogTraMode1, param.getLogTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsBillReport::getLogTraMode2, param.getLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsBillReport::getLogRedOrBlueList, param.getLogRedOrBlueList());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsBillReport::getLhrCode, param.getLhrCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsBillReport::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillReport::getBillNum, param.getBillNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getState()),TbLogisticsBillReport::getState, param.getState());
        //2. 执行分页查询
        Page<TbLogisticsBillReportResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBillReportResult> selectResult = tbLogisticsBillReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillReport insert(TbLogisticsBillReport tbLogisticsBillReport){
        tbLogisticsBillReportMapper.insert(tbLogisticsBillReport);
        return tbLogisticsBillReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillReport update(TbLogisticsBillReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBillReport> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBillReport>(tbLogisticsBillReportMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillReport::getLhrOddNum, param.getLhrOddNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsBillReport::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsBillReport::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBillReport::getLcCode, param.getLcCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsBillReport::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsBillReport::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsBillReport::getLogRedOrBlueList, param.getLogRedOrBlueList());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsBillReport::getLhrCode, param.getLhrCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsBillReport::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillReport::getBillNum, param.getBillNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getState()),TbLogisticsBillReport::getState, param.getState());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBillReport::getId, param.getId());
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
        int total = tbLogisticsBillReportMapper.deleteById(id);
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
        int delCount = tbLogisticsBillReportMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}