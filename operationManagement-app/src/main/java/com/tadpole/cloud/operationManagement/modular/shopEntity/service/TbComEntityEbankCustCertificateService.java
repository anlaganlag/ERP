package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityEbankCustCertificate;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityEbankCustCertificateParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityEbankCustCertificateResult;
import java.util.List;

 /**
 * 资源-公司实体银行设备电子银行客户证书;(Tb_Com_Entity_Ebank_Cust_Certificate)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityEbankCustCertificateService extends IService<TbComEntityEbankCustCertificate> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    TbComEntityEbankCustCertificate queryById(BigDecimal pkCode);
    
    /**
     * 分页查询
     *
     * @param tbComEntityEbankCustCertificate 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityEbankCustCertificateResult> paginQuery(TbComEntityEbankCustCertificateParam tbComEntityEbankCustCertificate, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbComEntityEbankCustCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityEbankCustCertificate insert(TbComEntityEbankCustCertificate tbComEntityEbankCustCertificate);
    /** 
     * 更新数据
     *
     * @param tbComEntityEbankCustCertificate 实例对象
     * @return 实例对象
     */
    TbComEntityEbankCustCertificate update(TbComEntityEbankCustCertificate tbComEntityEbankCustCertificate);
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