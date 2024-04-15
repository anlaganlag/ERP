package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDtail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeDtailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DatarangeResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * datarange源数据 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IDatarangeDtailService extends IService<DatarangeDtail> {

    List<DatarangeResult> getDatarangeList(DatarangeParam param);

    List<DatarangeDtail> getDatarangeDtailList(String settlementId);

    void InsertDataRangeDetailComfirm(@Param("paramCondition") DatarangeDtailParam paramCondition);

    /**
     * 刷新Datarange财务分类明细
     * @param paramCondition
     */
    void RefreshFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);

    void UpdateStatus(@Param("paramCondition") DatarangeParam paramCondition);

    void InsertFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);
}
