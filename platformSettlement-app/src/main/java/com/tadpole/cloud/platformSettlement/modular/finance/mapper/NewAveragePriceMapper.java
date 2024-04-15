package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.NewAveragePrice;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NewAveragePriceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NewAveragePriceResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 新核算库存平均单价表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface NewAveragePriceMapper extends BaseMapper<NewAveragePrice> {

    Page<NewAveragePriceResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") NewAveragePriceParam param);

    List<NewAveragePriceResult> queryList(@Param("paramCondition") NewAveragePriceParam param);

    List<NewAveragePriceParam> confirmAndDeleteBatch(@Param("paramCondition") NewAveragePriceParam param);
}
