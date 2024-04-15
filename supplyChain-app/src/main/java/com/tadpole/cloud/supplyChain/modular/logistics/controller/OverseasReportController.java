package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseService;
import com.tadpole.cloud.supplyChain.modular.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  海外仓报表前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-09-09
*/
@RestController
@ApiResource(name = "海外仓报表前端控制器", path = "/overseasReport")
@Api(tags = "海外仓报表")
public class OverseasReportController {

    @Autowired
    private IOverseasInWarehouseService overseasInWarehouseService;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    /**
     * 海外仓报表查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "海外仓报表", path = "/queryListPage",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "海外仓报表", response = OverseasReportResult.class)
    @BusinessLog(title = "海外仓报表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody OverseasReportParam param) {
        PageResult<OverseasReportResult> list = overseasInWarehouseService.queryReportListPage(param);
        return ResponseData.success(list);
    }

    /**
     * 海外仓报表导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "海外仓报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody OverseasReportParam param, HttpServletResponse response) throws IOException {

        List<OverseasReportResult> list = overseasInWarehouseService.exportOverseasReport(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasReportResult.class).sheet("海外仓报表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 账号下拉
     * @return
     */
    @GetResource(name = "账号下拉", path = "/shopsNameSelect")
    @ApiOperation(value = "账号下拉")
    public ResponseData shopsNameSelect() {
        return ResponseData.success(baseSelectConsumer.getShop());
    }

    /**
     * 站点下拉
     * @return
     */
    @GetResource(name = "站点下拉", path = "/siteSelect")
    @ApiOperation(value = "站点下拉")
    public ResponseData siteSelect() {
        return ResponseData.success(baseSelectConsumer.getSite());
    }

    /**
     * 出货仓名称下拉
     * @return
     */
    @GetResource(name = "出货仓名称下拉", path = "/outWarehouseSelect")
    @ApiOperation(value = "出货仓名称下拉")
    public ResponseData outWarehouseSelect() {
        return ResponseData.success(overseasInWarehouseService.outWarehouseSelect());
    }

    /**
     * 收货仓名称下拉
     * @return
     */
    @GetResource(name = "收货仓名称下拉", path = "/inWarehouseSelect")
    @ApiOperation(value = "收货仓名称下拉")
    public ResponseData inWarehouseSelect() {
        return ResponseData.success(overseasInWarehouseService.inWarehouseSelect());
    }

    /**
     * 运输方式下拉
     * @return
     */
    @GetResource(name = "运输方式下拉", path = "/suggestTransTypeSelect")
    @ApiOperation(value = "运输方式下拉")
    public ResponseData suggestTransTypeSelect() {
        return ResponseData.success(overseasInWarehouseService.suggestTransTypeSelect());
    }

    /**
     * 事业部下拉
     * @return
     */
    @GetResource(name = "事业部下拉", path = "/departmentSelect")
    @ApiOperation(value = "事业部下拉")
    public ResponseData departmentSelect() {
        return ResponseData.success(baseSelectConsumer.getDepartmentSelect());
    }

    /**
     * Team下拉
     * @return
     */
    @GetResource(name = "Team下拉", path = "/teamSelect")
    @ApiOperation(value = "Team下拉")
    public ResponseData teamSelect() {
        return ResponseData.success(baseSelectConsumer.getTeamSelect());
    }

    /**
     * 需求人员下拉
     * @return
     */
    @GetResource(name = "需求人员下拉", path = "/userSelect")
    @ApiOperation(value = "需求人员下拉")
    public ResponseData userSelect() {
        return ResponseData.success(baseSelectConsumer.userInfoSelect());
    }

}
