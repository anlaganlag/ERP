package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LingxingDatarange;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LingxingDatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LingxingDatarangeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author gal
* @since 2022-04-28
*/
public interface LingxingDatarangeMapper extends BaseMapper<LingxingDatarange> {

    /**
     * 查询datarange列表
     */
    Page<LingxingDatarangeResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") LingxingDatarangeParam param);

    /**
     * 获取店铺sid
     */
    List<Long> getShopSid();

    /**
     * 获取银行账号
     */
    String getBankAccount(@Param("paramCondition") LingxingDatarangeParam param);

    /**
     * 获取收款币种
     */
    @Select("SELECT DISTINCT  PROCEEDS_CURRENCY FROM CW_SHOP_CURRENCY T WHERE  PLATFORM_NAME='Amazon' AND SHOP_NAME=#{shopName} AND SITE=#{site} AND ORIGINAL_CURRENCY=#{originalCurrency}")
    String selectProceedsCurrency(String shopName,String site,String originalCurrency);

    /**
     * SettlementID下拉
     */
    List<Map<String, Object>> getSettlementIdSelect(@Param("paramCondition") LingxingDatarangeParam param);

    /**
     * 领星数据写入datarange
     */
    void lingXingToDatarange(@Param("paramCondition") SettlementReportCheck param);

    void lingXingToDataRangeDetailComfirm(@Param("paramCondition") LingxingDatarangeParam param);
}
