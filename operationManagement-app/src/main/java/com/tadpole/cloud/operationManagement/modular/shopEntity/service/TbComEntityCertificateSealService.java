package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCertificateSeal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCertificateSealParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.CertificateSealCountResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCertificateSealResult;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 资源-公司实体证件印章;(Tb_Com_Entity_Certificate_Seal)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityCertificateSealService extends IService<TbComEntityCertificateSeal> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityCertificateSeal queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityCertificateSeal 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityCertificateSealResult> paginQuery(TbComEntityCertificateSealParam tbComEntityCertificateSeal, long current, long size);


     Page<TbComEntityCertificateSealResult> queryPage(TbComEntityCertificateSealParam tbComEntityCertificateSeal, long current, long size);

     /**
     * 新增数据
     *
     * @param tbComEntityCertificateSeal 实例对象
     * @return 实例对象
     */
    TbComEntityCertificateSeal insert(TbComEntityCertificateSeal tbComEntityCertificateSeal);
    /** 
     * 更新数据
     *
     * @param tbComEntityCertificateSeal 实例对象
     * @return 实例对象
     */
    TbComEntityCertificateSeal update(TbComEntityCertificateSeal tbComEntityCertificateSeal);
    /** 
     * 通过主键删除数据
     *
     * @param pkCode 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkCode);
    
    /**
     * 通过主键删除数据--批量删除
     * @param pkCodeList
     * @return
     */
     boolean deleteBatchIds(List<BigDecimal> pkCodeList);

     /**
      * 按查询条件统计
      * @param param 查询条件
      * @return
      */
     List<CertificateSealCountResult> certificateSealCount(TbComEntityCertificateSealParam param);
 }