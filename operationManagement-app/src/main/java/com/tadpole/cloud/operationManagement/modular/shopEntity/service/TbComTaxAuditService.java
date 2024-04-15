package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxAudit;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxAuditResult;
import java.util.List;

 /**
 * 资源-税号查账记录;(Tb_Com_Tax_Audit)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComTaxAuditService extends IService<TbComTaxAudit> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param caseNo 主键
     * @return 实例对象
     */
    TbComTaxAudit queryById(String caseNo);
    
    /**
     * 分页查询
     *
     * @param tbComTaxAudit 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComTaxAuditResult> paginQuery(TbComTaxAuditParam tbComTaxAudit, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComTaxAudit 实例对象
     * @return 实例对象
     */
    TbComTaxAudit insert(TbComTaxAudit tbComTaxAudit);
    /** 
     * 更新数据
     *
     * @param tbComTaxAudit 实例对象
     * @return 实例对象
     */
    TbComTaxAudit update(TbComTaxAudit tbComTaxAudit);
    /** 
     * 通过主键删除数据
     *
     * @param caseNo 主键
     * @return 是否成功
     */
    boolean deleteById(String caseNo);
    
    /**
     * 通过主键删除数据--批量删除
     * @param caseNoList
     * @return
     */
     boolean deleteBatchIds(List<String> caseNoList);
}