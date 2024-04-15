package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  海外仓入库管理Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface OverseasInWarehouseMapper extends BaseMapper<OverseasInWarehouse> {

    /**
     * 海外仓入库管理查询列表
     * @param param  queryReportListPage
     * @return
     */
    Page<OverseasInWarehouseResult> queryListPage(@Param("page") Page page, @Param("param") OverseasInWarehouseParam param);

    /**
     * 海外仓入库管理查询列表数据汇总
     * @param param
     * @return
     */
    OverseasInWarehouseTotalResult queryPageTotal(@Param("param") OverseasInWarehouseParam param);

    /**
     * 海外仓入库管理是否可以全部签收
     * @param inOrder
     * @return
     */
    Integer allowAllSign(@Param("inOrder") String inOrder);

    /**
     * 海外仓报表查询列表
     * @param param
     * @return
     */
    Page<OverseasReportResult> queryReportListPage(@Param("page") Page page, @Param("param") OverseasReportParam param);

    /**
     * 海外仓入库管理查询详情
     * @param param
     * @return
     */
    Page<OverseasInWarehouseDetailResult> list(@Param("page") Page page, @Param("param") OverseasInWarehouseDetailParam param);

    /**
     * 海外仓入库管理导出
     * @param param
     * @return
     */
    List<OverseasInWarehouseResultVO> allList(@Param("param") OverseasInWarehouseParam param);

    /**
     * 已到货数量
     * @param orderNo
     * @param confirmStatus
     * @return
     */
    @Select("select SUM(ACTUAL_QUANTITY) from OVERSEAS_IN_WAREHOUSE_DETAIL WHERE IN_ORDER=#{orderNo} AND CONFIRM_STATUS=#{confirmStatus} ")
    BigDecimal signTotalQty(String orderNo, String confirmStatus);

    /**
     * 分配组织物料编码
     * @param orderNo
     * @return
     */
    @Select("select DISTINCT MATERIAL_CODE from OVERSEAS_IN_WAREHOUSE_DETAIL WHERE IN_ORDER=#{orderNo} ")
    List<String> matCodeList(String orderNo);

    /**
     * 获取海外仓入库管理-FBA退海外仓数据
     * @param param
     * @return
     */
    List<OwInFbaSignParam> getInWareHouseFBA(@Param("param") OwInFbaSignParam param);

    /**
     * 亚马逊发海外仓报表查询列表、导出
     * @param param
     * @return
     */
    Page<OverseasFbaReportResult> queryFbaReportPage(@Param("page") Page page, @Param("param") OverseasFbaReportParam param);

    /**
     * 亚马逊发海外仓报表查询列表数据汇总
     * @param param
     * @return
     */
    OverseasInWarehouseTotalResult queryFbaReportTotal(@Param("param") OverseasFbaReportParam param);

    /**
     * 根据id更新编辑收货仓的名称
     * @param param
     * @return
     */
    void updateWareNameById(@Param("param") OverseasInWarehouse param);

    /**
     * 亚马逊发海外仓刷新物料编码为空的数据
     */
    void refreshOiwMaterialCode();
}
