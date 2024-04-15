package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CrossTransfer;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportK3CrossTransferResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* <p>
    * K3跨组织直接调拨单主表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
public interface K3CrossTransferMapper extends BaseMapper<K3CrossTransfer> {

    Page<K3CrossTransferResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") K3CrossTransferParam paramCondition);

    Page<ExportK3CrossTransferResult> findPageBySpecV2(@Param("page") Page page, @Param("paramCondition") K3CrossTransferParam paramCondition);

    Page<K3CrossTransferItemResult> list(@Param("page") Page page, @Param("paramCondition") K3CrossTransferItemParam paramCondition);

    @Select("SELECT *  FROM MABANG_SHOP_LIST  m   WHERE m.ID = #{shopId}")
    MabangShopList getShoplist(String shopId);

    List<MabangOrders> syncMabangOrders(@Param("paramCondition")  MabangOrdersParam paramCondition);

    @Select("SELECT *  FROM MABANG_ORDER_ITEM  b   WHERE b.ORIGIN_ORDER_ID= #{erpOrderId} AND ( b.STOCK_WAREHOUSE_NAME= '雁田定制仓' OR b.STOCK_WAREHOUSE_ID=1047844)")
    List<MabangOrderItem> syncMabangOrderItem(String erpOrderId);

    @Select("select distinct b.FNAME from T_BD_CUSTOMER a left join T_BD_CUSTOMER_L b on b.FCUSTID=a.FCUSTID where a.FDOCUMENTSTATUS='C' and a.FNUMBER=#{fNumber}")
    String getFinanceName(String fNumber);

    @Select("select distinct F_OWNER_ID_HEAD from K3_CROSS_TRANSFER where F_OWNER_ID_HEAD_NAME IS NULL ")
    List<String> getFownerIdHead();

    @Update("update K3_CROSS_TRANSFER  set F_OWNER_ID_HEAD_NAME = #{ownerName} where F_OWNER_ID_HEAD = #{ownerId}")
    void updateOwnerIdName(String ownerId,String ownerName);

    @Select("SELECT  t2.F_MATERIAL_ID material_code,t2.F_OWNER_ID shop_code FROM K3_CROSS_TRANSFER t1 LEFT JOIN K3_CROSS_TRANSFER_ITEM t2 ON t2.CROSS_ID =t1.ID where t1.SYNC_STATUS=#{status} AND t2.F_OWNER_ID IS NOT null ")
    List<ZZDistributeMcms> getFownerIdAndMat(int status);

    /**
     * 马帮已发货订单非限定平台订单状态更新
     * @param param
     */
    void updateLimitPlatformOrders(@Param("param") MabangOrdersParam param);
}
