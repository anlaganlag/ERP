package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopTaxnCatManage;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopTaxnCatManageParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopTaxnCatManageResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-税号类别管理;(Tb_Com_Shop_Taxn_Cat_Manage)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopTaxnCatManageService extends IService<TbComShopTaxnCatManage> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbComShopTaxnCatManage queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbComShopTaxnCatManage 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopTaxnCatManageResult> paginQuery(TbComShopTaxnCatManageParam tbComShopTaxnCatManage, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    TbComShopTaxnCatManage insert(TbComShopTaxnCatManage tbComShopTaxnCatManage);
    /** 
     * 更新数据
     *
     * @param tbComShopTaxnCatManage 实例对象
     * @return 实例对象
     */
    TbComShopTaxnCatManage update(TbComShopTaxnCatManage tbComShopTaxnCatManage);
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
      * 根据店铺名查询税号变更记录
      * @param shopName
      * @return
      */
     List<TbComShopTaxnCatManage> taxnChangeRecord(String shopName);
 }