package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseEuCountries;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseEuCountriesParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseEuCountriesResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础信息-欧盟国家参数列表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface BaseEuCountriesMapper extends BaseMapper<BaseEuCountries> {

    Page<BaseEuCountriesResult> queryListPage(@Param("page") Page page, @Param("paramCondition") BaseEuCountriesParam paramCondition);

    List<String> euCountry();

    List<String> cnCountry();

    List<String> enCountry();
}
