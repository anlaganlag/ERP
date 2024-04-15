package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsTransferRecord;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.ExportDirectTransfersOrderResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsTransferRecordResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsTransferRecordParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 物流调拨记录;(tb_logistics_transfer_record)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsTransferRecordService extends IService<TbLogisticsTransferRecord> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbLogisticsTransferRecord queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsTransferRecord 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsTransferRecordResult> paginQuery(TbLogisticsTransferRecordParam tbLogisticsTransferRecord, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsTransferRecord 实例对象
     * @return 实例对象
     */
    TbLogisticsTransferRecord insert(TbLogisticsTransferRecord tbLogisticsTransferRecord);
    /** 
     * 更新数据
     *
     * @param tbLogisticsTransferRecord 实例对象
     * @return 实例对象
     */
    TbLogisticsTransferRecord update(TbLogisticsTransferRecordParam tbLogisticsTransferRecord);
    /** 
     * 通过主键删除数据
     *
     * @param sysId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal sysId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param sysIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> sysIdList);

     /**
      * 查询转仓记录
      * @param packCode
      * @return
      */
     List<TbLogisticsTransferRecord> getByPackCode(String packCode);


     /**
      * 导出直接调拨单
      * @param packCodeList
      * @return
      */
     List<ExportDirectTransfersOrderResult> export(List<String> packCodeList);

     /**
      * 更新处理状态
      * @param packCodeList
      * @return
      */
     void updateStatus(List<String> packCodeList);

 }