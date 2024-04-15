package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailComfirm;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * datarange明细刷完财务分类后的 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IDatarangeDetailComfirmService extends IService<DatarangeDetailComfirm> {

    /**
     * 根据时间和settlementId汇总金额
     * @param param
     * @return
     */
    DatarangeDetailComfirm getRangeMoney(DatarangeDetailComfirm param);

    /**
     * 非Amazon平台费
     * @param param
     * @return
     */
    DatarangeDetailComfirm getNotAmazonFee(DatarangeDetailComfirm param);

    List<DatarangeDetailComfirm> getPlatformAmazonFee(DatarangeDetailComfirm param);

    List<DetailResult> refreshFailure(StatementIncomeParam param);

    /**
     * 更新Data Range源报告站点为空的站点
     * @param param
     */
    void updateSite(SettlementReportCheck param);
}
