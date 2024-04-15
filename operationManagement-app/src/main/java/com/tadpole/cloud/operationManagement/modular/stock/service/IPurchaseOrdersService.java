package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.entity.VerifInfoRecord;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrders2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.*;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.PurchaseOrdersReqVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 采购订单 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-14
 */
public interface IPurchaseOrdersService extends IService<PurchaseOrders> {

    PageResult<PurchaseOrdersResult> queryPage(OperApplyInfoReqVO reqVO);

    PageResult<PurchaseOrdersVerifyResult>  applyRecord(PurchaseOrdersReqVO reqVO);


    Set<String> queryMaterialCodeList(OperApplyInfoReqVO reqVO);

    ResponseData savePurchaseOrders(PurchaseOrdersParam ordersParam);

    ResponseData comitPurchaseOrders(PurchaseOrdersParam ordersParam);

    ResponseData batchSubmit(List<String> orderIdList);



    PageResult<PurchaseOrdersResult> queryplanVerifList(PurchaseOrdersParam reqVO);

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    PageResult<PlanPurchaseOrdersResult> planVerifList(PurchaseOrdersParam reqVO);

    PageResult<PlanPurchaseOrders2Result> planVerifList2(PurchaseOrders2Param reqVO);

    ResponseData verif(PurchaseOrdersParam param);

    ResponseData detail(PurchaseOrdersParam param);

    ResponseData getAsinStockReason(PurchaseOrdersParam param);


    ResponseData pmcVerif(PurchaseOrdersParam param);


    boolean callK3(VerifInfoRecord verifInfoRecord, PurchaseOrders order);

    ResponseData batchVerif(List<PurchaseOrdersParam> paramList);

    List<PurchaseOrdersResult> exportExcel( OperApplyInfoReqVO param);

    @DataSource(name = "stocking")
    List<PurchaseOrdersExcelResult> queryByCondition(OperApplyInfoReqVO param);

    List<PurchaseOrdersVerifyResult> exportRecordExcel(PurchaseOrdersReqVO param);


    ResponseData overTimeAction(StockApprovaltimeParameterResult parameterResult);

    ResponseData planOverTimeAction(StockApprovaltimeParameterResult parameterResult);

    ResponseData getByPurchaseNo(String purchaseNo);

    PageResult<OperApplyInfoResult>  recordList(OperApplyInfoReqVO reqVO);


    ResponseData batchSave(List<PurchaseOrdersParam> ordersParamList);

    Date lastOrderTime(String platform, String team, String materialCode);

    void flashLastOrderTime();

    PurchaseOrders initPurchaseOrders(List<TeamVerif> teamVerifList);


    PageResult<PurchaseOrdersResult> queryPageV3(OperApplyInfoReqV3 reqVO);

    List<PurchaseOrdersResult> exportExcelV3(OperApplyInfoReqV3 param);
}
