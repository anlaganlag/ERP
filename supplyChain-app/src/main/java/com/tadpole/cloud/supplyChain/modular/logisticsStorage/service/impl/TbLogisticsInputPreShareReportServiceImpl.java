package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsInputPreShareReportMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsInputPreShareReportService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsInputPreShareReportParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流投入预估分担报告-暂时不需要;(tb_logistics_input_pre_share_report)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsInputPreShareReportServiceImpl  extends ServiceImpl<TbLogisticsInputPreShareReportMapper, TbLogisticsInputPreShareReport> implements TbLogisticsInputPreShareReportService{
    @Resource
    private TbLogisticsInputPreShareReportMapper tbLogisticsInputPreShareReportMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogisrId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareReport queryById(BigDecimal pkLogisrId){
        return tbLogisticsInputPreShareReportMapper.selectById(pkLogisrId);
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
    public Page<TbLogisticsInputPreShareReportResult> paginQuery(TbLogisticsInputPreShareReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsInputPreShareReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrPlatformName()),TbLogisticsInputPreShareReport::getBusLogisrPlatformName, param.getBusLogisrPlatformName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrShopNameSimple()),TbLogisticsInputPreShareReport::getBusLogisrShopNameSimple, param.getBusLogisrShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrCountryCode()),TbLogisticsInputPreShareReport::getBusLogisrCountryCode, param.getBusLogisrCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrDepart()),TbLogisticsInputPreShareReport::getBusLogisrDepart, param.getBusLogisrDepart());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrTeam()),TbLogisticsInputPreShareReport::getBusLogisrTeam, param.getBusLogisrTeam());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrMatCode()),TbLogisticsInputPreShareReport::getBusLogisrMatCode, param.getBusLogisrMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrMatOperateCate()),TbLogisticsInputPreShareReport::getBusLogisrMatOperateCate, param.getBusLogisrMatOperateCate());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsInputPreShareReport::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsInputPreShareReport::getSysPerCode, param.getSysPerCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogComfirmBillCurrency()),TbLogisticsInputPreShareReport::getBusLogComfirmBillCurrency, param.getBusLogComfirmBillCurrency());
        //2. 执行分页查询
        Page<TbLogisticsInputPreShareReportResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsInputPreShareReportResult> selectResult = tbLogisticsInputPreShareReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsInputPreShareReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareReport insert(TbLogisticsInputPreShareReport tbLogisticsInputPreShareReport){
        tbLogisticsInputPreShareReportMapper.insert(tbLogisticsInputPreShareReport);
        return tbLogisticsInputPreShareReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareReport update(TbLogisticsInputPreShareReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsInputPreShareReport> wrapper = new LambdaUpdateChainWrapper<TbLogisticsInputPreShareReport>(tbLogisticsInputPreShareReportMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrPlatformName()),TbLogisticsInputPreShareReport::getBusLogisrPlatformName, param.getBusLogisrPlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrShopNameSimple()),TbLogisticsInputPreShareReport::getBusLogisrShopNameSimple, param.getBusLogisrShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrCountryCode()),TbLogisticsInputPreShareReport::getBusLogisrCountryCode, param.getBusLogisrCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrDepart()),TbLogisticsInputPreShareReport::getBusLogisrDepart, param.getBusLogisrDepart());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrTeam()),TbLogisticsInputPreShareReport::getBusLogisrTeam, param.getBusLogisrTeam());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrMatCode()),TbLogisticsInputPreShareReport::getBusLogisrMatCode, param.getBusLogisrMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrMatOperateCate()),TbLogisticsInputPreShareReport::getBusLogisrMatOperateCate, param.getBusLogisrMatOperateCate());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsInputPreShareReport::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsInputPreShareReport::getSysPerCode, param.getSysPerCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogComfirmBillCurrency()),TbLogisticsInputPreShareReport::getBusLogComfirmBillCurrency, param.getBusLogComfirmBillCurrency());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsInputPreShareReport::getPkLogisrId, param.getPkLogisrId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPkLogisrId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkLogisrId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal pkLogisrId){
        int total = tbLogisticsInputPreShareReportMapper.deleteById(pkLogisrId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param pkLogisrIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkLogisrIdList) {
        int delCount = tbLogisticsInputPreShareReportMapper.deleteBatchIds(pkLogisrIdList);
        if (pkLogisrIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}