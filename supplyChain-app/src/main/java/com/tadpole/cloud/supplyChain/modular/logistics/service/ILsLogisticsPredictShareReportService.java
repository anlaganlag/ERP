package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsPredictShareReport;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsPredictShareReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsPredictShareReportResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  物流投入预估分摊报表 服务类
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
public interface ILsLogisticsPredictShareReportService extends IService<LsLogisticsPredictShareReport> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(LsLogisticsPredictShareReportParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<LsLogisticsPredictShareReportResult> export(LsLogisticsPredictShareReportParam param);

    /**
     * 导入
     * @param file
     * @return
     */
    ResponseData importExcel(MultipartFile file);

    /**
     * 定时生成BTB物流投入预估分摊报表
     * @return
     */
    ResponseData generateBtpReport();

    /**
     * 接收EBMS物流投入预估分摊报表
     * @param param
     * @return
     */
    ResponseData generateEBMSReport(List<LsLogisticsPredictShareReport> param);

}
