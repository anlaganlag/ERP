package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoRecordParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsNoRecordMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsNoRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 物流单费用操作记录 服务实现类
 * </p>
 *
 * @author ty
 * @since 2024-03-19
 */
@Service
public class LsLogisticsNoRecordServiceImpl extends ServiceImpl<LsLogisticsNoRecordMapper, LsLogisticsNoRecord> implements ILsLogisticsNoRecordService {

    @Resource
    private LsLogisticsNoRecordMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLogisticsNoRecordParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

}
