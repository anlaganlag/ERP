package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementIncome;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 收入记录表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IStatementIncomeService extends IService<StatementIncome> {

    /**
     * 收入记录表
     * @param param
     * @return
     */
    PageResult<StatementIncomeResult> findPageBySpec(StatementIncomeParam param);

    List<StatementIncome> selectList(QueryWrapper<StatementIncome> queryWrapper);

    void editFiscalPeriod(StatementIncomeParam param);

    void syncToErp(StatementIncomeParam param) throws IOException;

    ResponseData syncToErpBatch(List<StatementIncomeParam> params) throws IOException;

    void confirm(StatementIncomeParam param) throws Exception;

    void confirmBatch(List<StatementIncomeParam> params) throws Exception;

    /**
     * 刷取金额数据
     * @param param
     */
    void syncAmount(StatementIncomeParam param);

    /**
     * 导出收入记录列表
     * @param param
     * @return
     */
    List<StatementIncomeResult> exportStatementIncomeList(StatementIncomeParam param);


    boolean updateById(StatementIncome income);


    void updateVoucherNo(StatementIncome income);


    List<DetailResult> refreshFailure(StatementIncomeParam param);


    void toListing(StatementIncomeParam param);




    ResponseData assignAllocId(StationManualAllocationParam param);

    ResponseData shopSiteHave(StatementIncomeParam param);

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    ResponseData shopSiteToAlloc(StatementIncomeParam param);

    void toReceive(StatementIncomeParam param) throws Exception;

    void toChongHui(StatementIncomeParam param);

    void dateToListing(StatementIncomeParam param);


}
