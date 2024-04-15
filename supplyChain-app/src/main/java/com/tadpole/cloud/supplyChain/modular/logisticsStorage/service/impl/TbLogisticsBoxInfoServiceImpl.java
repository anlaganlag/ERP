package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsBoxInfo;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsBoxInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsBoxInfoService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsBoxInfoResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsBoxInfoParam;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
 /**
 * 物流箱信息-长宽高重;(tb_logistics_box_info)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsBoxInfoServiceImpl  extends ServiceImpl<TbLogisticsBoxInfoMapper, TbLogisticsBoxInfo> implements TbLogisticsBoxInfoService{
    @Resource
    private TbLogisticsBoxInfoMapper tbLogisticsBoxInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param packDetBoxNum 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBoxInfo queryById(BigDecimal packDetBoxNum){
        return tbLogisticsBoxInfoMapper.selectById(packDetBoxNum);
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
    public Page<TbLogisticsBoxInfoResult> paginQuery(TbLogisticsBoxInfoParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsBoxInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxType()),TbLogisticsBoxInfo::getPackDetBoxType, param.getPackDetBoxType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxSpeUnit()),TbLogisticsBoxInfo::getPackDetBoxSpeUnit, param.getPackDetBoxSpeUnit());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxVoluUnit()),TbLogisticsBoxInfo::getPackDetBoxVoluUnit, param.getPackDetBoxVoluUnit());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPackDetBoxWeigUnit()),TbLogisticsBoxInfo::getPackDetBoxWeigUnit, param.getPackDetBoxWeigUnit());
        //2. 执行分页查询
        Page<TbLogisticsBoxInfoResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsBoxInfoResult> selectResult = tbLogisticsBoxInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsBoxInfo 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBoxInfo insert(TbLogisticsBoxInfo tbLogisticsBoxInfo){
        tbLogisticsBoxInfoMapper.insert(tbLogisticsBoxInfo);
        return tbLogisticsBoxInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsBoxInfo update(TbLogisticsBoxInfoParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsBoxInfo> wrapper = new LambdaUpdateChainWrapper<TbLogisticsBoxInfo>(tbLogisticsBoxInfoMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxType()),TbLogisticsBoxInfo::getPackDetBoxType, param.getPackDetBoxType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxSpeUnit()),TbLogisticsBoxInfo::getPackDetBoxSpeUnit, param.getPackDetBoxSpeUnit());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxVoluUnit()),TbLogisticsBoxInfo::getPackDetBoxVoluUnit, param.getPackDetBoxVoluUnit());
         wrapper.set(ObjectUtil.isNotEmpty(param.getPackDetBoxWeigUnit()),TbLogisticsBoxInfo::getPackDetBoxWeigUnit, param.getPackDetBoxWeigUnit());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsBoxInfo::getPackDetBoxNum, param.getPackDetBoxNum());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getPackDetBoxNum());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param packDetBoxNum 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal packDetBoxNum){
        int total = tbLogisticsBoxInfoMapper.deleteById(packDetBoxNum);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param packDetBoxNumList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> packDetBoxNumList) {
        int delCount = tbLogisticsBoxInfoMapper.deleteBatchIds(packDetBoxNumList);
        if (packDetBoxNumList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}