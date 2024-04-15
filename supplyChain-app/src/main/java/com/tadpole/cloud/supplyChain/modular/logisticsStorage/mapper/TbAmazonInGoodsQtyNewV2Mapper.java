package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbAmazonInGoodsQtyNewV2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbAmazonInGoodsQtyNewV2Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * Amazon在途库存报表;(Tb_Amazon_In_Goods_Qty_New_V2)表数据库访问层
 * @author : LSY
 * @date : 2024-3-18
 */
@Mapper
public interface TbAmazonInGoodsQtyNewV2Mapper  extends BaseMapper<TbAmazonInGoodsQtyNewV2>{
    /**
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbAmazonInGoodsQtyNewV2Result> selectByPage(IPage<TbAmazonInGoodsQtyNewV2Result> page , @Param(Constants.WRAPPER) Wrapper<TbAmazonInGoodsQtyNewV2> wrapper);

    @Update("TRUNCATE TABLE Tb_Amazon_In_Goods_Qty_New_V2")
    void truncateTableTbAmazonInGoodsQtyNewV2();

    void initOnTheWayData();

    @Update("TRUNCATE TABLE Tb_Bsc_Overseas_Way_Ship_Status_Analysis_New")
    void truncateTableTbBscOverseasWayShipStatusAnalysisNew();

    void initFinishData();
}