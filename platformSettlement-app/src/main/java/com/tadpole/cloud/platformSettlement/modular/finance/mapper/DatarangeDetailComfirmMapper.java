package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailComfirm;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* datarange明细刷完财务分类后的 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface DatarangeDetailComfirmMapper extends BaseMapper<DatarangeDetailComfirm> {

    /**
     * 根据时间和settlementId汇总金额
     * @param param
     * @return
     */
    DatarangeDetailComfirm getRangeMoney(@Param("paramCondition") DatarangeDetailComfirm param);

    /**
     * 非Amazon平台费
     * @param param
     * @return
     */
    DatarangeDetailComfirm getNotAmazonFee(@Param("paramCondition") DatarangeDetailComfirm param);

    List<DatarangeDetailComfirm> getPlatformAmazonFee(@Param("paramCondition")  DatarangeDetailComfirm param);

    List<DetailResult> refreshFailure(@Param("paramCondition") StatementIncomeParam param);

    /**
     * 更新Data Range源报告站点为空的站点
     * @param param
     */
    void updateSite(@Param("paramCondition")  SettlementReportCheck param);
}
