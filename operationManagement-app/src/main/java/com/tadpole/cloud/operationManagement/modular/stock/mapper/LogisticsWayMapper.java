package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsWay;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsWayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsWayResult;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 设置物流方式 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-09-13
*/
public interface LogisticsWayMapper extends BaseMapper<LogisticsWay> {

    IPage<LogisticsWayResult> queryListPage(@Param("page") Page page, @Param("paramCondition") LogisticsWayParam paramCondition);
}
