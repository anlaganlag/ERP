package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReportUploadRecordResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * AZ报告上传记录 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IReportUploadRecordService extends IService<ReportUploadRecord> {
    PageResult<ReportUploadRecordResult> findPageBySpec(ReportUploadRecordParam param);

    void add(ReportUploadRecordParam param);

    void insertSettlementRecord(ReportUploadRecordParam param);

    void insertDataRangeRecord(ReportUploadRecordParam param);
}
