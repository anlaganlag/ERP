package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPmcVerifK3;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPmcVerifK3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockPmcVerifK3Result;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * PMC审核调用k3记录信息 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-09-07
*/
public interface StockPmcVerifK3Mapper extends BaseMapper<StockPmcVerifK3> {

    IPage<StockPmcVerifK3Result> queryPage(@Param("page") Page page, @Param("reqVO") StockPmcVerifK3Param reqVO);
}
