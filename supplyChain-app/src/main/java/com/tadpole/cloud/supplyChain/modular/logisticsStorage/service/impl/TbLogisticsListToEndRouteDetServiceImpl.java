package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToEndRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsListToEndRouteDetMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToEndRouteDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsListToEndRouteDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToEndRouteDetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
 /**
 * 物流单尾程信息-明细;(tb_logistics_list_to_end_route_det)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsListToEndRouteDetServiceImpl  extends ServiceImpl<TbLogisticsListToEndRouteDetMapper, TbLogisticsListToEndRouteDet> implements TbLogisticsListToEndRouteDetService{
    @Resource
    private TbLogisticsListToEndRouteDetMapper tbLogisticsListToEndRouteDetMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRouteDet queryById(BigDecimal id){
        return tbLogisticsListToEndRouteDetMapper.selectById(id);
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
    public Page<TbLogisticsListToEndRouteDetResult> paginQuery(TbLogisticsListToEndRouteDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsListToEndRouteDet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsListToEndRouteDet::getLhrCode, param.getLhrCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToEndRouteDet::getLhrOddNumb, param.getLhrOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerOddNumb()),TbLogisticsListToEndRouteDet::getLerOddNumb, param.getLerOddNumb());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsListToEndRouteDet::getPackCode, param.getPackCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsListToEndRouteDet::getPackDetBoxCode, param.getPackDetBoxCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsListToEndRouteDet::getShipmentId, param.getShipmentId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLerdState()),TbLogisticsListToEndRouteDet::getLerdState, param.getLerdState());
        //2. 执行分页查询
        Page<TbLogisticsListToEndRouteDetResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsListToEndRouteDetResult> selectResult = tbLogisticsListToEndRouteDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsListToEndRouteDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRouteDet insert(TbLogisticsListToEndRouteDet tbLogisticsListToEndRouteDet){
        tbLogisticsListToEndRouteDetMapper.insert(tbLogisticsListToEndRouteDet);
        return tbLogisticsListToEndRouteDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsListToEndRouteDet update(TbLogisticsListToEndRouteDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsListToEndRouteDet> wrapper = new LambdaUpdateChainWrapper<TbLogisticsListToEndRouteDet>(tbLogisticsListToEndRouteDetMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrCode()),TbLogisticsListToEndRouteDet::getLhrCode, param.getLhrCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLhrOddNumb()),TbLogisticsListToEndRouteDet::getLhrOddNumb, param.getLhrOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerOddNumb()),TbLogisticsListToEndRouteDet::getLerOddNumb, param.getLerOddNumb());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()),TbLogisticsListToEndRouteDet::getPackCode, param.getPackCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxCode()),TbLogisticsListToEndRouteDet::getPackDetBoxCode, param.getPackDetBoxCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()),TbLogisticsListToEndRouteDet::getShipmentId, param.getShipmentId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLerdState()),TbLogisticsListToEndRouteDet::getLerdState, param.getLerdState());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsListToEndRouteDet::getId, param.getId());
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
        int total = tbLogisticsListToEndRouteDetMapper.deleteById(id);
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
        int delCount = tbLogisticsListToEndRouteDetMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "logistics")
     @Override
     public int delByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb) {
         LambdaQueryWrapper<TbLogisticsListToEndRouteDet> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(TbLogisticsListToEndRouteDet::getLhrCode, lhrCode);
         wrapper.eq(TbLogisticsListToEndRouteDet::getLhrOddNumb, lhrOddNumb);
        return tbLogisticsListToEndRouteDetMapper.delete(wrapper);
     }

     /**
      * 根据批次号和快递单号 更新 物流单尾程信息
      * @param lhrCode
      * @param lhrOddNumb
      * @param state
      * @return
      */
     @DataSource(name = "logistics")
     @Override
     public int upByLhrCodeAndLhrOddNumb(String lhrCode, String lhrOddNumb, String state) {
         LambdaUpdateWrapper<TbLogisticsListToEndRouteDet> wrapper = new LambdaUpdateWrapper<>();
         wrapper.eq(TbLogisticsListToEndRouteDet::getLhrCode, lhrCode);
         wrapper.eq(TbLogisticsListToEndRouteDet::getLhrOddNumb, lhrOddNumb);
         wrapper.set(TbLogisticsListToEndRouteDet::getLerdState, state);
         return tbLogisticsListToEndRouteDetMapper.update(null,wrapper);
     }
 }