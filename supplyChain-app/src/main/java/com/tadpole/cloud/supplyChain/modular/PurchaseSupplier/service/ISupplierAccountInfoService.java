package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service;

import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierAccountInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierAccountInfoResult;

import java.util.List;

/**
 * <p>
 * 供应商-供应商信息 服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface ISupplierAccountInfoService extends IService<SupplierAccountInfo> {

    List<SupplierAccountInfoResult> listPage(SupplierAccountInfoParam param);

    void edit(SupplierAccountInfoParam param);
}
