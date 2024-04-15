package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecDetParam;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWay;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbBscOverseasWayMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbBscOverseasWayService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbBscOverseasWayParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 发货汇总表;(Tb_Bsc_Overseas_Way)表服务实现类
 * @author : LSY
 * @date : 2024-1-10
 */
@Service
@Transactional
@Slf4j
public class TbBscOverseasWayServiceImpl  extends ServiceImpl<TbBscOverseasWayMapper, TbBscOverseasWay> implements TbBscOverseasWayService{
    @Resource
    private TbBscOverseasWayMapper tbBscOverseasWayMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWay queryById(BigDecimal id){
        return tbBscOverseasWayMapper.selectById(id);
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
    public Page<TbBscOverseasWayResult> paginQuery(TbBscOverseasWayParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbBscOverseasWay> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),TbBscOverseasWay::getSku, param.getSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbBscOverseasWay::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDeliverStatus()),TbBscOverseasWay::getDeliverStatus, param.getDeliverStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getReturnStatus()),TbBscOverseasWay::getReturnStatus, param.getReturnStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStatus()),TbBscOverseasWay::getStatus, param.getStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMatCode()),TbBscOverseasWay::getMatCode, param.getMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDeliverType()),TbBscOverseasWay::getDeliverType, param.getDeliverType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOwName()),TbBscOverseasWay::getOwName, param.getOwName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbBscOverseasWay::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbBscOverseasWay::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbBscOverseasWay::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPlateForm()),TbBscOverseasWay::getPlateForm, param.getPlateForm());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getReceiveWarehouse()),TbBscOverseasWay::getReceiveWarehouse, param.getReceiveWarehouse());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDeliverWarehouse()),TbBscOverseasWay::getDeliverWarehouse, param.getDeliverWarehouse());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbBscOverseasWay::getShipmentRealStatus, param.getShipmentRealStatus());
        //2. 执行分页查询
        Page<TbBscOverseasWayResult> pagin = new Page<>(current , size , true);
        IPage<TbBscOverseasWayResult> selectResult = tbBscOverseasWayMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbBscOverseasWay 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWay insert(TbBscOverseasWay tbBscOverseasWay){
        tbBscOverseasWayMapper.insert(tbBscOverseasWay);
        return tbBscOverseasWay;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbBscOverseasWay update(TbBscOverseasWayParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbBscOverseasWay> wrapper = new LambdaUpdateChainWrapper<TbBscOverseasWay>(tbBscOverseasWayMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSku()),TbBscOverseasWay::getSku, param.getSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbBscOverseasWay::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverStatus()),TbBscOverseasWay::getDeliverStatus, param.getDeliverStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getReturnStatus()),TbBscOverseasWay::getReturnStatus, param.getReturnStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getStatus()),TbBscOverseasWay::getStatus, param.getStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMatCode()),TbBscOverseasWay::getMatCode, param.getMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverType()),TbBscOverseasWay::getDeliverType, param.getDeliverType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwName()),TbBscOverseasWay::getOwName, param.getOwName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbBscOverseasWay::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbBscOverseasWay::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbBscOverseasWay::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPlateForm()),TbBscOverseasWay::getPlateForm, param.getPlateForm());
         wrapper.set(ObjectUtil.isNotEmpty(param.getReceiveWarehouse()),TbBscOverseasWay::getReceiveWarehouse, param.getReceiveWarehouse());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDeliverWarehouse()),TbBscOverseasWay::getDeliverWarehouse, param.getDeliverWarehouse());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbBscOverseasWay::getShipmentRealStatus, param.getShipmentRealStatus());
        //2. 设置主键，并更新
        wrapper.eq(TbBscOverseasWay::getId, param.getId());
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
        int total = tbBscOverseasWayMapper.deleteById(id);
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
        int delCount = tbBscOverseasWayMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据PackCode 删除发货数据汇总
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     @DataSource(name = "logistics")
     @Override
     public boolean deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbBscOverseasWay> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbBscOverseasWay::getPackCode, packCode);
         int delCount = tbBscOverseasWayMapper.delete(wrapper);
         if (delCount>0) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 更新发货清单物流发货状态
      * @param packCode
      * @param state
      */
     @Override
     @DataSource(name = "logistics")
     public void updBscOverseasWayDeliverStatus(String packCode,String state) {
         LambdaUpdateWrapper<TbBscOverseasWay> updateChainWrapper = new LambdaUpdateWrapper<>();
         updateChainWrapper.eq(TbBscOverseasWay::getPackCode, packCode)
                 .set(TbBscOverseasWay::getDeliverStatus, state);
         tbBscOverseasWayMapper.update(null, updateChainWrapper);
     }

     /**
      * 创建发货记录
      * @param packCode
      */
     @Override
     @DataSource(name = "logistics")
     public boolean createBscOverseasWay(String packCode) {
       List<TbBscOverseasWay> tbBscOverseasWayList=  tbBscOverseasWayMapper.getBscOverseasWayMapper(packCode);
         return    this.saveBatch(tbBscOverseasWayList);

     }

     /**
      * 更新出货方式 + 更新物流发货状态
      * @param lhrCode 发货批次号
      * @param lhrOddNumb 头程物流单号
      * @param deliverType 发货方式
      * @param deliverStatus 物料发货状态
      * @return
      */
     @Override
     @DataSource(name = "logistics")
     public boolean updBscOverseasWayDeliveryType(String lhrCode, String lhrOddNumb, String deliverType, String deliverStatus) {

         //--更新出货方式 null
         if (ObjectUtil.isEmpty(deliverType)) {
             tbBscOverseasWayMapper.updateDeliverTypeIsNull(lhrCode, lhrOddNumb);
         } else {
             tbBscOverseasWayMapper.updateDeliverType(lhrCode, lhrOddNumb,deliverType);
             tbBscOverseasWayMapper.updateDeliverType(lhrCode,deliverType);
         }

         //--更新物流发货状态
         tbBscOverseasWayMapper.updateDeliverStatus(lhrCode, lhrOddNumb,deliverStatus);
         return true;
     }
     /**
      * 更新出货方式
      * @param lhrCode 发货批次号
      * @param deliverType 发货方式
      * @return
      */
     @Override
     @DataSource(name = "logistics")
     public int updBscOverseasWayDeliveryType(String lhrCode, String deliverType){

        return tbBscOverseasWayMapper.updateDeliverType(lhrCode,deliverType);
     }

     /**
      * 更新发货数量
      * @param detParam
      * @return
      */
     @Override
     @DataSource(name = "logistics")
     public int updateDeliveryNum(TbLogisticsPackListBoxRecDetParam detParam) {
         LambdaUpdateWrapper<TbBscOverseasWay> updateWrapper = new LambdaUpdateWrapper<TbBscOverseasWay>()
                 .eq(TbBscOverseasWay::getShipmentId, detParam.getShipmentId())
                 .eq(TbBscOverseasWay::getSku, detParam.getMerchantSku())
                 .eq(TbBscOverseasWay::getPackCode, detParam.getPackCode())
                 .eq(TbBscOverseasWay::getPackDetBoxNum, detParam.getPackDetBoxNum())
                 .set(TbBscOverseasWay::getDeliveryNum, detParam.getQuantity());
         return tbBscOverseasWayMapper.update(null, updateWrapper);
     }

 }