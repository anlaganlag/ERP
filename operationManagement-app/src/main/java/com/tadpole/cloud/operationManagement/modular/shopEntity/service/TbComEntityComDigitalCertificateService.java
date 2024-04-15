package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityComDigitalCertificate;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityComDigitalCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityComDigitalCertificateResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备公司数字证书;(Tb_Com_Entity_Com_Digital_Certificate)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityComDigitalCertificateService extends IService<TbComEntityComDigitalCertificate> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityComDigitalCertificate queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityComDigitalCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityComDigitalCertificateResult> paginQuery(TbComEntityComDigitalCertificateParam tbComEntityComDigitalCertificate, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityComDigitalCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityComDigitalCertificate insert(TbComEntityComDigitalCertificate tbComEntityComDigitalCertificate);
    /** 
     * 更新数据
     *
     * @param tbComEntityComDigitalCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityComDigitalCertificate update(TbComEntityComDigitalCertificate tbComEntityComDigitalCertificate);
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