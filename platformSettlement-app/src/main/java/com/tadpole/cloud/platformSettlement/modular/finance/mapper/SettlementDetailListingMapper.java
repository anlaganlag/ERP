package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailComfirm;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailListing;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailListingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailListingResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailResult;
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
 * @since 2021-12-24
 */
public interface SettlementDetailListingMapper extends BaseMapper<SettlementDetailListing> {

    Page<SettlementDetailListingResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") SettlementDetailListingParam param);

    /**
     * 根据SettlementId和会计期间获取Settlement金额
     *
     * @param param
     * @return
     */
    List<SettlementDetail> getSettlementMoney(@Param("paramCondition") SettlementDetail param);

    /**
     * 根据SettlementId和会计期间获取DataRange金额
     *
     * @param param
     * @return
     */
    List<DatarangeDetailComfirm> getDataRangeMoney(@Param("paramCondition") DatarangeDetailComfirm param);

    /**
     * 根据SKU刷物料
     *
     * @param param
     */
    void afreshListing(@Param("paramCondition") SettlementDetailListingParam param);

    SettlementDetailListingResult getQuantity(@Param("paramCondition") SettlementDetailListingParam paramCondition);

    /**
     * 根据SettlementId和会计期间获取DataRange数量
     *
     * @param param
     * @return
     */
    List<DatarangeDetailComfirm> getDataRangeNumber(@Param("paramCondition") DatarangeDetailComfirm param);

    /**
     * 根据SettlementId和会计期间获取Settlement数量
     *
     * @param detailSettlement
     * @return
     */
    List<SettlementDetail> getSettlementNumber(@Param("paramCondition") SettlementDetail detailSettlement);

    /**
     * 根据SKU刷物料（存档）
     *
     * @param param
     */
    void afreshListingFile(@Param("paramCondition") SettlementDetailListingParam param);

    List<SettlementDetailListingResult> emailList();

    /**
     * 批量将原币结算单明细表插入到美金结算单明细表
     *
     * @param param
     * @return
     */
    void insertBatchSettlementUsd(@Param("paramCondition") SettlementDetailListingParam param);

    /**
     * 批量确认更新原币结算单明细表
     *
     * @param param
     * @return
     */
    void updateConfirmDetailListing(@Param("paramCondition") SettlementDetailListingParam param);

    /**
     * 根据SettlementId和会计期间获取Settlement（确认）和DataRange（预估）数量
     *
     * @param detailSettlement
     * @param detailDataRange
     * @return
     */
    List<SettlementDetailResult> getSettlementDataRangeNumber(@Param("paramSettlement") SettlementDetail detailSettlement, @Param("paramDataRange") DatarangeDetailComfirm detailDataRange);

    List<DatarangeDetailComfirm> getDataRangeDiffrencelist(@Param("paramSettlement") SettlementDetail detailSettlement, @Param("paramDataRange") DatarangeDetailComfirm detailDataRange);

    /**
     * 将SettlementDetail的sku为null的设置为0
     */
    void updateSettlementDetailSkuNullToZero();

    /**
     * 将dataRangeDetail的sku为null的设置为0
     */
    void updateRangeDetailSkuNullToZero();

    /**
     * 根据FNSKU匹配物料表刷SKU
     *
     * @param param
     */
    void afreshListingSku(@Param("paramCondition") SettlementDetailListingParam param);

    /**
     * 根据FNSKU匹配物料表刷SKU（存档）
     *
     * @param param
     */
    void afreshListingSkuFile(@Param("paramCondition") SettlementDetailListingParam param);

    /**
     * FNSKU为空的将SKU赋值于FNSKU
     *
     * @param param
     */
    void afreshListingFnsku(@Param("paramCondition") SettlementDetailListingParam param);

    int assignAllocId(@Param("paramCondition") StationManualAllocationParam param);
}
