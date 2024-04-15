package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecommendation;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentTrackingParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SupplementaryTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.VerifParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ErpTeamAvailableQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentApplyItemResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 发货申请明细项 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
public interface IShipmentApplyItemService extends IService<ShipmentApplyItem> {

    /**
     * 发货申请，自选创建
     * @param param
     * @return
     */
    ResponseData selectCreateSku(InvProductGalleryParam param);

    /**
     * 文件导入创建发货sku
     * @param file
     * @return
     */
    ResponseData importCreateSku(MultipartFile file,Map<String, String> deliveryMap);

    /**
     * 查询申请单数据
     * @param applyStauts 申请单状态
     * @param bizType 业务类型 SQ申请,SH审核
     * @return
     */
    ResponseData applyList(String applyStauts,String bizType);

    @DataSource(name = "stocking")
    ResponseData verifyList(ShipmentTrackingParam trackingParam);

    ResponseData exportVerifyList(ShipmentTrackingParam trackingParam);


    /**
     * 发货分析
     * @param applyItemList
     * @param again 属于重新分析 ture是 ，false 否
     * @param needSave 是否保存更新分析结果 ture是 ，false 否
     * @return
     */
    ResponseData shipmentAnalyze(List<ShipmentApplyItem> applyItemList ,boolean again,boolean needSave);

    /**
     * 按批次多个sku分析
     * @param itemList 通一D6维度的sku集合
     * @param shipmentRecommendation BI推荐数据
     * @param again 再次分析
     * @param analyzeResultUpdateList 分析结果需要更新数据库item
     * @return
     */
    List<ShipmentApplyItemResult> batchSkuAnalyze(List<ShipmentApplyItem> itemList, ShipmentRecommendation shipmentRecommendation, boolean again, List<ShipmentApplyItem> analyzeResultUpdateList);



    /**
     * erp系统team下的可用物料数量
     * @param team
     * @param materialCode
     * @param deliverypointNo
     * @return
     */
    ErpTeamAvailableQytResult erpTeamAvailableQty(String team, String materialCode, String deliverypointNo);


    /**
     * erp系统可用物料数量
     * @param team
     * @param materialCode
     * @param deliverypointNo
     * @return
     */
    ErpTeamAvailableQytResult erpAvailableQty(String team, String materialCode,String deliverypointNo);

    /**
     * 发货审核-通过
     * @param verifParam
     * @return
     */

    ResponseData verif(VerifParam verifParam);

    /**
     * 发货重新分析
     * @param applyBatchNoList
     * @return
     */
    ResponseData shipmentAgainAnalyze(List<String> applyBatchNoList);

    /**
     * 根据批次号删除申请
     * @param applyBatchNoList
     * @return
     */
    ResponseData deleteByBatchNo(List<String> applyBatchNoList);

    /**
     * 分析并保存
     * @param applyItemList
     * @return
     */
    ResponseData analyzeSave(List<ShipmentApplyItem> applyItemList);

    /**
     * 自选创建-保存
     * @param itemGroupList
     * @return
     */
    ResponseData createSave(List<List<ShipmentApplyItem>> itemGroupList);

    /**
     * 自选创建-保存并提交
     * @param itemGroupList
     * @return
     */
    ResponseData createSaveComit(List<List<ShipmentApplyItem>> itemGroupList);

    /**
     * 提交申请
     * @param applyBatchNoList 批次编号List
     * @return
     */
    ResponseData comitByBatchNo(List<String> applyBatchNoList);

    /**
     * 海外仓
     * @return
     */
    ResponseData warehouse(String platform, String site);

    /**
     * 审核通过时校验库存
     * @param batchNoList
     * @return
     */
    List<String> verifComitCheck(List<String> batchNoList,Boolean isCommit);

    public Map<String, String> getWarehouseCode(Set<String> warehouseSet);

    public ResponseData batchSave(List<ShipmentApplyItem> itemList);

    /**
     * 发货审核-已通过-jcerp补调拨单数据
     * @param supplementaryTransferParam
     * @return
     */
    ResponseData supplementaryTransfer(SupplementaryTransferParam supplementaryTransferParam);
}
