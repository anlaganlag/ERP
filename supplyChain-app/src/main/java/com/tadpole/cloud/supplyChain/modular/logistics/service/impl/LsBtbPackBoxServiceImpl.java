package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBox;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbPackBoxMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackBoxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子信息服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Service
public class LsBtbPackBoxServiceImpl extends ServiceImpl<LsBtbPackBoxMapper, LsBtbPackBox> implements ILsBtbPackBoxService {

    @Resource
    private LsBtbPackBoxMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public List<LsBtbPackBox> queryList(LsBtbPackBoxParam param) {
        return mapper.queryList(param);
    }

}
