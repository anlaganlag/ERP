package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.ApplyConfig;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ApplyConfigParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author cyt
* @since 2022-05-24
*/
public interface ApplyConfigMapper extends BaseMapper<ApplyConfig> {

    Page<ApplyConfigResult> list(@Param("page") Page page, @Param("paramCondition") ApplyConfigParam paramCondition);

}
