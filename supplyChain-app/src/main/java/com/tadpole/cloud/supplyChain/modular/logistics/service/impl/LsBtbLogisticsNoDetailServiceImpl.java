package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNoDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbLogisticsNoDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbLogisticsNoDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  BTB物流单明细服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@Service
public class LsBtbLogisticsNoDetailServiceImpl extends ServiceImpl<LsBtbLogisticsNoDetailMapper, LsBtbLogisticsNoDetail> implements ILsBtbLogisticsNoDetailService {

    @Resource
    private LsBtbLogisticsNoDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public List<LsBtbLogisticsNoDetailResult> queryList(LsBtbLogisticsNoDetailParam param) {
        return mapper.queryList(param);
    }

}
