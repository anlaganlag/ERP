package com.tadpole.cloud.operationManagement.modular.shipment.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentLabelApply;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentLabelApplyParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentLabelApplyResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

 /**
 * 发货标签申请;(SHIPMENT_LABEL_APPLY)表服务接口
 * @author : LSY
 * @date : 2024-3-21
 */
public interface ShipmentLabelApplyService extends IService<ShipmentLabelApply> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    ShipmentLabelApply queryById(BigDecimal id);
    
    /**
     * 分页查询
     *
     * @param shipmentLabelApply 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<ShipmentLabelApplyResult> paginQuery(ShipmentLabelApplyParam shipmentLabelApply, long current, long size);
    /** 
     * 新增数据
     *
     * @param shipmentLabelApply 实例对象
     * @return 实例对象
     */
    ShipmentLabelApply insert(ShipmentLabelApply shipmentLabelApply);
    /** 
     * 更新数据
     *
     * @param shipmentLabelApply 实例对象
     * @return 实例对象
     */
    ShipmentLabelApply update(ShipmentLabelApplyParam shipmentLabelApply);
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

     List<ShipmentLabelApply> addBatch(List<ShipmentLabelApply> shipmentLabelApplyList);

     ResponseData syncLabelToK3(List<ShipmentLabelApply> shipmentLabelApplyList);

     ShipmentLabelApply syncToK3(ShipmentLabelApply labelApply);

     ResponseData syncToK3WebApi(ShipmentLabelApply item);

     ResponseData importCreateSku(MultipartFile file);
 }