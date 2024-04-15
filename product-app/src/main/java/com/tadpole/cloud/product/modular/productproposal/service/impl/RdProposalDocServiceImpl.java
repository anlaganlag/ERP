package com.tadpole.cloud.product.modular.productproposal.service.impl;

import com.tadpole.cloud.product.api.productproposal.entity.RdProposalDoc;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdProposalDocMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdProposalDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提案-提案管理-设计文档 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Service
public class RdProposalDocServiceImpl extends ServiceImpl<RdProposalDocMapper, RdProposalDoc> implements IRdProposalDocService {

    @Resource
    private RdProposalDocMapper mapper;

}
