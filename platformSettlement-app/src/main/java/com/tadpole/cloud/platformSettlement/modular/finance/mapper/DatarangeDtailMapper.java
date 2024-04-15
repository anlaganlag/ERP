package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDtail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeDtailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DatarangeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* datarange源数据 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface DatarangeDtailMapper extends BaseMapper<DatarangeDtail> {
    List<DatarangeResult> getDatarangeList(@Param("paramCondition") DatarangeParam param);

    /**
     * 获取刷取财务分类未成功的Datarange数据
     * @param settlementId
     * @return
     */
    List<DatarangeDtail> getDatarangeDtailList(String settlementId);

    void InsertDataRangeDetailComfirm(@Param("paramCondition") DatarangeDtailParam paramCondition);

    /**
     * 刷新财务分类金额
     * @param paramCondition
     */
    void RefreshFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);

    /**
     * 更新Datarange状态为行转列：2
     * @param paramCondition
     */
    void UpdateStatus(@Param("paramCondition") DatarangeParam paramCondition);

    /**
     * 未刷成功的分类写入分类表
     * @param paramCondition
     */
    void InsertFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);

    /**
     * Datarange刷财务分类明细
     * @param paramCondition
     */
    void FillFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);

    /**
     * Datarange刷特殊财务分类更新
     * @param paramCondition
     */
    void SpecialFinancialClass(@Param("paramCondition") DatarangeDtailParam paramCondition);

    void refreshDifference(@Param("paramCondition") DatarangeDtailParam paramCondition);
}
