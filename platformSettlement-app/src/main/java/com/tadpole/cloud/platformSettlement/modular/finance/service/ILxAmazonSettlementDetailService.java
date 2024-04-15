package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileListReq;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.CwLingxingSettlementResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementAbnormalParam;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gal
 * @since 2022-05-06
 */
public interface ILxAmazonSettlementDetailService extends IService<LxAmazonSettlementDetail> {

    /**
     *AZ结算异常监控查询列表
     */
    PageResult<CwLingxingSettlementResult> findPageBySpec(SettlementAbnormalParam param);

    /**
     * AZ结算异常监控导出
     */
    List<CwLingxingSettlementResult> list(SettlementAbnormalParam param);

    /**
     * 领星Settlement源文件下载
     */
    ResponseData generateSettlementSourceFile(SettlementFileListReq param) throws Exception;

    /**
     * 手动处理领星Settlement源文件下载
     * @param synDate
     * @return
     * @throws Exception
     */
    ResponseData handleGenerateSettlementSourceFile(String synDate) throws Exception;

    /**
     * Settlement文件列表
     */
    Map<Long,String> generateSettlementFileID(SettlementFileListReq param) throws Exception;

    /**
     * AZ结算异常数据生成（刷新）
     */
    ResponseData refresh();
}
