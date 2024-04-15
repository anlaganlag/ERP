package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierInfoResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 供应商-供应商信息 服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
public interface ISupplierInfoService extends IService<SupplierInfo> {

    PageResult<SupplierInfoResult> profileList(SupplierInfoParam param);


    List<SupplierInfoResult> supplierList(SupplierInfoParam param);

    ResponseData profileAdd(SupplierInfoParam param);

    ResponseData profileUpdate(SupplierInfoParam param) throws IllegalAccessException;

    ResponseData profileSubmit(SupplierInfoParam param) throws IllegalAccessException;

    void departmentCheck(SupplierInfoParam param);

    void qualificationCheck(SupplierInfoParam param);

    ResponseData review(SupplierInfoParam param) throws Exception;

    ResponseData syncSupplierToErp() throws Exception;

    List<Map<String, Object>>  supSelect();
}
