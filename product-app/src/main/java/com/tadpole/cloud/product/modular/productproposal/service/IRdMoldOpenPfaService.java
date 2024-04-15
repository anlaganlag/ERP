package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdMoldOpenPfaResult;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdMoldOpenPfaService extends IService<RdMoldOpenPfa> {
    ResponseData addOrEdit(RdMoldOpenPfaParam param);

    PageResult<RdMoldOpenPfaResult> listPage(RdMoldOpenPfaParam param);

    List<RdMoldOpenPfaResult> listMoldOpenPfa(RdMoldOpenPfaParam param);

    List<RdMoldOpenPfaResult> listMoldOpenPfaArrears(RdMoldOpenPfaParam param);

    ResponseData statisticsMoldOpenPfa(RdMoldOpenPfaParam param);

    ResponseData createAndAppMoldOpenPfa(List<RdMoldOpenPfaParam> params);

    ResponseData reviewMoldOpenPfa(RdMoldOpenPfaParam param);

    ResponseData printMoldOpenPfa(RdMoldOpenPfaParam param);

    ResponseData listAccountMgtPersonal() throws Exception;

    ResponseData listCompanyAccount() throws Exception;

    ResponseData payMoldOpenPfa(RdMoldOpenPfaParam param);

    ResponseData uploadMoldOpenPfa(RdMoldOpenPfaParam param);

    ResponseData apprMoldOpenPfa(RdMoldOpenPfaParam param);

    RdMoldOpenPfaResult detail(RdMoldOpenPfaParam param);
}
