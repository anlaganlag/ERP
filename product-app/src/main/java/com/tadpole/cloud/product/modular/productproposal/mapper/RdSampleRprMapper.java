package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleRpr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleRprResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-退货款记录 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSampleRprMapper extends BaseMapper<RdSampleRpr> {
    List<RdSampleRprResult> listSampleRpr(@Param("paramCondition") RdSampleRprParam param);
}
