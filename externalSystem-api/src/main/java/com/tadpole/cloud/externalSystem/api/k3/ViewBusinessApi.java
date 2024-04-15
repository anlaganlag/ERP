package com.tadpole.cloud.externalSystem.api.k3;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSONArray;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.api.k3.model.params.results.SupplierQtyAmount;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2023/10/20 <br>
 * @description: ERP视图数据查询
 */
@RequestMapping("/viewBusinessApi")
public interface ViewBusinessApi {

    @RequestMapping(value = "/getAgencys", method = RequestMethod.GET)
    List<ViewSupplier> getAgencys();

    @RequestMapping(value = "/getSupplierGroup", method = RequestMethod.GET)
    List<ViewSupplier> getSupplierGroup();

    @RequestMapping(value = "/getDeptUserId", method = RequestMethod.POST)
    ViewSupplier getDeptUserId(@RequestParam String account);

    @RequestMapping( value = "/getSupplierQtyAmount", method = RequestMethod.GET)
    SupplierQtyAmount getSupplierQtyAmount(String supplierCode);

    @RequestMapping( value = "/getSupplierNotAmount", method = RequestMethod.GET)
    SupplierQtyAmount getSupplierNotAmount(String supplierCode);
}
