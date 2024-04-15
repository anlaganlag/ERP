package com.tadpole.cloud.product.modular.productplan.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalExtentResult;

import java.util.List;

/**
 * <p>
 * 预提案 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
public interface IRdPreProposalService extends IService<RdPreProposal> {
    PageResult<RdPreProposalExtentResult> listPage(RdPreProposalParam param);

    ResponseData addOrEdit(RdPreProposalParam param);

    RdPreProposalExtentResult detail(RdPreProposalParam param);

    ResponseData archive(RdPreProposalParam param);

    ResponseData revoke(RdPreProposalParam param);

    ResponseData apply(RdPreProposalParam param);

    List<RdPreProposalExtentResult> listPageFebk(RdPreProposalParam param);

    ResponseData feedBack(RdPreProposalParam param);

    ResponseData examine(RdPreProposalParam param);

    ResponseData approve(RdPreProposalParam param);
}
