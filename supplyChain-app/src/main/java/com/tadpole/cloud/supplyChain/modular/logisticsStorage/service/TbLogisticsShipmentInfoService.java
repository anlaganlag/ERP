package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentInfo;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsShipmentInfoResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentInfoParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊返回的发货申请信息-新版API流程;(tb_logistics_shipment_info)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsShipmentInfoService extends IService<TbLogisticsShipmentInfo> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkShipId 主键
     * @return 实例对象
     */
    TbLogisticsShipmentInfo queryById(BigDecimal pkShipId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsShipmentInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsShipmentInfoResult> paginQuery(TbLogisticsShipmentInfoParam tbLogisticsShipmentInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsShipmentInfo 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentInfo insert(TbLogisticsShipmentInfo tbLogisticsShipmentInfo);
    /** 
     * 更新数据
     *
     * @param tbLogisticsShipmentInfo 实例对象
     * @return 实例对象
     */
    TbLogisticsShipmentInfo update(TbLogisticsShipmentInfoParam tbLogisticsShipmentInfo);
    /** 
     * 通过主键删除数据
     *
     * @param pkShipId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkShipId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkShipIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkShipIdList);
}