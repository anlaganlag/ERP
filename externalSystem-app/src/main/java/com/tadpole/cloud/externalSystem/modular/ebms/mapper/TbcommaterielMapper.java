package com.tadpole.cloud.externalSystem.modular.ebms.mapper;

import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.ebms.model.params.TbcommaterielParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    List<TbComMateriel> getWaitUpdateMatList(@Param("updateTimeStart") Date updateTimeStart,@Param("updateTimeEnd") Date updateTimeEnd,@Param("matList") List<String> matList);

    Page<TbComMateriel> listBySpec(@Param("page") Page page, @Param("paramCondition") TbcommaterielParam param);
}
