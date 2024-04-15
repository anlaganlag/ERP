package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsTrackStatusEnum;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsListToEndRouteMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToEndRouteService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToEndRouteParam;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流单尾程信息;(tb_logistics_list_to_end_route)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsListToEndRouteServiceImpl  extends ServiceImpl<TbLogisticsListToEndRouteMapper, TbLogisticsListToEndRoute> implements TbLogisticsListToEndRouteService{
    @Resource
    private TbLogisticsListToEndRouteMapper tbLogisticsListToEndRouteMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRoute queryById(BigDecimal id){
        return tbLogisticsListToEndRouteMapper.selectById(id);
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
    public Page<TbLogisticsListToEndRouteResult> paginQuery(TbLogisticsListToEndRouteParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsListToEndRoute> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsListToEndRoute::getLhrCode, param.getLhrCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToEndRoute::getLhrOddNumb, param.getLhrOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerOddNumb()),TbLogisticsListToEndRoute::getLerOddNumb, param.getLerOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsListToEndRoute::getSysPerCode, param.getSysPerCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsListToEndRoute::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerState()),TbLogisticsListToEndRoute::getLerState, param.getLerState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerStateNote()),TbLogisticsListToEndRoute::getLerStateNote, param.getLerStateNote());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsListToEndRoute::getLogTraMode1, param.getLogTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogEndRouteLink()),TbLogisticsListToEndRoute::getLogEndRouteLink, param.getLogEndRouteLink());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreChargType()),TbLogisticsListToEndRoute::getLerPreChargType, param.getLerPreChargType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreLogSurFeeDet()),TbLogisticsListToEndRoute::getLerPreLogSurFeeDet, param.getLerPreLogSurFeeDet());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreLogTaxFeeDet()),TbLogisticsListToEndRoute::getLerPreLogTaxFeeDet, param.getLerPreLogTaxFeeDet());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerLogComfirmBillCurrency()),TbLogisticsListToEndRoute::getLerLogComfirmBillCurrency, param.getLerLogComfirmBillCurrency());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreLogUnitPriceType()),TbLogisticsListToEndRoute::getLerPreLogUnitPriceType, param.getLerPreLogUnitPriceType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreLogAddAndSundryFeeRemark()),TbLogisticsListToEndRoute::getLerPreLogAddAndSundryFeeRemark, param.getLerPreLogAddAndSundryFeeRemark());
        //2. 执行分页查询
        Page<TbLogisticsListToEndRouteResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsListToEndRouteResult> selectResult = tbLogisticsListToEndRouteMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToEndRoute 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRoute insert(TbLogisticsListToEndRoute tbLogisticsListToEndRoute){
        tbLogisticsListToEndRouteMapper.insert(tbLogisticsListToEndRoute);
        return tbLogisticsListToEndRoute;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRoute update(TbLogisticsListToEndRouteParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsListToEndRoute> wrapper = new LambdaUpdateChainWrapper<TbLogisticsListToEndRoute>(tbLogisticsListToEndRouteMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsListToEndRoute::getLhrCode, param.getLhrCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToEndRoute::getLhrOddNumb, param.getLhrOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerOddNumb()),TbLogisticsListToEndRoute::getLerOddNumb, param.getLerOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerCode()),TbLogisticsListToEndRoute::getSysPerCode, param.getSysPerCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsListToEndRoute::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerState()),TbLogisticsListToEndRoute::getLerState, param.getLerState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerStateNote()),TbLogisticsListToEndRoute::getLerStateNote, param.getLerStateNote());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsListToEndRoute::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogEndRouteLink()),TbLogisticsListToEndRoute::getLogEndRouteLink, param.getLogEndRouteLink());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreChargType()),TbLogisticsListToEndRoute::getLerPreChargType, param.getLerPreChargType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreLogSurFeeDet()),TbLogisticsListToEndRoute::getLerPreLogSurFeeDet, param.getLerPreLogSurFeeDet());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreLogTaxFeeDet()),TbLogisticsListToEndRoute::getLerPreLogTaxFeeDet, param.getLerPreLogTaxFeeDet());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerLogComfirmBillCurrency()),TbLogisticsListToEndRoute::getLerLogComfirmBillCurrency, param.getLerLogComfirmBillCurrency());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreLogUnitPriceType()),TbLogisticsListToEndRoute::getLerPreLogUnitPriceType, param.getLerPreLogUnitPriceType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreLogAddAndSundryFeeRemark()),TbLogisticsListToEndRoute::getLerPreLogAddAndSundryFeeRemark, param.getLerPreLogAddAndSundryFeeRemark());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsListToEndRoute::getId, param.getId());
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
        int total = tbLogisticsListToEndRouteMapper.deleteById(id);
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
        int delCount = tbLogisticsListToEndRouteMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据批次号和快递单号 删除物流单尾程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb) {
         LambdaQueryWrapper<TbLogisticsListToEndRoute> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsListToEndRoute::getLhrCode, lhrCode).eq(TbLogisticsListToEndRoute::getLhrOddNumb, lhrOddNumb);
         return tbLogisticsListToEndRouteMapper.delete(wrapper);

     }

     /**
      * 根据批次号和快递单号 更新 物流单尾程信息 状态
      * @param lhrCode
      * @param lhrOddNumb
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state,Date signDate) {

         LambdaUpdateWrapper<TbLogisticsListToEndRoute> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(TbLogisticsListToEndRoute::getLhrCode, lhrCode)
                 .eq(TbLogisticsListToEndRoute::getLhrOddNumb, lhrOddNumb)
                 .set(LogisticsTrackStatusEnum.STATUS_6.getName().equals(state),TbLogisticsListToEndRoute::getLerSignDate ,signDate )
                 .set(TbLogisticsListToEndRoute::getSysUpdDatetime ,new Date() )
                 .set(TbLogisticsListToEndRoute::getLerState, state);
         return tbLogisticsListToEndRouteMapper.update(null,wrapper);
     }
 }