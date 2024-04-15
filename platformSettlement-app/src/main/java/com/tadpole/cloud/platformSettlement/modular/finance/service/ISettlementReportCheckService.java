package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportCheckParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportCheckResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * AZ结算报告审核 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface ISettlementReportCheckService extends IService<SettlementReportCheck> {

    /**
     * AZ结算报告审核列表
     * @param param
     * @return
     */
    PageResult<SettlementReportCheckResult> findPageBySpec(SettlementReportCheckParam param);

    /**
     * AZ结算报告审核
     * @param param
     * @throws Exception
     */
    void verify(SettlementReportCheckParam param) throws Exception;

    /**
     * AZ结算报告批量审核
     * @param params
     * @throws Exception
     */
    void verifyList(List<SettlementReportCheckParam> params) throws Exception;

    /**
     * 修改站点
     * @param param
     */
    ResponseData editSite(SettlementReportCheckParam param);

    /**
     * 修改单据类型
     * @param param
     */
    void editType(SettlementReportCheckParam param);

    /**
     * 修改收款币种
     * @param param
     */
    void editProceedsCurrency(SettlementReportCheckParam param);

    /**
     * 修改收款金额
     * @param param
     */
    void editAmount(SettlementReportCheckParam param);

    /**
     * AZ结算报告作废
     * @param param
     * @throws Exception
     */
    void reject(SettlementReportCheckParam param) throws Exception;

    /**
     * 删除对应的Settlement/Data Range汇总和明细解析数据
     * @param param
     */
    void invalid(SettlementReportCheckParam param);

    /**
     * 汇款银行下拉
     * @return
     */
    List<SettlementReportCheckResult> bankList();

    /**
     * 导出AZ结算报告审核列表
     * @param param
     * @return
     */
    List<SettlementReportCheckResult> exportSettlementReportCheckList(SettlementReportCheckParam param);
}
