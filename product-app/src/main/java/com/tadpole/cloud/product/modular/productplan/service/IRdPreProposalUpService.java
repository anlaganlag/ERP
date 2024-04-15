package com.tadpole.cloud.product.modular.productplan.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposalUp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalUpParam;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;

import java.util.List;

/**
 * <p>
 * 预提案-改良 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
public interface IRdPreProposalUpService extends IService<RdPreProposalUp> {
    int add(List<RdPreProposalUpParam> params);

    int delete(RdPreProposalUpParam param);

    List<RdPreProposalUpResult> detail(RdPreProposalUpParam param);
}
