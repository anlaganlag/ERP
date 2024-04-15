package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.WarehouseSixCodeParam;
import cn.stylefeng.guns.cloud.system.api.base.model.result.WarehouseSixCodeResult;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.RpMaterialConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 海外仓六位码查询前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-18
 */
@RestController
@ApiResource(name="海外仓六位码查询", path = "/warehouseSixCode")
@Api(tags =  "海外仓六位码")
@Slf4j
public class WarehouseSixCodeController {

    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;

    @PostResource(name = "海外仓六位码查询列表", path = "/sixCodeListPage", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "海外仓六位码查询列表", response = WarehouseSixCodeResult.class)
    @BusinessLog(title = "海外仓六位码查询列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData sixCodeListPage(@RequestBody WarehouseSixCodeParam param) {
        Page pageContext = param.getPageContext();
        PageResult<WarehouseSixCodeResult> list = rpMaterialConsumer.sixCodeListPage(pageContext.getCurrent(), pageContext.getSize(), param);
        return ResponseData.success(list);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "海外仓六位码查询列表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody WarehouseSixCodeParam param, HttpServletResponse response) throws IOException {

        List<WarehouseSixCodeResult> list = rpMaterialConsumer.sixCodeExport(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓六位码列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), WarehouseSixCodeResult.class).sheet("海外仓六位码列表").doWrite(list);
        return ResponseData.success();
    }

}
