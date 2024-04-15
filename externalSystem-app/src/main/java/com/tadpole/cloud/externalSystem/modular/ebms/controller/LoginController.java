package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.externalSystem.config.EbmsInterfaceConfig;
import com.tadpole.cloud.externalSystem.modular.ebms.model.params.JwtUser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
@RestController
@Api(tags = "EBMS登录")
@ApiResource(name = "EBMS登录", path = "/login")
public class LoginController {

    @Autowired
    EbmsInterfaceConfig interfaceConfig;

    @Autowired
    RestTemplate restTemplate;

    private static String GET_TOKEN = "/api/Token/ssoLogin?ssotoken=";

    @GetResource(name = "单点登录", path = "/ssoLogin")
    @ApiOperation("单点登录")
    public ResponseData ssoLogin() {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        if(loginUser.getAccount().equals("admin")){
            loginUser.setAccount("administrator");
            loginUser.setName("超级管理员");
        }

        JwtUser jwtUser = new JwtUser();
        jwtUser.setAccount(loginUser.getAccount());
        jwtUser.setName(loginUser.getName());
        jwtUser.setPhone(loginUser.getPhone());
        jwtUser.setEmail(loginUser.getEmail());
        String signString = "com.jcintergl.jcerp"+  loginUser.getAccount()+ loginUser.getName();
        jwtUser.setSign(DigestUtils.md5Hex(signString.getBytes(StandardCharsets.UTF_8)).toUpperCase());

        String userJson = JSON.toJSONString(jwtUser);//序列化user
        JwtBuilder jwtBuilder = Jwts.builder(); //获得JWT构造器
        Map<String,Object> map=new Hashtable<>();
        map.put("user",userJson);
        String token = jwtBuilder.setSubject("jwt") //设置用户数据
                .setIssuedAt(new Date()) //设置jwt生成时间
                .setId("1") //设置id为token id
                .setClaims(map) //通过map传值
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*10)) //设置token有效期
                .signWith(SignatureAlgorithm.HS256, "qianfeng") //设置token加密方式和密码
                .compact(); //生成token字符串

        String url = interfaceConfig.getServer()+GET_TOKEN+token;

        String restTemplateResult = restTemplate.getForObject(url, String.class);

        JSONObject data = JSONObject.parseObject(restTemplateResult);
        if(data.get("data")==null){
            return   ResponseData.success("");
        }
        String ebms_token = JSONObject.parseObject(data.get("data").toString()).get("access_token").toString();
        url = interfaceConfig.getLoginUrl()+ "/#/login?token="+ebms_token;
        return ResponseData.success(url);
    }


}
