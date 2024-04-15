package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsEssentialFactor;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsEssentialFactorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsEssentialFactorResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报关要素 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgCustomsEssentialFactorMapper extends BaseMapper<TgCustomsEssentialFactor> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsEssentialFactorResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsEssentialFactorParam param);

    /**
     * 报关要素下拉
     * @return
     */
    List<BaseSelectResult> essentialFactorSelect();

    /**
     * 删除报关要素引用的要素数据，并更新审核状态为反审：2
     * @param customsCode
     * @param name
     */
    void updateProductEssentialFactor(@Param("customsCode") String customsCode, @Param("name") String name);
}
