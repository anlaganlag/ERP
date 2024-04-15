package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManageRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  海外仓库存管理Mapper接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface OverseasWarehouseManageMapper extends BaseMapper<OverseasWarehouseManage> {

    /**
     * 获取海外仓商品现有库存
     */
    @Select("SELECT INVENTORY_QUANTITY FROM OVERSEAS_WAREHOUSE_MANAGE WHERE PLATFORM=#{param.platform} AND SYS_SHOPS_NAME='TS' AND SYS_SITE=#{param.site} AND MATERIAL_CODE=#{param.customerGoodsCode} AND INVENTORY_QUANTITY != 0")
    BigDecimal selectStockQty(@Param("param") RakutenOverseasShipmentResult param);

    /**
     * 获取海外仓商品现有库存
     */
    @Select("SELECT MAX(SKU) FROM OVERSEAS_WAREHOUSE_MANAGE T WHERE T.SYS_SITE=#{site} AND T.FN_SKU=#{fnsku}")
    String selectSku(String site,String fnsku);

    /**
     * 海外仓库存管理分页查询列表
     * @param param
     * @return
     */
    Page<OverseasWarehouseManageResult> queryPage(@Param("page") Page page, @Param("param") OverseasWarehouseManageParam param);

    /**
     * 海外仓管理分页查询列表数据汇总
     * @param param
     * @return
     */
    OverseasWarehouseManageTotalResult queryPageTotal(@Param("param") OverseasWarehouseManageParam param);

    /**
     * 获取EBMS标签管理数据
     * @return
     */
    List<ValidateLabelResult> selectTbComShippingLabel(@Param("param") ValidateLabelParam param);

    /**
     * 校验K3标签
     * @return
     */
    List<ValidateLabelResult> validateK3Label(@Param("param") ValidateLabelParam param);

    /**
     * 根据站点级联EBMS仓库名称下拉
     * @param site 站点
     * @param owState 状态
     * @param warehouseName 仓库名称
     * @return
     */
    List<Map<String, String>> warehouseNameBySiteSelect(@Param("site") String site, @Param("warehouseName") String warehouseName, @Param("owState") String owState);

    /**
     * 获取ERP乐天事业部和team信息
     * @return
     */
    String selectRakutenTeam(@Param("param") CustomerMaterialInfoParam param);

    /**
     * 获取乐天出库最大发货时间
     */
    /*@Select("SELECT MAX(SHIPMENT_DATE) from OVERSEAS_OUT_WAREHOUSE_DETAIL t where t.OUT_ORDER LIKE '%${orderNo}%'")
    Date outOrderMaxShipmentDate(String orderNo);*/

    /**
     * 同步乐天出库记录明细
     * @param idList
     * @param outOrg
     * @return
     */
    List<OverseasWarehouseManageRecord> getSyncList(@Param("idList") List<BigDecimal> idList, @Param("outOrg") String outOrg);

    /**
     * 待同步乐天出库单号信息
     * @param idList
     * @return
     */
    List<OverseasWarehouseManageRecord> getSyncRecordList(@Param("idList") List<BigDecimal> idList, @Param("operateType") String operateType);

    /**
     * sku种类集合
     * @param orderNo
     * @return
     */
    @Select("SELECT distinct t.sku from OVERSEAS_OUT_WAREHOUSE_DETAIL t where t.OUT_ORDER LIKE '%${orderNo}%'")
    List<String> typeList(String orderNo);

    /**
     * 库存产品重量体积报表查询列表、导出
     * @param param
     * @return
     */
    Page<OwVolumeReportResult> queryOwVolumePage(@Param("page") Page page, @Param("param") OwVolumeReportParam param);

    /**
     * 根据账号、站点、sku获取事业部和Team
     * @return
     */
    List<InvProductGalleryParam> getInvProductGallery(@Param("param") InvProductGalleryParam param);
}
