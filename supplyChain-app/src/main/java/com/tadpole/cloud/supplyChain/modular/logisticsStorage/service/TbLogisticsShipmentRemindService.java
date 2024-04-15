package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentRemind;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentRemindResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentRemindParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * TbLogisticsShipmentRemind--暂时不需要;(tb_logistics_shipment_remind)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsShipmentRemindService extends IService<TbLogisticsShipmentRemind> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysId 主键
     * @return 实例对象
     */
    TbLogisticsShipmentRemind queryById(BigDecimal sysId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsShipmentRemind 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsShipmentRemindResult> paginQuery(TbLogisticsShipmentRemindParam tbLogisticsShipmentRemind, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentRemind 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentRemind insert(TbLogisticsShipmentRemind tbLogisticsShipmentRemind);
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipmentRemind 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentRemind update(TbLogisticsShipmentRemindParam tbLogisticsShipmentRemind);
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
}