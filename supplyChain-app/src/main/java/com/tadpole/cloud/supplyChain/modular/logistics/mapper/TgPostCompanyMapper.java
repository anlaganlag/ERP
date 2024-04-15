package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgPostCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgPostCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgPostCompanyResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 发货公司 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgPostCompanyMapper extends BaseMapper<TgPostCompany> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgPostCompanyResult> queryPage(@Param("page") Page page, @Param("param") TgPostCompanyParam param);

    /**
     * 发货公司名称下拉
     * @return
     */
    List<BaseSelectResult> postCompanyNameSelect();
}
