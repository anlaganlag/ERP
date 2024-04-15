package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊货件-明细-按SKU的汇总;(tb_logistics_pack_list_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListDetService extends IService<TbLogisticsPackListDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param shipDetId 主键
     * @return 实例对象
     */
    TbLogisticsPackListDet queryById(BigDecimal shipDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackListDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackListDetResult> paginQuery(TbLogisticsPackListDetParam tbLogisticsPackListDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListDet 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListDet insert(TbLogisticsPackListDet tbLogisticsPackListDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListDet 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListDet update(TbLogisticsPackListDetParam tbLogisticsPackListDet);
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
      *
      * @param shipmentId
      * @return
      */
     List<TbLogisticsPackListDet> queryByShipmentId(String shipmentId);

     /**
      * 根据shipmentId更新已装箱数量
      * @param shipmentId
      * @param packListCode
      * @return
      */
     int updateBoxedQty(String shipmentId,String packListCode);

     /**
      * 根据packListCode查询明细
      * @param packListCode
      * @return
      */
     List<TbLogisticsPackListDet> queryByPackListCode(String packListCode);

     TbLogisticsPackListDet queryByPackListCodeAndSku(String packListCode, String sku);

     int deleteByPackListCode(String packListCode);

     int deleteByPackCode(String packCode);
 }