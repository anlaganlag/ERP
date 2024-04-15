package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MaterialResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("SELECT   DISTINCT MATERIAL_CODE  FROM MCMS_BASE_DATA.RP_MATERIAL")
    List<String> distinctMatList();

}
