package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopTaxnCatManage;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopTaxnCatManageMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopTaxnCatManageParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopTaxnCatManageResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopTaxnCatManageService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComTaxNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资源-税号类别管理;(Tb_Com_Shop_Taxn_Cat_Manage)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComShopTaxnCatManageServiceImpl extends ServiceImpl<TbComShopTaxnCatManageMapper, TbComShopTaxnCatManage>  implements TbComShopTaxnCatManageService{
    @Resource
    private TbComShopTaxnCatManageMapper tbComShopTaxnCatManageMapper;
    @Resource
    private TbComTaxNumService tbComTaxNumService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopTaxnCatManage queryById(BigDecimal sysId){
        return tbComShopTaxnCatManageMapper.selectById(sysId);
    }
    
    /**
     * 分页查询
     *
     * @param tbComShopTaxnCatManage 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopTaxnCatManageResult> paginQuery(TbComShopTaxnCatManageParam tbComShopTaxnCatManage, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopTaxnCatManage> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getShopName())){
            queryWrapper.eq(TbComShopTaxnCatManage::getShopName, tbComShopTaxnCatManage.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getSysOperatePerName())){
            queryWrapper.eq(TbComShopTaxnCatManage::getSysOperatePerName, tbComShopTaxnCatManage.getSysOperatePerName());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getSysOperatePerCode())){
            queryWrapper.eq(TbComShopTaxnCatManage::getSysOperatePerCode, tbComShopTaxnCatManage.getSysOperatePerCode());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getTaxnType())){
            queryWrapper.eq(TbComShopTaxnCatManage::getTaxnType, tbComShopTaxnCatManage.getTaxnType());
        }
        //2. 执行分页查询
        Page<TbComShopTaxnCatManageResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopTaxnCatManageResult> selectResult = tbComShopTaxnCatManageMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopTaxnCatManage insert(TbComShopTaxnCatManage tbComShopTaxnCatManage){
        LoginUser loginUser = LoginContext.me().getLoginUser();
        tbComShopTaxnCatManage.setSysId(BigDecimal.valueOf(IdWorker.getId()));
        tbComShopTaxnCatManage.setSysOperateDate(new Date());
        tbComShopTaxnCatManage.setSysOperatePerName(loginUser.getName());
        tbComShopTaxnCatManage.setSysOperatePerCode(loginUser.getAccount());
        tbComShopTaxnCatManageMapper.insert(tbComShopTaxnCatManage);

//       boolean r= tbComTaxNumService.updateTaxNum(tbComShopTaxnCatManage);
        return tbComShopTaxnCatManage;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopTaxnCatManage update(TbComShopTaxnCatManage tbComShopTaxnCatManage){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopTaxnCatManage> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopTaxnCatManageMapper);
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getShopName())){
            chainWrapper.set(TbComShopTaxnCatManage::getShopName, tbComShopTaxnCatManage.getShopName());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getSysOperatePerName())){
            chainWrapper.set(TbComShopTaxnCatManage::getSysOperatePerName, tbComShopTaxnCatManage.getSysOperatePerName());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getSysOperatePerCode())){
            chainWrapper.set(TbComShopTaxnCatManage::getSysOperatePerCode, tbComShopTaxnCatManage.getSysOperatePerCode());
        }
        if(StrUtil.isNotBlank(tbComShopTaxnCatManage.getTaxnType())){
            chainWrapper.set(TbComShopTaxnCatManage::getTaxnType, tbComShopTaxnCatManage.getTaxnType());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopTaxnCatManage::getSysId, tbComShopTaxnCatManage.getSysId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComShopTaxnCatManage.getSysId());
        }else{
            return tbComShopTaxnCatManage;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal sysId){
        int total = tbComShopTaxnCatManageMapper.deleteById(sysId);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysIdList){
         int delCount = tbComShopTaxnCatManageMapper.deleteBatchIds(sysIdList);
         if (sysIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
    /**
     * 根据店铺名查询税号变更记录
     * @param shopName
     * @return
     */
    @DataSource(name = "stocking")
    @Override
    public List<TbComShopTaxnCatManage> taxnChangeRecord(String shopName) {
        LambdaQueryWrapper<TbComShopTaxnCatManage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbComShopTaxnCatManage::getShopName, shopName);
        List<TbComShopTaxnCatManage> catManageList = tbComShopTaxnCatManageMapper.selectList(queryWrapper);
        return catManageList;
    }
}