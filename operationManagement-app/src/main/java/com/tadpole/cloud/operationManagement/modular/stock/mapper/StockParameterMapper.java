package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockParameterResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 备货通用参数 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
public interface StockParameterMapper extends BaseMapper<StockParameter> {
    @Select("select * from stock_parameter")
    List<StockParameterResult> customPageList(StockParameterParam param);
}
