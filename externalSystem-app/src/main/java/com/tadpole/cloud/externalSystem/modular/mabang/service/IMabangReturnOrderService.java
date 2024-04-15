package com.tadpole.cloud.externalSystem.modular.mabang.service;


import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangReturnOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 马帮退货单列表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-24
 */
public interface IMabangReturnOrderService extends IService<MabangReturnOrder> {


    List<MabangReturnOrderResult> exportList(MabangReturnOrderParam param);

    void add(MabangResult param);


    PageResult<MabangReturnOrderResult> listBySpec(MabangReturnOrderParam param);

    ResponseData getReturnOrderList(OrderParm orderParm);

    ResponseData queryPlatformNames();
    ResponseData queryShopName();

    ResponseData querySite();

    ResponseData queryStatus();


    ResponseData getHisReturnOrderList(OrderParm orderParm);
}
