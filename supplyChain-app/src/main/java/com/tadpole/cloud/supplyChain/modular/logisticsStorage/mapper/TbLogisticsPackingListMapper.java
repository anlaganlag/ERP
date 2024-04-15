package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpWarehouseCode;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsGoodsListReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsReceiveReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsShipmentReportParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 出货清单信息-金蝶+海外仓;(tb_logistics_packing_list)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackingListMapper  extends MPJBaseMapper<TbLogisticsPackingList> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackingListResult> selectByPage(IPage<TbLogisticsPackingListResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackingList> wrapper);

    List<TbClearancModel> getClearanceData(@Param("packCode") String packCode);

    /**
     * 获取组织编码
     * @return
     */
    @Select("select * from WAREHOUSE.ERP_WAREHOUSE_CODE")
    List<ErpWarehouseCode> getetErpWarehouseCodeList();

    @Select("select max(ele_Platform_Name) AS elePlatformName from STOCKING.TB_COM_SHOP where SHOP_NAME_SIMPLE=#{shopNameSimple} AND COUNTRY_CODE=#{countryCode}")
    String getElePlatformName(String shopNameSimple, String countryCode);

    IPage<TbLogisticsShipmentReportResult> queryPage(@Param("page")Page pageContext, @Param("reqVO")TbLogisticsShipmentReportParam reqVO);

    IPage<LogisticsGoodsListViewModel> getLogisticsGoodsListReport(@Param("page")Page pageContext, @Param("reqVO")TbLogisticsGoodsListReportParam reqVO);

    IPage<LogisticsReceiveReportViewModel> getLogisticsReceiveReport(@Param("page")Page pageContext, @Param("reqVO") TbLogisticsReceiveReportParam reqVO);

    List<Map<String, Object>> lpsr(@Param("packStoreHouseType")String packStoreHouseType, @Param("packStoreHouseName")String packStoreHouseName);

    List<TbShipmentApplyDetModel> lpsrDetAmazonOrWalmart(@Param("packStoreHouseType")String packStoreHouseType, @Param("packStoreHouseName")String packStoreHouseName);
    List<TbShipmentApplyDetModel> lpsrDetOther(@Param("packStoreHouseType")String packStoreHouseType, @Param("packStoreHouseName")String packStoreHouseName);

    @Update(" UPDATE Tb_Logistics_Packing_List T1 " +
            " SET T1.pack_log_state = #{packLogState} " +
            " WHERE EXISTS ( " +
            "     SELECT 1 " +
            "     FROM Tb_Logistics_List_To_Head_Route_Det T2 " +
            "     WHERE T1.pack_code = T2.pack_code AND T2.lhr_code = #{lhrCode} AND T2.lhr_odd_numb = #{lhrOddNumb} " +
            " )")
    int updatePackLogState(@Param("lhrCode")String lhrCode, @Param("lhrOddNumb")String lhrOddNumb, @Param("packLogState")String packLogState);

    @Select("   SELECT DISTINCT T1.*  " +
            "FROM tb_logistics_packing_list  T1 " +
            "JOIN tb_logistics_list_to_head_route_det  T2 ON T1.pack_code = T2.pack_code " +
            "WHERE T2.lhr_code = #{lhrCode}")
    List<TbLogisticsPackingList> queryByLhrCode(@Param("lhrCode")String lhrCode);

}