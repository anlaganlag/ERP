package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdCustProductDet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-定品明细 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
public interface RdCustProductDetMapper extends BaseMapper<RdCustProductDet> {
    List<RdCustProductDetResult> listCustProductDet(@Param("paramCondition") RdCustProductParam param);

    List<RdCustProductDetResult> listCustSample(@Param("paramCondition") RdCustProductParam param);
}
