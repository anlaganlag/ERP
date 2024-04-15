package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxAuditDet;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditDetParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxAuditDetResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-税号查账记录明细;(Tb_Com_Tax_Audit_Det)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComTaxAuditDetService extends IService<TbComTaxAuditDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param caseNoDetId 主键
     * @return 实例对象
     */
    TbComTaxAuditDet queryById(BigDecimal caseNoDetId);
    
    /**
     * 分页查询
     *
     * @param tbComTaxAuditDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComTaxAuditDetResult> paginQuery(TbComTaxAuditDetParam tbComTaxAuditDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComTaxAuditDet 实例对象
     * @return 实例对象
     */
    TbComTaxAuditDet insert(TbComTaxAuditDet tbComTaxAuditDet);
    /** 
     * 更新数据
     *
     * @param tbComTaxAuditDet 实例对象
     * @return 实例对象
     */
    TbComTaxAuditDet update(TbComTaxAuditDet tbComTaxAuditDet);
    /** 
     * 通过主键删除数据
     *
     * @param caseNoDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal caseNoDetId);
    
    /**
     * 通过主键删除数据--批量删除
     * @param caseNoDetIdList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> caseNoDetIdList);
}