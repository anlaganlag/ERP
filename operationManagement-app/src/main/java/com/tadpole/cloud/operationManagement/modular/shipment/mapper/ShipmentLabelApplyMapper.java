package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentLabelApply;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentLabelApplyResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


 /**
 * 发货标签申请;(SHIPMENT_LABEL_APPLY)表数据库访问层
 * @author : LSY
 * @date : 2024-3-21
 */
@Mapper
public interface ShipmentLabelApplyMapper  extends BaseMapper<ShipmentLabelApply>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<ShipmentLabelApplyResult> selectByPage(IPage<ShipmentLabelApplyResult> page , @Param(Constants.WRAPPER) Wrapper<ShipmentLabelApply> wrapper);
}