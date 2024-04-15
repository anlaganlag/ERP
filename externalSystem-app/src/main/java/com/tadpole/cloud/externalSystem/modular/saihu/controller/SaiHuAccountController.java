package com.tadpole.cloud.externalSystem.modular.saihu.controller;


import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.model.SaihuUser;
import com.meiyunji.cloud.builder.JwtLoginParamBuilder;
import com.meiyunji.cloud.exception.JwtLoginException;
import com.tadpole.cloud.externalSystem.modular.saihu.service.IAccountService;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "账号登录Controller类", path = "/saiHuAccount")
@Api(tags = "账号登录Controller类")
public class SaiHuAccountController {

    @Autowired
    private IAccountService accountService;

    @PostResource(name = "获取自定义店铺列表", path = "/getAccountList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取自定义店铺列表")
    public ResponseData getAccountList() throws Exception {

        return ResponseData.success(accountService.getAccountList());
    }

    @PostResource(name = "启用停用子账号", path = "/changeAccountStatus", requiredPermission = false, requiredLogin = false)
    @ApiOperation("启用停用子账号")
    public ResponseData changeAccountStatus(@RequestBody SaihuUser saihuUser) throws Exception {

        accountService.changeAccountStatus(saihuUser);
        return ResponseData.success();
    }

    @PostResource(name = "新增子账号", path = "/createAccount", requiredPermission = false, requiredLogin = false)
    @ApiOperation("新增子账号")
    public ResponseData createAccount(@RequestBody SaihuUser saihuUser) throws Exception {

        accountService.createAccount(saihuUser);
        return ResponseData.success();
    }

    @GetResource(name = "单点登录", path = "/ssoLogin", requiredPermission = false, requiredLogin = false)
    @ApiOperation("单点登录")
    public ResponseData ssoLogin() {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        // 构建登录参数
        JwtLoginParamBuilder builder = new JwtLoginParamBuilder()
                // 密钥
                .clientSecret("5a3961f3-c3dc-494f-91a5-063323aeb823")
                // 公司唯一标识
                .clientId("b42a4d3a-b606-11ee-92fb-3ce1a17f9380")
                // 第三方用户唯一标识
                .userUniqueKey("S20190109")
                // 用户手机号
                .mobile("13543292129")
                // 用户邮箱
                .email("")
                // 用户名
                .account("S20190109")
                // 姓名
                .nickname("邓亚良");
        String redirectUrl = null;
        try {
            // 获取登录URL
            redirectUrl = builder.build().getRedirectUrl();
        } catch (JwtLoginException e) {
            e.printStackTrace();
        }
        return ResponseData.success(redirectUrl);
    }
}
