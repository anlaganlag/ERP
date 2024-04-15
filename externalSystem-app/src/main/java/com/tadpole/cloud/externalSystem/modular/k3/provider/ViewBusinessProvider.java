package com.tadpole.cloud.externalSystem.modular.k3.provider;

import com.tadpole.cloud.externalSystem.api.k3.ViewBusinessApi;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.api.k3.model.params.results.SupplierQtyAmount;
import com.tadpole.cloud.externalSystem.modular.k3.service.ViewBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: ERP业务数据读取
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
@RestController
public class ViewBusinessProvider implements ViewBusinessApi {

    @Autowired
    ViewBusinessService viewBusinessService;
    @Override
    public List<ViewSupplier> getAgencys() {
        return viewBusinessService.getAgencys();

    }

    @Override
    public List<ViewSupplier> getSupplierGroup() {
        return viewBusinessService.getSupplierGroup();
    }


    @Override
    public ViewSupplier getDeptUserId(String account) {
        return viewBusinessService.getDeptUserId(account);
    }

    @Override
    public SupplierQtyAmount getSupplierQtyAmount(String supplierCode) {
        return viewBusinessService.getSupplierQtyAmount(supplierCode);
    }

    @Override
    public SupplierQtyAmount getSupplierNotAmount(String supplierCode) {
        return viewBusinessService.getSupplierNotAmount(supplierCode);
    }

}
