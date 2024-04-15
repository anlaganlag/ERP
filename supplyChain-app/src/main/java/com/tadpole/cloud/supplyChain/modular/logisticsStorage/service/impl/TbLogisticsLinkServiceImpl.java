package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

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
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsLink;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsLinkMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsLinkParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsLinkResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 物流信息查询;(tb_logistics_link)表服务实现类
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsLinkServiceImpl  extends ServiceImpl<TbLogisticsLinkMapper, TbLogisticsLink> implements TbLogisticsLinkService{
    @Resource
    private TbLogisticsLinkMapper tbLogisticsLinkMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsLink queryById(BigDecimal id){
        return tbLogisticsLinkMapper.selectById(id);
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
    public Page<TbLogisticsLinkResult> paginQuery(TbLogisticsLinkParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbLogisticsLink> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsLink::getSysPerName, param.getSysPerName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsLink::getLogTraMode1, param.getLogTraMode1());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogListLinkTemp()),TbLogisticsLink::getLogListLinkTemp, param.getLogListLinkTemp());
        //2. 执行分页查询
        Page<TbLogisticsLinkResult> pagin = new Page<>(current , size , true);
        IPage<TbLogisticsLinkResult> selectResult = tbLogisticsLinkMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbLogisticsLink 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsLink insert(TbLogisticsLink tbLogisticsLink){
        tbLogisticsLinkMapper.insert(tbLogisticsLink);
        return tbLogisticsLink;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsLink update(TbLogisticsLinkParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsLink> wrapper = new LambdaUpdateChainWrapper<TbLogisticsLink>(tbLogisticsLinkMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getSysPerName()),TbLogisticsLink::getSysPerName, param.getSysPerName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogTraMode1()),TbLogisticsLink::getLogTraMode1, param.getLogTraMode1());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogListLinkTemp()),TbLogisticsLink::getLogListLinkTemp, param.getLogListLinkTemp());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsLink::getId, param.getId());
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
        int total = tbLogisticsLinkMapper.deleteById(id);
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
        int delCount = tbLogisticsLinkMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "logistics")
     @Override
     public List<TbLogisticsLink> findByLogTraMode1(String logTraMode1) {
         return tbLogisticsLinkMapper.findByLogTraMode1(logTraMode1);
     }

     @DataSource(name = "logistics")
     @Override
     public ResponseData insertOrUpdate(TbLogisticsLinkParam linkParam) {
         LoginUser loginUser = LoginContext.me().getLoginUser();

         String logTraMode1  = linkParam.getLogTraMode1();
         if (ObjectUtil.isEmpty(logTraMode1 )) {
             return ResponseData.error("logTraMode1不能为空");
         }
         List<TbLogisticsLink> tbLogisticsLinks= this.findByLogTraMode1(logTraMode1);
         if (ObjectUtil.isNotEmpty(tbLogisticsLinks)) {
             for (TbLogisticsLink link : tbLogisticsLinks) {
                 link.setLogListLinkTemp(linkParam.getLogListLinkTemp());
                 link.setSysUpdDatetime(new Date());
                 link.setSysPerName(loginUser.getName());
             }
         } else {
             TbLogisticsLink link = new TbLogisticsLink();
             link.setLogTraMode1(logTraMode1);
             link.setLogListLinkTemp(linkParam.getLogListLinkTemp());
             link.setSysAddDate(new Date());
             link.setSysPerName(loginUser.getName());
             tbLogisticsLinks.add(link);
         }

         if (this.saveOrUpdateBatch(tbLogisticsLinks)) {
             return ResponseData.success();
         }
         return ResponseData.error("更新失败");
     }
 }