package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoHistory;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 特殊备货申请信息历史记录 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
public interface SpecialApplyInfoHistoryMapper extends BaseMapper<SpecialApplyInfoHistory> {

    IPage<OperApplyInfoResult> recordSpecialHisList(@Param("page") Page page, @Param("reqVO") List<String> reqVO);


}
