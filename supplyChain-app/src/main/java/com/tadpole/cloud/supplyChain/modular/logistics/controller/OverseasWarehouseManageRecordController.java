package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseManageRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageRecordResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseManageTotalResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OperateTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.OverseasBusinessTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  海外仓库存管理操作记录前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@RestController
@ApiResource(name = "海外仓库存管理操作记录前端控制器", path = "/overseasWarehouseManageRecord")
@Api(tags = "海外仓库存管理操作记录前端控制器")
public class OverseasWarehouseManageRecordController {

    @Autowired
    private IOverseasWarehouseManageRecordService service;

    /**
     * 海外仓库存管理操作记录分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "海外仓库存管理操作记录分页查询列表", path = "/queryDetailPage")
    @ApiOperation(value = "海外仓库存管理操作记录分页查询列表", response = OverseasWarehouseManageRecordResult.class)
    @BusinessLog(title = "海外仓库存管理操作记录-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OverseasWarehouseManageRecordParam param) {
        return service.queryPage(param);
    }

    /**
     * 海外仓库存管理操作记录分页查询列表数据汇总
     * @param param
     * @return
     */
    @PostResource(name = "海外仓库存管理操作记录分页查询列表数据汇总", path = "/queryPageTotal")
    @ApiOperation(value = "海外仓库存管理操作记录分页查询列表数据汇总", response = OverseasWarehouseManageTotalResult.class)
    @BusinessLog(title = "海外仓库存管理操作记录-列表汇总查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPageTotal(@RequestBody OverseasWarehouseManageRecordParam param) {
        return service.queryPageTotal(param);
    }

    /**
     * 操作下拉
     * @return
     */
    @GetResource(name = "操作下拉", path = "/operateTypeSelect")
    @ApiOperation(value = "操作下拉")
    public ResponseData operateTypeSelect() {
        return ResponseData.success(OperateTypeEnum.getOperateTypeName());
    }

    /**
     * 类型下拉
     * @return
     */
    @GetResource(name = "类型下拉", path = "/businessTypeSelect")
    @ApiOperation(value = "类型下拉")
    public ResponseData businessTypeSelect() {
        return ResponseData.success(OverseasBusinessTypeEnum.getBusinessType());
    }
}
