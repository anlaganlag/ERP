package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdCustProductDet2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductDet2Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 提案-定品明细 Mapper接口
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
public interface RdCustProductDet2Mapper extends BaseMapper<RdCustProductDet2> {

    List<RdCustProductDet2Result> listCustSample(@Param("paramCondition")  RdCustProductParam param);
}
