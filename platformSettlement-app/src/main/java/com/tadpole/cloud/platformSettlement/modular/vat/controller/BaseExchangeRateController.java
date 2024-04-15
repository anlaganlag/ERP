package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseExchangeRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * VAT基础信息-汇率表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@RestController
@Api(tags = "BaseExchangeRate VAT基础信息-汇率")
@ApiResource(name = "BaseExchangeRate VAT基础信息-汇率", path = "/baseExchangeRate")
public class BaseExchangeRateController {

    private final String ControllerName = "VAT基础信息-汇率";

    @Autowired
    private IBaseExchangeRateService service;

    @PostResource(name = ControllerName + "导出", path = "/exportExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation(ControllerName + "导出")
    @BusinessLog(title = "BaseExchangeRate VAT基础信息-汇率-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, BaseExchangeRateParam param) throws IOException {
        try {
            List<BaseExchangeRateResult> results = service.exportExcel(param);
            exportExcel(response, ControllerName);
            EasyExcel.write(response.getOutputStream(), BaseExchangeRateResult.class).sheet(ControllerName).doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, ControllerName + "导出错误");
        }
    }

    @PostResource(name = ControllerName + "列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = ControllerName + "列表查询", response = BaseExchangeRateResult.class)
    @BusinessLog(title = "BaseExchangeRate VAT基础信息-汇率-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody BaseExchangeRateParam param) {
        return ResponseData.success(service.queryListPage(param));
    }

    @PostResource(name = ControllerName + "修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = ControllerName + "修改", response = BaseExchangeRateResult.class)
    @BusinessLog(title = "BaseExchangeRate VAT基础信息-汇率-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody BaseExchangeRateParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(ControllerName + "修改失败，原因：获取数据异常，请确认传入数据。");
        }
        ResponseData responseData = validateIsNotEmpty(param);
        if(!responseData.getSuccess()) {
            return responseData;
        }
        try {
            return service.update(param,ControllerName);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "修改异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = ControllerName + "新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = ControllerName + "新增", response = ResponseData.class)
    @BusinessLog(title = "BaseExchangeRate VAT基础信息-汇率-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody BaseExchangeRateParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(ControllerName + "新增失败，原因：获取数据异常，请确认传入数据。");
        }
        ResponseData responseData = validateIsNotEmpty(param);
        if(!responseData.getSuccess()) {
            return responseData;
        }

        try {
           return service.add(param,ControllerName);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "新增异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = ControllerName + "批量新增", path = "/addBatch", requiredPermission = false)
    @ApiOperation(value = ControllerName + "批量新增", response = ResponseData.class)
    @BusinessLog(title = "BaseExchangeRate VAT基础信息-汇率-批量新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addBatch(@RequestBody List<BaseExchangeRateParam> params) {
        if (ObjectUtil.isEmpty(params)) {
            return ResponseData.error(ControllerName + "新增失败，原因：获取数据异常，请确认传入数据。");
        }
        List<BaseExchangeRateParam> rateUnqualified =  params.stream().filter(p -> p.getExchangeRate().compareTo(BigDecimal.valueOf(0)) != 1 ).collect(Collectors.toList());
        if(rateUnqualified.size()>0){
            return ResponseData.error(ControllerName+"新增失败，原因：汇率必须大于0，请确认。");
        }

        try {
            //验证传入参数重复
            Map<String, Integer> map = new HashMap<>();
            for (BaseExchangeRateParam p : params) {
                String mapKey = p.getOriginalCurrency() + ":" + p.getTargetCurrency();
                Integer i = 1; //定义一个计数器，用来记录重复数据的个数
                if (map.get(mapKey) != null) {
                    i = map.get(mapKey) + 1;
                }
                map.put(mapKey, i);

                ResponseData responseData = validateIsNotEmpty(p);
                if(!responseData.getSuccess()) {
                    return responseData;
                }
            }
            for (String s : map.keySet()) {
                if (map.get(s) > 1) {
                    return ResponseData.error(ControllerName + "新增失败，原因：汇率期间[年-月]、原币币种、目标币种、不能重复，请确认。");
                }
            }

            return service.addBatch(params,ControllerName);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "新增异常，原因：" + ex.getMessage());
        }
    }

    private ResponseData validateIsNotEmpty(BaseExchangeRateParam param){
        //验证 非空
        if (ObjectUtil.isEmpty(param.getTargetCurrency())) {
            return ResponseData.error(ControllerName + "新增失败，原因：目标币种 不能为空，请确认。");
        }
        if (ObjectUtil.isEmpty(param.getOriginalCurrency())) {
            return ResponseData.error(ControllerName + "新增失败，原因：原币币种 不能为空，请确认。");
        }
        if (ObjectUtil.isEmpty(param.getExchangeRatePeriod())) {
            return ResponseData.error(ControllerName + "新增失败，原因：汇率期间 不能为空，请确认。");
        }
        //验证 汇率
        if (param.getExchangeRate().compareTo(BigDecimal.valueOf(0)) != 1) {
            return ResponseData.error(ControllerName + "新增失败，原因：汇率必须大于0，请确认。");
        }
        return  ResponseData.success();
    }

    @GetResource(name = ControllerName + "原币币种下拉", path = "/getCurrencyO", requiredPermission = false)
    @ApiOperation(value = ControllerName + "原币币种下拉", response = BaseExchangeRateResult.class)
    public ResponseData queryOriginalCurrency() {
        return service.queryOriginalCurrency();
    }

    @GetResource(name = ControllerName + "目标币币种下拉", path = "/getTargetC", requiredPermission = false)
    @ApiOperation(value = ControllerName + "目标币币种下拉", response = BaseExchangeRateResult.class)
    public ResponseData queryTargetCurrency() {
        return service.queryTargetCurrency();
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

}
