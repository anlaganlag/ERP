package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleReturnOrderResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
* <p>
    * 销售退货单 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
public interface SaleReturnOrderMapper extends BaseMapper<SaleReturnOrder> {

    /**
     * 销售退货单列表
     * @param param
     * @return
     */
    Page<ExportSaleReturnOrderResult> findPageBySpec(@Param("page") Page page, @Param("param") SaleReturnOrderParam param);

    /**
     * 马帮退货订单待生成销售退货单
     * @param paramCondition
     * @return
     */
    List<MabangReturnOrder> syncMabangReturnOrders(@Param("paramCondition") MabangReturnOrderParam paramCondition);

    /**
     * 马帮退货订单明细
     * @param paramCondition
     * @return
     */
    List<MabangReturnOrderItem> syncMabangReturnOrderItem(@Param("paramCondition") MabangReturnOrderParam paramCondition);

    /**
     * 查询店铺信息
     * @param shopId
     * @return
     */
    @Select("SELECT *  FROM MABANG_SHOP_LIST  m   WHERE m.ID = #{shopId}")
    MabangShopList getShoplist(BigDecimal shopId);

    /**
     * 查询马帮发货订单平台SKU
     * @param platOrdId
     * @param stockSku
     * @return
     */
    @Select("SELECT moi.PLATFORM_SKU  FROM MABANG_ORDERS mo LEFT JOIN MABANG_ORDER_ITEM moi ON moi.ORIGIN_ORDER_ID = mo.ERP_ORDER_ID WHERE mo.PLAT_ORD_ID=#{platOrdId} AND moi.stock_sku=#{stockSku} AND rownum=1")
    String getPlatSku(String platOrdId,String stockSku);

    @Select("SELECT DISTINCT SAL_ORG_CODE FROM SALE_RETURN_ORDER T WHERE T.SAL_ORG_NAME IS NULL ")
    List<String> getSalOrgName();

    @Select("select distinct b.FNAME from T_BD_CUSTOMER a left join T_BD_CUSTOMER_L b on b.FCUSTID=a.FCUSTID where a.FDOCUMENTSTATUS='C' and a.FNUMBER=#{fNumber}")
    String getFinanceName(String fNumber);

    @Select("SELECT distinct D.FNAME FROM T_BD_STOCK C LEFT JOIN T_BD_STOCK_L D ON D.FSTOCKID=C.FSTOCKID WHERE C.FDOCUMENTSTATUS='C' AND C.FNUMBER=#{fNumber}")
    String getWarehouseName(String fNumber);

    @Update("update sale_return_order  set sal_org_name = #{ownerName} where sal_org_code = #{ownerId}")
    void updateSalOrgName(String ownerId,String ownerName);

    @Update("update sale_return_order_item  set warehouse_name = #{ownerName} where warehouse_id = #{ownerId}")
    void updateWarehouseName(String ownerId,String ownerName);
}
