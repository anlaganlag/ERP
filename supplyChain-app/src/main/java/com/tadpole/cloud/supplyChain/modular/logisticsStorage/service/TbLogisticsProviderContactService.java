package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsProviderContact;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsProviderContactResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsProviderContactParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流供应商联系信息;(tb_logistics_provider_contact)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsProviderContactService extends IService<TbLogisticsProviderContact> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param lpContactId 主键
     * @return 实例对象
     */
    TbLogisticsProviderContact queryById(BigDecimal lpContactId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsProviderContact 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsProviderContactResult> paginQuery(TbLogisticsProviderContactParam tbLogisticsProviderContact, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsProviderContact 实例对象
     * @return 实例对象
     */
    TbLogisticsProviderContact insert(TbLogisticsProviderContact tbLogisticsProviderContact);
    /** 
     * 更新数据
     *
     * @param tbLogisticsProviderContact 实例对象
     * @return 实例对象
     */
    TbLogisticsProviderContact update(TbLogisticsProviderContactParam tbLogisticsProviderContact);
    /** 
     * 通过主键删除数据
     *
     * @param lpContactId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal lpContactId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param lpContactIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> lpContactIdList);

     List<TbLogisticsProviderContact> queryByLpCode(String lpCode);


     @DataSource(name = "k3cloud")
         //    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     List<TbLogisticsProvider> getLogisticsProvider();

     @DataSource(name = "k3cloud")
         //    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     List<TbLogisticsProviderContact> getLogisticsProviderContact();
 }