package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangSummary;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangAllocationWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.CreateAllocationWarehouseParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAllocationWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouse2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 马帮分仓调拨单 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
public interface IMabangAllocationWarehouseService extends IService<MabangAllocationWarehouse> {

    ResponseData create(String summaryId, List<K3TransferItem> mabangOut);

    CreateAllocationWarehouseParm createAllocationWarehouse(MabangWarehouse src, MabangWarehouse target, List<K3TransferItem> itemList,String idStr);

    PageResult<MabangAllocationWarehouseResult> list(MabangAllocationWarehouseParam param);


    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    ResponseData anitAudit(K3TransferMabangSummary summary);

    PageResult<K3TransferMabangItemResult> display(String billNo, String allocationCode);

    PageResult<MabangAllocationWarehouse2Result> mergeList(MabangAllocationWarehouseParam param);

    List<MabangAllocationWarehouse2Result> exportExcel(MabangAllocationWarehouseParam param);
}
