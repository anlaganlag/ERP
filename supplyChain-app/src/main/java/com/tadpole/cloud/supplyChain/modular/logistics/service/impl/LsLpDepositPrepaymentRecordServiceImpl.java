package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepaymentRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentRecordParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLpDepositPrepaymentRecordMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLpDepositPrepaymentRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 物流商押金&预付操作记录 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
@Service
public class LsLpDepositPrepaymentRecordServiceImpl extends ServiceImpl<LsLpDepositPrepaymentRecordMapper, LsLpDepositPrepaymentRecord> implements ILsLpDepositPrepaymentRecordService {

    @Resource
    private LsLpDepositPrepaymentRecordMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLpDepositPrepaymentRecordParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

}
