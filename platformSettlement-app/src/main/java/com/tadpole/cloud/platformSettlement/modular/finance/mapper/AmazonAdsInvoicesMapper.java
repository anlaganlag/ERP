package com.tadpole.cloud.platformSettlement.modular.finance.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonAdsInvoices;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.AmazonAdsInvoicesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AmazonAdsInvoicesResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* <p>
    * Amazon广告费用账单 Mapper 接口
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
public interface AmazonAdsInvoicesMapper extends BaseMapper<AmazonAdsInvoices> {

    IPage<AmazonAdsInvoicesResult> selecMyPage(IPage<AmazonAdsInvoices> page, @Param("param") AmazonAdsInvoicesParam param);
    List<AmazonAdsInvoicesResult> export(@Param("param") AmazonAdsInvoicesParam param);
}
