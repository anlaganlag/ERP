package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBoxDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxDetailParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbPackBoxDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackBoxDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子明细信息服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Service
public class LsBtbPackBoxDetailServiceImpl extends ServiceImpl<LsBtbPackBoxDetailMapper, LsBtbPackBoxDetail> implements ILsBtbPackBoxDetailService {

    @Resource
    private LsBtbPackBoxDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public List<LsBtbPackBoxDetail> queryList(LsBtbPackBoxDetailParam param) {
        return mapper.queryList(param);
    }

}
