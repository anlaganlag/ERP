package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.LogisticsSignParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;

import java.util.List;


public interface LogisticsStorageTimedTaskService {

    /**
     * Amazon在途库存报表(新增规则：判断货件实际状态为已完成、当前时间往前推6个月、替换接收报告数据)
     * @return
     */
    ResponseData analysisAmazonInGoodsQtyNewV4();

    ResponseData syncTbReceivedInvenrotyAnalysisV2();

    ResponseData updateBscOverseasWayShipmentStatusNew();

    /**
     * 更新发货清单Amazon接收状态
     * @return
     */
    ResponseData updateBscOverseasWayStatus();

}
