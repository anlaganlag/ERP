package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ReceivableDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReceivableDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReceivableDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
* 应收明细 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface ReceivableDetailMapper extends BaseMapper<ReceivableDetail> {

    /**
     * 插入应收明细主表
     * @param param
     */
    void generateReceivable(@Param("paramCondition") ReceivableDetail param);

    List<ReceivableDetailResult> queryListPage(@Param("paramCondition") ReceivableDetail param);

    List<ReceivableDetail> verifyList(@Param("paramCondition") ReceivableDetailParam param);

    List<ReceivableDetailResult> list(@Param("paramCondition") ReceivableDetailParam paramCondition);

    /**
     * 获取最大序号
     */
    @Select("select max(SETTLEMENT_NO) SETTLEMENT_NO from Cw_Receivable_Detail_Detail where RECEIVABLE_ID=#{receivableId} and SETTLEMENT_NO IS NOT NULL")
    BigDecimal selectMaxNumber(BigDecimal receivableId);

    ReceivableDetail getEarly();
    ReceivableDetail getEarlyCheck();
}
