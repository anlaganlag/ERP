package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdProposal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;

/**
 * <p>
 * 提案-提案管理 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
public interface IRdProposalService extends IService<RdProposal> {

    ResponseData addOrEdit(RdProposalParam param);

}
