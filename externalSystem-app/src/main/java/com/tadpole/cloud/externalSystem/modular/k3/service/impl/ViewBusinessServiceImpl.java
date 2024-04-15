package com.tadpole.cloud.externalSystem.modular.k3.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.api.k3.model.params.results.SupplierQtyAmount;
import com.tadpole.cloud.externalSystem.modular.k3.mapper.ViewSupplierMapper;
import com.tadpole.cloud.externalSystem.modular.k3.service.ViewBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
@Service
public class ViewBusinessServiceImpl   implements ViewBusinessService {
    @Autowired
    ViewSupplierMapper viewSupplierMapper;

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getAgencys() {

        return viewSupplierMapper.selectList(null);
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getSupplierGroup() {
        return viewSupplierMapper.getSupplierGroup();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getProvince() {
        return viewSupplierMapper.getProvince();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getCity(String province) {
        return viewSupplierMapper.getCity(province);
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getDepartment() {
        return viewSupplierMapper.getDepartment();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getPaymentCondition() {
        return viewSupplierMapper.getPaymentCondition();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getSupplierClassify() {
        return viewSupplierMapper.getSupplierClassify();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getCurrency() {
        return viewSupplierMapper.getCurrency();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getTaxRate() {
        return viewSupplierMapper.getTaxRate();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getSettleType() {
        return viewSupplierMapper.getSettleType();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getBankCountry() {
        return viewSupplierMapper.getBankCountry();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getBankDetail(String bankDetail) {
        return viewSupplierMapper.getBankDetail(bankDetail);
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getTaxType() {
        return viewSupplierMapper.getTaxType();
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getInnerSupplier() {
        return viewSupplierMapper.getInnerSupplier();
    }

    @DataSource(name = "k3cloud")
    @Override
    public ViewSupplier getDeptUserId(String account) {
        return viewSupplierMapper.getDeptUserId(account);
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<ViewSupplier> getTrade() {
        return viewSupplierMapper.getTrade();
    }

    @DataSource(name = "k3cloud")
    @Override
    public SupplierQtyAmount getSupplierQtyAmount(String supplierCode) {
        return viewSupplierMapper.getSupplierQtyAmount(supplierCode);
    }

    @DataSource(name = "k3cloud")
    @Override
    public SupplierQtyAmount getSupplierNotAmount(String supplierCode) {
        return viewSupplierMapper.getSupplierNotAmount(supplierCode);
    }
}
