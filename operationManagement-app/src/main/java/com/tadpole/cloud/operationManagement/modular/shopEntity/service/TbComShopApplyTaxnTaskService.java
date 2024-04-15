package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyTaxnTask;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyTaxnTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyTaxnTaskResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺申请税务任务;(Tb_Com_Shop_Apply_Taxn_Task)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopApplyTaxnTaskService extends IService<TbComShopApplyTaxnTask> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param taxnId 主键
     * @return 实例对象
     */
    TbComShopApplyTaxnTask queryById(BigDecimal taxnId);
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyTaxnTask 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopApplyTaxnTaskResult> paginQuery(TbComShopApplyTaxnTaskParam tbComShopApplyTaxnTask, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopApplyTaxnTask 实例对象
     * @return 实例对象
     */
    TbComShopApplyTaxnTask insert(TbComShopApplyTaxnTask tbComShopApplyTaxnTask);
    /** 
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    TbComShopApplyTaxnTask update(TbComShopApplyTaxnTaskParam entityParam);
    /** 
     * 通过主键删除数据
     *
     * @param taxnId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal taxnId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param taxnIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> taxnIdList);

     /**
      * 根据店铺申请明细ID查找银行税号任务信息
      * @param sysApplyDetId
      * @return
      */
     TbComShopApplyTaxnTask getTaxnTaskByDetId(BigDecimal sysApplyDetId);
 }