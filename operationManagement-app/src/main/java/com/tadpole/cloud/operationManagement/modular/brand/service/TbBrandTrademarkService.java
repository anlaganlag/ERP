package com.tadpole.cloud.operationManagement.modular.brand.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademark;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkReportPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkReportResult;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌商标表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-19
 */
public interface TbBrandTrademarkService extends IService<TbBrandTrademark> {

    List<String> getTradeName();
    Page<TbBrandTrademarkResult> getPage(TbBrandTrademarkPageParam param);
    void save(TbBrandTrademarkParam param);
    List<Map<String, Object>> getDictionarys(String code);

    Page<TbBrandTrademarkReportResult> getTradeReportPage(TbBrandTrademarkReportPageParam param);
    List<TbBrandTrademarkReportResult> getTradeReport(TbBrandTrademarkReportPageParam param);
}

