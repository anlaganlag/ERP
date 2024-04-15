package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoV3;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoV3Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 特殊备货申请V3 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
public interface SpecialApplyInfoV3Mapper extends BaseMapper<SpecialApplyInfoV3> {

    IPage<SpecialApplyInfoV3Result> queryListPage(@Param("page") Page page, @Param("paramCondition") SpecialApplyInfoV3Param paramCondition);

    List<SpecialApplyInfoV3Result> queryList(@Param("paramCondition") SpecialApplyInfoV3Param param);

    List<SpecialApplyInfoV3Result> queryList2(@Param("paramCondition") SpecialApplyInfoV3Param param);

    List<SpecialApplyInfoV3Result> uploadValidateData(List<String> mergeFields);
}
