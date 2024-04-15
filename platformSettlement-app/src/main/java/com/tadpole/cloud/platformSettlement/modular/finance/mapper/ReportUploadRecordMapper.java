package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReportUploadRecordResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* AZ报告上传记录 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface ReportUploadRecordMapper extends BaseMapper<ReportUploadRecord> {
    Page<ReportUploadRecordResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") ReportUploadRecordParam paramCondition);

    void add(ReportUploadRecordParam param);
}
