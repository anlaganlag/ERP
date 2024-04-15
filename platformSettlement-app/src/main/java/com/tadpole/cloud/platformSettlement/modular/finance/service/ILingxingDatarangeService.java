package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.TransactionReq;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LingxingDatarange;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LingxingDatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LingxingDatarangeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gal
 * @since 2022-04-28
 */
public interface ILingxingDatarangeService extends IService<LingxingDatarange> {

    /**
     *daterange记录(每日)查询列表
     */
    PageResult<LingxingDatarangeResult> findPageBySpec(LingxingDatarangeParam param);

    /**
     *生成AZ报告记录
     */
    ResponseData generateReportRecords(LingxingDatarangeParam param,String bankAccount);

    /**
     *AZ报告记录生成校验
     */
    ResponseData verifyReportRecords(LingxingDatarangeParam param);

    /**
     * 获取领星交易明细
     */
    ResponseData generateDateRangeRecords(TransactionReq param) throws Exception;

    /**
     * 平台下拉
     */
    List<FinancialSiteResult> getPlatform();

    /**
     * 账号下拉
     */
    List<ShopCurrencyResult> getShop();

    /**
     * 站点下拉
     */
    List<FinancialSiteResult> getSite();

    /**
     * 结算ID下拉
     */
    List<Map<String, Object>> getSettlementIdSelect(LingxingDatarangeParam param);

    /**
     * 获取银行账号
     */
    String getBankAccount(LingxingDatarangeParam param);

    /**
     * 文件导出接口
     */
    List<LingxingDatarangeResult> list(LingxingDatarangeParam param);



}
