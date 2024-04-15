package com.tadpole.cloud.externalSystem.api.k3;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.k3.model.params.ShipmentLabelApplyK3Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/K3CloudApi")
public interface K3CloudWebApi {
    /**
     * 删除调拨单
     * @param billNo 调拨单号
     * @return
     */
    @RequestMapping(value = "/delTranferByBillNo", method = RequestMethod.POST)
    public boolean delTranferByBillNo(@RequestParam String billNo);

    /**
     * 调拨申请单 保存
     * @param applyParam
     * @return
     */
    @RequestMapping(value = "/tranferApplySave", method = RequestMethod.POST)
    public ResponseData tranferApplySave( @RequestBody ShipmentLabelApplyK3Param applyParam);


}
