package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdMoldOpenPfaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdMoldOpenPfaMapper extends BaseMapper<RdMoldOpenPfa> {
    List<RdMoldOpenPfaResult> listMoldOpenPfa(@Param("paramCondition") RdMoldOpenPfaParam param);

    List<RdMoldOpenPfaResult> listMoldOpenPfaArrears(@Param("paramCondition") RdMoldOpenPfaParam param);

    IPage<RdMoldOpenPfaResult> listPage(@Param("page") Page page, @Param("paramCondition") RdMoldOpenPfaParam param);

    RdMoldOpenPfaResult detail(@Param("paramCondition") RdMoldOpenPfaParam param);
}
