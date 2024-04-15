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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsShipmentDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipmentDetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 亚马逊发货申请记录-明细项;(tb_logistics_shipment_det)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsShipmentDetServiceImpl  extends ServiceImpl<TbLogisticsShipmentDetMapper, TbLogisticsShipmentDet> implements TbLogisticsShipmentDetService {
    @Resource
    private TbLogisticsShipmentDetMapper tbLogisticsShipmentDetMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shipDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentDet queryById(BigDecimal shipDetId){
        return tbLogisticsShipmentDetMapper.selectById(shipDetId);
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
    public Page<TbLogisticsShipmentDetResult> paginQuery(TbLogisticsShipmentDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsShipmentDet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentDet::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipmentDet::getPlanName, param.getPlanName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsShipmentDet::getMerchantSku, param.getMerchantSku());
        //2. 执行分页查询
        Page<TbLogisticsShipmentDetResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsShipmentDetResult> selectResult = tbLogisticsShipmentDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentDet insert(TbLogisticsShipmentDet tbLogisticsShipmentDet){
        tbLogisticsShipmentDetMapper.insert(tbLogisticsShipmentDet);
        return tbLogisticsShipmentDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipmentDet update(TbLogisticsShipmentDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsShipmentDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsShipmentDet>(tbLogisticsShipmentDetMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsShipmentDet::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPlanName()),TbLogisticsShipmentDet::getPlanName, param.getPlanName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMerchantSku()),TbLogisticsShipmentDet::getMerchantSku, param.getMerchantSku());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsShipmentDet::getShipDetId, param.getShipDetId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getShipDetId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param shipDetId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal shipDetId){
        int total = tbLogisticsShipmentDetMapper.deleteById(shipDetId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param shipDetIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> shipDetIdList) {
        int delCount = tbLogisticsShipmentDetMapper.deleteBatchIds(shipDetIdList);
        if (shipDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据出货清单号查询发货计划明细项List
      * @param packCode
      * @return
      */
     @Override
     @DataSource(name = "logistics")
     public List<TbLogisticsShipmentDet> queryByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsShipmentDet> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsShipmentDet::getPackCode, packCode);

       return   tbLogisticsShipmentDetMapper.selectList(queryWrapper);
     }

     /**
      * 根据出货清单号 删除 发货计划明细项
      * @param packCode
      * @return
      */
     @Override
     @DataSource(name = "logistics")
     public int deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsShipmentDet> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbLogisticsShipmentDet::getPackCode, packCode);
         return   tbLogisticsShipmentDetMapper.delete(queryWrapper);
     }
 }