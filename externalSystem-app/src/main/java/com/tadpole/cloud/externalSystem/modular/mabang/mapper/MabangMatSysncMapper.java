package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangMatSync;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangMatSyncParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangMatSyncResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* <p>
    * 马帮物料同步记录表 Mapper 接口
    * </p>
*
* @author gal
* @since 2022-10-24
*/
public interface MabangMatSysncMapper extends BaseMapper<MabangMatSync> {

   List<MabangWarehouse>  getNewWarehouseList(@Param("creatStartDate") Date creatStartDate);

   IPage<MabangMatSyncResult> listPage(@Param("pageContext") Page pageContext, @Param("paramCondition") MabangMatSyncParam paramCondition);
}
