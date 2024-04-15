package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoExcelParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyReqVO;

import java.util.List;

/**
 * <p>
 * 特殊备货申请信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-24
 */
public interface ISpecialApplyInfoService extends IService<SpecialApplyInfo> {

    PageResult<SpecialApplyInfoResult> queryPage(SpecialApplyInfoReqVO reqVO);
    List<SpecialApplyInfoResult> specialExport(SpecialApplyInfoReqVO param);


    ResponseData update(SpecialApplyInfo applyInfo);

    ResponseData commitBatch(List<SpecialApplyReqVO> reqParamList);

    ResponseData specialVerifySubmit(PurchaseOrdersParam ordersParam);
    ResponseData specialVerifySave(PurchaseOrdersParam ordersParam);


    ResponseData getByPurchaseId(String purchaseId);

    ResponseData checkData(List<SpecialApplyInfoExcelParam> dataList);
}
