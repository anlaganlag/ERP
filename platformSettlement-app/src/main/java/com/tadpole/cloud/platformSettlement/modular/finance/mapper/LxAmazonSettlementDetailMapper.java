package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.CwLingxingSettlementResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementAbnormalParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 领星Settlement Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
public interface LxAmazonSettlementDetailMapper extends BaseMapper<LxAmazonSettlementDetail> {

    /**
     * 查询AZ结算异常监控列表
     */
    Page<CwLingxingSettlementResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SettlementAbnormalParam param);

    /**
     * AZ结算异常数据生成（刷新）
     */
    void refresh();
}
