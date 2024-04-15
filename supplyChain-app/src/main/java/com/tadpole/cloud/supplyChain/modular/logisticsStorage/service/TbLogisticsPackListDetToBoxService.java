package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDetToBox;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetToBoxResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListDetToBoxParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊货件-明细-多少箱每箱装多少;(tb_logistics_pack_list_det_to_box)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListDetToBoxService extends IService<TbLogisticsPackListDetToBox> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TbLogisticsPackListDetToBox queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackListDetToBox 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackListDetToBoxResult> paginQuery(TbLogisticsPackListDetToBoxParam tbLogisticsPackListDetToBox, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListDetToBox 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListDetToBox insert(TbLogisticsPackListDetToBox tbLogisticsPackListDetToBox);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListDetToBox 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListDetToBox update(TbLogisticsPackListDetToBoxParam tbLogisticsPackListDetToBox);
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
      *
      * @param shipmentId
      * @return
      */
     List<TbLogisticsPackListDetToBox> queryByShipmentId(String shipmentId);

     /**
      *
      * @param packListCode
      * @return
      */
     List<TbLogisticsPackListDetToBox> queryByPackListCode(String packListCode);

     int deleteByPackListCode(String packListCode);

 }