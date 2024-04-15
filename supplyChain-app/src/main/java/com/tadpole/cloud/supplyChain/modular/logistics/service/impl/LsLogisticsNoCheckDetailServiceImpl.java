package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoCheckDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsNoCheckDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsNoCheckDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 物流单对账明细 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Service
public class LsLogisticsNoCheckDetailServiceImpl extends ServiceImpl<LsLogisticsNoCheckDetailMapper, LsLogisticsNoCheckDetail> implements ILsLogisticsNoCheckDetailService {

    @Resource
    private LsLogisticsNoCheckDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsNoCheckDetailResult> queryList(LsLogisticsNoCheckDetailParam param) {
        return mapper.queryList(param);
    }

}
