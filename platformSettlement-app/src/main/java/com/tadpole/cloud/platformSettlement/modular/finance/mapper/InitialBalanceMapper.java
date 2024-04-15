package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.InitialBalance;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InitialBalanceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InitialBalanceResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 设置期初余额 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface InitialBalanceMapper extends BaseMapper<InitialBalance> {

    Page<InitialBalanceResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") InitialBalanceParam paramCondition);


    List<InitialBalanceResult> exportInitialBalanceList( @Param("paramCondition") InitialBalanceParam paramCondition);

}
