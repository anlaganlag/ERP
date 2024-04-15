package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListBoxRecDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListBoxRecDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * 出货清单和亚马逊货件关系映射-明细;(tb_logistics_pack_list_box_rec_det)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackListBoxRecDetMapper  extends BaseMapper<TbLogisticsPackListBoxRecDet>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackListBoxRecDetResult> selectByPage(IPage<TbLogisticsPackListBoxRecDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackListBoxRecDet> wrapper);

    void updateShipmentRealStatus();
}