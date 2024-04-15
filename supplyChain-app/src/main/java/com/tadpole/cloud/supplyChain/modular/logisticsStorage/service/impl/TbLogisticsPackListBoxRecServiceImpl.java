package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackListBoxRecMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsPackListBoxRecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出货清单和亚马逊货件关系映射表;(tb_logistics_pack_list_box_rec)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackListBoxRecServiceImpl  extends ServiceImpl<TbLogisticsPackListBoxRecMapper, TbLogisticsPackListBoxRec> implements TbLogisticsPackListBoxRecService {
    @Resource
    private TbLogisticsPackListBoxRecMapper tbLogisticsPackListBoxRecMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRec queryById(BigDecimal sysId){
        return tbLogisticsPackListBoxRecMapper.selectById(sysId);
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
    public Page<TbLogisticsPackListBoxRecResult> paginQuery(TbLogisticsPackListBoxRecParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsPackListBoxRec> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListBoxRec::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getComWarehouseType()),TbLogisticsPackListBoxRec::getComWarehouseType, param.getComWarehouseType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListBoxRec::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOwName()),TbLogisticsPackListBoxRec::getOwName, param.getOwName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipTo()),TbLogisticsPackListBoxRec::getShipTo, param.getShipTo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsPackListBoxRec::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsPackListBoxRec::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getAmaRecState()),TbLogisticsPackListBoxRec::getAmaRecState, param.getAmaRecState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListBoxRec::getShipmentRealStatus, param.getShipmentRealStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListBoxRec::getPackListCode, param.getPackListCode());
        //2. 执行分页查询
        Page<TbLogisticsPackListBoxRecResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsPackListBoxRecResult> selectResult = tbLogisticsPackListBoxRecMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRec 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRec insert(TbLogisticsPackListBoxRec tbLogisticsPackListBoxRec){
        tbLogisticsPackListBoxRecMapper.insert(tbLogisticsPackListBoxRec);
        return tbLogisticsPackListBoxRec;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackListBoxRec update(TbLogisticsPackListBoxRecParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackListBoxRec> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackListBoxRec>(tbLogisticsPackListBoxRecMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsPackListBoxRec::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getComWarehouseType()),TbLogisticsPackListBoxRec::getComWarehouseType, param.getComWarehouseType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsPackListBoxRec::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOwName()),TbLogisticsPackListBoxRec::getOwName, param.getOwName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipTo()),TbLogisticsPackListBoxRec::getShipTo, param.getShipTo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbLogisticsPackListBoxRec::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsPackListBoxRec::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getAmaRecState()),TbLogisticsPackListBoxRec::getAmaRecState, param.getAmaRecState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentRealStatus()),TbLogisticsPackListBoxRec::getShipmentRealStatus, param.getShipmentRealStatus());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackListCode()),TbLogisticsPackListBoxRec::getPackListCode, param.getPackListCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackListBoxRec::getSysId, param.getSysId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getSysId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal sysId){
        int total = tbLogisticsPackListBoxRecMapper.deleteById(sysId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysIdList) {
        int delCount = tbLogisticsPackListBoxRecMapper.deleteBatchIds(sysIdList);
        if (sysIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     /**
      * 根据PackCode出货清单号 删除 出货清单和亚马逊货件关系映射
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     @DataSource(name = "logistics")
     @Override
     public boolean deleteByPackCode(String packCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRec> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRec::getPackCode, packCode);
         int delCount = tbLogisticsPackListBoxRecMapper.delete(wrapper);
         if (delCount>0) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 根据packListCode, packCode 查询出货清单和亚马逊货件关系映射
      * @param packListCode
      * @param packCode
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public TbLogisticsPackListBoxRec queryByPackListCodeAndPackCode(String packListCode, String packCode) {

         LambdaQueryWrapper<TbLogisticsPackListBoxRec> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRec::getPackCode, packCode);
         wrapper.eq(TbLogisticsPackListBoxRec::getPackListCode, packListCode);
         List<TbLogisticsPackListBoxRec> listBoxRecs = tbLogisticsPackListBoxRecMapper.selectList(wrapper);
         if (ObjectUtil.isNotEmpty(listBoxRecs)) {
             return listBoxRecs.get(0);
         }
         return null;
     }

     /**
      *  根据packListCode 查询出货清单和亚马逊货件关系映射
      * @param packListCode
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public TbLogisticsPackListBoxRec queryByPackListCode(String packListCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRec> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRec::getPackListCode, packListCode);
         List<TbLogisticsPackListBoxRec> listBoxRecs = tbLogisticsPackListBoxRecMapper.selectList(wrapper);
         if (ObjectUtil.isNotEmpty(listBoxRecs)) {
             return listBoxRecs.get(0);
         }
         return null;
     }

     /**
      * 根据packListCode 删除出货清单和亚马逊货件关系映射
      * @param packListCode
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int deleteByPackListCode(String packListCode) {
         LambdaQueryWrapper<TbLogisticsPackListBoxRec> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsPackListBoxRec::getPackListCode, packListCode);
        return tbLogisticsPackListBoxRecMapper.delete(wrapper);

     }

    @DataSource(name = "logistics")
     @Override
     public ResponseData amazRecStateList() {
         QueryWrapper<TbLogisticsPackListBoxRec> wrapper = new QueryWrapper<>();
         wrapper.select("DISTINCT ama_rec_state");
         wrapper.isNotNull("ama_rec_state");
         // 查询单个字段
         List<String> busLspNumList = this.listObjs(wrapper, Object::toString);
         if (ObjectUtil.isNotEmpty(busLspNumList)) {
             busLspNumList= busLspNumList.stream().distinct().collect(Collectors.toList());
         }
         return   ResponseData.success(busLspNumList);

     }
 }