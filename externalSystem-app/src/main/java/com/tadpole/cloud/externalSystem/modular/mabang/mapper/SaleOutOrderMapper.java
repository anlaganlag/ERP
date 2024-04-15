package com.tadpole.cloud.externalSystem.modular.mabang.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.modular.mabang.dto.MabangOrdersDTO;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SyncSaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleOutOrderResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* <p>
    * 销售出库单 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
public interface SaleOutOrderMapper extends BaseMapper<SaleOutOrder> {

    /**
     * 销售出库单列表
     * @param param
     * @return
     */
    Page<ExportSaleOutOrderResult> findPageBySpec(@Param("page") Page page, @Param("param") SaleOutOrderParam param);

    /**
     * 查询马帮发货订单生成销售出库的订单列表
     * @param param
     * @return
     */
    List<MabangOrdersDTO> selectMabangtOrders(@Param("param") SyncSaleOutOrderParam param);

    /**
     *
     * @param param
     * @return
     */
    List<MabangOrdersDTO> mabangtOrdersMergeList(@Param("param") SyncSaleOutOrderParam param);

    /**
     * 同步销售出库单明细
     * @param billNo
     * @return
     */
    List<SaleOutOrderItem> getSyncList(@Param("billNo") String billNo);

    /**
     *分配销售组织物料编码
     * @return
     */
    List<ZZDistributeMcms> getFsaleOrgIdAndMat();

    @Select("SELECT DISTINCT SAL_ORG_CODE FROM SALE_OUT_ORDER T WHERE T.SAL_ORG_NAME IS NULL ")
    List<String> getSalOrgName();

    @Select("select distinct b.FNAME from T_BD_CUSTOMER a left join T_BD_CUSTOMER_L b on b.FCUSTID=a.FCUSTID where a.FDOCUMENTSTATUS='C' and a.FNUMBER=#{fNumber}")
    String getFinanceName(String fNumber);

    @Select("SELECT distinct D.FNAME FROM T_BD_STOCK C LEFT JOIN T_BD_STOCK_L D ON D.FSTOCKID=C.FSTOCKID WHERE C.FDOCUMENTSTATUS='C' AND C.FNUMBER=#{fNumber}")
    String getWarehouseName(String fNumber);

    @Update("update sale_out_order  set sal_org_name = #{ownerName} where sal_org_code = #{ownerId}")
    void updateSalOrgName(String ownerId,String ownerName);

    @Update("update sale_out_order_item  set warehouse_name = #{ownerName} where warehouse_name is null and  warehouse_id = #{ownerId}")
    void updateWarehouseName(String ownerId,String ownerName);
}
