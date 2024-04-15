package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRecDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackListBoxRecDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbBscOverseasWayService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListBoxRecDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListDetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 出货清单和亚马逊货件关系映射-明细;(tb_logistics_pack_list_box_rec_det)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackListBoxRecDetServiceImpl  extends ServiceImpl<TbLogisticsPackListBoxRecDetMapper, TbLogisticsPackListBoxRecDet> implements TbLogisticsPackListBoxRecDetService {
    @Resource
    private TbLogisticsPackListBoxRecDetMapper tbLogisticsPackListBoxRecDetMapper;

    @Resource
    private TbBscOverseasWayService tbBscOverseasWayService;

    @Resource
    private   TbLogisticsPackListDetService tbLogisticsPackListDetService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRecDet queryById(BigDecimal sysDetId){
        return tbLogisticsPackListBoxRecDetMapper.selectById(sysDetId);
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
    public Page<TbLogisticsPackListBoxRecDetResult> paginQuery(TbLogisticsPackListBoxRecDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListBoxRecDet::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsPackListBoxRecDet::getMerchantSku, param.getMerchantSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackListBoxRecDet::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOriginPackDetBoxNumUpload()),TbLogisticsPackListBoxRecDet::getOriginPackDetBoxNumUpload, param.getOriginPackDetBoxNumUpload());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListBoxRecDet::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackListBoxRecDet::getPackDetBoxCode, param.getPackDetBoxCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOwName()),TbLogisticsPackListBoxRecDet::getOwName, param.getOwName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListBoxRecDet::getShipmentRealStatus, param.getShipmentRealStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListBoxRecDet::getPackListCode, param.getPackListCode());
        //2. 执行分页查询
        Page<TbLogisticsPackListBoxRecDetResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackListBoxRecDetResult> selectResult = tbLogisticsPackListBoxRecDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRecDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRecDet insert(TbLogisticsPackListBoxRecDet tbLogisticsPackListBoxRecDet){
        tbLogisticsPackListBoxRecDetMapper.insert(tbLogisticsPackListBoxRecDet);
        return tbLogisticsPackListBoxRecDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRecDet update(TbLogisticsPackListBoxRecDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackListBoxRecDet>(tbLogisticsPackListBoxRecDetMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListBoxRecDet::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsPackListBoxRecDet::getMerchantSku, param.getMerchantSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackListBoxRecDet::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOriginPackDetBoxNumUpload()),TbLogisticsPackListBoxRecDet::getOriginPackDetBoxNumUpload, param.getOriginPackDetBoxNumUpload());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListBoxRecDet::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackListBoxRecDet::getPackDetBoxCode, param.getPackDetBoxCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwName()),TbLogisticsPackListBoxRecDet::getOwName, param.getOwName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListBoxRecDet::getShipmentRealStatus, param.getShipmentRealStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListBoxRecDet::getPackListCode, param.getPackListCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackListBoxRecDet::getSysDetId, param.getSysDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getSysDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal sysDetId){
        int total = tbLogisticsPackListBoxRecDetMapper.deleteById(sysDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param sysDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysDetIdList) {
        int delCount = tbLogisticsPackListBoxRecDetMapper.deleteBatchIds(sysDetIdList);
        if (sysDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据PackCode出货清单号 删除 出货清单和亚马逊货件关系映射明细
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     @DataSource(name = "logistics")
     @Override
     public boolean deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRecDet::getPackCode, packCode);
         int delCount = tbLogisticsPackListBoxRecDetMapper.delete(wrapper);
         if (delCount>0) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackListBoxRecDet> queryByShipmentId(String shipmentId) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRecDet::getShipmentId, shipmentId);
         return tbLogisticsPackListBoxRecDetMapper.selectList(wrapper);
     }

     @DataSource(name = "logistics")
     @Override
     public ResponseData batchUpdate(List<TbLogisticsPackListBoxRecDetParam> detParamList) {
         for (TbLogisticsPackListBoxRecDetParam detParam : detParamList) {
             LambdaUpdateWrapper<TbLogisticsPackListBoxRecDet> updateWrapper = new LambdaUpdateWrapper<>();
             updateWrapper.eq(TbLogisticsPackListBoxRecDet::getPackListCode, detParam.getPackListCode());
             updateWrapper.eq(TbLogisticsPackListBoxRecDet::getMerchantSku, detParam.getMerchantSku());
             updateWrapper.eq(TbLogisticsPackListBoxRecDet::getPackDetBoxNumUpload, detParam.getPackDetBoxNumUpload());
             updateWrapper.set(TbLogisticsPackListBoxRecDet::getQuantity, detParam.getQuantity());
             tbLogisticsPackListBoxRecDetMapper.update(null, updateWrapper);
//            tbBscOverseasWayService.updateDeliveryNum(detParam); 货件管理时还没有
         }
//         BoxedQTY = _db.Tables.TbLogisticsPackListBoxRecDets.Where(x => x.ShipmentID == list[0].ShipmentID && x.MerchantSKU == l.MerchantSKU).Sum(n => n.Quantity)
         //根据shipmentId更新已装箱数量
         tbLogisticsPackListDetService.updateBoxedQty(null,detParamList.get(0).getPackListCode());

         return ResponseData.success();
     }


     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackListBoxRecDet> queryByPackListCode(String packListCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRecDet::getPackListCode, packListCode);
         return tbLogisticsPackListBoxRecDetMapper.selectList(wrapper);
     }

     /**
      * 根据PackListCode出货清单号 删除 出货清单和亚马逊货件关系映射明细
      *
      * @param packListCode 出货清单号
      * @return 是否成功
      */
     @DataSource(name = "logistics")
     @Override
     public int deleteByPackListCode(String packListCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRecDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRecDet::getPackListCode, packListCode);
         return tbLogisticsPackListBoxRecDetMapper.delete(wrapper);
     }
 }