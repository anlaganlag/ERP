package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsInGoodsModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsSingleBoxRecModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResultModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResult;

import java.util.List;

/**
 * 亚马逊货件;(tb_logistics_pack_list)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackListMapper  extends BaseMapper<TbLogisticsPackList>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackListResult> selectByPage(IPage<TbLogisticsPackListResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackList> wrapper);

     /**
      * 分页查询 xml 写sql
      *
      * @param pageContext
      * @param reqVO
      * @return
      */
     IPage<TbLogisticsPackListResultModel> queryPage(Page pageContext, @Param("reqVO") TbLogisticsPackListParam reqVO);

     List<LogisticsSingleBoxRecModel> getSingleBoxRec(@Param("shipmentId")String shipmentId, @Param("sku")String sku);

    List<LogisticsSingleBoxRecModel> getSingleBoxRecNew(@Param("packListCode")String packListCode, @Param("sku")String sku );

    void updateShipmentRealStatus();

    List<LogisticsInGoodsModel> queryLogisticsInGoodsList(@Param("shipmentId") String shipmentId);

    List<String> getTbComShippingLableFromEbms(@Param("csfm") List<String> csfm);
}