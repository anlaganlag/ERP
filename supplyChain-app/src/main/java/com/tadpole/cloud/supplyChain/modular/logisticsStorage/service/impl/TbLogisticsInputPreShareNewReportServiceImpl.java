package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsInputPreShareNewReport;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsInputPreShareNewReportMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsInputPreShareNewReportService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsInputPreShareNewReportResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsInputPreShareNewReportParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流投入预估分担报告-新-暂时不需要;(tb_logistics_input_pre_share_new_report)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsInputPreShareNewReportServiceImpl  extends ServiceImpl<TbLogisticsInputPreShareNewReportMapper, TbLogisticsInputPreShareNewReport> implements TbLogisticsInputPreShareNewReportService{
    @Resource
    private TbLogisticsInputPreShareNewReportMapper tbLogisticsInputPreShareNewReportMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkLogisrId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareNewReport queryById(BigDecimal pkLogisrId){
        return tbLogisticsInputPreShareNewReportMapper.selectById(pkLogisrId);
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
    public Page<TbLogisticsInputPreShareNewReportResult> paginQuery(TbLogisticsInputPreShareNewReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsInputPreShareNewReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrPlatformName()),TbLogisticsInputPreShareNewReport::getBusLogisrPlatformName, param.getBusLogisrPlatformName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrShopNameSimple()),TbLogisticsInputPreShareNewReport::getBusLogisrShopNameSimple, param.getBusLogisrShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrCountryCode()),TbLogisticsInputPreShareNewReport::getBusLogisrCountryCode, param.getBusLogisrCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrDepart()),TbLogisticsInputPreShareNewReport::getBusLogisrDepart, param.getBusLogisrDepart());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrTeam()),TbLogisticsInputPreShareNewReport::getBusLogisrTeam, param.getBusLogisrTeam());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrTraMode1()),TbLogisticsInputPreShareNewReport::getBusLogisrTraMode1, param.getBusLogisrTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrTraMode2()),TbLogisticsInputPreShareNewReport::getBusLogisrTraMode2, param.getBusLogisrTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrStoreHouseName()),TbLogisticsInputPreShareNewReport::getBusLogisrStoreHouseName, param.getBusLogisrStoreHouseName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrPackCode()),TbLogisticsInputPreShareNewReport::getBusLogisrPackCode, param.getBusLogisrPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrOddNumb()),TbLogisticsInputPreShareNewReport::getBusLogisrOddNumb, param.getBusLogisrOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrMatCode()),TbLogisticsInputPreShareNewReport::getBusLogisrMatCode, param.getBusLogisrMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrSku()),TbLogisticsInputPreShareNewReport::getBusLogisrSku, param.getBusLogisrSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrFnsku()),TbLogisticsInputPreShareNewReport::getBusLogisrFnsku, param.getBusLogisrFnsku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBusLogisrPackDirectCode()),TbLogisticsInputPreShareNewReport::getBusLogisrPackDirectCode, param.getBusLogisrPackDirectCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsInputPreShareNewReport::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsInputPreShareNewReport::getSysPerCode, param.getSysPerCode());
        //2. 执行分页查询
        Page<TbLogisticsInputPreShareNewReportResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsInputPreShareNewReportResult> selectResult = tbLogisticsInputPreShareNewReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsInputPreShareNewReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareNewReport insert(TbLogisticsInputPreShareNewReport tbLogisticsInputPreShareNewReport){
        tbLogisticsInputPreShareNewReportMapper.insert(tbLogisticsInputPreShareNewReport);
        return tbLogisticsInputPreShareNewReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsInputPreShareNewReport update(TbLogisticsInputPreShareNewReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsInputPreShareNewReport> wrapper = new LambdaUpdateChainWrapper<TbLogisticsInputPreShareNewReport>(tbLogisticsInputPreShareNewReportMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrPlatformName()),TbLogisticsInputPreShareNewReport::getBusLogisrPlatformName, param.getBusLogisrPlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrShopNameSimple()),TbLogisticsInputPreShareNewReport::getBusLogisrShopNameSimple, param.getBusLogisrShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrCountryCode()),TbLogisticsInputPreShareNewReport::getBusLogisrCountryCode, param.getBusLogisrCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrDepart()),TbLogisticsInputPreShareNewReport::getBusLogisrDepart, param.getBusLogisrDepart());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrTeam()),TbLogisticsInputPreShareNewReport::getBusLogisrTeam, param.getBusLogisrTeam());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrTraMode1()),TbLogisticsInputPreShareNewReport::getBusLogisrTraMode1, param.getBusLogisrTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrTraMode2()),TbLogisticsInputPreShareNewReport::getBusLogisrTraMode2, param.getBusLogisrTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrStoreHouseName()),TbLogisticsInputPreShareNewReport::getBusLogisrStoreHouseName, param.getBusLogisrStoreHouseName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrPackCode()),TbLogisticsInputPreShareNewReport::getBusLogisrPackCode, param.getBusLogisrPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrOddNumb()),TbLogisticsInputPreShareNewReport::getBusLogisrOddNumb, param.getBusLogisrOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrMatCode()),TbLogisticsInputPreShareNewReport::getBusLogisrMatCode, param.getBusLogisrMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrSku()),TbLogisticsInputPreShareNewReport::getBusLogisrSku, param.getBusLogisrSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrFnsku()),TbLogisticsInputPreShareNewReport::getBusLogisrFnsku, param.getBusLogisrFnsku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBusLogisrPackDirectCode()),TbLogisticsInputPreShareNewReport::getBusLogisrPackDirectCode, param.getBusLogisrPackDirectCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsInputPreShareNewReport::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsInputPreShareNewReport::getSysPerCode, param.getSysPerCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsInputPreShareNewReport::getPkLogisrId, param.getPkLogisrId());
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
        int total = tbLogisticsInputPreShareNewReportMapper.deleteById(pkLogisrId);
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
        int delCount = tbLogisticsInputPreShareNewReportMapper.deleteBatchIds(pkLogisrIdList);
        if (pkLogisrIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}