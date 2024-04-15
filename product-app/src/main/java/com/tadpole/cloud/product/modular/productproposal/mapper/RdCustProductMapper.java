package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdCustProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdMoldOpenPfaResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-定品 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
public interface RdCustProductMapper extends BaseMapper<RdCustProduct> {
    List<RdCustProductResult> listPage(@Param("paramCondition") RdCustProductParam param);

    List<RdCustProductResult> detail(@Param("paramCondition") RdCustProductParam param);

}
