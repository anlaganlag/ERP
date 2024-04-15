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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackingListDet2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackingListDet2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackingListDet2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 出货清单明细2-装箱内容-金蝶+海外仓;(tb_logistics_packing_list_det2)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackingListDet2ServiceImpl  extends ServiceImpl<TbLogisticsPackingListDet2Mapper, TbLogisticsPackingListDet2> implements TbLogisticsPackingListDet2Service {
    @Resource
    private TbLogisticsPackingListDet2Mapper tbLogisticsPackingListDet2Mapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet2 queryById(BigDecimal packDetId){
        return tbLogisticsPackingListDet2Mapper.selectById(packDetId);
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
    public Page<TbLogisticsPackingListDet2Result> paginQuery(TbLogisticsPackingListDet2Param param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackingListDet2> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackingListDet2::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),TbLogisticsPackingListDet2::getSku, param.getSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMateCode()),TbLogisticsPackingListDet2::getMateCode, param.getMateCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackingListDet2::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFnSku()),TbLogisticsPackingListDet2::getFnSku, param.getFnSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackingListDet2::getPackDetBoxCode, param.getPackDetBoxCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPickListCode()),TbLogisticsPackingListDet2::getPickListCode, param.getPickListCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDepName()),TbLogisticsPackingListDet2::getDepName, param.getDepName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getTeamName()),TbLogisticsPackingListDet2::getTeamName, param.getTeamName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPerName()),TbLogisticsPackingListDet2::getPerName, param.getPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackSugShipMethod()),TbLogisticsPackingListDet2::getPackSugShipMethod, param.getPackSugShipMethod());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDirectCode()),TbLogisticsPackingListDet2::getPackDirectCode, param.getPackDirectCode());
        //2. 执行分页查询
        Page<TbLogisticsPackingListDet2Result> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackingListDet2Result> selectResult = tbLogisticsPackingListDet2Mapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackingListDet2 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet2 insert(TbLogisticsPackingListDet2 tbLogisticsPackingListDet2){
        tbLogisticsPackingListDet2Mapper.insert(tbLogisticsPackingListDet2);
        return tbLogisticsPackingListDet2;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackingListDet2 update(TbLogisticsPackingListDet2Param param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackingListDet2> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackingListDet2>(tbLogisticsPackingListDet2Mapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackingListDet2::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getSku()),TbLogisticsPackingListDet2::getSku, param.getSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMateCode()),TbLogisticsPackingListDet2::getMateCode, param.getMateCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxNumUpload()),TbLogisticsPackingListDet2::getPackDetBoxNumUpload, param.getPackDetBoxNumUpload());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFnSku()),TbLogisticsPackingListDet2::getFnSku, param.getFnSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsPackingListDet2::getPackDetBoxCode, param.getPackDetBoxCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPickListCode()),TbLogisticsPackingListDet2::getPickListCode, param.getPickListCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDepName()),TbLogisticsPackingListDet2::getDepName, param.getDepName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getTeamName()),TbLogisticsPackingListDet2::getTeamName, param.getTeamName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPerName()),TbLogisticsPackingListDet2::getPerName, param.getPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackSugShipMethod()),TbLogisticsPackingListDet2::getPackSugShipMethod, param.getPackSugShipMethod());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDirectCode()),TbLogisticsPackingListDet2::getPackDirectCode, param.getPackDirectCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackingListDet2::getPackDetId, param.getPackDetId());
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
        int total = tbLogisticsPackingListDet2Mapper.deleteById(packDetId);
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
        int delCount = tbLogisticsPackingListDet2Mapper.deleteBatchIds(packDetIdList);
        if (packDetIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 更具物流单号以及出货清单号 查询清关数据
      * @param lhrOddNumb
      * @param packCodeList
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public List<TbShipemtListClearancModel> getClearanceData(String lhrOddNumb, List<String> packCodeList) {
         return  tbLogisticsPackingListDet2Mapper.getClearanceData( lhrOddNumb, packCodeList);
     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackingListDet2> queryByLhrCode(String lhrCode) {

         return  tbLogisticsPackingListDet2Mapper.queryByLhrCode( lhrCode);
     }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsPackingListDet2> queryByPackCode(List<String> packCodeList) {
         LambdaQueryWrapper<TbLogisticsPackingListDet2> wrapper = new LambdaQueryWrapper<>();
         wrapper.in(TbLogisticsPackingListDet2::getPackCode, packCodeList);
         return  tbLogisticsPackingListDet2Mapper.selectList( wrapper);
     }

     @DataSource(name = "logistics")
     @Override
     public int deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackingListDet2> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackingListDet2::getPackCode, packCode);
         return  tbLogisticsPackingListDet2Mapper.delete( wrapper);
     }
 }