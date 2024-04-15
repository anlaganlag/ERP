package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAddPurchaseOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.PurchaseAddParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrder2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrderResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 马帮新增采购订单 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
public interface IMabangAddPurchaseOrderService extends IService<MabangAddPurchaseOrder> {


    PageResult<MabangAddPurchaseOrderResult> list(MabangAddPurchaseOrderParam param);


    PageResult<K3TransferMabangItemResult> display(String billNo, String groupId);


    //@DataSource(name = "external")
    //@Transactional(rollbackFor = Exception.class)
    ResponseData add(String summaryId, List<K3TransferItem> transferItemList);

    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    Integer add2(String summaryId, List<K3TransferItem> transferItemList);

    //    @Transactional(rollbackFor = Exception.class)
    PurchaseAddParm createPurchaseOrder(List<K3TransferItem> itemList);

    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    ResponseData anitAudit (K3TransferMabangSummary summary);

    PageResult<MabangAddPurchaseOrder2Result> mergeList(MabangAddPurchaseOrderParam param);


    List<MabangAddPurchaseOrder2Result> exportExcel(MabangAddPurchaseOrderParam param);

    /**
     * k3物料有可用库存，自动产生销售出库，然后马帮自动添加采购单
     */
    void createMabangPurchaseOrder();

    /**
     * 创建马帮的采购订单
     * @param saleOutOrderK3
     * @param itemList
     * @return
     */
     ResponseData createMabangPurchaseOrder(SaleOutOrderK3 saleOutOrderK3, List<SaleOutOrderItemK3> itemList);

    PurchaseAddParm autoCreatePurchaseOrder(SaleOutOrderK3  saleOutOrderK3,List<SaleOutOrderItemK3> itemList);

    /**
     * 检查马帮采购入库是否成功
     * @param fBillNo K3单据编号
     * @return
     */
    boolean checkSyncException(String fBillNo);
}
