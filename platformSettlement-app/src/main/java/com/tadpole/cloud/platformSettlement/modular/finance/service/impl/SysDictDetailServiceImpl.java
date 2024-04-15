package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SysDictDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISysDictDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 用户字典明细 服务实现类
* </p>
*
* @author gal
* @since 2021-11-11
*/
@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements ISysDictDetailService {

    @Autowired
    private SysDictDetailMapper mapper;

}
