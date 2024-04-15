package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayValue;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDaySummaryParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDaySummaryService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SaftyDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 备货2.0-安全天数设置 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Slf4j
@RestController
@Api(tags = "备货2.0-安全天数设置")
@ApiResource(name = "备货2.0-PMC审批", path = "/saftyDaySummary")
public class SaftyDaySummaryController {

    @Autowired
    private ISaftyDaySummaryService service;


    private final String controllerName = "备货2.0-安全天数设置";

    /**
     * 备货2.0-安全天数-列表
     */
    @PostResource(name = "备货2.0-安全天数-列表", path = "/list", requiredPermission = false , menuFlag = true)
    @ApiOperation("备货2.0-安全天数-列表")
    @BusinessLog(title = controllerName + "_" +"备货2.0-安全天数-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody SaftyDaySummaryParam summaryParam) {
        try {
            return ResponseData.success(service.list(summaryParam));
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     * 安全天数 新增
     */
    @PostResource(name = "备货2.0-安全天数-新增", path = "/add", requiredPermission = false )
    @ApiOperation("备货2.0-安全天数-新增")
    @BusinessLog(title = controllerName + "_" +"备货2.0-安全天数-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SaftyDayVO saftyDayVO) {
        try {
            return service.add(saftyDayVO);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 安全天数 状态更新
     */
    @PostResource(name = "备货2.0-安全天数-状态更新", path = "/updateStatus", requiredPermission = false )
    @ApiOperation("备货2.0-安全天数-状态更新")
    @BusinessLog(title = controllerName + "_" +"备货2.0-安全天数-状态更新",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateStatus( @RequestBody  HashMap<String,Object> parMap) {
        try {

            if (ObjectUtil.isNull(parMap)) {
                ResponseData.error("参数不能为空");
            }
            Object action = parMap.get("action");
            Object idList = parMap.get("idList");
            if (ObjectUtil.isNull(action) || ObjectUtil.isEmpty(idList)) {
                ResponseData.error("参数不能为空");
            }
            List<Integer> ids = (List<Integer>) idList;
            Integer doaction = (Integer) action;// "0:禁用、1:启用、2删除

            return service.updateStatus(ids, doaction);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 安全天数 更新值
     */
    @PostResource(name = "备货2.0-安全天数-更新值", path = "/updateValue", requiredPermission = false )
    @ApiOperation("备货2.0-安全天数-更新值")
    @BusinessLog(title = controllerName + "_" +"备货2.0-安全天数-更新值",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateValue(@RequestBody List<SaftyDayValue> valueList) {
        try {
            return service.updateValue(valueList);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 安全天数 安维度重新生成
     */
    @PostResource(name = "备货2.0-安全天数-安维度重新生成", path = "/revive", requiredPermission = false )
    @ApiOperation("备货2.0-安全天数-安维度重新生成")
    @BusinessLog(title = controllerName + "_" +"备货2.0-安全天数-安维度重新生成",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData revive(@RequestBody SaftyDayVO saftyDayVO) {
        try {
            return service.revive(saftyDayVO);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    @PostResource(name = "/安全天数导出", path = "/exportExcel" )
    @ApiOperation("安全天数导出")
    @BusinessLog(title = controllerName + "_" +"安全天数导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, @RequestBody SaftyDaySummaryParam param) throws Exception {
        List<SaftyDaySummaryResult> resultList = service.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("安全天数导出!");
        }
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("安全天数导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SaftyDaySummaryResult.class).sheet("安全天数导出")
                .doWrite(resultList);
    }

}
