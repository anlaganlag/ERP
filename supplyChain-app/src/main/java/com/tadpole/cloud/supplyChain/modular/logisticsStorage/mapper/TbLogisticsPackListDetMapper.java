package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 亚马逊货件-明细-按SKU的汇总;(tb_logistics_pack_list_det)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackListDetMapper  extends BaseMapper<TbLogisticsPackListDet>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackListDetResult> selectByPage(IPage<TbLogisticsPackListDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackListDet> wrapper);


    @Update("   MERGE INTO " +
            "   tb_logistics_pack_list_det a " +
            "   USING ( " +
            "      SELECT MERCHANT_SKU,SUM(QUANTITY) QUANTITY,MAX(SHIPMENT_ID)SHIPMENT_ID     " +
            "      FROM        tb_logistics_pack_list_box_rec_det   " +
            "      WHERE SHIPMENT_ID =#{shipmentId} GROUP BY MERCHANT_SKU " +
            "   )b ON (a.SHIPMENT_ID=b.SHIPMENT_ID AND a.MERCHANT_SKU=b.MERCHANT_SKU) " +
            " WHEN MATCHED THEN " +
            " update set a.BOXED_QTY = b.QUANTITY WHERE a.SHIPMENT_ID=#{shipmentId}")
    int updateBoxedQtyByShipmentId(@Param("shipmentId")String shipmentId);

    @Update("   MERGE INTO " +
            "   tb_logistics_pack_list_det a " +
            "   USING ( " +
            "      SELECT MERCHANT_SKU,SUM(QUANTITY) QUANTITY,MAX(pack_list_code)pack_list_code     " +
            "      FROM        tb_logistics_pack_list_box_rec_det   " +
            "      WHERE pack_list_code =#{packListCode} GROUP BY MERCHANT_SKU " +
            "   )b ON (a.SHIPMENT_ID=b.pack_list_code AND a.MERCHANT_SKU=b.MERCHANT_SKU) " +
            " WHEN MATCHED THEN " +
            " update set a.BOXED_QTY = b.QUANTITY WHERE a.pack_list_code=#{packListCode}")
    int updateBoxedQtyByPackListCode(@Param("packListCode")String packListCode);

    void updateShipmentRealStatus();
}