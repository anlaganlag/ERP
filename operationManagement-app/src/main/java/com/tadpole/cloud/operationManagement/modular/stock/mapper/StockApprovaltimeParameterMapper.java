package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockApprovaltimeParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApprovaltimeParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 日常备货流程审核节点参数设置 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
public interface StockApprovaltimeParameterMapper extends BaseMapper<StockApprovaltimeParameter> {


    @Select("SELECT *  FROM stock_approvaltime_parameter")
    List<StockApprovaltimeParameterResult> customPageList(StockApprovaltimeParameterParam param);
}
