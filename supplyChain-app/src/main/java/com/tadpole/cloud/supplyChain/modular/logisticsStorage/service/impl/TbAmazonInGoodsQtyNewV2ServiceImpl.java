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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbAmazonInGoodsQtyNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbAmazonInGoodsQtyNewV2Mapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbAmazonInGoodsQtyNewV2Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbAmazonInGoodsQtyNewV2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbAmazonInGoodsQtyNewV2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * Amazon在途库存报表;(Tb_Amazon_In_Goods_Qty_New_V2)表服务实现类
 * @author : LSY
 * @date : 2024-3-18
 */
@Service
@Transactional
@Slf4j
public class TbAmazonInGoodsQtyNewV2ServiceImpl  extends ServiceImpl<TbAmazonInGoodsQtyNewV2Mapper, TbAmazonInGoodsQtyNewV2> implements TbAmazonInGoodsQtyNewV2Service {
    @Resource
    private TbAmazonInGoodsQtyNewV2Mapper tbAmazonInGoodsQtyNewV2Mapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbAmazonInGoodsQtyNewV2 queryById(BigDecimal id){
        return tbAmazonInGoodsQtyNewV2Mapper.selectById(id);
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
    public Page<TbAmazonInGoodsQtyNewV2Result> paginQuery(TbAmazonInGoodsQtyNewV2Param param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbAmazonInGoodsQtyNewV2> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSku()),TbAmazonInGoodsQtyNewV2::getSku, param.getSku());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMatCode()),TbAmazonInGoodsQtyNewV2::getMatCode, param.getMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbAmazonInGoodsQtyNewV2::getShopNameSimple, param.getShopNameSimple());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbAmazonInGoodsQtyNewV2::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbAmazonInGoodsQtyNewV2::getShipmentId, param.getShipmentId());
        //2. 执行分页查询
        Page<TbAmazonInGoodsQtyNewV2Result> pagin = new Page<>(current , size , true);
        IPage<TbAmazonInGoodsQtyNewV2Result> selectResult = tbAmazonInGoodsQtyNewV2Mapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbAmazonInGoodsQtyNewV2 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbAmazonInGoodsQtyNewV2 insert(TbAmazonInGoodsQtyNewV2 tbAmazonInGoodsQtyNewV2){
        tbAmazonInGoodsQtyNewV2Mapper.insert(tbAmazonInGoodsQtyNewV2);
        return tbAmazonInGoodsQtyNewV2;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbAmazonInGoodsQtyNewV2 update(TbAmazonInGoodsQtyNewV2Param param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbAmazonInGoodsQtyNewV2> wrapper = new LambdaUpdateChainWrapper<TbAmazonInGoodsQtyNewV2>(tbAmazonInGoodsQtyNewV2Mapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSku()),TbAmazonInGoodsQtyNewV2::getSku, param.getSku());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMatCode()),TbAmazonInGoodsQtyNewV2::getMatCode, param.getMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()),TbAmazonInGoodsQtyNewV2::getShopNameSimple, param.getShopNameSimple());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbAmazonInGoodsQtyNewV2::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbAmazonInGoodsQtyNewV2::getShipmentId, param.getShipmentId());
        //2. 设置主键，并更新
        wrapper.eq(TbAmazonInGoodsQtyNewV2::getId, param.getId());
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
        int total = tbAmazonInGoodsQtyNewV2Mapper.deleteById(id);
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
        int delCount = tbAmazonInGoodsQtyNewV2Mapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}