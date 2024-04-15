package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipToRec;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsShipToRecMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipToRecParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipToRecResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsShipToRecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 平台货件接收地址;(tb_logistics_ship_to_rec)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsShipToRecServiceImpl  extends ServiceImpl<TbLogisticsShipToRecMapper, TbLogisticsShipToRec> implements TbLogisticsShipToRecService {
    @Resource
    private TbLogisticsShipToRecMapper tbLogisticsShipToRecMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lfrId 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipToRec queryById(BigDecimal lfrId){
        return tbLogisticsShipToRecMapper.selectById(lfrId);
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
    public Page<TbLogisticsShipToRecResult> paginQuery(TbLogisticsShipToRecParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsShipToRec> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsShipToRec::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsShipToRec::getElePlatformName, param.getElePlatformName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsShipToRec::getCountryCode, param.getCountryCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCity()),TbLogisticsShipToRec::getCity, param.getCity());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getState()),TbLogisticsShipToRec::getState, param.getState());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecAddress1()),TbLogisticsShipToRec::getLogRecAddress1, param.getLogRecAddress1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecAddress2()),TbLogisticsShipToRec::getLogRecAddress2, param.getLogRecAddress2());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecAddress3()),TbLogisticsShipToRec::getLogRecAddress3, param.getLogRecAddress3());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecZip()),TbLogisticsShipToRec::getLogRecZip, param.getLogRecZip());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecPerson()),TbLogisticsShipToRec::getLogRecPerson, param.getLogRecPerson());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecCompany()),TbLogisticsShipToRec::getLogRecCompany, param.getLogRecCompany());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecPersonTel()),TbLogisticsShipToRec::getLogRecPersonTel, param.getLogRecPersonTel());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogRecHouseName()),TbLogisticsShipToRec::getLogRecHouseName, param.getLogRecHouseName());
        //2. 执行分页查询
        Page<TbLogisticsShipToRecResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsShipToRecResult> selectResult = tbLogisticsShipToRecMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipToRec 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipToRec insert(TbLogisticsShipToRec tbLogisticsShipToRec){
        String name = LoginContext.me().getLoginUser().getName();
        tbLogisticsShipToRec.setSysPerName(name);
        tbLogisticsShipToRecMapper.insert(tbLogisticsShipToRec);
        return tbLogisticsShipToRec;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsShipToRec update(TbLogisticsShipToRecParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsShipToRec> wrapper = new LambdaUpdateChainWrapper<TbLogisticsShipToRec>(tbLogisticsShipToRecMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsShipToRec::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getElePlatformName()),TbLogisticsShipToRec::getElePlatformName, param.getElePlatformName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()),TbLogisticsShipToRec::getCountryCode, param.getCountryCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCity()),TbLogisticsShipToRec::getCity, param.getCity());
         wrapper.set(ObjectUtil.isNotEmpty(param.getState()),TbLogisticsShipToRec::getState, param.getState());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress1()),TbLogisticsShipToRec::getLogRecAddress1, param.getLogRecAddress1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress2()),TbLogisticsShipToRec::getLogRecAddress2, param.getLogRecAddress2());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecAddress3()),TbLogisticsShipToRec::getLogRecAddress3, param.getLogRecAddress3());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecZip()),TbLogisticsShipToRec::getLogRecZip, param.getLogRecZip());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecPerson()),TbLogisticsShipToRec::getLogRecPerson, param.getLogRecPerson());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecCompany()),TbLogisticsShipToRec::getLogRecCompany, param.getLogRecCompany());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecPersonTel()),TbLogisticsShipToRec::getLogRecPersonTel, param.getLogRecPersonTel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogRecHouseName()),TbLogisticsShipToRec::getLogRecHouseName, param.getLogRecHouseName());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsShipToRec::getLfrId, param.getLfrId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(TbLogisticsShipToRec::getSysUpdDatetime, loginUser.getName());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getLfrId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param lfrId 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal lfrId){
        int total = tbLogisticsShipToRecMapper.deleteById(lfrId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param lfrIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> lfrIdList) {
        int delCount = tbLogisticsShipToRecMapper.deleteBatchIds(lfrIdList);
        if (lfrIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "logistics")
     @Override
     public List<String> logRecHouseNameList() {
         return tbLogisticsShipToRecMapper.logRecHouseNameList();
     }

    @Override
    public ResponseData batchAdd(List<TbLogisticsShipToRec> tbLogisticsShipToRecList) {
        if(CollectionUtil.isEmpty(tbLogisticsShipToRecList)){
            return ResponseData.error("导入数据为空！");
        }
        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();
        for (TbLogisticsShipToRec logisticsShipToRec : tbLogisticsShipToRecList) {
            logisticsShipToRec.setSysPerName(name);
            LambdaQueryWrapper<TbLogisticsShipToRec> qw = new LambdaQueryWrapper<>();
            qw.eq(TbLogisticsShipToRec :: getElePlatformName, logisticsShipToRec.getElePlatformName())
                    .eq(TbLogisticsShipToRec :: getCountryCode, logisticsShipToRec.getCountryCode())
                    .eq(TbLogisticsShipToRec :: getLogRecHouseName, logisticsShipToRec.getLogRecHouseName());
            TbLogisticsShipToRec result = tbLogisticsShipToRecMapper.selectOne(qw);
            if(result != null){
                logisticsShipToRec.setLfrId(result.getLfrId());
                logisticsShipToRec.setSysUpdDatetime(nowDate);
            }
        }
        this.saveOrUpdateBatch(tbLogisticsShipToRecList);
        return ResponseData.success();
    }
}