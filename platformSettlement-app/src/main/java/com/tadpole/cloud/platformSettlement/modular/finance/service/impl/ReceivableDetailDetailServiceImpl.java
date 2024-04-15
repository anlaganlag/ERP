package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ReceivableDetailDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ReceivableDetailDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReceivableDetailDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 应收明细详情 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class ReceivableDetailDetailServiceImpl extends ServiceImpl<ReceivableDetailDetailMapper, ReceivableDetailDetail> implements IReceivableDetailDetailService {

    @Autowired
    private ReceivableDetailDetailMapper mapper;

}
