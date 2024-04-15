package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OwVolumeReportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.RakutenOverseasShipmentResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.ValidateLabelResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OrderTypePreEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 海外仓库存管理服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
public interface IOverseasWarehouseManageService extends IService<OverseasWarehouseManage> {

    /**
     * 乐天海外仓出库导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 乐天海外仓出库保存
     * @param params
     * @return
     */
    ResponseData save(List<RakutenOverseasShipmentResult> params);

    /**
     * 海外仓库存管理分页查询列表
     */
    ResponseData queryPage(OverseasWarehouseManageParam param);

    /**
     * 海外仓管理分页查询列表数据汇总
     */
    ResponseData queryPageTotal(OverseasWarehouseManageParam param);

    /**
     * 海外仓库存管理导出
     * @param param
     * @return
     */
    List<OverseasWarehouseManageResult> export(OverseasWarehouseManageParam param);

    /**
     * 换标
     * @param param
     * @param overseasWarehouseManage
     * @return
     */
    ResponseData exchange(OverseasWarehouseChangeParam param, OverseasWarehouseManage overseasWarehouseManage);

    /**
     * 批量换标（导入）
     * @param file
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @param userList
     * @return
     */
    ResponseData upload(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList, List<Map<String, Object>> userList);

    /**
     * 批量换标-保存
     * @param params
     * @param isValidPass
     * @return
     */
    ResponseData batchExchange(List<OverseasWarehouseManageExchangeParam> params, Boolean isValidPass);

    /**
     * 盘点
     * @param param
     * @param overseasWarehouseManage
     * @return
     */
    ResponseData check(OverseasWarehouseManageParam param, OverseasWarehouseManage overseasWarehouseManage);

    /**
     * 批量盘点-导入
     * @param file
     * @param platformList
     * @param shopList
     * @param siteList
     * @param departmentList
     * @param teamList
     * @return
     */
    ResponseData uploadCheck(MultipartFile file, List<String> platformList, List<String> shopList, List<String> siteList, List<String> departmentList, List<String> teamList);

    /**
     * 批量盘点-保存
     * @param params
     * @return
     */
    ResponseData batchCheck(List<OverseasWarehouseManageCheckParam> params);

    /**
     * 根据站点级联EBMS仓库名称下拉
     * @param site 站点
     * @param owState 状态
     * @param warehouseName 仓库名称
     * @return
     */
    ResponseData warehouseNameBySiteSelect(String site, String warehouseName, String owState);

    /**
     * 每天定时生成海外仓订单号
     * @return
     */
    ResponseData generateOrder();

    /**
     * 手动生成每天海外仓订单号
     * @param orderType
     * @param key
     * @param noLength
     * @return
     */
    ResponseData handleGenerateOrder(String orderType, String key, Integer noLength);

    /**
     * 获取海外仓订单号
     * @param redisKey
     * @param orderType
     * @param noLength
     * @return
     */
    String getLogisticsOrder(String redisKey, OrderTypePreEnum orderType, Integer noLength);

    /**
     * 校验标签
     * @param param
     * @return
     */
    List<ValidateLabelResult> validateLabel(ValidateLabelParam param);

    /**
     * 校验K3标签
     * @param param
     * @return
     */
    List<ValidateLabelResult> validateK3Label(ValidateLabelParam param);

    /**
     * ERP乐天事业部和team信息
     * @param param
     * @return
     */
    String RakutenTeam(CustomerMaterialInfoParam param);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    OverseasWarehouseManage getManageById(Serializable id);

    /**
     * 乐天海外仓出库单同步到K3销售出库单(定时)
     * @return
     */
    ResponseData syncAbnormalOutWarehouseToErp();

    /**
     * Monthly Inventory History生成期末库存列表获取海外仓数据
     */
    List<OverseasWarehouseManage> getOverseasWarehouseManageList();

    /**
     * 查询海外仓管理信息
     * @param param
     * @return
     */
    OverseasWarehouseManage getWarehouseManage(OverseasWarehouseManage param);

    /**
     * 盘点-K3其他入库
     * @param id
     * @return
     */
    ResponseData syncCheckInStockToErp(BigDecimal id);

    /**
     * 批量盘点-K3其他入库
     * @param inIdList
     * @return
     */
    ResponseData syncBatchCheckInStockToErp(List<BigDecimal> inIdList);

    /**
     * 盘点-K3其他出库
     * @param id
     * @return
     */
    ResponseData syncCheckOutStockToErp(BigDecimal id);

    /**
     * 批量盘点-K3其他出库
     * @param outIdList
     * @return
     */
    ResponseData syncBatchCheckOutStockToErp(List<BigDecimal> outIdList);

    /**
     * 批量换标-K3其他入库
     * @param inIdList
     * @return
     */
    ResponseData syncBatchChangeInStockToErp(List<BigDecimal> inIdList);

    /**
     * 批量换标-K3其他出库
     * @param outIdList
     * @return
     */
    ResponseData syncBatchChangeOutStockToErp(List<BigDecimal> outIdList);

    /**
     * 乐天出库-K3销售出库
     * @param outIdList
     * @return
     */
    String syncOutWarehouseToErp(List<BigDecimal> outIdList);

    /**
     * 库存产品重量体积报表查询列表
     */
    ResponseData queryOwVolumePage(OwVolumeReportParam param);

    /**
     * 库存产品重量体积报表查询列表导出
     * @param param
     * @return
     */
    List<OwVolumeReportResult> exportOwVolume(OwVolumeReportParam param);

    /**
     * 根据账号、站点、sku获取事业部和Team
     * @param param
     * @return
     */
    List<InvProductGalleryParam> getInvProductGallery(InvProductGalleryParam param);
}
