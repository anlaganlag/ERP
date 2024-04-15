package com.tadpole.cloud.operationManagement.modular.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TbComMateriel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TbcommaterielParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author gal
* @since 2022-10-25
*/
public interface TbcommaterielMapper extends BaseMapper<TbComMateriel> {

    Page<TbComMateriel> listBySpec(@Param("page") Page page, @Param("paramCondition") TbcommaterielParam param);
}
