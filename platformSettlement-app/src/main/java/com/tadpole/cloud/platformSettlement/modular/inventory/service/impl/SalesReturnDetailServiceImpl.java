package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturnDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesReturnDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesReturnDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 销售退货列表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-10
*/
@Service
public class SalesReturnDetailServiceImpl extends ServiceImpl<SalesReturnDetailMapper, SalesReturnDetail> implements ISalesReturnDetailService {

    @Autowired
    private SalesReturnDetailMapper mapper;

}
