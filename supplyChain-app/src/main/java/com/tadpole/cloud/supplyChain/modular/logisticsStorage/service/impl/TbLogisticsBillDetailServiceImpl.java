package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBillDetail;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBillDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBillDetailService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBillDetailResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBillDetailParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流账单明细;(tb_logistics_bill_detail)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBillDetailServiceImpl  extends ServiceImpl<TbLogisticsBillDetailMapper, TbLogisticsBillDetail> implements TbLogisticsBillDetailService{
    @Resource
    private TbLogisticsBillDetailMapper tbLogisticsBillDetailMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillDetail queryById(BigDecimal id){
        return tbLogisticsBillDetailMapper.selectById(id);
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
    public Page<TbLogisticsBillDetailResult> paginQuery(TbLogisticsBillDetailParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBillDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsBillDetail::getLpCode, param.getLpCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBillDetail::getLcCode, param.getLcCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillDetail::getBillNum, param.getBillNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillDetail::getLhrOddNum, param.getLhrOddNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsBillDetail::getElePlatformName, param.getElePlatformName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsBillDetail::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsBillDetail::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTransferNum()),TbLogisticsBillDetail::getTransferNum, param.getTransferNum());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChannel()),TbLogisticsBillDetail::getChannel, param.getChannel());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDeclaredVal()),TbLogisticsBillDetail::getDeclaredVal, param.getDeclaredVal());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDestinationCountry()),TbLogisticsBillDetail::getDestinationCountry, param.getDestinationCountry());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDestinationWarehouse()),TbLogisticsBillDetail::getDestinationWarehouse, param.getDestinationWarehouse());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsBillDetail::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getVatNo()),TbLogisticsBillDetail::getVatNo, param.getVatNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getEori()),TbLogisticsBillDetail::getEori, param.getEori());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsBillDetail::getLhrCode, param.getLhrCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getChargType()),TbLogisticsBillDetail::getChargType, param.getChargType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStatus()),TbLogisticsBillDetail::getStatus, param.getStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsBillDetail::getLogRedOrBlueList, param.getLogRedOrBlueList());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsBillDetail::getLogTraMode1, param.getLogTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsBillDetail::getLogTraMode2, param.getLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogSeaTraRoute()),TbLogisticsBillDetail::getLogSeaTraRoute, param.getLogSeaTraRoute());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogGoodCharacter()),TbLogisticsBillDetail::getLogGoodCharacter, param.getLogGoodCharacter());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogSeaTraConType()),TbLogisticsBillDetail::getLogSeaTraConType, param.getLogSeaTraConType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerPreChargType()),TbLogisticsBillDetail::getLerPreChargType, param.getLerPreChargType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getRemarks()),TbLogisticsBillDetail::getRemarks, param.getRemarks());
        //2. 执行分页查询
        Page<TbLogisticsBillDetailResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBillDetailResult> selectResult = tbLogisticsBillDetailMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBillDetail 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillDetail insert(TbLogisticsBillDetail tbLogisticsBillDetail){
        tbLogisticsBillDetailMapper.insert(tbLogisticsBillDetail);
        return tbLogisticsBillDetail;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBillDetail update(TbLogisticsBillDetailParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBillDetail> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBillDetail>(tbLogisticsBillDetailMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLpCode()),TbLogisticsBillDetail::getLpCode, param.getLpCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLcCode()),TbLogisticsBillDetail::getLcCode, param.getLcCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getBillNum()),TbLogisticsBillDetail::getBillNum, param.getBillNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNum()),TbLogisticsBillDetail::getLhrOddNum, param.getLhrOddNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsBillDetail::getElePlatformName, param.getElePlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsBillDetail::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsBillDetail::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTransferNum()),TbLogisticsBillDetail::getTransferNum, param.getTransferNum());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChannel()),TbLogisticsBillDetail::getChannel, param.getChannel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDeclaredVal()),TbLogisticsBillDetail::getDeclaredVal, param.getDeclaredVal());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDestinationCountry()),TbLogisticsBillDetail::getDestinationCountry, param.getDestinationCountry());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDestinationWarehouse()),TbLogisticsBillDetail::getDestinationWarehouse, param.getDestinationWarehouse());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsBillDetail::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getVatNo()),TbLogisticsBillDetail::getVatNo, param.getVatNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getEori()),TbLogisticsBillDetail::getEori, param.getEori());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsBillDetail::getLhrCode, param.getLhrCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getChargType()),TbLogisticsBillDetail::getChargType, param.getChargType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getStatus()),TbLogisticsBillDetail::getStatus, param.getStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRedOrBlueList()),TbLogisticsBillDetail::getLogRedOrBlueList, param.getLogRedOrBlueList());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsBillDetail::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsBillDetail::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraRoute()),TbLogisticsBillDetail::getLogSeaTraRoute, param.getLogSeaTraRoute());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogGoodCharacter()),TbLogisticsBillDetail::getLogGoodCharacter, param.getLogGoodCharacter());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogSeaTraConType()),TbLogisticsBillDetail::getLogSeaTraConType, param.getLogSeaTraConType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerPreChargType()),TbLogisticsBillDetail::getLerPreChargType, param.getLerPreChargType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getRemarks()),TbLogisticsBillDetail::getRemarks, param.getRemarks());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBillDetail::getId, param.getId());
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
        int total = tbLogisticsBillDetailMapper.deleteById(id);
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
        int delCount = tbLogisticsBillDetailMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}