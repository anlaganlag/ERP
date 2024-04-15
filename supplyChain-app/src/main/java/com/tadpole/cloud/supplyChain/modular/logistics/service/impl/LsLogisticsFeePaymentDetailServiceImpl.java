package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsFeePaymentDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsFeePaymentDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsFeePaymentDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 物流费付款明细 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@Service
public class LsLogisticsFeePaymentDetailServiceImpl extends ServiceImpl<LsLogisticsFeePaymentDetailMapper, LsLogisticsFeePaymentDetail> implements ILsLogisticsFeePaymentDetailService {

    @Resource
    private LsLogisticsFeePaymentDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsFeePaymentDetailResult> queryList(LsLogisticsFeePaymentDetailParam param) {
        return mapper.queryList(param);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryListTotal(LsLogisticsFeePaymentDetailParam param) {
        return ResponseData.success(mapper.queryListTotal(param));
    }

}
