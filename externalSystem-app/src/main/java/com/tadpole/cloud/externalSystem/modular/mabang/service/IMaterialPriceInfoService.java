package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MaterialPriceInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3StockReceiveSendDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MaterialPriceInfoParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MaterialPriceInfoResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物料价格信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-05-06
 */
public interface IMaterialPriceInfoService extends IService<MaterialPriceInfo> {

    /**
     * 获取马帮采购单的物料价格
     * @param matCodes
     * @return
     */
    Map<String, BigDecimal> getMabangMatPrice(List<K3AvailableResult> availableResultList);

    /**
     * 更新物料库存价格
     * @param detailList
     * @return
     */
    ResponseData updateMatStockPrice(List<K3StockReceiveSendDetail> detailList);

    /**
     * 更新物料库存价格
     * @param tempDate
     * @return
     */
    ResponseData updateMatStockPrice(LocalDate tempDate);


    List<K3StockReceiveSendDetail> getMatStockPrice(LocalDate needUpdateDate);

    PageResult<MaterialPriceInfoResult> findPageBySpec(MaterialPriceInfoParam param);

    ResponseData updateTemporaryPrice(MaterialPriceInfoParam param);
}
