package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ManualAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ManualAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ManualAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 手动分摊调整表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface IManualAllocationAdjustService extends IService<ManualAllocationAdjust> {

    PageResult<ManualAllocationAdjustResult> findPageBySpec(ManualAllocationAdjustParam param);

    List<ManualAllocationAdjustResult> queryList(ManualAllocationAdjustParam param);

    ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList);

    void edit(ManualAllocationAdjustParam param);

    ResponseData confirm(ManualAllocationAdjustParam param);

    void delete(ManualAllocationAdjustParam param);

    ResponseData confirmBatch(ManualAllocationAdjustParam param);

    void deleteBatch(ManualAllocationAdjustParam param);

     ManualAllocationAdjustResult getQuantity(ManualAllocationAdjustParam param);

    List<SettlementDetailUsdResult> getManualAllocationAdjust(SettlementDetailUsdParam param);

    void updateAmountOrInsert(SettlementDetailUsd usd);
}
