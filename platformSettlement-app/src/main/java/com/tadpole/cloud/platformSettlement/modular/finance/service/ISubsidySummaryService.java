package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SubsidySummary;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SubsidySummaryParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 补贴汇总表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISubsidySummaryService extends IService<SubsidySummary> {

    PageResult<SubsidySummaryResult> findPageBySpec(SubsidySummaryParam param);

    ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList);

    void edit(SubsidySummaryParam param);

    void confirm(SubsidySummaryParam param);

    void delete(SubsidySummaryParam param);

    ResponseData confirmBatch(SubsidySummaryParam param);

    void deleteBatch(SubsidySummaryParam param);

    List<SubsidySummaryResult> queryList(SubsidySummaryParam param);

    SubsidySummaryResult getQuantity(SubsidySummaryParam param);
}
