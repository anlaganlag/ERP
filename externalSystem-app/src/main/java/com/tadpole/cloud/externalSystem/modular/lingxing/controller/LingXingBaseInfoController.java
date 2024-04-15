package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.asinking.uc.sso.bean.JwtSSOLoginDTO;
import com.asinking.uc.sso.exception.SSOLoginException;
import com.asinking.uc.sso.service.JWTLoginClient;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: ty
 * @description: 领星基础数据Controller类
 * @date: 2022/4/22
 */
@RestController
@ApiResource(name = "领星基础数据Controller类", path = "/lingXingBaseInfo")
@Api(tags = "领星基础数据Controller类")
public class LingXingBaseInfoController {

    private static final String DOMAIN = "https://jcbsc.lingxing.com";

    // TODO 需要替换为配置在ERP系统的验签密钥字段值 （菜单路径：设置/业务配置/全局/单点登录设置：验签密钥）
    private static final String SIGN_KEY = "071ce3abdb0e43b2819178e0702d9e1b";

    // TODO 需替换为配置在ERP系统的企业唯一标识值 （菜单路径：设置/业务配置/全局/单点登录设置：企业唯一标识）
    private static final String CLIENT_ID = "0F3A6FB10B2E910693F7477687014D61";

    // TODO 如果需要指定当前jwtToken的过期时间，请手动调整此项值
    public static final Long EXPIRE_TIME = 36000L;//过期时间，单位秒，默认一小时

    @Resource
    private LingXingBaseInfoService lingXingBaseInfoService;

    @GetResource(name = "查询亚马逊店铺信息", path = "/getSellerLists", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊店铺信息")
    public LingXingBaseRespData getSellerLists() throws Exception {
        return lingXingBaseInfoService.getSellerLists();
    }

    @GetResource(name = "单点登录地址", path = "/getLoginUrl", requiredPermission = false, requiredLogin = true)
    @ApiOperation("单点登录地址")
    public ResponseData getLoginUrl() throws Exception {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        String uniqueKey = loginUser.getAccount();
        if(uniqueKey.equals("S20190109")){
            uniqueKey="userId001";
        }

        String redirectUrl = null;

        JWTLoginClient client = new JWTLoginClient();
        JwtSSOLoginDTO loginDTO = JwtSSOLoginDTO.builder()
                .domain(DOMAIN)
                .signKey(SIGN_KEY)
                .clientId(CLIENT_ID)
                .expireMilliseconds(EXPIRE_TIME)
                .uniqueKey(uniqueKey)
                .mobile(loginUser.getPhone())
                .email(loginUser.getEmail())
                .build();
        try {
            redirectUrl = client.getRedirectUrl(loginDTO);
        } catch (SSOLoginException e) {
            // TODO 异常处理
        }

        return ResponseData.success(redirectUrl);
    }

}
