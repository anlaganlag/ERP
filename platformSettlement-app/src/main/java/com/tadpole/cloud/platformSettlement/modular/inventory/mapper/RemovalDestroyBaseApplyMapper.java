package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyBaseApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpBankAccountParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ErpBankAccountResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author cyt
* @since 2022-05-25
*/
public interface RemovalDestroyBaseApplyMapper extends BaseMapper<RemovalDestroyBaseApply> {

    Page<ErpBankAccountResult> bankAccountList(@Param("page") Page page, @Param("paramCondition") ErpBankAccountParam paramCondition);

    @Select("SELECT * FROM APPLY_CONFIG WHERE APPLY_NAME=#{applyName} AND DEPARTMENT=#{department}")
    ApplyConfigResult getApplyConfig(String applyName, String department);

}
