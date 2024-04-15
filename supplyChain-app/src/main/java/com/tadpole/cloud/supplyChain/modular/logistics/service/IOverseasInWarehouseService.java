package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouse;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  海外仓入库管理服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface IOverseasInWarehouseService extends IService<OverseasInWarehouse> {

    /**
     * 出货仓名称下拉
     * @return
     */
    List<Map<String, Object>> outWarehouseSelect();

    /**
     * 收货仓名称下拉
     * @return
     */
    List<Map<String, Object>> inWarehouseSelect();

    /**
     * 海外仓入库管理查询列表接口
     */
    PageResult<OverseasInWarehouseResult> queryListPage(OverseasInWarehouseParam param);

    /**
     * 海外仓入库管理查询列表数据汇总
     */
    ResponseData queryPageTotal(OverseasInWarehouseParam param);

    /**
     * 海外仓入库管理是否可以全部签收
     */
    ResponseData allowAllSign(OverseasInWarehouseParam param);

    /**
     * 海外仓报表查询列表接口
     */
    PageResult<OverseasReportResult> queryReportListPage(OverseasReportParam param);

    /**
     * 查询入库单详情接口
     */
    PageResult<OverseasInWarehouseDetailResult> list(OverseasInWarehouseDetailParam param);

    /**
     * 海外仓入库管理列表导出接口
     */
    List<OverseasInWarehouseResultVO> export(OverseasInWarehouseParam param);

    /**
     * 海外仓报表导出接口
     */
    List<OverseasReportResult> exportOverseasReport(OverseasReportParam param);

    /**
     * 编辑收货仓接口
     */
    ResponseData edit(OverseasInWarehouseParam param);

    /**
     * 编辑备注接口
     */
    ResponseData editRemark(OverseasInWarehouseParam param);

    /**
     * 签收接口
     */
    ResponseData sign(OverseasInWarehouseParam overseasInWarehouseParam,OverseasInWarehouseDetailParam param) throws Exception;

    /**
     * 获取FBA退海外仓任务（定时/手动）
     * @param resultList
     * @return
     */
    ResponseData generateInWarehouseByFBA(List<OverseasInWarehouseFBAResult> resultList);

    /**
     * 亚马逊发海外仓刷新物料编码为空的数据
     */
    void refreshOiwMaterialCode();

    /**
     * 获取EBMS出货清单
     */
    ResponseData generateInWarehouseByEBMS(List<EbmsOverseasInWarehouseParam> params);

    /**
     * 接收EBMS出货清单物流信息接口
     */
    ResponseData updateLogistics(List<EbmsOiwLogisticsParam> params);

    /**
     * 接收EBMS出货清单物流跟踪状态接口
     */
    ResponseData updateLogisticsStatus(List<EbmsOiwLogisticsParam> params);

    /**
     * 接收EBMS出货清单物流删除接口
     */
    ResponseData deleteLogistics(List<EbmsOiwLogisticsParam> params);

    /**
     * 更新入库海外仓状态
     * @return
     */
    void updateGenerateHwc(List<BigDecimal> rsdIdList);

    /**
     * 分配组织物料编码
     * @param param
     * @return
     */
    List<String> matCodeList(OverseasInWarehouseDetailParam param);

    /**
     * 海外仓入库单同步到K3组织内调拨单(定时)
     * @return
     */
    ResponseData reSyncTransferToErp();

    /**
     * 运输方式下拉
     * @return
     */
    List<Map<String, Object>> suggestTransTypeSelect();

    /**
     * 批量签收FBA退海外仓-导入
     * @param file
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @param warehouseNameList
     * @return
     */
    ResponseData uploadFbaSign(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList, List<Map<String, String>> warehouseNameList);

    /**
     * 批量签收FBA退海外仓-保存
     * @param params
     * @return
     */
    ResponseData batchFbaSign(List<OwInFbaSignParam> params);

    /**
     * 亚马逊发海外仓报表查询列表
     */
    ResponseData queryFbaReportPage(OverseasFbaReportParam param);

    /**
     * 亚马逊发海外仓报表查询列表数据汇总
     */
    ResponseData queryFbaReportTotal(OverseasFbaReportParam param);

    /**
     * 亚马逊发海外仓报表导出
     */
    List<OverseasFbaReportResult> exportFbaReport(OverseasFbaReportParam param);

    /**
     * 批量签收FBA退海外仓（新）
     * @param params
     * @return
     */
    ResponseData batchFbaSignNew(List<OwInFbaSignParam> params);
}
