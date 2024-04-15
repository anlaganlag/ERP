package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.BackPrepareRecomBiParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.BackPrepareRecomBiResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IBackPrepareRecomBiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 每日备货推荐表-BI 前端控制器
    * </p>
*
* @author cyt
* @since 2022-08-19
*/
@Slf4j
@RestController
@ApiResource(name = "每日备货推荐New", path = "/StockRecom")
@Api(tags = "每日备货推荐New")
public class BackPrepareRecomBiController {

    @Resource
    private IBackPrepareRecomBiService service;


    private final String controllerName = "每日备货推荐New";


    /**
     * 每日备货推荐列表
     * @param reqVO
     * @return
     */
    @PostResource(name = "每日备货推荐查询列表", path = "/list", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "每日备货推荐查询列表", response = BackPrepareRecomBiResult.class)
    @BusinessLog(title = controllerName + "_" +"每日备货推荐查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody BackPrepareRecomBiParam reqVO) {
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();
        if (ObjectUtils.isNull(loginUserInfo)||ObjectUtils.isNull(loginUserInfo.getAccount())) {
                return ResponseData.error("每日备货推荐查询列表列表查询错误，原因：未查询到登陆用户！");
        }
        return ResponseData.success(service.queryPage(reqVO));
    }


    /**
     * 每日备货推荐导出 3.1 版本优化按给定模板导出
     * @param reqVO
     * @return
     */
    @PostResource(name = "每日备货推荐查询导出", path = "/export",requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "每日备货推荐查询导出", response = BackPrepareRecomBiResult.class)
    @BusinessLog(title = controllerName + "_" +"每日备货推荐查询导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody BackPrepareRecomBiParam reqVO, HttpServletResponse response) throws IOException {
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();
        if (ObjectUtils.isNull(loginUserInfo)||ObjectUtils.isNull(loginUserInfo.getAccount())) {
            return ;
        }
        List<BackPrepareRecomBiResult> resultList = service.export(reqVO);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("每日备货推荐.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), BackPrepareRecomBiResult.class).sheet("每日备货推荐").doWrite(resultList);
    }

    @ApiOperation("每日备货推荐导出")
    @PostResource(name = "/每日备货推荐导出", path = "/exportExcel", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"每日备货推荐导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response,@RequestBody BackPrepareRecomBiParam reqVO)throws IOException{
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();
        if (ObjectUtils.isNull(loginUserInfo)||ObjectUtils.isNull(loginUserInfo.getAccount())) {
            return ;
        }
        try {
            service.exportExcel(response,reqVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 部门信息获取
     * @param
     * @return
     */
    @GetResource(name = "部门信息获取", path = "/department",requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "部门信息获取", response = BackPrepareRecomBiResult.class)
    @BusinessLog(title = controllerName + "_" +"部门信息获取",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData department(@RequestParam String accountCode)  {
        return service.getDepartment(accountCode);
    }



    /**
     * 每日备货推荐从BI抽数完成后
     * 刷新特殊备货申请单处于计划部审批的 ，关联的BI推荐数据
     * @param
     * @return
     */
    @GetResource(name = "刷新特殊备货申请关联的BI推荐数据", path = "/flashSpecialApplyData",requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "刷新特殊备货申请关联的BI推荐数据", response = BackPrepareRecomBiResult.class)
    public ResponseData flashSpecialApplyData()  {
        return service.flashSpecialApplyData();
    }



}
