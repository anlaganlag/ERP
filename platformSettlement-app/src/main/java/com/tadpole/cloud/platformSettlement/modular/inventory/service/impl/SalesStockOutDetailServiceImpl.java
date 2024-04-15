package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOutDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.SalesStockOutDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesStockOutDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author gal
* @since 2021-12-08
*/
@Service
public class SalesStockOutDetailServiceImpl extends ServiceImpl<SalesStockOutDetailMapper, SalesStockOutDetail> implements ISalesStockOutDetailService {

    @Autowired
    private SalesStockOutDetailMapper mapper;

}
