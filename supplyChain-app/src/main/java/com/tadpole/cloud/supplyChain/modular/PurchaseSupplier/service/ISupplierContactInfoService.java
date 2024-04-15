package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierContactInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierAccountInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierContactInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierContactInfoResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierInfoResult;

import java.util.List;

/**
 * <p>
 * 供应商-联系人信息 服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface ISupplierContactInfoService extends IService<SupplierContactInfo> {

    List<SupplierContactInfo> listPage(SupplierContactInfoParam param);

    void edit(SupplierContactInfoParam param);

    void add(SupplierContactInfoParam param);
}
