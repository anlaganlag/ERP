package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburseDet;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdExpenseReimburseDetMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdExpenseReimburseDetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 提案-研发费报销明细 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@Service
public class RdExpenseReimburseDetServiceImpl extends ServiceImpl<RdExpenseReimburseDetMapper, RdExpenseReimburseDet> implements IRdExpenseReimburseDetService {

    @Resource
    private RdExpenseReimburseDetMapper mapper;

}
