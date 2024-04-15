package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdPurchaseRequire;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdPurchaseRequireParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.ProdPurchaseRequireExtentsResult;

import java.util.List;

/**
 * <p>
 * 新品采购申请 服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-20
 */
public interface IProdPurchaseRequireService extends IService<ProdPurchaseRequire> {


    ResponseData purchaseCheck(ProdPurchaseRequireParam prodPurchaseRequireParam);


    ResponseData purchasePmcCheck(ProdPurchaseRequireParam param);

    ResponseData batchUpdate(ProdPurchaseRequireParam prodPurchaseRequireParam);

    ResponseData batchComit(ProdPurchaseRequireParam param);

    ResponseData pmcBatchComit(ProdPurchaseRequireParam param);

    ResponseData syncK3(ProdPurchaseRequireParam param);

    PageResult<ProdPurchaseRequireExtentsResult> applyListPage(ProdPurchaseRequireParam reqVO);

    List<ProdPurchaseRequireExtentsResult> exportExcel(ProdPurchaseRequireParam param);
}
