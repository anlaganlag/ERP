package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopShroffAccountMapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopShroffAccountParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopShroffAccountResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopShroffAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 /**
 * 资源-信用卡账号管理;(Tb_Com_Shop_Shroff_Account)--服务实现类
 * @author : LSY
 * @create : 2023-8-3
 */
@Slf4j
@Service
@Transactional
public class TbComShopShroffAccountServiceImpl extends ServiceImpl<TbComShopShroffAccountMapper, TbComShopShroffAccount>  implements TbComShopShroffAccountService{
    @Resource
    private TbComShopShroffAccountMapper tbComShopShroffAccountMapper;
    @Resource
    private TbComShopService tbComShopService;

    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopShroffAccount queryById(BigDecimal sysId){
        return tbComShopShroffAccountMapper.selectById(sysId);
    }
    
    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopShroffAccountResult> paginQuery(TbComShopShroffAccountParam queryParam, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopShroffAccount> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopName()),TbComShopShroffAccount::getShopName, queryParam.getShopName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSysOperatePerName()),TbComShopShroffAccount::getSysOperatePerName, queryParam.getSysOperatePerName());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSysOperatePerCode()),TbComShopShroffAccount::getSysOperatePerCode, queryParam.getSysOperatePerCode());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopAccountNo()),TbComShopShroffAccount::getShopAccountNo, queryParam.getShopAccountNo());
           queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopBankName()),TbComShopShroffAccount::getShopBankName, queryParam.getShopBankName());
        
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSysId()),TbComShopShroffAccount::getSysId, queryParam.getSysId());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSysOperateDate()),TbComShopShroffAccount::getSysOperateDate, queryParam.getSysOperateDate());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShopEffeRangeStart()),TbComShopShroffAccount::getShopEffeRangeStart, queryParam.getShopEffeRangeStart());
           queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getShopEffeRangeEnd()),TbComShopShroffAccount::getShopEffeRangeEnd, queryParam.getShopEffeRangeEnd());
        //2. 执行分页查询
        Page<TbComShopShroffAccountResult> pagin = new Page<>(current , size , true);
        IPage<TbComShopShroffAccountResult> selectResult = tbComShopShroffAccountMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComShopShroffAccount 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopShroffAccount insert(TbComShopShroffAccount tbComShopShroffAccount){
        tbComShopShroffAccountMapper.insert(tbComShopShroffAccount);
        return tbComShopShroffAccount;
    }
    
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopShroffAccount update(TbComShopShroffAccount entityParam){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopShroffAccount> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopShroffAccountMapper);
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()),TbComShopShroffAccount::getShopName, entityParam.getShopName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysOperatePerName()),TbComShopShroffAccount::getSysOperatePerName, entityParam.getSysOperatePerName());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSysOperatePerCode()),TbComShopShroffAccount::getSysOperatePerCode, entityParam.getSysOperatePerCode());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopAccountNo()),TbComShopShroffAccount::getShopAccountNo, entityParam.getShopAccountNo());
            chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopBankName()),TbComShopShroffAccount::getShopBankName, entityParam.getShopBankName());
        
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSysOperateDate()),TbComShopShroffAccount::getSysOperateDate, entityParam.getSysOperateDate());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeStart()),TbComShopShroffAccount::getShopEffeRangeStart, entityParam.getShopEffeRangeStart());
           chainWrapper.set(ObjectUtil.isNotNull(entityParam.getShopEffeRangeEnd()),TbComShopShroffAccount::getShopEffeRangeEnd, entityParam.getShopEffeRangeEnd());
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopShroffAccount::getSysId, entityParam.getSysId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(entityParam.getSysId());
        }else{
            return entityParam;
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
        int total = tbComShopShroffAccountMapper.deleteById(sysId);
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
         int delCount = tbComShopShroffAccountMapper.deleteBatchIds(sysIdList);
         if (sysIdList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }

     /**
      * 批量变更店铺信用卡号
      * @param shroffAccountList
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public ResponseData addBatch(List<TbComShopShroffAccount> shroffAccountList) {
         LoginUser loginUser = LoginContext.me().getLoginUser();
         shroffAccountList.forEach(sa->{
             sa.setSysOperateDate(new Date());
             sa.setSysOperatePerName(loginUser.getName());
             sa.setSysOperatePerCode(loginUser.getAccount());
             sa.setSysId(BigDecimal.valueOf(IdWorker.getId()));
             tbComShopShroffAccountMapper.insert(sa);
             tbComShopService.updateShroffAccount(sa);
         });
        return ResponseData.success();
     }

     /**
      *根据店铺名称查询信用卡变更记录
      * @param shopName
      * @return
      */
     @DataSource(name = "stocking")
     @Override
     public List<TbComShopShroffAccount> shroffAccountChangeRecord(String shopName) {
         LambdaQueryWrapper<TbComShopShroffAccount> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(TbComShopShroffAccount::getShopName, shopName).orderByAsc(TbComShopShroffAccount::getSysOperateDate);
         List<TbComShopShroffAccount> accountList = tbComShopShroffAccountMapper.selectList(queryWrapper);
         return accountList;
     }
 }