package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MaterialResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
* 物料信息表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface MaterialMapper extends BaseMapper<Material> {

    MaterialResult getMaterial(String materialCode);

}
