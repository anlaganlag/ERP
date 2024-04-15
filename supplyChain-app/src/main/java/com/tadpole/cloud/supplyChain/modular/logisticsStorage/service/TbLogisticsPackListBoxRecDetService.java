package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRecDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListBoxRecDetParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 出货清单和亚马逊货件关系映射-明细;(tb_logistics_pack_list_box_rec_det)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListBoxRecDetService extends IService<TbLogisticsPackListBoxRecDet> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param sysDetId 主键
     * @return 实例对象
     */
    TbLogisticsPackListBoxRecDet queryById(BigDecimal sysDetId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackListBoxRecDet 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackListBoxRecDetResult> paginQuery(TbLogisticsPackListBoxRecDetParam tbLogisticsPackListBoxRecDet, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListBoxRecDet 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListBoxRecDet insert(TbLogisticsPackListBoxRecDet tbLogisticsPackListBoxRecDet);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListBoxRecDet 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListBoxRecDet update(TbLogisticsPackListBoxRecDetParam tbLogisticsPackListBoxRecDet);
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
      * 根据PackCode出货清单号 删除 出货清单和亚马逊货件关系映射明细
      *
      * @param packCode 出货清单号
      * @return 是否成功
      */
     boolean deleteByPackCode(String packCode);

     /**
      *
      * @param shipmentId
      * @return
      */
     List<TbLogisticsPackListBoxRecDet> queryByShipmentId(String shipmentId);

     /**
      * 批量更新数量
      * @param detParamList
      * @return
      */
     ResponseData batchUpdate(List<TbLogisticsPackListBoxRecDetParam> detParamList);

     /**
      * 根据PackListCode出货清单号 查询 出货清单和亚马逊货件关系映射明细
      * @param packListCode
      * @return
      */
     List<TbLogisticsPackListBoxRecDet> queryByPackListCode(String packListCode);

     int deleteByPackListCode(String packListCode);
 }