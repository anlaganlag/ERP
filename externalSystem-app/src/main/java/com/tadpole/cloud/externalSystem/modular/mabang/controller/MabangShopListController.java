package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangShopListMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangShopListParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.ShopParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangShopListResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangShopListService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 马帮店铺列表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@RestController
@ApiResource(name="Mabang ShopList 马帮店铺列表",path = "/mabangShopList")
@Api(tags =  "Mabang ShopList 马帮店铺列表")
public class MabangShopListController {

    @Autowired
    private IMabangShopListService service;


    @Resource
    private MabangShopListMapper mapper;


    @Resource
    IMabangRequstService mabangRequstService;

    @PostResource(name = "马帮店铺列表", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "马帮店铺列表", response = MabangShopListResult.class)
    public ResponseData list(@RequestBody  MabangShopListParam param) {
        PageResult<MabangShopListResult> list = service.list(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "立即同步店铺列表",path="/refreshShopList",requiredPermission = false)
    @ApiOperation(value = "立即同步店铺列表", response = ResponseData.class)
    public ResponseData refreshShopList(@RequestBody(required = false) ShopParm shopParm ) {
        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-shop-list", shopParm);
        MabangResult mabangResult = mabangRequstService.getShopList(mabangHeadParm);
        if (mabangResult.getCode().equals("200")) {
             service.add(mabangResult);
        }
        if (ObjectUtil.isNull(shopParm)) {
            shopParm = new ShopParm();
        }

        if (ObjectUtil.isNull(shopParm)) {
            shopParm = new ShopParm();
        }
        shopParm.setStatus(2);
        MabangHeadParm mabangHeadParm2 = new MabangHeadParm("sys-get-shop-list", shopParm);
        MabangResult mabangResult2 = mabangRequstService.getShopList(mabangHeadParm2);
        if (mabangResult2.getCode().equals("200")) {

            ArrayList list = (ArrayList) ((Map) mabangResult2.getData()).get("data");
            if (CollectionUtil.isEmpty(list)) {
                return ResponseData.success("没有停用的店铺!");
            }
            return service.add(mabangResult2);
        }
        return ResponseData.error(mabangResult.getMessage());
    }


    @GetResource(name = "店铺名称", path="/queryNames", requiredPermission = false)
    @ApiOperation(value = "店铺名称", response = ResponseData.class)
    public ResponseData queryNames(){
        return service.queryNames();
    }

    @GetResource(name = "店铺财务编码", path="/financeCodeList", requiredPermission = false)
    @ApiOperation(value = "店铺财务编码", response = ResponseData.class)
    public ResponseData queryFinanceCodeList(){
        return service.queryFinanceCodeList();
    }

    @GetResource(name = "平台名称", path="/queryPlatformNames", requiredPermission = false)
    @ApiOperation(value = "平台名称", response = ResponseData.class)
    public ResponseData queryPlatformNames(){
        return service.queryPlatformNames();
    }


    @PostResource(name = "马帮店铺列表导出", path = "/exportExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("马帮店铺列表导出")
    public void exportExcel(HttpServletResponse response,@RequestBody MabangShopListParam param) throws IOException {
        try {
            List<MabangShopListResult> results = service.exportExcel(param);
            exportExcel(response, "马帮店铺列表");
            EasyExcel.write(response.getOutputStream(), MabangShopListResult.class).sheet("马帮店铺列表").doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, "马帮店铺列表导出错误");
        }
    }




    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

}
