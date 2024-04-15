package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsPredictShareReport;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsPredictShareReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsPredictShareReportResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  物流投入预估分摊报表Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
public interface LsLogisticsPredictShareReportMapper extends BaseMapper<LsLogisticsPredictShareReport> {

    /**
     * 分页查询列表、导出
     * @param param
     * @return
     */
    Page<LsLogisticsPredictShareReportResult> queryPage(@Param("page") Page page, @Param("param") LsLogisticsPredictShareReportParam param);

    /**
     * 定时生成BTB物流投入预估分摊报表
     * @param dataMonth
     * @return
     */
    void generateBtpReport(@Param("dataMonth") String dataMonth);

}
