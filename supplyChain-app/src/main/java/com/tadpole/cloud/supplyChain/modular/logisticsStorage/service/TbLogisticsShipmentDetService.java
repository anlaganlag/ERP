package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊发货申请记录-明细项;(tb_logistics_shipment_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsShipmentDetService extends IService<TbLogisticsShipmentDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shipDetId 主键
     * @return 实例对象
     */
    TbLogisticsShipmentDet queryById(BigDecimal shipDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsShipmentDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsShipmentDetResult> paginQuery(TbLogisticsShipmentDetParam tbLogisticsShipmentDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentDet 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentDet insert(TbLogisticsShipmentDet tbLogisticsShipmentDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipmentDet 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentDet update(TbLogisticsShipmentDetParam tbLogisticsShipmentDet);
    /** 
     * 通过主键删除数据
     *
     * @param shipDetId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal shipDetId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param shipDetIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> shipDetIdList);

     /**
      * 根据出货清单号查询发货计划明细项List
      * @param packCode
      * @return
      */
     List<TbLogisticsShipmentDet> queryByPackCode(String packCode);

     /**
      * 根据出货清单号 删除 发货计划明细项
      * @param packCode
      * @return
      */
     int deleteByPackCode(String packCode);
 }