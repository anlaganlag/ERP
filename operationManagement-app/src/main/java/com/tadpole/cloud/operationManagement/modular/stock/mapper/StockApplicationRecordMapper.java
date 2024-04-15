package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApplicationRecordParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApplicationRecordAnalysisResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApplicationRecordResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockApplicationRecordMapper extends BaseMapper<StockPurchaseOrders> {

    IPage<StockApplicationRecordResult> queryListPage(@Param("page") Page page, @Param("paramCondition") StockApplicationRecordParam paramCondition);

    List<StockApplicationRecordAnalysisResult> queryAnalysisResultExcel(@Param("paramCondition") StockApplicationRecordParam paramCondition);
}
