package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopShroffAccount;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopShroffAccountParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopShroffAccountResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-信用卡账号管理;(Tb_Com_Shop_Shroff_Account)--服务接口
 * @author : LSY
 * @date : 2023-8-3
 */
public interface TbComShopShroffAccountService extends IService<TbComShopShroffAccount> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbComShopShroffAccount queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbComShopShroffAccount 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopShroffAccountResult> paginQuery(TbComShopShroffAccountParam tbComShopShroffAccount, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopShroffAccount 实例对象
     * @return 实例对象
     */
    TbComShopShroffAccount insert(TbComShopShroffAccount tbComShopShroffAccount);
    /** 
     * 更新数据
     *
     * @param tbComShopShroffAccount 实例对象
     * @return 实例对象
     */
    TbComShopShroffAccount update(TbComShopShroffAccount tbComShopShroffAccount);
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysIdList);

     /**
      * 批量变更店铺信用卡号
      * @param shroffAccountList
      * @return
      */
     ResponseData addBatch(List<TbComShopShroffAccount> shroffAccountList);

     /**
      * 根据店铺名称查询信用卡变更记录
      * @param shopName
      * @return
      */
     List<TbComShopShroffAccount> shroffAccountChangeRecord(String shopName);
 }