package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SeasonParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SeasonParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SeasonParameterVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 季节系数参数表 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
public interface SeasonParameterMapper extends BaseMapper<SeasonParameter> {

    Page<SeasonParameterResult> queryList(@Param("page")  Page page, @Param("reqVO") SeasonParameterVO reqVO);

    List<SeasonParameterResult> exportExcel(@Param("reqVO") SeasonParameterVO reqVO);
    void saveSeaBatch(@Param("reqVO") List<SeasonParameter> reqVO);



}
