package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdRefRegist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdRefRegistResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-退款登记 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdRefRegistMapper extends BaseMapper<RdRefRegist> {
    List<RdRefRegistResult> listRefRegist(@Param("paramCondition") RdRefRegistParam param);

    List<RdRefRegistResult> listPage(@Param("paramCondition") RdRefRegistParam param);

    List<RdRefRegistResult> listPageDetail(@Param("paramCondition") RdRefRegistParam param);

    List<RdRefRegistResult> listPageStatisticsRefRegist();


}
