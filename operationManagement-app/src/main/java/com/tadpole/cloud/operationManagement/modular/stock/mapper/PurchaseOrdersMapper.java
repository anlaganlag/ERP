package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrders2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.*;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.PurchaseOrdersReqVO;
import feign.Param;

import java.util.List;

/**
* <p>
    * 采购订单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
public interface PurchaseOrdersMapper extends BaseMapper<PurchaseOrders> {

    IPage<PurchaseOrdersResult> queryPage(@Param("page") Page page, @Param("reqVO") OperApplyInfoReqVO reqVO);

    List<PurchaseOrdersExcelResult> queryByCondition(@Param("reqVO") OperApplyInfoReqVO reqVO);

    IPage<PurchaseOrdersVerifyResult> applyRecord(@Param("page") Page page, @Param("reqVO") OperApplyInfoReqVO reqVO);


    IPage<PurchaseOrdersVerifyResult> applyRecordPurchaseNo(@Param("page") Page page, @Param("reqVO") OperApplyInfoReqVO reqVO);


    IPage<PurchaseOrdersResult> queryplanVerifList(@Param("page") Page page,@Param("reqVO") PurchaseOrdersParam reqVO);

    IPage<PlanPurchaseOrdersResult> planVerifList(@Param("page") Page page, @Param("reqVO") PurchaseOrdersParam reqVO);

    IPage<PlanPurchaseOrders2Result> planVerifList2(@Param("page") Page page, @Param("req") PurchaseOrders2Param req);

    //    List<PurchaseOrders> queryOrderList(@Param("operApplyInfoReqVO") OperApplyInfoReqVO operApplyInfoReqVO);
    List<PurchaseOrders> queryOrderList(@Param("oper") String oper);

    IPage<OperApplyInfoResult> recordList(@Param("page") Page pageContext, @Param("reqVO") List<String> reqVO);

    IPage<PurchaseOrdersVerifyResult> purchaseOrderRecord(@Param("page") Page page,  @Param("reqVO") PurchaseOrdersReqVO reqVO);


    void flashLastOrderTime();
    IPage<PurchaseOrdersResult> queryPageV3(@Param("page") Page page,  @Param("reqVO") OperApplyInfoReqV3 reqVO);

}
