package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.InvReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InvReportUploadRecordParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 上传记录表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-26
 */
public interface InvReportUploadRecordMapper extends BaseMapper<InvReportUploadRecord> {

  Page<InvReportUploadRecord> customPageList(@Param("page") Page page, @Param("paramCondition") InvReportUploadRecordParam paramCondition);
}
