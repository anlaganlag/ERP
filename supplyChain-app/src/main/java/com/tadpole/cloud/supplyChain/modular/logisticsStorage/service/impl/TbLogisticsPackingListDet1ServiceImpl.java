package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackingListDet1Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet1Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListDet1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;(tb_logistics_packing_list_det1)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackingListDet1ServiceImpl  extends ServiceImpl<TbLogisticsPackingListDet1Mapper, TbLogisticsPackingListDet1> implements TbLogisticsPackingListDet1Service {
    @Resource
    private TbLogisticsPackingListDet1Mapper tbLogisticsPackingListDet1Mapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet1 queryById(BigDecimal packDetId){
        return tbLogisticsPackingListDet1Mapper.selectById(packDetId);
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
    public Page<TbLogisticsPackingListDet1Result> paginQuery(TbLogisticsPackingListDet1Param param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackingListDet1> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackingListDet1::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackingListDet1::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxType()),TbLogisticsPackingListDet1::getPackDetBoxType, param.getPackDetBoxType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackingListDet1::getPackDetBoxCode, param.getPackDetBoxCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxSpeUnit()),TbLogisticsPackingListDet1::getPackDetBoxSpeUnit, param.getPackDetBoxSpeUnit());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxVoluUnit()),TbLogisticsPackingListDet1::getPackDetBoxVoluUnit, param.getPackDetBoxVoluUnit());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxWeigUnit()),TbLogisticsPackingListDet1::getPackDetBoxWeigUnit, param.getPackDetBoxWeigUnit());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxLogState()),TbLogisticsPackingListDet1::getPackDetBoxLogState, param.getPackDetBoxLogState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxPlanState()),TbLogisticsPackingListDet1::getPackDetBoxPlanState, param.getPackDetBoxPlanState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxFirLogNo()),TbLogisticsPackingListDet1::getPackDetBoxFirLogNo, param.getPackDetBoxFirLogNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsPackingListDet1::getLogTraMode2, param.getLogTraMode2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxEndLogNo()),TbLogisticsPackingListDet1::getPackDetBoxEndLogNo, param.getPackDetBoxEndLogNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxBillState()),TbLogisticsPackingListDet1::getPackDetBoxBillState, param.getPackDetBoxBillState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxLogCostNo()),TbLogisticsPackingListDet1::getPackDetBoxLogCostNo, param.getPackDetBoxLogCostNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxTaxCostNo()),TbLogisticsPackingListDet1::getPackDetBoxTaxCostNo, param.getPackDetBoxTaxCostNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxLogClaimState()),TbLogisticsPackingListDet1::getPackDetBoxLogClaimState, param.getPackDetBoxLogClaimState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackingListDet1::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsPackingListDet1::getLogTraMode1, param.getLogTraMode1());
        //2. 执行分页查询
        Page<TbLogisticsPackingListDet1Result> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackingListDet1Result> selectResult = tbLogisticsPackingListDet1Mapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet1 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet1 insert(TbLogisticsPackingListDet1 tbLogisticsPackingListDet1){
        tbLogisticsPackingListDet1Mapper.insert(tbLogisticsPackingListDet1);
        return tbLogisticsPackingListDet1;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet1 update(TbLogisticsPackingListDet1Param param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackingListDet1> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackingListDet1>(tbLogisticsPackingListDet1Mapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackingListDet1::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackingListDet1::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxType()),TbLogisticsPackingListDet1::getPackDetBoxType, param.getPackDetBoxType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackingListDet1::getPackDetBoxCode, param.getPackDetBoxCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxSpeUnit()),TbLogisticsPackingListDet1::getPackDetBoxSpeUnit, param.getPackDetBoxSpeUnit());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxVoluUnit()),TbLogisticsPackingListDet1::getPackDetBoxVoluUnit, param.getPackDetBoxVoluUnit());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxWeigUnit()),TbLogisticsPackingListDet1::getPackDetBoxWeigUnit, param.getPackDetBoxWeigUnit());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxLogState()),TbLogisticsPackingListDet1::getPackDetBoxLogState, param.getPackDetBoxLogState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxPlanState()),TbLogisticsPackingListDet1::getPackDetBoxPlanState, param.getPackDetBoxPlanState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxFirLogNo()),TbLogisticsPackingListDet1::getPackDetBoxFirLogNo, param.getPackDetBoxFirLogNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode2()),TbLogisticsPackingListDet1::getLogTraMode2, param.getLogTraMode2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxEndLogNo()),TbLogisticsPackingListDet1::getPackDetBoxEndLogNo, param.getPackDetBoxEndLogNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxBillState()),TbLogisticsPackingListDet1::getPackDetBoxBillState, param.getPackDetBoxBillState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxLogCostNo()),TbLogisticsPackingListDet1::getPackDetBoxLogCostNo, param.getPackDetBoxLogCostNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxTaxCostNo()),TbLogisticsPackingListDet1::getPackDetBoxTaxCostNo, param.getPackDetBoxTaxCostNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxLogClaimState()),TbLogisticsPackingListDet1::getPackDetBoxLogClaimState, param.getPackDetBoxLogClaimState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackingListDet1::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsPackingListDet1::getLogTraMode1, param.getLogTraMode1());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackingListDet1::getPackDetId, param.getPackDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPackDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param packDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal packDetId){
        int total = tbLogisticsPackingListDet1Mapper.deleteById(packDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param packDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> packDetIdList) {
        int delCount = tbLogisticsPackingListDet1Mapper.deleteBatchIds(packDetIdList);
        if (packDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 通过出货清单号查询 明细1的数据
     *
     * @param packCodeList
     * @return 实例对象
     */
     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackingListDet1> getByPackCode(List<String> packCodeList) {
         LambdaQueryWrapper<TbLogisticsPackingListDet1> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.in(TbLogisticsPackingListDet1::getPackCode, packCodeList);
         queryWrapper.orderByAsc(TbLogisticsPackingListDet1::getPackDetId);
         return tbLogisticsPackingListDet1Mapper.selectList(queryWrapper);

     }

     /**
      * 获取通关所需箱件信息
      *
      * @param packCodeList
      * @return 实例对象
      */
     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackingListDet1Result> getClearanceBoxInfoData(String lhrOddNumb, List<String> packCodeList) {
         return tbLogisticsPackingListDet1Mapper.getClearanceBoxInfoData( lhrOddNumb, packCodeList);
     }

     /**
      * 根据批次号和快递单号 重置出货清单明细1数据
      *
      * @param lhrCode
      * @param lhrOddNumb
      * @return 实例对象
      */
     @DataSource(name = "logistics")
     @Override
     public int resetData(String lhrCode, String lhrOddNumb) {

         return tbLogisticsPackingListDet1Mapper.resetData(lhrCode, lhrOddNumb);
     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackingListDet1> queryByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackingListDet1> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsPackingListDet1::getPackCode, packCode);
         return tbLogisticsPackingListDet1Mapper.selectList(queryWrapper);
     }

     @DataSource(name = "logistics")
     @Override
     public int deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackingListDet1> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsPackingListDet1::getPackCode, packCode);
         return tbLogisticsPackingListDet1Mapper.delete(queryWrapper);

     }
 }