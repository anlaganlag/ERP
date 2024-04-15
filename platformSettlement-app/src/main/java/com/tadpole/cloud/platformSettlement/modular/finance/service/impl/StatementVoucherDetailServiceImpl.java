package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucherDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StatementVoucherDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementVoucherDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 收入记录表凭证明细 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class StatementVoucherDetailServiceImpl extends ServiceImpl<StatementVoucherDetailMapper, StatementVoucherDetail> implements IStatementVoucherDetailService {

    @Autowired
    private StatementVoucherDetailMapper mapper;

}
