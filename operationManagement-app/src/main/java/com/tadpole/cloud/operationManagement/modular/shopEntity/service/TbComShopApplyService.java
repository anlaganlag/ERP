package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApply;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺申请;(Tb_Com_Shop_Apply)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopApplyService extends IService<TbComShopApply> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyId 主键
     * @return 实例对象
     */
    TbComShopApply queryById(BigDecimal sysApplyId);
    
    /**
     * 分页查询
     *
     * @param tbComShopApply 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopApplyResult> paginQuery(TbComShopApplyParam tbComShopApply, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    TbComShopApply insert(TbComShopApply tbComShopApply);


    TbComShopApply copy(TbComShopApply tbComShopApplyParam);

     /**
     * 更新数据
     *
     * @param tbComShopApply 实例对象
     * @return 实例对象
     */
    TbComShopApplyParam update(TbComShopApplyParam tbComShopApply);
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysApplyId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysApplyIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysApplyIdList);

     /**
      * 分页连表查询
      * @param tbComShopApplyParam
      * @param current
      * @param size
      * @return
      */
     Page<TbComShopApplyResult> joinPaginQuery(TbComShopApplyParam tbComShopApplyParam, long current, long size);
 }