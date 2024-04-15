package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCorporateCaCertificate;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityCorporateCaCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCorporateCaCertificateResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备法人CA证书;(Tb_Com_Entity_Corporate_CA_Certificate)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityCorporateCaCertificateService extends IService<TbComEntityCorporateCaCertificate> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityCorporateCaCertificate queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityCorporateCaCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityCorporateCaCertificateResult> paginQuery(TbComEntityCorporateCaCertificateParam tbComEntityCorporateCaCertificate, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityCorporateCaCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityCorporateCaCertificate insert(TbComEntityCorporateCaCertificate tbComEntityCorporateCaCertificate);
    /** 
     * 更新数据
     *
     * @param tbComEntityCorporateCaCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityCorporateCaCertificate update(TbComEntityCorporateCaCertificate tbComEntityCorporateCaCertificate);
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
}