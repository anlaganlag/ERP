package com.tadpole.cloud.externalSystem.modular.k3.service;

import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.api.k3.model.params.results.SupplierQtyAmount;

import java.util.List;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
public interface ViewBusinessService {
    List<ViewSupplier> getAgencys();

    List<ViewSupplier> getSupplierGroup();

    List<ViewSupplier> getProvince();

    List<ViewSupplier> getCity(String province);

    List<ViewSupplier> getDepartment();

    List<ViewSupplier> getPaymentCondition();

    List<ViewSupplier> getSupplierClassify();

    List<ViewSupplier> getCurrency();

    List<ViewSupplier> getTaxRate();

    List<ViewSupplier> getSettleType();

    List<ViewSupplier> getBankCountry();

    List<ViewSupplier> getBankDetail(String bankDetail);

    List<ViewSupplier> getTaxType();

    List<ViewSupplier> getInnerSupplier();

    ViewSupplier getDeptUserId(String account);

    List<ViewSupplier> getTrade();

    SupplierQtyAmount getSupplierQtyAmount(String supplierCode);

    SupplierQtyAmount getSupplierNotAmount(String supplierCode);
}
