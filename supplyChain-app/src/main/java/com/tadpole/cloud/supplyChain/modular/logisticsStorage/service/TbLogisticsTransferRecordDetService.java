package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecordDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsTransferRecordDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流调拨记录明细;(tb_logistics_transfer_record_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsTransferRecordDetService extends IService<TbLogisticsTransferRecordDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDetId 主键
     * @return 实例对象
     */
    TbLogisticsTransferRecordDet queryById(BigDecimal sysDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsTransferRecordDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsTransferRecordDetResult> paginQuery(TbLogisticsTransferRecordDetParam tbLogisticsTransferRecordDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsTransferRecordDet 实例对象
     * @return 实例对象
     */
    TbLogisticsTransferRecordDet insert(TbLogisticsTransferRecordDet tbLogisticsTransferRecordDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsTransferRecordDet 实例对象
     * @return 实例对象
     */
    TbLogisticsTransferRecordDet update(TbLogisticsTransferRecordDetParam tbLogisticsTransferRecordDet);
    /** 
     * 通过主键删除数据
     *
     * @param sysDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysDetId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param sysDetIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> sysDetIdList);

     /**
      * 根据 物流调拨记录ID （SysId）查询调拨明细
      * @param sysId
      * @return
      */
     List<TbLogisticsTransferRecordDet> queryBySysId(Integer sysId);
 }