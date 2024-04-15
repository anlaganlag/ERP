package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MaterialPriceInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MaterialPriceInfoParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MaterialPriceInfoResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 物料价格信息 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-05-06
*/
public interface MaterialPriceInfoMapper extends BaseMapper<MaterialPriceInfo> {

    Page<MaterialPriceInfoResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") MaterialPriceInfoParam paramCondition);
}
