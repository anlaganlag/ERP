package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopApplyBankCollectTask;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopApplyBankCollectTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopApplyBankCollectTaskResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-店铺申请银行收款任务;(Tb_Com_Shop_Apply_Bank_Collect_Task)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComShopApplyBankCollectTaskService extends IService<TbComShopApplyBankCollectTask> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysApplyDetBctId 主键
     * @return 实例对象
     */
    TbComShopApplyBankCollectTask queryById(BigDecimal sysApplyDetBctId);
    
    /**
     * 分页查询
     *
     * @param tbComShopApplyBankCollectTask 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComShopApplyBankCollectTaskResult> paginQuery(TbComShopApplyBankCollectTaskParam tbComShopApplyBankCollectTask, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComShopApplyBankCollectTask 实例对象
     * @return 实例对象
     */
    TbComShopApplyBankCollectTask insert(TbComShopApplyBankCollectTask tbComShopApplyBankCollectTask);
    /** 
     * 更新数据
     *
     * @param tbComShopApplyBankCollectTask 实例对象
     * @return 实例对象
     */
    TbComShopApplyBankCollectTask update(TbComShopApplyBankCollectTaskParam bankCollectTaskParam);
    /** 
     * 通过主键删除数据
     *
     * @param sysApplyDetBctId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysApplyDetBctId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param sysApplyDetBctIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> sysApplyDetBctIdList);

     /**
      * 根据申请明细ID 查找银行任务信息
      * @param sysApplyDetId
      * @return
      */
     TbComShopApplyBankCollectTask getBankCollectTaskByDetId(BigDecimal sysApplyDetId);
 }