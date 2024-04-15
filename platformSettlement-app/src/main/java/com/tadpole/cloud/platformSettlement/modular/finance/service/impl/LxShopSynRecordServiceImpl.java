package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LxShopSynRecordMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxShopSynRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* <p>
*  服务实现类
* </p>
*
* @author ty
* @since 2022-05-17
*/
@Service
public class LxShopSynRecordServiceImpl extends ServiceImpl<LxShopSynRecordMapper, LxShopSynRecord> implements ILxShopSynRecordService {

    @Resource
    private LxShopSynRecordMapper mapper;

}
