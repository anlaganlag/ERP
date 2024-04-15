package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipment;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊发货申请记录-EBMS形成记录;(tb_logistics_shipment)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsShipmentService extends IService<TbLogisticsShipment> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsShipment queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsShipment 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsShipmentResult> pageQuery(TbLogisticsShipmentParam tbLogisticsShipment, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipment 实例对象
     * @return 实例对象
     */
    TbLogisticsShipment insert(TbLogisticsShipment tbLogisticsShipment);
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipment 实例对象
     * @return 实例对象
     */
    TbLogisticsShipment update(TbLogisticsShipmentParam tbLogisticsShipment);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal id);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param idList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> idList);

     /**
      * 根据出货清单号查询 发货计划信息
      * @param packCode
      * @return
      */
     TbLogisticsShipment queryByPackCode(String packCode);

     /**
      * Amazon发货申请删除
      * @param packCode
      * @return
      */
     boolean deleteByPackCode(String packCode);

     /**
      * 基于出货清单生成Plan
      * @param shipmentList
      * @return
      */
     ResponseData createShipmentPlanToShipmentList(List<TbLogisticsShipmentParam> shipmentList);

     /**
      * 获取出货清单信息
      * @param packCode
      * @return
      */
     ResponseData getLogisticsShipmentListToPlan(String packCode);
 }