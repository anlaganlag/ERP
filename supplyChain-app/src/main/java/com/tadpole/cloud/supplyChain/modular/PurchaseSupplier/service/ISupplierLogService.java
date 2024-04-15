package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierLog;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierLogParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierLogResult;

import java.util.List;

/**
 * <p>
 * 供应商-日志 服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface ISupplierLogService extends IService<SupplierLog> {

    List<SupplierLogResult> listPage(SupplierLogParam param);
}
