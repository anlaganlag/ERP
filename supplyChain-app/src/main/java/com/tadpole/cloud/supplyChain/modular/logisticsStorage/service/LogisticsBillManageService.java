package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.LogisticsSignParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LogisticsBillManageService  {
    /**
     *
     * 获取通关数据
     * @param lhrOddNumb
     * @return
     */
    List<TbShipemtListClearancModel> getClearanceData(String lhrOddNumb);

    /**
     * 获取通关所需箱件信息
     * @param lhrOddNumb
     * @return
     */
    List<TbLogisticsPackingListDet1Result> getClearanceBoxInfoData(String lhrOddNumb);

    /**
     * 删除物流单
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    ResponseData delLogisticsOrder(String lhrCode, String lhrOddNumb);

    /**
     * 退回物流单
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    ResponseData returnLogisticsOrder(String lhrCode, String lhrOddNumb);

    /**
     * 更新物流单信息
     * @param request
     * @return
     */
    ResponseData updateLogisticsOrder(TbLogisticsListToHeadRouteParam request);

    /**
     * 签收
     * @param signParam
     * @return
     */
    ResponseData sign(LogisticsSignParam signParam);

    /**
     * 导入预估费用
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);
}
