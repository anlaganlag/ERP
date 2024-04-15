package com.tadpole.cloud.platformSettlement.modular.sales.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.sales.enums.SeasonEnum;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.TargetBoardParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.TargetBoardResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ITargetBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
@RestController
@ApiResource(name = "Target board 目标看板", path = "/targetBoard")
@Api(tags = "Target board 目标看板")
public class TargetBoardController {

    @Autowired
    private ITargetBoardService service;

    /**
     * 目标看板列表接口
     */
    @PostResource(name = "目标看板列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "目标看板列表", response = TargetBoardResult.class)
    @ParamValidator
    @BusinessLog(title = "目标看板-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody TargetBoardParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        return ResponseData.success(service.listBySpec(param));
    }

    /**
     * 目标看板导出接口
     *
     */
    @PostResource(name = "目标看板导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "目标看板导出")
    @BusinessLog(title = "目标看板-目标看板导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TargetBoardParam param, HttpServletResponse response) throws IOException {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份及版本不能为空");
        }
        List<TargetBoardResult> list = service.listBySpec(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("目标看板.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TargetBoardResult.class).sheet("目标看板").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 目标看板合计接口
     *
     */
    @PostResource(name = "目标看板合计", path = "/listSum",  requiredPermission = false)
    @ApiOperation(value = "目标看板合计", response = TargetBoardResult.class)
    @ParamValidator
    @BusinessLog(title = "目标看板-目标看板合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody TargetBoardParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        return ResponseData.success(service.listSum(param));
    }

    @GetResource(name = "季度下拉", path = "/seasonSelect", requiredPermission = false)
    @ApiOperation(value = "季度下拉")
    public ResponseData getSeasonSelect() {
        List<Map<String,String>>  seasonMap = SeasonEnum.getEnumList();
        List<String> seasonList = seasonMap.stream().map(i->new ArrayList<>(i.keySet()).get(0)).collect(Collectors.toList());
        return ResponseData.success(seasonList);
    }
}
