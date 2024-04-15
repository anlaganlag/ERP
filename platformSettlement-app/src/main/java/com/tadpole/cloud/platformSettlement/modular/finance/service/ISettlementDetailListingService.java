package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailComfirm;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailListing;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailListingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailListingResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 收入记录表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISettlementDetailListingService extends IService<SettlementDetailListing> {

    PageResult<SettlementDetailListingResult> findPageBySpec(SettlementDetailListingParam param);

    List<SettlementDetailListingResult> export(SettlementDetailListingParam param);


    ResponseData uploadSku(MultipartFile file);

    /**
     * 根据SettlementId和会计期间获取Settlement金额
     * @param param
     * @return
     */
    List<SettlementDetail> getSettlementMoney(SettlementDetail param);

    /**
     * 根据SettlementId和会计期间获取DataRange金额
     * @param param
     * @return
     */
    List<DatarangeDetailComfirm> getDataRangeMoney(DatarangeDetailComfirm param);

    SettlementDetailListingResult getQuantity(SettlementDetailListingParam param);

    ResponseData confirm(SettlementDetailListingParam param) throws ParseException;

    ResponseData confirmBatch(SettlementDetailListingParam param) throws ParseException;

    void afreshListing(SettlementDetailListingParam param) throws ParseException;

    void updateDetailList(SettlementDetailListingParam param) throws ParseException;

    void updateLatestDate(SettlementDetailListingParam param) throws ParseException;

    /**
     * 根据SettlementId和会计期间获取DataRange数量
     * @param param
     * @return
     */
    List<DatarangeDetailComfirm> getDataRangeNumber(DatarangeDetailComfirm param);

    /**
     * 根据SettlementId和会计期间获取Settlement数量
     * @param detailSettlement
     * @return
     */
    List<SettlementDetail> getSettlementNumber(SettlementDetail detailSettlement);

    List<SettlementDetailListingResult> emailList();

    /**
     * 根据SettlementId和会计期间获取Settlement（确认）和DataRange（预估）数量
     * @param detailSettlement
     * @param detailDataRange
     * @return
     */
    List<SettlementDetailResult> getSettlementDataRangeNumber(SettlementDetail detailSettlement, DatarangeDetailComfirm detailDataRange);

    List<DatarangeDetailComfirm> getDataRangeDiffrencelist(SettlementDetail detailSettlement, DatarangeDetailComfirm detailDataRange);

    /**
     * 将SettlementDetail的sku为null的设置为0
     */
    void updateSettlementDetailSkuNullToZero();

    /**
     * 将dataRangeDetail的sku为null的设置为0
     */
    void updateRangeDetailSkuNullToZero();
}
