package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsFeePaymentDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 物流费付款明细 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
public interface LsLogisticsFeePaymentDetailMapper extends BaseMapper<LsLogisticsFeePaymentDetail> {

    /**
     * 列表查询
     * @param param
     * @return
     */
    List<LsLogisticsFeePaymentDetailResult> queryList(@Param("param") LsLogisticsFeePaymentDetailParam param);

    /**
     * 列表合计
     * @param param
     * @return
     */
    LsLogisticsFeePaymentDetailResult queryListTotal(@Param("param") LsLogisticsFeePaymentDetailParam param);

}
