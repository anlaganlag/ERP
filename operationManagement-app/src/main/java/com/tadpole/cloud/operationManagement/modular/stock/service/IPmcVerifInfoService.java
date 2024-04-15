package com.tadpole.cloud.operationManagement.modular.stock.service;


import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PmcVerifInfo;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.VerifInfoRecord;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcAutoAnalyzeParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcVerifInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SupplierInfoResult;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 审核记录信息 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-05
 */
public interface IPmcVerifInfoService extends IService<PmcVerifInfo> {

    Boolean saveOrUpdateBatch(List<PmcVerifInfoParam> list, HashMap<String, String> allSupplier);

    String getSupplier(String supplier);

    void exportExcel(HttpServletResponse response, PmcVerifInfoParam info);

    ResponseData createPmcVerifyInfo(VerifInfoRecord verifInfoRecord, PurchaseOrders order);

    ResponseData pmcVerif(PmcVerifInfoParam param);

    boolean callK3(PmcVerifInfo pmcVerifInfo);

    boolean batchCallK3(List<PmcVerifInfo> pmcVerifInfoList);

    ResponseData batchVerif(List<PmcVerifInfoParam> parmList, String verifyResult);

    ResponseData updateInfo(PmcVerifInfoParam param);

    PageResult<PmcVerifInfoResult> querypmcVerifList(PmcVerifInfoParam param);


    List<SupplierInfoResult> getMatControlPersonBySupplier(String supplier, String supplierCode);


    ResponseData auditAll();


    HashMap<String, String> getAllSupplier();

    ResponseData getSupplierByName(PmcVerifInfoParam param, HashMap<String, String> allSupplier);

    List<PmcVerifInfoResult>  queryList(PmcVerifInfoParam param);

    ResponseData autoAnalyze(PmcAutoAnalyzeParam param);

    @DataSource(name = "erpcloud")
    ResponseData autoAnalyzeByMat(PmcAutoAnalyzeParam param);

    ResponseData batchMergeAction(List<PmcVerifInfoParam> parmList, String verifyResult);

    ResponseData mergeDetails(PmcVerifInfoParam parm);

    ResponseData detailsComit(List<PmcVerifInfoParam> parmList);

    ResponseData planApprovedOrder(PmcVerifInfoParam param);


    /**
     * 获取物料规格型号
     * @param materialCode
     * @return
     */
    String getMatModeSpec(String materialCode);

}
