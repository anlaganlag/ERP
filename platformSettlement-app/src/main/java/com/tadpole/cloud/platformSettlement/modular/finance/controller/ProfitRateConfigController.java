package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ProfitRateConfigParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ProfitRateConfigResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IProfitRateConfigService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  利润率参数管理
 * </p>
 *
 * @author ty
 * @since 2022-05-27
 */
@RestController
@ApiResource(name = "利润率参数管理", path = "/profitRateConfig")
@Api(tags = "利润率参数管理")
public class ProfitRateConfigController {

    @Autowired
    private IProfitRateConfigService service;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    /**
     * 利润率参数管理
     * @param param
     * @return
     */
    @PostResource(name = "利润率参数管理", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "利润率参数管理", response = ProfitRateConfigResult.class)
    @BusinessLog(title = "利润率参数管理-利润率参数管理列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody ProfitRateConfigParam param) {
        return service.queryPage(param);
    }

    /**
     * 利润率参数管理导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "利润率参数管理导出", path = "/export")
    @ApiOperation(value = "利润率参数管理导出")
    @BusinessLog(title = "利润率参数管理-利润率参数管理导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody ProfitRateConfigParam param, HttpServletResponse response) throws IOException {
        List<ProfitRateConfigResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("利润率参数管理导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ProfitRateConfigResult.class).sheet("利润率参数管理导出").doWrite(resultList);
    }

    /**
     * 销毁移除成本月分摊表列表导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "销毁移除成本月分摊表列表导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "销毁移除成本月分摊表列表导入")
    @BusinessLog(title = "利润率参数管理-销毁移除成本月分摊表列表导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        return service.importExcel(file, departmentList, teamList);
    }

    /**
     * 历史利润率查询列表
     * @param param
     * @return
     */
    @PostResource(name = "历史利润率查询列表", path = "/queryHistoryPage")
    @ApiOperation(value = "历史利润率查询列表", response = ProfitRateConfigResult.class)
    @BusinessLog(title = "利润率参数管理-历史利润率查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryHistoryPage(@RequestBody ProfitRateConfigParam param) {
        return service.queryHistoryPage(param);
    }

    /**
     * 事业部下拉
     * @return
     */
    @GetResource(name = "事业部下拉", path = "/departmentSelect")
    @ApiOperation(value = "事业部下拉")
    public ResponseData departmentSelect() {
        return service.departmentSelect();
    }

    /**
     * Team下拉
     * @return
     */
    @GetResource(name = "Team下拉", path = "/teamSelect")
    @ApiOperation(value = "Team下拉")
    public ResponseData teamSelect() {
        return service.teamSelect();
    }

    /**
     * 运营大类下拉
     * @return
     */
    @GetResource(name = "运营大类下拉", path = "/productTypeSelect")
    @ApiOperation(value = "运营大类下拉")
    public ResponseData productTypeSelect() {
        return service.productTypeSelect();
    }
}
