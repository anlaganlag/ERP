package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgImportCompany;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgImportCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgEbmsCompanyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgImportCompanyResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 进口商 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgImportCompanyMapper extends BaseMapper<TgImportCompany> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgImportCompanyResult> queryPage(@Param("page") Page page, @Param("param") TgImportCompanyParam param);

    /**
     * 进口商名称下拉
     * @return
     */
    List<BaseSelectResult> inCompanyNameSelect();

    /**
     * 实体公司下拉
     * @param comTaxType 税务类型
     * @return
     */
    List<TgEbmsCompanyResult> companySelect(@Param("comTaxType") String comTaxType);
}
