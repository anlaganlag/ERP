package com.tadpole.cloud.externalSystem.modular.mabang.service;

import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;

public interface IMabangRequstService {

    MabangResult postRequset(MabangHeadParm mabangHeadParm);

    MabangResult getOrderList(MabangHeadParm mabangHeadParm);
    MabangResult getOrderListNew(MabangHeadParm mabangHeadParm);

    MabangResult getPurOrderList(MabangHeadParm mabangHeadParm);

    MabangResult getSkuStockQty(MabangHeadParm mabangHeadParm);


    MabangResult getShopList(MabangHeadParm mabangHeadParm);

    MabangResult purchaseOrderAdd(MabangHeadParm mabangHeadParm);

    MabangResult purchaseOrderModify(MabangHeadParm mabangHeadParm);

    MabangResult warehouseList(MabangHeadParm mabangHeadParm);

    MabangResult createAllocationWarehouse(MabangHeadParm mabangHeadParm);

    MabangResult getPurchaseOrderList(MabangHeadParm mabangHeadParm);

    MabangResult returnOrderList(MabangHeadParm mabangHeadParm);

    MabangResult stockDoSearchSkuList(MabangHeadParm mabangHeadParm);

    MabangResult stockDoAddStock(MabangHeadParm mabangHeadParm);

    MabangResult stockDoChangeStock(MabangHeadParm mabangHeadParm);

    MabangResult stockChangeGrid(MabangHeadParm mabangHeadParm);

    MabangResult sysGetEmployeeList(MabangHeadParm mabangHeadParm);

    MabangResult orderUpdate(MabangHeadParm mabangHeadParm);

    MabangResult bindEmployee(MabangHeadParm mabangHeadParm);
}
