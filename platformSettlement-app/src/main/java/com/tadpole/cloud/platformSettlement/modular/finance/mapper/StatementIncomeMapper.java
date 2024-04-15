package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementIncome;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 收入记录表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface StatementIncomeMapper extends BaseMapper<StatementIncome> {

    /**
     * 收入记录表
     * @param page
     * @param param
     * @return
     */
    Page<StatementIncomeResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") StatementIncomeParam param);

    /**
     * 导出收入记录列表
     * @param paramCondition
     * @return
     */
    List<StatementIncomeResult> exportStatementIncomeList( @Param("paramCondition") StatementIncomeParam paramCondition);

    void updateVoucherNo(@Param("paramCondition")  StatementIncome income);

    /**
     * 根据id和时间获取收入记录表信息
     * @param income
     * @return
     */
    List<StatementIncome> getConfirmIncome(@Param("paramCondition")  StatementIncome income);
}
