package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 站内手工分摊表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface IStationManualAllocationService extends IService<StationManualAllocation> {

    void deleteBatch(StationManualAllocationParam param);
    ResponseData pullStorageDisposeFee(StationManualAllocationParam param) throws ParseException;




    ResponseData confirmBatch(StationManualAllocationParam param);

    void delete(StationManualAllocationParam param);


    @DataSource(name = "finance")
    void fnskuFillDestroyListing(StationManualAllocationParam param);


    @DataSource(name = "finance")
    void fillListing(StationManualAllocationParam param);

    void fillSalesBrand(StationManualAllocationParam param);

    ResponseData confirm(StationManualAllocationParam param);

    void edit(StationManualAllocationParam param);

    ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList);

    PageResult<StationManualAllocationResult> findPageBySpec(StationManualAllocationParam param);

    List<StationManualAllocationResult> export(StationManualAllocationParam param);

    StationManualAllocationResult getQuantity(StationManualAllocationParam param);

    @DataSource(name = "finance")
    ResponseData mergeAllocLine(StationManualAllocationParam param);

    void updateAllocLineStatus();

    @DataSource(name = "finance")
    void updateAllocLineStatusNew();
}
